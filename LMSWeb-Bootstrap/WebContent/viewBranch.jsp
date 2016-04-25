<%@ include file="include.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="com.gcit.lms.entity.LibraryBranch"%>
<%@ page import="com.gcit.lms.service.AdministratorService"%>
<%
	AdministratorService service = new AdministratorService();
	Integer branchCount = service.getBranchCount();
	
    List<LibraryBranch> branch = new ArrayList<LibraryBranch>();
	if (request.getAttribute("branch") != null) {
		branch = (List<LibraryBranch>)request.getAttribute("branch");
		branchCount = branch.size();
	}else{
		branch = service.getAllBranch(1);	
	}
%>

<script type="text/javascript">
	//Delete author function.
	function deleteBranch(branchId) {

		$.ajax({
			url : "deleteBranch",
			data : {
				branchId : branchId
			}
		}).done(function(data) {
			$('#branchTable').html(data);
		});
	}
	
	//Search branch function.
	function searchBranch(searchString){
	
	$.ajax({
		  url: "searchBranch",
		  data:{
			  branchId: searchString
		  }
		}).done(function(data) {
		  $('#searchResults').html(data);
		});
	}
	
	
</script>

<title>LMS</title>

<form action="searchBranch" method="post">
	<div class="input-group">
		<p><input type="text" class="form-control" placeholder="SEARCH"
			aria-describedby="basic-addon1" name="searchString" onchange="searchBranch()"></p>
		<p><button class="btn btn btn-info" onclick="searchBranch();">Search</button></p>
	</div>
</form>

<div id="searchResults">

	<nav>
	<ul class="pagination">
		<li><a href="#" aria-label="Previous"> <span
				aria-hidden="true">&laquo;</span>
		</a></li>
		<%
			if (branchCount != null && branchCount > 0) {
				int pageNo = branchCount % 10;
				int pages = 0;
				if (pageNo == 0) {
					pages = branchCount / 10;
				} else {
					pages = branchCount / 10 + 1;
				}
				for (int i = 1; i <= pages; i++) {
		%>
		<li><a href="pageBranch?pageNo=<%=i%>"><%=i%></a></li>
		<%
			}

			}
		%>
		<li><a href="#" aria-label="Next"> <span aria-hidden="true">&raquo;</span>
		</a>
	</ul>
	</nav>
	
	<div class="row">
		<div class="col-md-6">
			<table border="2" id="branchTable" class="table">
				<tr>
					<th>Branch Name</th>
					<th>Branch Address</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
				<tr>
					<%
						for (LibraryBranch b : branch) {
					%>
					<td>
						<%
							out.println(b.getBranchName());
						%>
					</td>
					<td>
						<%
							out.println(b.getBranchAddress());
						%>
					</td>
					<td align="center"><button type="button"
							class="btn btn btn-primary" data-toggle="modal"
							data-target="#myModal1"
							href="editBranch?branchId=<%=b.getBranchId()%>">EDIT</button></td>


					<td align="center"><button type="button"
							class="btn btn-sm btn-danger"
							onclick="deleteBranch(<%=b.getBranchId()%>)">DELETE</button></td>

				</tr>
				<%
					}
				%>
			</table>
		</div>
	</div>
</div>

	<div id="myModal1" class="modal fade" tabindex="-1" role="dialog"
		aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg">
			<div class="modal-content"></div>
		</div>
	</div>
	
