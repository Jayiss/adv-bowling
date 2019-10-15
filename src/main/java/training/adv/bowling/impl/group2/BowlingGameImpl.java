package training.adv.bowling.impl.group2;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame, BowlingGameEntity {
    private BowlingTurn firstTurn;
    private BowlingTurnEntity[] bowlingTurnEntities;
    private Integer gameId;
    private Integer max_pin;
    private Integer max_turn;

    public BowlingGameImpl(Integer gameId, Integer max_pin, Integer max_turn) {
        this.gameId = gameId;
        this.max_pin = max_pin;
        this.max_turn = max_turn;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return this.firstTurn;
    }

    @Override
    public BowlingTurn[] getTurns() {
        List<BowlingTurn> turnList = new ArrayList<>();
        if (this.firstTurn != null) {
            turnList.add(this.firstTurn);
            int index = 1;
            BowlingTurn myFirstTurn = this.firstTurn;
            while ((myFirstTurn = myFirstTurn.getAsLinkedNode().getNextItem()) != null) {
                if (index < 10) {
                    turnList.add(myFirstTurn);
                    ++index;
                }
            }
        }
        return turnList.toArray(new BowlingTurn[turnList.size()]);
    }

    @Override
    public BowlingTurn newTurn() {
        return new BowlingTurnImpl(0, 0, null);
    }

    private BowlingTurn getTurnByIndex(int index) {
        BowlingTurn result = this.firstTurn;
        for (int i = 1; i < index; i++) {
            if (result.getAsLinkedNode().getNextItem() != null) {
                result = result.getAsLinkedNode().getNextItem();
            }
        }
        return result;
    }

    @Override
    public Boolean isGameFinished() {
        int length = getLengthOfTurn();
        if (length == max_turn + 2 && getTurnByIndex(max_turn).isStrike() && getTurnByIndex(max_turn + 2).getSecondPin() == 0) {
            return true;
        } else if (length == max_turn + 1 && (getTurnByIndex(max_turn).isSpare() || getTurnByIndex(max_turn).isStrike() && getTurnByIndex(max_turn + 1).getFirstPin() != 0 && getTurnByIndex(max_turn + 1).getSecondPin() != 0)) {
            return true;
        } else {
            return length == max_turn && getTurnByIndex(max_turn).isMiss() && getTurnByIndex(max_turn).isFinished();
        }
    }

    private BowlingTurn getTheEndOfTurn(BowlingTurn currentTurn) {
        BowlingTurn end = currentTurn;
        if (currentTurn != null) {
            BowlingTurn temp = currentTurn.getAsLinkedNode().getNextItem();
            while (temp != null) {
                end = temp;
                temp = temp.getAsLinkedNode().getNextItem();
            }
            return end;
        }
        return null;
    }

    private Integer getLengthOfTurn() {
        BowlingTurn myFirstTurn = this.firstTurn;
        if (myFirstTurn != null) {
            Integer end = 1;
            while ((myFirstTurn = myFirstTurn.getAsLinkedNode().getNextItem()) != null) {
                ++end;
            }
            return end;
        }
        return 0;
    }

    private boolean isNewPinsAllowed(Integer... pins) {
        int lengthOfPins = pins.length;
        int firstTurnNullFlag = 0;
        int firstPin0Flag = 0;
        int secondPin0Flag = 0;

        if (this.firstTurn == null) {
            this.firstTurn = newTurn();
            firstTurnNullFlag = 1;
        }
        BowlingTurn currentTurn = getTheEndOfTurn(this.firstTurn);
        if (currentTurn.getFirstPin() == 0) firstPin0Flag = 1;
        if (currentTurn.getSecondPin() == 0) secondPin0Flag = 1;

        if (lengthOfPins == 0) return false;
        for (int i = 0; i < lengthOfPins; i++) {
            if (isGameFinished()) {
                //todo
                if (firstPin0Flag == 1) {
                    currentTurn.getEntity().setFirstPin(0);
                }
                if (secondPin0Flag == 1) {
                    currentTurn.getEntity().setSecondPin(0);
                }
                currentTurn.getAsLinkedNode().setNextItem(null);
                return false;
            }
            if (pins[i] < 0 || pins[i] > max_pin) {
                //
                if (firstPin0Flag == 1) {
                    currentTurn.getEntity().setFirstPin(0);
                }
                if (secondPin0Flag == 1) {
                    currentTurn.getEntity().setSecondPin(0);
                }
                currentTurn.getAsLinkedNode().setNextItem(null);
                return false;
            }
            if (pins[i].equals(max_pin)) {
                //
                if (getTheEndOfTurn(currentTurn).addPins(pins[i]).equals(StatusCodeImpl.FAILEDADD)) {
                    if (firstPin0Flag == 1) {
                        currentTurn.getEntity().setFirstPin(0);
                    }
                    if (secondPin0Flag == 1) {
                        currentTurn.getEntity().setSecondPin(0);
                    }
                    currentTurn.getAsLinkedNode().setNextItem(null);
                    return false;
                }
            } else if (i + 1 != lengthOfPins && pins[i] + pins[i + 1] > max_pin) return false;
                //传来的pins超长
            else {
                if (getTheEndOfTurn(currentTurn).addPins(pins[i]).equals(StatusCodeImpl.FAILEDADD)) {
                    if (firstPin0Flag == 1) {
                        currentTurn.getEntity().setFirstPin(0);
                    }
                    if (secondPin0Flag == 1) {
                        currentTurn.getEntity().setSecondPin(0);
                    }
                    currentTurn.getAsLinkedNode().setNextItem(null);
                    return false;
                }
            }
        }
        //
        currentTurn.getAsLinkedNode().setNextItem(null);
        if (firstPin0Flag == 1) {
            currentTurn.getEntity().setFirstPin(0);
        }
        if (secondPin0Flag == 1) {
            currentTurn.getEntity().setSecondPin(0);
        }
        if (firstTurnNullFlag == 1)
            firstTurn = null;
        return true;
    }

    /**
     * 如果新传入的pins是合法的，每次给最后的一个turn的addPins传一个pin用以组装
     *
     * @param pins 待组装成新的turn的数据
     * @return 成功/失败
     */
    @Override
    public StatusCode addScores(Integer... pins) {
        StatusCode statusCode = StatusCodeImpl.FAILEDADD;
        BowlingTurn curr = getTheEndOfTurn(firstTurn);
        if (isNewPinsAllowed(pins)) {
            if (firstTurn != null) {
//                System.out.println("not null first turn");
                for (int i = 0; i < pins.length; i++) {
                    if (getTheEndOfTurn(firstTurn).addPins(pins[i]).equals(StatusCodeImpl.SUCCESSADD)) {
                        statusCode = StatusCodeImpl.SUCCESSADD;
                    } else {
                        curr.getAsLinkedNode().setNextItem(null);
                        statusCode = StatusCodeImpl.FAILEDADD;
                    }
                }
            } else {
//                System.out.println("null first turn");
                BowlingTurn start = newTurn();
                statusCode = start.addPins(pins[0]).equals(StatusCodeImpl.SUCCESSADD) ? StatusCodeImpl.SUCCESSADD : StatusCodeImpl.FAILEDADD;
                firstTurn = start;
                for (int i = 1; i < pins.length; i++) {
                    if (getTheEndOfTurn(firstTurn).addPins(pins[i]).equals(StatusCodeImpl.SUCCESSADD)) {
                        statusCode = StatusCodeImpl.SUCCESSADD;
                    } else {
                        curr.getAsLinkedNode().setNextItem(null);
                        statusCode = StatusCodeImpl.FAILEDADD;
                    }
                }
            }
        }
        BowlingTurn temp = firstTurn;
        for (int i = 0; temp != null; ++i) {
            temp.getEntity().setId(new TurnKeyImpl(i, getId()));
            temp=temp.getAsLinkedNode().getNextItem();
        }
        return statusCode;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return this;
    }

    @Override
    public Integer getMaxPin() {
        return max_pin;
    }

    @Override
    public void setTurnEntities(BowlingTurnEntity[] turns) {
        bowlingTurnEntities = turns;
    }

    @Override
    public BowlingTurnEntity[] getTurnEntities() {
        return bowlingTurnEntities;
    }

    @Override
    public Integer getMaxTurn() {
        return max_turn;
    }

    @Override
    public Integer getId() {
        return this.gameId;
    }

    @Override
    public void setId(Integer id) {
        this.gameId = id;
    }
}
