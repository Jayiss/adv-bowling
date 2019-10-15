package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    ADD_SUCCESS("add success","0"), ADD_FAILED("add error", "1");

    private String message;
    private String code;

    private StatusCodeImpl(String message, String code) {
        this.message=message;
        this.code=code;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
