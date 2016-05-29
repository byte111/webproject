package com.deva.webproj.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.asm.commons.TryCatchBlockSorter;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

import com.deva.webproj.vo.DataUpload;

public class DataUploadDAO {
	private JdbcTemplate jdbcTemplate;
	
	public DataUploadDAO() {
		jdbcTemplate = new JdbcTemplate(DataSource.getDataSource());
	}
	
	public int insertDataUploadRecord(final DataUpload dataUploadObj)
	{
		int result = 0;
		
		String sqlInsert = "INSERT INTO Dataupload(Correlationid,Senderid,Receiverid,sentdatetime) VALUES (?,?,?,?)";
		try {
			
			result = jdbcTemplate.execute(sqlInsert, new PreparedStatementCallback<Integer>() {
				
				

				public Integer doInPreparedStatement(PreparedStatement ps) throws SQLException, DataAccessException {
					// TODO Auto-generated method stub
					ps.setString(1, dataUploadObj.getCorrelationId());
					ps.setString(2,dataUploadObj.getSenderId());
					ps.setString(3, dataUploadObj.getReceiverId());					
					ps.setDate(4, new java.sql.Date(dataUploadObj.getSentDateTime().getTime()));
					
					return ps.executeUpdate();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return result;
	}
	
	public List<DataUpload> getAllDataUploadedList(final String receiverId)
	{
		String sqlSelect = "SELECT * FROM Dataupload WHERE receiverid ='"+receiverId+"'";
		RowMapper<DataUpload> rowMapper = new RowMapper<DataUpload>() {
			
			public DataUpload mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				DataUpload dataUpload = new DataUpload();
				dataUpload.setCorrelationId(rs.getString("CORRELATIONID"));
				dataUpload.setReceiverId(receiverId);
				dataUpload.setSenderId(rs.getString("SENDERID"));
				dataUpload.setSentDateTime(rs.getDate("SENTDATETIME"));
				return dataUpload;
			}
		};
		
		List<DataUpload> dataUploadList = jdbcTemplate.query(sqlSelect,rowMapper);
		return dataUploadList;
	}
	
	public static void main(String[] args) {
		/*DataUpload d = new DataUpload();
		d.setCorrelationId("1001");
		d.setReceiverId("1111");
		d.setSenderId("1003");
		d.setSentDateTime(new java.sql.Date(1000011));
		
		System.out.println(new DataUploadDAO().insertDataUploadRecord(d));*/
		System.out.println(new DataUploadDAO().getAllDataUploadedList("BLRDEV1002"));
	}

}
