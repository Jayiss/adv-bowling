package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity gameEntity;

    @Override
    public BowlingTurn[] getTurns() {
        BowlingTurn[] turns = new BowlingTurn[gameEntity.getTurnEntities().length];

        for ()

        return turns;
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

    @Override
    public BowlingTurn getFirstTurn() {
        return null;
    }
}
