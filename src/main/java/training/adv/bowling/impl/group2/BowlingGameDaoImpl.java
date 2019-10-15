package training.adv.bowling.impl.group2;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        String sql = "insert into GAME values(?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.setInt(2, entity.getMaxPin());
            ps.setInt(3, entity.getMaxTurn());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        String gameTableSql = "select ID, MAX_TURN, MAX_PIN from GAME where ID = ?";
        String turnTableSql = "select ID, FOREIGN_ID, FIRST_PIN, SECOND_PIN from TURN where FOREIGN_ID = ?";
        try {
            PreparedStatement ps = connection.prepareStatement(gameTableSql);
            ps.setInt(1, id);
            ResultSet gameTable = ps.executeQuery();
            if (gameTable.next()) {
                BowlingGameEntity result = new BowlingGameImpl(id, gameTable.getInt("MAX_PIN"), gameTable.getInt("MAX_TURN"));
                ps = connection.prepareStatement(turnTableSql);
                ps.setInt(1, id);
                ResultSet turnTable = ps.executeQuery();
                List<BowlingTurnEntity> turnEntities = new ArrayList<>();
                while (turnTable.next()) {
                    BowlingTurnEntity turnEntity;
                    turnEntity = new BowlingTurnImpl(turnTable.getInt("FIRST_PIN"), turnTable.getInt("SECOND_PIN"));
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

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame result = new BowlingGameImpl(entity.getId(), entity.getMaxPin(), entity.getMaxTurn());
        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();
        int length = turnEntities.length;
        List<Integer> scores = new ArrayList<>();

        int indexOfEntity = 0;
        for (; indexOfEntity < length; indexOfEntity++) {
            scores.add(turnEntities[indexOfEntity].getFirstPin());
            if (turnEntities[indexOfEntity].getSecondPin() != null && turnEntities[indexOfEntity].getSecondPin() != 0) {
                scores.add(turnEntities[indexOfEntity].getSecondPin());
            }
        }
        result.addScores(scores.toArray(new Integer[scores.size()]));
        return result;
    }

    @Override
    public boolean remove(Integer key) {
        String sql = "delete from GAME where ID = ? ";
        try (Connection connection = DBUtil.getConnection()) {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, key);
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
}
