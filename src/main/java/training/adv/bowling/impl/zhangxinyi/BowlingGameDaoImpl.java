package training.adv.bowling.impl.zhangxinyi;

import training.adv.bowling.api.*;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {
    private Connection conn;
    private Statement st;

    public BowlingGameDaoImpl(Connection connection) {
        try {
            conn = connection;
            st = conn.createStatement();
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        try {
            BowlingGameEntityImpl.uniqueId += 1;
            st.execute("INSERT INTO game (id, maxTurn) VALUES ('" + BowlingGameEntityImpl.uniqueId + "', '" +
                    entity.getMaxTurn() + "');");
            entity.setId(BowlingGameEntityImpl.uniqueId);
            TurnEntity[] turnEntities = entity.getTurnEntities();
            for (TurnEntity en : turnEntities) {
                BowlingTurnEntityImpl.uniqueId += 1;
                en.setId(new BowlingTurnKeyImpl(BowlingTurnEntityImpl.uniqueId, BowlingGameEntityImpl.uniqueId));
                int second;
                if (((BowlingTurnEntityImpl) en).getSecondPin() == null) {
                    second = -1;
                } else {
                    second = ((BowlingTurnEntityImpl) en).getSecondPin();
                }
                st.execute("INSERT INTO turn (id, firstPin, secondPin, foreignKey) VALUES ('"
                        + en.getId().getId() + "', '"
                        + ((BowlingTurnEntityImpl) en).getFirstPin() + "', '" + second + "', '"
                        + en.getId().getForeignId() + "')");
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM game WHERE id = '" + id + "'");
            conn.commit();
            while (rs.next()) {
                Integer lTurnMax = rs.getInt(2);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BowlingGameEntity en = new BowlingGameImpl().getEntity();
        en.setId(id);
        List<TurnKey> turnKeys = loadAllKey(id);
        BowlingTurnEntity[] turnEntities = new BowlingTurnEntityImpl[turnKeys.size()];
        for (int i = 0; i < turnKeys.size(); i++) {
            turnEntities[i] = doLoadTurn(turnKeys.get(i));
            System.out.println(turnEntities[i].getFirstPin() + ", " + turnEntities[i].getSecondPin());
        }
        en.setTurnEntities(turnEntities);
        return en;
    }

    private List<TurnKey> loadAllKey(int foreignId) {
        Integer lId = null;
        Integer lFirstPin = null;
        Integer lSecondPin = null;
        List<TurnKey> turnKeys = new ArrayList<>();
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM turn WHERE foreignKey = '" + foreignId + "'");
            conn.commit();
            while (rs.next()) {
                lId = rs.getInt(1);
                TurnKey key = new BowlingTurnKeyImpl(lId, foreignId);
                turnKeys.add(key);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return turnKeys;
    }

    private BowlingTurnEntity doLoadTurn(TurnKey id) {
        Integer lFirstPin = null;
        Integer lSecondPin = null;
        try {
            ResultSet rs = st.executeQuery("SELECT * FROM turn WHERE id = '" + id.getId() + "'");
            conn.commit();
            while (rs.next()) {
                lFirstPin = rs.getInt(2);
                lSecondPin = rs.getInt(3);
                if (lSecondPin == -1) {
                    lSecondPin = null;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        BowlingTurnEntity en = new BowlingTurnEntityImpl();
        en.setId(id);
        en.setFirstPin(lFirstPin);
        en.setSecondPin(lSecondPin);
        return en;
    }

    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        return ((BowlingGameEntityImpl) entity).getGame();
    }

    @Override
    public boolean remove(Integer key) {
        try {
            st.execute("DELETE FROM game WHERE id = '" + key + "'");
            conn.commit();
            List<TurnKey> turnKeys = loadAllKey(key);
            for (TurnKey turnKey : turnKeys) {
                st.execute("DELETE FROM turn WHERE id = '" + turnKey.getId() + "'");
                conn.commit();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
