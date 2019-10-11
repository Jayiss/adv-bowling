package training.adv.bowling.impl.group2;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

public class BowlingGameImpl implements BowlingGame {
    @Override
    public BowlingTurn getFirstTurn() {
        return null;
    }

    @Override
    public int getTotalScore() {
        return 0;
    }

    @Override
    public int[] getScores() {
        return new int[0];
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
