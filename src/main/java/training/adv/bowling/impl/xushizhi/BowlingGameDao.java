package training.adv.bowling.impl.xushizhi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BowlingGameDao extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {
    private Connection connection;

    public BowlingGameDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        int maxPins = entity.getMaxPin();
        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();
        String insertSql = "insert into bowling_game values(?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, 0);  // Test Data
            preparedStatement.setInt(3, maxTurn);
            preparedStatement.setInt(4, maxPins);
            preparedStatement.executeUpdate();
            for (BowlingTurnEntity turnEntity : turnEntities) {
                new BowlingTurnDao(connection).doSave(turnEntity);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        BowlingGameEntity gameEntity;
        String querySql = "select * from bowling_game where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(querySql);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int maxTurn = resultSet.getInt(3);
            int maxPins = resultSet.getInt(4);
            List<BowlingTurnEntity> turns = new BowlingTurnDao(connection).batchLoad(id);
            gameEntity = new BowlingGameEntityImpl(id, maxTurn, maxPins);
            gameEntity.setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
            return gameEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingTurnEntity[] bowlingTurnEntitys = entity.getTurnEntities();
        for (BowlingTurnEntity turnEntity : bowlingTurnEntitys) {
            System.out.println(turnEntity.getFirstPin() + "--" + turnEntity.getSecondPin());
        }
        BowlingTurn bowlingTurn = new BowlingTurnImpl(entity.getMaxPin(), entity.getMaxTurn(), null);
        for (int i = 0; i < bowlingTurnEntitys.length; i++) {
            BowlingTurn newTurn = new BowlingTurnImpl(entity.getMaxPin(), entity.getMaxTurn(), bowlingTurn);
            TurnKey turnKey = new TurnKeyImpl(i + 1, entity.getId());
            Integer firstTurn = bowlingTurnEntitys[i].getFirstPin();
            Integer seconTurn = bowlingTurnEntitys[i].getSecondPin();
            BowlingTurnEntity turnEntity = newTurn.getEntity();
            turnEntity.setId(turnKey);
            turnEntity.setFirstPin(firstTurn);
            turnEntity.setSecondPin(seconTurn);
            bowlingTurn = newTurn;
        }
        BowlingGame bowlingGame = new BowlingGameImpl(entity, bowlingTurn);
        return bowlingGame;
    }

    @Override
    public boolean remove(Integer key) {
        String delStatement = "delete from bowling_game where id = ? ";
        boolean isRemoved = false;
        try {
            PreparedStatement statement = connection.prepareStatement(delStatement);
            statement.setInt(1, key);
            isRemoved = statement.execute();
            new BowlingTurnDao(connection).batchRemove(key);
            connection.commit();
            return isRemoved;
        } catch (SQLException e) {
            e.printStackTrace();
            return isRemoved;
        }
    }
}
