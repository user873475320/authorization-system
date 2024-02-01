package servlets;

public enum Colors {
	GREEN("#4CAF50"), RED("#fa8072"), BLUE("#87cefa"), ORANGE("#ffa500");

	final String colorCode;
	Colors(String colorCode) {
		this.colorCode = colorCode;
	}

	public String getColorCode() {
		return colorCode;
	}
}
