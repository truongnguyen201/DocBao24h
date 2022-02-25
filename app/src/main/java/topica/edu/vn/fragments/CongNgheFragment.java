package topica.edu.vn.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import topica.edu.vn.adapter.CongNghe_Adapter;
import topica.edu.vn.data.api.Api_Rss_CongNghe;
import topica.edu.vn.data.model.Congnghe;
import topica.edu.vn.data.rss.congnghe.Rss;
import topica.edu.vn.data.rss.congnghe.RssItem;
import topica.edu.vn.docbao24h.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CongNgheFragment} factory method to
 * create an instance of this fragment.
 */
public class CongNgheFragment extends Fragment {
    private static final String TAG="AmThucFrament";


    private ArrayList<Congnghe> congngheArrayList;
    private RecyclerView recyclerView;
    private CongNghe_Adapter congNghe_adapter;
    private Context context;
    View view;
    private static final String BASE_URL="https://cdn.24h.com.vn/";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControls();

    }

    private void addControls() {
        congngheArrayList = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        Api_Rss_CongNghe api_rss_congNghe =retrofit.create(Api_Rss_CongNghe.class);
        Call<Rss> rssCall=api_rss_congNghe.getRss();
        rssCall.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                String title="";
                String img="";
                String link="";
                String time="";
                List<RssItem> rssList=response.body().getChannel().getItems();
                for (int i=0;i<rssList.size();i++)
                {
                    title=rssList.get(i).getTitle();
                    link=rssList.get(i).getLink();
                    String cdata=rssList.get(i).getDescription();
                    time=rssList.get(i).getPubDate();
                    Pattern pattern=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher matcher= pattern.matcher(cdata);
                    if(matcher.find())
                    {
                        img=matcher.group(1);
                    }
                    congngheArrayList.add(new Congnghe(title,img,link,time));
                }

                congNghe_adapter=new CongNghe_Adapter(getActivity(),congngheArrayList);
                recyclerView.setAdapter(congNghe_adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG,t.getMessage());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_cong_nghe,container,false);
       recyclerView=view.findViewById(R.id.recycleCongNghe);
        return view;
    }
}