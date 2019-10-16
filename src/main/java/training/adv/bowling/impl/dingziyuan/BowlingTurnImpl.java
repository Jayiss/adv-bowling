package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;

import java.util.List;

public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn> {
    //TODO should have one or two parameters
    private BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
    private BowlingTurn previous = null;
    private BowlingTurn next = null;
    private final Integer MAX_TURN, MAX_PIN;

    public BowlingTurnImpl(Integer MAX_TURN, Integer MAX_PIN) {
        this.MAX_TURN = MAX_TURN;
        this.MAX_PIN = MAX_PIN;
    }

    public BowlingTurnImpl(Integer MAX_TURN, Integer MAX_PIN, Integer initPin, BowlingTurn previous) {
        this.MAX_TURN = MAX_TURN;
        this.MAX_PIN = MAX_PIN;
        this.bowlingTurnEntity.setFirstPin(initPin);
        this.bowlingTurnEntity.setSecondPin(initPin);
        this.previous = previous;
    }


    @Override
    public Boolean isStrike() {
        return getSecondPin() == null &&
                getFirstPin() == MAX_PIN;
    }

    @Override
    public Boolean isSpare() {
        return getSecondPin() != null &&
                getFirstPin() + getSecondPin() == MAX_PIN;
    }

    @Override
    public Boolean isMiss() {
        return getSecondPin() != null &&
                getFirstPin() + getSecondPin() < MAX_PIN;
    }

    @Override
    public Integer getFirstPin() {
        return bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return bowlingTurnEntity.getSecondPin();
    }

    @Override
    public void addPins(Integer... pins) {
        if(pins.length==1)
        {
            if(getFirstPin()==null)
                bowlingTurnEntity.setFirstPin(pins[0]);
            else
                bowlingTurnEntity.setSecondPin(pins[0]);
        }

        if (pins.length == 2) {
            bowlingTurnEntity.setFirstPin(pins[0]);
            bowlingTurnEntity.setSecondPin(pins[1]);
        }
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return this;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return bowlingTurnEntity;
    }

    @Override
    public Boolean isFinished() {
        return getSecondPin() != null || getFirstPin() == MAX_PIN;
    }


    private Integer getTurnNum() {
        LinkedList<BowlingTurn> current = this.getAsLinkedNode();
        Integer num = 0;
        while (current.getPreviousItem() != null) {
            current = current.getPreviousItem().getAsLinkedNode();
            num++;
        }
//        previous = this.getAsLinkedNode().getPreviousItem();
        return num;
    }

    @Override
    public Integer getScore() {
        Integer i = getTurnNum();
        if (i >= 10)
            return 0;
        Integer score = 0;
        //EMPTY
        if (getFirstPin() == null && getSecondPin() == null)
            return 0;
        //SPARE
        if (isSpare()) {
            score += (MAX_PIN + fetchFromNextPins(1));
        }
        //STRIKE
        else if (isStrike()) {
            score += (MAX_PIN + fetchFromNextPins(2));
        } else {
            //MISS
            if (getSecondPin() != null)
                score += (getFirstPin() + getSecondPin());
                //UNFINISHED
            else
                score += (getFirstPin());
        }
        return score;
    }

    private Integer fetchFromNextPins(Integer pinsNum) {
        BowlingTurn turn = this;
        BowlingTurn nextOne, nextSecond;
        if (turn.getAsLinkedNode().getNextItem() == null) {
            nextOne = new BowlingTurnImpl(MAX_TURN, MAX_PIN, 0, this);
        } else
            nextOne = turn.getAsLinkedNode().getNextItem();

        if (turn.getAsLinkedNode().getNextItem() == null) {
            nextSecond = new BowlingTurnImpl(MAX_TURN, MAX_PIN, 0, this);
        } else if (turn.getAsLinkedNode().getNextItem().getAsLinkedNode().getNextItem() == null)
            nextSecond = new BowlingTurnImpl(MAX_TURN, MAX_PIN, 0, this);
        else
            nextSecond = turn.getAsLinkedNode().getNextItem().getAsLinkedNode().getNextItem();

        if (pinsNum == 1) {
            return nextOne.getFirstPin();
        } else if (pinsNum == 2) {
            if (nextOne.getSecondPin() == null) {
                return nextOne.getFirstPin() + nextSecond.getFirstPin();
            }
            if (nextOne.getSecondPin() != null) {
                return nextOne.getFirstPin() + nextOne.getSecondPin();
            }
        }
        return -1;
    }

    @Override
    public Boolean isValid() {
        Integer pin1 = bowlingTurnEntity.getFirstPin();
        Integer pin2 = bowlingTurnEntity.getSecondPin();
        Boolean isSumValid = (pin1 + (pin2 == null ? 0 : pin2) <= MAX_PIN);
        return isSumValid && isPinValid(pin1) && isPinValid(pin2);
    }

    private Boolean isPinValid(Integer pin) {
        if (pin == null)
            return true;
        else {
            return pin >= 0 && pin <= MAX_PIN;
        }
    }

    @Override
    public BowlingTurn getNextItem() {
        return next;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.next = item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return previous;
    }
}
