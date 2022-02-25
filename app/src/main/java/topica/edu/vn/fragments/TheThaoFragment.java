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
import topica.edu.vn.adapter.TheThao_Adapter;
import topica.edu.vn.data.api.Api_Rss_TheThao;
import topica.edu.vn.data.model.Congnghe;
import topica.edu.vn.data.model.TheThao;
import topica.edu.vn.data.rss.thethao.Rss;
import topica.edu.vn.data.rss.thethao.RssItem;
import topica.edu.vn.docbao24h.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TheThaoFragment} factory method to
 * create an instance of this fragment.
 */
public class TheThaoFragment extends Fragment {
    public static final  String TAG="TheThaoFrament";

    private ArrayList<TheThao>thethaoArrayList;
    private RecyclerView  recyclerView;
    private TheThao_Adapter adapter;

    private Context context;
    View view;
    private static final String Base_Url="https://cdn.24h.com.vn/";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thethaoArrayList=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Base_Url)
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        Api_Rss_TheThao api_rss_theThao=retrofit.create(Api_Rss_TheThao.class);//đay là một inter face
        Call<Rss> call=api_rss_theThao.getRss();
        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                String title="";
                String time="";
                String img="";
                String link="";
                List<RssItem> list=response.body().getChannel().getItem();
                for (int i=0;i<list.size();i++)
                {
                    title=list.get(i).getTitle();
                    link=list.get(i).getLink();
                    time=list.get(i).getPubDate();

                    String cdata=list.get(i).getDescription();
                    Pattern pattern=Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher matcher=pattern.matcher(cdata);
                    if(matcher.find())
                    {
                        img=matcher.group(1);
                    }

                    thethaoArrayList.add(new TheThao(title,img,link,time));

                }

                adapter=new TheThao_Adapter(getActivity(),thethaoArrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG,"expashi::::"+t.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     view=inflater.inflate(R.layout.fragment_the_thao,container,false);
       recyclerView=view.findViewById(R.id.recycleThethao);
        return view;
    }
}