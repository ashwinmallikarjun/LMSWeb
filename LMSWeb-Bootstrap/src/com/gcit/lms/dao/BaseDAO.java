package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public abstract class BaseDAO {

	private Connection conn;
	private Integer pageNo;
	private Integer pageSize;

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	public Connection getConnection(){
		return conn;
	}
	
	
	//Saving after performing update delete operation using objects.
	public void save(String query, Object[] vals) throws ClassNotFoundException, SQLException{
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		int count = 1;
		
		if(vals!=null){
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		pstmt.executeUpdate();
		return;
	}
	
	//Saving after performing update delete operation using PK's.
	public void save(String query, int val1, int val2) throws ClassNotFoundException, SQLException{
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		pstmt.setInt(1, val1);
		pstmt.setInt(2, val2);
		pstmt.executeUpdate();
	}
	
	//Inserting information into database.
	public Integer saveWithID(String query, Object[] vals) throws ClassNotFoundException, SQLException{
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
		int count = 1;
		
		if(vals!=null){
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		pstmt.executeUpdate();
		ResultSet rs = pstmt.getGeneratedKeys();
		
		if(rs.next()){
			return rs.getInt(1);
		}else{
			return null;
		}
	}
	
	//Reading information from the database to list only 10 authors.
	public List<?> readAll(String query, Object[] vals) throws SQLException, ClassNotFoundException{
		
		if (pageNo != null && pageNo >0) {
			query+=" LIMIT "+(pageNo - 1)*10+", 10";
		}
		
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		int count = 1;
		
		if(vals!=null){
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		
		return extractData(rs);
	}
	
	//Fetching total count of entries in a table.
	public Integer getCount(String query) throws ClassNotFoundException, SQLException {
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();

		if (rs.next()) {
			return rs.getInt(1);
		} else {
			return 0;
		}
	}

	//Second read method to avoid deadlock while single query tries to read multiple tables.
	public List<?> readFirstLevel(String query, Object[] vals) throws SQLException, ClassNotFoundException{
		Connection conn = getConnection();
		PreparedStatement pstmt = conn.prepareStatement(query);
		int count = 1;
		
		if(vals!=null){
			for(Object o: vals){
				pstmt.setObject(count, o);
				count++;
			}
		}
		ResultSet rs = pstmt.executeQuery();
		
		return extractDataFirstLevel(rs);
	}
	
	public abstract List<?> extractData(ResultSet rs) throws SQLException;
	public abstract List<?> extractDataFirstLevel(ResultSet rs) throws SQLException;
	
	
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
}
