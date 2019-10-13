package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity gameE = new BowlingGameEntityImpl(this);
    private BowlingTurn firstT;

    public void setfirstT(BowlingTurn firstT) {
        this.firstT = firstT;
    }

    @Override
    public BowlingTurn getFirstTurn() {
        return firstT;
    }

    @Override
    // Forbidden to use.
    public BowlingTurn[] getTurns() {
        if (firstT != null) {
            LinkedList<BowlingTurn> list = firstT.getAsLinkedNode();
            return convertLinkedList(list);
        } else {
            return new BowlingTurn[0];
        }
    }

    private BowlingTurn[] convertLinkedList(LinkedList<BowlingTurn> list) {
        List<BowlingTurn> arrayL = new ArrayList<BowlingTurn>();
        while (list != null) {
            arrayL.add((BowlingTurn) list);
            list = (LinkedList<BowlingTurn>) list.getNextItem();
        }
        return arrayL.toArray(new BowlingTurn[0]);
    }

    @Override
    // Only used for firstT.
    public BowlingTurn newTurn() {
        firstT = new BowlingTurnImpl(null, gameE.getMaxTurn());
        ((BowlingTurnEntityImpl) firstT.getEntity()).setMaxPin(gameE.getMaxPin());
        return firstT;
    }

    public BowlingTurn newTurn(BowlingTurnImpl prev) {
        BowlingTurn turn = new BowlingTurnImpl(prev, gameE.getMaxTurn());
        ((BowlingTurnEntityImpl) turn.getEntity()).setMaxPin(gameE.getMaxPin());
        return turn;
    }

    @Override
    public Boolean isGameFinished() {
        //Use LinkedList
        int len = getLength();
        if (len >= gameE.getMaxTurn() + 2) {
            return true;
        } else if (len == gameE.getMaxTurn() + 1 && getPos(gameE.getMaxTurn() - 1).isSpare()) {
            return true;
        } else {
            return len == gameE.getMaxTurn() && getPos(gameE.getMaxTurn() - 1).isMiss();
        }
    }

    private Integer getLength() {
        int len = 0;
        BowlingTurn temp = firstT;
        while (temp != null) {
            len += 1;
            temp = ((BowlingTurnImpl) temp).getNextItem();
        }
        return len;
    }

    private BowlingTurn getPos(Integer n) {
        BowlingTurn temp = firstT;
        while (n != 0 && temp != null) {
            n -= 1;
            temp = ((BowlingTurnImpl) temp).getNextItem();
        }
        return temp;
    }

    @Override
    public StatusCode addScores(Integer... pins) {
        //First Edition Complete
        //Use LinkedList
        System.out.println();
        System.out.println("Now Length: " + getLength());
        boolean hasSetSecondPin = false;
        BowlingTurnImpl revertPoint = (BowlingTurnImpl) getPos(getLength() - 1);
        if (!isGameFinished()) {
            int len = getLength();
            BowlingTurn lastNormalT = null;
            if (len >= gameE.getMaxTurn()) {
                lastNormalT = getPos(gameE.getMaxTurn() - 1);
            }
            int index = 0;
            BowlingTurn lastT = null;
            if (len >= 1) {
                lastT = getPos(len - 1);
                if (!lastT.isFinished() && len <= gameE.getMaxTurn()) {
                    BowlingTurn temp = newTurn(null);
                    temp.addPins(lastT.getFirstPin(), pins[0]);
                    if (temp.isValid()) {
                        index += 1;
                        lastT.getEntity().setSecondPin(pins[0]);
                        hasSetSecondPin = true;
                        if (len == gameE.getMaxTurn()) {
                            lastNormalT = temp;
                        }
                    } else {
                        temp = null;
                        revert(revertPoint, hasSetSecondPin);
                        return StatusCodeImpl.INVALID;
                    }
                }
            }
            while (index < pins.length) {
                if (pins[index].equals(gameE.getMaxPin()) || index == pins.length - 1 || len >= gameE.getMaxTurn()) {
                    BowlingTurn temp;
                    if (lastT != null) {
                        temp = newTurn((BowlingTurnImpl) lastT);
                    } else {
                        temp = newTurn();
                    }
                    temp.addPins(pins[index]);
                    if (temp.isValid()) {
                        index += 1;
                        len += 1;
                        if (lastT != null) {
                            ((BowlingTurnImpl) lastT).setNextItem(temp);
                        }
                        lastT = temp;
                        if (len == gameE.getMaxTurn()) {
                            lastNormalT = temp;
                        }
                    } else {
                        temp = null;
                        revert(revertPoint, hasSetSecondPin);
                        return StatusCodeImpl.INVALID;
                    }
                } else if (index != pins.length - 1) {
                    BowlingTurn temp;
                    if (lastT != null) {
                        temp = newTurn((BowlingTurnImpl) lastT);
                    } else {
                        temp = newTurn();
                    }
                    temp.addPins(pins[index], pins[index + 1]);
                    if (temp.isValid()) {
                        len += 1;
                        if (lastT != null) {
                            ((BowlingTurnImpl) lastT).setNextItem(temp);
                        }
                        lastT = temp;
                        index += 2;
                        if (len == gameE.getMaxTurn()) {
                            lastNormalT = temp;
                        }
                    } else {
                        temp = null;
                        revert(revertPoint, hasSetSecondPin);
                        return StatusCodeImpl.INVALID;
                    }
                }
            }
            if (lastNormalT == null || lastNormalT.isMiss()) {
                if (len > gameE.getMaxTurn()) {
                    revert(revertPoint, hasSetSecondPin);
                    return StatusCodeImpl.TOOMUCH;
                }
            } else if (lastNormalT.isSpare()) {
                if (len > gameE.getMaxTurn() + 1) {
                    revert(revertPoint, hasSetSecondPin);
                    return StatusCodeImpl.TOOMUCH;
                }
            } else {
                if (len > gameE.getMaxTurn() + 2) {
                    revert(revertPoint, hasSetSecondPin);
                    return StatusCodeImpl.TOOMUCH;
                }
            }
        } else {
            return StatusCodeImpl.TOOMUCH;
        }
        return StatusCodeImpl.SUCCESS;
    }

    private void revert(BowlingTurnImpl revertPoint, boolean hasSetSecondPin) {
        if (revertPoint == null) {
            firstT = null;
        } else {
            revertPoint.setNextItem(null);
            if (hasSetSecondPin) {
                revertPoint.getEntity().setSecondPin(null);
            }
        }
    }

    @Override
    public BowlingGameEntity getEntity() {
        return gameE;
    }
}
