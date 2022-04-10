package com.viettel.platform.util;

import com.google.gson.JsonObject;
import io.vertx.core.http.HttpServerResponse;

public class ResponseProvider {

    private static String SUCCESS_MESSAGES = null;
    private static String INTERNAL_SERVER_ERROR_MESSAGES = null;
    private static String UNKNOWN_PATH_MESSAGES = null;
    private static String PARAMETER_ERROR_MESSAGES = null;
    private static String CANT_NOT_CREATE_MESSAGES = null;
    private static String ERROR_ES_MESSAGES = null;
    private static String ERROR_INPUT_WRONG_MESSAGES = null;

    static {

        JsonObject jsonSuccess = new JsonObject();
        jsonSuccess.addProperty("messages", "success");
        SUCCESS_MESSAGES = jsonSuccess.toString();


        JsonObject jsonInternalServerError = new JsonObject();
        jsonInternalServerError.addProperty("status", 500);
        jsonInternalServerError.addProperty("messages", "Internal server error");
        INTERNAL_SERVER_ERROR_MESSAGES = jsonInternalServerError.toString();


        JsonObject jsonParamterError = new JsonObject();
        jsonParamterError.addProperty("messages", "Parameter error");
        PARAMETER_ERROR_MESSAGES = jsonParamterError.toString();

        JsonObject jsonCreateMessagesError = new JsonObject();
        jsonParamterError.addProperty("messages", "Cannot create messages");
        CANT_NOT_CREATE_MESSAGES = jsonCreateMessagesError.toString();

        JsonObject jsonUnkownPathMess = new JsonObject();
        jsonUnkownPathMess.addProperty("messages", "Unknown path messages");
        UNKNOWN_PATH_MESSAGES = jsonUnkownPathMess.toString();

        JsonObject jsonErrorESMess = new JsonObject();
        jsonErrorESMess.addProperty("messages", "Elasticsearch TransportClient hasn't initialized before");
        ERROR_ES_MESSAGES = jsonErrorESMess.toString();

        JsonObject jsonInputWrongMess = new JsonObject();
        jsonInputWrongMess.addProperty("messages", "Input wrong page and size_page must larger than 0");
        ERROR_INPUT_WRONG_MESSAGES = jsonInputWrongMess.toString();


    }


    private static void addDefaultHeader(HttpServerResponse response) {
        response.putHeader("content-type", "application/json");
        response.putHeader("Access-Control-Allow-Origin", "*");
    }


    public static void makeBadRequestResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.setStatusCode(400);
        response.end();
    }

    public static void makeErrorResponse(int statusCode, String message, HttpServerResponse response) {
        addDefaultHeader(response);
        response.setStatusCode(statusCode);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", statusCode);
        jsonObject.addProperty("message", message);
        response.end(jsonObject.toString());
    }

    public static void makeSuccessResponse(int statusCode, HttpServerResponse response, String message) {
        addDefaultHeader(response);
        response.setStatusCode(statusCode);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("status", statusCode);
        jsonObject.addProperty("message", message);
        response.end(jsonObject.toString());
    }

    public static void makeInternalServerErrorResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.end(INTERNAL_SERVER_ERROR_MESSAGES);
    }

    public static void makeUnknownPathResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.end(UNKNOWN_PATH_MESSAGES);
    }

    public static void makeParameterErrorResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.setStatusCode(400);
        response.end(PARAMETER_ERROR_MESSAGES);
    }

    public static void makeErrorDBResponse(HttpServerResponse response) {
        makeErrorResponse(500, "Lỗi database", response);
    }

    public static void makeErrorESResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.end(ERROR_ES_MESSAGES);
    }

    public static void makeSuccessResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.setStatusCode(200);
        response.end(SUCCESS_MESSAGES);
    }

    public static void makeSuccessResponse(HttpServerResponse response, String value) {
        addDefaultHeader(response);
        response.setStatusCode(200);
        response.end(value);
    }

    public static void makeSuccessMessageResponse(HttpServerResponse response, String message) {
        addDefaultHeader(response);
        response.setStatusCode(200);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", message);
        response.end(jsonObject.toString());
    }

    public static void makeCannotCreateResponse(HttpServerResponse response) {
        addDefaultHeader(response);
        response.setStatusCode(200);
        response.end(CANT_NOT_CREATE_MESSAGES);
    }

    public static void makeInputWrong(HttpServerResponse response) {
        addDefaultHeader(response);
        response.end(ERROR_INPUT_WRONG_MESSAGES);
    }

    public static void makeFailure(HttpServerResponse response, String messages) {
        addDefaultHeader(response);
        JsonObject jsonInputWrongMess = new JsonObject();
        jsonInputWrongMess.addProperty("messages", messages);
        response.end(jsonInputWrongMess.toString());
    }
}
