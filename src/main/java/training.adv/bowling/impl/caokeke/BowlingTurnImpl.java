package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity, LinkedList<BowlingTurn> {

    private Integer MAX_SOCRE;
    private Integer firstPin;
    private Integer secondPin;
    private TurnKey key;
    private BowlingTurn next;
    private BowlingTurn pre;

    public BowlingTurnImpl(BowlingTurn previous,Integer max){
        this.pre=previous;
        this.MAX_SOCRE=max;
    }

    @Override
    public Boolean isStrike() {
        if (!isFinished())return false;
        return firstPin==MAX_SOCRE;
    }

    @Override
    public Boolean isSpare() {
        if (!isFinished())return false;
        return !isStrike()&&secondPin+firstPin==MAX_SOCRE;
    }

    @Override
    public Boolean isMiss() {
        if (!isFinished())return false;
        return !isStrike()&&!isSpare();
    }

    @Override
    public Integer getFirstPin() {
        return firstPin;
    }

    @Override
    public Integer getSecondPin() {
        return secondPin;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.firstPin=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.secondPin=pin;
    }

    @Override
    public StatusCode addPins(Integer... pins) {
        BowlingTurn last=getLast(new IntPointer());
        if (last!=this)return last.addPins(pins);

        BowlingTurnImpl save=new BowlingTurnImpl(pre,MAX_SOCRE);
        save.firstPin=this.firstPin;
        save.secondPin=this.secondPin;
        save.next=this.next;
        save.key=this.key;

        if (pins==null&&!addable()){
            return StatusCodeImpl.ADD_FINISHED;
        }

        for (Integer i :
                pins) {
            StatusCode code=addPin(i);
            if (code!=StatusCodeImpl.ADD_SUCCESS){
                this.firstPin=save.firstPin;
                this.secondPin=save.secondPin;
                this.MAX_SOCRE=save.MAX_SOCRE;
                this.next=save.next;
                this.key=save.key;
                this.pre=save.pre;
                return code;
            }
        }

        return StatusCodeImpl.ADD_SUCCESS;
    }

    private class IntPointer{
        int value;
    }

    private StatusCode addPin(Integer pin){
        if (!addable())
            return StatusCodeImpl.ADD_FAILED;
        IntPointer roundNumber=new IntPointer();
        roundNumber.value=1;
        BowlingTurn last=getLast(roundNumber);
        if (last==this){
            if (!isFinished()){
                if (firstPin==null){
                    firstPin=pin;
                    if (isValid())return StatusCodeImpl.ADD_SUCCESS;
                    else return StatusCodeImpl.ADD_FAILED;
                }
                else {
                    if (secondPin!=null)throw  new IllegalStateException();
                    secondPin=pin;
                    if (isValid())return StatusCodeImpl.ADD_SUCCESS;
                    else return StatusCodeImpl.ADD_FAILED;
                }
            }
            else {
                this.setNextItem(new BowlingTurnImpl(this,MAX_SOCRE));
                return addPins(pin);
            }
        }else return last.addPins(pin);
    }

    //assume current turn is legal
    private boolean addable(){
        IntPointer roundNumber=new IntPointer();
        BowlingTurn last=getLast(roundNumber);

        if (roundNumber.value<MAX_SOCRE)return true;
        if (roundNumber.value==MAX_SOCRE){
            if (last.isMiss())
                return false;
            else
                return true;
        }
        if (roundNumber.value==MAX_SOCRE+1){
            BowlingTurn maxthTurn=last.getAsLinkedNode().getPreviousItem();
            if (maxthTurn.isSpare()&&last.getFirstPin()==null)return true;
            else if (maxthTurn.isStrike()){
                return last.getSecondPin()==null;
            }else return false;
        }
        if (roundNumber.value==MAX_SOCRE+2){
            BowlingTurn maxthTurn=last.getAsLinkedNode().getPreviousItem().getAsLinkedNode().getPreviousItem();
            if (maxthTurn.isStrike()&&last.getFirstPin()==null)return true;
            return false;
        }return false;
    }

    private BowlingTurn getLast(IntPointer pointer){
        BowlingTurn first=this;
        pointer.value=1;
        while (first.getAsLinkedNode().getPreviousItem()!=null)
            first=first.getAsLinkedNode().getPreviousItem();

        BowlingTurn last=first;
        while (last.getAsLinkedNode().getNextItem()!=null){
            last= last.getAsLinkedNode().getNextItem();
            pointer.value++;
        }
        return last;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return this;
    }

    @Override
    public TurnKey getId() {
        return key;
    }

    @Override
    public void setId(TurnKey id) {
        this.key=id;
    }

    @Override
    public BowlingTurn getNextItem() {
        return next;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        next=item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return pre;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this;
    }

    //assume turn is valid
    @Override
    public Boolean isFinished() {
        if (firstPin==null)return false;
        return firstPin.equals(MAX_SOCRE)||secondPin!=null;
    }

    @Override
    public Integer getScore() {

        if (getRoundNumber()>MAX_SOCRE)return 0;
        Integer score=0;
        if (firstPin!=null)score+=firstPin;
        if (secondPin!=null)score+=secondPin;
        if (isSpare()){
            score+=getScoreOfNext(next,1);
        }else if (isStrike())
            score+=getScoreOfNext(next,2);
        return score;
    }

    private int getRoundNumber(){
        BowlingTurn turn=this;
        int round=0;
        while (turn!=null){
            turn=turn.getAsLinkedNode().getPreviousItem();
            round++;
        }
        return round;
    }

    private Integer getScoreOfNext(BowlingTurn turn,int i){
        Integer result=0;
        if (turn!=null&&i>0){
            if (turn.getFirstPin()!=null){
                result+=turn.getFirstPin();
                i--;
            }
            if (turn.getSecondPin()!=null&&i>0){
                result+=turn.getSecondPin();
                i--;
            }
            return result+=getScoreOfNext(turn.getAsLinkedNode().getNextItem(),i);
        }
        return result;
    }

    @Override
    public Boolean isValid() {
        if (firstPin==null)return secondPin==null;
        if (!check(firstPin))return false;
        if (firstPin.equals(MAX_SOCRE)&&secondPin!=null)return false;
        if (secondPin!=null){
            if (!check(secondPin))return false;
            if (firstPin+secondPin>MAX_SOCRE)return false;
        }
        return true;
    }

    public void setMax(Integer max){
        if (max<0)throw new IllegalArgumentException();
        this.MAX_SOCRE=max;
    }

    private Boolean check(Integer i){
        return i>=0&&i<=MAX_SOCRE;
    }
}
