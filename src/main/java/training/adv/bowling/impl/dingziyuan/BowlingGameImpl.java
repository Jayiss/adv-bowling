package training.adv.bowling.impl.dingziyuan;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurn cursor;
    private final Integer MAX_TURN, MAX_PIN;

    public BowlingGameImpl(Integer MAX_TURN, Integer MAX_PIN) {
        this.MAX_PIN = MAX_PIN;
        this.MAX_TURN = MAX_TURN;
        this.bowlingGameEntity = new BowlingGameEntityImpl(MAX_TURN, MAX_PIN);
        this.cursor = new BowlingTurnImpl(MAX_TURN, MAX_PIN);
    }

    @Override
    public BowlingTurn getFirstTurn() {
        BowlingTurn current = cursor;
        while (current.getAsLinkedNode().getPreviousItem() != null) {
            current = current.getAsLinkedNode().getPreviousItem();
        }
        return current;
    }

    private BowlingTurn getLastTurn() {
        BowlingTurn current = cursor;
        while (current.getAsLinkedNode().getNextItem() != null) {
            current = current.getAsLinkedNode().getNextItem();
        }
        return current;
    }

    @Override
    public BowlingTurn[] getTurns() {
        //return copyTurns to avoid editing
        List<BowlingTurn> copyTurns = new ArrayList<>();
        BowlingTurn current = getFirstTurn();
        while (current != null) {
            copyTurns.add(current);
            current = current.getAsLinkedNode().getNextItem();
        }
        return copyTurns.toArray(new BowlingTurn[0]);
    }

    @Override
    public BowlingTurn newTurn() {
        return new BowlingTurnImpl(MAX_TURN, MAX_PIN);
    }

    @Override
    public Boolean isGameFinished() {

        List<BowlingTurn> newTurnsList = Arrays.asList(getTurns());

        //may be one or two more pins
        if (newTurnsList.size() >= MAX_TURN) {
            int extraPinsNum = countPinsfromTurns(newTurnsList.size() > MAX_TURN ? newTurnsList.get(MAX_TURN) : null,
                    newTurnsList.size() > MAX_TURN + 1 ? newTurnsList.get(MAX_TURN + 1) : null);

            if (newTurnsList.get(MAX_TURN - 1).isSpare())
                if (extraPinsNum > 1) {
                    return false;
                }
            if (newTurnsList.get(MAX_TURN - 1).isStrike())
                if (extraPinsNum > 2) {
                    return false;
                }
            if (newTurnsList.get(MAX_TURN - 1).isMiss())
                if (extraPinsNum > 0) {
                    return false;
                }
        }
        return true;

    }

    @Override
    public StatusCode addScores(Integer... pins) {

        BowlingTurn recoverNode = getLastTurn();
        Integer recoverPin1 = recoverNode.getFirstPin();
        Integer recoverPin2 = recoverNode.getSecondPin();

        int i = 0;

        Boolean isValid = true;
        while (i < pins.length) {
            BowlingTurn last = getLastTurn();

            //finished: create a new turn, then fill with data
            if (last.isFinished()) {
//                BowlingTurn next = newTurn();
                BowlingTurn next = new BowlingTurnImpl(MAX_TURN, MAX_PIN, null, last);
                next.addPins(pins[i]);
                last.getAsLinkedNode().setNextItem(next);
                isValid = next.isValid();
            } else {
                //unfinished: fill with data
                last.addPins(pins[i]);
                isValid = last.isValid();
            }
            if (!isValid)
                break;
            i++;
        }

        isValid = isValid & isGameFinished();

        if (!isValid) {
            recoverNode.getEntity().setFirstPin(recoverPin1);
            recoverNode.getEntity().setSecondPin(recoverPin2);
            recoverNode.getAsLinkedNode().setNextItem(null);
        }

        syncWithGameEntity();

        return null;
    }

    private void syncWithGameEntity() {
        BowlingTurn[] turns = getTurns();
        BowlingTurnEntity[] turnEntities = new BowlingTurnEntity[turns.length];
        for (int i = 0; i < turns.length; i++) {
            turnEntities[i] = turns[i].getEntity();
            turnEntities[i].setId(new TurnKeyImpl(String.valueOf(i), this.bowlingGameEntity.getId()));
        }
        this.bowlingGameEntity.setTurnEntities(turnEntities);
    }

    private Integer countPinsfromTurns(BowlingTurn... turns) {
        Integer cnt = 0;
        for (BowlingTurn turn : turns) {
            if (turn == null)
                continue;
            if (turn.getSecondPin() != null)
                cnt += 2;
            else
                cnt += 1;
        }
        return cnt;
    }


    @Override
    public BowlingGameEntity getEntity() {
        return bowlingGameEntity;
    }
}
