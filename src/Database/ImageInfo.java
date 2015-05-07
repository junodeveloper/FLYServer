package Database;

public class ImageInfo {
	double latitude, longitude;
	int radius;
	String fPath;
	
	public ImageInfo(double latitude, double longitude, int radius) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.radius = radius;
	}
	
	public boolean inRange(double latitude, double longitude) {
		// TODO: inRange Source Code
		return true;
	}
	
	public String getPath() {
		return fPath;
	}
}
