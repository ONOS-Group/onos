package org.foo.app;

import org.onosproject.net.behaviour.QueueConfigBehaviour;
import org.onosproject.net.behaviour.QueueDescription.Type;
import org.onosproject.net.behaviour.QueueDescription;
import org.onosproject.net.behaviour.DefaultQueueDescription;
import org.onosproject.net.behaviour.QueueId;

import java.util.Collection;
import java.util.Collections;
import org.onosproject.net.behaviour.QueueId;
//import org.onosproject.drivers.ovsdb.OvsdbQueueConfig;


public class QueueOpImp  {

   /*      private static Integer qid = 0;
        private static final String controllerId = "of:0000000000000001";

        public boolean createQueue() {

                DriverHandler controllerHandler = driverService.createHandler(controllerId);
                // switchHandler = driverService.createHandler(switchId);
                OvsdbQueueConfig ovsdbQueueConfig = OvsdbQueueConfig().getOvsdbClient(controllerHandler);
                // ovsdbQueueConfig.setHandler(controllerHandler);
                // ovsdbQueueConfig.setHandler(switchHandler);
                DefaultQueueDescription queueDescription =
                        (DefaultQueueDescription) DefaultQueueDescription.builder()
                                .queueId(QueueId.queueId("queue" + qid))
                                .type(EnumSet.of(QueueDescription.Type.BURST))
                                .maxRate(Bandwidth.of(1000, DataRateUnit.KBPS))
                                .minRate(Bandwidth.of(10, DataRateUnit.KBPS))
                                .burst(Long.valueOf(1024))
                                .priority(Long.valueOf(32))
                                .build();

                qid++;
                ovsdbQueueConfig.addQueue(queueDescription);
                System.out.println("Add a queue to a device successfully!");
                System.out.println("the queue info is:" + queueDescription.toString());
                return true;
        } */
//
//        public Collection<QueueDescription> getQueues() {
//                OvsdbQueueConfig ovsdbQueueConfig = new OvsdbQueueConfig();
//                Collection<QueueDescription> queues = ovsdbQueueConfig.getQueues();
//
//                return queues;
//        }
    
}
