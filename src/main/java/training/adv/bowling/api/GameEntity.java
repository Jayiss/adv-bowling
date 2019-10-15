package training.adv.bowling.api;

public interface GameEntity<T extends TurnEntity> extends Entity<Integer> {
	void setTurnEntities(T[] turns);
	T[] getTurnEntities();
	
	Integer getMaxTurn();

}
