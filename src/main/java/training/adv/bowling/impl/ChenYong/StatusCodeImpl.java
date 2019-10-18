package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImpl implements StatusCode {
    VALID("1","valid"),
    INVALID("2","invalid"),
    FINISH("3","finish")
    ;
    private String code;
    private String message;
    StatusCodeImpl(String code,String message)
    {
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
