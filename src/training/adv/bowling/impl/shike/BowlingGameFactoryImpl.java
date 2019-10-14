package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingGameFactory;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameFactoryImpl implements BowlingGameFactory{

	@Override
	public BowlingGame getGame() {
		BowlingGameEntity gameEntity = new BowlingGameEntityImpl(10, 10);
		
		Integer id = new GetNumber().getGameNum();
		
		gameEntity.setTurnEntities(new BowlingTurnEntity[0]);
		gameEntity.setId(id);
		
		BowlingGameImpl game = new BowlingGameImpl(gameEntity);
		
		
		return game;
	}

}
