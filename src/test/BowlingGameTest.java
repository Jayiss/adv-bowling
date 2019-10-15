


import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.lihaojie.BowlingGameImpl;

public class BowlingGameTest {
    @Test
    public void testNoPins() {
        BowlingGame game = new BowlingGameImpl(10,10,1);
        game.addScores();
        assertEquals(Integer.valueOf(0),Integer.valueOf(game.getTotalScore()) );
    }

    @Test
    public void testNegative() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(-1);
        assertEquals(Integer.valueOf(0), Integer.valueOf(game.getTotalScore()));
    }


    @Test
    public void testPartialStrike() {
        BowlingGame game = new BowlingGameImpl(10,10,1);
        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testTotalStrike() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testTotalStrikeSeparately() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testGreaterThanMaxPins() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 10, 20);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testInvalidPins() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 6, 7, 8);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testSpareCalculation() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5, 5);
        assertEquals(Integer.valueOf(100), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testSpareSeparately() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10, 5);
        assertEquals(Integer.valueOf(75), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(100), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testExtraTwoPins() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(285), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testExtraTwoPinsWithStrike() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 5, 5);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));

        game.addScores(10, 5);
        assertEquals(Integer.valueOf(295), Integer.valueOf(game.getTotalScore()));

    }

    @Test
    public void testLastTurnSpareSeparately() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(270), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void testFinishedGameNotAcceptNewPins() {
        BowlingGame game = new BowlingGameImpl(10,10,1);

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
        assertEquals(Integer.valueOf(235), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(255), Integer.valueOf(game.getTotalScore()));
    }

    @Test
    public void multiThreadTest() throws InterruptedException {
        List<BowlingGame> games = Arrays.asList(new BowlingGameImpl(10,10,1)
                ,new BowlingGameImpl(10,10,1)
                ,new BowlingGameImpl(10,10,1));
        for (int i = 0; i < 21; i++) {
            games.parallelStream().forEach(g -> g.addScores(5));
        }
        games.stream().forEach(g -> {
            assertEquals(Integer.valueOf(150), Integer.valueOf(g.getTotalScore()));
        });
    }
}
