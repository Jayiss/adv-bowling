package training.adv.bowling.impl.zhangsan;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BowlingTurnDaoImpl extends AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> {
    private  Connection connection;
    public BowlingTurnDaoImpl(Connection connection){
        this.connection = connection;
    }
    @Override
    protected void doSave(BowlingTurnEntity entity) {
        int first = entity.getFirstPin();
        TurnKey key = entity.getId();
        int foreignId = key.getForeignId();
        int id = key.getId();
        try {
            Statement stmt = connection.createStatement();
            if(entity.getSecondPin()==null){
                Integer second = null;
                stmt.executeUpdate("insert into turn values("+id+","+foreignId+","+first+","+second+")");
            }else{
                Integer second = entity.getSecondPin();
                stmt.executeUpdate("insert into turn values("+id+","+foreignId+","+first+","+second+")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        BowlingTurnEntity bowlingTurnEntity = new BowlingTurnEntityImpl();
        String querySql = "select * from TURN where  id = ? and foreign_id= ?";
        try {
            PreparedStatement statement = connection.prepareStatement(querySql);
            statement.setInt(1,id.getId());
            statement.setInt(2,id.getForeignId());
            ResultSet rs = statement.executeQuery();
            rs.next();
            bowlingTurnEntity.setFirstPin(rs.getInt(3));
            bowlingTurnEntity.setSecondPin(rs.getInt(4));
            bowlingTurnEntity.setId(id);
            return bowlingTurnEntity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        return null;
    }

    @Override
    public boolean remove(TurnKey key) {
        String delStatement = "delete from turn where id = ? and foreign_id = ?";
        boolean isRemoved = false;
        try {
            PreparedStatement statement = connection.prepareStatement(delStatement);
            statement.setInt(1,key.getId());
            statement.setInt(2,key.getForeignId());
            isRemoved = statement.execute();
            return isRemoved;
        } catch (SQLException e) {
            e.printStackTrace();
            return isRemoved;
        }
    }
    public final List<BowlingTurnEntity> batchLoad(int foreignId) {
        return loadAllKey(foreignId).stream().map(this::doLoad).collect(Collectors.toList());
    }

    public final void batchRemove(int foreignId) {
        loadAllKey(foreignId).stream().forEach(this::remove);
    }
    protected List<TurnKey> loadAllKey(int foreignId) {
        List<TurnKey> turnKeyList = new ArrayList<>();
        try{
            String sql = "select id from TURN where foreign_id= ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1,foreignId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                int id = rs.getInt(1);
                turnKeyList.add(new TurnKeyImpl(id,foreignId));
            }
        }catch (SQLException sqlException){

        }
        return turnKeyList;
    }
}