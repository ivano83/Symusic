package it.fivano.symusic.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import it.fivano.symusic.SymusicUtility;
import it.fivano.symusic.backend.service.UserService;
import it.fivano.symusic.core.Release0DayMp3Service;
import it.fivano.symusic.core.Release0DayMusicService;
import it.fivano.symusic.core.ReleaseFromPreDbService;
import it.fivano.symusic.core.ReleaseFromPresceneService;
import it.fivano.symusic.core.ReleaseListService;
import it.fivano.symusic.core.ReleaseMusicDLService;
import it.fivano.symusic.core.util.C.SearchType;
import it.fivano.symusic.core.util.ReleaseModelComparator;
import it.fivano.symusic.core.util.SearchInput;
import it.fivano.symusic.model.ReleaseModel;
import it.fivano.symusic.model.UserModel;

/**
 * Servlet implementation class MassiveReleaseServlet
 */

public class MassiveReleaseServlet extends BaseAction {
	private static final long serialVersionUID = 1L;

	private String urlPrecedente;
	private String urlSuccessivo;

	private String reload;

    /**
     * Default constructor.
     */
    public MassiveReleaseServlet() {
    	this.setLogger(getClass());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {


			UserModel user = null;
			if(request.getSession().getAttribute("user")!=null)
				user = (UserModel) request.getSession().getAttribute("user");
			else
				user = new UserService().getUser("ivano");


			String choose = request.getParameter("crewOrGenre");
			String genre = request.getParameter("genre");
			String crew = request.getParameter("crew");
			String searchName = request.getParameter("searchValue");
			String searchNameMaxItem = request.getParameter("searchMaxItem");
			Date initDate = null;
			Date endDate = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String iDate = request.getParameter("initDate");
			String eDate = request.getParameter("endDate");

			Integer annoDa = Integer.parseInt(request.getParameter("annoDa"));
			Integer annoAl = Integer.parseInt(request.getParameter("annoAl"));

			String enableBeatport = request.getParameter("enableBeatport");
			String excludeReleaseRip = request.getParameter("excludeRelaseRip");
			String excludeReleaseVA = request.getParameter("excludeVA");
			String onlyPopularLevel = request.getParameter("onlyPopularLevel");
			boolean flagBeatport = (enableBeatport!=null && enableBeatport.equalsIgnoreCase("true"))?true:false;
			boolean flagRip = (excludeReleaseRip!=null && excludeReleaseRip.equalsIgnoreCase("true"))?true:false;
			boolean flagVA = (excludeReleaseVA!=null && excludeReleaseVA.equalsIgnoreCase("true"))?true:false;
			boolean flagOnlyPopularLevel = (onlyPopularLevel!=null && onlyPopularLevel.equalsIgnoreCase("true"))?true:false;

			// se non presente le date sono inizializzate alla data corrente
			initDate = (iDate==null || iDate.isEmpty())? sdf.parse(sdf.format(new Date())) : sdf.parse(iDate);
			endDate = (eDate==null || eDate.isEmpty())? sdf.parse(sdf.format(new Date())) : sdf.parse(eDate);

			urlPrecedente = request.getRequestURI()+"?crewOrGenre="+choose+"&genre="+genre+"&crew="+crew+"&initDate="+sdf.format(SymusicUtility.sottraiData(initDate, 2))+"&endDate="+sdf.format(SymusicUtility.sottraiData(initDate, 1));
			urlPrecedente += "&enableBeatport="+flagBeatport+"&excludeRelaseRip="+flagRip;
			urlSuccessivo = request.getRequestURI()+"?crewOrGenre="+choose+"&genre="+genre+"&crew="+crew+"&initDate="+sdf.format(SymusicUtility.aggiungiData(endDate, 1))+"&endDate="+sdf.format(SymusicUtility.aggiungiData(endDate, 2));
			urlSuccessivo += "&enableBeatport="+flagBeatport+"&excludeRelaseRip="+flagRip;

			request.setAttribute("urlPrecedente", urlPrecedente);
			request.setAttribute("urlSuccessivo", urlSuccessivo);

			List<ReleaseModel> listRelease = new ArrayList<ReleaseModel>();

			reload = request.getParameter("reload");
			if(reload!=null && reload.length()>0) {
				listRelease = (List<ReleaseModel>) request.getSession().getAttribute("listRelease");
			} else {

				SearchInput in = new SearchInput();
				if("genre".equals(choose))
					in.setSearchType(SearchType.SEARCH_BY_GENRE);
				else if("name".equals(choose))
					in.setSearchType(SearchType.SEARCH_BY_NAME);
				else
					in.setSearchType(SearchType.SEARCH_BY_CREW);
				in.setCrew(crew);
				in.setGenre(genre);
				in.setName(searchName);
				in.setDataDa(initDate);
				in.setDataA(endDate);
				in.setExcludeRadioRip(flagRip);
				in.setExcludeVa(flagVA);
				in.setOnlyPopularLevel(flagOnlyPopularLevel);
				in.setAnnoMin(annoDa);
				in.setAnnoMax(annoAl);

				if(!StringUtils.isEmpty(searchNameMaxItem)) {
					in.setMaxItem(Integer.parseInt(searchNameMaxItem));
				}

				ReleaseListService relService = new ReleaseListService(user.getId());
				listRelease = relService.loadReleaseList(in);

				Collections.sort(listRelease, new ReleaseModelComparator());
			}

			request.getSession().setAttribute("listRelease", listRelease);


			RequestDispatcher rd = getServletContext().getRequestDispatcher("/jsp/release_result.jsp");
			rd.forward(request, response);


		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	public String getUrlPrecedente() {
		return urlPrecedente;
	}

	public void setUrlPrecedente(String urlPrecedente) {
		this.urlPrecedente = urlPrecedente;
	}

	public String getUrlSuccessivo() {
		return urlSuccessivo;
	}

	public void setUrlSuccessivo(String urlSuccessivo) {
		this.urlSuccessivo = urlSuccessivo;
	}

	public String getReload() {
		return reload;
	}

	public void setReload(String reload) {
		this.reload = reload;
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		System.out.println(new String("Ferry Tayle ft. Sarah Shields – The Most Important Thing (Club".getBytes(),"ISO-8859-1"));
	}

}
