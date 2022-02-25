package topica.edu.vn.adapter;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import topica.edu.vn.data.Database;
import topica.edu.vn.docbao24h.MainActivity2;
import topica.edu.vn.docbao24h.R;
import topica.edu.vn.data.model.DaLuu;
import topica.edu.vn.data.model.TheThao;

public class TheThao_Adapter extends RecyclerView.Adapter<TheThao_Adapter.TheThao_Holdel> {

    private Context context;
    private ArrayList<TheThao> mTheThao;
    private NotificationManagerCompat notificationManagerCompat;
    private static final String CHANEL_ID="channel";
    private Database db;


    public TheThao_Adapter(Context context, ArrayList<TheThao> mTheThao) {
        this.context = context;
        this.mTheThao = mTheThao;
    }

    @NonNull
    @Override
    public TheThao_Holdel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        TheThao_Holdel rowholdel=new TheThao_Holdel(row);
        return rowholdel;
    }

    @Override
    public void onBindViewHolder(@NonNull TheThao_Holdel holder, int position) {
        db=new Database(context);
        notificationManagerCompat=NotificationManagerCompat.from(context);
        TheThao theThao=mTheThao.get(position);
        holder.textView01.setText(theThao.title);
        holder.textViewTime.setText(theThao.time);
        if(!theThao.img.equals(""))
        {
            Picasso.get().load(theThao.img).into(holder.img01);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(v.getContext(), MainActivity2.class);
                intent.putExtra("URL",theThao.link);
                v.getContext().startActivity(intent);
            }
        });
        db=new Database(context);
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sendChannel();
                    String title=theThao.title;
                    String img=theThao.img;
                    String time=theThao.time;
                    String link=theThao.getLink();
                    DaLuu daLuu=new DaLuu(title,img,link,time);
                    db.addTinTuc(daLuu);
                    Toast.makeText(v.getContext(),"Lưu Thành Công",Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(v.getContext(),"Lưu Thất bại",Toast.LENGTH_LONG).show();
                }
            }

            private void sendChannel() {
                Notification notification=new NotificationCompat.Builder(context,CHANEL_ID)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setSmallIcon(R.drawable.save)
                        .setContentTitle(theThao.title)
                        .setContentText(theThao.link)
                        .setCategory(NotificationCompat.CATEGORY_PROMO)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();
                int notificationID=1;
                notificationManagerCompat.notify(notificationID,notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTheThao.size();
    }

    public static  class TheThao_Holdel extends RecyclerView.ViewHolder
    {
        private ImageView img01;
        private TextView textView01;
        private TextView textViewTime;
        private ImageButton btnSave;
        public TheThao_Holdel(@NonNull View itemView) {
            super(itemView);
            img01=itemView.findViewById(R.id.img01);
            textView01=itemView.findViewById(R.id.textView01);
            textViewTime=itemView.findViewById(R.id.textViewTime);
            btnSave=itemView.findViewById(R.id.btnSave);
        }
    }
}
