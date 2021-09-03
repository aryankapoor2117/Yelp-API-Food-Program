package PA3;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurants {

	@SerializedName("data")
	@Expose
	private List<Restaurant> data = null;

	public List<Restaurant> getData() {
		return data;
	}

	public void setData(List<Restaurant> data) {
		this.data = data;
	}

}
