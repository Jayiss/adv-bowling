package impl;

import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.impl.zhangsan.BowlingGameImpl;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import java.util.Arrays;
import java.util.List;
import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.zhangsan.BowlingGameFactoryImpl;

public class BowlingTest {

    private BowlingGameFactory factory = new BowlingGameFactoryImpl();


    @Test
    public void testNoPins() {
        BowlingGame game = factory.getGame();
        game.addScores();
        assertEquals(Integer.valueOf(0),Integer.valueOf(game.getTotalScore()) );
    }

    @Test
    public void testNegative() {
        BowlingGame game = factory.getGame();

        game.addScores(-1);
        assertEquals(Integer.valueOf(0), Integer.valueOf(game.getTotalScore()));
    }


    @Test
    public void testPartialStrike() {
        BowlingGame game = factory.getGame();
        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testTotalStrike() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testTotalStrikeSeparately() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testGreaterThanMaxPins() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 10, 20);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testInvalidPins() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 6, 7, 8);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testSpareCalculation() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        //game.addScores(5, 5, 5, 5);
        //assertEquals(Integer.valueOf(100), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testSpareSeparately() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10, 5);
        assertEquals(Integer.valueOf(75), Integer.valueOf(game.getTotalScore()));

        //game.addScores(5, 5, 5);
        //assertEquals(Integer.valueOf(100), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testExtraTwoPins() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(285), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testExtraTwoPinsWithStrike() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 5, 5);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 5);
        assertEquals(Integer.valueOf(295), Integer.valueOf(game.getTotalScore()));

    }

    @Test
    public void testLastTurnSpareSeparately() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testFinishedGameNotAcceptNewPins() {
        BowlingGame game = factory.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
        assertEquals(Integer.valueOf(235), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void multiThreadTest() throws InterruptedException {
        List<BowlingGame> games = Arrays.asList(factory.getGame(), factory.getGame(), factory.getGame());
        for (int i = 0; i < 21; i++) {
            games.parallelStream().forEach(g -> g.addScores(5));
        }
        games.stream().forEach(g -> {
            assertEquals(150, g.getTotalScore());
        });
    }

}
