package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Author;
import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.entity.Publisher;
import com.gcit.lms.service.AdministratorService;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet({ "/addAuthor", "/viewAuthor", "/addBook", "/editAuthor", "/deleteAuthor", "/updateAuthor",
	"/editBook", "/updateBook", "/deleteBook", "/addBranch", "/editBranch", "/deleteBranch", "/updateBranch",
	"/updateBorrower","/deleteBorrower", "/editBorrower", "/addBorrower", "/validateBorrower", "/editDate", "/pageAuthors",
	"/pageBranch", "/pageBook", "/pageBorrower", "/searchAuthors", "/searchBooks", "/searchBranch", "/searchBorrower"})
public class AdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AdminServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
				
		case "/deleteAuthor": deleteAuthor(request, response);
							  break;
		
		case "/editAuthor": editAuthor(request, response);
							break;
							
		case "/deleteBook": deleteBook(request, response);
		  					  break;
		  
		case "/editBook": 	editBook(request, response);
							break;
							
		case "/editBranch": editBranch(request, response);
							break;
		
		case "/deleteBranch": deleteBranch(request, response);
							  break;
							  
		case "/editBorrower": editBorrower(request, response);
							  break;

		case "/deleteBorrower": deleteBorrower(request, response);
		  						break;
		  						
		case "/validateBorrower": validateBorrower(request, response);
		  							break;
		  							
		case "/editDate": editDate(request, response);
						 break;
						 
		case "/pageAuthors": pageAuthors(request, response);
							 break;
							 
		case "/pageBranch": pageBranch(request, response);
							break;
							
		case "/pageBook": pageBook(request, response);
						  break;
						  
		case "/pageBorrower": pageBorrower(request, response);
							  break;
							  
		default:				break;
		}
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		case "/addAuthor": 		addAuthor(request, response);
						   		break;
						   
		case "/addBook":   		addBook(request, response);
		   				   		break;
		   				   
		case "/updateAuthor": 	try {
				updateAuthor(request, response);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		   					  	break;
		   					  
		case "/deleteAuthor": 	deleteAuthor(request, response);
		  					  	break;
		  					  	
		case "/updateBook": 	updateBook(request, response);
		  						break;
		  						
		case "/deleteBook": 	deleteBook(request, response);
		  						break;
		
		case "/addBranch":		addBranch(request, response);
								break;
								
		case "/updateBranch": 	updateBranch(request, response);
								break;
								
		case "/addBorrower":	addBorrower(request, response);
								break;
								
		case "/updateBorrower": updateBorrower(request, response);
								break;
								
		case "/deleteBorrower": deleteBorrower(request, response);
								break;
								
		case "/searchAuthors": searchAuthors(request, response);
								break;
								
		case "/searchBooks": searchBooks(request, response);
							break;
							
		case "/searchBranch": searchBranch(request, response);
							 break;
							 
		case "/searchBorrower": searchBorrower(request, response);
		 					  break;
			   				   
		default:		   		break;
		}
	}

	
	//-------------------------------------------Author functions-------------------------------------------------------------------
	//get method of delete author.
	private void deleteAuthor(HttpServletRequest request, HttpServletResponse response) {
		Integer authorId = Integer.parseInt(request.getParameter("authorId"));
		AdministratorService service = new AdministratorService();
		StringBuilder str = new StringBuilder();
		try {
			Author author = new Author();
			author.setAuthorId(authorId);
			service.deleteAuthor(author);
			List<Author> authors = service.getAllAuthors(null);
			
			str.append("<tr><th>Author Name</th><th>Book Title</th><th>Edit</th><th>Delete</th></tr>");
			for(Author a: authors){
				str.append("<tr><td>"+a.getAuthorName()+"</td><td>Book Name</td>");
				str.append("<td><button type='button' onclick='javascript:location.href='editAuthor?authorId="+a.getAuthorId()+"''>EDIT</button><td>"
						+ "<button type='button' onclick='javascript:location.href='deleteAuthor?authorId="+a.getAuthorId()+"''>DELETE</button><td>");
			}
		} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
		}
		request.setAttribute("result", "Author Deleted Sucessfully");
		RequestDispatcher rd = request.getRequestDispatcher("viewAuthor.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
		try {
			response.getWriter().append(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	//Adding author to database.
	private void addAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = 0,authorId =0;
		AdministratorService service = new AdministratorService();
		String returnPath = "/administrator.jsp";
		String authorName = request.getParameter("authorName");
		String addAuthorResult = "";
		
		if (authorName != null && authorName.length() > 3 && authorName.length() < 45) {
			Author a = new Author();
			a.setAuthorName(authorName);
			try {
				authorId = service.createAuthorWithID(a);
				
				if (bookId != 0){
					bookId = Integer.parseInt(request.getParameter("bookId"));
					service.createCommonTable("tbl_book_authors",bookId,authorId);
				}
				returnPath = "/viewAuthor.jsp";
				addAuthorResult = "Author added sucessfully.";
			} catch (ClassNotFoundException | SQLException e) {
				returnPath = "/addauthor.jsp";
				addAuthorResult = "Author add failed";
				e.printStackTrace();
			}
		} else {
			returnPath = "/addauthor.jsp";
			addAuthorResult = "Author Name cannot be empty or more than 45 chars in length";
		}
		RequestDispatcher rd = request.getRequestDispatcher(returnPath);
		request.setAttribute("result", addAuthorResult);
		rd.forward(request, response);
	}
	
	//Edit author method.
		private void editAuthor(HttpServletRequest request, HttpServletResponse response){
			int authorId = Integer.parseInt(request.getParameter("authorId"));
			AdministratorService service = new AdministratorService();
			Author author = null;
			
			try {
				author = service.getAuthorByID(authorId);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("author", author);
			RequestDispatcher rd = request.getRequestDispatcher("updateAuthor.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}

		
	//Updating changes made to author.
	private void updateAuthor(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ClassNotFoundException, SQLException {
		
		AdministratorService service = new AdministratorService();
		String returnPath = "/administrator.jsp";
		int authorId = Integer.parseInt(request.getParameter("authorId"));
		String authorName = request.getParameter("authorName");
		String[] bookList = request.getParameterValues("title");
		String[] bookIdList = request.getParameterValues("bookId");
		String addAuthorResult = "";
		
		if (authorName != null && authorName.length() > 3 && authorName.length() < 45) {
			Author a = new Author();
			a.setAuthorName(authorName);
			a.setAuthorId(authorId);
			
			for(int i=0;i<bookList.length;i++){
				Book b = new Book();
				b.setBookId(Integer.parseInt(bookIdList[i]));
				b.setTitle(bookList[i]);
				service.editBook(b);
			}
			try {
				service.editAuthor(a);
				returnPath = "/viewAuthor.jsp";
				addAuthorResult = "Author edited sucessfully.";
			} catch (ClassNotFoundException | SQLException e) {
				returnPath = "/addauthor.jsp";
				addAuthorResult = "Author edit failed";
				e.printStackTrace();
			}
		} else {
			returnPath = "/addauthor.jsp";
			addAuthorResult = "Author Name cannot be empty or more than 45 chars in length";
		}
		RequestDispatcher rd = request.getRequestDispatcher(returnPath);
		request.setAttribute("result", addAuthorResult);
		rd.forward(request, response);
	}
	
	
	
	
	
	//-------------------------------------------Book functions-------------------------------------------------------------------
	//Adding book to database.
	private void addBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		AdministratorService service = new AdministratorService();
		String returnPath = "/administrator.jsp";
		String title = request.getParameter("title");
		String[] authorId = (request.getParameterValues("authorId"));
		String publisherId = (request.getParameter("publisherId"));
		String[] genre_id= (request.getParameterValues("genre_id"));
		String addBookResult = "";
		
		if ((title != null && title.length() > 3 && title.length() < 45)&& (authorId!=null) && (publisherId != null) && (genre_id!=null)) {
			Book b = new Book();
			b.setTitle(title);
			Publisher pu = new Publisher();
			pu.setPublisherId(Integer.parseInt(publisherId));
			b.setPublisher(pu);
			
			try {
				service.createBookWithID(b);
				int bookId = b.getBookId();
				for(String genreId: genre_id ){
					service.createCommonTable("tbl_book_genres",Integer.parseInt(genreId),bookId);
				}
				for(String author: authorId){
					service.createCommonTable("tbl_book_authors", bookId, Integer.parseInt(author));
				}
				
				returnPath = "/administrator.jsp";
				addBookResult = "Book added sucessfully.";
			} catch (ClassNotFoundException | SQLException e) {
				returnPath = "/addauthor.jsp";
				addBookResult = "Book add failed";
				e.printStackTrace();
			}
		} else {
			returnPath = "/addbook.jsp";
			addBookResult = "Book title/author/publisher/genre cannot be empty.";
		}
		RequestDispatcher rd = request.getRequestDispatcher(returnPath);
		request.setAttribute("result", addBookResult);
		rd.forward(request, response);
	}
	
	//Updating changes made to book.
	private void updateBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		AdministratorService service = new AdministratorService();
		String returnPath = "/administrator.jsp";
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");
		String addAuthorResult = "";
		
		if (title != null && title.length() > 3 && title.length() < 45) {
			Book a = new Book();
			a.setTitle(title);
			a.setBookId(bookId);
			try {
				service.editBook(a);
				returnPath = "/viewBook.jsp";
				addAuthorResult = "Book edited sucessfully.";
			} catch (ClassNotFoundException | SQLException e) {
				returnPath = "/addauthor.jsp";
				addAuthorResult = "Book edit failed";
				e.printStackTrace();
			}
		} else {
			returnPath = "/addbook.jsp";
			addAuthorResult = "Author Name cannot be empty or more than 45 chars in length";
		}
		RequestDispatcher rd = request.getRequestDispatcher(returnPath);
		request.setAttribute("result", addAuthorResult);
		rd.forward(request, response);
	}
	
	//Edit author method.
	private void editBook(HttpServletRequest request, HttpServletResponse response){
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		AdministratorService service = new AdministratorService();
		Book book = null;
		
		try {
			book = service.getBookByID(bookId);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("book", book);
		RequestDispatcher rd = request.getRequestDispatcher("updateBook.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
		}
	}
	
	//get method of delete author.
		private void deleteBook(HttpServletRequest request, HttpServletResponse response) {
			Integer bookId = Integer.parseInt(request.getParameter("bookId"));
			AdministratorService service = new AdministratorService();
			StringBuilder str = new StringBuilder();
			try {
				Book book = new Book();
				book.setBookId(bookId);
				service.deleteBook(book);
				List<Book> books = service.getAllBooks(null);
				
				str.append("<tr><th>Book Title</th><th>Edit</th><th>Delete</th></tr>");
				for(Book a: books){
					str.append("<tr><td>"+a.getTitle()+"</td>");
					str.append("<td><button type='button' onclick='javascript:location.href='editBook?bookId="+a.getBookId()+"''>EDIT</button><td>"
							+ "<button type='button' onclick='javascript:location.href='deleteBook?bookId="+a.getBookId()+"''>DELETE</button><td>");
				}
			} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
			}
			request.setAttribute("result", "Book Deleted Sucessfully");
			RequestDispatcher rd = request.getRequestDispatcher("viewBook.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			try {
				response.getWriter().append(str.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		//-------------------------------------------Branch functions-------------------------------------------------------------------
		//Adding Branch to database.
		private void addBranch(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			AdministratorService service = new AdministratorService();
			String returnPath = "/administrator.jsp";
			String branchName = request.getParameter("branchName");
			String branchAddress = request.getParameter("branchAddress");
			String addBranchResult = "";
			
			if ((branchName != null && branchName.length() > 3 && branchName.length() < 45) && (branchAddress != null && branchAddress.length() > 3 && branchAddress.length() < 45) ) {
				LibraryBranch a = new LibraryBranch();
				a.setBranchName(branchName);
				a.setBranchAddress(branchAddress);
				try {
					service.createBranchWithID(a);
					returnPath = "/viewBranch.jsp";
					addBranchResult = "Branch added sucessfully.";
				} catch (ClassNotFoundException | SQLException e) {
					returnPath = "/addauthor.jsp";
					addBranchResult = "Branch add failed";
					e.printStackTrace();
				}
			} else {
				returnPath = "/addBranch.jsp";
				addBranchResult = "Branch Name cannot be empty or more than 45 chars in length";
			}
			RequestDispatcher rd = request.getRequestDispatcher(returnPath);
			request.setAttribute("result", addBranchResult);
			rd.forward(request, response);
		}
		
		//Edit branch method.
		private void editBranch(HttpServletRequest request, HttpServletResponse response){
			int branchId = Integer.parseInt(request.getParameter("branchId"));
			AdministratorService service = new AdministratorService();
			LibraryBranch branch = null;

			try {
				branch = service.getBranchByID(branchId);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("branch", branch);
			RequestDispatcher rd = request.getRequestDispatcher("updateBranch.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}
		
		//Updating changes made to branch.
		private void updateBranch(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			AdministratorService service = new AdministratorService();
			String returnPath = "/administrator.jsp";
			int branchId = Integer.parseInt(request.getParameter("branchId"));
			String branchName = request.getParameter("branchName");
			String addBranchResult = "";
			
			if (branchName != null && branchName.length() > 3 && branchName.length() < 45) {
				LibraryBranch a = new LibraryBranch();
				a.setBranchName(branchName);
				a.setBranchId(branchId);
				try {
					service.editBranch(a);
					returnPath = "/viewBranch.jsp";
					addBranchResult = "Branch edited sucessfully.";
				} catch (ClassNotFoundException | SQLException e) {
					returnPath = "/addBranch.jsp";
					addBranchResult = "Branch edit failed";
					e.printStackTrace();
				}
			} else {
				returnPath = "/addBranch.jsp";
				addBranchResult = "Branch Name/Address cannot be empty or more than 45 chars in length";
			}
			RequestDispatcher rd = request.getRequestDispatcher(returnPath);
			request.setAttribute("result", addBranchResult);
			rd.forward(request, response);
		}
		
		
		//Delete branch method.
		private void deleteBranch(HttpServletRequest request, HttpServletResponse response) {
			Integer branchId = Integer.parseInt(request.getParameter("branchId"));
			AdministratorService service = new AdministratorService();
			StringBuilder str = new StringBuilder();
			try {
				LibraryBranch branch = new LibraryBranch();
				branch.setBranchId(branchId);
				service.deleteBranch(branch);
				List<LibraryBranch> branches = service.getAllBranch(null);
				
				str.append("<tr><th>Branch Name</th><th>Edit</th><th>Delete</th></tr>");
				for(LibraryBranch a: branches){
					str.append("<tr><td>"+a.getBranchName()+"</td>");
					str.append("<td><button type='button' onclick='javascript:location.href='editBranch?branchId="+a.getBranchId()+"''>EDIT</button><td>"
							+ "<button type='button' onclick='javascript:location.href='deleteBranch?branchId="+a.getBranchId()+"''>DELETE</button><td>");
				}
			} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
			}
			request.setAttribute("result", "Branch Deleted Sucessfully");
			RequestDispatcher rd = request.getRequestDispatcher("viewBranch.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			try {
				response.getWriter().append(str.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		
		//-------------------------------------------Borrower functions-------------------------------------------------------------------
		//Edit borrower method.
		private void editBorrower(HttpServletRequest request, HttpServletResponse response){
			int cardNo = Integer.parseInt(request.getParameter("cardNo"));
			System.out.println("In editBorrower(AdminServlet) where cardNo : "+cardNo);
			AdministratorService service = new AdministratorService();
			Borrower brr = null;

			try {
				brr = service.getBorrowerByID(cardNo);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			request.setAttribute("borrower", brr);
			RequestDispatcher rd = request.getRequestDispatcher("updateBorrower.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}

		//get method of delete borrower.
		private void deleteBorrower(HttpServletRequest request, HttpServletResponse response) {
			Integer cardNo = Integer.parseInt(request.getParameter("cardNo"));
			AdministratorService service = new AdministratorService();
			StringBuilder str = new StringBuilder();
			try {
				Borrower brr = new Borrower();
				brr.setCardNo(cardNo);;
				service.deleteBorrower(brr);
				List<Borrower> borrowers = service.getAllBorrower(null);
				
				str.append("<tr><th>Author Name</th><th>Book Title</th><th>Edit</th><th>Delete</th></tr>");
				for(Borrower a: borrowers){
					str.append("<tr><td>"+a.getName()+"</td>");
					str.append("<td><button type='button' onclick='javascript:location.href='editBorrower?cardNo="+a.getCardNo()+"''>EDIT</button><td>"
							+ "<button type='button' onclick='javascript:location.href='deleteBorrower?cardNo="+a.getCardNo()+"''>DELETE</button><td>");
				}
			} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
			}
			request.setAttribute("result", "Borrower Deleted Sucessfully");
			RequestDispatcher rd = request.getRequestDispatcher("viewBorrower.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
			try {
				response.getWriter().append(str.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		//Updating changes made to borrower.
		private void updateBorrower(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			AdministratorService service = new AdministratorService();
			String returnPath = "/administrator.jsp";
			int cardNo = Integer.parseInt(request.getParameter("cardNo"));
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			String addBorrowerResult = "";
			
			if (name != null && name.length() > 3 && name.length() < 45) {
				Borrower a = new Borrower();
				a.setName(name);
				a.setAddress(address);
				a.setPhone(phone);
				a.setCardNo(cardNo);

				try {
					service.editBorrower(a);
					returnPath = "/viewBorrower.jsp";
					addBorrowerResult = "Borrower edited sucessfully.";
				} catch (ClassNotFoundException | SQLException e) {
					returnPath = "/addBorrower.jsp";
					addBorrowerResult = "Borrower edit failed";
					e.printStackTrace();
				}
			} else {
				returnPath = "/addBorrower.jsp";
				addBorrowerResult = "Borrower Name cannot be empty or more than 45 chars in length";
			}
			RequestDispatcher rd = request.getRequestDispatcher(returnPath);
			request.setAttribute("result", addBorrowerResult);
			rd.forward(request, response);
		}

		//Adding borrower to database.
		private void addBorrower(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			AdministratorService service = new AdministratorService();
			String returnPath = "/administrator.jsp";
			String name = request.getParameter("name");
			String address = request.getParameter("address");
			String phone = request.getParameter("phone");
			String addBorrowerResult = "";
			
			if ((name != null && name.length() > 3 && name.length() < 45) && (address != null && address.length() > 3 && address.length() < 45) && (phone != null && phone.length() > 3 && phone.length() < 45)) {
				Borrower br = new Borrower();
				br.setName(name);
				br.setAddress(address);
				br.setPhone(phone);
				
				try {
					service.createBorrowerWithID(br);
										
					returnPath = "/viewBorrower.jsp";
					addBorrowerResult = "Borrower added sucessfully.";
				} catch (ClassNotFoundException | SQLException e) {
					returnPath = "/addBorrower.jsp";
					addBorrowerResult = "Borrower add failed";
					e.printStackTrace();
				}
			} else {
				returnPath = "/addBorrower.jsp";
				addBorrowerResult = "Borrower Name cannot be empty or more than 45 chars in length";
			}
			RequestDispatcher rd = request.getRequestDispatcher(returnPath);
			request.setAttribute("result", addBorrowerResult);
			rd.forward(request, response);
		}
		
		//Validating borrower method.
		private void validateBorrower(HttpServletRequest request, HttpServletResponse response){
			int cardNo = Integer.parseInt(request.getParameter("cardNo"));
			AdministratorService service = new AdministratorService();
			List<BookLoans> brr = null;

			try {
				brr = service.getBookLoansByID(cardNo);
			} catch (ClassNotFoundException | SQLException e1) {
				e1.printStackTrace();
			}
			if (brr != null){
			request.setAttribute("bookLoans", brr);
			request.setAttribute("cardNo", cardNo);
			RequestDispatcher rd = request.getRequestDispatcher("borrowerBooks.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		}else{
				request.getRequestDispatcher("administrator.jsp");
			}
		}
		

		//Edit author method.
		private void editDate(HttpServletRequest request, HttpServletResponse response){
			String returnPath = "/administrator.jsp";
			String addBorrowerResult = "";
			int cardNo = Integer.parseInt(request.getParameter("cardNo"));
			int branchId = Integer.parseInt(request.getParameter("branchId"));
			int bookId = Integer.parseInt(request.getParameter("bookId"));
			
			AdministratorService service = new AdministratorService();
			BookLoans bl = new BookLoans();
			
			
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchId(branchId);
			bl.setBranch(lib);
			
			Book bo = new Book();
			bo.setBookId(bookId);
			bl.setBooks(bo);
			
			Borrower br = new Borrower();
			br.setCardNo(cardNo);
			bl.setBorrower(br);

			try {
				service.editDueDate(bl);
				returnPath = "/overRideDate.jsp";
				addBorrowerResult = "Date edited sucessfully.";
			} catch (ClassNotFoundException | SQLException e1) {
				returnPath = "/overRideDate.jsp";
				addBorrowerResult = "Date edit failed";
				e1.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher(returnPath);
			request.setAttribute("result", addBorrowerResult);
			try {
				rd.forward(request, response);
			} catch (ServletException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
		
		//----------------------------------------------Other functions-----------------------------------------------------
		//Pagination function.
		private void pageAuthors(HttpServletRequest request, HttpServletResponse response) {
			
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			AdministratorService service = new AdministratorService();
			try {
				List<Author> authors = service.getAllAuthors(pageNo);
				request.setAttribute("authors", authors);
				RequestDispatcher rd = request.getRequestDispatcher("viewAuthor.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		private void pageBook(HttpServletRequest request, HttpServletResponse response) {
			
			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			AdministratorService service = new AdministratorService();
			try {
				List<Book> book = service.getAllBooks(pageNo);
				request.setAttribute("book", book);
				RequestDispatcher rd = request.getRequestDispatcher("viewBook.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		private void pageBranch(HttpServletRequest request, HttpServletResponse response) {

			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			AdministratorService service = new AdministratorService();
			try {
				List<LibraryBranch> branch = service.getAllBranch(pageNo);
				request.setAttribute("branch", branch);
				RequestDispatcher rd = request.getRequestDispatcher("viewBranch.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
		
		private void pageBorrower(HttpServletRequest request, HttpServletResponse response) {

			Integer pageNo = Integer.parseInt(request.getParameter("pageNo"));
			AdministratorService service = new AdministratorService();
			try {
				List<Borrower> brr = service.getAllBorrower(pageNo);
				request.setAttribute("borrower", brr);
				RequestDispatcher rd = request.getRequestDispatcher("viewBorrower.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		}
		
		//-------------------------------------------Search functions.--------------------------------------------------------------------------------
		private void searchAuthors(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			AdministratorService service = new AdministratorService();
			String searchString = request.getParameter("searchString");
			List<Author> authors;
			try {
				authors = service.getAllAuthorsByName(searchString, 1);
				request.setAttribute("authors", authors);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/viewAuthor.jsp");
			rd.forward(request, response);
			
		}
		
		private void searchBooks(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			AdministratorService service = new AdministratorService();
			String searchString = request.getParameter("searchString");
			List<Book> book;
			try {
				book = service.getAllBooksByName(searchString, 1);
				request.setAttribute("book", book);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/viewBook.jsp");
			rd.forward(request, response);
			
		}
				
		private void searchBranch(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			AdministratorService service = new AdministratorService();
			String searchString = request.getParameter("searchString");
			List<LibraryBranch> branch;
			try {
				branch = service.getAllBranchByName(searchString, 1);
				request.setAttribute("branch", branch);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/viewBranch.jsp");
			rd.forward(request, response);
			
		}
		
		private void searchBorrower(HttpServletRequest request, HttpServletResponse response) 
				throws ServletException, IOException {
			AdministratorService service = new AdministratorService();
			String searchString = request.getParameter("searchString");
			List<Borrower> brr;
			try {
				brr = service.getAllBorrowerByName(searchString, 1);
				request.setAttribute("borrower", brr);
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher rd = request.getRequestDispatcher("/viewBorrower.jsp");
			rd.forward(request, response);
			
		}
}
