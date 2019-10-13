package wangbingchao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.TurnKey;

public class DefensiveTest {
	
	
	@Test
	public void testTurnsArrayDefensive() {
		BowlingGame game = new BowlingGameImpl();
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
		
		BowlingTurn[] turns = game.getTurns();
		for (int i = 0; i < turns.length; i++) {
			turns[i] = null;
		}
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
		
		for (BowlingTurn bowlingTurn : game.getTurns()) {
			assertEquals(Integer.valueOf(10), bowlingTurn.getFirstPin());
		}
	}
	
	@Test
	public void testTurnInstanceDefensive() {
		BowlingGame game = new BowlingGameImpl();
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
		
		Integer gameId = game.getEntity().getId();
		
		BowlingTurn[] turns = game.getTurns();
		for (int i = 0; i < turns.length; i++) {
			turns[i].getEntity().setId(new DummyTurnKey());
			turns[i].getEntity().setFirstPin(5);
			turns[i].getEntity().setSecondPin(5);
		}

		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
		
		for (BowlingTurn bowlingTurn : game.getTurns()) {
			assertEquals(gameId, bowlingTurn.getEntity().getId().getForeignId());
			assertEquals(Integer.valueOf(10), bowlingTurn.getFirstPin());
			assertNotEquals(Integer.valueOf(5), bowlingTurn.getSecondPin());
		}
	}
	
	static class DummyTurnKey implements TurnKey {
		private static final long serialVersionUID = 1L;
		@Override
		public Integer getId() {
			return -1;
		}
		@Override
		public Integer getForeignId() {
			return -1;
		}
	}
	
}
