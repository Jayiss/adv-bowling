package training.adv.bowling.impl.fanxu;


import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.AbstractGame;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity bowlingGameEntity;
    private LinkedList<BowlingTurnImpl> bowlingTurns;
    //每次改变
    final static int MAX_TURN = 10;
    final static int MAX_PIN = 10;

    public BowlingGameImpl(BowlingGameEntity bowlingGameEntity,LinkedList<BowlingTurnImpl> bowlingTurns){
        this.bowlingGameEntity =  bowlingGameEntity;
        this.bowlingTurns = bowlingTurns;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return bowlingTurns.getNextItem();
    }

    @Override
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> bowlingTurnList = new java.util.LinkedList<>();
        while (bowlingTurns.getNextItem()!=null){
            bowlingTurnList.add(bowlingTurns.getNextItem());
            bowlingTurns = bowlingTurns.getNextItem();
        }
        BowlingTurn[] turns = bowlingTurnList.toArray(new BowlingTurn[0]);
        return turns;
    }

    //??what mean;
    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    @Override
    public Boolean isGameFinished() {
        return null;
    }

    //statuscode什么意思？
    @Override
    public StatusCode addScores(Integer... pins) {
        if(isGameFinished()){
            return new StatusCodeImpl("-1","game finished");
        }
        BowlingTurn bowlingTurn = getFirstTurn();
        while (bowlingTurn.ge)
    }

    @Override
    public BowlingGameEntity getEntity() {
        return bowlingGameEntity;
    }

}
