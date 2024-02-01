package servlets;

public interface ConstantsInterface {
	String pathToProject = "/home/none/Dropbox/main1/Java/sign-in-and-sign-up/";
	String pathToClientsData = pathToProject + "src/main/resources/users";
	String pathToImages = pathToProject + "src/main/webapp/img";

	String signInAttribute = "needSignIn";
	String bannerAttribute = "bannerAttribute";

	String messageThatClientAlreadySignedIn = "You are already signed in into your account. Please log out in order to get access to this page.";

	String domainName = "localhost:8080";
	String pathToSignInPage = "/sign-in";
	String pathToSignUpPage = "/sign-up";
	String pathToHomePage = "/home";

	String httpScheme = "http://";
}
