package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame, BowlingGameEntity {
    private int gameId;
    private Integer maxTurn, maxPin;
    private BowlingTurnImpl firstTurn;

    public BowlingGameImpl(Integer maxTurn, Integer maxPin) {
        this.maxTurn = maxTurn;
        this.maxPin = maxPin;
        gameId = UidUtil.getNewGameId();
        firstTurn = new BowlingTurnImpl(gameId, null, null, null, this.maxTurn, this.maxPin);
    }

    public BowlingGameImpl(Integer gameId, Integer maxTurn, Integer maxPin) {
        this.gameId = gameId;
        this.maxTurn = maxTurn;
        this.maxPin = maxPin;
    }

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
//        for (int i = 0; i < this.maxTurn - 1 && null != traverse.getNextItem(); i++) {
//            result.add(traverse);
//            traverse = traverse.getNextItem();
//        }
        while (null != traverse.getNextItem()) {
            result.add(traverse);
            traverse = traverse.getNextItem();
        }
        result.add(traverse);
        return result.toArray(new BowlingTurn[0]);
    }

    //TODO
    @Override
    public BowlingTurn newTurn() {
        return null;
    }

    @Override
    public Boolean isGameFinished() {
        return firstTurn.isGameFinished();
//        BowlingTurnImpl currentTurn = this.firstTurn;
//        for (int i = 0; i < getMaxTurn() + 2 && null != currentTurn; i++) {
//            if (null == currentTurn.getNextItem()) {
//                if (i + 1 < getMaxTurn()) {
//                    return false;
//                } else if (i + 1 == getMaxTurn()) {//last turn miss, current turn is last turn
//                    return currentTurn.isMiss();
//                } else if (i + 1 == getMaxTurn() + 1) {//last turn spare or strike, current turn is bonus turn one
//                    BowlingTurnImpl lastTurn = currentTurn.getPreviousItem();
//                    boolean strikeFinished = lastTurn.isStrike() &&
//                            currentTurn.getFirstPin() != null && currentTurn.getSecondPin() != null;
//                    boolean spareFinished = lastTurn.isSpare() &&
//                            currentTurn.getFirstPin() != null && currentTurn.getSecondPin() == null;
//                    return strikeFinished || spareFinished;
//                } else if (i + 1 == getMaxTurn() + 2) {//last turn strike, current turn is bonus turn two
//                    BowlingTurnImpl lastTurn = currentTurn.getPreviousItem().getPreviousItem();
//                    BowlingTurnImpl bonusTurnOne = currentTurn.getPreviousItem();
//                    return lastTurn.isStrike() && null != bonusTurnOne.getFirstPin() && null == bonusTurnOne.getSecondPin() && null != currentTurn.getFirstPin() && null == currentTurn.getSecondPin();
//                } else
//                    return null;
//            } else {
//                currentTurn = currentTurn.getNextItem();
//            }
//        }
//        return null;//Game not valid
    }

    //TODO
    @Override
    public StatusCode addScores(Integer... pins) {
        for (Integer pin :
                pins) {
            if (null == pin || pin < 0 || pin > this.maxPin) {
                return BowlingAddScoresStatusCode.valueOf("INVALID_PIN");
            }
        }

        if (this.isGameFinished()) {
            return BowlingAddScoresStatusCode.valueOf("GAME_ALREADY_FINISHED");
        } else if (pins.length == 0) {
            return BowlingAddScoresStatusCode.valueOf("SUCCESSFUL");
        } else {
            return this.firstTurn.addPins(pins);
        }
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        assert turns != null;
        if (turns.length == 0) {
            this.firstTurn = new BowlingTurnImpl(gameId, null, null, null, this.getMaxTurn(), this.getMaxPin());
            return;
        }

        //set first turn
        this.firstTurn = new BowlingTurnImpl(gameId, null, null, null, this.getMaxTurn(),
                this.getMaxPin());
        for (BowlingTurnEntity currentTurn :
                turns) {
            if (null != currentTurn.getFirstPin()) {
                this.addScores(currentTurn.getFirstPin());
            }
            if (null != currentTurn.getSecondPin()) {
                this.addScores(currentTurn.getSecondPin());
            }
        }
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        ArrayList<BowlingTurnImpl> bowlingTurnEntities = new ArrayList<>();
        BowlingTurnImpl currentTurn = this.firstTurn;
        while (currentTurn != null) {
            bowlingTurnEntities.add(currentTurn);
            currentTurn = currentTurn.getNextItem();
        }
        return bowlingTurnEntities.toArray(new BowlingTurnEntity[0]);
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
