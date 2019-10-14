package training.adv.bowling.impl.shike;
import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnDao;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractBatchDao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import java.sql.Types;
public class BowlingTurnDaoImpl extends AbstractBatchDao implements BowlingTurnDao{

	Connection conn = null;
	String sql;
	
	private GetNumber getNum = new GetNumber();
	
	public BowlingTurnDaoImpl(Connection connection) {		// TODO Auto-generated constructor stub
		conn=connection;
	}
	
	@Override
	protected List<TurnKey> loadAllKey(int foreignId) {
		List<TurnKey> tks = new ArrayList<TurnKey>();
		ResultSet set=null;
		sql = "select * from turn where foreign_id= "+foreignId;
		try (Statement stmt = conn.createStatement();){
			
			set = stmt.executeQuery(sql);
			while (set.next()) {
				int id=set.getInt("id") ;
				int fid=set.getInt("foreign_id") ;
				TurnKey tk = new TurnKeyImpl(id,fid);
				tks.add(tk);
				}
			return tks;
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void doSave(BowlingTurnEntity entity) {
//		sql = "insert into turn (f_id,pin1,pin2) values ("+entity.getId().getForeignId()+","+entity.getFirstPin()+","+entity.getSecondPin()+");";
		
		if (entity.getSecondPin() != null) {
			sql = "insert into turn (id,foreign_id,first_pin,second_pin) values ("+entity.getId().getId()
					+","+entity.getId().getForeignId()+","+entity.getFirstPin()+","+entity.getSecondPin()+");";
		}else {
			sql = "insert into turn (id,foreign_id,first_pin) values ("+entity.getId().getId()
					+","+entity.getId().getForeignId()+","+entity.getFirstPin()+");";
		}
		

		System.out.println(sql);
		try (Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	protected BowlingTurnEntity doLoad(TurnKey id) {
		ResultSet set=null;
		sql = "select * from turn where id= "+id.getId()+" and foreign_id= "+id.getForeignId();
		try (Statement stmt = conn.createStatement()){
			
			set = stmt.executeQuery(sql);
			
			if(set.next()) {
				BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
				TurnKey tk = new TurnKeyImpl(set.getInt("ID"), set.getInt("FOREIGN_ID"));
				turnEntity.setId(tk);
				
				Object first = set.getObject("FIRST_PIN");
				turnEntity.setFirstPin(first==null ? null : Integer.parseInt(first.toString()));
				Object second = set.getObject("SECOND_PIN");
				turnEntity.setSecondPin(second == null ?null: Integer.parseInt(second.toString()));
				
//				turnEntity.setFirstPin(set.getInt("FIRST_PIN"));
//				turnEntity.setSecondPin(set.getInt("SECOND_PIN"));
				return turnEntity;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
//		BowlingTurn game;
//		if (entity.getSecondPin()!=null) {
//	    	game = new BowlingTurnImpl(entity.getFirstPin());
//		}else {
//			game = new BowlingTurnImpl(entity.getFirstPin(),entity.getSecondPin());
//		}
//		TurnKey tk = new TurnKeyImpl(entity.getId().getId(),entity.getId().getForeignId());
//		game.getEntity().setId(tk);
//		return game;
		return null;
	}

	@Override
	public boolean remove(TurnKey key) {
		sql = "DELETE FROM turn where id= "+key.getId()+" and foreign_id= "+key.getForeignId();
		try (Statement stmt = conn.createStatement()){
			stmt.executeUpdate(sql);
			return true;
		}catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}


}
