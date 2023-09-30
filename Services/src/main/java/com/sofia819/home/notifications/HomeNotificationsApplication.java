package com.sofia819.home.notifications;

import io.dropwizard.core.Application;
import io.dropwizard.core.setup.Bootstrap;
import io.dropwizard.core.setup.Environment;
import jakarta.servlet.DispatcherType;
import jakarta.servlet.FilterRegistration;
import java.util.EnumSet;
import org.eclipse.jetty.servlets.CrossOriginFilter;

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
    
    environment.jersey().register(
        new StatusResource(managerComponent.alertManager(), managerComponent.doorMAnager()));
    environment.healthChecks().register("health-check", new HealthCheckResource());

    configureCors(environment);
  }

  private void configureCors(Environment environment) {
    final FilterRegistration.Dynamic cors =
        environment.servlets().addFilter("CORS", CrossOriginFilter.class);

    // Configure CORS parameters
    cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM,
        "X-Requested-With,Content-Type,Accept,Origin,Authorization");
    cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM,
        "OPTIONS,GET,PUT,POST,DELETE,HEAD");
    cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

    // Add URL mapping
    cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

  }
}
