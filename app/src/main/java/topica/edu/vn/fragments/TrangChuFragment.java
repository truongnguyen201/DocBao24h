package topica.edu.vn.fragments;

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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.SimpleXmlConverterFactory;
import topica.edu.vn.adapter.TrangChu_Adapter;
import topica.edu.vn.data.api.Api_Rss_TrangChu;
import topica.edu.vn.data.model.TrangChu;
import topica.edu.vn.data.rss.trangchu.Rss;
import topica.edu.vn.data.rss.trangchu.RssItem;
import topica.edu.vn.data.rss.trangchu.Rssdescription;
import topica.edu.vn.data.rss.trangchu.Rssimg;
import topica.edu.vn.docbao24h.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TrangChuFragment} factory method to
 * create an instance of this fragment.
 */
public class TrangChuFragment extends Fragment {
    private static final String TAG="TrangChuFragment";

    private ArrayList<TrangChu>arrayList;
    private RecyclerView recyclerView;
    private TrangChu_Adapter adapter;

    private static final String Base_Url="https://cdn.24h.com.vn/";

    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        arrayList=new ArrayList<>();
        Retrofit retrofit=new Retrofit.Builder().baseUrl(Base_Url).addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        Api_Rss_TrangChu api_rss_trangChu=retrofit.create(Api_Rss_TrangChu.class);
        Call<Rss> call=api_rss_trangChu.getRss();
        call.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                String title="";
                String img="";
                String time="";
                String link="";
                List<RssItem> list=response.body().getChannel().getItems();
                for (int i=0;i<list.size();i++)
                {
                    title=list.get(i).getTitle();
                    time=list.get(i).getTime();
                    link=list.get(i).getLink();

                    List<Rssdescription>rssdescriptions=list.get(i).getMdescriptionList();
                    List<Rssimg> rssimg=rssdescriptions.get(0).getListimg();//lấy description thứ 0 co một cai description
                    if(!rssimg.get(0).getSrc().equals(""))//lay hinh ở vt1 co motjn hinh
                    {
                        img=rssimg.get(0).getSrc();
                    }
                    arrayList.add(new TrangChu(title,img,link,time));

                }
                adapter=new TrangChu_Adapter(getActivity(),arrayList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                Log.d(TAG," expashi:::: "+t.getMessage());
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_trang_chu,container,false);
      recyclerView=view.findViewById(R.id.recycleTrangChu);
        return view;

    }
}