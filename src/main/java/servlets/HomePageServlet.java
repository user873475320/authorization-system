package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static utils.MyUtils.isSignedIn;
import static servlets.ConstantsInterface.*;

@WebServlet(pathToHomePage)
public class HomePageServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (isSignedIn(session)) {
			try {
				getServletContext().getRequestDispatcher("/jsp/homePage.jsp").forward(req, resp);
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
			catch (ServletException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		}
		else {
			try {
				session.setAttribute(bannerAttribute, "You are not signed in. Please signed in or signed up.");

				resp.sendRedirect(httpScheme + domainName);
			}
			catch (IOException e) {
				// TODO: Log out
				throw new RuntimeException(e);
			}
		}
	}
}
