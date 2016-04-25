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

import com.gcit.lms.entity.Book;
import com.gcit.lms.entity.BookCopies;
import com.gcit.lms.entity.LibraryBranch;
import com.gcit.lms.service.AdministratorService;
import com.gcit.lms.service.LibrarianService;

@WebServlet({ "/selectBranch", "/updateCopies"})

public class LibrarianServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;

	public LibrarianServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
			  							
		case "/selectBranch": selectBranch(request, response);
								break;
		  						
		default:				break;
		}
		
	}
	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String reqUrl = request.getRequestURI().substring(request.getContextPath().length(),
				request.getRequestURI().length());
		switch (reqUrl) {
			   				   
		case "/updateCopies": updateCopies(request, response);
									break;

		default:		   		break;
		}
	}
	
	//Select Branch method.
			private void selectBranch(HttpServletRequest request, HttpServletResponse response){
				int branchId = Integer.parseInt(request.getParameter("branchId"));
				AdministratorService service = new AdministratorService();
				List<BookCopies> bc = null;
				
				try {
					bc = service.getBookCopiesByBranchID(branchId);
				} catch (ClassNotFoundException | SQLException e1) {
					e1.printStackTrace();
				}
				request.setAttribute("bookCopies", bc);
				RequestDispatcher rd = request.getRequestDispatcher("updateBookCopies.jsp");
				try {
					rd.forward(request, response);
				} catch (ServletException | IOException e) {
					e.printStackTrace();
				}
			}

			
			//Updating changes made to author.
			private void updateCopies(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException {

				LibrarianService service = new LibrarianService();
				String returnPath = "/administrator.jsp";
				int bookId = Integer.parseInt(request.getParameter("bookId"));
				int branchId = Integer.parseInt(request.getParameter("branchId"));
				int noOfCopies = Integer.parseInt(request.getParameter("noOfCopies"));
				String addAuthorResult = "";

				if (bookId >0) {
					BookCopies bc=new BookCopies();
					bc.setNoOfCopies(noOfCopies);
					Book a = new Book();
					a.setBookId(bookId);
					bc.setBooks(a);
					LibraryBranch lib = new LibraryBranch();
					lib.setBranchId(branchId);
					bc.setBranch(lib);
					
					try {
						service.editBookCopies(bc);
						returnPath = "/librarianOperation.jsp";
						addAuthorResult = "Book Copies edited sucessfully.";
					} catch (ClassNotFoundException | SQLException e) {
						returnPath = "/updateBookCopies.jsp";
						addAuthorResult = "Book copies edit failed";
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

	}
