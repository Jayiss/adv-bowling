package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity, LinkedList<BowlingTurn> {
    private BowlingTurnImpl previousItem, nextItem;
    private TurnKey turnKey;
    private Integer maxPin, firstPin, secondPin;


    //constructors
    //TODO
    BowlingTurnImpl(BowlingTurnImpl previousItem, Integer firstPin, Integer secondPin, Integer maxPin) {
        this.previousItem = previousItem;
        this.firstPin = firstPin;
        this.secondPin = secondPin;
        this.maxPin = maxPin;
    }


    //inherited methods
    @Override
    public Boolean isStrike() {
        return null == this.secondPin && this.firstPin.equals(maxPin);
    }

    @Override
    public Boolean isSpare() {
        return this.firstPin != null && this.secondPin != null && this.firstPin + this.secondPin == maxPin;
    }

    @Override
    public Boolean isMiss() {
        return this.firstPin != null && this.secondPin != null && this.firstPin + this.secondPin < maxPin;
    }

    @Override
    public Integer getFirstPin() {
        return this.firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return this.secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin = pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin = pin;
    }

    //TODO
    @Override
    public void addPins(Integer... pins) {
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return this;
    }

    @Override
    public TurnKey getId() {
        return this.turnKey;
    }

    @Override
    public void setId(TurnKey id) {
        this.turnKey = id;
    }

    @Override
    public BowlingTurn getNextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.nextItem = (BowlingTurnImpl) item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return previousItem;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this;
    }

    @Override
    public Boolean isFinished() {
        if (!isValid())
            return false;
        else
            return isMiss() || isSpare() || isStrike();
    }

    @Override
    public Integer getScore() {
        int currentScore = 0;
        if (isMiss())//miss
            currentScore = calcMissTurnScore();
        else if (isSpare())//spare
            currentScore = calcSpareTurnScore();
        else if (isStrike())//strike
            currentScore = calcStrikeTurnScore();
        else//unfinished
            currentScore += this.firstPin;
        return currentScore;
    }

    private Integer calcStrikeTurnScore() {
        int bonus = 0, bonusCount = 0;
        BowlingTurnImpl bonusTurn = (BowlingTurnImpl) this.getNextItem();
        for (int i = 0; i < 2 && bonusTurn != null; i++) {
            if (bonusCount < 2 && bonusTurn.getFirstPin() != null) {
                bonus += bonusTurn.getFirstPin();
                bonusCount++;
            }
            if (bonusCount < 2 && bonusTurn.getSecondPin() != null) {
                bonus += bonusTurn.getSecondPin();
                bonusCount++;
            }
            bonusTurn = (BowlingTurnImpl) bonusTurn.getNextItem();
        }
        return this.maxPin + bonus;
    }

    private Integer calcSpareTurnScore() {
        Integer bonus = 0;
        BowlingTurnImpl bonusTurn = (BowlingTurnImpl) this.getNextItem();
        if (bonusTurn.getFirstPin() != null) {
            bonus += bonusTurn.getFirstPin();
        }
        return this.maxPin + bonus;
    }

    private Integer calcMissTurnScore() {
        return this.firstPin + this.secondPin;
    }

    @Override
    public Boolean isValid() {
        //each turn should contain one or two pin records
        if (this.firstPin == null)
            return false;
        else if (this.firstPin > this.maxPin || this.firstPin < 0)
            return false;
        else if (this.secondPin == null)
            return true;
        else if (this.secondPin > this.maxPin || this.secondPin < 0)
            return false;
        else
            return this.firstPin + this.secondPin <= this.maxPin;
    }
}
