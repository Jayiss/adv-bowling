package training.adv.bowling.impl.lihaojie;

import training.adv.bowling.api.*;

public class BowlingTurnImpl implements BowlingTurn, BowlingTurnEntity, LinkedList<BowlingTurn> {
    private Integer max;//not null
    private Integer first;
    private Integer second;
    private TurnKey key;
    private BowlingTurn next;
    private BowlingTurn previous;//not null
    private BowlingTurn last;
    public BowlingTurnImpl(BowlingTurn previous,Integer max){
        this.previous=previous;
        this.max=max;
    }


    //the following three method all assume isValid
    //any method return true means turn is finished
    //any turn that is not finish invoking following method will return false
    @Override
    public Boolean isStrike() {
        if (!isFinished())return false;
        return first==max;
    }

    @Override
    public Boolean isSpare() {
        if (!isFinished())return false;
        return !isStrike()&&second+first==max;
    }

    @Override
    public Boolean isMiss() {
        if (!isFinished())return false;
        return !isStrike()&&!isSpare();
    }

    @Override
    public Integer getFirstPin() {
        return first;
    }

    @Override
    public Integer getSecondPin() {
        return second;
    }

    @Override
    public void setFirstPin(Integer pin) {
        this.first=pin;
    }

    @Override
    public void setSecondPin(Integer pin) {
        this.second=pin;
    }

    @Override
    public StatusCode addPins(Integer... pins) {
        BowlingTurn last=getlast(new IntPointer());
        if (last!=this)return last.addPins(pins);

        BowlingTurnImpl save=new BowlingTurnImpl(previous,max);
        save.first=this.first;
        save.second=this.second;
        save.next=this.next;
        save.key=this.key;

        if (pins==null&&!addable()){
            return StatusCodeImpl.FINISHED;
        }

        for (Integer i :
                pins) {
            StatusCode code=addPin(i);
            if (code!=StatusCodeImpl.SUCCESS){
                this.first=save.first;
                this.second=save.second;
                this.max=save.max;
                this.next=save.next;
                this.key=save.key;
                this.previous=save.previous;
                return code;
            }
        }

        return StatusCodeImpl.SUCCESS;
    }

    private class IntPointer{
        int value;
    }

    private StatusCode addPin(Integer pin){
        if (!addable())
            return StatusCodeImpl.FAILED;
        IntPointer roundNumber=new IntPointer();
        roundNumber.value=1;
        BowlingTurn last=getlast(roundNumber);
        if (last==this){
            if (!isFinished()){
                if (first==null){
                    first=pin;
                    if (isValid())return StatusCodeImpl.SUCCESS;
                    else return StatusCodeImpl.FAILED;
                }
                else {
                    if (second!=null)throw  new IllegalStateException();
                    second=pin;
                    if (isValid())return StatusCodeImpl.SUCCESS;
                    else return StatusCodeImpl.FAILED;
                }
            }
            else {
                this.setNextItem(new BowlingTurnImpl(this,max));
                return addPins(pin);
            }
        }else return last.addPins(pin);
    }

    //assume current turn is legal
    private boolean addable(){
        IntPointer roundNumber=new IntPointer();
        BowlingTurn last=getlast(roundNumber);

        //-----------------------------
        if (roundNumber.value<max)return true;
        if (roundNumber.value==max){
            if (last.isMiss())
                return false;
            else
                return true;
        }
        if (roundNumber.value==max+1){
            BowlingTurn maxthTurn=last.getAsLinkedNode().getPreviousItem();
            if (maxthTurn.isSpare()&&last.getFirstPin()==null)return true;
            else if (maxthTurn.isStrike()){
                return last.getSecondPin()==null;
            }else return false;
        }
        if (roundNumber.value==max+2){
            BowlingTurn maxthTurn=last.getAsLinkedNode().getPreviousItem().getAsLinkedNode().getPreviousItem();
            if (maxthTurn.isStrike()&&last.getFirstPin()==null)return true;
            return false;
        }return false;
    }

    //get the last turn,pointer indicate the round number, 0 means dummy
    private BowlingTurn getlast(IntPointer pointer){
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
        return previous;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return this;
    }

    //assume turn is valid
    @Override
    public Boolean isFinished() {
        if (first==null)return false;
        return first.equals(max)||second!=null;
    }

    @Override
    public Integer getScore() {

        if (getRoundNumber()>max)return 0;
        Integer score=0;
        if (first!=null)score+=first;
        if (second!=null)score+=second;
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

    /**
     * get the score of next i throws,from turn
     * @param turn
     * @param i
     * @return
     */
    private static Integer getScoreOfNext(BowlingTurn turn,int i){
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
        if (first==null)return second==null;
        if (!check(first))return false;
        if (first.equals(max)&&second!=null)return false;
        if (second!=null){
            if (!check(second))return false;
            if (first+second>max)return false;
        }
        return true;
    }

    public void setMax(Integer max){
        if (max<0)throw new IllegalArgumentException();
        this.max=max;
    }

    private Boolean check(Integer i){
        return i>=0&&i<=max;
    }
}
