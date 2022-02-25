package topica.edu.vn.docbao24h;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.core.widget.NestedScrollView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Locale;

import topica.edu.vn.data.model.TrangChu;

public class MainActivity2 extends AppCompatActivity {
    private TrangChu trangchu;
    private TextView textViewtitle;
    private ImageView imageView;
    private TextView textViewtime;
    private TextView textViewnoidung;
    private NestedScrollView scrollView;
    //button
    private View back_drop;
    private boolean rotate = false;
    private View lyt_mic;
    private View lyt_call;
    private View mreader;
    private Button btnnho;
    private Button btnvua;
    private Button btnlon;
    private Button btnngay;
    private Button btndem;
    private Button btnnoi;
    private Button btntamdung;
    SharedPreferences sharedPreferences;
    private TextToSpeech textToSpeech;
    int check=0;

    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        addControls();
        addEvents();
    }

    private void addEvents() {
        textToSpeech=new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    int result=textToSpeech.setLanguage(Locale.GERMAN);

                    if(result==TextToSpeech.LANG_MISSING_DATA ||
                            result==TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS","Language not supported");
                    }
                }else{
                    Log.e("TTS","Language");
                }
            }
        });
        sharedPreferences = getSharedPreferences("cochutrangcon",MODE_PRIVATE);
        setTextSize();
        setColor();
        initShowOut(lyt_mic);
        initShowOut(lyt_call);
        initShowOut(mreader);
        back_drop.setVisibility(View.GONE);
        //button add
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(v);
            }
        });
        //
        back_drop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFabMode(floatingActionButton);
            }
        });
        //button nho
        btnnho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noidung",10);
                editor.putInt("time",5);
                editor.putInt("title",13);
                editor.putBoolean("check",true);
                editor.commit();
                setTextSize();
            }
        });
        //btn vua
        btnvua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noidung",20);
                editor.putInt("time",10);
                editor.putInt("title",25);
                editor.putBoolean("check",true);
                editor.commit();
                setTextSize();
            }
        });

        //end
        //btnlon
        btnlon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("noidung",25);
                editor.putInt("time",13);
                editor.putInt("title",35);
                editor.putBoolean("check",true);
                editor.commit();
                setTextSize();
            }
        });
        //end
        //button ngay
        btndem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                int color = res.getColor(R.color.black);
                int textcolor = res.getColor(R.color.white);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("backgroup",color);
                editor.putInt("txttitle",textcolor);
                editor.putInt("txttime",textcolor);
                editor.putInt("txtnoidung",textcolor);
                editor.commit();
                setColor();
            }
        });
        //end
        //btndem
        btnngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Resources res = getResources();
                int color = res.getColor(R.color.white);
                int textcolor = res.getColor(R.color.black);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("backgroup",color);
                editor.putInt("txttitle",textcolor);
                editor.putInt("txttime",textcolor);
                editor.putInt("txtnoidung",textcolor);
                editor.commit();
                setColor();
            }
        });
        //end
        //btn tam dung
        btntamdung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textToSpeech.stop();
            }
        });
        //end
        iniComponent();
        //nhan du lieu
        final Intent intent =getIntent();
        final String url= intent.getStringExtra("URL");
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String Title="";
                String time="";
                String img="";
                String noidung="";
                Document document = Jsoup.parse(response);
                if(document!=null){
                    Elements element =document.select("article.nwsHt") ;
                    Elements elementsimg = document.select("img.news-image");
                    if(elementsimg != null){
                        img =elementsimg.attr("src");
                    }
                    Elements elementsp = document.select("p");
                    if(elementsp != null){
                        noidung =elementsp.text();
                    }
                    for(Element elements : element){
                        Element elementTitle = elements.getElementsByTag("h1").first();
                        Element elementTime = elements.getElementsByTag("div").first();
                        if(elementTitle != null){
                            Title =elementTitle.text();
                        }
                        if(elementTime != null){
                            time =elementTime.text();
                        }
                    }
                    textViewnoidung.setText(noidung);
                    textViewtime.setText(time);
                    textViewtitle.setText(Title);
                    Picasso.get().load(img).into(imageView);
                    //btn noi
                    final String finalNoidung = noidung;

                    btnnoi.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            speak();
                        }
                    });
                    //end
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);
    }

    private void speak() {
        textToSpeech.speak(textViewtitle.getText().toString(),TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onDestroy() {
        if(textToSpeech!=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }

    private void addControls() {
        //anh xa
        textViewnoidung = findViewById(R.id.txtnoidung);
        textViewtime =findViewById(R.id.txttime);
        textViewtitle =findViewById(R.id.txttitle);
        imageView =findViewById(R.id.imgcon);

        back_drop = findViewById(R.id.back_drop);

        btnnho =findViewById(R.id.btn1);
        btnvua =findViewById(R.id.btn2);
        btnlon =findViewById(R.id.btn3);
        //btnngay dem
        scrollView =findViewById(R.id.nested_scroll_view);
        btndem =findViewById(R.id.btnlight2);
        btnngay =findViewById(R.id.btnlight1);
        //btn noi
        btnnoi =findViewById(R.id.btnReader1);
        btntamdung =findViewById(R.id.btnReader2);

        lyt_mic = findViewById(R.id.lyt_mic);
        lyt_call = findViewById(R.id.lyt_call);
        mreader = findViewById(R.id.reader);
        floatingActionButton=findViewById(R.id.fab_add);

    }

    private  void setColor(){
        textViewtitle.setTextColor(sharedPreferences.getInt("txttitle",0));
        textViewtime.setTextColor(sharedPreferences.getInt("txttime",0));
        textViewnoidung.setTextColor(sharedPreferences.getInt("txtnoidung",0));
        scrollView.setBackgroundColor(sharedPreferences.getInt("backgroup",0));
    }
    private void setTextSize(){
        textViewnoidung.setTextSize(sharedPreferences.getInt("noidung",0));
        textViewtime.setTextSize(sharedPreferences.getInt("time",0));
        textViewtitle.setTextSize(sharedPreferences.getInt("title",0));
    }
    //btncauhinh
    private void toggleFabMode(View v) {
        rotate = rotateFab(v, !rotate);
        if (rotate) {
            showIn(lyt_mic);
            showIn(lyt_call);
            showIn(mreader);
            back_drop.setVisibility(View.VISIBLE);
        } else {
            showOut(lyt_mic);
            showOut(lyt_call);
            showOut(mreader);
            back_drop.setVisibility(View.GONE);
        }
    }
    public static void initShowOut(final View v) {
        v.setVisibility(View.GONE);
        v.setTranslationY(v.getHeight());
        v.setAlpha(0f);
    }
    public static boolean rotateFab(final View v, boolean rotate) {
        v.animate().setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .rotation(rotate ? 135f : 0f);
        return rotate;
    }
    public static void showOut(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(1f);
        v.setTranslationY(0);
        v.animate()
                .setDuration(200)
                .translationY(v.getHeight())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        v.setVisibility(View.GONE);
                        super.onAnimationEnd(animation);
                    }
                }).alpha(0f)
                .start();
    }

    public static void showIn(final View v) {
        v.setVisibility(View.VISIBLE);
        v.setAlpha(0f);
        v.setTranslationY(v.getHeight());
        v.animate()
                .setDuration(200)
                .translationY(0)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                    }
                })
                .alpha(1f)
                .start();
    }
    //end
    //scorll view
    boolean hide = false;
    private void iniComponent() {
        ((NestedScrollView) findViewById(R.id.nested_scroll_view)).setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY >= oldScrollY) { // down
                    if (hide) return;
                    hide = true;
                } else {
                    if (!hide) return;
                    hide = false;
                }
            }
        });
    }
    //Chia sẻ

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.menushare, menu);
        //MenuItem item = menu.findItem(R.id.cse);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.cse:
                    share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Drawable drawable=imageView.getDrawable();
        Bitmap bitmap=((BitmapDrawable)drawable).getBitmap();

        try {
            File file = new File(getApplicationContext().getExternalCacheDir(), File.separator +"menu.png");
            FileOutputStream fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
            file.setReadable(true, false);
            final Intent intent = new Intent(android.content.Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri photoURI = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID +".provider", file);

            intent.putExtra(Intent.EXTRA_STREAM, photoURI);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/jpg");

            startActivity(Intent.createChooser(intent, "Share image via"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}