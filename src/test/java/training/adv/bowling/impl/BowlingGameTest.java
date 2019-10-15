package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.caokeke.BowlingGameImpl;


public class BowlingGameTest {
	
	private BowlingGame game = new BowlingGameImpl();

	@Test
	public void testNoPins() {
		game.addScores();
		assertEquals(Integer.valueOf(0), (Integer) game.getTotalScore());
	}
	
	@Test
	public void testNegative() {
		game.addScores(-1);
		assertEquals(Integer.valueOf(0), (Integer)game.getTotalScore());
	}
	
	
	@Test
	public void testPartialStrike() {
		game.addScores(10, 10, 10);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testTotalStrike() {
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(Integer.valueOf(300), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testTotalStrikeSeparately() {
		
		game.addScores(10, 10, 10);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(Integer.valueOf(300), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testGreaterThanMaxPins() {
		game.addScores(10, 10, 10);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
		
		game.addScores(10, 10, 20);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testInvalidPins() {

		game.addScores(10, 10, 10);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
		
		game.addScores(5, 6, 7, 8);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testSpareCalculation() {
		game.addScores(10, 10, 10);
		assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
		
		game.addScores(5, 5, 5, 5);
		assertEquals(Integer.valueOf(100), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testSpareSeparately() {
		game.addScores(10, 10, 10, 5);
		assertEquals(Integer.valueOf(75), (Integer)game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals(Integer.valueOf(100), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testExtraTwoPins() {

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals(Integer.valueOf(285), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testExtraTwoPinsWithStrike() {

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());
		
		game.addScores(10, 5, 5);
		assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());
		
		game.addScores(10, 5);
		assertEquals(Integer.valueOf(295), (Integer)game.getTotalScore());
		
	}
	
	@Test
	public void testLastTurnSpareSeparately() {

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
		assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());
	}
	
	@Test
	public void testFinishedGameNotAcceptNewPins() {

		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
		assertEquals(Integer.valueOf(235), (Integer)game.getTotalScore());
		
		game.addScores(5, 5, 5);
		assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());
		
		game.addScores(5, 5);
		assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());
	}
	
}
