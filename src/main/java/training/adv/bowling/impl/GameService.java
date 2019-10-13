package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.zhuyurui.BowlingGameDaoImpl;
import training.adv.bowling.impl.zhuyurui.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;

public class GameService {


	private Connection connection = DBUtil.getConnection();
	AbstractDao gameDao=new BowlingGameDaoImpl(connection);


	public void play() {

	}


	public void save(BowlingGame game) {
		gameDao.save(game);
		commit();
	}


	public BowlingGame load(Integer id) {
		BowlingGame game = (BowlingGame) gameDao.load(id);
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
}
