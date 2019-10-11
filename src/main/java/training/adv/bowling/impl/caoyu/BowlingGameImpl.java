package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame, BowlingGameEntity {
    private int gameId;
    private Integer maxTurn, maxPin;
    private BowlingTurnImpl firstTurn;

    @Override
    public BowlingTurn getFirstTurn() {
        return this.firstTurn;
    }

    @Override
    public Integer getMaxPin() {
        return maxPin;
    }

    @Override
    public Integer getId() {
        return gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId = id;
    }

    @Override
    public BowlingTurn[] getTurns() {
        ArrayList<BowlingTurn> result = new ArrayList<>();
        BowlingTurnImpl traverse = this.firstTurn;
        while (null != traverse.getNextItem()) {
            result.add(traverse);
            traverse = (BowlingTurnImpl) traverse.getNextItem();
        }
        return result.toArray(BowlingTurn[]::new);
    }

    //TODO
    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    //TODO
    @Override
    public Boolean isGameFinished() {
        return null;
    }

    //TODO
    @Override
    public StatusCode addScores(Integer... pins) {
        return null;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        if (null != turns || turns.length == 0) {
            this.firstTurn = new BowlingTurnImpl(null, null, null, this.getMaxPin());
            return;
        }

        //set first turn
        this.firstTurn = new BowlingTurnImpl(null, turns[0].getFirstPin(), turns[0].getSecondPin(), this.getMaxPin());
        BowlingTurnImpl currentTurn = this.firstTurn;
        TurnKey turnKey = new TurnKeyImpl(this.gameId);
        currentTurn.setId(turnKey);

        //set every other turn
        for (int i = 1; i < turns.length; i++) {
            //prepare next turn
            BowlingTurnImpl nextTurn = new BowlingTurnImpl(currentTurn, turns[i].getFirstPin(), turns[i].getSecondPin(),
                    this.getMaxPin());
            TurnKey nextTurnKey = new TurnKeyImpl(this.gameId);
            nextTurn.setId(nextTurnKey);

            //set next turn
            currentTurn.setNextItem(nextTurn);
            currentTurn = (BowlingTurnImpl) currentTurn.getNextItem();
        }
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        ArrayList<BowlingTurnImpl> bowlingTurnEntities = new ArrayList<>();
        BowlingTurnImpl currentTurn = this.firstTurn;
        while (currentTurn != null) {
            bowlingTurnEntities.add(currentTurn);
            currentTurn = (BowlingTurnImpl) currentTurn.getNextItem();
        }
        return bowlingTurnEntities.toArray(BowlingTurnEntity[]::new);
    }

    @Override
    public Integer getMaxTurn() {
        return this.maxTurn;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }
}
