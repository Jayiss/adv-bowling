package wangbingchao;

import static org.junit.Assert.assertEquals;


import java.util.Arrays;
import java.util.List;
import org.junit.Test;


import training.adv.bowling.api.BowlingGame;

public class GameTest {
	private BowlingGame bowlingGame = new BowlingGameImpl();
	
	@Test
	public void testNoPins() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores();
		assertEquals((int)Integer.valueOf(0), game.getTotalScore());
	}
	
	@Test
	public void testNegative() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(-1);
		assertEquals((int)Integer.valueOf(0), game.getTotalScore());
	}
	
	
	@Test
	public void testPartialStrike() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
	}
	
	@Test
	public void testTotalStrike() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
	}
	
	@Test
	public void testTotalStrikeSeparately() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
	}
	
	@Test
	public void testGreaterThanMaxPins() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(10, 10, 20);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
	}
	
	@Test
	public void testInvalidPins() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(5, 6, 7, 8);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
	}
	
	@Test
	public void testSpareCalculation() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10);
		assertEquals((int)Integer.valueOf(60), game.getTotalScore());
		
		game.addScores(5, 5, 5, 5);
		assertEquals((int)Integer.valueOf(100), game.getTotalScore());
	}
	
	@Test
	public void testSpareSeparately() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10, 5);
		assertEquals((int)Integer.valueOf(75), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(100), game.getTotalScore());
	}
	
	@Test
	public void testExtraTwoPins() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals((int)Integer.valueOf(285), game.getTotalScore());
	}
	
	@Test
	public void testExtraTwoPinsWithStrike() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(10, 5, 5);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
		
		game.addScores(10, 5);
		assertEquals((int)Integer.valueOf(295), game.getTotalScore());
		
	}
	
	@Test
	public void testLastTurnSpareSeparately() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals((int)Integer.valueOf(270), game.getTotalScore());
	}
	
	@Test
	public void testFinishedGameNotAcceptNewPins() {
		BowlingGame game = new BowlingGameImpl();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
		assertEquals((int)Integer.valueOf(235), game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals((int)Integer.valueOf(255), game.getTotalScore());
	}
	
	@Test
	public void multiThreadTest() throws InterruptedException {
		BowlingGame game1 = new BowlingGameImpl();
		BowlingGame game2 = new BowlingGameImpl();
		BowlingGame game3 = new BowlingGameImpl();
		List<BowlingGame> games = Arrays.asList(game1, game2, game3);
		for (int i = 0; i < 21; i++) {
			games.parallelStream().forEach(g -> g.addScores(5));
		}
		games.stream().forEach(g -> {
			assertEquals((int)Integer.valueOf(150), g.getTotalScore());
		});
	}

}
