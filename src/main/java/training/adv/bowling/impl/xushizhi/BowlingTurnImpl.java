package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;
import training.adv.bowling.api.StatusCode;

// Using LinkedList as current game's turns record -> Each node is a turn
public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn> {

    private static int MAX_PIN;
    private static int MAX_TURN;

    private BowlingTurnEntity turnEntity;
    private BowlingTurn next, prev;  // Pointer of current LinkedList
    private StatusCode statusCode;

    BowlingTurnImpl(Integer maxPin, Integer maxTurn, BowlingTurn preTurn) {
        this.turnEntity = new BowlingTurnEntityImpl();
        MAX_PIN = maxPin;
        MAX_TURN = maxTurn;
        prev = preTurn;

        // Set previous turn next node to current node(turn) (if previous turn exists)
        if (preTurn != null) {
            preTurn.getAsLinkedNode().setNextItem(this);
        }
    }

    // Check current turn's 1st & 2nd pin's range -> > 0 && < MAX_PIN + 1
    private Boolean checkPinRange(Integer tempPin) {
        if (tempPin < 0) {
            statusCode = StatusCodeImpl.NEGATIVE;
            return false;
        }
        if (tempPin > MAX_PIN) {
            statusCode = StatusCodeImpl.OVERSIZE;
            return false;
        }
        return true;
    }

    // Check current turn's 1stPin + 2ndPin < MAX_PIN + 1
    private Boolean checkPinSum(Integer tempPin_1, Integer tempPin_2) {
        if (tempPin_1 + tempPin_2 > MAX_PIN) {
            statusCode = StatusCodeImpl.SUMNOTALLOWED;
            return false;
        }
        return true;
    }

    @Override
    // Check if current turn is valid & set the corresponding status code
    public Boolean isValid() {
        Integer firstPin = turnEntity.getFirstPin(), secondPin = turnEntity.getSecondPin();
        statusCode = StatusCodeImpl.ACCEPTED;

        if (checkPinRange(firstPin)) {
            if (isFinished()) {
                if (!(checkPinRange(secondPin))) {
                    return false;
                }
                return checkPinSum(firstPin, secondPin);
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public Boolean isStrike() {
        if (isFinished()) {
            return turnEntity.getFirstPin() == MAX_PIN;
        }
        return false;
    }

    @Override
    public Boolean isSpare() {
        if (isFinished() && turnEntity.getSecondPin() != null) {
            return (turnEntity.getFirstPin() + turnEntity.getSecondPin() == MAX_PIN);
        }
        return false;
    }

    @Override
    public Boolean isMiss() {
        if (isFinished() && turnEntity.getSecondPin() != null) {
            return (!isSpare());
        }
        return false;
    }

    @Override
    // Check if current turn has finished
    public Boolean isFinished() {
        return (turnEntity.getFirstPin() != null && turnEntity.getSecondPin() != null);
    }

    @Override
    public Integer getFirstPin() {
        return turnEntity.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return turnEntity.getSecondPin();
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return turnEntity;
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return this;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return this.prev;
    }

    @Override
    public BowlingTurn getNextItem() {
        return this.next;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.next = item;
    }

    @Override
    // Add new pins to current turn
    public StatusCode addPins(Integer... pins) {
        // Scenario I: Current new pin is current turn 2nd toss -> {1stPin, newPins[0]}
        if (turnEntity.getFirstPin() != null) {
            turnEntity.setSecondPin(pins[0]);
            // Scenario II: Current new pin will be the 1st toss
        } else {
            turnEntity.setFirstPin(pins[0]);
            // Scenario II/1: Current turn is STRIKE -> {newPins[0], 0}
            if (pins[0] == MAX_PIN) {
                turnEntity.setSecondPin(0);
            }
        }
        // Check if the new turn is valid & Reset the StatusCode
        if (!isValid()) {
            System.out.println("Error ! New turn is NOT valid !");
        }
        return statusCode;
    }

    private int calcTurnScore(BowlingTurnEntity turnEntity) {
        if (turnEntity.getSecondPin() != null) {
            return (turnEntity.getFirstPin() + turnEntity.getSecondPin());
        } else {
            return turnEntity.getFirstPin();
        }
    }

    @Override
    // Return current turn score
    public Integer getScore() {
        int counterTurn = 1;
        BowlingTurn currTurn = this;
        while (currTurn != null) {
            counterTurn++;
            currTurn = ((BowlingTurnImpl) currTurn).prev;

            // If has reached default max turn
            if (counterTurn > MAX_TURN + 2) {
                return 0;
            }
        }

        // Scenario I: Current turn is STRIKE
        if (isStrike()) {
            // Scenario I/1: 11th turn is STRIKE -> 2 * MAX_PIN + Next but one turn 1st toss
            if (next != null && next.isStrike()) {
                return MAX_PIN + MAX_PIN + (next.getAsLinkedNode().getNextItem() == null ? 0 : next.getAsLinkedNode().getNextItem().getFirstPin());
                // Scenario I/2: 11th turn is NOT STRIKE -> MAX_PIN + Next turn 1st & 2nd toss
            } else if (next != null && (next.isSpare() || next.isMiss())) {
                return MAX_PIN + next.getFirstPin() + next.getSecondPin();
                // Scenario I/3: 11th turn is NOT finished -> MAX_PIN + Next turn 1st toss
            } else {
                return MAX_PIN + (next == null ? 0 : next.getFirstPin());
            }
            // Scenario II: Current turn is SPARE -> MAX_PIN + Next 1 toss
        } else if (isSpare()) {
            return MAX_PIN + (next == null ? 0 : next.getFirstPin());
            // Scenario III: Current turn is MISS -> 1st Toss + 2nd Toss
        } else if (isMiss()) {
            return turnEntity.getFirstPin() + (turnEntity.getSecondPin());
        }
        return turnEntity.getFirstPin();
    }
}
