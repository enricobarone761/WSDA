package it.unipa.apsw.aa2122.servlet;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SearchEngines
 */
@WebServlet({ "/SearchEngines", "/search-engines" })
public class SearchEngines extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SearchEngines() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String searchString = request.getParameter("searchString");
		if ((searchString == null) || (searchString.length() == 0)) {
			reportProblem(response, "Missing search string");
			return;
		}
		// The URLEncoder changes spaces to "+" signs and other
		// non-alphanumeric characters to "%XY", where XY is the
		// hex value of the ASCII (or ISO Latin-1) character.
		// Browsers always URL-encode form values, and the
		// getParameter method decodes automatically. But since
		// we're just passing this on to another server, we need to
		// re-encode it to avoid characters that are illegal in
		// URLs.
		searchString = URLEncoder.encode(searchString, "utf-8");
		String searchEngineName = request.getParameter("searchEngine");
		if ((searchEngineName == null) || (searchEngineName.length() == 0)) {
			reportProblem(response, "Missing search engine name");
			return;
		}
		String searchURL = SearchUtilities.makeURL(searchEngineName, searchString);
		if (searchURL != null) {
			response.sendRedirect(searchURL);
		} else {
			reportProblem(response, "Unrecognized search engine");
		}
	}

	private void reportProblem(HttpServletResponse response, String message) throws IOException {
		response.sendError(HttpServletResponse.SC_NOT_FOUND, message);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
