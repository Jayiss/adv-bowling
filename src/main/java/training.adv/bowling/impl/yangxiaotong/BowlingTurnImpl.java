package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.yangxiaotong.BowlingTurnEntityImpl;

public class BowlingTurnImpl implements BowlingTurn{

    private BowlingTurnEntity entity=null;
    private LinkedList<BowlingTurn> node=null;

    public BowlingTurnImpl(Integer firstPin, Integer secondPin, TurnKey id,LinkedList node){
        entity=new BowlingTurnEntityImpl();
        entity.setFirstPin(firstPin);
        entity.setSecondPin(secondPin);
        entity.setId(id);
        this.node=node;

        /*if(previous!=null){
            this.node=new LinkedListImpl(previous,this);
        }else{
            this.node=new LinkedListImpl(this);
        }*/

    }

    public BowlingTurnImpl(){

    }


    @Override
    public Boolean isStrike() {
        if(entity.getFirstPin()==maxPin) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isSpare() {
        if((entity.getSecondPin()!=null)&&(entity.getFirstPin()+entity.getSecondPin()==maxPin)) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isMiss() {
        if((entity.getSecondPin()!=null)&&(entity.getFirstPin()+entity.getSecondPin()<maxPin)) {
            return true;
        }
        return false;
    }

    @Override
    public Integer getFirstPin() {
        return entity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return entity.getSecondPin();
    }

    @Override
    public StatusCode addPins(Integer... pins) {//递归



        if(pins.length==2){
            entity.setFirstPin(pins[0]);
            entity.setSecondPin(pins[1]);
        }else if(pins.length==1){
            if((entity.getFirstPin()!=null)&&(entity.getSecondPin()==null)&&(entity.getFirstPin()!=maxTurn)){
                entity.setSecondPin(pins[0]);
            }else
        }
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return node;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return entity;
    }

    @Override
    public Boolean isFinished() {
        if(this.isStrike()||this.isSpare()||this.isMiss()){
            return true;
        }
        return false;
    }

    @Override
    public Integer getScore() {
        BowlingTurn nextNode=null;
        BowlingTurn nextNextNode=null;
        if(node.getNextItem()!=null){
            BowlingTurn next=node.getNextItem();
        }
        if(nextNode.getAsLinkedNode().getNextItem()!=null){
            nextNextNode=nextNode.getAsLinkedNode().getNextItem();
        }

        Integer score=0;
        if(this.isValid()) {
            if(nextNode==null){
                if(!this.isStrike()){
                    if(this.getSecondPin()!=null){
                        score=this.getFirstPin()+this.getSecondPin();
                    }else{
                        score=this.getFirstPin();
                    }
                }else if(this.isStrike()){
                    score=maxPin;
                }
            }else{
                if(nextNextNode==null){
                    if(this.isStrike() && nextNode.isStrike()){
                        score=2*maxPin;
                    }else if(this.isStrike() && !nextNode.isStrike()){
                        if(nextNode.getSecondPin()!=null){
                            score=maxPin+nextNode.getFirstPin()+nextNode.getSecondPin();
                        }else{
                            score=maxPin+nextNode.getFirstPin();
                        }
                    }else if(this.isSpare()){
                        score=maxPin+nextNode.getFirstPin();
                    }else if(this.isMiss()){
                        score=this.getFirstPin()+this.getSecondPin();
                    }
                }else{
                    if(this.isStrike() && nextNode.isStrike()){
                        score=2*maxPin+nextNextNode.getFirstPin();
                    }else if(this.isStrike() && !nextNode.isStrike()){
                        score=maxPin+nextNode.getFirstPin();
                    }else if(this.isSpare()){
                        score=maxPin+nextNode.getFirstPin();
                    }else if(this.isMiss()){
                        score=this.getFirstPin()+this.getSecondPin();
                    }
                }
            }

        }
        return score;
}

    @Override
    public Boolean isValid() {
        if((this.getFirstPin()<0)||(this.getFirstPin()>maxPin)) {
            return false;
        }
        if(this.getSecondPin()!=null) {
            if((this.getSecondPin()<0)||(this.getSecondPin()>maxPin)||(this.getFirstPin()+this.getSecondPin()>maxPin)) {
                return false;
            }
        }
        return true;
    }

}
