package training.adv.bowling.api;
//不同
public interface BowlingGame extends Game<BowlingTurn, BowlingTurnEntity, BowlingGameEntity> {
	 BowlingTurn getFirstTurn();
}
