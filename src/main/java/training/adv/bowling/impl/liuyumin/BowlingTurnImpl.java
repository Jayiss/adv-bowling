package training.adv.bowling.impl.liuyumin;

import training.adv.bowling.api.*;

public class BowlingTurnImpl implements BowlingTurn {
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey turnKey;
    private int maxPin = 10;
    private BowlingTurnImpl next;
    private BowlingTurnImpl perv;

    @Override
    public Boolean isStrike() {
        if(isFinished()){
            if(firstPin == maxPin){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isSpare() {
        if(isFinished()){
            if(firstPin + secondPin == maxPin){
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isMiss() {
        if(isFinished()){
            if(firstPin + secondPin < maxPin){
                return true;
            }
        }
        return false;
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
    public StatusCode addPins(Integer... pins) {
        return null;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return null;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return null;
    }

    @Override
    public Boolean isFinished() {
        if(isValid()){
            if(firstPin != null && secondPin != null){
                return true;
            } else if(firstPin == maxPin){
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer getScore() {
        return null;
    }

    @Override
    public Boolean isValid() {
        if(firstPin != null){
            if(firstPin < 0 || firstPin > maxPin){
                return false;
            }
        } else if(secondPin != null) {
            if(secondPin < 0 || secondPin > maxPin){
                return false;
            }
        } else if(firstPin != null && secondPin != null) {
            if(firstPin + secondPin > maxPin){
                return false;
            }
        }
        return true;
    }

}
