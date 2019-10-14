package training.adv.bowling.api;

public interface BowlingService {
	void save(BowlingGame game);
	BowlingGame load(Integer id);
	void remove(Integer id);

}
