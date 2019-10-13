package training.adv.bowling.impl.ChaoyiFang;


import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn> {
    private BowlingTurnEntity bowlingTurnEntity;
    private int MAX_PIN;
    private int MAX_TURN;
    private BowlingTurn next;
    private BowlingTurn pre;
    private StatusCode statusCode;
    public BowlingTurnImpl(int maxPin,int maxTurn,BowlingTurn preBowlingTurn){
        this.bowlingTurnEntity = new BowlingTurnEntityImpl();
        this.MAX_PIN = maxPin;
        this.MAX_TURN =  maxTurn;
        pre = preBowlingTurn;
        if(preBowlingTurn!=null){
            preBowlingTurn.getAsLinkedNode().setNextItem(this);
        }
    }
    @Override
    public Boolean isStrike() {
        return getFirstPin()==MAX_PIN;
    }

    @Override
    public Boolean isSpare() {
        if(!isFinished()){
            return false;
        }
        return getFirstPin()+getSecondPin()==MAX_PIN && getFirstPin()!=MAX_PIN;
    }

    @Override
    public Boolean isMiss() {
        if(!isFinished()){
            return false;
        }
        return getFirstPin()+getSecondPin()!=MAX_PIN;
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
            if(pins[0].equals(MAX_PIN)){
               bowlingTurnEntity.setFirstPin(MAX_PIN);
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
        if(index-1>MAX_TURN){
            return 0;
        }
        if (isStrike()){
            if(next!=null&&next.isStrike()){
                return MAX_PIN + MAX_PIN +(next.getAsLinkedNode().getNextItem()==null?0:next.getAsLinkedNode().getNextItem().getFirstPin());
            }else if(next!=null&&(next.isSpare()||next.isMiss())){
                return MAX_PIN + next.getFirstPin() + next.getSecondPin();
            }else {
                return MAX_PIN + (next==null?0:next.getFirstPin());
            }
        }
        if(isSpare()){
            return MAX_PIN + (next==null?0:next.getFirstPin());
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
                statusCode = StatusCodeImp.INVALIDPIN;
                return false;
            }
            if(bowlingTurnEntity.getFirstPin()+bowlingTurnEntity.getSecondPin()>MAX_PIN){
                statusCode = StatusCodeImp.INVALIDPIN;
                return false;
            }
        }else {
            if(bowlingTurnEntity.getFirstPin()<0){
                statusCode = StatusCodeImp.INVALIDPIN;
                return false;
            }
            if(bowlingTurnEntity.getFirstPin()>MAX_PIN){
                statusCode = StatusCodeImp.INVALIDPIN;
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
