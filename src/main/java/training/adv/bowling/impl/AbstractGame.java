package training.adv.bowling.impl;

import java.util.Arrays;

import training.adv.bowling.api.Game;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.Turn;
import training.adv.bowling.api.TurnEntity;

public abstract class AbstractGame<T extends Turn, E extends TurnEntity, G extends GameEntity<E>> implements Game<T, E, G> {

	@Override
	public final int getTotalScore() {
		return Arrays.stream(this.getScores()).sum();
	}

	@Override
	public final int[] getScores() {
		return Arrays.stream(this.getTurns()).mapToInt(t -> t.getScore()).toArray();
	}

}
