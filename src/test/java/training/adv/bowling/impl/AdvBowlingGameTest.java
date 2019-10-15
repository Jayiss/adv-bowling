package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.Arrays;
import java.util.List;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.dingziyuan.BowlingGameImpl;


public class AdvBowlingGameTest {
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();

	@Test
	public void testNoPins() {
		BowlingGame game = factory.getGame();
		game.addScores();
		assertEquals((0), game.getTotalScore());
	}

	@Test
	public void testNegative() {
		BowlingGame game = factory.getGame();

		game.addScores(-1);
		assertEquals((0), game.getTotalScore());
	}


	@Test
	public void testPartialStrike() {
		BowlingGame game = factory.getGame();
		game.addScores(10, 10, 10);
		assertEquals((60), game.getTotalScore());
	}

	@Test
	public void testTotalStrike() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((300), game.getTotalScore());
	}

	@Test
	public void testTotalStrikeSeparately() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10);
		assertEquals((60), game.getTotalScore());

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((300), game.getTotalScore());
	}

	@Test
	public void testGreaterThanMaxPins() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10);
		assertEquals((60), game.getTotalScore());

		game.addScores(10, 10, 20);
		assertEquals((60), game.getTotalScore());
	}

	@Test
	public void testInvalidPins() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10);
		assertEquals((60), game.getTotalScore());

		game.addScores(5, 6, 7, 8);
		assertEquals((60), game.getTotalScore());
	}

	@Test
	public void testSpareCalculation() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10);
		assertEquals((60), game.getTotalScore());

		game.addScores(5, 5, 5, 5);
		assertEquals((100), game.getTotalScore());
	}

	@Test
	public void testSpareSeparately() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10, 5);
		assertEquals((75), game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals((100), game.getTotalScore());
	}

	@Test
	public void testExtraTwoPins() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((270), game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals((270), game.getTotalScore());

		game.addScores(5, 5);
		assertEquals((285), game.getTotalScore());
	}

	@Test
	public void testExtraTwoPinsWithStrike() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((270), game.getTotalScore());

		game.addScores(10, 5, 5);
		assertEquals((270), game.getTotalScore());

		game.addScores(10, 5);
		assertEquals((295), game.getTotalScore());

	}

	@Test
	public void testLastTurnSpareSeparately() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
		assertEquals((255), game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals((255), game.getTotalScore());

		game.addScores(5, 5);
		assertEquals((270), game.getTotalScore());
	}

	@Test
	public void testFinishedGameNotAcceptNewPins() {
		BowlingGame game = factory.getGame();

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
		assertEquals((235), game.getTotalScore());

		game.addScores(5, 5, 5);
		assertEquals((255), game.getTotalScore());

		game.addScores(5, 5);
		assertEquals((255), game.getTotalScore());
	}

	@Test
	public void multiThreadTest() throws InterruptedException {
		List<BowlingGame> games = Arrays.asList(factory.getGame(), factory.getGame(), factory.getGame());
		for (int i = 0; i < 21; i++) {
			games.parallelStream().forEach(g -> g.addScores(5));
		}
		games.stream().forEach(g -> {
			assertEquals((150), g.getTotalScore());
		});
	}
}
