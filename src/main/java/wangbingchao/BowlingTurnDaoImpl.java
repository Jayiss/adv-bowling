package wangbingchao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;

public class BowlingTurnDaoImpl extends AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> {

	private Connection connection;
	private GetNumber getNum = new GetNumber();
	public BowlingTurnDaoImpl(Connection connection) {

		this.connection = connection;
	}
	@Override
	protected void doSave(BowlingTurnEntity entity) {
		// TODO Auto-generated method stub
		try {
			String sql = "INSERT INTO TURN (ID, FOREIGN_ID, FIRST_PIN, SECOND_PIN) VALUES (?,?,?,?)";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
//			TurnKey turnkey = entity.getId();
			
			int gameNum = entity.getId().getForeignId();
			int turnNum = getNum.getTurnNum();
			TurnKey turnkey = new TurnKeyImpl(turnNum,gameNum);
			entity.setId(turnkey);
			preparedStatement.setInt(1, entity.getId().getId());
			preparedStatement.setInt(2, entity.getId().getForeignId());
			
			preparedStatement.setInt(3, entity.getFirstPin());
			
			if(entity.getSecondPin() != null)
				preparedStatement.setInt(4, entity.getSecondPin());
			else
				preparedStatement.setNull(4, Types.INTEGER);
			preparedStatement.execute();
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	protected BowlingTurnEntity doLoad(TurnKey id) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM TURN WHERE FOREIGN_ID = ? AND ID = ?";
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id.getForeignId());
			preparedStatement.setInt(2, id.getId());
			
			
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				
				BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
				TurnKey turnKey = new TurnKeyImpl(id.getId(),id.getForeignId());
				turnEntity.setId(turnKey);
				Object first = resultSet.getObject("FIRST_PIN");
				turnEntity.setFirstPin(first==null ? null : Integer.parseInt(first.toString()));
				Object second = resultSet.getObject("SECOND_PIN");
				turnEntity.setSecondPin(second == null ?null: Integer.parseInt(second.toString()));
				return turnEntity;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
		BowlingTurn bowlingTurn = new BowlingTurnImpl(entity);
		return bowlingTurn;
	}

	@Override
	public boolean remove(TurnKey key) {
		// TODO Auto-generated method stub
		try {
			String sql = "DELETE FROM TURN WHERE FOREIGN_ID = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, key.getForeignId());
			preparedStatement.execute();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
}


