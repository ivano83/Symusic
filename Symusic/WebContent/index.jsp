<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<jsp:include page="jsp/head_common.jsp"/>

	<title>SYMUSIC - Facilita la ricerca delle release musicali</title>

	<script type="text/javascript">
		function checkDataRange() {
			var da = document.getElementById("annoDa");
			var a = document.getElementById("annoAl");
			var daValue = da.options[da.selectedIndex].value;
			var aValue = a.options[a.selectedIndex].value;
			if(aValue<daValue) {
				da.selectedIndex = a.selectedIndex;
			}

		}
		function lastReleaseDate(genreIn) {
			 $.get('LastReleaseDateServlet',{genre:genreIn},function(responseText) {
				 $('.inDate').val(responseText);
	     });
		}

		function chooseCrewOrGenre(scelta) {
			if(scelta==1) {
				document.getElementById('genreSelect').style.display='block';
				document.getElementById('crewSelect').style.display='none';
			} else if(scelta==2) {
				document.getElementById('crewSelect').style.display='block';
				document.getElementById('genreSelect').style.display='none';
			} else {
				document.getElementById('crewSelect').style.display='none';
				document.getElementById('genreSelect').style.display='none';
			}

		}
	</script>
</head>
<body onload="javascript:chooseCrewOrGenre(1);">
<h2>S Y M U S I C</h2>

<form action="MassiveReleaseServlet" id="form0">

	<table>
		<tr>
			<td>

				<span><input type="radio" name="crewOrGenre" value="genre" checked="checked" onclick="javascript:chooseCrewOrGenre(1);" >Genere </span>
				<span><input type="radio" name="crewOrGenre" value="crew" onclick="javascript:chooseCrewOrGenre(2);" >Crew </span>
			</td>
			<td>
				<select id="genreSelect" name="genre" onchange="javascript:lastReleaseDate(document.forms['form1'].elements['genre'].value)">
					<option value="dance" selected="selected">Dance</option>
					<option value="trance">Trance</option>
					<option value="house">House</option>
					<option value="techno">Techno</option>
					<option value="electronic">Electronic</option>
				</select>
				<select id="crewSelect" name="crew">
					<c:forEach items="${crewList}" var="cr">
        				<option value="${cr}">${cr}</option>
    			  	</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Data inizio </div>
			</td>
			<td>
				<input type="text" name="initDate" class="inDate tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Data Fine </div>
			</td>
			<td>
				<input type="text" name="endDate" class="outDate tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Estrai dati da Beatport</div>

			</td>
			<td>
				<input type="checkbox" name="enableBeatport" value="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Radio/Sat RIP</div>
			</td>
			<td>
				<input type="checkbox" name="excludeRelaseRip" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Various Artist (VA)</div>
			</td>
			<td>
				<input type="checkbox" name="excludeVA" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Range Data</div>
			</td>
			<td>
				<jsp:useBean id="date" class="java.util.Date" />
				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
				Dal <select id="annoDa" name="annoDa" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
				Al <select id="annoAl" name="annoAl" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ricarica pagina precedente</div>
			</td>
			<td>
				<input type="checkbox" name="reload" value="true" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Solo release con rank</div>
			</td>
			<td>
				<input type="checkbox" name="onlyPopularLevel" value="true" />
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">

</form>

<div style="padding-bottom: 60px"></div>


<form action="ZeroDayMusicServlet" id="form1">

	<table>
		<tr>
			<td>
				<div>Fonte Release</div>
			</td>
			<td>
				<select name="site">
					<option value="1">0DayMusic</option>
					<option value="2">0DayMp3</option>
					<option value="3">Music DL</option>
					<option value="4" selected="selected">Prescene.tk</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Genere Release</div>
			</td>
			<td>
				<select name="genre" onchange="javascript:lastReleaseDate(document.forms['form1'].elements['genre'].value)">
					<option value="dance" selected="selected">Dance</option>
					<option value="trance">Trance</option>
					<option value="house">House</option>
					<option value="techno">Techno</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Data inizio </div>
			</td>
			<td>
				<input type="text" name="initDate" class="inDate tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Data Fine </div>
			</td>
			<td>
				<input type="text" name="endDate" class="outDate tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Estrai dati da Beatport</div>

			</td>
			<td>
				<input type="checkbox" name="enableBeatport" value="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Radio/Sat RIP</div>
			</td>
			<td>
				<input type="checkbox" name="excludeRelaseRip" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Various Artist (VA)</div>
			</td>
			<td>
				<input type="checkbox" name="excludeVA" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Range Data</div>
			</td>
			<td>
				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
				Dal <select id="annoDa" name="annoDa" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
				Al <select id="annoAl" name="annoAl" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ricarica pagina precedente</div>
			</td>
			<td>
				<input type="checkbox" name="reload" value="true" />
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">

</form>

<div style="padding-bottom: 60px"></div>

<form action="ZeroDayMusicServlet">

	<table>
		<tr>
			<td>
				<div>Fonte Release</div>
			</td>
			<td>
				<select name="site">
					<option value="4a">Prescene.tk</option>
					<option value="5" selected="selected" >PreDB.me</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Crew List</div>
			</td>
			<td>
				<select name="crew">
					<c:forEach items="${crewList}" var="cr">
        				<option value="${cr}">${cr}</option>
    			  	</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Data inizio </div>
			</td>
			<td>
				<input type="text" name="initDate" class="tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Data Fine </div>
			</td>
			<td>
				<input type="text" name="endDate" class="tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Estrai dati da Beatport</div>

			</td>
			<td>
				<input type="checkbox" name="enableBeatport" value="true"/>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Radio/Sat RIP</div>
			</td>
			<td>
				<input type="checkbox" name="excludeRelaseRip" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Various Artist (VA)</div>
			</td>
			<td>
				<input type="checkbox" name="excludeVA" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Range Data</div>
			</td>
			<td>
				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
				Dal <select id="annoDa" name="annoDa" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
				Al <select id="annoAl" name="annoAl" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ricarica pagina precedente</div>
			</td>
			<td>
				<input type="checkbox" name="reload" value="true" />
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">

