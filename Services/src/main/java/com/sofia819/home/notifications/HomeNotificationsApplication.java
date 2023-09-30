package com.sofia819.home.notifications;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;

public class HomeNotificationsApplication extends Application<HomeNotificationsConfiguration> {

  public static void main(String[] args) throws Exception {
    new HomeNotificationsApplication().run(args);
  }

  @Override
  public String getName() {
    return "home-notifications";
  }

  @Override
  public void initialize(Bootstrap<HomeNotificationsConfiguration> bootstrap) {
    // nothing to do yet
  }

  @Override
  public void run(HomeNotificationsConfiguration configuration, Environment environment) {
    ManagerComponent managerComponent = DaggerManagerComponent.create();

    environment.jersey().register(new AlertResource(managerComponent.alertManager()));
    environment.jersey().register(new ArduinoResource());
    environment.jersey().register(
        new StatusResource(managerComponent.alertManager(), managerComponent.doorMAnager()));
    environment.healthChecks().register("health-check", new HealthCheckResource());
  }
}
