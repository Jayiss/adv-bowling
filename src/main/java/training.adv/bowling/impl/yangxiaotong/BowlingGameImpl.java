package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;

public class BowlingGameImpl implements BowlingGame {

    private BowlingGameEntity entity=null;

    public BowlingGameImpl(Integer maxPin, Integer maxTurn, BowlingTurnEntity[] turns, Integer id){
        entity=new BowlingGameEntityImpl(maxPin,maxTurn,id);
        entity.setTurnEntities(turns);
    }

    @Override
    public BowlingTurn getFirstTurn() {
        BowlingTurn turn=new BowlingTurnImpl(entity.getTurnEntities()[0].getFirstPin(),
                entity.getTurnEntities()[0].getSecondPin(),entity.getTurnEntities()[0].getId());
        return turn;
    }

    @Override
    public int getTotalScore() {
        return entity.get;
    }

    @Override
    public int[] getScores() {
        return new int[0];
    }


    @Override
    public BowlingTurn[] getTurns() {
        Integer length=entity.getTurnEntities().length;
        BowlingTurn[] bTurns=new BowlingTurnImpl[length];

        bTurns[0]=new BowlingTurnImpl(entity.getTurnEntities()[0].getFirstPin(),
                entity.getTurnEntities()[0].getSecondPin(),entity.getTurnEntities()[0].getId(),node);

        LinkedList<BowlingTurn> node=new LinkedListImpl(bTurns[0]);

        for(int i=1;i<length;i++){
            bTurns[i]=new BowlingTurnImpl(entity.getTurnEntities()[i].getFirstPin(),entity.getTurnEntities()[i].getSecondPin(),
                    entity.getTurnEntities()[i].getId());
            node=new LinkedListImpl(bTurns[i-1],bTurns[i]);

        }
        return bTurns;
    }

    @Override
    public Boolean isGameFinished() {
        Integer length;
        BowlingTurn[] turns=this.getTurns();

        if(turns!=null) {
            length=turns.length;
        }else {
            length=0;
        }

        if((length==maxTurn)&&(turns[length-1].getFirstPin()!=maxPin)&&(turns[length-1].getSecondPin()!=null)) {
            return true;
        }else if((length==maxTurn+1)&&(turns[maxTurn-1].isSpare())) {
            return true;
        }else if((length==maxTurn+2)&&(turns[maxTurn-1].isStrike())&&(turns[maxTurn].isStrike())) {
            return true;
        }
        return false;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        return null;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return entity;
    }
}
