package training.adv.bowling.impl;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.xushizhi.*;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataAccessTest {

    private BowlingGameFactoryImpl factory = new BowlingGameFactoryImpl();

    @Before
    public void before() {
        String path_before = "/src/main/java/training/adv/bowling/impl/xushizhi/resources/scripts/setup.sql";
        String path = System.getProperty("user.dir") + path_before;
        System.out.println("SQL Setup File Path : " + path.replace("%20", " "));

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
        String path_after = "/src/main/java/training/adv/bowling/impl/xushizhi/resources/scripts/clean.sql";
        String path = System.getProperty("user.dir") + path_after;
        System.out.println("SQL Clean File Path : " + path.replace("%20", " "));

        try (Connection conn = DBUtil.getConnection();
             FileReader fr = new FileReader(new File(path))) {
            RunScript.execute(conn, fr);
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSave() {
        BowlingGame game = factory.getGame();
        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        new BowlingGameDaoImpl(DBUtil.getConnection()).save(game);
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
        BowlingGame game = new BowlingGameDaoImpl(DBUtil.getConnection()).load(1001);
        BowlingGameEntity entity = game.getEntity();

        assertEquals(Integer.valueOf(1001), entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(300, game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() {
        GameEntity before = query(1001);
        assertEquals(1001, before.getId());

        new BowlingGameDaoImpl(DBUtil.getConnection()).remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }

    // Get designated game entity by given id_game
    private BowlingGameEntity query(Integer id) {
        Integer maxTurn = null, maxPin = null;
        String sql = "Select * From GAME Where id_game = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            conn.commit();

            if (rs.next()) {
                maxTurn = rs.getInt(3);
                maxPin = rs.getInt(4);
                return new BowlingGameEntityImpl(id, maxTurn, maxPin);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    // Get designated turn entities by given turn key
    private BowlingTurnEntity query(TurnKey key) {
        BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
        String sql = "Select * From TURN Where id_turn = ? And id_game = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, key.getId());
            pstmt.setInt(2, key.getForeignId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                turnEntity.setFirstPin(rs.getInt(3));
                turnEntity.setSecondPin(rs.getInt(4));
                turnEntity.setId(key);
                return turnEntity;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
}
