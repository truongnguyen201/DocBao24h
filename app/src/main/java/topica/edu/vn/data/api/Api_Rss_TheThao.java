package topica.edu.vn.data.api;

import retrofit2.Call;
import retrofit2.http.GET;
import topica.edu.vn.data.model.TheThao;
import topica.edu.vn.data.rss.thethao.Rss;

public interface Api_Rss_TheThao {
    String Base_URL ="https://cdn.24h.com.vn/";
    @GET("upload/rss/thethao.rss")
    Call<Rss> getRss();


}
