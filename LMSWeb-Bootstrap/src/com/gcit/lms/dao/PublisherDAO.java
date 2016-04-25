package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Publisher;

public class PublisherDAO extends BaseDAO {

	public PublisherDAO(Connection conn) {
		super(conn);
		
	}
	
	//Reading publishers list.
	@SuppressWarnings("unchecked")
	public List<Publisher> readAllPublisher() throws ClassNotFoundException, SQLException {
		return (List<Publisher>) readAll("select * from tbl_publisher", null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		List<Publisher> publisher = new ArrayList<Publisher>();
		BookDAO bdao = new BookDAO(getConnection());
		
		while(rs.next()){
			Publisher b = new Publisher();
			b.setPublisherId(rs.getInt("publisherId"));
			b.setPublisherName(rs.getString("publisherName"));
			b.setPublisherAddress(rs.getString("publisherAddress"));
			b.setPublisherPhone(rs.getString("publisherPhone"));
			
			if ((b.getBooks() != null && b.getBooks().size() > 0)){
			try {
				for(Book bo: b.getBooks())
					b.setBooks((List<Book>) bdao.readFirstLevel("select * from tbl_book where bookId = ?)", new Object[] {bo.getBookId()}));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		   }
			publisher.add(b);
		}
		return publisher;
	}

	@Override
	public List<Publisher> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Publisher> publisher = new ArrayList<Publisher>();
				
		while(rs.next()){
			Publisher b = new Publisher();
			b.setPublisherId(rs.getInt("publisherId"));
			b.setPublisherName(rs.getString("publisherName"));
			b.setPublisherAddress(rs.getString("publisherAddress"));
			b.setPublisherPhone(rs.getString("publisherPhone"));
			
			publisher.add(b);
		}
		return publisher;
	}

}
