package training.adv.bowling.impl.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.h2.tools.RunScript;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingService;
import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.GameEntity;
import training.adv.bowling.api.TurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.BowlingServiceImpl;
import training.adv.bowling.impl.DBUtil;
import training.adv.bowling.impl.shike.BowlingGameEntityImpl;
import training.adv.bowling.impl.shike.BowlingGameFactoryImpl;
import training.adv.bowling.impl.shike.BowlingTurnEntityImpl;
import training.adv.bowling.impl.shike.BowlingTurnImpl;
import training.adv.bowling.impl.shike.TurnKeyImpl;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


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
//			assertEquals(turnEntity.getId().getId(), turnResult.getId().getId());
//			assertEquals(turnEntity.getId().getForeignId(), turnResult.getId().getForeignId());
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
		assertEquals((int)Integer.valueOf(300), game.getTotalScore());
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
		return null;
	}

	private BowlingTurnEntity query(TurnKey key) {
		return null;
	}
		

}


