package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.GameService;

import java.sql.Connection;
import java.sql.SQLException;

public class BowlingGameServiceImpl extends GameService {
    private Connection connection = DBUtil.getConnection();
    private BowlingGameDaoImpl gameDao = new BowlingGameDaoImpl(connection);

    public void save(BowlingGame game) {
        gameDao.save(game);
        commit();
    }

    public BowlingGame load(Integer id) {
        BowlingGame game = gameDao.load(id);
        commit();
        return game;
    }

    public void remove(Integer id) {
        gameDao.remove(id);
        commit();
    }

    private void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void play() {
        System.out.println("Now Playing!");
    }

    public BowlingGame getGame() {
        return new BowlingGameImpl();
    }
}
