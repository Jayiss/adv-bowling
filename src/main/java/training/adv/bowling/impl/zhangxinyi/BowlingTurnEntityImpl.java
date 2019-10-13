package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
    private Integer maxPin;
    private Integer maxTurn;
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey key;
    private BowlingTurnImpl nextItem;
    private BowlingTurnImpl previousItem;
    public static Integer uniqueId = 2000;

    public BowlingTurnEntityImpl() {
    }

    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        secondPin = pin;
    }

    @Override
    public TurnKey getId() {
        return key;
    }

    @Override
    public void setId(TurnKey id) {
        key = id;
    }

    public Integer getMaxPin() {
        return maxPin;
    }

    public void setMaxPin(Integer maxPin) {
        this.maxPin = maxPin;
    }

    public BowlingTurnImpl getNextItem() {
        return nextItem;
    }

    public void setNextItem(BowlingTurnImpl nextItem) {
        this.nextItem = nextItem;
    }

    public BowlingTurnImpl getPreviousItem() {
        return previousItem;
    }

    public void setPreviousItem(BowlingTurnImpl previousItem) {
        this.previousItem = previousItem;
    }

    public Integer getMaxTurn() {
        return maxTurn;
    }

    public void setMaxTurn(Integer maxTurn) {
        this.maxTurn = maxTurn;
    }
}
