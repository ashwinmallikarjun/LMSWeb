package com.gcit.lms.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookLoans;
import com.gcit.lms.entity.Borrower;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.BorrowerService;

@WebServlet({ "/checkBorrower", "/checkOutBook", "/checkOutBookSelect", "/returnBook","/returnBookSelect"})

public class BorrowerServlet extends HttpServlet{
	
	private static final long serialVersionUID = 1L;

	public BorrowerServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
		
		case "/checkBorrower" : checkBorrower(request, response);
								break;
								
		case "/checkOutBook" : checkOutBook(request, response);
		   						break;
		
		case "/checkOutBookSelect" : checkOutBookSelect(request, response);
									break;
									
		case "/returnBook" : returnBook(request, response);
							break;
							
		case "/returnBookSelect" : returnBookSelect(request,response);
									break;
									
		default:				break;
		
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
			


		default:		   		break;
		}
	}

	//Check for borrower validity
	private void checkBorrower(HttpServletRequest request, HttpServletResponse response){
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		BorrowerService service = new BorrowerService();
		Borrower bc = null;
		
		try {
			bc = service.getValidateCardNo(cardNo);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		request.setAttribute("cardNo", bc);
		RequestDispatcher rd = request.getRequestDispatcher("borrowerOperation.jsp");
		try {
			rd.forward(request, response);
		} catch (ServletException | IOException e) {
			e.printStackTrace();
			}
		}
	
	//Check out book function.
	private void returnBook(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<Book> bookLoanList = new ArrayList<Book>();
		BorrowerService service = new BorrowerService();
		int cardNo = Integer.parseInt(request.getParameter("cardNo"));
		int branchId = Integer.parseInt(request.getParameter("branchId"));
		if (cardNo > 0) {
			BookLoans bc=new BookLoans();
			
			Borrower br = new Borrower();
			br.setCardNo(cardNo);
			bc.setBorrower(br);
			
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchId(branchId);
			bc.setBranch(lib);
			
			try {
				bookLoanList = service.returnBookList(bc);
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			request.setAttribute("bookLoanList", bookLoanList);
			request.setAttribute("cardNo", cardNo);
			request.setAttribute("branchId", branchId);
			RequestDispatcher rd = request.getRequestDispatcher("/returnBookList.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				e.printStackTrace();
			}
		} 
	}
	
	//Check out book function.
		private void checkOutBook(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			
			List<Book> bookList = new ArrayList<Book>();
			BorrowerService service = new BorrowerService();
			int cardNo = Integer.parseInt(request.getParameter("cardNo"));
			int branchId = Integer.parseInt(request.getParameter("branchId"));
			if (cardNo > 0) {
				BookLoans bc=new BookLoans();
				
				Borrower br = new Borrower();
				br.setCardNo(cardNo);
				bc.setBorrower(br);
				
				LibraryBranch lib = new LibraryBranch();
				lib.setBranchId(branchId);
				bc.setBranch(lib);
				
				try {
					bookList = service.checkOutBookList(bc);
					
				} catch (ClassNotFoundException | SQLException e) {
					e.printStackTrace();
				}
				request.setAttribute("bookList", bookList);
				request.setAttribute("cardNo", cardNo);
				request.setAttribute("branchId", branchId);
				RequestDispatcher rd = request.getRequestDispatcher("/checkOutBookList.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			} 
		}
		
		//Return book phase I.
	private void checkOutBookSelect(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int bookId = 0,branchId =0,cardNo;
		BorrowerService service = new BorrowerService();
		String returnPath = "/administrator.jsp";
		bookId = Integer.parseInt(request.getParameter("bookId"));
		cardNo = Integer.parseInt(request.getParameter("cardNo"));
		branchId = Integer.parseInt(request.getParameter("branchId"));
		
		String addAuthorResult = "";
		
		if (cardNo > 0) {
			BookLoans bc=new BookLoans();
			
			Borrower br = new Borrower();
			br.setCardNo(cardNo);
			bc.setBorrower(br);
			
			LibraryBranch lib = new LibraryBranch();
			lib.setBranchId(branchId);
			bc.setBranch(lib);
			
			Book bo = new Book();
			bo.setBookId(bookId);
			bc.setBooks(bo);
			
			try {
				service.createBookLoans(bc);
				returnPath = "/borrowerCard.jsp";
				addAuthorResult = "Check Out sucessful.";
			} catch (ClassNotFoundException | SQLException e) {
				returnPath = "/borrowerCard.jsp";
				addAuthorResult = "Check Out failed";
				e.printStackTrace();
			}
		} else {
			returnPath = "/borrowerCard.jsp";
			addAuthorResult = "Task failed.";
		}
		RequestDispatcher rd = request.getRequestDispatcher(returnPath);
		request.setAttribute("result", addAuthorResult);
		rd.forward(request, response);
	}
	
	//Inserting book loaned.
		private void returnBookSelect(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			int bookId = 0,branchId =0,cardNo;
			BorrowerService service = new BorrowerService();
			String returnPath = "/administrator.jsp";
			bookId = Integer.parseInt(request.getParameter("bookId"));
			cardNo = Integer.parseInt(request.getParameter("cardNo"));
			branchId = Integer.parseInt(request.getParameter("branchId"));
			
			String addAuthorResult = "";
			
			if (cardNo > 0) {
				BookLoans bc=new BookLoans();
				
				Borrower br = new Borrower();
				br.setCardNo(cardNo);
				bc.setBorrower(br);
				
				LibraryBranch lib = new LibraryBranch();
				lib.setBranchId(branchId);
				bc.setBranch(lib);
				
				Book bo = new Book();
				bo.setBookId(bookId);
				bc.setBooks(bo);
				
				try {
					service.returnBookLoans(bc);
					returnPath = "/borrowerCard.jsp";
					addAuthorResult = "Check Out sucessful.";
				} catch (ClassNotFoundException | SQLException e) {
					returnPath = "/borrowerCard.jsp";
					addAuthorResult = "Check Out failed";
					e.printStackTrace();
				}
			} else {
				returnPath = "/borrowerCard.jsp";
				addAuthorResult = "Task failed.";
			}
			RequestDispatcher rd = request.getRequestDispatcher(returnPath);
			request.setAttribute("result", addAuthorResult);
			rd.forward(request, response);
		}
}

