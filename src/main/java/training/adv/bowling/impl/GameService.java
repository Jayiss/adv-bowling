package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.group2.BowlingGameDaoImpl;
import training.adv.bowling.impl.group2.BowlingGameImpl;
import training.adv.bowling.impl.group2.BowlingTurnDaoImpl;
import training.adv.bowling.impl.group2.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GameService {
	private Connection connection = DBUtil.getConnection();
	private BowlingGameDaoImpl gameDao = new BowlingGameDaoImpl(connection);
	private BowlingTurnDaoImpl turnDao = new BowlingTurnDaoImpl(connection);


	public BowlingGame play() {
		return new BowlingGameImpl(10,10,10);
	}

	public void save(BowlingGame game) {
		gameDao.save(game);
		BowlingTurn turn = game.getFirstTurn();
		while (turn!=null){
			turnDao.save(turn);
			turn = turn.getAsLinkedNode().getNextItem();
		}
		commit();
	}
	public BowlingGame load(Integer id) {
		BowlingGame game = gameDao.load(id);
		return game;
	}
	public void remove(int id){
		turnDao.batchRemove(id);
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
