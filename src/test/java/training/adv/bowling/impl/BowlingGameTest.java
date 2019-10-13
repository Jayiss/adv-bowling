package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.StatusCode;
import training.adv.bowling.impl.caoyu.BowlingGameImpl;

import java.util.Arrays;
import java.util.List;

public class BowlingGameTest {
    @Test
    public void testNoPins() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores();
        assertEquals(Integer.valueOf(0), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testNegative() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(-1);
        assertEquals(Integer.valueOf(0), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testPartialStrike() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testTotalStrike() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testTotalStrikeSeparately() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(300), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testGreaterThanMaxPins() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(10, 10, 20);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testInvalidPins() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 6, 7, 8);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testInvalidPinsSeparately() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 5);
        assertEquals(Integer.valueOf(45), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(6, 7, 8);
        assertEquals(Integer.valueOf(45), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testSpareCalculation() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10);
        assertEquals(Integer.valueOf(60), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5, 5, 5);
        assertEquals(Integer.valueOf(100), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testSpareSeparately() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10, 5);
        assertEquals(Integer.valueOf(75), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(100), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testExtraTwoPins() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(270), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5);
        assertEquals(Integer.valueOf(285), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testExtraTwoPinsWithStrike() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        assertEquals(Integer.valueOf(270), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(10, 5, 5);
        assertEquals(Integer.valueOf(270), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(10, 5);
        assertEquals(Integer.valueOf(295), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

    }

    @Test
    public void testLastTurnSpareSeparately() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 5);
        assertEquals(Integer.valueOf(255), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5);
        assertEquals(Integer.valueOf(270), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void testFinishedGameNotAcceptNewPins() {
        BowlingGame game = new BowlingGameImpl(10, 10);

        StatusCode statusCode = game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 5, 5);
        assertEquals(Integer.valueOf(235), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5, 5);
        assertEquals(Integer.valueOf(255), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());

        statusCode = game.addScores(5, 5);
        assertEquals(Integer.valueOf(255), (Integer) game.getTotalScore());
        System.out.println("Status code:" + statusCode.getCode() + ": " + statusCode.getMessage());
    }

    @Test
    public void multiThreadTest() throws InterruptedException {
        List<BowlingGame> games = Arrays.asList(new BowlingGameImpl(10, 10), new BowlingGameImpl(10, 10), new BowlingGameImpl(10, 10));
        for (int i = 0; i < 21; i++) {
            games.parallelStream().forEach(g -> g.addScores(5));
        }
        games.stream().forEach(g -> {
            assertEquals(Integer.valueOf(150), (Integer) g.getTotalScore());
        });
    }
}
