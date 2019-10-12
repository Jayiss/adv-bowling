package training.adv.bowling.impl;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.impl.lliushiying.BowlingGameDaoImpl;
import training.adv.bowling.impl.lliushiying.BowlingGameEntityImpl;
import training.adv.bowling.impl.lliushiying.BowlingTurnDaoImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class GameService {
	private Connection conn=DBUtil.getConnection();
	private BowlingGameDaoImpl gameDao=new BowlingGameDaoImpl(conn);
	private BowlingTurnDaoImpl turnDao=new BowlingTurnDaoImpl(conn);

	public void play() {

	}


	public void save(BowlingGame game) {
		gameDao.save(game);
		for (BowlingTurn turn : game.getTurns()) {
			turnDao.save(turn);
		}
		commit();
	}


	public BowlingGame load(Integer id) {
		BowlingGame game = gameDao.load(id);
		//List<BowlingTurnEntity> turns = turnDao.batchLoad(id);
		//?应该加一个判断game是否为null吧
		//game.getEntity().setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
		return game;
	}


	public void remove(Integer id) {

		//turnDao.batchRemove(id);
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
