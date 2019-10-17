package training.adv.bowling.api;

public interface LinkedList<T> {
	T getNextItem();
	void setNextItem(T item);
	
	T getPreviousItem();
}
