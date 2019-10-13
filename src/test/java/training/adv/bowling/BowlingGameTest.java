package training.adv.bowling;

import org.junit.Test;


import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.zhuyurui.BowlingGameFactory;


import static org.junit.Assert.assertEquals;


public class BowlingGameTest {

	private BowlingGameFactory factory =  BowlingGameFactory.getInstance();

	@Test
	public void testNoPins() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores();
		assertEquals(0, game.getTotalScore());
	}

	@Test
	public void testNegative() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(-1);
		assertEquals(0, game.getTotalScore());
	}


	@Test
	public void testPartialStrike() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10);
		assertEquals(60, game.getTotalScore());
	}

	@Test
	public void testTotalStrike() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(300, game.getTotalScore());
	}

	@Test
	public void testTotalStrikeSeparately() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10);
		assertEquals(60, game.getTotalScore());

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(300, game.getTotalScore());
	}

	@Test
	public void testGreaterThanMaxPins() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10);
		assertEquals(60, game.getTotalScore());

		game.addScores(10, 10, 20);
		assertEquals(60, game.getTotalScore());
	}

	@Test
	public void testInvalidPins() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10);
		assertEquals(60, game.getTotalScore());

		game.addScores(5, 6, 7, 8);
		assertEquals(60, game.getTotalScore());
	}

	@Test
	public void testSpareCalculation() {
		BowlingGame game=factory.getGame(10,10);
		game.addScores(10, 10, 10);
		assertEquals(60, game.getTotalScore());

		game.addScores(5, 5, 5, 5);
		assertEquals(100, game.getTotalScore());
	}

	@Test
	public void testSpareSeparately() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10, 5);
		assertEquals(75, game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals(100, game.getTotalScore());
	}

	@Test
	public void testExtraTwoPins() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(270, game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals(270, game.getTotalScore());

		game.addScores(5, 5);
		assertEquals(285, game.getTotalScore());
	}

	@Test
	public void testExtraTwoPinsWithStrike() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(270, game.getTotalScore());

		game.addScores(10, 5, 5);
		assertEquals(270, game.getTotalScore());

		game.addScores(10, 5);
		assertEquals(295, game.getTotalScore());

	}

	@Test
	public void testLastTurnSpareSeparately() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
		assertEquals(255, game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals(255, game.getTotalScore());

		game.addScores(5, 5);
		assertEquals(270, game.getTotalScore());
	}

	@Test
	public void testFinishedGameNotAcceptNewPins() {
		BowlingGame game=factory.getGame(10,10);

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
		assertEquals(235, game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals(255, game.getTotalScore());

		game.addScores(5, 5);
		assertEquals(255, game.getTotalScore());
	}



}





