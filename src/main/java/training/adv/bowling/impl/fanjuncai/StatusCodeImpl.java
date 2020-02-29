package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.StatusCode;

public enum StatusCodeImpl implements StatusCode {
    OUTOFRANGE("failure","The game is finished."),
    FINISHED("success","The current turn is finished."),
    NEGATIVE("failure","There are negative pins."),
    BIG("failure","There is pin which is bigger than MaxPin."),
    SUM("failure","The sum of pins in same turn is bigger than MaxPin."),
    SUCCESS("success","Adding scores success.")
    ;

    private final String code;
    private final String message;

    StatusCodeImpl(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }


}
