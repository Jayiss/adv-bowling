package training.adv.bowling.api;

import java.io.Serializable;

public interface Entity<K extends Serializable> extends Serializable {
	K getId();
	void setId(K id);
}
