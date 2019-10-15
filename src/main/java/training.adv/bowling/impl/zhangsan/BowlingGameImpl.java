package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;

import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurn first;
    final static int MaxPin = 10;
    final static int MaxTurn = 10;

    public BowlingGameImpl(BowlingGameEntity bowlingGameEntity, BowlingTurn first){
        this.bowlingGameEntity =  bowlingGameEntity;
        this.first = first;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        BowlingTurn cur = first;
        while (cur.getAsLinkedNode().getPreviousItem()!=null){
            cur = cur.getAsLinkedNode().getPreviousItem();
        }

        return cur.getAsLinkedNode().getNextItem();
    }

    @Override
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> bowlingTurnList = new java.util.LinkedList<>();
        BowlingTurn headCopy = getFirstTurn();
        if(headCopy==null){
            return new BowlingTurn[0];
        }
        while (headCopy!=null){
            bowlingTurnList.add(headCopy);
            headCopy = headCopy.getAsLinkedNode().getNextItem();
        }
        BowlingTurn[] turns = bowlingTurnList.toArray(new BowlingTurn[0]);
        return turns;
    }

    @Override
    public BowlingTurn newTurn() {
        BowlingTurn newTurn = new BowlingTurnImpl(MaxPin,MaxTurn,first);
        return newTurn;
    }


    @Override
    public Boolean isGameFinished() {
        int index = 1;
        BowlingTurn bowlingTurn = getFirstTurn();
        if(bowlingTurn==null){
            return false;
        }
        while (bowlingTurn.getAsLinkedNode().getNextItem()!=null){
            if(index==MaxTurn){
                break;
            }
            index++;
            if(!bowlingTurn.isFinished()){
                return false;
            }
            bowlingTurn = bowlingTurn.getAsLinkedNode().getNextItem();
        }
        if(index<10){
            return false;
        }
        if(!bowlingTurn.isFinished()){
            return false;
        }
        if(bowlingTurn.isStrike()){
            BowlingTurn nextTurn = bowlingTurn.getAsLinkedNode().getNextItem();
            if(nextTurn==null){
                return false;
            }else if(nextTurn.isStrike()){
                return nextTurn.getAsLinkedNode().getNextItem() != null;
            }else {
                return nextTurn.isFinished();
            }
        }
        if(bowlingTurn.isSpare()){
            if(bowlingTurn.getAsLinkedNode().getNextItem()==null){
                return false;
            }
            return bowlingTurn.getAsLinkedNode().getNextItem().getFirstPin()!=null;
        }
        return true;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        StatusCode statusCode =  StatusCodeImp.SUCCESS;
        boolean lastFishedFlag = false;
        BowlingTurn tempHead = first;
        if (first.getAsLinkedNode().getPreviousItem()==null&&pins.length!=0){
            first = newTurn();
        }
        if(tempHead.isFinished()){
            lastFishedFlag = true;
        }
        for(int i = 0;i<pins.length;i++){
            if(isGameFinished()){
                statusCode =  StatusCodeImp.FINISHED;
                break;
            }
            if(first.isFinished()){
                first = newTurn();
            };
            statusCode = first.addPins(pins[i]);
            if (statusCode!=StatusCodeImp.SUCCESS){
                break;
            }
        }
        if(statusCode!=StatusCodeImp.SUCCESS){
            first = tempHead;
            first.getAsLinkedNode().setNextItem(null);
            if(!lastFishedFlag){
                first.getEntity().setSecondPin(null);
            }
        }
        return statusCode;
    }

    @Override
    public BowlingGameEntity getEntity() {
        BowlingTurn[] turns = getTurns();
        List<BowlingTurnEntity> list = new ArrayList<>();
        int id = 1;
        for(BowlingTurn turn:turns){
            TurnKey turnKey = new TurnKeyImpl(id,bowlingGameEntity.getId());
            BowlingTurnEntity bowlingTurnEntity = turn.getEntity();
            bowlingTurnEntity.setId(turnKey);
            list.add(bowlingTurnEntity);
            id++;
        }
        BowlingTurnEntity[] bowlingTurnEntities = new BowlingTurnEntityImpl[turns.length];
        bowlingTurnEntities = list.toArray(bowlingTurnEntities);
        bowlingGameEntity.setTurnEntities(bowlingTurnEntities);
        return bowlingGameEntity;
    }

}