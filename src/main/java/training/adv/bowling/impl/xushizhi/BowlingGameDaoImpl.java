package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.*;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {

    private PreparedStatement pstmt = null;
    private Connection conn = null;
    private ResultSet rs = null;

    // H2 Database GAME table initialization - id_game(PK), MAX_TURN
    public BowlingGameDaoImpl(Connection connection) {
        try {
            conn = connection;
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        }
    }

    private void closeAll(Connection conn, PreparedStatement pstmt, ResultSet rs) {
        // Close ResultSet
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        // Close PreparedStatement
        if (pstmt != null) {
            try {
                pstmt.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        // Close Connection
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    // Save bowling game entity to H2 DB
    protected void doSave(BowlingGameEntity entity) {
        BowlingTurnEntity[] turnEntities;

        int maxTurn = entity.getMaxTurn(), maxPin = entity.getMaxPin();
        int id_game = -1;

        String sql = "Insert Into GAME (FINAL_SCORES, MAX_TURN, MAX_PIN) Values (?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, 0);
            pstmt.setInt(2, maxTurn);
            pstmt.setInt(3, maxPin);
            pstmt.executeUpdate();

            // Get insert action generated auto-increment PK - id_game
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                id_game = rs.getInt(1);
                entity.setId(id_game);
            }

            turnEntities = entity.getTurnEntities();
            for (BowlingTurnEntity turnEntity : turnEntities) {
                BowlingTurnEntityImpl.id_turn++;
                turnEntity.setId(new TurnKeyImpl(BowlingTurnEntityImpl.id_turn, id_game));

                new BowlingTurnDaoImpl(conn).doSave(turnEntity);
            }
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }
    }

//    @Override
//    protected void doSave(BowlingGameEntity entity) {
//        int maxTurn = entity.getMaxTurn();
//        int id = entity.getId();
//        int maxPins = entity.getMaxPin();
//        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();
//
//        String insertSql = "Insert Into GAME Values(?, ?, ?, ?)";
//        try {
//            PreparedStatement preparedStatement = conn.prepareStatement(insertSql);
//            preparedStatement.setInt(1, id);
//            preparedStatement.setInt(2, 0);  // Test Data
//            preparedStatement.setInt(3, maxTurn);
//            preparedStatement.setInt(4, maxPins);
//            preparedStatement.executeUpdate();
//
//            for (BowlingTurnEntity bowlingTurnEntity : turnEntities) {
//                new BowlingTurnDaoImpl(conn).doSave(bowlingTurnEntity);
//            }
//            conn.commit();
//        } catch (SQLException se) {
//            se.printStackTrace();
//        } finally {
//            this.closeAll(null, pstmt, rs);
//        }
//    }

    @Override
    // Get the bowling game entity data from H2 DB (by Game ID)
    protected BowlingGameEntity doLoad(Integer id) {
        int maxTurn = -1, maxPin = -1;
        String sql = "Select * From GAME Where id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            conn.commit();

            while (rs.next()) {
                maxTurn = rs.getInt(3);
                maxPin = rs.getInt(4);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }

        List<BowlingTurnEntity> turns = new BowlingTurnDaoImpl(conn).batchLoad(id);
        BowlingGameEntity gameEntity = new BowlingGameEntityImpl(id, maxTurn, maxPin);
        gameEntity.setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
        return gameEntity;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        Integer firstPin, secondPin;
        BowlingTurnEntity[] bowlingTurnEntities = entity.getTurnEntities();
        // BowlingTurnImpl(MAX_PIN, MAX_TURN, Pre_Turn)
        BowlingTurn bowlingTurn = new BowlingTurnImpl(entity.getMaxPin(), entity.getMaxTurn(), null);

        // Initialize new bowling turns & set turn entities
        for (int i = 0; i < bowlingTurnEntities.length; i++) {
            BowlingTurn newTurn = new BowlingTurnImpl(entity.getMaxPin(), entity.getMaxTurn(), bowlingTurn);

            BowlingTurnEntity turnEntity = newTurn.getEntity();
            TurnKey turnKey = new TurnKeyImpl(i + 1, entity.getId());  // id_turn, id_game
            firstPin = bowlingTurnEntities[i].getFirstPin();
            secondPin = bowlingTurnEntities[i].getSecondPin();

            turnEntity.setId(turnKey);
            turnEntity.setFirstPin(firstPin);
            turnEntity.setSecondPin(secondPin);

            bowlingTurn = newTurn;
        }
        return (new BowlingGameImpl(entity, bowlingTurn));
    }

    @Override
    // Remove designated game from H2 DB
    public boolean remove(Integer key) {
        String sql_1 = "Delete From TURN Where id_game = ?";
        String sql_2 = "Delete From GAME Where id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql_1);
            pstmt.setInt(1, key);
            pstmt.executeUpdate();

            pstmt = conn.prepareStatement(sql_2);
            pstmt.setInt(1, key);
            pstmt.executeUpdate();

            new BowlingTurnDaoImpl(conn).batchRemove(key);

            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, null);
        }
        return true;
    }
}
