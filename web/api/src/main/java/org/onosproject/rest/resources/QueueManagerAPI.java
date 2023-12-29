package org.onosproject.rest.resources;

import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import org.onlab.util.ItemNotFoundException;
import org.onosproject.app.ApplicationService;
import org.onosproject.core.ApplicationId;
import org.onosproject.net.Device;
import org.onosproject.net.DeviceId;
import org.onosproject.net.device.DeviceService;
import org.onosproject.net.flow.FlowEntry;
import org.onosproject.net.flow.FlowRule;
import org.onosproject.net.flow.FlowRuleService;
import org.onosproject.net.flow.IndexTableId;
import org.onosproject.rest.AbstractWebResource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

@Path("/queue")
public class QueueController extends AbstractWebResource {

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{deviceId}/{queueId}")
    public String deleteQueue(@PathVariable String deviceId, @PathVariable int queueId) {

        DeviceService deviceService = get(DeviceService.class);

        boolean deletionStatus = deviceService.deleteQueue(deviceId, queueId);
        if (deletionStatus) {
            return "Queue " + queueId + " deleted successfully on device " + deviceId;
        } else {
            return "Failed to delete queue " + queueId + " on device " + deviceId;
        }
    }
}
