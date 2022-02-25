package topica.edu.vn.docbao24h;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import java.util.ArrayList;

import topica.edu.vn.adapter.DaLuu_Adapter;
import topica.edu.vn.data.Database;
import topica.edu.vn.data.model.DaLuu;

public class MainActivity3 extends AppCompatActivity {


    ArrayList<DaLuu>list;
    RecyclerView recyclerView;
    DaLuu_Adapter adapter;

    Context context;

    Database db;

    private static final String Base_Url="https://cdn.24h.com.vn/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        recyclerView=findViewById(R.id.recycleDaLuu);
        db=new Database(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter= new DaLuu_Adapter(context,list);
        list=db.getAllDaLuu();
        adapter.setmDaLuu(list);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menuseach,menu);
        MenuItem mnuitem=menu.findItem(R.id.mSeach);
        SearchView searchView= (SearchView) mnuitem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setmDaLuu(db.readItemBySearchKey(newText));
                adapter.notifyDataSetChanged();
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}