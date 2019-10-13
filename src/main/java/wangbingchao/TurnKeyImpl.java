package wangbingchao;

import training.adv.bowling.api.TurnKey;

public class TurnKeyImpl implements TurnKey {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((foreignId == null) ? 0 : foreignId.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	

	private Integer id;
	private Integer foreignId;
	public TurnKeyImpl(Integer foreignId) {
		this.foreignId = foreignId;
	}
	public TurnKeyImpl(Integer id,Integer foreignId) {
		this.id = id;
		this.foreignId = foreignId;
	}
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	@Override
	public Integer getForeignId() {
		// TODO Auto-generated method stub
		return foreignId;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TurnKeyImpl other = (TurnKeyImpl) obj;
		if (foreignId == null) {
			if (other.foreignId != null)
				return false;
		} else if (!foreignId.equals(other.foreignId))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
