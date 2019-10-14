package training.adv.bowling.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingService;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.dao.impl.yangxiaotong.BowlingGameDaoImpl;
import training.adv.bowling.dao.impl.yangxiaotong.BowlingTurnDaoImpl;

public class BowlingServiceImpl implements BowlingService {
	//TODO: implement DBUtil
	private Connection connection = DBUtil.getConnection();
	
	private BowlingGameDao gameDao = new BowlingGameDaoImpl(connection);
	private BowlingTurnDao turnDao = new BowlingTurnDaoImpl(connection);
	
	@Override
	public void save(BowlingGame game) throws SQLException {
		gameDao.save(game);
		for (BowlingTurn turn : game.getTurns()) {
			turnDao.save(turn);
		}
		commit();
	}

	@Override
	public BowlingGame load(Integer id) {
		BowlingGame game = gameDao.load(id);
		List<BowlingTurnEntity> turns = turnDao.batchLoad(id);
		
		//System.out.println(turns.size());
		
		BowlingTurnEntity[] turn=turns.toArray(new BowlingTurnEntity[0]);
		
		/*for(int i=0;i<turns.size();i++) {
			System.out.println(turn[i].getId().getForeignId()+" "+turn[i].getId().getId()+" "+turn[i].getFirstPin()+" "+turn[i].getSecondPin());
		}*/
		
		game.getEntity().setTurnEntities(turns.toArray(new BowlingTurnEntity[0]));
		return game;
	}
	
	@Override
	public void remove(Integer id) {
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
