package training.adv.bowling.impl.ChenYong;

import training.adv.bowling.api.LinkedList;

public class LinkedListImpl implements LinkedList {
    private Object prev;
    private Object next;
    @Override
    public Object getNextItem() {
        return next;
    }

    @Override
    public Object getPreviousItem() {
        return prev;
    }

    @Override
    public void setNextItem(Object item) {
        next=item;
    }
}
