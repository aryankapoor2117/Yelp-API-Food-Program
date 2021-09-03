package PA3;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("address")
    @Expose
    private String address;

    @SerializedName("latitude")
    @Expose
    private Double latitude;

    @SerializedName("longitude")
    @Expose
    private Double longitude;

    @SerializedName("drivers")
    @Expose
    private Integer drivers;

    @SerializedName("menu")
    @Expose
    private List<String> menu = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getDrivers() {
        return drivers;
    }

    public void setDrivers(Integer drivers) {
        this.drivers = drivers;
    }

    public List<String> getMenu() {
        return menu;
    }

    public void setMenu(List<String> menu) {
        this.menu = menu;
    }

    public double getDistance(double lat, double lon) {
        double delta = lon - longitude;
        double dist = Math.sin(Math.toRadians(lat)) * Math.sin(Math.toRadians(latitude))
                + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(delta));
        dist = Math.acos(dist);
        dist = 3963.0 * dist;
        return dist;
    }

}
