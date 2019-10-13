package wangbingchao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;



public class GameDaoService {
private Connection connection = DBUtil.getConnection();
	
	private AbstractDao<BowlingGameEntity, BowlingGame, Integer> gameDao = new BowlingGameDaoImpl(connection);
	private AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> turnDao = new BowlingTurnDaoImpl(connection);
	
	public void save(BowlingGame game) {
		gameDao.save(game);
		
		
		for (BowlingTurn turn : game.getTurns()) {
			
			turnDao.save(turn);
		}
		commit();
	}

	public BowlingGame load(Integer id) {
		BowlingGame game = gameDao.load(id);
		List<TurnKey> keys = this.loadAllKey(id);
		
//		System.out.println(keys.get(1).getId());
		for(int i = 0;i < keys.size();i++) {
			
			BowlingTurnEntity turnEntity = turnDao.load(keys.get(i)).getEntity();
//			System.out.println(turnEntity.getFirstPin() + "+" + turnEntity.getSecondPin());
			if(turnEntity.getFirstPin() != null)
				game.addScores(turnEntity.getFirstPin());
			if(turnEntity.getSecondPin() != null)
				game.addScores(turnEntity.getSecondPin());
		}
		
	
		return game;
	}
	
	public void remove(Integer id) {
		gameDao.remove(id);
		List<TurnKey> keys = this.loadAllKey(id);
		
		for(int i = 0;i < keys.size();i++) {
			turnDao.remove(keys.get(i));
		}
		commit();
	}
	
	private void commit() {
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public List<TurnKey> loadAllKey(int foreignId) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM TURN WHERE FOREIGN_ID = ?";
		List<TurnKey> list = new ArrayList<TurnKey>();
		 try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, foreignId);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				TurnKey turnKey = new TurnKeyImpl(resultSet.getInt("ID"),resultSet.getInt("FOREIGN_ID"));
				list.add(turnKey);
			}
			return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
