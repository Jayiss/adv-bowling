package training.adv.bowling.api;

public interface BowlingGame extends Game<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> {
	BowlingTurn getFirstTurn();
}
