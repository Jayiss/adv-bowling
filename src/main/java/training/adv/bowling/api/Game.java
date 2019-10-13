package training.adv.bowling.api;

public interface Game<T extends Turn, E extends TurnEntity, G extends GameEntity<E>> extends Persistable<G> {
	int getTotalScore();
    int[] getScores();
	T[] getTurns();
	
	T newTurn();

	Boolean isGameFinished();

	StatusCode addScores(Integer... pins);
}
