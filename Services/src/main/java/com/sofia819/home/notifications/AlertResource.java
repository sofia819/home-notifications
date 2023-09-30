package com.sofia819.home.notifications;

import com.sofia819.home.notifications.response.AlertMessageResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import javax.inject.Inject;

@Path("/alert")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AlertResource {

  private final AlertManager alertManager;

  @Inject
  public AlertResource(AlertManager alertManager) {
    this.alertManager = alertManager;
  }

  @POST
  @Path("/text")
  public AlertMessageResponse sendText() {
    return alertManager.sendTextAlert();
  }
}
