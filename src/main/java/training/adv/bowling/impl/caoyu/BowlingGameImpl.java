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

    @Override
    public Boolean isGameFinished() {
        BowlingTurnImpl currentTurn = this.firstTurn;
        for (int i = 0; i < getMaxTurn() + 2 && null != currentTurn; i++) {
            if (null == currentTurn.getNextItem()) {
                if (i + 1 < getMaxTurn()) {
                    return false;
                } else if (i + 1 == getMaxTurn()) {//last turn miss, current turn is last turn
                    return currentTurn.isMiss();
                } else if (i + 1 == getMaxTurn() + 1) {//last turn spare or strike, current turn is bonus turn one
                    BowlingTurnImpl lastTurn = (BowlingTurnImpl) currentTurn.getPreviousItem();
                    boolean strikeFinished = lastTurn.isStrike() &&
                            currentTurn.getFirstPin() != null && currentTurn.getSecondPin() != null;
                    boolean spareFinished = lastTurn.isSpare() &&
                            currentTurn.getFirstPin() != null && currentTurn.getSecondPin() == null;
                    return strikeFinished || spareFinished;
                } else if (i + 1 == getMaxTurn() + 2) {//last turn strike, current turn is bonus turn two
                    BowlingTurnImpl lastTurn = (BowlingTurnImpl) ((BowlingTurnImpl) currentTurn.getPreviousItem()).getPreviousItem();
                    BowlingTurnImpl bonusTurnOne = (BowlingTurnImpl) currentTurn.getPreviousItem();
                    return lastTurn.isStrike() && null != bonusTurnOne.getFirstPin() && null == bonusTurnOne.getSecondPin() && null != currentTurn.getFirstPin() && null == currentTurn.getSecondPin();
                } else
                    return null;
            } else {
                currentTurn = (BowlingTurnImpl) currentTurn.getNextItem();
            }
        }
        return null;//Game not valid
    }

    //TODO
    @Override
    public StatusCode addScores(Integer... pins) {
        return null;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        assert turns != null;
        if (turns.length == 0) {
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
