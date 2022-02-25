package topica.edu.vn.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import topica.edu.vn.data.Database;
import topica.edu.vn.docbao24h.MainActivity2;
import topica.edu.vn.docbao24h.R;
import topica.edu.vn.data.model.DaLuu;

public class DaLuu_Adapter extends RecyclerView.Adapter<DaLuu_Adapter.DaLuu_holde> {
    private Context context;
    private ArrayList<DaLuu> mDaLuu;
    private Database database;

    public DaLuu_Adapter(Context context, ArrayList<DaLuu> mDaLuu) {
        this.context = context;
        this.mDaLuu = mDaLuu;
    }

    public ArrayList<DaLuu> getmDaLuu() {
        return mDaLuu;
    }

    public void setmDaLuu(ArrayList<DaLuu> mDaLuu) {
        this.mDaLuu = mDaLuu;
    }

    @NonNull

    @Override
    public DaLuu_holde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View row= LayoutInflater.from(parent.getContext()).inflate(R.layout.daluu_layout,parent,false);
        DaLuu_holde rowhodle=new DaLuu_holde(row);
        return rowhodle;
    }

    @Override
    public void onBindViewHolder(@NonNull DaLuu_holde holder, int position) {
       database=new Database(context);
       final DaLuu daLuu=mDaLuu.get(position);
        holder.txtTileDaluu.setText(daLuu.title);
        holder.txtTimeDaLuu.setText(daLuu.getTime());
        if (!daLuu.img.equals(""))
        {
            Picasso.get().load(daLuu.img).into(holder.imgDaluu);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent=new Intent(v.getContext(), MainActivity2.class);
                    intent.putExtra("URL",daLuu.link);
                    v.getContext().startActivity(intent);
                }
                catch (Exception e){
                    Toast.makeText(v.getContext(),"Không Thành Công!!" + "", Toast.LENGTH_LONG).show();
                }
            }
        });
        holder.btnDeleteDaLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*try
                {
                    mDaLuu.remove(daLuu);
                    database.deleteTinTuc(daLuu.id);
                    notifyDataSetChanged();
                }
                catch (Exception ex)
                {
                    Toast.makeText(v.getContext(),"Không Thành Công!!" + "", Toast.LENGTH_LONG).show();
                }*/

                try {
                    int position1=position;
                    int i= database.deleteData(String.valueOf(position1+1));
                    mDaLuu.remove(position1);
                    Toast.makeText(v.getContext(),"Xoá Thành Công!!" + "", Toast.LENGTH_SHORT).show();
                    if(i>0)
                    {
                        Toast.makeText(v.getContext(),"Xoá Thành Công!!" + "", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(v.getContext(),"Xoá Thành Công!!" + "", Toast.LENGTH_SHORT).show();
                    }
                }
                catch (NullPointerException ex)
                {
                    Toast.makeText(v.getContext(),"Không Thành Công!!" + "", Toast.LENGTH_SHORT).show();}

            }
        });

    }

    @Override
    public int getItemCount() {
        return mDaLuu.size();
    }
    public long getItemId(int position) {
        return position;
    }

    public static class DaLuu_holde extends RecyclerView.ViewHolder {
        private TextView txtTileDaluu;
        private TextView txtTimeDaLuu;
        private ImageView imgDaluu;
        private ImageButton btnDeleteDaLuu;
        public DaLuu_holde(@NonNull View itemView) {
            super(itemView);
            txtTileDaluu=itemView.findViewById(R.id.txtviewTitleDaluu);
            txtTimeDaLuu=itemView.findViewById(R.id.txtviewtimeDaluu);
            imgDaluu=itemView.findViewById(R.id.img01);
            btnDeleteDaLuu=itemView.findViewById(R.id.btnDeleteDaluu);
        }
    }
}
