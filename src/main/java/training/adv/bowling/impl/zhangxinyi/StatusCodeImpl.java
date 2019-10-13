package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    INVALID("0", "Invalid turn"), TOOMUCH("1", "Too much turn");
    private String code;
    private String message;

    StatusCodeImpl(String code, String message) {
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
