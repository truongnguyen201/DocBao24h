package topica.edu.vn.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import topica.edu.vn.data.rss.congnghe.Rss;

public interface Api_Rss_CongNghe {
    String Base_Url="https://cdn.24h.com.vn/";
    @GET("upload/rss/congnghethongtin.rss")
    public Call<Rss> getRss();
}
