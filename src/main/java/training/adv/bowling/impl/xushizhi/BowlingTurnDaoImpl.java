package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BowlingTurnDaoImpl extends AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> {

    private PreparedStatement pstmt = null;
    private Connection conn = null;
    private ResultSet rs = null;

    // H2 DB TURN table initialization - id_turn(PK), id_game(FK), MAX_TURN
    BowlingTurnDaoImpl(Connection connection) {
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

    // Get all turn keys (id_turn) of designated game by given FK (id_game / foreignId)
    private List<TurnKey> loadAllKey(int foreignId) {
        Integer id_turn = null;
        List<TurnKey> turnKeys = new ArrayList<>();

        String sql = "Select * From TURN Where id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, foreignId);
            rs = pstmt.executeQuery();
            conn.commit();

            // Get turn key -> Generate TurnKey -> Add to List<TurnKey> turnKeys
            while (rs.next()) {
                id_turn = rs.getInt(1);
                turnKeys.add(new TurnKeyImpl(id_turn, foreignId));
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }
        return turnKeys;
    }

    // Collect all corresponding turn keys
    final List<BowlingTurnEntity> batchLoad(int foreignId) {
        return loadAllKey(foreignId).stream().map(this::doLoad).collect(Collectors.toList());
    }

    // Remove all corresponding turn keys
    final void batchRemove(int foreignId) {
        loadAllKey(foreignId).forEach(this::remove);
    }

    @Override
    // Save current turn's entity to H2 DB
    protected void doSave(BowlingTurnEntity entity) {
        int secondPin = -1;
        if (entity.getSecondPin() != null) {
            secondPin = entity.getSecondPin();
        }

        String sql = "Insert Into TURN (id_turn, id_game, firstPin, secondPin) Values (?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, entity.getId().getId());  // id_turn
            pstmt.setInt(2, entity.getId().getForeignId());  // id_game
            pstmt.setInt(3, entity.getFirstPin());  // firstPin
            pstmt.setInt(4, secondPin);  // secondPin
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, null);
        }
    }

    @Override
    // Load designated turn entity from DB by given id_TurnKey
    protected BowlingTurnEntity doLoad(TurnKey id) {
        BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();

        String sql = "Select * From TURN Where id_turn = ? And id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id.getId());
            pstmt.setInt(2, id.getForeignId());
            rs = pstmt.executeQuery();
            conn.commit();

            while (rs.next()) {
                turnEntity.setFirstPin(rs.getInt(3));
                turnEntity.setSecondPin(rs.getInt(4));
                turnEntity.setId(id);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, rs);
        }
        return turnEntity;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    // Remove designated turn from H2 DB
    public boolean remove(TurnKey key) {
        String sql = "Delete From TURN Where id_turn = ? And id_game = ?";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, key.getId());  // TurnKey's ID (id_turn)
            pstmt.setInt(2, key.getForeignId());  // TurnKey's FK (id_game)
            pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException se) {
            se.printStackTrace();
        } finally {
            this.closeAll(null, pstmt, null);
        }
        return true;
    }
}