</form>

<div style="padding-bottom: 60px"></div>

<form action="EuroadrenalineServlet">
	<h3>Ricerca Euroadrenaline</h3>
	<table>
		<tr>
			<td>
				<div># pagine da caricare </div>
			</td>
			<td>
				<input type="text" name="numPagine" size="10">
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Radio/Sat RIP</div>
			</td>
			<td>
				<input type="checkbox" name="excludeRelaseRip" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Generi ricercati</div>
			</td>
			<td>
				<select name="genre" multiple="multiple" size="3" >
					<option value="House" selected="selected">House</option>
					<option value="Trance">Trance</option>
					<option value="Hardstyle">Hardstyle</option>
				</select>
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">

</form>

<div style="padding-bottom: 60px"></div>

<form action="ScenelogServlet">
	<h3>Ricerca Scenelog</h3>
	<table>

		<tr>
			<td>
				<div>Data inizio </div>
			</td>
			<td>
				<input type="text" name="initDate" class="tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Data Fine </div>
			</td>
			<td>
				<input type="text" name="endDate" class="tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Radio/Sat RIP</div>
			</td>
			<td>
				<input type="checkbox" name="excludeRelaseRip" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Generi ricercati</div>
			</td>
			<td>
				<select name="genre" multiple="multiple" size="5" >
					<option value="Hard Dance" selected="selected">Hard Dance</option>
					<option value="Trance">Trance</option>
					<option value="Progressive House">Progressive House</option>
					<option value="House">House</option>
					<option value="Electro House">Electro House</option>
				</select>
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">

</form>

<div style="padding-bottom: 60px"></div>

<form action="BeatportServlet">
	<h3>Ricerca Beatport</h3>
	<table>
		<tr>
			<td>
				<div>Genere Release</div>
			</td>
			<td>
				<select name="genre">
				  <c:forEach items="${genreList}" var="gen">
        			<option value="${gen}" ${gen == selectedGenre ? 'selected' : ''}>${gen}</option>
    			  </c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Ricarica pagina precedente</div>
			</td>
			<td>
				<input type="checkbox" name="reload" value="true" />
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">&nbsp;&nbsp;

</form>

<div style="padding-bottom: 60px"></div>

<form action="MassiveReleaseServlet">
	<h3>Ricerca libera</h3>
	<input type="hidden" name="crewOrGenre"  value="name" >
	<table>
		<tr>
			<td>
				<div>Cerca </div>
			</td>
			<td>
				<span><input type="text" name="searchValue" value="" size="40" /> </span>
			</td>
			<td>
				<span>Item:
				<select id="searchMaxItem" name="searchMaxItem" >
					<option value="1" >1</option>
					<option value="10" selected="selected">10</option>
					<option value="50" >50</option>
					<option value="100" >100</option>
					<option value="200" >200</option>
					<option value="300" >300</option>
				</select> </span>
			</td>

		</tr>
		<tr>
			<td>
				<div>Ignora Release Radio/Sat RIP</div>
			</td>
			<td colspan="2">
				<input type="checkbox" name="excludeRelaseRip" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Ignora Release Various Artist (VA)</div>
			</td>
			<td colspan="2">
				<input type="checkbox" name="excludeVA" value="true" checked="checked" />
			</td>
		</tr>
		<tr>
			<td>
				<div>Range Data</div>
			</td>
			<td colspan="2">
				<fmt:formatDate value="${date}" pattern="yyyy" var="currentYear" />
				Dal <select id="annoDa" name="annoDa" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno" varStatus="status">
					<c:choose>
						<c:when test="${status.index==1}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
				Al <select id="annoAl" name="annoAl" onchange="javascript:checkDataRange();">
				<c:forEach items="${listaAnni}" var="anno">
					<c:choose>
						<c:when test="${anno==currentYear}">
							<option value="${anno}" selected="selected">${anno}</option>
						</c:when>
						<c:otherwise>
							<option value="${anno}">${anno}</option>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				</select>
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">

</form>

<div style="padding-bottom: 60px"></div>

<form action="LocalReleaseServlet">
	<h3>Ricerca Release Salvate</h3>
	<table>
		<tr>
			<td>
				<div>Genere Release</div>
			</td>
			<td>
				<select name="genre">
					<option value="ALL" selected="selected">[ALL]</option>
					<option value="dance">Dance</option>
					<option value="trance">Trance</option>
					<option value="techno">Techno</option>
					<option value="house">House</option>
				</select>
				&nbsp;
				<select id="crewSelect2" name="crew">
					<option value="ALL" selected="selected">[ALL]</option>
					<c:forEach items="${crewList}" var="cr">
        				<option value="${cr}">${cr}</option>
    			  	</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				<div>Data inizio </div>
			</td>
			<td>
				<input type="text" name="initDate" class="tcal">
			</td>
		</tr>
		<tr>
			<td>
				<div>Data Fine </div>
			</td>
			<td>
				<input type="text" name="endDate" class="tcal">
			</td>
		</tr>
	</table>
	<input type="submit" value="Ricerca">&nbsp;&nbsp;

</form>



<div style="padding-bottom: 300px"></div>
</body>
</html>
