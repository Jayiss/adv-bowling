package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.StatusCode;

public enum BowlingAddScoresStatusCode implements StatusCode {
    EMPTY_PIN("PINS_EMPTY"),
    TOO_MANY_PINS("TOO_MANY_PINS"),
    INVALID_PIN("INVALID_PIN"),
    PINS_NEEDED("PINS_NEEDED"),
    GAME_ALREADY_FINISHED("GAME_ALREADY_FINISHED"),
    SUCCESSFUL("SUCCESSFUL");

    private String message;

    BowlingAddScoresStatusCode(String s) {
        this.message = s;
    }

    @Override
    public String getCode() {
        return this.message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
