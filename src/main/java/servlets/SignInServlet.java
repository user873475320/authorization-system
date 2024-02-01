package servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.*;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import static utils.MyUtils.isSignedIn;
import static servlets.ConstantsInterface.*;

@WebServlet(ConstantsInterface.pathToSignInPage)
public class SignInServlet extends HttpServlet {
	private String usernameOrEmail;
	private String password;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (isSignedIn(session)) {
			try {
				session.setAttribute(bannerAttribute, messageThatClientAlreadySignedIn);
				resp.sendRedirect(httpScheme + Path.of(domainName, pathToHomePage));
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		} else {
			try {
				// Forward to /sign-in
				getServletContext().getRequestDispatcher("/jsp/sign-in.jsp").forward(req, resp);
			}
			catch (ServletException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();

		if (isSignedIn(session)) {
			try {
				session.setAttribute(bannerAttribute, messageThatClientAlreadySignedIn);
				resp.sendRedirect(httpScheme + Path.of(domainName, pathToHomePage));
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		} else {
			try {
				Reader requestReader = req.getReader();
				getUsersData(requestReader);

				File usersDataFile = new File(pathToClientsData);
				if (isUserDataCorrect(usersDataFile)) {
					session.setAttribute(signInAttribute, true);
					resp.sendRedirect(httpScheme + Path.of(domainName, pathToHomePage));
				} else {
					session.setAttribute(signInAttribute, false);
					resp.sendError(409, "You haven't logged in successfully, because your password or login isn't correct");
				}
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		}
	}

	public boolean isUserDataCorrect(File usersDataFile) {
		try (var usersDataFileBufferedReader = new BufferedReader(new FileReader(usersDataFile))) {
			if (!usersDataFile.isDirectory() && usersDataFile.isFile()) {
				String line;

				while ((line = usersDataFileBufferedReader.readLine()) != null) {
					String tmpUsername = line.split(" ")[0];
					String tmpEmail = line.split(" ")[1];
					String tmpPassword = line.split(" ")[2];

					if (tmpPassword.equals(password) && (tmpUsername.equals(usernameOrEmail) || tmpEmail.equals(usernameOrEmail))) {
						return true;
					}
				}
				return false;
			} else {
				// TODO: Log out it
				throw new IllegalArgumentException("Users Data File isn't a file");
			}
		}
		catch (IOException e) {
			// TODO: Log out it
			throw new RuntimeException(e);
		}
	}

	private void getUsersData(Reader requestReader) {
		try {
			BufferedReader requestBufferedReader = new BufferedReader(requestReader);

			String lineWithParameters = requestBufferedReader.readLine();

			usernameOrEmail = URLDecoder.decode(lineWithParameters.split("&")[0].split("=")[1], StandardCharsets.UTF_8);
			password = URLDecoder.decode(lineWithParameters.split("&")[1].split("=")[1], StandardCharsets.UTF_8);
		}
		catch (IOException e) {
			// TODO: Log out it
			throw new RuntimeException(e);
		}
	}
}
