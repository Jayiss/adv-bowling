package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImpl implements StatusCode {
    SUCCESS("200","add pins successfully!"),
    INVALID_PIN("400","the pins contain invalid value!");

    private String code;
    private String message;
    private StatusCodeImpl(String code, String message){
        this.code = code;
        this.message = message;
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
