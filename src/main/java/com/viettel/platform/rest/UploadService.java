package com.viettel.platform.rest;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class UploadService {


  public static void LocalUpload(RoutingContext routingContext) {

    HttpServerResponse response = routingContext.response();
    response
      .putHeader("content-type", "text/html")
      .end("<h1>Hello from my first Vert.x 3 application</h1>");
  }
}
