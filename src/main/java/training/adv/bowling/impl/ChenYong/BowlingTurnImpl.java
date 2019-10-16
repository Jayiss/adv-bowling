package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;

public class BowlingTurnImpl  implements BowlingTurn {
    public static Integer MAX_PINS=10;
    public static Integer MAX_TURN=10;
    private Integer first;
    private Integer second;
    private BowlingTurnEntity bowlingTurnEntity;
    @Override
    public Integer getFirstPin() {
        return first;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        BowlingTurnEntity bowlingTurnEntity1=new BowlingTurnEntityImpl(bowlingTurnEntity.getFirstPin(),bowlingTurnEntity.getSecondPin(),bowlingTurnEntity.getId());
        return bowlingTurnEntity1;
    }

    @Override
    public Integer getSecondPin() {
        return second;
    }

    @Override
    public Integer getScore() {
        Integer turnScore=getFirstPin();
        if(getSecondPin()!=null)
            return turnScore+getSecondPin();
        return turnScore;
    }

    @Override
    public Boolean isFinished() {
        if(getFirstPin()<10&&getSecondPin()==null)
            return false;
        return true;
    }

    @Override
    public Boolean isMiss() {
        if(!isFinished())
            return false;
        if(getSecondPin()+getSecondPin()<10)
            return true;
        return false;
    }

    @Override
    public Boolean isSpare() {
        if(!isFinished())
            return false;
        if(getFirstPin()<MAX_PINS&&getFirstPin()+getSecondPin()==MAX_PINS)
            return true;
        return false;
    }

    @Override
    public Boolean isStrike() {
        if(getFirstPin()==MAX_PINS)
            return true;
        return false;
    }

    @Override
    public Boolean isValid() {
        if(getFirstPin()<0&&getFirstPin()>10)
            return false;
        if(getSecondPin()<0&&getSecondPin()>10)
            return false;
        if(getFirstPin()+getSecondPin()>10)
            return false;
        return true;
    }

    @Override
    public void addPins(Integer... pins) {

    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return null;
    }
}
