package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.StatusCode;

public class BowlingGameImpl implements BowlingGame {
    @Override
    public BowlingGameEntity getEntity() {
        return null;
    }

    @Override
    public BowlingTurn[] getTurns() {
        return new BowlingTurn[0];
    }


    @Override
    public int[] getScores() {
        return new int[0];
    }

    @Override
    public int getTotalScore() {
        return 0;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return null;
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
}
