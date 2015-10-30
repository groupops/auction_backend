/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.epam.training.auction_backend.client;

import com.epam.training.auction.common.UserTransferObject;
import com.epam.training.auction.common.UsersService;
import org.apache.camel.util.IOHelper;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public final class UIClient {

  private UIClient() {
  }

  public static void main(final String[] args) throws InterruptedException {
    addUser();
  }

  public static void addUser(){
    AbstractApplicationContext context =
        new ClassPathXmlApplicationContext("camel-client-remoting.xml");
    UsersService
        usersService =
        context.getBean("usersServiceImplProxy", UsersService.class);

    System.out.println("Invoking the logging");
    UserTransferObject userTransferObject =
        new UserTransferObject("user", "pass");
    usersService.addUser(userTransferObject);
    System.out.println("User is logged");

    IOHelper.close(context);
  }

}
