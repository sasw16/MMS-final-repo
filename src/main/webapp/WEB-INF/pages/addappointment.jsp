<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<script type="text/javascript"
	src="<c:url value="/resources/js/dashboard.js" />"></script>
<title>Patient Dashboard</title>
</head>


<body id="body-pd">
	<!-- Header -->
    <header class="header" id="header">
        <div class="container text-center">
            <div class="row h-100">
                <div class="col col-3"></div>
                <div class="col col-6 my-auto">
                    <h3 class="text-white">Patient Dashboard</h3>
                </div>
                <div class="col col-3 h-100 text-end">
                    <span class="text-white"><i class='bx bxs-user m-1'></i>
                        ${user.name}
                    </span>
                    <br>
                    <small class="text-white">Patient ID: ${user.pid}</small>
                </div>
            </div>
        </div>
    </header>
	<div class="l-navbar" id="nav-bar">
		<nav class="nav">
			<div>
				<div class="nav_list">
					<a class="header_toggle nav_link"> <i
						class='bx bx-menu nav_icon' id="header-toggle"></i></a> <a
						href="patdash" class=" nav_link"> <i
						class='bx bx-grid-alt nav_icon'></i> <span class="nav_name">Dashboard</span>
					</a> <a href="patientprofile" class="nav_link"> <i
						class='bx bx-user nav_icon'></i> <span class="nav_name">My
							Profile</span>
					</a> <a href="editpatient" class=" nav_link"> <i
						class='bx bx-edit nav_icon'></i> <span class="nav_name">Edit
							Profile</span>
					</a> <a href="doctorlist" class="nav_link"> <i
						class='bx bx-group nav_icon'></i> <span class="nav_name">Doctors</span>
					</a> <a href="addappointment" class="nav_link active"> <i
						class='bx bx-calendar nav_icon'></i> <span class="nav_name">Book
							Appointment</span>
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
		<h4>BOOK APPOINTMENTS</h4>
		<small>You can book appointments for the next 7 days.</small>
		<hr>
		<!-- Add Appointments Form -->
		<form action="filterdoctor" method="post" autocomplete="off">
			<div class="row d-flex justify-content-center form-row text-start">
				<!-- <div class="form-group col-md-3">
                    <label for="date" class="form-label">Date</label>
                   <input type="date" name="date">
                </div> -->
				<div class="form-group col-md-3">
					<label for="specialisation" class="form-label">Specialisation</label>
					<select id="specialisation" name="specialisation"
						class="form-select">
						<option selected>Choose Specialisation...</option>
						<option value="General Physician">General Physician</option>
						<option value="ENT">ENT</option>
						<option value="Cardiologist">Cardiologist</option>
						<option value="Neurologist">Neurologist</option>
					</select>
				</div>
				<div class="form-group col-md-4">
					<br>
					<button type="submit" class="btn btn-primary search-btn mt-2">
						<i class='bx bxs-search'></i> Search
					</button>
				</div>
			</div>
		</form>
		<hr>
		<h4>ALL DOCTORS</h4>
		<hr>

		<!-- Appointments Table -->
		<div class="table-responsive p-2 m-2">
			<table class="table table-striped text-center">
				<thead>
					<tr>
						
						<th scope="col">Doctor name</th>
						<th scope="col">Specialization</th>
						<th scope="col">Fees</th>

						<th scope="col">Book</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<c:forEach items="${allDoctors}" var="doctor">
							<tr>
								
								<td>Dr. ${doctor.name }</td>
								<td>${doctor.specialisation}</td>
								<td>${doctor.fees }</td>

								<td>
									<form action="bookappointment" method="">
										<div class="form-group d-none">
											<input type="hidden" value="${doctor.doc_id }" name="doc_id">
										</div>
										<button type="submit" class="btn-sm btn-success">Book</button>
									</form>
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