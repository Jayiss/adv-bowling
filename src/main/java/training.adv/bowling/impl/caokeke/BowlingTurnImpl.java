package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

import java.util.ArrayList;
import java.util.Arrays;

public class BowlingTurnImpl implements BowlingTurn {

    private BowlingTurnEntity bte;
    private LinkedList<BowlingTurn> ptb;
    private Integer MAX_SCORE;



    public BowlingTurnImpl(){
        bte=new BowlingTurnEntityImpl(null,null,null,null);
        ptb=new LinkedListImpl(null);
    }

    @Override
    public Boolean isStrike() {
        if (!isFinished())return false;
        return bte.getFirstPin()==MAX_SCORE;
    }

    @Override
    public Boolean isSpare() {
        if (!isFinished())return false;
        return !isStrike()&&bte.getFirstPin()+bte.getSecondPin()==MAX_SCORE;
    }

    @Override
    public Boolean isMiss() {
        if (!isFinished())return false;
        return !isStrike()&&!isSpare();
    }

    @Override
    public Integer getFirstPin() {
        return bte.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return bte.getSecondPin();
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.first=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.second=pin;
    }

    private StatusCode firstAdd(Integer...pins){
        if(pins.length==0)return StatusCodeImpl.ADD_SUCCESS;
        else if(pins.length==1 && pins[0]<=10 || pins[0]==10){
            if(addNewNode(this,1,pins)==StatusCodeImpl.ADD_FAILED){
                this.getAsLinkedNode().setNextItem(null);
                return StatusCodeImpl.ADD_FAILED;
            }else return StatusCodeImpl.ADD_SUCCESS;
        }
        else if(pins.length>=2 && pins[0]+pins[1]<=10){
            if(addNewNode(this,2,pins)==StatusCodeImpl.ADD_FAILED){
                this.getAsLinkedNode().setNextItem(null);
                return StatusCodeImpl.ADD_FAILED;
            }else return StatusCodeImpl.ADD_SUCCESS;
        }
        else return StatusCodeImpl.ADD_FAILED;
    }

    private StatusCode addNewNode(BowlingTurn tmp,int index,Integer...pins){
        //BowlingTurn tmp=new BowlingTurnImpl();
        this.getAsLinkedNode().setNextItem(tmp);
        tmp.getEntity().setFirstPin(pins[0]);
        if(index==2)
            tmp.getEntity().setSecondPin(pins[1]);
        Integer []tmp_pins= Arrays.copyOfRange(pins,index,pins.length);
        if(tmp.addPins(tmp_pins)==StatusCodeImpl.ADD_FAILED){
            this.getAsLinkedNode().setNextItem(null);
            return StatusCodeImpl.ADD_FAILED;
        }
        else return StatusCodeImpl.ADD_SUCCESS;
    }

    @Override
    public StatusCode addPins(Integer... pins) {
        if(pins.length==0)
            return StatusCodeImpl.ADD_SUCCESS;
        //第一次加入
        if(bte.getFirstPin()==null)firstAdd(pins);

        //判断当前最后一个是否结束
        BowlingTurn bt=this;
        while(bt.getAsLinkedNode().getNextItem()!=null)bt=bt.getAsLinkedNode().getNextItem();
        if(bt.getSecondPin()==null && bt.getFirstPin()+pins[0]<=10){
            bt.getEntity().setSecondPin(pins[0]);

            BowlingTurn tmp=new BowlingTurnImpl();
            this.getAsLinkedNode().setNextItem(tmp);

            Integer []tmp_pins= Arrays.copyOfRange(pins,1,pins.length);

            if(tmp.addPins(tmp_pins)==StatusCodeImpl.ADD_FAILED){
                bt.getEntity().setSecondPin(null);
                return StatusCodeImpl.ADD_FAILED;
            }else return StatusCodeImpl.ADD_SUCCESS;
        }else if((pins.length==1 && pins[0]<=10) || pins[0]==10){
            BowlingTurn tmp=new BowlingTurnImpl();
            this.getAsLinkedNode().setNextItem(tmp);
            if(addNewNode(tmp,1,pins)==StatusCodeImpl.ADD_FAILED){
                this.getAsLinkedNode().setNextItem(null);
                return StatusCodeImpl.ADD_FAILED;
            }else return StatusCodeImpl.ADD_SUCCESS;
        }
        else if(pins.length>=2 && pins[0]+pins[1]<=10){
            BowlingTurn tmp=new BowlingTurnImpl();
            this.getAsLinkedNode().setNextItem(tmp);
            if(addNewNode(tmp,2,pins)==StatusCodeImpl.ADD_FAILED){
                this.getAsLinkedNode().setNextItem(null);
                return StatusCodeImpl.ADD_FAILED;
            }else return StatusCodeImpl.ADD_SUCCESS;
        }
        else return StatusCodeImpl.ADD_FAILED;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return ptb;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return bte;
    }

    @Override
    public Boolean isFinished() {
        return bte.getSecondPin()==null;
    }

    @Override
    public Integer getScore() {
        return null;
    }

    @Override
    public Boolean isValid() {
        if(bte.getSecondPin()==null)
            return bte.getFirstPin()>=0 &&bte.getFirstPin()<=MAX_SCORE;

        return bte.getFirstPin()>=0 && bte.getSecondPin()>=0 &&
                bte.getFirstPin()<=MAX_SCORE && bte.getSecondPin()<=MAX_SCORE &&
                bte.getFirstPin()+bte.getSecondPin()<=MAX_SCORE;
    }
}
