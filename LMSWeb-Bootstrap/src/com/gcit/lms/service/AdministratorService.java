package com.gcit.lms.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookLoansDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.Genre;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;

public class AdministratorService {
	
	//------------------------------------------Inserting Info.------------------------------------------------------------
	//Adding author
	public Integer createAuthorWithID(Author author) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer authorId = null;
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			authorId = adao.addAuthorWithID(author);
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		return authorId;
	}
	
	//Adding book.
	public Integer createBookWithID(Book book) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer bookId = null;
		try{
			BookDAO bdao = new BookDAO(conn);
			bookId = bdao.addBookWithID(book);
			conn.commit();
		}catch (Exception e){
			System.out.println(e);
			conn.rollback();
		}finally{
			conn.close();
		}
		return bookId;
	}
	
	//Adding library branch.
		public Integer createBranchWithID(LibraryBranch branch) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			Integer branchId = null;
			try{
				LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
				branchId = brdao.addBranchWithID(branch);
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}finally{
				conn.close();
			}
			return branchId;
		}
		
	//Adding Borrower.
		public Integer createBorrowerWithID(Borrower brr) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			Integer cardNo = null;
			try{
				BorrowerDAO brdao = new BorrowerDAO(conn);
				cardNo = brdao.addCardnoWithID(brr);
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}finally{
				conn.close();
			}
			return cardNo;
		}
	
	//------------------------------------------Fetching Info.------------------------------------------------------------
	
	//Fetching authors for each page.
	public List<Author> getAllAuthors(Integer pageNo) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAllAuthors(pageNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	
	//Fetching author by authorId
	public Author getAuthorByID(Integer authorId) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			return adao.readAuthorsByID(authorId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching books list.
	public List<Book> getAllBooks(Integer pageNo) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.readAllBooks();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching book by bookId
	public Book getBookByID(Integer bookId) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			return bdao.readBookByID(bookId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching branch list.
	public List<LibraryBranch> getAllBranch(Integer pageNo) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
			return brdao.readAllBranch();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching branch by branchId
	public LibraryBranch getBranchByID(Integer branchId) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			LibraryBranchDAO brdao = new LibraryBranchDAO(conn);
			return brdao.readBranchByID(branchId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching publishers list.
	public List<Publisher> getAllPublisher() throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			PublisherDAO pbdao = new PublisherDAO(conn);
			return pbdao.readAllPublisher();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	
	//Fetching genre list.
	public List<Genre> getAllGenre() throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			GenreDAO gndao = new GenreDAO(conn);
			return gndao.readAllGenre();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching borrower list.
	public List<Borrower> getAllBorrower(Integer pageNo) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.readAllBorrower();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}

	//Fetching borrower by cardNo
	public Borrower getBorrowerByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			return brdao.readBorrowerByID(cardNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching book loans by cardNo
	public List<BookLoans> getBookLoansByID(Integer cardNo) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BookLoansDAO bdao = new BookLoansDAO(conn);
			return bdao.readBookLoansByID(cardNo);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}
	
	//Fetching book copies by branchID
	public List<BookCopies> getBookCopiesByBranchID(Integer branchId) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BookCopiesDAO bdao = new BookCopiesDAO(conn);
			return bdao.readBookCopiesByBranchID(branchId);
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		return null;
	}

	
	//------------------------------------------Updating Info.------------------------------------------------------------
	//Editing author.
	public Integer editAuthor(Author author) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer authorId = null;
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			authorId = adao.updateAuthor(author);
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		return authorId;
	}
	
	//Edit book.
	public Integer editBook(Book book) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer bookId = null;
		try{
			BookDAO bdao = new BookDAO(conn);
			bookId = bdao.updateBook(book);
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		return bookId;
	}
	
	//Edit Branch.
	public Integer editBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer branchId = null;
		try{
			LibraryBranchDAO bdao = new LibraryBranchDAO(conn);
			branchId = bdao.updateBranch(branch);
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		return branchId;
	}
	
	//Editing borrower.
	public Integer editBorrower(Borrower brr) throws ClassNotFoundException, SQLException{
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer cardNo = null;
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			cardNo = brdao.updateBorrower(brr);
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		return cardNo;
	}

	//Editing dueDate.
		public void editDueDate(BookLoans bl) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			
			try{
				BookLoansDAO adao = new BookLoansDAO(conn);
				adao.updateDueDate(bl);
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}finally{
				conn.close();
			}
			return;
		}
	
	
	//------------------------------------------Deleting Info.------------------------------------------------------------
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			adao.deleteAuthor(author);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
	}
	
	public void deleteBook(Book book) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BookDAO bdao = new BookDAO(conn);
			bdao.deleteBook(book);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
	}
	
	public void deleteBranch(LibraryBranch branch) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			LibraryBranchDAO libdao = new LibraryBranchDAO(conn);
			libdao.deleteBranch(branch);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
	}
	
	public void deleteBorrower(Borrower brr) throws ClassNotFoundException, SQLException {
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			BorrowerDAO brdao = new BorrowerDAO(conn);
			brdao.deleteBorrower(brr);
			conn.commit();
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			conn.close();
		}
		
	}
	
	
	//------------------------------------------Other operation.------------------------------------------------------------
	//Inserting into table with common data.
	public void createCommonTable(String tableName,int val1, int val2) throws ClassNotFoundException, SQLException {
		
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		try{
			switch(tableName){
			case "tbl_book_authors" : AuthorDAO audao = new AuthorDAO(conn);
									  audao.addBookAuthors(val1,val2);
									  break;
									  
			case "tbl_book_genres" : GenreDAO gndao = new GenreDAO(conn);
			  						 gndao.addBookGenres(val1,val2); 
			  						 break;
				
			}
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
		
	}
	
	//Getting count of number of authors in the database.
	public Integer getAuthorCount() throws ClassNotFoundException, SQLException{
		
		ConnectionUtil c = new ConnectionUtil();
		Connection conn = c.getConnection();
		Integer count = null;
		try{
			AuthorDAO adao = new AuthorDAO(conn);
			count = adao.totalAuthorCount();
			conn.commit();
		}catch (Exception e){
			conn.rollback();
		}finally{
			conn.close();
		}
				
		return count;
	}
	
	//Getting count of number of books in the database.
		public Integer getBookCount() throws ClassNotFoundException, SQLException{
			
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			Integer count = null;
			try{
				BookDAO adao = new BookDAO(conn);
				count = adao.totalBookCount();
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}finally{
				conn.close();
			}
			
			return count;
		}
		
		//Getting count of number of library branch in the database.
		public Integer getBranchCount() throws ClassNotFoundException, SQLException{

			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			Integer count = null;
			try{
				LibraryBranchDAO adao = new LibraryBranchDAO(conn);
				count = adao.totalBranchCount();
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}finally{
				conn.close();
			}

			return count;
		}
		
		//Getting count of number of borrowers in the database.
		public Integer getBorrowerCount() throws ClassNotFoundException, SQLException{

			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			Integer count = null;
			try{
				BorrowerDAO adao = new BorrowerDAO(conn);
				count = adao.totalBorrowerCount();
				conn.commit();
			}catch (Exception e){
				conn.rollback();
			}finally{
				conn.close();
			}
				
			return count;
		}
	//-------------------------------------------------Search Info.-------------------------------------------------------
		public List<Author> getAllAuthorsByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			try{
				AuthorDAO adao = new AuthorDAO(conn);
				return adao.readAuthorsByName(searchString, pageNo);
			}catch (Exception e){
				e.printStackTrace();
				//conn.rollback();
			}finally{
				conn.close();
			}
			return null;
		}
		
		public List<Book> getAllBooksByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			try{
				BookDAO adao = new BookDAO(conn);
				return adao.readBooksByName(searchString, pageNo);
			}catch (Exception e){
				e.printStackTrace();
				//conn.rollback();
			}finally{
				conn.close();
			}
			return null;
		}
		
		
		public List<LibraryBranch> getAllBranchByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			try{
				LibraryBranchDAO adao = new LibraryBranchDAO(conn);
				return adao.readBranchByName(searchString, pageNo);
			}catch (Exception e){
				e.printStackTrace();
				//conn.rollback();
			}finally{
				conn.close();
			}
			return null;
		}
		
		
		public List<Borrower> getAllBorrowerByName(String searchString, Integer pageNo) throws ClassNotFoundException, SQLException{
			ConnectionUtil c = new ConnectionUtil();
			Connection conn = c.getConnection();
			try{
				BorrowerDAO adao = new BorrowerDAO(conn);
				return adao.readBorrowerByName(searchString, pageNo);
			}catch (Exception e){
				e.printStackTrace();
				//conn.rollback();
			}finally{
				conn.close();
			}
			return null;
		}
}
