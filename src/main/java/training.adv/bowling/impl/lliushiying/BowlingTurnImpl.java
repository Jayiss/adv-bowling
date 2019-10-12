package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

import java.util.Arrays;

public class BowlingTurnImpl implements BowlingTurn ,LinkedList<BowlingTurn> {
    private BowlingTurnEntity bowlingTurnEntity;
    private BowlingTurn nextTurn;
    private BowlingTurn previousTurn;

    public BowlingTurnImpl(BowlingTurnEntity bowlingTurnEntity,BowlingTurn previousTurn){
        this(bowlingTurnEntity,previousTurn,null);
    }
    public BowlingTurnImpl(BowlingTurnEntity bowlingTurnEntity,BowlingTurn previous,BowlingTurn next){
        this.previousTurn=previous;
        this.nextTurn=next;
        this.bowlingTurnEntity=bowlingTurnEntity;
    }

    public BowlingTurnImpl(BowlingTurnEntity bowlingTurnEntity){
        this(bowlingTurnEntity,null,null);
    }
    @Override
    public Boolean isStrike() {
        //TODO: getMaxPin()
        if (getFirstPin() == 10 && getSecondPin() == 0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isSpare() {
        //TODO: getMaxPin()
        if (getFirstPin() + getSecondPin() ==10 && getFirstPin() != 10) {
            return true;
        }
        return false;

    }

    @Override
    public Boolean isMiss() {
        //TODO: getMaxPin()
        if (getFirstPin() + getSecondPin() < 10) {
            return true;
        } else {
            return false;
        }
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
    public StatusCode addPins(Integer... pins) {
        StatusCode code;
        if(pins==null||pins.length==0){
            return null;
        }
        Integer pre=null;
        int count=0;
        BowlingTurn next=new BowlingTurnImpl(new BowlingTurnEntityImpl(),this,null);
        this.setNextItem(next);
        if(isFinished()){

        }else if(getFirstPin()==null){
            if(pins[0]==10){
                this.getEntity().setFirstPin(pins[0]);
                this.getEntity().setSecondPin(0);
                count++;
            }else{
                this.getEntity().setFirstPin(pins[0]);
                if(pins.length>1){
                    this.getEntity().setSecondPin(pins[1]);
                    count++;
                }else {
                    this.getEntity().setSecondPin(null);
                }
                count++;
            }
        }else{
            //secondpin为空
            pre=getFirstPin();
            this.getEntity().setSecondPin(pins[0]);
            count++;
        }

        if(isValid()){
            //TODO:截取算左边
            pins= Arrays.copyOfRange(pins,count,pins.length);
            code= addPins(pins);
        }else{
            this.getEntity().setFirstPin(pre);
            this.getEntity().setSecondPin(null);
            //return 失败的状态码
            return null;
        }
        //TODO:失败的状态码
        //TODO:如果第一个是firstTurn
        if(code==null){
            this.getAsLinkedNode().setNextItem(null);
            if(this.getAsLinkedNode().getPreviousItem()==null){
            }
        }
        return code;
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
        if (getSecondPin() == null) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public Integer getScore() {
        if(!isFinished()){
            return getFirstPin()==null?0:getFirstPin();
        }
        if(isMiss()){
            return getFirstPin()+getSecondPin();
        }
        if(isSpare()){
            BowlingTurn nextTurn=this.getNextItem();
            if(nextTurn==null||nextTurn.getFirstPin()==null){
                return getFirstPin()+getSecondPin();
            }else{
                return getFirstPin()+getSecondPin()+nextTurn.getFirstPin();
            }
        }
        if(isStrike()){
            BowlingTurn nextTurn=this.getNextItem();
            if(nextTurn==null||nextTurn.getFirstPin()==null){
                return 10;
            }
            BowlingTurn afterNextTurn=this.getNextItem();
            if(!nextTurn.isFinished()){
                return 10+nextTurn.getFirstPin();
            }
            if(nextTurn.isSpare()||nextTurn.isMiss()){
                return 10+nextTurn.getFirstPin()+nextTurn.getSecondPin();
            }
            if(nextTurn.isStrike()&&(afterNextTurn==null||afterNextTurn.getFirstPin()==null)){
                return 10*2;
            }else{
                return 10*2+afterNextTurn.getFirstPin();
            }
        }
        return 10;
    }

    @Override
    public Boolean isValid() {
        if (getFirstPin() < 0 || getFirstPin() > 10) {
            return false;
        }
        if (getSecondPin() == null) {
            return true;
        }
        if (getSecondPin() < 0 || getSecondPin() > 10) {
            return false;
        }
        if (getFirstPin() + getSecondPin() > 10) {
            return false;
        }
        return true;
    }


    //implement linkedlist
    @Override
    public BowlingTurn getNextItem() {
        return nextTurn;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.nextTurn=item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return previousTurn;
    }
}
