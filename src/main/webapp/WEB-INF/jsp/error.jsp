<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<!doctype html>
<html lang="it" class="h-100" >
	<head>
		<meta charset="ISO-8859-1">
		<!-- Common imports in pages -->
	 	<jsp:include page="./header.jsp" />
		<title>Error page</title>
	</head>
	<body class="d-flex flex-column h-100">
		<!-- Fixed navbar -->
		<sec:authorize access="isAuthenticated()">
   		<jsp:include page="./navbar.jsp"></jsp:include>
   		  </sec:authorize>
   		
		<main class="flex-shrink-0">
		
			<div class="container">
					
					<div class="card border-danger" >
					  <div class="card-header">Oooops......</div>
					  <div class="card-body text-danger">
					    <h5 class="card-title">Piccolo Problemino</h5>
					    <p class="card-text">Abbiamo avuto un piccolo problemino... Ci scusiamo per l'inconveniente.</p>
					    
					    <!-- dettaglio -->
				    	<p>
						  <a class="btn btn-primary btn-sm" data-bs-toggle="collapse" href="#collapseExample" role="button" aria-expanded="false" aria-controls="collapseExample">
						    Dettaglio
						  </a>
						</p>
						<div class="collapse" id="collapseExample">
							<div class="card-body text-danger">
								 <p class="card-text">Nulla di grave ma ti chiediamo di contattare l'assistenza.</p>
							</div>
						</div>
					
						<!-- card-body -->	
					  </div>
					  
					<!-- card- -->	
					</div>
					
			<!-- container- -->			
			</div>
			
		</main>
			
	    
	    <!-- Footer -->
		<jsp:include page="./footer.jsp" />
	</body>
</html>