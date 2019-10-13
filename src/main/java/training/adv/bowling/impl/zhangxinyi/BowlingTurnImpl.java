package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;

public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn> {
    private BowlingTurnEntity turnE = new BowlingTurnEntityImpl();

    public BowlingTurnImpl(BowlingTurnImpl prev, Integer maxTurn) {
        ((BowlingTurnEntityImpl) turnE).setPreviousItem(prev);
        ((BowlingTurnEntityImpl) turnE).setMaxTurn(maxTurn);
    }

    @Override

    public Boolean isStrike() {
        if (isFinished()) {
            return getFirstPin().equals(getMaxPin());
        }
        return false;
    }

    @Override
    public Boolean isSpare() {
        if (getSecondPin() != null && isFinished()) {
            return getFirstPin() + getSecondPin() == getMaxPin();
        }
        return false;
    }

    @Override
    public Boolean isMiss() {
        return getSecondPin() != null && isFinished() && !isSpare();
    }

    @Override
    public Integer getFirstPin() {
        return turnE.getFirstPin();
    }

    @Override
    public Integer getSecondPin() {
        return turnE.getSecondPin();
    }

    @Override
    public void addPins(Integer... pins) {
        turnE.setFirstPin(pins[0]);
        if (pins.length == 2) {
            turnE.setSecondPin(pins[1]);
        }
    }

    @Override
    public LinkedList<BowlingTurn> getAsLinkedNode() {
        return this;
    }

    @Override
    public BowlingTurnEntity getEntity() {
        return turnE;
    }

    @Override
    public Boolean isFinished() {
        return !(getSecondPin() == null && getFirstPin() < getMaxPin());
    }

    @Override
    public Integer getScore() {
        int scoreAcc = 0;
        if (getOwnPos() <= ((BowlingTurnEntityImpl) turnE).getMaxTurn()) {
            scoreAcc += getSingleTurnScore(this);
            if (isSpare() && ((BowlingTurnEntityImpl) turnE).getNextItem() != null) {
                scoreAcc += ((BowlingTurnEntityImpl) turnE).getNextItem().getFirstPin();
            }
            if (isStrike() && ((BowlingTurnEntityImpl) turnE).getNextItem() != null) {
                if (((BowlingTurnEntityImpl) ((BowlingTurnEntityImpl) turnE).getNextItem().getEntity()).getNextItem()
                        == null) {
                    scoreAcc += getSingleTurnScore(((BowlingTurnEntityImpl) turnE).getNextItem());
                } else {
                    if (((BowlingTurnEntityImpl) turnE).getNextItem().isStrike()) {
                        scoreAcc += ((BowlingTurnEntityImpl) turnE).getMaxTurn() + ((BowlingTurnEntityImpl)
                                ((BowlingTurnEntityImpl) turnE).getNextItem().getEntity()).getNextItem().getFirstPin();
                    } else {
                        if (((BowlingTurnEntityImpl) turnE).getNextItem().isFinished()) {
                            scoreAcc += getSingleTurnScore(((BowlingTurnEntityImpl) turnE).getNextItem());
                        } else {
                            scoreAcc += ((BowlingTurnEntityImpl) turnE).getNextItem().getFirstPin() +
                                    ((BowlingTurnEntityImpl) ((BowlingTurnEntityImpl) turnE).getNextItem().getEntity()).
                                            getNextItem().getFirstPin();
                        }
                    }
                }
            }
        }
        System.out.println("firstPin: " + turnE.getFirstPin() + "  secondPin: " + turnE.getSecondPin());
        System.out.println("Score of this turn: " + scoreAcc);
        return scoreAcc;
    }

    private Integer getSingleTurnScore(BowlingTurnImpl turn) {
        if (turn.getSecondPin() != null) {
            return turn.getFirstPin() + turn.getSecondPin();
        } else {
            return turn.getFirstPin();
        }
    }

    private Integer getOwnPos() {
        BowlingTurnImpl now = this;
        int acc = 1;
        while (now.getPreviousItem() != null) {
            now = now.getPreviousItem();
            acc += 1;
        }
        return acc;
    }

    @Override
    public Boolean isValid() {
        if (getSecondPin() == null) {
            return getFirstPin() >= 0 && getFirstPin() <= getMaxPin();
        } else {
            Integer first = getFirstPin();
            Integer second = getSecondPin();
            return first >= 0 && first <= getMaxPin() && second >= 0
                    && second <= getMaxPin() && first + second <= getMaxPin();
        }
    }

    @Override
    public BowlingTurnImpl getNextItem() {
        return ((BowlingTurnEntityImpl) turnE).getNextItem();
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        ((BowlingTurnEntityImpl) turnE).setNextItem((BowlingTurnImpl) item);
    }

    @Override
    public BowlingTurnImpl getPreviousItem() {
        return ((BowlingTurnEntityImpl) turnE).getPreviousItem();
    }

    public Integer getMaxPin() {
        return ((BowlingTurnEntityImpl) getEntity()).getMaxPin();
    }
}
