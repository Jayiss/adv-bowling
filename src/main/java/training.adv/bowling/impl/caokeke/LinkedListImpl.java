package training.adv.bowling.impl.caokeke;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.LinkedList;

public class LinkedListImpl implements LinkedList<BowlingTurn> {

    private BowlingTurn next;
    private BowlingTurn pre;

    public LinkedListImpl(BowlingTurn pre) { this.pre=pre; }
    @Override
    public BowlingTurn getNextItem() {
        return next;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.next=item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return pre;
    }
}
