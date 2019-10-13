package training.adv.bowling;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.ChaoyiFang.*;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class DataAccessTest {
    private BowlingGameFactory factory = new BowlingGameFactory();

    @Before
    public void before() {
        String path = ClassLoader.getSystemResource("script/create.sql").getPath();
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
        String path = ClassLoader.getSystemResource("script/drop.sql").getPath();
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

    @Test
    public void testLoad() {
        BowlingGame game = new BowlingGameDaoImpl(DBUtil.getConnection()).load(1001);
        BowlingGameEntity entity = game.getEntity();
        BowlingTurnEntity[] turnEntities =entity.getTurnEntities();
        assertEquals(Integer.valueOf(1001), entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(300, game.getTotalScore());
    }

    @Test
    public void testRemove() {
        GameEntity before = query(1001);
        assertEquals(Integer.valueOf(1001), before.getId());

        new BowlingGameDaoImpl(DBUtil.getConnection()).remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }


    private BowlingGameEntity query(Integer id) {
        //TODO
        try{
            String querySql = "select * from GAME where id = ?";
            PreparedStatement statement = DBUtil.getConnection().prepareStatement(querySql);
            statement.setInt(1,id);
            ResultSet rs = statement.executeQuery();
            if(rs.next()){
                BowlingGameEntity bowlingGameEntity = new BowlingGameEntityImpl(id,rs.getInt(3));
                return bowlingGameEntity;
            }else {
                return null;
            }
        }catch (SQLException exc){
            exc.printStackTrace();
            return null;
        }
    }

    private BowlingTurnEntity query(TurnKey key) {
        //TODO
        try {
            BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
            String querySql = "select * from TURN where  id = ? and foreign_id= ?";
            PreparedStatement statement = DBUtil.getConnection().prepareStatement(querySql);
            statement.setInt(1,key.getId());
            statement.setInt(2,key.getForeignId());
            ResultSet rs = statement.executeQuery();
            rs.next();
            bowlingTurnEntity.setFirstPin(rs.getInt(3));
            bowlingTurnEntity.setSecondPin(rs.getInt(4));
            bowlingTurnEntity.setId(key);
            return bowlingTurnEntity;
        }catch (SQLException exc){
            exc.printStackTrace();
            return null;
        }

    }

}
