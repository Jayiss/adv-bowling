package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity,BowlingGameEntity> implements BowlingGame {

    @Override
    public BowlingTurn getFirstTurn() {
        return null;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }

    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    @Override
    public Boolean isGameFinished() {
        return null;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        return null;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return null;
    }
}
