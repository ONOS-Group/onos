// package org.foo.app;

// import org.onosproject.net.behaviour.QueueConfigBehaviour;
// import org.onosproject.net.behaviour.QueueDescription.Type;
// import org.onosproject.net.behaviour.QueueDescription;
// import org.onosproject.net.behaviour.DefaultQueueDescription;
// import org.onosproject.net.behaviour.QueueId;

// import java.util.Collection;
// import java.util.Collections;
// import org.onlab.util.Bandwidth;
// import org.onlab.util.DataRateUnit;

// import java.util.EnumSet;


// public class QueueOpImp  {

//         private static Integer qid = 0;
//         private static final String controllerId = "of:0000000000000001";

//         public boolean createQueue() {

//                 DriverHandler controllerHandler = driverService.createHandler(controllerId);
//                 // switchHandler = driverService.createHandler(switchId);
//                 OvsdbQueueConfig ovsdbQueueConfig = OvsdbQueueConfig().getOvsdbClient(controllerHandler);
//                 // ovsdbQueueConfig.setHandler(controllerHandler);
//                 // ovsdbQueueConfig.setHandler(switchHandler);
//                 DefaultQueueDescription queueDescription =
//                         (DefaultQueueDescription) DefaultQueueDescription.builder()
//                                 .queueId(QueueId.queueId("queue" + qid))
//                                 .type(EnumSet.of(QueueDescription.Type.BURST))
//                                 .maxRate(Bandwidth.of(1000, DataRateUnit.KBPS))
//                                 .minRate(Bandwidth.of(10, DataRateUnit.KBPS))
//                                 .burst(Long.valueOf(1024))
//                                 .priority(Long.valueOf(32))
//                                 .build();

//                 qid++;
//                 ovsdbQueueConfig.addQueue(queueDescription);
//                 System.out.println("Add a queue to a device successfully!");
//                 System.out.println("the queue info is:" + queueDescription.toString());
//                 return true;
//         }

//       public Collection<QueueDescription> getQueues() {
//               OvsdbQueueConfig ovsdbQueueConfig = new OvsdbQueueConfig();
//               Collection<QueueDescription> queues = ovsdbQueueConfig.getQueues();

//               return queues;
//       }
    
// }

import org.onosproject.net.DeviceId;
import org.onosproject.net.flow.DefaultQueueDescription;
import org.onosproject.net.flow.QueueDescription;
import org.onosproject.net.flow.QueueId;
import org.onosproject.net.flow.QueueProperty;
import org.onosproject.net.flow.QueueProperty.Type;
import org.onosproject.net.flow.instructions.Instructions;
import org.onosproject.net.flowobjective.DefaultQueueObjective;
import org.onosproject.net.flowobjective.FlowObjectiveService;
import org.onosproject.net.flowobjective.Objective;
import org.onosproject.net.flowobjective.ObjectiveContext;
import org.onosproject.net.flowobjective.ForwardingObjective;

import org.onosproject.core.ApplicationId;
import org.onosproject.core.CoreService;
import org.onosproject.net.flowobjective.FlowObjectiveService;
import org.onosproject.core.Application;

// Get the Core Service from ONOS context
CoreService coreService = serviceDirectory.get(CoreService.class);

// Create an application ID (replace "org.onosproject.example" with your app's name)
ApplicationId appId = coreService.registerApplication("org.onosproject.example");

// Get the FlowObjectiveService using the application ID
FlowObjectiveService flowObjectiveService = serviceDirectory.get(FlowObjectiveService.class);

// Now you can use flowObjectiveService to manage flow objectives (e.g., adding queues)


// Define Queue Configuration
DeviceId deviceId = DeviceId.deviceId("of:0000000000000001"); // Device ID of the switch
QueueId queueId = QueueId.queueId(1); // Unique ID for the queue
QueueDescription queueDescription = DefaultQueueDescription.builder()
        .deviceId(deviceId)
        .queueId(queueId)
        .maxRate(1000L) // Maximum rate in Kbps
        .minRate(500L) // Minimum rate in Kbps
        .queueProperties(List.of(QueueProperty.queueProperty(Type.MIN_RATE, 500L))) // Additional properties
        .build();

// Create Queue Objective
Objective queueObjective = DefaultQueueObjective.builder()
        .addQueue(queueDescription)
        .fromApp(appId) // Your application ID
        .add();

// Submit Queue Objective to ONOS
ObjectiveContext context = new DefaultObjectiveContext(
        (objective) -> log.info("Queue configured successfully"),
        (objective, error) -> log.error("Queue configuration failed: {}", error));
flowObjectiveService.forward(deviceId, queueObjective);

