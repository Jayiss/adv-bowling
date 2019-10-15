package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.*;

import java.util.Arrays;

public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn> {
    private BowlingTurnEntity bowlingTurnEntity;
    private int MaxPin;
    private int MaxTurn;
    private BowlingTurn next;
    private BowlingTurn pre;
    private StatusCode statusCode;
    public BowlingTurnImpl(int maxPin,int maxTurn,BowlingTurn pre){
        this.bowlingTurnEntity = new BowlingTurnEntityImpl();
        this.MaxPin = maxPin;
        this.MaxTurn =  maxTurn;
        this.pre = pre;
        if(pre!=null){
            pre.getAsLinkedNode().setNextItem(this);
        }
    }
    @Override
    public Boolean isStrike() {
        return getFirstPin()==MaxPin;
    }

    @Override
    public Boolean isSpare() {
        if(!isFinished()){
            return false;
        }
        return getFirstPin()+getSecondPin()==MaxPin && getFirstPin()!=MaxPin;
    }

    @Override
    public Boolean isMiss() {
        if(!isFinished()){
            return false;
        }
        return getFirstPin()+getSecondPin()!=MaxPin;
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
        if(bowlingTurnEntity.getFirstPin()!=null){
            bowlingTurnEntity.setSecondPin(pins[0]);
        }else{
            if(pins[0].equals(MaxPin)){
                bowlingTurnEntity.setFirstPin(MaxPin);
                bowlingTurnEntity.setSecondPin(0);
            }else {
                bowlingTurnEntity.setFirstPin(pins[0]);
            }
        }
        isValid();
        return statusCode;
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
        return bowlingTurnEntity.getFirstPin()!=null&&bowlingTurnEntity.getSecondPin()!=null;
    }

    @Override
    public Integer getScore() {
        int index = 0;
        BowlingTurn cursor= this;
        while (cursor!=null){
            cursor = ((BowlingTurnImpl) cursor).pre;
            index++;
        }
        if(index-1>MaxTurn){
            return 0;
        }
        if (isStrike()){
            if(next!=null&&next.isStrike()){
                return MaxPin + MaxPin +(next.getAsLinkedNode().getNextItem()==null?0:next.getAsLinkedNode().getNextItem().getFirstPin());
            }else if(next!=null&&(next.isSpare()||next.isMiss())){
                return MaxPin + next.getFirstPin() + next.getSecondPin();
            }else {
                return MaxPin + (next==null?0:next.getFirstPin());
            }
        }
        if(isSpare()){
            return MaxPin + (next==null?0:next.getFirstPin());
        }
        if(isMiss()){
            return bowlingTurnEntity.getFirstPin()+(bowlingTurnEntity.getSecondPin());
        }
        return bowlingTurnEntity.getFirstPin();
    }

    @Override
    public Boolean isValid() {
        if(isFinished()){
            if(bowlingTurnEntity.getFirstPin()<0||bowlingTurnEntity.getSecondPin()<0){
                statusCode = StatusCodeImp.FAILED;
                return false;
            }
            if(bowlingTurnEntity.getFirstPin()+bowlingTurnEntity.getSecondPin()>MaxPin){
                statusCode = StatusCodeImp.FAILED;
                return false;
            }
        }else {
            if(bowlingTurnEntity.getFirstPin()<0){
                statusCode = StatusCodeImp.FAILED;
                return false;
            }
            if(bowlingTurnEntity.getFirstPin()>MaxPin){
                statusCode = StatusCodeImp.FAILED;
                return false;
            }
        }
        statusCode = StatusCodeImp.SUCCESS;
        return true;
    }

    @Override
    public BowlingTurn getNextItem() {
        return this.next;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.next = item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return this.pre;
    }


}