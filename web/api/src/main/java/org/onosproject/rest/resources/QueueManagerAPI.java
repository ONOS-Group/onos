// /*
//  * Copyright 2015-present Open Networking Foundation
//  *
//  * Licensed under the Apache License, Version 2.0 (the "License");
//  * you may not use this file except in compliance with the License.
//  * You may obtain a copy of the License at
//  *
//  *     http://www.apache.org/licenses/LICENSE-2.0
//  *
//  * Unless required by applicable law or agreed to in writing, software
//  * distributed under the License is distributed on an "AS IS" BASIS,
//  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  * See the License for the specific language governing permissions and
//  * limitations under the License.
//  */
// package org.onosproject.rest.resources;

// import com.fasterxml.jackson.databind.JsonNode;
// import com.fasterxml.jackson.databind.node.ArrayNode;
// import com.fasterxml.jackson.databind.node.ObjectNode;
// import com.google.common.collect.ArrayListMultimap;
// import com.google.common.collect.ListMultimap;
// import org.onlab.util.ItemNotFoundException;
// import org.onosproject.app.ApplicationService;
// import org.onosproject.core.ApplicationId;
// import org.onosproject.drivers.ovsdb.OvsdbQueueConfig;
// import org.onosproject.net.Device;
// import org.onosproject.net.DeviceId;
// import org.onosproject.net.device.DeviceService;
// import org.onosproject.net.flow.FlowEntry;
// import org.onosproject.net.flow.FlowRule;
// import org.onosproject.net.flow.FlowRuleService;
// import org.onosproject.net.flow.IndexTableId;
// import org.onosproject.rest.AbstractWebResource;
// import org.onosproject.net.driver.DriverHandler;
// import com.google.common.collect.ArrayListMultimap;
// import com.google.common.collect.ListMultimap;
// import org.onosproject.net.flow.IndexTableId;
// import org.onosproject.net.driver.DriverService;


// import javax.ws.rs.Consumes;
// import javax.ws.rs.DELETE;
// import javax.ws.rs.GET;
// import javax.ws.rs.POST;
// import javax.ws.rs.Path;
// import javax.ws.rs.PathParam;
// import javax.ws.rs.Produces;
// import javax.ws.rs.QueryParam;
// import javax.ws.rs.core.Context;
// import javax.ws.rs.core.MediaType;
// import javax.ws.rs.core.Response;
// import javax.ws.rs.core.UriBuilder;
// import javax.ws.rs.core.UriInfo;
// import java.io.IOException;
// import java.io.InputStream;
// import java.util.ArrayList;
// import java.util.EnumSet;
// import java.util.List;
// import java.util.stream.StreamSupport;

// import static org.onlab.util.Tools.nullIsIllegal;
// import static org.onlab.util.Tools.nullIsNotFound;
// import static org.onlab.util.Tools.readTreeFromStream;

// import org.onosproject.net.behaviour.QueueConfigBehaviour;
// import org.onosproject.net.behaviour.QueueDescription.Type;
// import org.onosproject.net.behaviour.QueueDescription;
// import org.onosproject.net.behaviour.DefaultQueueDescription;
// import org.onosproject.net.behaviour.QueueId;
// import org.onosproject.ovsdb.controller.OvsdbClientService;
// import org.onosproject.ovsdb.controller.OvsdbController;
// import org.onosproject.ovsdb.controller.OvsdbNodeId;
// import org.onosproject.ovsdb.controller.OvsdbQueue;


// /**
//  * Query and program flow rules.
//  */

// @Path("queue")
// public class QueueManagerAPI extends AbstractWebResource {

//     @Context
//     private UriInfo uriInfo;

//     private static int qid = 0;

//     /**
//      * Gets all pending flow entries. Returns array of all pending flow rules in the system.
//      *
//      * @return 200 OK with a collection of flows
//      * @onos.rsModel FlowEntries
//      */
//     @POST
//     @Produces(MediaType.APPLICATION_JSON)
//     @Path("{deviceId}/{queueId}")
//     public Response deleteQueue(@PathParam("deviceId") String deviceId,@PathParam("queueId") String queueId) {

// //        FlowRuleService service = get(FlowRuleService.class);
// //        Iterable<FlowEntry> flowEntries = service.getFlowEntries(DeviceId.deviceId(deviceId));
//         DriverService driverHandler = get(DriverService.class);

//         OvsdbController ovsController = driverHandler.get(OvsdbController.class);
//         OvsdbNodeId nodeId = changeDeviceIdToNodeId(driverHandler.data().deviceId());

//         OvsdbClientService ovsdbClientService = ovsController.getOvsdbClient(nodeId);

//         QueueId queueID = QueueId.queueId(queueId);
//         ovsdbClient.dropQueue(queueID);

        
//         // DeviceId deviceID = DeviceId.deviceId("of:0000000000000001");

//         // DriverHandler controllerHandler = driverHandler.createHandler(deviceID);
//         //  // switchHandler = driverService.createHandler(switchId);
//         // OvsdbQueueConfig ovsdbQueueConfig = OvsdbQueueConfig().getOvsdbClient(controllerHandler);
//         //  // ovsdbQueueConfig.setHandler(controllerHandler);
//         //  // ovsdbQueueConfig.setHandler(switchHandler);
//         // DefaultQueueDescription queueDescription =
//         //          (DefaultQueueDescription) DefaultQueueDescription.builder()
//         //                  .queueId(QueueId.queueId("queue" + qid))
//         //                  .type(EnumSet.of(QueueDescription.Type.BURST))
//         //                  .maxRate(Bandwidth.of(1000, DataRateUnit.KBPS))
//         //                  .minRate(Bandwidth.of(10, DataRateUnit.KBPS))
//         //                  .burst(Long.valueOf(1024))
//         //                  .priority(Long.valueOf(32))
//         //                  .build();

//         //  qid++;
//         //  ovsdbQueueConfig.addQueue(queueDescription);
//         //  System.out.println("Add a queue to a device successfully!");
//         //  System.out.println("the queue info is:" + queueDescription.toString());

//         ObjectNode node = mapper().createObjectNode().put("Response","Queue deleted successfully");
//         return ok(node).build();
//     }

// }


// //
// //boolean deletionStatus = deviceService.deleteQueue(deviceId, queueId);
// //        if (deletionStatus) {
// //ObjectNode node = mapper().createObjectNode().put("Response","OK");
// //            return ok(node).build();
// ////            "Queue " + queueId + " deleted successfully on device " + deviceId;
// //        } else {
// //ObjectNode node = mapper().createObjectNode().put("Response","FAIL");
// //            return ok(node).build();
// ////            return "Failed to delete queue " + queueId + " on device " + deviceId;
// //        }
// //                }
