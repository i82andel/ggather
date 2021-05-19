<!DOCTYPE html>
<html lang="en">
<%@ page import = "com.mycompany.iw.Pista" %>
<%@ page import = "com.mycompany.iw.Club" %>
<%@ page import = "com.mycompany.iw.mysql.MySQLDaoManager" %>
<%@ page import = "java.util.List" %>
<head>
	<title>Partidos</title>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1">
   
<!--===============================================================================================-->	
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
<!--===============================================================================================-->
<%
   
   
	List<Pista> pistas = (List<Pista>) session.getAttribute("pistas");

	Class.forName("com.mysql.jdbc.Driver");
	MySQLDaoManager man = new MySQLDaoManager("ggather.zapto.org", "java", "1234", "aplicacion");

	
   %> 

</head>

<header id="main-header">
            
    <a id="logo-header" href="mainMenu.html">
    <span class="header-pic"><img src="images/icons/logo3.png"></span>
        <span class="site-name">GGather</span>
    </a> 

    <nav>
        <ul>
            <li><a href="mainMenu.html">Inicio</a></li>
            <li><a href="home.html">Crear Partido</a></li>
            <li><a href="home.html"  >Acceder</a></li>
        </ul>
    </nav>

</header>


<body style="background-image: url(&quot;https://cutewallpaper.org/21/street-football-wallpaper/Soccer-Field-City-Night-Light-Dark-iPad-Air-Wallpapers-Free-.jpg&quot;); background-size: cover; margin: 0; background-repeat: no-repeat; height: 200vh; overflow-x:hidden; overflow-y:hidden">
   <br>
   <br>
   <br>
    <div class="search2-page__cards">
        
       <%
       for(int i = 0; i< pistas.size(); i++){
    	%>
    	   <div>
           <article class="card">
               <a class="card__header-link" >
                
               </a>
               <header class="card__header" style="background-image: url(&quot;https://openarena.es/wp-content/uploads/2019/05/open_arena_instalaciones16.jpg&quot;); margin-top: -5px; width: 101%; margin-left: -2px;">
                <h1>Pista de <%=pistas.get(i).getDeporte() %></h1>
                <%

                
                %>
                <h2><%=man.getClubDAO().obtener(pistas.get(i).getClub()).getNombreClub() %> </h2>
                <span class="focus-icon"> </span>
                <i  style="left:150px; top:20px;" class="fa fa-map-pin" aria-hidden="true"></i>
                <h1 style="left: 170px;"><%=pistas.get(i).getId() %></h1>
                </header>
               <div class="card_body">


                       <span style="margin-left: 85px;" class="card_address"> Valoracion </span>
                       <div style= "margin-left: 85px;">
                       <span  class="focus-input100"></span>
                       
                       <%
                       int valoracion = (int) pistas.get(i).getPuntuacionMedia();
                       for(int j = 0; j<valoracion; j++){
                    	   %><i class="fa fa-star" aria-hidden="true" style="color: gold;"></i><%
                       }
                       %>
                       </span>
                       </div>
                   </div>
                   <div class="slots">
                    <a>
                        <div><button class="partido-btn" onclick="location.href='partido.html'">
							�Crear Partido!
						</button>

                        </div>
                    </a>

                   </div>
    	   
       
       
               </div>
               
           </article>
           <%}
       %> 
       </div> 





       

    </div>

    
</body>