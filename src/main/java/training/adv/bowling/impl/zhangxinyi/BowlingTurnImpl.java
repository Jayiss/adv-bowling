package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;

public class BowlingTurnImpl implements BowlingTurn, LinkedList<BowlingTurn> {
    private BowlingTurnEntity turnE = new BowlingTurnEntityImpl();

    public BowlingTurnImpl(BowlingTurnImpl prev) {
        ((BowlingTurnEntityImpl)turnE).setPreviousItem(prev);
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
    // Useless?
    public void addPins(Integer... pins) {

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
        //TODO
        return 0;
    }

    @Override
    public Boolean isValid() {
        if (getSecondPin() == null) {
            return getFirstPin() >= 0 && getFirstPin() <= getMaxPin();
        } else {
            Integer first = getFirstPin();
            Integer second = getSecondPin();
            return first >= 0 && first <= getMaxPin() && second >= 0
                    && second <= getMaxPin();
        }
    }

    @Override
    public BowlingTurnImpl getNextItem() {
        //TODO
        return null;
    }

    @Override
    public void setNextItem(BowlingTurn item) {

    }

    @Override
    public BowlingTurnImpl getPreviousItem() {
        //TODO
        return null;
    }

    public Integer getMaxPin() {
        return ((BowlingTurnEntityImpl) getEntity()).getMaxPin();
    }
}
