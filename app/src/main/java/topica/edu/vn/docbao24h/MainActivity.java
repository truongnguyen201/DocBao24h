package topica.edu.vn.docbao24h;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

import topica.edu.vn.adapter.DaLuu_Adapter;
import topica.edu.vn.adapter.SectionPagerAdapter;
import topica.edu.vn.data.Database;
import topica.edu.vn.data.model.DaLuu;
import topica.edu.vn.data.model.TrangChu;

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ImageView imageView;


    private DaLuu_Adapter daLuu_adapter;
    private ArrayList<DaLuu> list;
    private TrangChu trangChu;
    private Database database;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createNotificationChannels();

        initComponent();
        imageView = (ImageView) findViewById(R.id.img01);
    }

    private void createNotificationChannels() {
    }

    private void initComponent() {
        viewPager =  findViewById(R.id.viewpage01);
        setupViewPager(viewPager);
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager view_pager) {
        SectionPagerAdapter adapter = new SectionPagerAdapter(getApplicationContext(),getSupportFragmentManager());
        view_pager.setAdapter(adapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mnudaluu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.set1:
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                MainActivity.this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}