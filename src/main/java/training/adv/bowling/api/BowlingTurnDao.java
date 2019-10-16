package training.adv.bowling.api;

import java.util.List;

public interface BowlingTurnDao {
    void save(BowlingTurn domain);
    List<BowlingTurnEntity> batchLoad(String foreignId);
    void batchRemove(String foreignId);
}
