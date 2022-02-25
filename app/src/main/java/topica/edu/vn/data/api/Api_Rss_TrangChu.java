package topica.edu.vn.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import topica.edu.vn.data.rss.trangchu.Rss;

public interface Api_Rss_TrangChu {
    String Base_URL ="https://cdn.24h.com.vn/";
    @GET("upload/rss/trangchu24h.rss")
    Call<Rss> getRss();
}
