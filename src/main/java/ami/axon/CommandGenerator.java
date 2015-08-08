/*
 * Copyright (c) 2010-2014. Axon Framework
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ami.axon;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;

import ami.axon.api.CreateToDoItemCommand;
import ami.axon.api.MarkCompletedCommand;

/**
 * Runner that uses the provided CommandGateway to send some commands to our application.
 *
 * @author Jettro Coenradie
 */
public class CommandGenerator {

    public static void sendCommands(CommandGateway commandGateway) {
        final String itemId1 = UUID.randomUUID().toString();
        final String itemId2 = UUID.randomUUID().toString();
        commandGateway.sendAndWait(new CreateToDoItemCommand(itemId1, "Check if it really works!"));
        commandGateway.sendAndWait(new CreateToDoItemCommand(itemId2, "Think about the next steps!"));
        commandGateway.sendAndWait(new MarkCompletedCommand(itemId1));
    }
}
