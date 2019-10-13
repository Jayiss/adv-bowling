package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    SUCCEED,FAILED;


    @Override
    public String getCode() {
        return this.toString();
    }

    @Override
    public String getMessage() {
        return this.getCode();
    }
}

