package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
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
    public BowlingTurn[] getTurns() {
        BowlingTurn[] bowlingTurns;
        BowlingTurnEntity[] turnEntity= this.getEntity().getTurnEntities();
        bowlingTurns=new BowlingTurn[turnEntity.length];
        bowlingTurns[0]=new BowlingTurnImpl(turnEntity[0],null);
        for(int i=1;i<turnEntity.length-1;i++){
            bowlingTurns[i]=new BowlingTurnImpl(turnEntity[i],bowlingTurns[i-1]);
        }
        for(int i=0;i<turnEntity.length-1;i++){
            bowlingTurns[i].getAsLinkedNode().setNextItem(bowlingTurns[i+1]);
        }
        return bowlingTurns;
    }

    @Override
    public BowlingTurn newTurn() {
        BowlingTurn last=null;
        if(getTurns().length==0){
            last=null;
        }
        last=getTurns()[getTurns().length-1];
        return new BowlingTurnImpl(new BowlingTurnEntityImpl(),last);
        //set firstPin secondPin nextNode在外面操作
    }

    @Override
    public Boolean isGameFinished() {
        //测试是否超出长度
        int count=0;
        BowlingTurn turn=firstTurn;
        while((turn!=null||turn.getFirstPin()!=null)&&count<getEntity().getMaxTurn()+1){
            turn=turn.getAsLinkedNode().getNextItem();
            count++;
        }
        if(count<getEntity().getMaxTurn()+1){
            return true;
        }
        //第11个->第10个
        BowlingTurn preTurn=turn.getAsLinkedNode().getPreviousItem();
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
        if(preTurn.isStrike()&&turn.isStrike()&&nextTurn!=null)


    }

    @Override
    public StatusCode addScores(Integer... pins) {
        if(pins==null){
            return null;
        }
        //前一次的最后一个
        BowlingTurn lastTurn;
        if(firstTurn==null){
            lastTurn=newTurn();
        }else{
            lastTurn=getTurns()[getTurns().length-1];
        }
        StatusCode code=lastTurn.addPins(pins);
        injectEntity();
        //TODO:code=成功
        if(!isGameFinished()){
            //超出：回退
            lastTurn.getEntity().setSecondPin(null);
            //TODO:code为firstPin为null有secondpin为null
            if(code==null){
                lastTurn.getEntity().setFirstPin(null);
            }
            lastTurn.getAsLinkedNode().setNextItem(null);
            //TODO:code为失败的代码
            code=null;
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
        while(turn!=null){
            entities.add(firstTurn.getEntity());
            turn=turn.getAsLinkedNode().getNextItem();
        }
        this.getEntity().setTurnEntities(entities.toArray(new BowlingTurnEntity[0]));
    }
}
