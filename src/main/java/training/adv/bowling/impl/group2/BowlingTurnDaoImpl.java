package training.adv.bowling.impl.group2;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractBatchDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BowlingTurnDaoImpl extends AbstractBatchDao {
    private Connection connection;
    public BowlingTurnDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        String sql = "insert into TURN values(?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entity.getFirstPin());
            try {
                ps.setInt(2, entity.getSecondPin());
            } catch (Exception e) {
                ps.setNull(2, Types.NUMERIC);
            }
            ps.setInt(3, entity.getId().getId());
            ps.setInt(4, entity.getId().getForeignId());
            ps.execute();
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        String sql = "select FIRST_PIN, SECOND_PIN from TURN where ID = ? and FOREIGN_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id.getId());
            ps.setInt(2, id.getForeignId());
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new BowlingTurnImpl(rs.getInt("FIRST_PIN"), rs.getInt("SECOND_PIN"), null);
            }
//            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return new BowlingTurnImpl(entity.getFirstPin(), entity.getSecondPin(), null);
    }

    @Override
    public boolean remove(TurnKey key) {
        String sql = "delete from Turn where ID=? and FOREIGN_ID = ?";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, key.getId());
            ps.setInt(2, key.getForeignId());
            ps.executeUpdate();
            connection.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //todo
        //remove turns
        return false;
    }

    @Override
    protected List<TurnKey> loadAllKey(int foreignId) {
        String sql = "select ID, FOREIGN_ID from TURN where FOREIGN_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, foreignId);
            ResultSet rs = ps.executeQuery();
            List<TurnKey> result = new ArrayList<>();
            while (rs.next()) {
                result.add(new TurnKeyImpl(rs.getInt("ID"), rs.getInt("FOREIGN_ID")));
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
