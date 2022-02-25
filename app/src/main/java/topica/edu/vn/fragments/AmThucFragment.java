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
import topica.edu.vn.adapter.AmThuc_Adapter;
import topica.edu.vn.data.api.Api_Rss_Amthuc;
import topica.edu.vn.data.model.AmThuc;
import topica.edu.vn.data.rss.amthuc.Rss;
import topica.edu.vn.data.rss.amthuc.RssItem;
import topica.edu.vn.docbao24h.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AmThucFragment} factory method to
 * create an instance of this fragment.
 */
public class AmThucFragment extends Fragment {

    private static String TAG="AmThucFragment";


    private ArrayList<AmThuc> amThucArrayList;
    private RecyclerView recyclerView;
    private AmThuc_Adapter adapter;
    private Context context;

    View view;

    private static  final String BASE_URL="https://cdn.24h.com.vn/";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addControls();


    }

    private void addControls() {
        amThucArrayList=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        Api_Rss_Amthuc api_rss_amthuc=retrofit.create(Api_Rss_Amthuc.class);
        Call<Rss> rssCall=api_rss_amthuc.getRss();
        rssCall.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                String title="";
                String img="";
                String link="";
                String time="";
                List<RssItem> rssItems=response.body().getChannel().getItem();
                for (int i=0;i<rssItems.size();i++) {
                    title=rssItems.get(i).getTitle();
                    String cdata=rssItems.get(i).getDescription();
                    Pattern p=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher matcher=p.matcher(cdata);
                    if(matcher.find())
                    {
                        img=matcher.group(1);
                    }
                    link=rssItems.get(i).getLink();
                    time=rssItems.get(i).getPubDate();
                    amThucArrayList.add(new AmThuc(title,img,link,time));
                }
                adapter =new AmThuc_Adapter(getActivity(),amThucArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG,"test"+t.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_am_thuc,container,false);
        recyclerView=view.findViewById(R.id.recycleAmThuc);
        return view;
    }
}