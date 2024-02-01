package servlets;

import exceptions.IllegalInputParameters;
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
import java.util.regex.Pattern;

import static utils.MyUtils.isSignedIn;
import static servlets.ConstantsInterface.*;

@WebServlet(pathToSignUpPage)
public class SignUpServlet extends HttpServlet {
	private String username;
	private String email;
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
				// Forward to /sign-up
				getServletContext().getRequestDispatcher("/jsp/sign-up.jsp").forward(req, resp);
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
			// Checks the data that client sent to the server
			try {
				Reader reader = req.getReader();

				if (writeUserDataToStore(reader)) {
					session.setAttribute(bannerAttribute, "Success! You have successfully signed-up.");
					resp.sendRedirect(httpScheme + domainName);
				} else {
					resp.sendError(409, "User with the same email or username that you have entered already exist");
				}
			}
			catch (IllegalInputParameters e) {
				try {
					resp.sendError(400, e.getMessage());
				}
				catch (IOException ex) {
					// TODO: Log out it
					throw new RuntimeException(ex);
				}
			}
			catch (IOException e) {
				// TODO: Log out it
				throw new RuntimeException(e);
			}
		}
	}

	private static boolean containsWhiteSpaceRegex(String input) {
		// Use a regular expression to check for whitespace
		return Pattern.compile("\\s").matcher(input).find();
	}

	private static int getCharacterOccurrences(String str, char ch) {
		int count = 0;
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == ch) count++;
		}
		return count;
	}

	private void checkCorrectnessOfLineWithParameters(String lineWithParameters) throws IllegalInputParameters {
		// Check if a line have some whitespaces
		if (containsWhiteSpaceRegex(lineWithParameters)) {
			throw new IllegalInputParameters("Line contains some whitespaces");
		}

		// Check if a line have not English letters
		for (int i = 0; i < lineWithParameters.length(); i++) {
			int cp = lineWithParameters.codePointAt(i);
			if (Character.isSupplementaryCodePoint(cp)) {
				throw new IllegalInputParameters("Line have not only English letters and digits(Line must have only English letters and digits)");
			}
		}
		if (!lineWithParameters.matches("^[a-zA-Z0-9@._&%=-]+$")) {
			throw new IllegalInputParameters("Line have not only English letters and digits(Line must have only English letters and digits)");
		}

		// Check if the number of '&' sign is not 2
		if (getCharacterOccurrences(lineWithParameters, '&') != 2) {
			throw new IllegalInputParameters("The number of '&' sign isn't 2. You should send only username, email and password");
		}
		// Check if the number of '=' sign is not 3
		if (getCharacterOccurrences(lineWithParameters, '=') < 3) {
			throw new IllegalInputParameters("The number of '=' sign less than 3. It seems some data(username, email or password) wasn't received");
		}
		if (getCharacterOccurrences(lineWithParameters, '=') > 3) {
			throw new IllegalInputParameters("The number of '=' sign more than 3. It seems data isn't correct. Data can't contain '=' sign");
		}
	}

	private void checkCorrectnessOfUserData() throws IllegalInputParameters {
		// Check that the length of username/email/password is not too large
		if (this.username.length() >= 500 || this.email.length() >= 500 || this.password.length() >= 500) {
			throw new IllegalInputParameters("Length of username/email/password is too large");
		}

		// Check that the username, email and password matches to the conditions
		String message = "";
		if (!(this.username.matches("^[a-zA-Z0-9]+$"))) {
			message += "username";
		} else if (!this.email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
			if (!message.isBlank()) message += ", email";
			else message += "email";
		} else if (!this.password.matches("^(?=(.*[A-Za-z]){5,})(?=(.*\\d){3,})[A-Za-z\\d]{8,}$")) {
			if (!message.isBlank()) message += ", password";
			else message += "password";
		}
		if (!message.isBlank()) {
			throw new IllegalInputParameters("The next data is not correct: " + message);
		}
	}

	private void getUserData(Reader requestReader) throws IllegalInputParameters {
		try (BufferedReader requestBufferedReader = new BufferedReader(requestReader)) {
			String lineWithParameters = requestBufferedReader.readLine();

			// Check the correctness of line with parameters(data) like checking of availability whitespaces, letters
			// and number of '&' sign
			checkCorrectnessOfLineWithParameters(lineWithParameters);

			// Parse a line with parameters
			try {
				this.username = URLDecoder.decode(lineWithParameters.split("&")[0].split("=")[1], StandardCharsets.UTF_8);
				this.email = URLDecoder.decode(lineWithParameters.split("&")[1].split("=")[1], StandardCharsets.UTF_8);
				this.password = URLDecoder.decode(lineWithParameters.split("&")[2].split("=")[1], StandardCharsets.UTF_8);
			}
			catch (ArrayIndexOutOfBoundsException e) {
				// TODO: Log out it
				throw new IllegalInputParameters("Error: a key or value was absent");
			}

			// Check the correctness of data(username, email and password)
			checkCorrectnessOfUserData();
		}
		catch (IOException e) {
			// TODO: Log out it
			throw new RuntimeException(e);
		}
	}

	private boolean writeUserDataToStore(Reader requestReader) throws IllegalInputParameters {
		File clientsData = new File(pathToClientsData);

		try (var fw = new FileWriter(clientsData, true);
		     var clientsDataReader = new BufferedReader(new FileReader(clientsData))) {

			getUserData(requestReader);

			String line;
			while ((line = clientsDataReader.readLine()) != null) {
				String tmpUsername = line.split(" ")[0];
				String tmpEmail = line.split(" ")[1];

				if (tmpUsername.equals(username) || tmpEmail.equals(email)) return false;
			}

			fw.write(username + " " + email + " " + password + System.lineSeparator());
			return true;
		}
		catch (IOException e) {
			// TODO: Log out it
			throw new RuntimeException(e);
		}
	}
}
