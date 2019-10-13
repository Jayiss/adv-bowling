package wangbingchao;

import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;

public class BowlingTurnEntityImpl implements BowlingTurnEntity {
	private TurnKey id;
	private Integer firstPin;
	private Integer secondPin;
	@Override
	public TurnKey getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public void setId(TurnKey id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public Integer getFirstPin() {
		// TODO Auto-generated method stub
		return firstPin;
	}

	@Override
	public Integer getSecondPin() {
		// TODO Auto-generated method stub
		return secondPin;
	}

	@Override
	public void setFirstPin(Integer pin) {
		// TODO Auto-generated method stub
		this.firstPin = pin;
	}

	@Override
	public void setSecondPin(Integer pin) {
		// TODO Auto-generated method stub
		this.secondPin = pin;
	}

}
