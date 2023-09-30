package com.sofia819.home.notifications;

import com.sofia819.home.notifications.request.DoorStatusRequest;
import com.sofia819.home.notifications.response.AlertStatusResponse;
import com.sofia819.home.notifications.response.DoorStatusResponse;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import javax.inject.Inject;

@Path("/status")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {

  private final AlertManager alertManager;
  private final DoorManager doorManager;

  @Inject
  public StatusResource(AlertManager alertManager, DoorManager doorManager) {
    this.alertManager = alertManager;
    this.doorManager = doorManager;
  }

  @GET
  @Path("/alert")
  public AlertStatusResponse shouldSendAlert() {
    return new AlertStatusResponse(alertManager.shouldSendAlert());
  }

  @PUT
  @Path("/alert")
  public AlertStatusResponse toggleEnableAlert() {
    alertManager.toggleAlertEnabled();
    return shouldSendAlert();
  }

  @GET
  @Path("/door")
  public DoorStatusResponse isDoorOpened() {
    return new DoorStatusResponse(doorManager.getIsDoorOpened(),
        doorManager.getDoorOpenedSince());
  }

  @PUT
  @Path("/door")
  public DoorStatusResponse updateIsDoorOpened(DoorStatusRequest request) {
    doorManager.updateIsDoorOpened(request.isDoorOpened());
    return isDoorOpened();
  }
}
