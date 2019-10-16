package training.adv.bowling.api;


public interface BowlingGameDao{
    void save(BowlingGame domain);
    BowlingGame load(String id);
    boolean remove(String id);
}
