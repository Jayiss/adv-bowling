package wangbingchao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class DaoTest {
	
	private GameDaoService bowlingService = new GameDaoService();
	
	@Before
	public void before() {
		
//		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
		String path = "C:/Users/lemon/Desktop/»¨Æì/adv-bowling/src/main/java/wangbingchao/script/setup.sql";
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
//		String path = ClassLoader.getSystemResource("script/clean.sql").getPath();
		String path = "C:/Users/lemon/Desktop/»¨Æì/adv-bowling/src/main/java/wangbingchao/script/clean.sql";
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
	public void testSave() {
		BowlingGame game = new BowlingGameImpl();
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		bowlingService.save(game);
		BowlingGameEntity result = query(game.getEntity().getId());
		assertEquals(game.getEntity().getId(), result.getId());
		assertEquals(game.getEntity().getMaxTurn(), result.getMaxTurn());
		
		for (BowlingTurn turn : game.getTurns()) {
			BowlingTurnEntity turnEntity = turn.getEntity();
			BowlingTurnEntity turnResult = query(turnEntity.getId());
			assertEquals(turnEntity.getId(), turnResult.getId());
			assertEquals(turnEntity.getFirstPin(), turnResult.getFirstPin());
			assertEquals(turnEntity.getSecondPin(), turnResult.getSecondPin());
		}
	}
	
	//Prepared data in db.
	@Test
	public void testLoad() {
		BowlingGame game = bowlingService.load(1001);
		BowlingGameEntity entity = game.getEntity();
		
		assertEquals(Integer.valueOf(1001), entity.getId());
		assertEquals(Integer.valueOf(10), entity.getMaxTurn());
		
//		BowlingTurn [] turns = game.getTurns();
//		for(int i = 0; i < turns.length;i++) {
//			System.out.print(turns[i].getFirstPin()+" ");
//			System.out.println(turns[i].getSecondPin());
//		}
		
		assertEquals(12, game.getTurns().length);
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
	}
	
	//Prepared data in db.
	@Test
	public void testRemove() {
		BowlingGameEntity before = query(1001);
		assertEquals(Integer.valueOf(1001), before.getId());
		
		bowlingService.remove(1001);
		
		BowlingGameEntity after = query(1001);
		assertNull(after);
	}	
	
	
	private BowlingGameEntity query(Integer id) {
		//TODO
//		GameEntity game = new GameEntityImpl();
		try (Connection connection = DBUtil.getConnection()) {
			String sql = "SELECT * FROM GAME WHERE ID = ?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				BowlingGameEntity entity = new BowlingGameEntityImpl();
				entity.setId(id);
				
				BowlingTurnEntity[] list = entity.getTurnEntities();
//				ArrayList<BowlingTurnEntity> bowlingTurns= new ArrayList<BowlingTurnEntity>();
//				for(BowlingTurnEntity turn: list) {
//					
//					BowlingTurn bowlingTurn = new BowlingTurnImpl();
//					bowlingTurn.getEntity().setId(turn.getId());
//					bowlingTurn.getEntity().setFirstPin(turn.getFirstPin());
//					bowlingTurn.getEntity().setSecondPin(turn.getSecondPin());
//					
//				}
				return entity;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		try (Connection connection = DBUtil.getConnection()) {
			String sql = "SELECT * FROM TURN WHERE ID = ? AND FOREIGN_ID=?";
			
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			
			preparedStatement.setInt(1, key.getId());
			preparedStatement.setInt(2, key.getForeignId());
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				BowlingTurnEntity turnEntity = new BowlingTurnEntityImpl();
//				entity.setId(resultSet.getInt("ID"));
//				entity.setId(resultS);
				TurnKey turnKey = new TurnKeyImpl(resultSet.getInt("ID"),resultSet.getInt("FOREIGN_ID"));
				turnEntity.setId(turnKey);
				Object first = resultSet.getObject("FIRST_PIN");
				turnEntity.setFirstPin(first==null ? null : Integer.parseInt(first.toString()));
				Object second = resultSet.getObject("SECOND_PIN");
				turnEntity.setSecondPin(second == null ?null: Integer.parseInt(second.toString()));
			
				return turnEntity;
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
