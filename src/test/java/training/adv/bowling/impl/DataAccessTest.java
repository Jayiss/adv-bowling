package training.adv.bowling.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingRule;
import training.adv.bowling.api.BowlingService;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.dao.impl.yangxiaotong.TurnKeyImpl;
import training.adv.bowling.impl.yangxiaotong.BowlingGameEntityInfo;
import training.adv.bowling.impl.yangxiaotong.BowlingGameFactoryImpl;
import training.adv.bowling.impl.yangxiaotong.BowlingRuleInfo;
import training.adv.bowling.impl.yangxiaotong.BowlingTurnEntityInfo;


public class DataAccessTest {
	
	private BowlingService bowlingService = new BowlingServiceImpl();
	private BowlingGameFactory factory = new BowlingGameFactoryImpl();
	
	@Before
	public void before() {
		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
		System.out.println(path);
		try (Connection conn = DBUtil.getConnection();
				FileReader fr = new FileReader(new File(path))) {
			RunScript.execute(conn, fr);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void after() {
		String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
		System.out.println(path);
		try (Connection conn = DBUtil.getConnection();
			 FileReader fr = new FileReader(new File(path))) {
			RunScript.execute(conn, fr);
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSave() throws SQLException {
		BowlingGame game =factory.getGame();
		//System.out.println(game.getTotalScore());
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		//System.out.println(game.getTotalScore());
		bowlingService.save(game);
		//System.out.println(game.getEntity().getId());
		GameEntity result = query(game.getEntity().getId());
		//System.out.println(result.getId());
		//System.out.println(result.getMaxTurn());
		assertEquals(game.getEntity().getId(), result.getId());
		assertEquals(game.getEntity().getMaxTurn(), result.getMaxTurn());
		
		for (BowlingTurn turn : game.getTurns()) {
			BowlingTurnEntity turnEntity = turn.getEntity();
			BowlingTurnEntity turnResult = query(turnEntity.getId());
			//System.out.println(turnEntity.getId());
			//System.out.println(turnResult.getId());
			assertEquals(turnEntity.getId(), turnResult.getId());
			//System.out.println(turnResult.getFirstPin());
			assertEquals(turnEntity.getFirstPin(), turnResult.getFirstPin());
			//System.out.println(turnResult.getSecondPin());
			assertEquals(turnEntity.getSecondPin(), turnResult.getSecondPin());
		}
	}
	
	//Prepared data in db.
	@Test
	public void testLoad() {
		BowlingGame game = bowlingService.load(1001);
		BowlingGameEntity entity = game.getEntity();
		//System.out.println(entity.getTurnEntities().length);
		assertEquals(Integer.valueOf(1001), entity.getId());
		assertEquals(Integer.valueOf(10), entity.getMaxTurn());
		assertEquals(12, game.getTurns().length);
		assertEquals(Integer.valueOf(300), game.getTotalScore());
	}
	
	//Prepared data in db.
	@Test
	public void testRemove() throws SQLException {
		GameEntity before = query(1001);
		assertEquals(Integer.valueOf(1001), before.getId());
		
		bowlingService.remove(1001);
		
		GameEntity after = query(1001);
		assertNull(after);
	}	
	
	
	/*private GameEntity query(Integer id) throws SQLException {
		//TODO
		Connection conn=DBUtil.getConnection();
		Statement st=conn.createStatement();
		String sql="select * from bowlingGame where id="+id;
		ResultSet rs=st.executeQuery(sql);
		Integer maxTurn=rs.getInt("maxTurn");
		Integer maxPin=rs.getInt("maxPin");
		BowlingRule gameRule=new BowlingRuleInfo(id,maxTurn,maxPin);
		GameEntity gameEntity=new BowlingGameEntityInfo(gameRule);
		return gameEntity;
	}*/
	
	private GameEntity query(Integer id) {
		   //TODO
		   GameEntity gameEntity = null;
		   
		   try (Connection connection = DBUtil.getConnection()) {
		      Statement stmt = connection.createStatement();
		      ResultSet rs = stmt.executeQuery("select * from bowlingGame where id="+id);
		      if (rs.next()) {
		         Integer maxTurn = rs.getInt("maxTurn");
		         Integer maxPin = rs.getInt("maxPin");
		         BowlingRule gameRule=new BowlingRuleInfo(id,maxTurn,maxPin);
		         gameEntity = new BowlingGameEntityInfo(gameRule);
		      }
		   } catch (SQLException e) {
		      e.printStackTrace();
		   }
		   return gameEntity;
		}
	
	private BowlingTurnEntity query(TurnKey key) {
		   //TODO
		   BowlingTurnEntity bowlingTurnEntity = null;
		   try (Connection connection = DBUtil.getConnection()){
		      Statement stmt = connection.createStatement();
		      int foreignId = key.getForeignId();
		      int id = key.getId();
		      ResultSet rs = stmt.executeQuery("select * from bowlingTurn where id="+foreignId+" and turnId="+id);
		      if (rs.next()) {
		         Integer first = rs.getInt("firstPin");
		         Integer second = null;
		         if(!(rs.getString("secondPin")==null))
		            second = rs.getInt("secondPin");
		         bowlingTurnEntity = new BowlingTurnEntityInfo();
		         bowlingTurnEntity.setId(key);
		 		 bowlingTurnEntity.setFirstPin(first);
		 		 bowlingTurnEntity.setSecondPin(second);
		      }
		   } catch (SQLException e) {
		      e.printStackTrace();
		   }
		   return bowlingTurnEntity;
		}
	
	/*private BowlingTurnEntity query(TurnKey key) throws SQLException {
		//TODO
		Connection conn=DBUtil.getConnection();
		Statement st=conn.createStatement();
		
		Integer id=key.getForeignId();
		Integer turnId=key.getId();
		
		String sql="select * from bowlingTurn where id="+id+"and key="+turnId;
		ResultSet rs=st.executeQuery(sql);
		
		Integer firstPin=rs.getInt("firstPin");
		Integer secondPin=null;
		if(!(rs.getString("secondPin")==null))
            secondPin = rs.getInt("secondPin");
		
		BowlingTurnEntity turnEntity=new BowlingTurnEntityInfo();
		turnEntity.setId(key);
		turnEntity.setFirstPin(firstPin);
		turnEntity.setSecondPin(secondPin);
		return turnEntity;
	}*/
	
}
