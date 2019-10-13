package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.zhangxinyi.BowlingGameServiceImpl;

public class BowlingGameTest {

    private BowlingGameServiceImpl service = new BowlingGameServiceImpl();

    @Test
    public void testNoPins() {
        BowlingGame game = service.getGame();

        game.addScores();
        Integer temp = game.getTotalScore();
        assertEquals(Integer.valueOf(0), (Integer)game.getTotalScore());
    }

    @Test
    public void testNegative() {
        BowlingGame game = service.getGame();

        game.addScores(-1);
        Integer temp = game.getTotalScore();
        assertEquals(Integer.valueOf(0), (Integer) game.getTotalScore());
    }


    @Test
    public void testPartialStrike() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
    }

    @Test
    public void testTotalStrike() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), (Integer)game.getTotalScore());
    }

    @Test
    public void testTotalStrikeSeparately() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), (Integer)game.getTotalScore());
    }

    @Test
    public void testGreaterThanMaxPins() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());

        game.addScores(10, 10, 20);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
    }

    @Test
    public void testInvalidPins() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());

        game.addScores(5, 6, 7, 8);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());
    }

    @Test
    public void testSpareCalculation() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer)game.getTotalScore());

        game.addScores(5, 5, 5, 5);
        assertEquals(Integer.valueOf(100), (Integer)game.getTotalScore());
    }

    @Test
    public void testSpareSeparately() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10, 5);
        assertEquals(Integer.valueOf(75), (Integer)game.getTotalScore());

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(100), (Integer)game.getTotalScore());
    }

    @Test
    public void testExtraTwoPins() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(285), (Integer)game.getTotalScore());
    }

    @Test
    public void testExtraTwoPinsWithStrike() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());

        game.addScores(10, 5, 5);
        assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());

        game.addScores(10, 5);
        assertEquals(Integer.valueOf(295), (Integer)game.getTotalScore());

    }

    @Test
    public void testLastTurnSpareSeparately() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
        assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(270), (Integer)game.getTotalScore());
    }

    @Test
    public void testFinishedGameNotAcceptNewPins() {
        BowlingGame game = service.getGame();

        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
        assertEquals(Integer.valueOf(235), (Integer)game.getTotalScore());

        game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());

        game.addScores(5, 5);
        assertEquals(Integer.valueOf(255), (Integer)game.getTotalScore());
    }

    @Test
    public void multiThreadTest() throws InterruptedException {
        List<BowlingGame> games = Arrays.asList(service.getGame(), service.getGame(), service.getGame());
        for (int i = 0; i < 21; i++) {
            games.parallelStream().forEach(g -> g.addScores(5));
        }
        games.stream().forEach(g -> {
            assertEquals(Integer.valueOf(150), (Integer)g.getTotalScore());
        });
    }

}
