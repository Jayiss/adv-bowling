package training.adv.bowling.api;

public interface BowlingTurn extends Turn, Persistable<BowlingTurnEntity> {
	Boolean isStrike();
	Boolean isSpare();
	Boolean isMiss();
	Integer getFirstPin();
	Integer getSecondPin();
	
	StatusCode addPins(Integer... pins);
	
	LinkedList<BowlingTurn> getAsLinkedNode();
}
