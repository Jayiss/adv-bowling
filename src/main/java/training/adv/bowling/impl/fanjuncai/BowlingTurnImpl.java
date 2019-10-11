package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

public class BowlingTurnImpl implements BowlingTurn {

    private BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();

    public void setEntity(BowlingTurnEntity bowlingTurnEntity) {
        this.bowlingTurnEntity = bowlingTurnEntity;
    }

    @Override
    public Boolean isStrike() {
        if(bowlingTurnEntity.getFirstPin()!=null&&bowlingTurnEntity.getFirstPin()==10)
            return true;
        else
            return false;
    }

    @Override
    public Boolean isSpare() {
        if(bowlingTurnEntity.getFirstPin()!=null
                &&bowlingTurnEntity.getSecondPin()!=null
                &&(bowlingTurnEntity.getSecondPin()+bowlingTurnEntity.getFirstPin()==10))
            return true;
        else
            return false;
    }

    @Override
    public Boolean isMiss() {
        if(bowlingTurnEntity.getFirstPin()!=null
                &&bowlingTurnEntity.getSecondPin()!=null
                &&(bowlingTurnEntity.getSecondPin()+bowlingTurnEntity.getFirstPin()<10))
            return true;
        else
            return false;
    }

    @Override
    public Integer getFirstPin() {
        return this.bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return this.bowlingTurnEntity.getSecondPin();
    }

    @Override
    public StatusCode addPins(Integer... pins) {
        return null;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode(){

        return null;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this.bowlingTurnEntity;
    }

    @Override
    public Boolean isFinished() {
        return null;
    }

    @Override
    public Integer getScore() {
        return null;
    }

    @Override
    public Boolean isValid() {
        return null;
    }

}
