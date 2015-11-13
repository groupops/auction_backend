package com.epam.training.auction.model;

import com.epam.training.auction.common.BiddingRaceException;
import com.epam.training.auction.common.UserBidTransferObject;
import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import com.epam.training.auction.exception.BiddingException;
import org.apache.log4j.Logger;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.zookeeper.CreateMode.EPHEMERAL_SEQUENTIAL;
import static org.apache.zookeeper.Watcher.Event.EventType.None;
import static org.apache.zookeeper.ZooDefs.Ids.OPEN_ACL_UNSAFE;



/**
 * Created by Dmytro_Ulanovych on 10/29/2015.
 */
@Component
public class ZooKeeperBiddingStore implements BiddingStore, Watcher, Closeable {
    private static final Logger LOG = Logger.getLogger(ZooKeeperBiddingStore.class);
    //TODO: use path from properties
    private static final String ZOO_KEEPER_CONNECTION_PATH = "10.30.0.99:2181";
    private static final int ZOO_KEEPER_SESSION_TTL = 2 * 60 * 1000;
    private static final String AUCTION_BIDS_PATH = "/auction/%d/bid";
    private static final Pattern AUCTION_ID_PATTERN = Pattern.compile("[0-9]+");
    private ZooKeeper zooKeeper;
    private UsersService usersService;

    @Autowired
    public ZooKeeperBiddingStore(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserBidTransferObject getMaxBid(long auctionId) {
        return getHighestBidByPath(String.format(AUCTION_BIDS_PATH, auctionId));
    }

    private UserBidTransferObject getHighestBidByPath(String auctionPath) {
        try {
            long auctionId = getAuctionIdFromPath(auctionPath);
            long maxBid = getMaxBidByAuctionPath(auctionPath);
            long bidderId = getBidderIdByPath(auctionPath + "/" + maxBid);
            UserTransferObject user = usersService.getUserById(bidderId);
            return new UserBidTransferObject(user, auctionId, maxBid);
        } catch (KeeperException | InterruptedException e) {
            throw new BiddingException(e);
        }
    }

    private long getAuctionIdFromPath(String path) {
        Matcher matcher = AUCTION_ID_PATTERN.matcher(path);
        String auctionId = "";
        if (matcher.find()) {
            auctionId = matcher.group();
        }
        return Long.valueOf(auctionId);
    }

    private long getMaxBidByAuctionPath(String auctionPath) throws KeeperException, InterruptedException {
        List<String> bids = getZooKeeper().getChildren(auctionPath, true);
        return bids.stream().mapToLong(Long::valueOf).max().orElseThrow(() -> new BiddingException("No Bids in this auction!"));
    }

    private long getBidderIdByPath(String bidPath) throws KeeperException, InterruptedException {
        Stat stat = getZooKeeper().exists(bidPath, true);
        byte[] serializedBidderId = getZooKeeper().getData(bidPath, true, stat);
        return (long) SerializationUtils.deserialize(serializedBidderId);
    }

    @Override
    public boolean makeBid(long auctionId, long amount, long userId) throws BiddingRaceException {
        String pathForCurrentAuctionsBid = String.format(AUCTION_BIDS_PATH, auctionId);
        createNodeIfNotExist(pathForCurrentAuctionsBid);
        makeBid(pathForCurrentAuctionsBid, amount, userId);
        return true;
    }

    private void makeBid(String bidsRoot, long bidValue, long userId) throws BiddingRaceException {
        String currentBidPath = bidsRoot + "/" + String.valueOf(bidValue);
        try {
            Stat stat = getZooKeeper().exists(currentBidPath, true);
            if (stat != null) {
                byte[] serializedPreviousBidder = getZooKeeper().getData(currentBidPath, true, stat);
                long previousBidderId = (long) SerializationUtils.deserialize(serializedPreviousBidder);
                UserTransferObject otherBidder = usersService.getUserById(previousBidderId);
                UserBidTransferObject highestBid = getHighestBidByPath(bidsRoot);
                throw new BiddingRaceException(otherBidder, highestBid.getBid());
            } else {
                getZooKeeper().create(currentBidPath, SerializationUtils.serialize(userId), OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
            }
        } catch (KeeperException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    private ZooKeeper getZooKeeper() {
        if (zooKeeper == null) {
            try {
                zooKeeper = new ZooKeeper(ZOO_KEEPER_CONNECTION_PATH, ZOO_KEEPER_SESSION_TTL, this);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return zooKeeper;
    }

    private void createNodeIfNotExist(String fullNodePath) {
        String path = "";
        StringTokenizer tokens = new StringTokenizer(fullNodePath, "/");
        try {
            while (tokens.hasMoreTokens()) {
                path += "/" + tokens.nextToken();
                Stat stat = getZooKeeper().exists(path, true);
                if (stat == null) {
                    getZooKeeper().create(path, new byte[0], OPEN_ACL_UNSAFE, EPHEMERAL_SEQUENTIAL);
                }
            }
        } catch (KeeperException | InterruptedException e) {
            LOG.error("Cannot create a node " + path, e);
        }
    }

    public void process(WatchedEvent event) {
        LOG.debug(event);
        if (event.getType() == None) {
            switch (event.getState()) {
                case SyncConnected:
                    LOG.debug("Zookeeper client has been connected.");
                    break;
                case Disconnected:
                    LOG.debug("Zookeeper client has been disconnected.");
                    break;
                case Expired:
                    LOG.debug("Session has been timed out.");
                default:
                    break;
            }
        }
    }

    @Override
    public void close() throws IOException {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            LOG.warn("Unable to close Zookeeper", e);
        }
    }
}
