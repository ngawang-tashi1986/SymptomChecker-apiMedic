<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Using ApiMedic Api</title>

<link rel="stylesheet" href="<%=request.getContextPath() %>/resource/selector.css">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resource/selector.css?v=1">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/resource/font-awesome.min.css" />

<script src="<%=request.getContextPath() %>/resource/jquery-2.2.3.min.js"></script>
<script src="<%=request.getContextPath() %>/resource/jquery.imagemapster.js"></script>
<script src="<%=request.getContextPath() %>/resource/json2.js"></script>
<script src="<%=request.getContextPath() %>/resource/typeahead.bundle.js"></script>
<script src="<%=request.getContextPath() %>/resource/selector.js?v=3.3"></script>

<%
	String token = (String) session.getAttribute("token");
%>
<script>
	var userToken = "<%=token%>";
	$(document).ready(function () {
	    $("#symptomSelector").symptomSelector(
	    {
	        mode: "diagnosis",
	        webservice: "https://healthservice.priaid.ch",
	        language: "en-gb",
	        specUrl: "sample_specialisation_page",
	        accessToken: userToken
	    });
	});
</script>
</head>

<body>

<jsp:include page="/TokenGeneratorServlet"></jsp:include>

	<table class="container-table">
	    <tr>
	        <td valign="middle" colspan="2" class="td-header box-white bordered-box width50"><h4 class="header" id="selectSymptomsTitle"><span class="badge pull-left badge-primary visible-lg margin5R">1</span></h4></td>
	        <td valign="middle" class="td-header bordered-box box-white width25"><h4 class="header" id="selectedSymptomsTitle"><span class="badge pull-left badge-primary visible-lg margin5R">2</span></h4></td>
	        <td valign="middle" class="td-header bordered-box box-white width25"><h4 class="header" id="possibleDiseasesTitle"><span class="badge pull-left badge-primary visible-lg margin5R">3</span></h4></td>
	    </tr>
	    <tr>
	        <td valign="top" class="selector_container bordered-box box-white width25"><div id="symptomSelector"></div></td>
	        <td valign="top" class="selector_container bordered-box box-white width25"><div id="symptomList"></div></td>
	        <td valign="top" class="selector_container bordered-box box-white width25"><div id="selectedSymptomList"></div></td>
	        <td valign="top" class="selector_container bordered-box box-white width25"><div id="diagnosisList"></div></td>
	    </tr>
	</table>

</body>
</html>