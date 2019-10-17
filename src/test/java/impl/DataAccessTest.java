package impl;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.DBUtil;
import training.adv.bowling.impl.zhangsan.BowlingGameDaoImpl;
import training.adv.bowling.impl.zhangsan.BowlingGameEntityImpl;
import training.adv.bowling.impl.zhangsan.BowlingGameFactoryImpl;
import training.adv.bowling.impl.zhangsan.BowlingTurnEntityImpl;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DataAccessTest {

    private BowlingGameFactory factory = new BowlingGameFactoryImpl();
    @Before
    public void before() {
        String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
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
        String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
        System.out.println(path);
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
        GameEntity entity = game.getEntity();
        assertEquals(Integer.valueOf(1001), entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(300, game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() {
        GameEntity before = query(1001);
        assertEquals(Integer.valueOf(1001), before.getId());

        new BowlingGameDaoImpl(DBUtil.getConnection()).remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }


    private GameEntity query(Integer id) {
        //TODO
        GameEntity gameEntity = null;
        try (Connection connection = DBUtil.getConnection()) {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("select * from game where id="+id);
            if (rs.next()) {
                //Integer maxTurn = rs.getInt("maxTurn");
                //Integer maxPin = rs.getInt("maxPin");
                gameEntity = new BowlingGameEntityImpl(id);
                gameEntity.setId(id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return gameEntity;
    }

    private BowlingTurnEntity query(TurnKey key) {
        //TODO
        BowlingTurnEntity bowlingTurnEntity = null;
        try (Connection connection = DBUtil.getConnection()){
            Statement stmt = connection.createStatement();
            int foreignId = key.getForeignId();
            int id = key.getId();
            ResultSet rs = stmt.executeQuery("select * from turn where id="+id+" and foreignId="+foreignId);
            if (rs.next()) {
                Integer first = rs.getInt("firstPin");
                Integer second = null;
                if(!(rs.getString("secondPin")==null))
                    second = rs.getInt("secondPin");
                bowlingTurnEntity = new BowlingTurnEntityImpl();
                bowlingTurnEntity.setId(key);
                bowlingTurnEntity.setFirstPin(first);
                bowlingTurnEntity.setSecondPin(second);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bowlingTurnEntity;
    }

}