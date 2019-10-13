package training.adv.bowling.impl;


import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.caoyu.*;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DataAccessTest {


    private BowlingGameDaoImpl bowlingGameDao = new BowlingGameDaoImpl(DBUtil.getConnection());

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
//        String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
//        System.out.println(path);
//        try (Connection conn = DBUtil.getConnection();
//             FileReader fr = new FileReader(new File(path))) {
//            RunScript.execute(conn, fr);
//            conn.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Test
    public void testSave() {
        BowlingGame game = new BowlingGameImpl(10, 10);
        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
        bowlingGameDao.save(game);
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
        BowlingGame game = bowlingGameDao.load(1001);
        GameEntity entity = game.getEntity();

        assertEquals(1001, entity.getId());
        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
        assertEquals(12, game.getTurns().length);
        assertEquals(Integer.valueOf(300), (Integer) game.getTotalScore());
    }

    //Prepared data in db.
    @Test
    public void testRemove() {
        GameEntity before = query(1001);
        assertEquals(1001, before.getId());

        bowlingGameDao.remove(1001);

        GameEntity after = query(1001);
        assertNull(after);
    }

    private GameEntity query(Integer id) {
        BowlingGameImpl gameResult = null;
        try {
            PreparedStatement queryStatement = DBUtil.getConnection().prepareStatement("select * from games where " +
                    "game_id = ?;");
            queryStatement.setInt(1, id);
            ResultSet rs = queryStatement.executeQuery();

            if (rs.next()) {
                gameResult = new BowlingGameImpl(rs.getInt("GAME_ID"), rs.getInt("MAX_TURN"), rs.getInt("MAX_PIN"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (null != gameResult) {

            //load turns
            ArrayList<BowlingTurnImpl> turnResults = new ArrayList<>();
            try {
                PreparedStatement queryStatement = DBUtil.getConnection().prepareStatement("select * from turns where " +
                        "game_id = ?;");
                queryStatement.setInt(1, gameResult.getId());
                ResultSet rs = queryStatement.executeQuery();

                TurnKey turnKey;
                BowlingTurnImpl currentTurn;
                Integer turnCount = 0;
                while (rs.next()) {
                    turnCount++;
                    turnKey = new TurnKeyImpl(rs.getInt("TURN_ID"), rs.getInt("GAME_ID"));
                    currentTurn = new BowlingTurnImpl(gameResult.getMaxPin(), gameResult.getMaxTurn(), turnCount);
                    currentTurn.setId(turnKey);
                    currentTurn.setFirstPin(rs.getInt("FIRST_PIN"));
                    int secondPin = rs.getInt("SECOND_PIN");
                    currentTurn.setSecondPin(secondPin == -1 ? null : secondPin);
                    turnResults.add(currentTurn);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            gameResult.setTurnEntities(turnResults.toArray(new BowlingTurnEntity[0]));
        }

        return gameResult;
    }

    private BowlingTurnEntity query(TurnKey key) {
        BowlingTurnEntity result = null;
        try {
            PreparedStatement queryStatement = DBUtil.getConnection().prepareStatement("select * from turns where " +
                    "game_id = ?;");
            queryStatement.setInt(1, key.getForeignId());
            ResultSet rs = queryStatement.executeQuery();

            TurnKey turnKey;
            BowlingTurnImpl currentTurn;
            Integer turnCount = 0;
            while (rs.next()) {
                turnCount++;
                turnKey = new TurnKeyImpl(rs.getInt("TURN_ID"), rs.getInt("GAME_ID"));
                currentTurn = new BowlingTurnImpl();
                currentTurn.setId(turnKey);
                currentTurn.setFirstPin(rs.getInt("FIRST_PIN"));
                int secondPin = rs.getInt("SECOND_PIN");
                currentTurn.setSecondPin(secondPin == -1 ? null : secondPin);
                if (currentTurn.getId().getId().equals(key.getId())) {
                    return currentTurn;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
