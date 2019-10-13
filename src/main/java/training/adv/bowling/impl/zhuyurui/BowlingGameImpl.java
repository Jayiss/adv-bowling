package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn,BowlingTurnEntity,BowlingGameEntity> implements BowlingGame {

    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurn firstTurn=new BowlingTurnImpl(null,null);
    //private int[] scores={0};

    public BowlingGameImpl (Integer maxPin,Integer maxTurn){
        bowlingGameEntity=new BowlingGameEntityImpl(maxPin,maxTurn);
        firstTurn=new BowlingTurnImpl(null,null,maxPin);
    }

    public BowlingGameImpl (BowlingGameEntity entity){
            bowlingGameEntity=entity;
            if(entity!=null){
                firstTurn=new BowlingTurnImpl(null,null,entity.getMaxPin());
            }


    }

    public BowlingGameImpl (){
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return firstTurn;
    }

    @Override
    public BowlingTurn[] getTurns() {
        if (bowlingGameEntity.getTurnEntities()==null){
            BowlingTurn bowlingTurn=new BowlingTurnImpl(null,null);
            BowlingTurn[] turns={bowlingTurn};
            return turns;
        }

//        int index=1;
//        List<BowlingTurn> turns=new ArrayList<>();
//        BowlingTurn temp= firstTurn;
//        turns.add(temp);
//        while (temp.getAsLinkedNode().getNextItem()!=null){
//            if(index==10){
//                break;
//            }
//            temp=temp.getAsLinkedNode().getNextItem();
//            turns.add(temp);
//            index++;
//
//        }
//        return turns.toArray(BowlingTurn[]::new);

        int index=1;
        List<BowlingTurn> turns=new ArrayList<>();
        BowlingTurn temp= new BowlingTurnImpl(firstTurn.getEntity(),firstTurn.getAsLinkedNode(),bowlingGameEntity.getMaxPin());
        turns.add(temp);
        while (temp.getAsLinkedNode().getNextItem()!=null){
            if(index==10){
                break;
            }
            temp=temp.getAsLinkedNode().getNextItem();
            BowlingTurn turn= new BowlingTurnImpl(temp.getEntity(),temp.getAsLinkedNode(),bowlingGameEntity.getMaxPin());
            turns.add(turn);
            index++;

        }
        return turns.toArray(BowlingTurn[]::new);





    }

    @Override
    public BowlingTurn newTurn() {
        firstTurn=new BowlingTurnImpl(bowlingGameEntity.getMaxPin());
        return firstTurn;
    }



    @Override
    public Boolean isGameFinished() {
        int length=getLength(firstTurn);
        if (length <bowlingGameEntity.getMaxTurn() ) {
            return false;
        }
        if (isFinishBorder(firstTurn,length)) {
            return true;
        }
        return false;
    }

    private boolean isFinishBorder(BowlingTurn first,int length) {
        BowlingTurn turn=getTurnOfIndex(first,9);
        if (length == 10 && turn.isFinished() && turn.isMiss()) {
            return true;
        }
        if (length == 11 && turn.isFinished() && turn.isSpare()) {
            Integer a = getTurnOfIndex(first,10).getSecondPin();
            if (a == null || a == 0) {
                return true;
            }
        }
        if (length == 11 && turn.isStrike() && getTurnOfIndex(first,10).isSpare()) {
            return true;
        }
        if (length == 12 && turn.isStrike() && getTurnOfIndex(first,10).isStrike()) {
            Integer a = getTurnOfIndex(first,11).getSecondPin();
            if (a == null || a == 0) {
                return true;
            }
        }

        return false;
    }


    private BowlingTurn getTurnOfIndex(BowlingTurn firstTurn,int index){
        int i=0;
        BowlingTurn temp=firstTurn;
        while (temp.getAsLinkedNode().getNextItem()!=null){
            temp=temp.getAsLinkedNode().getNextItem();
            i++;
            if(i==index){
                return temp;
            }
        }
        return null;
    }

    private int getLength(BowlingTurn firstTurn){
        if(firstTurn.getFirstPin()==null){
            return 0;
        }
        int length=1;
        while (firstTurn.getAsLinkedNode().getNextItem()!=null){
            length++;
            firstTurn=firstTurn.getAsLinkedNode().getNextItem();

        }
        return length;
    }

    private BowlingTurn getLast(BowlingTurn firstTurn){

        BowlingTurn last=firstTurn;
        while (last.getAsLinkedNode().getNextItem()!=null){
            last=last.getAsLinkedNode().getNextItem();
        }
        return last;
    }



    @Override
    public StatusCode addScores(Integer... pins) {
        if (pins.length == 0) {
            return StatusCodeImpl.FAILED;
        }
        if (isGameFinished()) {
            return StatusCodeImpl.FAILED;
        }
        List<Integer> pinsA = new ArrayList<>();
        for (Integer t : pins) {
            if(t<0){
                return StatusCodeImpl.FAILED;
            }
            pinsA.add(t);
            if (t == 10) {
                pinsA.add(0);
            }
        }

        BowlingTurn last=getLast(firstTurn);

        LinkedList<BowlingTurn> backuplist= new LinkedListImpl<BowlingTurn>(last.getAsLinkedNode().getPreviousItem());
        BowlingTurn backup=new BowlingTurnImpl(last.getEntity(),backuplist,bowlingGameEntity.getMaxPin());
        StatusCode code=last.addPins(pinsA.toArray(Integer[]::new));
        if (getLength(firstTurn) > 12 || (getLength(firstTurn) > 10 && !(isFinishBorder(firstTurn,getLength(firstTurn))))) {
            last=backup;
            last.getAsLinkedNode().getPreviousItem().getAsLinkedNode().setNextItem(backup);
            code=StatusCodeImpl.FAILED;

        }
        bowlingGameEntity.setTurnEntities(getTurnEntityFromList(firstTurn));
        return code;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return bowlingGameEntity;
    }



    private BowlingTurnEntity[] getTurnEntityFromList(BowlingTurn first){
        List<BowlingTurnEntity> turns=new ArrayList<>();
        int index=1;
        TurnKey key=new TurnKeyImpl(index,bowlingGameEntity.getId());
        first.getEntity().setId(key);
        turns.add(first.getEntity());
        while (first.getAsLinkedNode().getNextItem()!=null){
            index++;
            first=first.getAsLinkedNode().getNextItem();
            TurnKey key1=new TurnKeyImpl(index,bowlingGameEntity.getId());
            first.getEntity().setId(key1);
            turns.add(first.getEntity());
        }

        return turns.toArray(BowlingTurnEntity[]::new);

    }
}
