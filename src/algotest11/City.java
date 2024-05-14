package algotest11;


public class City {
	private String cityName;
	private double longitude;  // x
	private double latitude;  // y
    private double X;
    private double Y;
    
    
    
    
	public double getX() {
		return X;
	}

	public void setX(double x) {
		X = x;
	}

	public double getY() {
		return Y;
	}

	public void setY(double y) {
		Y = y;
	}

	public City(String cityName,double longitude,double latitude , double X ,double Y) {
		this.cityName = cityName;
		this.longitude=longitude;
		this.latitude=latitude;
	    this.X=X;
	    this.Y=Y;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

}