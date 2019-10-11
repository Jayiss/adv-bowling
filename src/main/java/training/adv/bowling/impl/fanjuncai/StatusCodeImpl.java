package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    NEGATIVE(),
    SUM()

    ;


    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return this.toString();
    }


}
