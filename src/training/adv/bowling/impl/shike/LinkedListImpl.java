package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.LinkedList;

public class LinkedListImpl implements LinkedList<BowlingTurn>{

	BowlingTurn nextItem;
	BowlingTurn previousItem;
	
	public LinkedListImpl(BowlingTurn previousItem) {
		this.previousItem=previousItem;
	}
	
	@Override
	public BowlingTurn getNextItem() {
		// TODO Auto-generated method stub
		return this.nextItem;
	}

	@Override
	public void setNextItem(BowlingTurn item) {
		this.nextItem=item;
	}

	@Override
	public BowlingTurn getPreviousItem() {
		// TODO Auto-generated method stub
		return this.previousItem;
	}



}
