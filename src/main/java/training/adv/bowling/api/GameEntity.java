package training.adv.bowling.api;

import java.io.Serializable;

public interface GameEntity<T extends TurnEntity> extends Entity<String> {
	void setTurnEntities(T[] turns);
	T[] getTurnEntities();
	
	Integer getMaxTurn();

}
