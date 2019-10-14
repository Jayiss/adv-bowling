package training.adv.bowling.impl.shike;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameDao;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnEntity;
import training.adv.bowling.impl.AbstractDao;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> implements BowlingGameDao{

	Connection conn = null;
	String sql;

    public BowlingGameDaoImpl(Connection connection) {
    	conn=connection;
	}
    
	@Override
	protected void doSave(BowlingGameEntity entity) {
		try {
			Statement stmt = conn.createStatement();
			int maxTurn=entity.getMaxTurn();
			int maxPin = entity.getMaxPin();
			sql="INSERT INTO game (maxturn,maxpin) VALUES ("+maxTurn+","+maxPin+")";
			stmt.executeUpdate(sql);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	protected BowlingGameEntity doLoad(Integer id) {
    	BowlingGameEntity gEntity=null;
		ResultSet set=null;
		sql="select * from game where id= "+id;
		boolean flag=false;
		try (Statement stmt = conn.createStatement()){
			
			set = stmt.executeQuery(sql);
			Integer maxturn=null;
			Integer maxpin=null;
			TurnEntity[] turns;

			while (set.next()) {
				maxturn=Integer.parseInt(set.getString(2)) ;
				maxpin=Integer.parseInt(set.getString(3)) ;
				flag=true;
				}
			gEntity = new BowlingGameEntityImpl(maxpin, maxturn);
			gEntity.setId(id);
			conn.commit();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (flag) {
			return gEntity;
		}
		else {
			return null;
		}
	}

	@Override
	protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
		return null;
 
	}

	@Override
	public boolean remove(Integer key) {
		sql = "DELETE FROM game WHERE id = "+key;
		try (Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

}
