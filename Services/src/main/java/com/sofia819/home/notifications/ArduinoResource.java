package com.sofia819.home.notifications;

import com.sofia819.home.notifications.request.ArduinoLogRequest;
import com.sofia819.home.notifications.response.ArduinoLogResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/arduino")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArduinoResource {

  private static final Logger LOG = LoggerFactory.getLogger(ArduinoResource.class);

  @POST
  @Path("/log")
  public ArduinoLogResponse log(ArduinoLogRequest request) {
    LOG.info(request.message());

    return new ArduinoLogResponse(true);
  }
}
