/*
 * Copyright 2023-present Open Networking Foundation
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
package org.qosproject;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import org.onosproject.net.behaviour.QueueId;
import org.onosproject.net.behaviour.QueueConfigBehaviour;
import org.onosproject.net.behaviour.QueueDescription.Type;
import org.onosproject.net.behaviour.QueueDescription;
import org.onosproject.net.behaviour.DefaultQueueDescription;
import org.onlab.util.Bandwidth;


import org.onosproject.rest.AbstractWebResource;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.Optional;

import static org.onlab.util.Tools.nullIsNotFound;

/**
 * Sample web resource.
 */
@Path("api")
public class AppWebResource extends AbstractWebResource {

    /**
     * Get hello world greeting.
     *
     * @return 200 OK
     */
    @GET
    @Path("getQueue")
    public Response getQueue() {
        ObjectNode node = mapper().createObjectNode().put("getQueue","getQueue");
        return ok(node).build();
    }

    @GET
    @Path("getQueues")
    public Response getQueues() {

        // Create a QueueId

        QueueId queueId = QueueId.queueId("myQueue");

        // Create an EnumSet for the Queue type (for example, MAX)
        EnumSet<QueueDescription.Type> type = EnumSet.of(QueueDescription.Type.MAX);

        // Set optional parameters if needed (Example: maxRate, minRate, burst, priority)
        Optional<Bandwidth> maxRateOp = Optional.of(Bandwidth.bps(1000000)); // 1 Mbps
        Optional<Bandwidth> minRateOp = Optional.of(Bandwidth.bps(500000)); // 0.5 Mbps
        Optional<Long> burstOp = Optional.of(200L);
        Optional<Long> priorityOp = Optional.of(5L);

        // Use the QueueDescription builder to create the QueueDescription
        DefaultQueueDescription.Builder builder = DefaultQueueDescription.builder()
                .queueId(queueId)
                .type(type)
                .maxRate(maxRateOp.get()) // Add maxRate if present
                .minRate(minRateOp.get()) // Add minRate if present
                .burst(burstOp.get()) // Add burst if present
                .priority(priorityOp.get()); // Add priority if present

        // Build the QueueDescription
        QueueDescription queue = builder.build();

        // QueueOpImp queueOpImp = new QueueOpImp();
        
        // Collection<QueueDescription> queues = queueOpImp.getQueues();

        ObjectMapper objectMapper = new ObjectMapper();

        // Create an ObjectNode to hold the queue information
        ObjectNode rootNode = objectMapper.createObjectNode();
        ArrayNode queuesArray = rootNode.putArray("queues");


        ObjectNode queueNode = objectMapper.createObjectNode();

        // Add queue information to the queueNode
        queueNode.put("queueId", queueId.name());
        queue.dscp().ifPresent(dscp -> queueNode.put("dscp", dscp));
        queueNode.put("type", queue.type().toString());

        queue.maxRate().ifPresent(maxRate -> queueNode.put("maxRate", maxRate.toString()));
        queue.minRate().ifPresent(minRate -> queueNode.put("minRate", minRate.toString()));
        queue.burst().ifPresent(burst -> queueNode.put("burst", burst));
        queue.priority().ifPresent(priority -> queueNode.put("priority", priority));

        // Add the queueNode to the queuesArray
        queuesArray.add(queueNode);

        queuesArray.add(queueNode);






        // Iterate through the Collection of QueueDescriptions
        /* for (QueueDescription queue : queues) {
            ObjectNode queueNode = objectMapper.createObjectNode();
            QueueId queueId = queue.queueId();

            // Add queue information to the queueNode
            queueNode.put("queueId", queueId.name());
            queue.dscp().ifPresent(dscp -> queueNode.put("dscp", dscp));
            queueNode.put("type", queue.type().toString());

            queue.maxRate().ifPresent(maxRate -> queueNode.put("maxRate", maxRate.toString()));
            queue.minRate().ifPresent(minRate -> queueNode.put("minRate", minRate.toString()));
            queue.burst().ifPresent(burst -> queueNode.put("burst", burst));
            queue.priority().ifPresent(priority -> queueNode.put("priority", priority));

            // Add the queueNode to the queuesArray
            queuesArray.add(queueNode);
        } */
        
        return ok(rootNode).build();
    }

    @POST
    @Path("updateQueue")
    public Response updateQueue() {
        ObjectNode node = mapper().createObjectNode().put("updateQueue","updateQueue");
        return ok(node).build();
    }

    @DELETE
    @Path("deleteQueue")
    public Response deleteQueue() {
        ObjectNode node = mapper().createObjectNode().put("deleteQueue","deleteQueue");
        return ok(node).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{deviceId}/{queueId}")
    public Response deleteQueue(@PathParam("deviceId") String deviceId,@PathParam("queueId") String queueId) {

//        FlowRuleService service = get(FlowRuleService.class);
//        Iterable<FlowEntry> flowEntries = service.getFlowEntries(DeviceId.deviceId(deviceId));
        DriverService driverHandler = get(DriverService.class);

        OvsdbController ovsController = driverHandler.get(OvsdbController.class);
        OvsdbNodeId nodeId = changeDeviceIdToNodeId(driverHandler.data().deviceId());

        OvsdbClientService ovsdbClientService = ovsController.getOvsdbClient(nodeId);

        QueueId queueID = QueueId.queueId(queueId);
        ovsdbClient.dropQueue(queueID);

        
        // DeviceId deviceID = DeviceId.deviceId("of:0000000000000001");

        // DriverHandler controllerHandler = driverHandler.createHandler(deviceID);
        //  // switchHandler = driverService.createHandler(switchId);
        // OvsdbQueueConfig ovsdbQueueConfig = OvsdbQueueConfig().getOvsdbClient(controllerHandler);
        //  // ovsdbQueueConfig.setHandler(controllerHandler);
        //  // ovsdbQueueConfig.setHandler(switchHandler);
        // DefaultQueueDescription queueDescription =
        //          (DefaultQueueDescription) DefaultQueueDescription.builder()
        //                  .queueId(QueueId.queueId("queue" + qid))
        //                  .type(EnumSet.of(QueueDescription.Type.BURST))
        //                  .maxRate(Bandwidth.of(1000, DataRateUnit.KBPS))
        //                  .minRate(Bandwidth.of(10, DataRateUnit.KBPS))
        //                  .burst(Long.valueOf(1024))
        //                  .priority(Long.valueOf(32))
        //                  .build();

        //  qid++;
        //  ovsdbQueueConfig.addQueue(queueDescription);
        //  System.out.println("Add a queue to a device successfully!");
        //  System.out.println("the queue info is:" + queueDescription.toString());

        ObjectNode node = mapper().createObjectNode().put("Response","Queue deleted successfully");
        return ok(node).build();

}
