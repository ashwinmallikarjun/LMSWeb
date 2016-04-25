package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;

public class GenreDAO extends BaseDAO{

	public GenreDAO(Connection conn) {
		super(conn);
	}
	
	//Reading genre list.
	@SuppressWarnings("unchecked")
	public List<Genre> readAllGenre() throws ClassNotFoundException, SQLException {
		return (List<Genre>) readAll("select * from tbl_genre", null);
	}
	
	//Inserting into tbl_book_genres
	public void addBookGenres(int genre_id, int bookId) throws ClassNotFoundException, SQLException {
		save("insert into tbl_book_genres (genre_id,bookId) values (?,?)", genre_id, bookId);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		List<Genre> genre = new ArrayList<Genre>();
		BookDAO bdao = new BookDAO(getConnection());
		
		while(rs.next()){
			Genre b = new Genre();
			b.setGenre_id(rs.getInt("genre_id"));
			b.setGenre_name(rs.getString("genre_name"));
			
			if ((b.getBooks() != null && b.getBooks().size() > 0)){
			try {
				for(Book bo: b.getBooks())
					b.setBooks((List<Book>) bdao.readFirstLevel("select * from tbl_book where bookId = ?)", new Object[] {bo.getBookId()}));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			}
			genre.add(b);
		}
		return genre;
	}

	@Override
	public List<Genre> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Genre> genre = new ArrayList<Genre>();
				
		while(rs.next()){
			Genre b = new Genre();
			b.setGenre_id(rs.getInt("genre_id"));
			b.setGenre_name(rs.getString("genre_name"));
						
			genre.add(b);
		}
		return genre;
	}

}
