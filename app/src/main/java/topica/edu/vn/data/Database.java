package topica.edu.vn.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import topica.edu.vn.data.model.DaLuu;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "luutruFa.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME="tblTrangchuOne";
    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {//tao bang
       db.execSQL("CREATE TABLE "+TABLE_NAME
                + "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT,img TEXT,LinK TEXT,time TEXT,noidung TEXT)");
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
    }

    public void addTinTuc(DaLuu daLuu)//insert
    {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", daLuu.getTitle());
        contentValues.put("img", daLuu.img);
        contentValues.put("Link", daLuu.getLink());
        contentValues.put("time", daLuu.getTime());
        SQLiteDatabase db = getWritableDatabase();
        db.insert("tblTrangChuOne", null, contentValues);
    }

    public void deleteTinTuc(int daLuaID) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete("tblTrangChuOne", "id=?", new String[]{String.valueOf(daLuaID)});
    }

    //show all da Luu
    public ArrayList<DaLuu> getAllDaLuu() {

        ArrayList<DaLuu> dsDaLuu = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query("tblTrangChuOne", null, null, null, null, null, null);

        //Đến dòng đầu của tập dữ liệu
        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            int idDaLuu = cursor.getInt(0);
            String title = cursor.getString(1);
            String img = cursor.getString(2);
            String link = cursor.getString(3);
            String time = cursor.getString(4);
            dsDaLuu.add(new DaLuu(idDaLuu, title, img, link, time));
        }

        cursor.close();
        return dsDaLuu;
    }

    public ArrayList<DaLuu> readItemBySearchKey(String key) {
        ArrayList<DaLuu> list = new ArrayList<>();
        String clause = "title LIKE ?";
        String[] args = {"%" + key + "%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("tblTrangchuOne", null,
                clause, args, null, null, null);
        while (rs.moveToNext()) {
            String TenItem = rs.getString(1);
            String img = rs.getString(2);
            String LinK = rs.getString(3);
            String ngay = rs.getString(4);
            list.add(new DaLuu(TenItem, img, LinK, ngay));
        }
        return list;
    }
    public void delete(int id){
        SQLiteDatabase st=this.getWritableDatabase();
        String query="DELETE FROM tblTrangchuOne WHERE Id='"+id+"'";
        st.execSQL(query);
    }
    public Integer deleteData(String id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        int i=db.delete(TABLE_NAME,"ID=?",new String[]{id});
        return i;
    }
}
