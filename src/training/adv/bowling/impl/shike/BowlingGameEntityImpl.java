package training.adv.bowling.impl.shike;

import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;

public class BowlingGameEntityImpl implements BowlingGameEntity{
	
	Integer maxPin;
	Integer maxTurn;
	
	BowlingTurnEntity[] turnEntitys;
	Integer id;
	
	
	public BowlingGameEntityImpl(Integer maxPin,Integer maxTurn) {
		this.maxPin=maxPin;
		this.maxTurn=maxTurn;
	}

	@Override
	public void setTurnEntities(BowlingTurnEntity[] turns) {this.turnEntitys=turns;}

	@Override
	public BowlingTurnEntity[] getTurnEntities() {return this.turnEntitys;}

	@Override
	public Integer getMaxTurn() {return this.maxTurn;}

	@Override
	public Integer getId() {return this.id;}

	@Override
	public void setId(Integer id) {this.id=id;}

	@Override
	public Integer getMaxPin() {return this.maxPin;}

}
