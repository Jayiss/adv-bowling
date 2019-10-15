package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.group2.BowlingGameImpl;
import training.adv.bowling.impl.group2.BowlingTurnImpl;
import training.adv.bowling.impl.group2.DBUtil;
import training.adv.bowling.impl.group2.TurnKeyImpl;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataAccessTest {

    private GameService bowlingService = new GameService();

    @Before
    public void before() {
        String path = ClassLoader.getSystemResource("script/TEST_PUBLIC_GAME.sql").getPath();
//        System.out.println(path);
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
        String path = ClassLoader.getSystemResource("script/DROP_TABLES.sql").getPath();
//        System.out.println(path);
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
        BowlingGame game = new BowlingGameImpl(10, 10, 1);
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
        BowlingGame game = bowlingService.load(1001);
        GameEntity entity = game.getEntity();

        assertEquals(Integer.valueOf(1001), entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(10, game.getTurns().length);
        assertEquals(300, game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() throws SQLException {
        GameEntity before = query(1001);
        assertEquals(Integer.valueOf(1001), before.getId());

        bowlingService.remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }


    /**
     * 根据传入的gameId先去game表找，找到对应记录再去turn表找所有的turn，
     * 把turn组装成entity加入到gameEntity
     *
     * @param id game的id
     * @return gameEntity
     */
    private GameEntity query(Integer id) {
        try (Connection connection = DBUtil.getConnection()) {
            String gameTableSql = "select ID, MAX_TURN, MAX_PIN from GAME where ID = ?";
            String turnTableSql = "select ID, FOREIGN_ID, FIRST_PIN, SECOND_PIN from TURN where FOREIGN_ID = ?";
            PreparedStatement ps = connection.prepareStatement(gameTableSql);
            ps.setInt(1, id);
            ResultSet gameTable = ps.executeQuery();
            if (gameTable.next()) {
                GameEntity result = new BowlingGameImpl(id, gameTable.getInt("MAX_TURN"), gameTable.getInt("MAX_PIN"));
                ps = connection.prepareStatement(turnTableSql);
                ps.setInt(1, id);
                ResultSet turnTable = ps.executeQuery();
                List<BowlingTurnEntity> turnEntities = new ArrayList<>();
                while (turnTable.next()) {
                    BowlingTurnEntity turnEntity = new BowlingTurnImpl(turnTable.getInt("FIRST_PIN"), turnTable.getInt("SECOND_PIN")).getEntity();
                    turnEntity.setId(new TurnKeyImpl(turnTable.getInt("ID"), turnTable.getInt("FOREIGN_ID")));
                    turnEntities.add(turnEntity);
                }

                result.setTurnEntities(turnEntities.toArray(new BowlingTurnEntity[turnEntities.size()]));
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private BowlingTurnEntity query(TurnKey key) {
        try (Connection connection = DBUtil.getConnection()) {
            String sql = "select FIRST_PIN, SECOND_PIN, ID,FOREIGN_ID from TURN where ID = ? and FOREIGN_ID = ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, key.getId());
            ps.setInt(2, key.getForeignId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                BowlingTurnEntity result;
                if (rs.getInt("SECOND_PIN") == 0) {
                    result = new BowlingTurnImpl(rs.getInt("FIRST_PIN"), null);
                } else {
                    result = new BowlingTurnImpl(rs.getInt("FIRST_PIN"), rs.getInt("SECOND_PIN"));
                }
                result.setId(new TurnKeyImpl(rs.getInt("ID"), rs.getInt("FOREIGN_ID")));
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
