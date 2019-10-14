package training.adv.bowling.impl.yangxiaotong;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.LinkedList;

public class LinkedListImpl implements LinkedList<BowlingTurn> {

    private BowlingTurn previous=null;
    private BowlingTurn next=null;

    public LinkedListImpl(BowlingTurn previous){
        this(previous,null);
    }
    public LinkedListImpl(BowlingTurn previous,BowlingTurn next){
        this.previous=previous;
        this.next=next;
    }

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
        return previous;
    }
}
