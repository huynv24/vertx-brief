package com.viettel.platform.rest;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.ext.web.RoutingContext;
import okhttp3.*;

import java.io.IOException;

import static com.viettel.platform.util.ResponseProvider.makeErrorResponse;
import static com.viettel.platform.util.ResponseProvider.makeSuccessResponse;

public class RestFullApiService {
  public static void callApi(RoutingContext routingContext) {
    Future response = RestFullApiService.getBodyHtml();
    response.onSuccess(o -> makeSuccessResponse(200, routingContext.response(), o.toString()));
    response.onFailure(throwable -> makeErrorResponse(401, throwable.toString(), routingContext.response()));
  }

  public static Future getBodyHtml() {
    Promise promise = Promise.promise();
    OkHttpClient client = new OkHttpClient.Builder()
      .build();
    Request request = new Request.Builder()
      .url("https://dantri.com.vn/")
      .build();
    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      public void onResponse(Call call, Response response) throws IOException {
        System.out.println(response.body().string());
        promise.complete("complete");
      }

      public void onFailure(Call call, IOException e) {
        promise.fail(e.getMessage());
      }
    });

    return promise.future();

  }
}
