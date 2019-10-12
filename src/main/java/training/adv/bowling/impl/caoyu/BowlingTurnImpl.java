package training.adv.bowling.impl.caoyu;

import com.sun.org.apache.xpath.internal.operations.Bool;
import training.adv.bowling.api.*;

import java.util.ArrayList;
import java.util.List;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity, LinkedList<BowlingTurnImpl> {
    private BowlingTurnImpl previousItem, nextItem;
    private TurnKey turnKey;
    private Integer maxTurn, maxPin, turnCount;
    private Integer firstPin, secondPin;


    //constructors
    BowlingTurnImpl(Integer gameId, BowlingTurnImpl previousItem, Integer firstPin, Integer secondPin, Integer maxTurn,
                    Integer maxPin) {
        this.previousItem = previousItem;
        this.firstPin = firstPin;
        this.secondPin = secondPin;
        this.maxTurn = maxTurn;
        this.maxPin = maxPin;
        if (null != this.previousItem && null != this.previousItem.turnCount) {
            this.turnCount = this.previousItem.turnCount + 1;
        } else {
            this.turnCount = 1;
        }
        this.turnKey = new TurnKeyImpl(gameId);
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
    public StatusCode addPins(Integer... pins) {
        return null;
    }

    Boolean isGameFinished() {
        if (null == this.getNextItem()) {
            if (this.turnCount < getMaxTurn()) {
                return false;
            } else if (this.turnCount == getMaxTurn()) {//last turn miss, current turn is last turn
                return this.isMiss();
            } else if (this.turnCount == getMaxTurn() + 1) {//last turn spare or strike, current turn is bonus turn one
                BowlingTurnImpl lastTurn = this.getPreviousItem();
                boolean strikeFinished = lastTurn.isStrike() &&
                        this.getFirstPin() != null && this.getSecondPin() != null;
                boolean spareFinished = lastTurn.isSpare() &&
                        this.getFirstPin() != null && this.getSecondPin() == null;
                return strikeFinished || spareFinished;
            } else if (this.turnCount == getMaxTurn() + 2) {//last turn strike, current turn is bonus turn two
                BowlingTurnImpl lastTurn = this.getPreviousItem().getPreviousItem();
                BowlingTurnImpl bonusTurnOne = this.getPreviousItem();
                return lastTurn.isStrike() && null != bonusTurnOne.getFirstPin() && null == bonusTurnOne.getSecondPin() && null != this.getFirstPin() && null == this.getSecondPin();
            } else//game not valid
                return null;
        } else {
            return this.nextItem.isGameFinished();
        }
    }

    private int getMaxTurn() {
        return this.maxTurn;
    }


    @Override

    public LinkedList<BowlingTurnImpl> getAsLinkedNode() {
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
    public BowlingTurnImpl getNextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(BowlingTurnImpl item) {
        this.nextItem = item;
    }

    @Override
    public BowlingTurnImpl getPreviousItem() {
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
        BowlingTurnImpl bonusTurn = this.getNextItem();
        for (int i = 0; i < 2 && bonusTurn != null; i++) {
            if (bonusCount < 2 && bonusTurn.getFirstPin() != null) {
                bonus += bonusTurn.getFirstPin();
                bonusCount++;
            }
            if (bonusCount < 2 && bonusTurn.getSecondPin() != null) {
                bonus += bonusTurn.getSecondPin();
                bonusCount++;
            }
            bonusTurn = bonusTurn.getNextItem();
        }
        return this.maxPin + bonus;
    }

    private Integer calcSpareTurnScore() {
        Integer bonus = 0;
        BowlingTurnImpl bonusTurn = this.getNextItem();
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
