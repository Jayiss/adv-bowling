package training.adv.bowling.impl.fanxu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurn head;
    //每次改变
    final static int MAX_TURN = 10;
    final static int MAX_PIN = 10;

    public BowlingGameImpl(BowlingGameEntity bowlingGameEntity, BowlingTurn head){
        this.bowlingGameEntity =  bowlingGameEntity;
        //第一个BowlingTurn不存数据。
        this.head = head;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        BowlingTurn cursor = head;
        while (cursor.getAsLinkedNode().getPreviousItem()!=null){
            cursor = cursor.getAsLinkedNode().getPreviousItem();
        }

        return cursor.getAsLinkedNode().getNextItem();
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
//        while (bowlingTurnList.size()>10){
//            ((LinkedList<BowlingTurn>) bowlingTurnList).removeLast();
//        }
        BowlingTurn[] turns = bowlingTurnList.toArray(new BowlingTurn[0]);
        return turns;
    }

    //??what mean;
    @Override
    public BowlingTurn newTurn() {
        BowlingTurn newTurn = new BowlingTurnImpl(MAX_PIN,MAX_TURN,head);
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
            if(index==MAX_TURN){
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
        //既然出来了，说明index = 10，并且完成了，对该情况进行判断,此时bowlingTurn应该是finish的状态：
        if(bowlingTurn.isStrike()){
            BowlingTurn nextBolingTurn = bowlingTurn.getAsLinkedNode().getNextItem();
            if(nextBolingTurn==null){
                return false;
            }else if(nextBolingTurn.isStrike()){
                return nextBolingTurn.getAsLinkedNode().getNextItem() != null;
            }else {
                return nextBolingTurn.isFinished();
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
        //回退的地点：
        BowlingTurn tempHead = head;
        if (head.getAsLinkedNode().getPreviousItem()==null&&pins.length!=0){
            head = newTurn();
        }
        if(tempHead.isFinished()){
            lastFishedFlag = true;
        }
        for(int i = 0;i<pins.length;i++){
            if(isGameFinished()){
                System.out.println(i);
                statusCode =  StatusCodeImp.GAMEFINISHED;
                break;
            }
            if(head.isFinished()){
                head = newTurn();
            };
            statusCode = head.addPins(pins[i]);
            if (statusCode!=StatusCodeImp.SUCCESS){
                break;
            }
        }
        if(statusCode!=StatusCodeImp.SUCCESS){
            //回滚：
            System.out.println(statusCode);
            head = tempHead;
            head.getAsLinkedNode().setNextItem(null);
            if(!lastFishedFlag){
                head.getEntity().setSecondPin(null);
            }
        }
        return statusCode;
    }

    @Override
    public BowlingGameEntity getEntity() {
        BowlingTurn[] turns = getTurns();
        List<BowlingTurnEntity> list = new ArrayList<>();
        int turnID = 1;
        for(BowlingTurn turn:turns){
            TurnKey turnKey = new BowlingTurnKeyBean(turnID,bowlingGameEntity.getId());
            BowlingTurnEntity bowlingTurnEntity = turn.getEntity();
            bowlingTurnEntity.setId(turnKey);
            list.add(bowlingTurnEntity);
            turnID++;
        }
        BowlingTurnEntity[] bowlingTurnEntities = new BowlingTurnBean[turns.length];
        bowlingTurnEntities = list.toArray(bowlingTurnEntities);
        bowlingGameEntity.setTurnEntities(bowlingTurnEntities);
        return bowlingGameEntity;
    }

}
