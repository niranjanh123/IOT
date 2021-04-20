package com.example.iot;



import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.iot.Items;
import com.example.iot.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class TabPageAdapter extends PagerAdapter {
    private Context mContext;
    private List<Items> contentItems;

    public TabPageAdapter(Context mContext, List<Items> contentItems) {
        this.mContext = mContext;
        this.contentItems = contentItems;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        assert layoutInflater != null;
        @SuppressLint("InflateParams") View layoutItems = layoutInflater.inflate(R.layout.slide_tab_layout, null);

        TextView first = layoutItems.findViewById(R.id.TopText);
        TextView title = layoutItems.findViewById(R.id.Title);
        TextView description = layoutItems.findViewById(R.id.Description);
        ImageView images = layoutItems.findViewById(R.id.Image);

        first.setText(contentItems.get(position).getFirst());
        title.setText(contentItems.get(position).getTitles());
        description.setText(contentItems.get(position).getDescription());
        images.setImageResource(contentItems.get(position).getImages());
        container.addView(layoutItems);
        return layoutItems;

    }

    @Override
    public int getCount() {
        return contentItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
