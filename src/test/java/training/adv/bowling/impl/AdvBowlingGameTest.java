package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.dingziyuan.BowlingGameImpl;


public class AdvBowlingGameTest {

	@Test
	public void testNoPins() {
		BowlingGame game = new BowlingGameImpl();
		game.addScores();
//		assertEquals(Integer.valueOf(0), game.getTotalScore());
	}
}
