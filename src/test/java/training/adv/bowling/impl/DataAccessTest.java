package training.adv.bowling.impl;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import training.adv.bowling.api.*;
import training.adv.bowling.impl.DBUtil;
import training.adv.bowling.impl.GameService;
import training.adv.bowling.impl.lliushiying.BowlingGameEntityImpl;
import training.adv.bowling.impl.lliushiying.BowlingGameFactoryImpl;
import training.adv.bowling.impl.lliushiying.BowlingTurnEntityImpl;
import training.adv.bowling.impl.lliushiying.TurnKeyImpl;


import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;


public class DataAccessTest {
	

	private BowlingGameFactoryImpl factory=BowlingGameFactoryImpl.getInstance();
	private Connection conn= DBUtil.getConnection();
	private GameService bowlingService = new GameService();
	@Before
	public void before() {
		String path = ClassLoader.getSystemResource("script/setup.sql").getPath();
		System.out.println(path);
		try (Connection conn = DBUtil.getConnection();
				FileReader fr = new FileReader(new File(path))) {
			conn.setAutoCommit(true);
			RunScript.execute(conn, fr);
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
			conn.setAutoCommit(true);
			RunScript.execute(conn, fr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSave() {
		BowlingGame game = factory.getGame();
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10);
		bowlingService.save(game);
		GameEntity result = query(game.getEntity().getId());
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

	//多次save load
	@Test
	public void testSaveAndLoad() {
		BowlingGame game = factory.getGame();
		game.addScores(10, 10, 10);
		bowlingService.save(game);
		game=bowlingService.load(game.getEntity().getId());
		game.addScores(10, 10, 10, 10, 10, 10, 10, 10, 10);
		bowlingService.save(game);
		GameEntity result = query(game.getEntity().getId());
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
		GameEntity entity = game.getEntity();
		
		assertEquals(Integer.valueOf(1001), entity.getId());
		assertEquals(Integer.valueOf(10), entity.getMaxTurn());
		assertEquals(12, game.getTurns().length);
		assertEquals(300, game.getTotalScore());
	}
	
	//Prepared data in db.
	@Test
	public void testRemove() {
		GameEntity before = query(1001);
		assertEquals(Integer.valueOf(1001), before.getId());
		
		bowlingService.remove(1001);
		
		GameEntity after = query(1001);
		assertNull(after);
	}	
	
	
	private GameEntity query(Integer id) {
		String sql="select * from game_table where game_id=?";
		BowlingGameEntityImpl bowlingGameEntity=null;
		try{
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setObject(1, id);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()){
				bowlingGameEntity=new BowlingGameEntityImpl(id,rs.getInt("maxTurn"),rs.getInt("maxPin"));
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return bowlingGameEntity;
	}
	
	private BowlingTurnEntity query(TurnKey key) {
		int turn_id=key.getId();
		int game_id=key.getForeignId();
		String sql="select * from turns_table where turn_id=? and game_id=?";
		BowlingTurnEntity bowlingTurnEntity=null;
		try{
			PreparedStatement pstm = conn.prepareStatement(sql);
			pstm.setObject(1, turn_id);
			pstm.setObject(2, game_id);
			ResultSet rs=pstm.executeQuery();
			while(rs.next()){
				bowlingTurnEntity=new BowlingTurnEntityImpl();
				TurnKey turnKey=new TurnKeyImpl(rs.getInt("turn_id"),rs.getInt("game_id"));
				bowlingTurnEntity.setId(turnKey);
				bowlingTurnEntity.setFirstPin(rs.getInt("firstpin"));
				String secondPin=rs.getString("secondpin");
				if(secondPin==null||secondPin.equals("null")){
					bowlingTurnEntity.setSecondPin(null);
				}else {
					bowlingTurnEntity.setSecondPin(rs.getInt("secondpin"));
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return bowlingTurnEntity;
	}
	
}

