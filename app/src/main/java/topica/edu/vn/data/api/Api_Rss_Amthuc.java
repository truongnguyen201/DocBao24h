package topica.edu.vn.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import topica.edu.vn.data.rss.amthuc.Rss;

public interface Api_Rss_Amthuc {
    String Base_URL ="https://cdn.24h.com.vn/";
    @GET("upload/rss/amthuc.rss")
    Call<Rss> getRss();
}
