package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.file.Path;

import static servlets.ConstantsInterface.*;
import static servlets.ConstantsInterface.pathToHomePage;
import static utils.MyUtils.*;

@WebServlet("/")
public class IndexServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		String requestURI = req.getRequestURI();

		// If URL is http://localhost:8080/
		if (isSignedIn(session) && requestURI.compareTo("/") == 0) {
			try {
				session.setAttribute(bannerAttribute, messageThatClientAlreadySignedIn);
				resp.sendRedirect(httpScheme + Path.of(domainName, pathToHomePage));
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		}
		// If URL contains /img(e.g. http://localhost:8080/img/image.png)
		else if (requestURI.contains("/img/")) {
			if (isSignedIn(session)) {
				String imageFileName = getFileNameFromURI(requestURI);

				try {
					sendImage(resp, imageFileName);
				}
				catch (IOException e) {
					// TODO: Log out it
					throw new RuntimeException(e);
				}
			} else {
				try {
					resp.sendError(404);
				}
				catch (IOException e) {
					// TODO: Log out it
					throw new RuntimeException(e);
				}
			}
		} else if (isSignedIn(session) && requestURI.compareTo("/") != 0) {
			try {
				session.setAttribute(bannerAttribute, messageThatClientAlreadySignedIn);
				resp.sendRedirect(httpScheme + Path.of(domainName, pathToHomePage));
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		} else if (!isSignedIn(session) && requestURI.compareTo("/") == 0) {
			try {
				getServletContext().getRequestDispatcher("/jsp/index.jsp").forward(req, resp);
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
			catch (ServletException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		} else {
			try {
				resp.sendError(404);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
