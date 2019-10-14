package training.adv.bowling.api;

public interface BowlingGameDao{
    void save(BowlingGame domain);
    BowlingGame load(Integer id);
    boolean remove(Integer id);
}
