package it.fivano.symusic.action;

import it.fivano.symusic.backend.service.ReleaseService;
import it.fivano.symusic.backend.service.UserService;
import it.fivano.symusic.core.GoogleService;
import it.fivano.symusic.core.parser.YoutubeParser;
import it.fivano.symusic.core.util.ReleaseModelComparator;
import it.fivano.symusic.model.ReleaseModel;
import it.fivano.symusic.model.UserModel;

import java.io.IOException;
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

public class LocalReleaseServlet extends BaseAction {

	private static final long serialVersionUID = 3045179509033676076L;

	public LocalReleaseServlet() {
    	this.setLogger(getClass());
    }



	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {


			UserModel user = null;
			if(request.getSession().getAttribute("user")!=null)
				user = (UserModel) request.getSession().getAttribute("user");
			else
				user = new UserService().getUser("ivano");

			Date initDate = null;
			Date endDate = null;

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String iDate = request.getParameter("initDate");
			String eDate = request.getParameter("endDate");

			// se non presente le date sono inizializzate alla data corrente
			initDate = (iDate==null || iDate.isEmpty())? sdf.parse(sdf.format(new Date())) : sdf.parse(iDate);
			endDate = (eDate==null || eDate.isEmpty())? sdf.parse(sdf.format(new Date())) : sdf.parse(eDate);

			String genre = request.getParameter("genre");
			if(genre.equals("ALL")) {
				genre = null;
			}

			String crew = request.getParameter("crew");
			if(crew.equals("ALL")) {
				crew = null;
			}

			List<ReleaseModel> listRelease = new ArrayList<ReleaseModel>();

			ReleaseService relServ = new ReleaseService();
			listRelease = relServ.getListRelease(genre, crew, initDate, endDate, user.getId());

			Collections.sort(listRelease, new ReleaseModelComparator());

			YoutubeParser youtube = new YoutubeParser();
			GoogleService google = new GoogleService();
			for(ReleaseModel r : listRelease) {
				youtube.addManualSearchLink(r);
				google.addManualSearchLink(r);
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

}
