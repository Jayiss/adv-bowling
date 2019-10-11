package training.adv.bowling.impl.fanjuncai;

import training.adv.bowling.api.LinkedList;

public class LinkedListImpl implements LinkedList {


    private Object nextItem;
    private Object previousItem;


    @Override
    public Object getNextItem() {
        return nextItem;
    }

    @Override
    public void setNextItem(Object item) {
        this.nextItem = item;
    }

    @Override
    public Object getPreviousItem() {
        return this.previousItem;
    }
}
