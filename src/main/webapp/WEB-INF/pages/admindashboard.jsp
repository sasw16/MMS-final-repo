<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page isELIgnored="false"%>

<!DOCTYPE html>
<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="<c:url value="/resources/css/bootstrap.min.css" />"
	rel="stylesheet">
<link href="<c:url value="/resources/css/dashboard.css" />"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/boxicons@latest/css/boxicons.min.css" />
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p"
	crossorigin="anonymous">
	
</script>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript"
	src="https://cdn.datatables.net/1.10.2/js/jquery.dataTables.min.js"></script>
<script src="<c:url value="/resources/js/dashboard.js" />"></script>
<title>Admin Dashboard</title>
</head>


<body id="body-pd">

	<!-- Header -->
    <header class="header" id="header">
        <div class="container text-center">
            <div class="row h-100">
                <div class="col col-3"></div>
                <div class="col col-6 my-auto">
                    <h3 class="text-white">Admin Dashboard</h3>
                </div>
                <div class="col col-3 h-100 text-end">
                    <span class="text-white"><i class='bx bxs-user m-1'></i>
                        ${user.userName}
                    </span>
                    <br>
                    <small class="text-white">Admin ID: ${user.a_id}</small>
                </div>
            </div>
        </div>
    </header>
	<!-- Sidebar -->
	<div class="l-navbar" id="nav-bar">
		<nav class="nav">
			<div>
				<div class="nav_list">
					<a class="header_toggle nav_link"> <i
						class='bx bx-menu nav_icon' id="header-toggle"></i></a> <a
						href="admindashboard" class=" nav_link active"> <i
						class='bx bx-grid-alt nav_icon'></i> <span class="nav_name">Dashboard</span>
					</a> <a href="addadmin" class="nav_link"> <i
						class='bx bx-user-plus nav_icon'></i> <span class="nav_name">Add
							Admin</span>
					</a> <a href="adddoctor" class="nav_link"> <i
						class='bx bx-user-plus nav_icon'></i> <span class="nav_name">Add
							Doctor</span>
					</a> <a href="report" class="nav_link"> <i class='bx bxs-report'></i>
						<span class="nav_name">Generate report</span>
					</a> <a href="editdoctor" class=" nav_link"> <i
						class='bx bx-edit nav_icon'></i> <span class="nav_name">Edit
							Doctor</span>
					</a>
				</div>
			</div>
			<a href="logout" class="nav_link"> <i
				class='bx bx-log-out nav_icon'></i> <span class="nav_name">Logout</span>
			</a>
		</nav>
	</div>
	<!-- Sidebar End -->


	<!--Container Main start-->
	<div
		class="container body-container text-center justify-content-md-center">

		<h4>DOCTOR DETAILS</h4>
		<hr>
		<c:if test="${error}">
			<div class="error-text m-2">
				<small>Cannot disable as the doctor has appointment</small>
			</div>
		</c:if>
		<c:if test="${success} m-2">
			<div class="text-success">
				<small>Disabled Successfully</small>
			</div>
		</c:if>
		<c:if test="${success1} m-2">
			<div class="text-success">
				<small>Enabled Successfully</small>
			</div>
		</c:if>
		<c:if test="${error1} m-2">
			<div class="error-text">
				<small>Already Enabled</small>
			</div>
		</c:if>

		<!-- Appointments Table -->
		<div class="table-responsive p-2 m-2">
			<table class="table table-striped text-center">
				<thead>
					<tr>
						<th scope="col">Doctor ID</th>
						<th scope="col">Doctor name</th>
						<th scope="col">Specialization</th>
						<th scope="col">Fees</th>
						<th scope="col">Status</th>
						<th scope="col">Enable/Disable</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<c:forEach items="${allDoctors}" var="doctor">
							<tr>
								<td>${doctor.doc_id }</td>
								<td>Dr. ${doctor.name }</td>
								<td>${doctor.specialisation}</td>
								<td>${doctor.fees }</td>
								<td>${doctor.status}</td>
								<c:set var="status" value="${doctor.status}"/>
								<td>
									<c:if test="${fn:substring(status, 0, 8) == 'Inactive'}">
										<form action="enableDoctor" class="d-inline-block">
											<div class="form-group d-none">
												<input type="hidden" value="${doctor.doc_id }" name="doc_id">
											</div>
											<button type="submit" class="btn btn-sm btn-success m-1"><i class='bx bxs-user-plus'></i> Enable</button>
										</form>
									</c:if>
									<c:if test="${fn:substring(status, 0, 6) == 'Active'}">
										<form action="disableDoctor" class="d-inline-block">
											<div class="form-group d-none">
												<input type="hidden" value="${doctor.doc_id }" name="doc_id">
											</div>
											<button type="submit" class="btn btn-sm btn-danger m-1"><i class='bx bxs-user-minus'></i> Disable</button>
										</form>
									</c:if>
								</td>
							</tr>
						</c:forEach>
					</tr>
				</tbody>
			</table>

		</div>
	</div>
	<!--Container Main end-->
</body>

</html>