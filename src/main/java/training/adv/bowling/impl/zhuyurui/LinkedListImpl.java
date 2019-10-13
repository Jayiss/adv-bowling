package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.LinkedList;

public class LinkedListImpl<T> implements LinkedList<BowlingTurn> {

    private BowlingTurn nextItem;
    private BowlingTurn previousItem;


    public LinkedListImpl(BowlingTurn previousItem){
        this(null,previousItem);
    }

    public LinkedListImpl(BowlingTurn nextItem,BowlingTurn previousItem){
        this.nextItem=nextItem;
        this.previousItem=previousItem;
    }

    @Override
    public BowlingTurn getNextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(BowlingTurn item) {
        this.nextItem =item;
    }

    @Override
    public BowlingTurn getPreviousItem() {
        return previousItem;
    }
}
