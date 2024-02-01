package utils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import servlets.Colors;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static servlets.ConstantsInterface.*;

public class MyUtils {
	public static String getFileNameFromURI(String URI) {
		String[] tmp = URI.split("/");
		return tmp[tmp.length - 1];
	}

	public static boolean isFileInDirectory(File directory, File file) {
		// Check if the file's parent directory is the specified directory
		return file.getParentFile().equals(directory);
	}

	public static String getContentTypeOfImageFile(String fileName) {
		String[] tmp = fileName.split("\\.");
		return "image/" + tmp[tmp.length - 1];
	}

	public static void sendImage(HttpServletResponse resp, String imageFileName) throws IOException {
		Path pathToTheImageFile = Path.of(pathToImages, imageFileName);

		if (Files.exists(pathToTheImageFile)
				&& !Files.isDirectory(pathToTheImageFile)
				&& isFileInDirectory(new File(pathToImages), new File(pathToTheImageFile.toString()))) {
			try (ServletOutputStream sos = resp.getOutputStream()) {
				String contentType = getContentTypeOfImageFile(imageFileName);

				byte[] fileBytes = Files.readAllBytes(pathToTheImageFile);

				resp.setContentLength(fileBytes.length);
				resp.setContentType(contentType);

				sos.write(fileBytes);
				sos.flush();
			}
		}
	}

	public static void sendHtmlPage(String pathToHtmlPages, String nameOfPage, int statusCode, HttpServletResponse resp)
			throws IOException {
		try (var outputStream = resp.getOutputStream()) {
			byte[] bytes = Files.readAllBytes(Path.of(pathToHtmlPages, nameOfPage));
			resp.setContentType("text/html");
			resp.setContentLength(bytes.length);
			resp.setStatus(statusCode);

			outputStream.write(bytes);
		}
	}

	public static boolean isSignedIn(HttpSession session) {
		Object tmp = session.getAttribute(signInAttribute);
		if (tmp instanceof Boolean) {
			return (Boolean) tmp;
		}
		return false;
	}

	public static String insertBannerToJSP(HttpServletRequest request) {
		HttpSession session = request.getSession();

		Object tmp = session.getAttribute(bannerAttribute);
		if (tmp instanceof String) {
			String attribute = (String) tmp;

			if (!attribute.isBlank()) {
				String colorOfBannerBackground = Colors.BLUE.getColorCode();
				String styles = "display: none; position: fixed; top: 0; left: 0; width: 100%; background-color: " + colorOfBannerBackground + "; color: white; text-align: center; padding: 10px; opacity: 0; transition: opacity 1s;";

				session.setAttribute(bannerAttribute, "");

				return "<div class=\"banner\" style=\"" + styles + "\" id=\"banner\">" + attribute.strip() + "</div>\n" +
						"<script>\n" +
						"  const banner = document.getElementById('banner');\n" +
						"\n" +
						"  function showBanner() {\n" +
						"    banner.style.display = 'block';\n" +
						"    banner.style.opacity = '1';\n" +
						"    setTimeout(function() {\n" +
						"      banner.style.opacity = '0';\n" +
						"      setTimeout(function() {\n" +
						"        banner.style.display = 'none';\n" +
						"      }, 1000); // Wait for the fade-out transition to complete (1 second)\n" +
						"    }, 5000); // 5 seconds\n" +
						"  }\n" +
						"\n" +
						"  // Call the function to show the banner (you can trigger this function when needed)\n" +
						"  showBanner();\n" +
						"</script>";
			}
		}
		return "";
	}

	public static String insertBannerToJSP(HttpServletRequest request, Colors colorOfBannerBackground) {
		HttpSession session = request.getSession();

		Object tmp = session.getAttribute(bannerAttribute);
		if (tmp instanceof String) {
			String attribute = (String) tmp;

			if (!attribute.isBlank()) {
				String styles = "display: none; position: fixed; top: 0; left: 0; width: 100%; background-color: " + colorOfBannerBackground + "; color: white; text-align: center; padding: 10px; opacity: 0; transition: opacity 1s;";

				session.setAttribute(bannerAttribute, "");

				return "<div class=\"banner\" style=\"" + styles + "\" id=\"banner\">" + attribute.strip() + "</div>\n" +
						"<script>\n" +
						"  const banner = document.getElementById('banner');\n" +
						"\n" +
						"  function showBanner() {\n" +
						"    banner.style.display = 'block';\n" +
						"    banner.style.opacity = '1';\n" +
						"    setTimeout(function() {\n" +
						"      banner.style.opacity = '0';\n" +
						"      setTimeout(function() {\n" +
						"        banner.style.display = 'none';\n" +
						"      }, 1000); // Wait for the fade-out transition to complete (1 second)\n" +
						"    }, 5000); // 5 seconds\n" +
						"  }\n" +
						"\n" +
						"  // Call the function to show the banner (you can trigger this function when needed)\n" +
						"  showBanner();\n" +
						"</script>";
			}
		}
		return "";
	}
}
