package training.adv.bowling.impl.group2;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;

public class LinkedListImpl<T> implements LinkedList<BowlingTurn> {
    private BowlingTurn nextItem;
    private BowlingTurn previousItem;
    private BowlingTurnEntity bowlingTurnEntity;

    public LinkedListImpl(BowlingTurn previousItem) {
        this.previousItem = previousItem;
    }

    @Override
    public BowlingTurn getNextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.nextItem = item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return previousItem;
    }
}
