// // package org.qosproject;

// // import org.onosproject.net.behaviour.QueueConfigBehaviour;
// // import org.onosproject.net.behaviour.QueueDescription.Type;
// // import org.onosproject.net.behaviour.QueueDescription;
// // import org.onosproject.net.behaviour.DefaultQueueDescription;
// // import org.onosproject.net.behaviour.QueueId;

// // import java.util.Collection;
// // import java.util.Collections;
// // import org.onlab.util.Bandwidth;
// // import org.onlab.util.DataRateUnit;

// // import java.util.EnumSet;

// // public class QueueOpImp  {

// //         private static Integer qid = 0;
// //         private static final String controllerId = "of:0000000000000001";

// //         public boolean createQueue() {

// //                 DriverHandler controllerHandler = driverService.createHandler(controllerId);
// //                 // switchHandler = driverService.createHandler(switchId);
// //                 OvsdbQueueConfig ovsdbQueueConfig = OvsdbQueueConfig().getOvsdbClient(controllerHandler);
// //                 // ovsdbQueueConfig.setHandler(controllerHandler);
// //                 // ovsdbQueueConfig.setHandler(switchHandler);
// //                 DefaultQueueDescription queueDescription =
// //                         (DefaultQueueDescription) DefaultQueueDescription.builder()
// //                                 .queueId(QueueId.queueId("queue" + qid))
// //                                 .type(EnumSet.of(QueueDescription.Type.BURST))
// //                                 .maxRate(Bandwidth.of(1000, DataRateUnit.KBPS))
// //                                 .minRate(Bandwidth.of(10, DataRateUnit.KBPS))
// //                                 .burst(Long.valueOf(1024))
// //                                 .priority(Long.valueOf(32))
// //                                 .build();

// //                 qid++;
// //                 ovsdbQueueConfig.addQueue(queueDescription);
// //                 System.out.println("Add a queue to a device successfully!");
// //                 System.out.println("the queue info is:" + queueDescription.toString());
// //                 return true;
// //         }

// //       public Collection<QueueDescription> getQueues() {
// //               OvsdbQueueConfig ovsdbQueueConfig = new OvsdbQueueConfig();
// //               Collection<QueueDescription> queues = ovsdbQueueConfig.getQueues();

// //               return queues;
// //       }
    
// // }



// // CoreService coreService = serviceDirectory.get(CoreService.class);

// // // Create an application ID (replace "org.onosproject.example" with your app's name)
// // ApplicationId appId = coreService.registerApplication("org.onosproject.example");

// // // Get the FlowObjectiveService using the application ID
// // FlowObjectiveService flowObjectiveService = serviceDirectory.get(FlowObjectiveService.class);

// // // Now you can use flowObjectiveService to manage flow objectives (e.g., adding queues)


// // // Define Queue Configuration
// // DeviceId deviceId = DeviceId.deviceId("of:0000000000000001"); // Device ID of the switch
// // QueueId queueId = QueueId.queueId(1); // Unique ID for the queue
// // QueueDescription queueDescription = DefaultQueueDescription.builder()
// //         .deviceId(deviceId)
// //         .queueId(queueId)
// //         .maxRate(1000L) // Maximum rate in Kbps
// //         .minRate(500L) // Minimum rate in Kbps
// //         .queueProperties(List.of(QueueProperty.queueProperty(Type.MIN_RATE, 500L))) // Additional properties
// //         .build();

// // // Create Queue Objective
// // Objective queueObjective = DefaultQueueObjective.builder()
// //         .addQueue(queueDescription)
// //         .fromApp(appId) // Your application ID
// //         .add();

// // // Submit Queue Objective to ONOS
// // ObjectiveContext context = new DefaultObjectiveContext(
// //         (objective) -> log.info("Queue configured successfully"),
// //         (objective, error) -> log.error("Queue configuration failed: {}", error));
// // flowObjectiveService.forward(deviceId, queueObjective);

// package org.qosproject;

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
