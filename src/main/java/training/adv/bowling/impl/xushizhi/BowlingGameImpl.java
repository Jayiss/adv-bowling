package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {

    private static final int MAX_PIN = 10;  // Set default bowling game max pin to 10
    private static final int MAX_TURN = 10;  // Set default bowling game max turn to 10
    private static StatusCode statusCode = StatusCodeImpl.ACCEPTED;  // Preset to ACCEPTED
    private BowlingGameEntity bowlingGameEntity;
    private BowlingTurn header;

    BowlingGameImpl(BowlingGameEntity bowlingGameEntity, BowlingTurn header) {
        this.bowlingGameEntity = bowlingGameEntity;
        this.header = header;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        BowlingTurn pointer = this.header;
        // Linked List
        while (pointer.getAsLinkedNode().getPreviousItem() != null) {
            pointer = pointer.getAsLinkedNode().getPreviousItem();
        }
        return pointer.getAsLinkedNode().getNextItem();
    }

    @Override
    public BowlingTurn[] getTurns() {
        if (getFirstTurn() == null) {
            return new BowlingTurn[0];
        }

        BowlingTurn currNode = getFirstTurn();  // Pointer of current LinkedList node
        List<BowlingTurn> turnList = new LinkedList<>();
        // Add current node(each turn) to turn list
        while (currNode != null) {
            turnList.add(currNode);
            currNode = currNode.getAsLinkedNode().getNextItem();  // Move to the next one
        }

        return turnList.toArray(new BowlingTurn[0]);
    }

    @Override
    public Boolean isGameFinished() {
        if (getFirstTurn() == null) {
            return false;
        }

        int counter = 1;  // If current game has reached preset max turn
        BowlingTurn currNode = getFirstTurn();
        while (currNode.getAsLinkedNode().getNextItem() != null) {
            if (!currNode.isFinished()) {  // Required turns (MAX_TURN) must be finished
                return false;
            }
            if (counter < MAX_TURN) {
                counter++;
            } else {
                break;
            }
            currNode = currNode.getAsLinkedNode().getNextItem();  // Move to the next one
        }
        if (!currNode.isFinished() || counter < MAX_TURN) {
            return false;
        }

        BowlingTurn nextNode = currNode.getAsLinkedNode().getNextItem();
        if (currNode.isStrike()) {
            if (nextNode == null) {
                return false;
            } else if (nextNode.isStrike()) {  // Poss 1: 11th turn is STRIKE
                return (nextNode.getAsLinkedNode().getNextItem() != null);
            } else {  // 11th turn is SPARE / 11th turn is MISS
                return (nextNode.isFinished());
            }
        } else if (currNode.isSpare()) {  // 10th turn is SPARE
            if (currNode.getAsLinkedNode().getNextItem() != null) {
                return (currNode.getAsLinkedNode().getNextItem().getFirstPin() != null);
            }
            return false;
        }
        return true;
    }

    @Override
    public BowlingTurn newTurn() {
        return new BowlingTurnImpl(MAX_PIN, MAX_TURN, header);
    }

    @Override
    public BowlingGameEntity getEntity() {
        BowlingTurn[] bowlingTurns = getTurns();
        List<BowlingTurnEntity> turnEntities = new ArrayList<>();

        // Generate corresponding turn entities
        int id_turn = 1;  // DB TURN PK
        for (BowlingTurn turn : bowlingTurns) {
            turnEntities.add(turn.getEntity());
            turn.getEntity().setId(new TurnKeyImpl(id_turn++, bowlingGameEntity.getId()));  // id_turn, id_game
        }

        bowlingGameEntity.setTurnEntities(turnEntities.toArray(new BowlingTurnEntity[0]));
        return bowlingGameEntity;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        BowlingTurn headBackUp = header;
        if (header.getAsLinkedNode().getPreviousItem() == null && pins.length != 0) {
            header = newTurn();
        }

        for (Integer pin : pins) {
            if (isGameFinished()) {
                statusCode = StatusCodeImpl.GAMEISFINISHED;
                break;
            } else if (header.isFinished()) {  // Move to the next turn
                header = newTurn();
            }

            statusCode = header.addPins(pin);  // Reload Status Code
            if (statusCode != StatusCodeImpl.ACCEPTED) {
                break;
            }
        }

        // Rollback LinkedList if current game is invalid
        if (statusCode != StatusCodeImpl.ACCEPTED) {
            System.out.println("Error ! Current bowling game is NOT valid ! Status Code - " + statusCode);
            System.out.println("Rolling Back...\n");

            header = headBackUp;  // Set to original header
            header.getAsLinkedNode().setNextItem(null);  // Set next pointer to null
            if (headBackUp.isFinished()) {  // Set header secondPin to null
                header.getEntity().setSecondPin(null);
            }
        }

        return statusCode;
    }
}
