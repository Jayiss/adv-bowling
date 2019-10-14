package training.adv.bowling.impl.shike;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingTurn;

public class MyTest {

	public static void main(String[] args) {
		BowlingGameFactory factory = new BowlingGameFactoryImpl();
		BowlingGame game = factory.getGame();
		
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
//		BowlingTurn[] bTurn = game.getTurns();
//		System.out.println(bTurn.length);
		
		int[] scores = game.getScores();
		
		for (int i = 0; i < scores.length; i++) {
			System.out.print(scores[i]+",");
		}
		Integer totalScore =  game.getTotalScore();
		System.out.println(totalScore);

	}

}
