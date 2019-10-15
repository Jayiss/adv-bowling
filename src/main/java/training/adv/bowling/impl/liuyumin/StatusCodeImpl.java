package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    SUCCESS("1", "SUCCESS"), FAIL("-1", "FAIL");
    private String statusCode;
    private String message;

    StatusCodeImpl(String statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.statusCode;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
