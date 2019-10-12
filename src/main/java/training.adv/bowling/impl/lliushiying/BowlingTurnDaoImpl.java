package training.adv.bowling.impl.lliushiying;

import training.adv.bowling.api.BowlingTurn;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class BowlingTurnDaoImpl extends AbstractDao<BowlingTurnEntity, BowlingTurn, TurnKey> {

    private static Connection conn;
    public BowlingTurnDaoImpl(Connection connection){
        conn= connection;
    }

    @Override
    protected void doSave(BowlingTurnEntity entity) {
        //如果相等就不加
        if(doLoad(entity.getId())!=null){
            return;
        }
        String sql="insert into turns_table(turn_id,firstpin,secondpin,game_id)" +
                "values(?,?,?,?)";
        try {
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, entity.getId().getId());
            pstm.setObject(2, entity.getFirstPin());
            pstm.setObject(3, entity.getSecondPin());
            pstm.setObject(4, entity.getId().getForeignId());
            pstm.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    protected BowlingTurnEntity doLoad(TurnKey id) {
        int turn_id=id.getId();
        int game_id=id.getForeignId();
        String sql="select * from turns_table where turn_id=? and game_id=?";
        BowlingTurnEntity bowlingTurnEntity=null;
        try{
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, turn_id);
            pstm.setObject(2, game_id);
            ResultSet rs=pstm.executeQuery();
            while(rs.next()){
                bowlingTurnEntity=new BowlingTurnEntityImpl();
                TurnKey turnKey=new TurnKeyImpl(rs.getInt("turn_id"),rs.getInt("game_id"));
                bowlingTurnEntity.setId(turnKey);
                bowlingTurnEntity.setFirstPin(rs.getInt("firstpin"));
                bowlingTurnEntity.setSecondPin(rs.getInt("secondpin"));

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bowlingTurnEntity;
    }

    @Override
    protected BowlingTurn doBuildDomain(BowlingTurnEntity entity) {
        BowlingTurn turn=new BowlingTurnImpl(entity);
        return turn;
    }

    @Override
    public boolean remove(TurnKey key) {
        String sql="delete from turns_table where turn_id=? and game_id=?";
        try{
            PreparedStatement pstm = conn.prepareStatement(sql);
            pstm.setObject(1, key.getId());
            pstm.setObject(2, key.getForeignId());
            return pstm.executeUpdate()>=0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
