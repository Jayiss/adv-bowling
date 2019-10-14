package training.adv.bowling.impl.shike;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey{

	Integer id;
	Integer fid;
	
	public TurnKeyImpl(int id, int fid) {
		this.id=id;
		this.fid=fid;
	}

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public Integer getForeignId() {
		return fid;
	}

}
