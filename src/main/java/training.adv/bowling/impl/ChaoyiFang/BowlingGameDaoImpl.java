package training.adv.bowling.impl.ChaoyiFang;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame,Integer> {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }
    @Override
    protected void doSave(BowlingGameEntity entity) {
        int maxTurn = entity.getMaxTurn();
        int id = entity.getId();
        int maxPins = entity.getMaxPin();
        BowlingTurnEntity[] turnEntities = entity.getTurnEntities();
        String insertSql = "insert into GAME values(?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertSql);
            preparedStatement.setInt(1,id);
            preparedStatement.setInt(2,12);
            preparedStatement.setInt(3,maxTurn);
            preparedStatement.executeUpdate();
            for (BowlingTurnEntity bowlingTurnEntity:turnEntities){
                new BowlingTurnDaoImpl(connection).doSave(bowlingTurnEntity);
            }
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        BowlingGameEntity gameEntity;
        String querySql = "select * from GAME where id = ?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(querySql);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            int maxTurn = resultSet.getInt(3);
            List<BowlingTurnEntity> turns = new BowlingTurnDaoImpl(connection).batchLoad(id);
            gameEntity = new BowlingGameEntityImpl(id,maxTurn);
            gameEntity.setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
            return  gameEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingTurnEntity[] bowlingTurnEntitys = entity.getTurnEntities();
        BowlingTurn bowlingTurn = new BowlingTurnImpl(entity.getMaxPin(),entity.getMaxTurn(),null);
        for(int i = 0;i<bowlingTurnEntitys.length;i++){
            BowlingTurn newTurn = new BowlingTurnImpl(entity.getMaxPin(),entity.getMaxTurn(),bowlingTurn);
            TurnKey turnKey = new TurnKeyImpl(i+1,entity.getId());
            Integer firstTurn = bowlingTurnEntitys[i].getFirstPin();
            Integer seconTurn =  bowlingTurnEntitys[i].getSecondPin();
            BowlingTurnEntity turnEntity =  ((BowlingTurnImpl) newTurn).getEntity();
            turnEntity.setId(turnKey);
            turnEntity.setFirstPin(firstTurn);
            turnEntity.setSecondPin(seconTurn);
            bowlingTurn = newTurn;
        }
        BowlingGame bowlingGame = new BowlingGameImpl(entity,bowlingTurn);
        return bowlingGame;
    }

    @Override
    public boolean remove(Integer key) {
        String delStatement = "delete from GAME where id = ? ";
        boolean isRemoved = false;
        try {
            PreparedStatement statement = connection.prepareStatement(delStatement);
            statement.setInt(1,key);
            isRemoved = statement.execute();
            new BowlingTurnDaoImpl(connection).batchRemove(key);
            connection.commit();
            return isRemoved;
        } catch (SQLException e) {
            e.printStackTrace();
            return isRemoved;
        }
    }
}
