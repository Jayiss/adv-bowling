package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    SUCCESS("1","SUCCESS"),
    FINISHED("2","FULLED"),
    FAILED("3","FAILED");

    private final String code;
    private final String message;
    StatusCodeImpl(String code,String message){
        this.code=code;
        this.message=message;
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
