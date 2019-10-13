//package training.adv.bowling.impl;
//
//
//import org.h2.tools.RunScript;
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import training.adv.bowling.api.*;
//import training.adv.bowling.impl.caoyu.*;
//
//import java.io.File;
//import java.io.FileReader;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNull;
//
//
//public class DataAccessTest {
//
//
//    private BowlingService bowlingService = new BowlingServiceImpl();
//    private BowlingGameFactory factory = new BowlingGameFactoryImpl();
//
//    @Before
//    public void before() {
//        String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
//        System.out.println(path);
//        try (Connection conn = DBUtil.getConnection();
//             FileReader fr = new FileReader(new File(path))) {
//            RunScript.execute(conn, fr);
//            conn.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @After
//    public void after() {
////        String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
////        System.out.println(path);
////        try (Connection conn = DBUtil.getConnection();
////             FileReader fr = new FileReader(new File(path))) {
////            RunScript.execute(conn, fr);
////            conn.commit();
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
//    }
//
//    @Test
//    public void testSave() {
//        BowlingGame game = factory.getGame();
//        game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
//        bowlingService.save(game);
//        GameEntity result = query(game.getEntity().getId());
//        assertEquals(game.getEntity().getId(), result.getId());
//        assertEquals(game.getEntity().getMaxTurn(), result.getMaxTurn());
//
//        for (BowlingTurn turn : game.getTurns()) {
//            BowlingTurnEntity turnEntity = turn.getEntity();
//            BowlingTurnEntity turnResult = query(turnEntity.getId());
//            assertEquals(turnEntity.getId(), turnResult.getId());
//            assertEquals(turnEntity.getFirstPin(), turnResult.getFirstPin());
//            assertEquals(turnEntity.getSecondPin(), turnResult.getSecondPin());
//        }
//    }
//
//    //Prepared data in db.
//    @Test
//    public void testLoad() {
//        BowlingGame game = bowlingService.load(1001);
//        GameEntity entity = game.getEntity();
//
//        assertEquals(1001, entity.getId());
//        assertEquals(Integer.valueOf(10), entity.getMaxTurn());
//        assertEquals(12, game.getTurns().length);
//        assertEquals(Integer.valueOf(300), game.getTotalScore());
//    }
//
//    //Prepared data in db.
//    @Test
//    public void testRemove() {
//        GameEntity before = query(1001);
//        assertEquals(1001, before.getId());
//
//        bowlingService.remove(1001);
//
//        GameEntity after = query(1001);
//        assertNull(after);
//    }
//
//    private GameEntity query(Integer id) {//select * from games where game_id = 1000;
//        GameEntity result = null;
//        try {
//            var queryStatement = DBUtil.getConnection().prepareStatement("select * from games where " +
//                    "game_id = ?;");
//            queryStatement.setInt(1, id);
//            ResultSet rs = queryStatement.executeQuery();
//
//            BowlingRule rule;
//            if (rs.next()) {
//                rule = new BowlingRuleImpl(rs.getInt("MAX_TURN"), rs.getInt("MAX_PIN"));
//                result = new BowlingGameImpl(rule, rs.getInt("GAME_ID"));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    private BowlingTurnEntity query(TurnKey key) {
//        BowlingTurnEntity result = null;
//        try {
//            PreparedStatement queryStatement = DBUtil.getConnection().prepareStatement("select * from turns where " +
//                    "turn_id = ?;");
//            queryStatement.setInt(1, key.getId());
//            ResultSet rs = queryStatement.executeQuery();
//
//            TurnKey turnKey;
//            if (rs.next()) {
//                turnKey = new BowlingTurnKeyImpl(rs.getInt("TURN_ID"), rs.getInt("GAME_ID"));
//                result = new BowlingTurnImpl();
//                result.setId(turnKey);
//                result.setFirstPin(rs.getInt("FIRST_PIN"));
//                int secondPin = rs.getInt("SECOND_PIN");
//                result.setSecondPin(secondPin == -1 ? null : secondPin);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//}
