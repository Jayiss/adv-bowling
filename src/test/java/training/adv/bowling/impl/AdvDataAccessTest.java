package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;


public class AdvDataAccessTest {

    private BowlingService bowlingService = new BowlingServiceImpl();
    private BowlingGameFactory factory = new BowlingGameFactoryImpl();

    @Before
    public void before() {
        String path = ClassLoader.getSystemResource("clean&create.sql").getPath();
        System.out.println(path);
        try (Connection conn = DBUtil.getConnection();
             FileReader fr = new FileReader(new File(path))) {
            RunScript.execute(conn, fr);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
    }

    @Test
    public void testSave() {
        BowlingGame game = factory.getGame();
        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        bowlingService.save(game);
        GameEntity result = query(game.getEntity().getId());
        assertEquals(game.getEntity().getId(), result.getId());
        assertEquals(game.getEntity().getMaxTurn(), result.getMaxTurn());

        for (BowlingTurn turn : game.getTurns()) {
            BowlingTurnEntity turnEntity = turn.getEntity();
            BowlingTurnEntity turnResult = query(turnEntity.getId());
            assertEquals(turnEntity.getId(), turnResult.getId());
            assertEquals(turnEntity.getFirstPin(), turnResult.getFirstPin());
            assertEquals(turnEntity.getSecondPin(), turnResult.getSecondPin());
        }
    }

    //Prepared data in db.
    @Test
    public void testLoad() {
        BowlingGame game = bowlingService.load("1001");
        GameEntity entity = game.getEntity();

        assertEquals("1001", entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(300, game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() {
        GameEntity before = query("1001");
        assertEquals("1001", before.getId());

        bowlingService.remove("1001");

        GameEntity after = query("1001");
        assertNull(after);
    }


    private GameEntity query(String id) {
        BowlingGame game = bowlingService.load(id);
        BowlingTurn[] turns = game.getTurns();
//        || (turns[0].getFirstPin()==null&&turns[0].getSecondPin()==null)
        if (game.getEntity().getId().equals("NA"))
            return null;
        return game.getEntity();
    }

    private BowlingTurnEntity query(TurnKey key) {
        BowlingGame game = bowlingService.load(key.getForeignId());
        for (BowlingTurnEntity turnEntity : game.getEntity().getTurnEntities()) {
            if (turnEntity.getId().getId().equals(key.getId()))
                return turnEntity;
        }
        return null;
    }

}
