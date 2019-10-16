package training.adv.bowling.impl.dingziyuan.dao;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.dingziyuan.BowlingGameImpl;

import java.sql.*;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, String> implements BowlingGameDao {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        try {
            String insertSql =
                    "insert into GAMES(GID, MAX_TURN, MAX_PIN) values(?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, entity.getId());
            stmt.setInt(2, entity.getMaxTurn());
            stmt.setInt(3, entity.getMaxPin());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingGameEntity doLoad(String id) {
        try {
            String selectSql =
                    "select * from GAMES where gid =?";
            PreparedStatement stmt = connection.prepareStatement(selectSql);
            stmt.setString(1, id);

            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                //build a BowlingGameEntity
                String gameId = resultSet.getString(1);
                Integer gameMAX_TURN = resultSet.getInt(2);
                Integer gameMAX_PIN = resultSet.getInt(3);

                BowlingGame bowlingGame = new BowlingGameImpl(gameMAX_TURN,gameMAX_PIN);
                bowlingGame.getEntity().setId(gameId);
                return bowlingGame.getEntity();
            }
            BowlingGame bowlingGame = new BowlingGameImpl(10,10);
            bowlingGame.getEntity().setId("NA");
            return bowlingGame.getEntity();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame game =new BowlingGameImpl(entity.getMaxTurn(),entity.getMaxPin());
        game.getEntity().setId(entity.getId());
        return game;
    }

    @Override
    public boolean remove(String key) {
        try {
            String removeGameSql =
                    "DELETE FROM GAMES WHERE GID = ?";
//            String removeTurnsSql =
//                    "DELETE FROM TURNS WHERE GID = ?";
            PreparedStatement stmt = connection.prepareStatement(removeGameSql);
            stmt.setString(1, key);
            stmt.executeUpdate();
//            stmt = connection.prepareStatement(removeTurnsSql);
//            stmt.setString(1, key);
//            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
