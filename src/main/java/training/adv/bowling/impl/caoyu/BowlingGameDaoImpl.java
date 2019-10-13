package training.adv.bowling.impl.caoyu;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;
import training.adv.bowling.impl.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {
    private Connection connection;

    public BowlingGameDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        //save game
        try {
            //game deleting DELETE FROM PUBLIC.GAMES WHERE GAME_ID = 33;
            PreparedStatement deleteExistingGame = connection.prepareStatement("DELETE FROM PUBLIC.GAMES WHERE " +
                    "GAME_ID = ?;");
            deleteExistingGame.setInt(1, entity.getId());
            deleteExistingGame.executeUpdate();


            //game insertion
            PreparedStatement insertBowlingGameStatement = connection.prepareStatement("INSERT INTO \"PUBLIC\"" +
                    ".\"GAMES\" " +
                    "(\"GAME_ID\", \"MAX_TURN\", \"MAX_PIN\") VALUES (?, ?,?)");
            insertBowlingGameStatement.setInt(1, entity.getId());
            insertBowlingGameStatement.setInt(2, entity.getMaxTurn());
            insertBowlingGameStatement.setInt(3, entity.getMaxPin());
            insertBowlingGameStatement.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //save turns
        for (BowlingTurnEntity turnEntity :
                entity.getTurnEntities()) {
            //game insertion
            try (PreparedStatement insertBowlingGameStatement = connection.prepareStatement("INSERT into PUBLIC.TURNS " +
                    "(turn_id, game_id, first_pin, second_pin) values (?, ?, ?, ?);")) {
                insertBowlingGameStatement.setInt(1, turnEntity.getId().getId());
                insertBowlingGameStatement.setInt(2, turnEntity.getId().getForeignId());
                insertBowlingGameStatement.setInt(3, turnEntity.getFirstPin());
                if (null != turnEntity.getSecondPin())
                    insertBowlingGameStatement.setInt(4, turnEntity.getSecondPin());
                else
                    insertBowlingGameStatement.setInt(4, -1);
                insertBowlingGameStatement.executeUpdate();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        //load game
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
        return gameResult;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return (BowlingGameImpl) entity;
    }

    @Override
    public boolean remove(Integer key) {
        try {
            PreparedStatement deleteExistingGame = connection.prepareStatement("DELETE FROM PUBLIC.GAMES WHERE " +
                    "GAME_ID = ?;");
            deleteExistingGame.setInt(1, key);
            deleteExistingGame.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
