package training.adv.bowling.impl.zhuyurui;

import training.adv.bowling.api.BowlingGame;
import training.adv.bowling.api.BowlingGameEntity;
import training.adv.bowling.api.BowlingTurnEntity;
import training.adv.bowling.api.TurnKey;
import training.adv.bowling.impl.AbstractDao;

import java.sql.*;
import java.util.ArrayList;

public class BowlingGameDaoImpl extends AbstractDao<BowlingGameEntity, BowlingGame, Integer> {
    private Connection connection;
    public BowlingGameDaoImpl(Connection connection){
        this.connection=connection;
    }

    @Override
    protected void doSave(BowlingGameEntity entity) {
        String sqlGame="INSERT INTO games (id,maxPin,maxTurn) VALUES ("+entity.getId()+","+entity.getMaxPin()+","+entity.getMaxTurn()+");";
        String sqlTurn="";

        for(BowlingTurnEntity turnEntity:entity.getTurnEntities()){
            sqlTurn+="INSERT INTO turns (id, gameId, firstTurn, secondTurn) VALUES ("
                    +turnEntity.getId().getId()+","+turnEntity.getId().getForeignId()+","
                    + turnEntity.getFirstPin()+","+turnEntity.getSecondPin()+");";

        }
        String sql=sqlGame+sqlTurn;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected BowlingGameEntity doLoad(Integer id) {
        String sql1="select  gameId,t.id,firstTurn,secondTurn,maxPin,maxTurn  " +
                "from games g left join turns t on g.id=t.gameId " +
                " where gameId="+id+";";
        BowlingGameEntity bowlingGameEntity=null;
        ArrayList<BowlingTurnEntity> turnEntities=new ArrayList<>();
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql1);

            ResultSet rs=preparedStatement.executeQuery();
            while(rs.next()){
                bowlingGameEntity=new BowlingGameEntityImpl(id,rs.getInt("maxPin"),rs.getInt("maxTurn"));
                Integer firstPin=rs.getInt("firstTurn");
                Integer secondPin=rs.getInt("secondTurn");
                TurnKey turnKey=new TurnKeyImpl(rs.getInt("id"),rs.getInt("gameId"));
                BowlingTurnEntity turnEntity=new BowlingTurnEntityImpl(firstPin,secondPin,turnKey);
                turnEntities.add(turnEntity);
            }
            if(bowlingGameEntity==null){
                return null;
            }
            if(turnEntities==null||turnEntities.size()==0){
                bowlingGameEntity.setTurnEntities(null);
            }
            bowlingGameEntity.setTurnEntities(turnEntities.toArray(new BowlingTurnEntity[0]));

        }catch (Exception e){
            e.printStackTrace();
        }

        return bowlingGameEntity;
    }



    @Override
    protected BowlingGame doBuildDomain(BowlingGameEntity entity) {
        BowlingGame game=new BowlingGameImpl(entity);
        return game;
    }

    @Override
    public boolean remove(Integer key) {
        String sql="delete from games where id="+key+";";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return preparedStatement.executeUpdate()>=0;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


}
