<!doctype html>
<html lang="it" class="h-100" >
	 <head>
	 
	 	<!-- Common imports in pages -->
	 	<jsp:include page="../header.jsp" />
	   
	   <title>Ricerca</title>
	 </head>
	   <body class="d-flex flex-column h-100">
	   
	   		<!-- Fixed navbar -->
	   		<jsp:include page="../navbar.jsp"></jsp:include>
	    
			
			<!-- Begin page content -->
			<main class="flex-shrink-0">
			  <div class="container">
			  
			  		<div class="alert alert-danger alert-dismissible fade show ${errorMessage==null?'d-none':'' }" role="alert">
					  ${errorMessage}
					  <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close" ></button>
					</div>
			  
			  <div class='card'>
				    <div class='card-header'>
				        <h5>Ricerca elementi</h5> 
				    </div>
				    <div class='card-body'>
		
		
							<form method="post" action="${pageContext.request.contextPath}/tavolo/list" class="row g-3" >
							
							
								<div class="col-md-6">
									<label for="denominazione" class="form-label">denominazione <span class="text-danger">*</span></label>
									<input type="text" name="denominazione" id="denominazione" class="form-control" placeholder="Inserire la denominazione"  >
								</div>
								
								<div class="col-md-3">
									<label for="dataCreazione" class="form-label">Data Creazione <span class="text-danger">*</span></label>
                        			<input class="form-control" id="dataCreazione" type="date" placeholder="dd/MM/yy"
                            			title="formato : gg/mm/aaaa"  name="dataCreazione"   >
								</div>
								
								
								<div class="col-md-6">
									<label for="esperienzaMin" class="form-label">Esperienza Minima<span class="text-danger">*</span></label>
									<input type="number" name="esperienzaMin" id="esperienzaMin" class="form-control" placeholder="Inserire l'esperienza Minima"  >
								</div>
							
								<div class="col-md-6">
									<label for="cifraMin" class="form-label">Cifra Minima <span class="text-danger">*</span></label>
									<input type="number" class="form-control" name="cifraMin" id="cifraMin" placeholder="Inserire la Cifra Minima"  >
								</div>
								
					
								<div class="col-12">
									<button type="submit" name="submit" value="submit" id="submit" class="btn btn-primary">Conferma</button>
									<a class="btn btn-outline-primary ml-2" href="${pageContext.request.contextPath}/tavolo/insert">Add New</a>
									<input class="btn btn-outline-warning" type="reset" value="Ripulisci">
								</div>
		
						</form>
  
				    
				    
					<!-- end card-body -->			   
				    </div>
				<!-- end card -->
				</div>		
					  
			    
			  <!-- end container -->  
			  </div>
			  
			</main>
			
			<!-- Footer -->
			<jsp:include page="../footer.jsp" />
	  </body>
</html>