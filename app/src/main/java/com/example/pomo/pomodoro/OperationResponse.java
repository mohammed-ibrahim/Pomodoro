package com.example.pomo.pomodoro;

/**
 * Created by ibrahim on 01/08/17.
 */

public class OperationResponse {

    private ResponseStatus status;

    private String message;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return this.status == ResponseStatus.SUCCESS ? true : false;
    }

    public static OperationResponse success(String message) {
        OperationResponse response = new OperationResponse();
        response.message = message;
        response.status = ResponseStatus.SUCCESS;

        return response;
    }

    public static OperationResponse failure(String message) {
        OperationResponse response = new OperationResponse();
        response.message = message;
        response.status = ResponseStatus.FAILURE;

        return response;
    }
}
