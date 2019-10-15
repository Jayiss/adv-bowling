package training.adv.bowling.impl.group2;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn>, BowlingTurnEntity {
    private Integer firstPin;
    private Integer secondPin;
    private BowlingTurn nextItem;
    private BowlingTurn previousItem;
    private TurnKey bowlingTurnId;
    //TODO
    private Integer MAX_PIN = 10;

    public BowlingTurnImpl(Integer firstPin, Integer secondPin, BowlingTurn previousItem) {
        this.firstPin = firstPin;
        this.secondPin = secondPin;
        this.previousItem = previousItem;
    }
    public BowlingTurnImpl(Integer firstPin, Integer secondPin) {
        this.firstPin = firstPin;
        this.secondPin = secondPin;
    }


    @Override
    public Boolean isStrike() {
        try {
            return getFirstPin().equals(MAX_PIN);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isSpare() {
        try {
            return getFirstPin() + getSecondPin() == MAX_PIN && getSecondPin() != 0;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean isMiss() {
        try {
            return getFirstPin() + getSecondPin() < MAX_PIN;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Integer getFirstPin() {
        return this.firstPin;
    }

    @Override
    public Integer getSecondPin() {
        if (this.secondPin == null)
            return 0;
        return this.secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        if (firstPin == 0)
            this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        if (firstPin != 10)
            this.secondPin = pin;
    }

    @Override
    public StatusCode addPins(Integer... pins) {
        StatusCode statusCode;
        if (pins.length != 1) return null;
        if (isFinished()) {
            this.getAsLinkedNode().setNextItem(new BowlingTurnImpl(pins[0], 0, this));
            statusCode = StatusCodeImpl.SUCCESSADD;
        } else {
            if (getFirstPin() != 0) {
                setSecondPin(pins[0]);
                if (isValid()) {
                    statusCode = StatusCodeImpl.SUCCESSADD;
                } else {
                    setSecondPin(0);
                    statusCode = StatusCodeImpl.FAILEDADD;
                }
            } else {
                setFirstPin(pins[0]);
                statusCode = StatusCodeImpl.SUCCESSADD;
            }

        }
        return statusCode;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return this;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this;
    }

    @Override
    public Boolean isFinished() {
        return firstPin.equals(MAX_PIN) || secondPin != 0;
    }

    @Override
    public Integer getScore() {
        Integer result;
        if (isStrike()) { // 本轮打出strike
            result = MAX_PIN;
            if (this.getAsLinkedNode().getNextItem() != null) { //下轮已经开始
                if (this.getAsLinkedNode().getNextItem().isStrike()) {//下轮打出strike
                    result += MAX_PIN;
                    BowlingTurn nextTurn = this.getAsLinkedNode().getNextItem();
                    if (nextTurn.getAsLinkedNode().getNextItem() != null) {//下下轮已开始
                        result += nextTurn.getAsLinkedNode().getNextItem().getFirstPin();
                        //如果连续打出两轮strike，需要三轮才能算出第一个strike的分数
                    }
                } else {//下轮不是strike
                    result += this.getAsLinkedNode().getNextItem().getFirstPin() + this.getAsLinkedNode().getNextItem().getSecondPin();
                }
            }
        } else if (isSpare()) {
            result = MAX_PIN;
            if (this.getAsLinkedNode().getNextItem() != null) { //下轮已经开始
                result += this.getAsLinkedNode().getNextItem().getFirstPin();
            }
        } else {
            result = this.getFirstPin() + getSecondPin();
        }
        return result;
    }

    @Override
    public Boolean isValid() {
        return getFirstPin() + getSecondPin() <= MAX_PIN && getFirstPin() >= 0;
    }

    @Override
    public BowlingTurn getNextItem() {
        try {
            return nextItem;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.nextItem = item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return previousItem;
    }

    @Override
    public TurnKey getId() {
        //todo
        return bowlingTurnId;
    }

    @Override
    public void setId(TurnKey id) {
        //todo
        this.bowlingTurnId = id;
    }
}
