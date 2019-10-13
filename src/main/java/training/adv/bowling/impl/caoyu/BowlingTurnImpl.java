package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;

import java.util.ArrayList;
import java.util.Arrays;
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

    @Override
    public StatusCode addPins(Integer... pins) {
//        if (null == pins && 0 == pins.length) {//empty pins
//            return BowlingAddScoresStatusCode.valueOf("SUCCESSFUL");
//        }
        assert null != pins && 0 != pins.length;
        if (this.isGameFinished()) {//pins not empty, but game already finished
            return BowlingAddScoresStatusCode.valueOf("TOO_MANY_PINS");
        } else if (this.isFinished() && null != this.nextItem) {
            return this.getNextItem().addPins(pins);
        } else if (this.isFinished() && null == this.nextItem) {
            this.nextItem = new BowlingTurnImpl(this.turnKey.getForeignId(), this, null, null, this.maxTurn,
                    this.maxPin);
            return this.getNextItem().addPins(pins);
        }

        /*if this turn not finished, get the pins to be added to this turn
         * pins to add is stored temporarily*/
        List<Integer> pinsLeft = new java.util.LinkedList<>(Arrays.asList(pins));
        boolean firstPinAdded = false, secondPinAdded = false;
        if (null == this.firstPin && null == this.secondPin) {//two pins empty
            this.firstPin = pinsLeft.get(0);
            pinsLeft.remove(0);
            firstPinAdded = true;
            if (!this.isGameFinished() && !this.isFinished() && 0 < pinsLeft.size()) {
                this.secondPin = pinsLeft.get(0);
                pinsLeft.remove(0);
                secondPinAdded = true;
            }
        } else if (null != this.firstPin && null == this.secondPin) {//second pin empty
            this.secondPin = pinsLeft.get(0);
            pinsLeft.remove(0);
            secondPinAdded = true;
        }


        //check after adding pins
        if (!this.isValid()) {
            //roll back
            if (firstPinAdded)
                this.firstPin = null;
            if (secondPinAdded)
                this.secondPin = null;
            return BowlingAddScoresStatusCode.valueOf("INVALID_PIN");
        } else if (0 == pinsLeft.size()) {
            return BowlingAddScoresStatusCode.valueOf("SUCCESSFUL");
        } else if (this.isGameFinished() && 0 < pinsLeft.size()) {
            //roll back
            if (firstPinAdded)
                this.firstPin = null;
            if (secondPinAdded)
                this.secondPin = null;
            return BowlingAddScoresStatusCode.valueOf("TOO_MANY_PINS");
        } else if (this.isGameFinished() && 0 == pinsLeft.size()) {
            return BowlingAddScoresStatusCode.valueOf("SUCCESSFUL");
        }

        //pins left add to next turn
        assert null == this.nextItem;
        BowlingTurnImpl nextTurnToAdd = new BowlingTurnImpl(this.turnKey.getForeignId(), this, null, null, this.maxTurn,
                this.maxPin);
        StatusCode nextTurnAddPinsStatusCode = nextTurnToAdd.addPins(pinsLeft.toArray(new Integer[0]));
        if (BowlingAddScoresStatusCode.valueOf("SUCCESSFUL").equals(nextTurnAddPinsStatusCode)) {
            this.nextItem = nextTurnToAdd;
            return BowlingAddScoresStatusCode.valueOf("SUCCESSFUL");
        } else {
            //roll back
            if (firstPinAdded)
                this.firstPin = null;
            if (secondPinAdded)
                this.secondPin = null;
            return nextTurnAddPinsStatusCode;
        }
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
        if (null == this.firstPin && null == this.secondPin)
            return 0;
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
        if (null != bonusTurn && null != bonusTurn.getFirstPin()) {
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
