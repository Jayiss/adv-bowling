package training.adv.bowling.impl.why;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImpl implements StatusCode {
    SUCCESS("1","SUCCESS"),
    FINISHED("2","FULLED"),
    FAILED("3","FAILED")
    ;


    private String code;
    private String message;
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
