package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;


import java.util.ArrayList;

public class BowlingGameImpl  implements BowlingGame {
    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurn firstTurn;

    public BowlingGameImpl(){
        this(null);
    }

    public BowlingGameImpl(BowlingGameEntity bowlingGameEntity){
        this.bowlingGameEntity=bowlingGameEntity;
    }


    @Override
    public BowlingTurn getFirstTurn() {
        return firstTurn;
    }

    @Override
    public int getTotalScore() {
        injectTurn();
        int sum=0;
        int[]scores=getScores();
        if(scores==null||scores.length==0){
            return 0;
        }
        if(!isGameFinished()){
            for(Integer s:scores){
                System.out.println(s);
                sum+=s;
            }
        }else{
            for(int i=0;i<getEntity().getMaxTurn();i++){
                System.out.println("score："+scores[i]);
                sum+=scores[i];
            }
        }
        return sum;
    }

    @Override
    public int[] getScores() {
        if(this.getEntity().getTurnEntities()==null||this.getEntity().getTurnEntities().length==0){
            return null;
        }
        int[] scores=new int[this.getEntity().getTurnEntities().length];
        int i=0;
        BowlingTurn turn=firstTurn;
        while(turn!=null){
            scores[i]=turn.getScore();
            turn=turn.getAsLinkedNode().getNextItem();
            i++;
        }
        return scores;

    }

    @Override
    public BowlingTurn[] getTurns() {
        BowlingTurnEntity[] turnEntity= this.getEntity().getTurnEntities();
        BowlingTurn[] returnTurns=new BowlingTurnImpl[turnEntity.length];
        BowlingTurnEntity[] returnEntites=new BowlingTurnEntity[turnEntity.length];
        for(int i=0;i<turnEntity.length;i++){
            returnEntites[i]=new BowlingTurnEntityImpl(turnEntity[i].getFirstPin(),turnEntity[i].getSecondPin(),turnEntity[i].getId());
            returnTurns[i]=new BowlingTurnImpl(returnEntites[i]);
        }
        return returnTurns;


    }

    @Override
    public BowlingTurn newTurn() {
        BowlingTurn last=getLastTurn();
        return new BowlingTurnImpl(new BowlingTurnEntityImpl(),last);
    }

    @Override
    public Boolean isGameFinished() {
        int length =this.getEntity().getTurnEntities().length;
        if (length < getEntity().getMaxTurn()) {
            return false;
        }
        int i=0;
        BowlingTurn turn=firstTurn;
        while(i<getEntity().getMaxTurn()-1){
            turn=turn.getAsLinkedNode().getNextItem();
            i++;
        }
        if(turn.getSecondPin()==null){
            return false;
        }
        return true;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        if(pins==null||pins.length==0){
            return null;
        }
        //前一次的最后一个
        BowlingTurn lastTurn;
        lastTurn=getLastTurn();
        if(lastTurn==null){
            lastTurn=newTurn();
            firstTurn=lastTurn;
        }
        Integer preFirst=lastTurn.getFirstPin();
        Integer preSecond=lastTurn.getSecondPin();
        StatusCode code=lastTurn.addPins(pins);
        /*BowlingTurn turn=firstTurn;
        int i=0;
        while(turn!=null){
            System.out.println(i+"   "+turn.getFirstPin()+":"+turn.getSecondPin());
            turn=turn.getAsLinkedNode().getNextItem();
            i++;
        }*/

        if(code==StatusCodeImpl.FAIL||(!testReer()&&code==StatusCodeImpl.SUCCESS)){
            //超出：回退
            lastTurn.getEntity().setSecondPin(preSecond);
            lastTurn.getEntity().setFirstPin(preFirst);
            lastTurn.getAsLinkedNode().setNextItem(null);
            //firstTurn原本为null的情况
            if(lastTurn.getFirstPin()==null){
                firstTurn=null;
            }
            code=StatusCodeImpl.FAIL;
        }
        injectEntity();
        return code;
    }

    @Override
    public BowlingGameEntity getEntity() {
       return bowlingGameEntity;
    }

    private void injectEntity(){
        ArrayList<BowlingTurnEntity> entities=new ArrayList<>();
        BowlingTurn turn=firstTurn;
        int i=0;
        while(turn!=null){
            turn.getEntity().setId(new TurnKeyImpl(i,this.getEntity().getId()));
            entities.add(turn.getEntity());
            turn=turn.getAsLinkedNode().getNextItem();
        }
        this.getEntity().setTurnEntities(entities.toArray(new BowlingTurnEntity[0]));
    }

    private void injectTurn(){
        BowlingTurnEntity[] turnEntities=this.getEntity().getTurnEntities();
        if(turnEntities==null||turnEntities.length==0){
            firstTurn=null;
            return;
        }
        BowlingTurn[] turns=new BowlingTurnImpl[turnEntities.length];
        turns[0]=new BowlingTurnImpl(turnEntities[0],null);
        for(int i=1;i<turnEntities.length;i++){
            turns[i]=new BowlingTurnImpl(turnEntities[i],turns[i-1]);
        }
        for(int i=0;i<turnEntities.length-1;i++){
            turns[i].getAsLinkedNode().setNextItem(turns[i+1]);
        }

        this.firstTurn=turns[0];
    }

    private BowlingTurn getLastTurn(){
        BowlingTurn turn=firstTurn;
        BowlingTurn pre=null;
        while(turn!=null){
            pre=turn;
            turn=turn.getAsLinkedNode().getNextItem();
        }
        return pre;
    }
    //测试长度是否超出
    private boolean testReer(){
        //测试是否超出长度
        int count=0;
        BowlingTurn turn=firstTurn;
        BowlingTurn preTurn=null;
        while(turn!=null&&count<getEntity().getMaxTurn()){
            preTurn=turn;
            turn=turn.getAsLinkedNode().getNextItem();
            count++;
            //System.out.println("count:"+count);
        }
        if(count<getEntity().getMaxTurn()+1&&turn==null){
            return true;
        }
        //第11轮->第10个

        if(preTurn.isMiss()){
            return false;
        }
        if(preTurn.isSpare()&&turn.getSecondPin()!=null){
            return false;
        }
        BowlingTurn nextTurn=turn.getAsLinkedNode().getNextItem();
        if (nextTurn == null) {
            return true;
        }
        if(preTurn.isStrike()&&!turn.isStrike()&&nextTurn!=null){
            return false;
        }
        if(preTurn.isStrike()&&turn.isStrike()&&!nextTurn.isStrike()&&nextTurn.getSecondPin()!=null){
            return false;
        }
        return true;
    }
}
