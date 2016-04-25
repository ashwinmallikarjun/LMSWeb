package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Publisher;

public class BookDAO extends BaseDAO{

	public BookDAO(Connection conn) {
		super(conn);
	}

	public Integer addBookWithID(Book book) throws ClassNotFoundException, SQLException {
		return saveWithID("insert into tbl_book (title, pubId) values (?, ?)", new Object[] {book.getTitle(), book.getPublisher().getPublisherId()});
	}
	
	//Reading book by ID.
	public Book readBookByID(Integer bookId) throws ClassNotFoundException, SQLException{
		@SuppressWarnings("unchecked")
		List<Book> book = (List<Book>) readAll("select * from tbl_book where bookId = ?", new Object[] {bookId});
		if(book!=null && book.size() >0){
			return book.get(0);
		}
		return null;
	}
	
	//Search function for book by name.
	@SuppressWarnings("unchecked")
	public List<Book> readBooksByName(String name, int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		name = "%"+name+"%";
		return (List<Book>) readAll("select * from tbl_book where title like ?", new Object[] {name});
	}
	
	//Updating book by name.
	public Integer updateBook(Book book) throws ClassNotFoundException, SQLException{
		return saveWithID("UPDATE tbl_book SET title = ? WHERE bookId = ?", new Object[] {book.getTitle(),book.getBookId()});
		}
	
	//Deleting author.
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException{
		save("delete from tbl_book where bookId = ?", new Object[] {book.getBookId()});
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> readAllBooks() throws ClassNotFoundException, SQLException{
		return (List<Book>) readAll("select * from tbl_book", null);
	}
	
	@SuppressWarnings("unchecked")
	public List<Book> checkOutBookList(BookLoans bc) throws ClassNotFoundException, SQLException{
		return ((List<Book>) readAll("select * from tbl_book where bookId in (select bc.bookId from tbl_book_copies as bc where bc.branchId = ? and noOfCopies>0)", new Object[] {bc.getBranch().getBranchId()}));
	}
	
	//Reading bookLoans by cardNo.
	public List<Book> returnBookLoanList(BookLoans bc) throws ClassNotFoundException, SQLException{
		@SuppressWarnings("unchecked")
		List<Book> brr = (List<Book>) readAll("select * from tbl_book where bookId IN (select bookId from tbl_book_loans where cardNo = ? AND branchId = ?)", new Object[] {bc.getBorrower().getCardNo(),bc.getBranch().getBranchId()});
		if(brr!=null && brr.size() >0){
			return brr;
		}
		return null;
	}
	
	public Integer totalBookCount() throws ClassNotFoundException, SQLException {
		return getCount("select count(*) from tbl_book;");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<?> extractData(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		AuthorDAO adao = new AuthorDAO(getConnection());
		GenreDAO gdao = new GenreDAO(getConnection());
				
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(p);
			try {
				b.setAuthors((List<Author>) adao.readFirstLevel("select * from tbl_author where authorId IN (select authorId from tbl_book_authors where bookId = ?)", new Object[] {b.getBookId()}));
				b.setGenres((List<Genre>) gdao.readFirstLevel("select * from tbl_genre where genre_id IN (select genre_id from tbl_book_genres where bookId = ?)", new Object[] {b.getBookId()}));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			books.add(b);
		}
		return books;
	}
	
	@Override
	public List<Book> extractDataFirstLevel(ResultSet rs) throws SQLException {
		List<Book> books = new ArrayList<Book>();
		
		while(rs.next()){
			Book b = new Book();
			b.setBookId(rs.getInt("bookId"));
			b.setTitle(rs.getString("title"));
			Publisher p = new Publisher();
			p.setPublisherId(rs.getInt("pubId"));
			b.setPublisher(p);

			books.add(b);
		}
		return books;
	}


}
