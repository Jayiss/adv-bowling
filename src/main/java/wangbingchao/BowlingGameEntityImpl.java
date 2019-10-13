package wangbingchao;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity {

	private BowlingTurnEntity[] turns;
	private Integer maxTurn = 10;
	private Integer id;
	private Integer maxPin = 10;
	
	@Override
	public void setTurnEntities(BowlingTurnEntity[] turns) {
		// TODO Auto-generated method stub
		this.turns = turns;
	}

	@Override
	public BowlingTurnEntity[] getTurnEntities() {
		// TODO Auto-generated method stub
		return turns;
	}

	@Override
	public Integer getMaxTurn() {
		// TODO Auto-generated method stub
		return maxTurn;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(Integer id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Integer getMaxPin() {
		// TODO Auto-generated method stub
		return maxPin;
	}

}
