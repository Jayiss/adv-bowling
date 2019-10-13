package training.adv.bowling.impl;

import java.util.Arrays;

import training.adv.bowling.api.*;

public abstract class AbstractGame<T extends Turn, E extends TurnEntity, G extends GameEntity<E>> implements Game<T, E, G> {

	@Override
	public final int getTotalScore() {
		return Arrays.stream(this.getScores()).sum();
	}

	@Override
	public final int[] getScores() {
		T[] turns=this.getTurns();
		int[] i=Arrays.stream(this.getTurns()).mapToInt(t -> t.getScore()).toArray();
		return i;
		//return Arrays.stream(this.getTurns()).mapToInt(t -> t.getScore()).toArray();
	}

}
