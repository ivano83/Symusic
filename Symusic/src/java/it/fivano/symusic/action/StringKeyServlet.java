package it.fivano.symusic.action;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.fivano.symusic.backend.service.PopularKeyService;
import it.fivano.symusic.backend.service.UserService;
import it.fivano.symusic.model.UserModel;

public class StringKeyServlet extends BaseAction {

	private static final long serialVersionUID = -4206933270800350792L;

	private String key;

	public StringKeyServlet() {
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


			key = request.getParameter("key");

			PopularKeyService serv = new PopularKeyService();

			serv.savePopularKey(user.getId(), key);


		} catch (Exception e) {
			log.error("Errore inserimento preferenza: "+key,e);
		}


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}



}
