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
import topica.edu.vn.data.model.AmThuc;
import topica.edu.vn.data.model.DaLuu;

public class AmThuc_Adapter extends RecyclerView.Adapter<AmThuc_Adapter.AmThuc_Hodel> {

    private Context context;
    private ArrayList<AmThuc> mAnThuc;
    private NotificationManagerCompat notificationManagerCompat;
    private Database db;

    public AmThuc_Adapter(Context context, ArrayList<AmThuc> mAnThuc) {
        this.context = context;
        this.mAnThuc = mAnThuc;
    }

    @NonNull
    @Override
    public AmThuc_Hodel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.item_layout,parent,false);
        AmThuc_Hodel amThuc_hodel=new AmThuc_Hodel(v);
        return amThuc_hodel;

    }

    @Override
    public void onBindViewHolder(@NonNull AmThuc_Hodel holder, int position) {
        notificationManagerCompat=NotificationManagerCompat.from(context);
        final AmThuc amThuc=mAnThuc.get(position);
        if(!amThuc.img.equals(""))
        {
            Picasso.get().load(amThuc.getImg()).into(holder.img01);
        }
        db=new Database(context);
        holder.textView01.setText(amThuc.getTitle());
        holder.textViewTime.setText(amThuc.getTime());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, MainActivity2.class);
                intent.putExtra("URL",amThuc.getLink());
                context.startActivity(intent);
            }
        });
        holder.btnSave.setOnClickListener(new View.OnClickListener() {

            private static final String CHANNEL_1_ID="channel";

            @Override
            public void onClick(View v) {
                try {
                    sendOnChange();
                    String ten=amThuc.title;
                    String img=amThuc.img;
                    String link=amThuc.link;
                    String time=amThuc.time;

                    DaLuu daLuu=new DaLuu(ten,img,link,time);
                    db.addTinTuc(daLuu);
                    Toast.makeText(v.getContext(),"Lưu thành công",Toast.LENGTH_LONG).show();
                }
                catch (Exception ex)
                {
                    Toast.makeText(v.getContext(),"Lưu thất bại!!" + "", Toast.LENGTH_SHORT).show();
                }

            }

            private void sendOnChange() {
                String title=amThuc.title;
                String message=amThuc.link;
                Notification notification=new NotificationCompat.Builder(context,CHANNEL_1_ID)
                        .setSmallIcon(R.drawable.save)
                        .setContentTitle(title)
                        .setContentText(message)
                        .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                         .setPriority(Notification.PRIORITY_LOW)
                        .setCategory(Notification.CATEGORY_PROMO)
                        .build();
                int notificationID=1;
                notificationManagerCompat.notify(notificationID,notification);

            }
        });


    }



    @Override
    public int getItemCount() {
        return mAnThuc.size();
    }

    public static class AmThuc_Hodel extends RecyclerView.ViewHolder
    {
        private ImageView img01;
        private TextView textView01;
        private ImageButton btnSave;
        private TextView textViewTime;

        public AmThuc_Hodel(@NonNull View itemView) {
            super(itemView);
            img01=itemView.findViewById(R.id.img01);
            textView01=itemView.findViewById(R.id.textView01);
            btnSave=itemView.findViewById(R.id.btnSave);
           textViewTime=itemView.findViewById(R.id.textViewTime);

        }
    }

}
