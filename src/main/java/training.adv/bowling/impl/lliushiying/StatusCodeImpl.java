package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.StatusCode;

public enum  StatusCodeImpl implements StatusCode {
    SUCCESS("101","SUCCESS"),
    FAIL("102","Fail"),
    FINISH("103","FINISH");

    private String code;
    private String message;
    private StatusCodeImpl(String code,String message){
        this.code=code;
        this.message=message;
    }
    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
