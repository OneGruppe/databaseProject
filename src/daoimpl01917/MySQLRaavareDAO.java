package daoimpl01917;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connector01917.Connector;
import daointerfaces01917.DALException;
import daointerfaces01917.RaavareDAO;
import dto01917.RaavareDTO;

public class MySQLRaavareDAO implements RaavareDAO {

	@Override
	public RaavareDTO getRaavare(int raavareId) throws DALException {
		ResultSet rs = Connector.doQuery("SELECT * FROM raavare WHERE raavare_id = " + raavareId);

		try {
			if (!rs.first())
			{
				throw new DALException("Råvaren " + raavareId + " findes ikke");
			}
			return new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer"));
		} catch (SQLException e) {
			throw new DALException(e);
		}
	}

	@Override
	public List<RaavareDTO> getRaavareList() throws DALException {
		List<RaavareDTO> raavareList = new ArrayList<RaavareDTO>();
		ResultSet rs = Connector.doQuery("SELECT * FROM raavare");
		try {
			while(rs.next()) {
				raavareList.add(new RaavareDTO(rs.getInt("raavare_id"), rs.getString("raavare_navn"), rs.getString("leverandoer")));
			}
			
		} catch(SQLException e) {
			throw new DALException(e);
		}
		
		return raavareList;
	}

	@Override
	public void createRaavare(RaavareDTO raavare) throws DALException {
		Connector.doUpdate(
				"CALL add_raavare (" 
				+raavare.getRaavareId()+ ", '" +raavare.getRaavareNavn()+ "', '" +raavare.getLeverandoer()+ "')"
				);
		
	}

	@Override
	public void updateRaavare(RaavareDTO raavare) throws DALException {
		Connector.doUpdate(
				"CALL update_raavare(" +raavare.getRaavareId()+ ", '" +raavare.getRaavareNavn()+ 
				"', '" +raavare.getLeverandoer()+ "')"
				);
		
	}

}
