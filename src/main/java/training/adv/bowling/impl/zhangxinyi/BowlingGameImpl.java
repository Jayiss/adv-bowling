package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractGame;

import java.util.ArrayList;
import java.util.List;

public class BowlingGameImpl extends AbstractGame<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> implements BowlingGame {
    private BowlingGameEntity gameE = new BowlingGameEntityImpl();
    private BowlingTurn firstT;


    @Override
    public BowlingTurn getFirstTurn() {
        return firstT;
    }

    @Override
    // Forbidden to use.
    public BowlingTurn[] getTurns() {
        LinkedList<BowlingTurn> list = firstT.getAsLinkedNode();
        return convertLinkedList(list);
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
    public BowlingTurn newTurn() {
        firstT = new BowlingTurnImpl(null);
        ((BowlingTurnEntityImpl) firstT.getEntity()).setMaxPin(gameE.getMaxPin());
        return firstT;
    }

    public BowlingTurn newTurn(BowlingTurnImpl prev) {
        BowlingTurn turn = new BowlingTurnImpl(prev);
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
        if (temp != null) {
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
        //TODO
        //Use LinkedList
        if (!isGameFinished()) {
            int len = getLength();
            BowlingTurn lastNormalT = null;
            if (len >= gameE.getMaxTurn()) {
                lastNormalT = getPos(gameE.getMaxTurn() - 1);
            }
            int index = 0;
            if (len >= 1) {
                BowlingTurn lastT = getPos(len - 1);
                if (!lastT.isFinished() && len <= gameE.getMaxTurn()) {
                    BowlingTurn temp = newTurn();
                    temp.addPins(lastT.getFirstPin(), pins[0]);
                    if (temp.isValid()) {
                        index += 1;
                        lastT.getEntity().setSecondPin(pins[0]);
                        if (len == gameE.getMaxTurn()) {
                            lastNormalT = temp;
                        }
                    } else {
                        temp = null;
                        return new StatusCodeImpl("Invalid");
                    }
                }
            }
            BowlingTurn lastT = getPos(len - 1);
            while (index < pins.length) {
                if (pins[index] == gameE.getMaxPin() || index == pins.length - 1 || len >= gameE.getMaxTurn()) {
                    BowlingTurn temp = newTurn((BowlingTurnImpl) lastT);
                    temp.addPins(pins[index]);
                    if (temp.isValid()) {

                        len += 1;
                        if (len == gameE.getMaxTurn()) {
                            lastNormalT = temp;
                        }
                    }
                }
            }
        }
        return null;
    }

    @Override
    public BowlingGameEntity getEntity() {
        return gameE;
    }
}
