package topica.edu.vn.adapter;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

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
import topica.edu.vn.data.model.TrangChu;

public class TrangChu_Adapter extends RecyclerView.Adapter<TrangChu_Adapter.TrangChu_ViewHodel> {
    private NotificationManagerCompat notificationManagerCompat;
    private Context context;
    private Database db;
    private static final String CHANNEL_1_ID="channel";
    private ArrayList<TrangChu> mTrangChu;

    public TrangChu_Adapter(Context context, ArrayList<TrangChu> mTrangChu) {
        this.context = context;
        this.mTrangChu = mTrangChu;
    }


    @NonNull
    @Override
    public TrangChu_ViewHodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        TrangChu_ViewHodel viewHodel=new TrangChu_ViewHodel(v);
        return viewHodel;
    }

    @Override
    public void onBindViewHolder(@NonNull TrangChu_ViewHodel holder, int position) {
        notificationManagerCompat=NotificationManagerCompat.from(context);
        final TrangChu trangChu=mTrangChu.get(position);
        holder.textView01.setText(trangChu.getTitle());
        holder.textViewTime.setText(trangChu.getTime());
        if(!trangChu.img.equals(""))
        {
            Picasso.get().load(trangChu.img).into(holder.img01);
        }
        db=new Database(context);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivity2.class);
                intent.putExtra("URL",trangChu.getLink());
                context.startActivity(intent);
            }
        });
        holder.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               sendChannel();
               String title=trangChu.title;
               String link=trangChu.link;
               String img=trangChu.img;
               String time=trangChu.time;
               DaLuu daLuu=new DaLuu(title,img,link,time);
               db.addTinTuc(daLuu);

            }

            private void sendChannel() {
               Notification notification =new NotificationCompat.Builder(context,CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.save)
                        .setContentTitle(trangChu.title)
                        .setContentText(trangChu.link)
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
        return mTrangChu.size();
    }

    public static class TrangChu_ViewHodel extends RecyclerView.ViewHolder {
        private ImageButton btnSave;
        private ImageView img01;
        private TextView textView01;
        private TextView textViewTime;

        public TrangChu_ViewHodel(@NonNull View itemView) {
            super(itemView);
            textViewTime=itemView.findViewById(R.id.textViewTime);
            btnSave=itemView.findViewById(R.id.btnSave);
            img01=itemView.findViewById(R.id.img01);
            textView01=itemView.findViewById(R.id.textView01);

        }
    }
}
