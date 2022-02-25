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
import topica.edu.vn.data.model.Congnghe;
import topica.edu.vn.data.model.DaLuu;

public class CongNghe_Adapter extends RecyclerView.Adapter<CongNghe_Adapter.CongNge_Holder> {
    private NotificationManagerCompat notificationManagerCompat;
    private Context context;
    private ArrayList<Congnghe>mCongNghe;
    private static final String CHANEL_1_ID="channel";
    private Database db;
    public CongNghe_Adapter(Context context, ArrayList<Congnghe> mCongNghe) {
        this.context = context;
        this.mCongNghe = mCongNghe;
    }

    @NonNull
    @Override
    public CongNge_Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        CongNge_Holder rowViewhodle=new CongNge_Holder(row);
        return rowViewhodle;
    }

    @Override
    public void onBindViewHolder(@NonNull CongNge_Holder holder, int position) {
        notificationManagerCompat=NotificationManagerCompat.from(context);
        final Congnghe congnghe=mCongNghe.get(position);

        holder.textView01.setText(congnghe.title);
        holder.textViewTime.setText(congnghe.time);
        if(!congnghe.img.equals(""))
        {
            Picasso.get().load(congnghe.img).into(holder.img01);
        }
        db=new Database(context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivity2.class);
                intent.putExtra("URL",congnghe.link);
                context.startActivity(intent);
            }
        });
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                   sendOnChannel();
                   String title=congnghe.title;
                   String img=congnghe.img;
                   String time=congnghe.time;
                   String link=congnghe.link;
                    DaLuu daLuu=new DaLuu(title,img,link,time);
                   db.addTinTuc(daLuu);
                   Toast.makeText(v.getContext(),"Luu thành cong",Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(v.getContext(),"Lưu thất bại",Toast.LENGTH_LONG).show();
                }
            }

            private void sendOnChannel() {
                Notification notification=new NotificationCompat.Builder(context,CHANEL_1_ID)
                        .setSmallIcon(R.drawable.save)
                        .setContentText(congnghe.link)
                        .setContentTitle(congnghe.title)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                        .setCategory(NotificationCompat.CATEGORY_PROMO)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();

                int notifycationId=1;
                notificationManagerCompat.notify(notifycationId,notification);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCongNghe.size();
    }


    public static class CongNge_Holder extends RecyclerView.ViewHolder {
        private TextView textView01;
        private TextView textViewTime;
        private ImageView img01;
        private ImageButton btnSave;
        public CongNge_Holder(@NonNull View itemView) {
            super(itemView);
            textView01=itemView.findViewById(R.id.textView01);
            textViewTime=itemView.findViewById(R.id.textViewTime);
            img01=itemView.findViewById(R.id.img01);
            btnSave=itemView.findViewById(R.id.btnSave);
        }
    }

}
