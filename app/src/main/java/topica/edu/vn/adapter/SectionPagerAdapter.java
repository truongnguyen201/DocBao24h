package topica.edu.vn.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import topica.edu.vn.data.model.TrangChu;
import topica.edu.vn.fragments.AmThucFragment;
import topica.edu.vn.fragments.CongNgheFragment;
import topica.edu.vn.fragments.TheThaoFragment;
import topica.edu.vn.fragments.TrangChuFragment;

public class SectionPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment=null;
        switch (position)
        {
            case 0:
                fragment=new TrangChuFragment();
                break;
            case 1:
                fragment=new CongNgheFragment();
                break;
            case 2:
                fragment=new TheThaoFragment();
                break;
            case 3:
                fragment=new AmThucFragment();
                break;
            default:fragment=new TrangChuFragment();
        }
        return  fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title;
        switch (position)
        {
            case 0:
                title="Trang chủ";
                break;

            case 1:
                title="Công nghệ";
                break;
            case 2:
                title="Thể Thao";
                break;
            case 3:
                title="Ẩm Thực";
                break;
            default:title= "Trang chủ";
        }
        return title;
    }

}
