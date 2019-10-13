package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.impl.lliushiying.BowlingGameDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class GameService {
	private Connection conn=DBUtil.getConnection();;
	private BowlingGameDaoImpl gameDao=new BowlingGameDaoImpl(conn);

	public void play() {

	}


	public void save(BowlingGame game) {
		gameDao.save(game);
		commit();
	}


	public BowlingGame load(Integer id) {
		BowlingGame game = gameDao.load(id);
		return game;
	}


	public void remove(Integer id) {
		gameDao.remove(id);
		commit();
	}

	private void commit() {
		try {
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
