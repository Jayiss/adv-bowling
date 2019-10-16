package training.adv.bowling.api;

public interface BowlingService {
	void save(BowlingGame game);
	BowlingGame load(String id);
	void remove(String id);
}
