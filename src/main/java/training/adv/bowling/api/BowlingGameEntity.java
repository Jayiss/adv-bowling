package training.adv.bowling.api;

public interface BowlingGameEntity extends GameEntity<BowlingTurnEntity> {

    Integer getMaxPin();
}
