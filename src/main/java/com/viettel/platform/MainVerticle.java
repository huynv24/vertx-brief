package com.viettel.platform;

import com.viettel.platform.rest.RestFullApiService;
import com.viettel.platform.rest.UploadService;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.CorsHandler;

import java.util.HashSet;

import static com.viettel.platform.constant.Path.API_CALL;
import static com.viettel.platform.constant.Path.API_UPLOAD;

public class MainVerticle extends AbstractVerticle {

  public static void main(String[] args) {
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(MainVerticle.class.getName(),
      new DeploymentOptions()
        .setInstances(1)
        .setWorker(true)
        .setWorkerPoolSize(20));
  }

  @Override
  public void start(Promise<Void> startPromise) {
    Router router = Router.router(vertx);
    router.post(API_UPLOAD).handler(UploadService::LocalUpload);
    router.get(API_CALL).handler(routingContext -> RestFullApiService.callApi(routingContext));
    //Add for cross domain ajax
    router.route().handler(CorsHandler.create("*")
      .allowedMethods(new HashSet<HttpMethod>() {{
        add(HttpMethod.POST);
        add(HttpMethod.GET);
        add(HttpMethod.PUT);
        add(HttpMethod.DELETE);
      }})
      .allowedHeader("Access-Control-Request-Method")
      .allowedHeader("Access-Control-Allow-Origin")
      .allowedHeader("Access-Control-Allow-Credentials")
      .allowedHeader("Content-Type")
      .allowedHeader("Authorization")
    );


    vertx.createHttpServer().requestHandler(router).listen(8888, http -> {
      if (http.succeeded()) {
        startPromise.complete();
        System.out.println("HTTP server started on port 8888");
      } else {
        startPromise.fail(http.cause());
      }
    });
  }
}
