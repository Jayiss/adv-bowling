package wangbingchao;

import training.adv.bowling.api.LinkedList;

public class LinkedListImpl implements LinkedList {

	private Object preItem;
	private Object nextItem;
	public LinkedListImpl(Object preItem) {
		this.preItem = preItem;
	}
	@Override
	public Object getNextItem() {
		// TODO Auto-generated method stub
		return nextItem;
	}

	@Override
	public void setNextItem(Object item) {
		// TODO Auto-generated method stub
		this.nextItem = item;
	}

	@Override
	public Object getPreviousItem() {
		// TODO Auto-generated method stub
		return preItem;
	}

}
