package com.example.fahad.newp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.onetoten.fahad.newp.R;
import java.util.ArrayList;
import java.util.List;

public class HottestInSchoolAdapter extends BaseAdapter {
    String Name;
    private ArrayList<HottestPost> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<HottestPost> post1 = null;

    public class ViewHolder {
        TextView AvgRate;
        TextView Name;
        TextView UserName;
        ImageView picture;
    }

    public HottestInSchoolAdapter(Context context, List<HottestPost> post) {
        this.context = context;
        this.post1 = post;
        this.inflater = LayoutInflater.from(context);
        this.array = new ArrayList();
        this.array.addAll(post);
        this.imageLoader = new ImageLoader(context);
    }

    public int getCount() {
        return this.post1.size();
    }

    public Object getItem(int position) {
        return this.post1.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.inflater.inflate(R.layout.activity_upload__image, null);
            holder.AvgRate = (TextView) view.findViewById(R.id.textView17);
            holder.Name = (TextView) view.findViewById(R.id.caption);
            holder.UserName = (TextView) view.findViewById(R.id.HoimageView);
            holder.picture = (ImageView) view.findViewById(R.id.hotName);
            holder.Name.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    Intent i = new Intent(HottestInSchoolAdapter.this.context, OthersProfile.class);
                    i.putExtra("CUobjectId", ((HottestPost) HottestInSchoolAdapter.this.post1.get(position)).getHoCUobjectId());
                    i.putExtra("Name", ((HottestPost) HottestInSchoolAdapter.this.post1.get(position)).getHoName());
                    i.putExtra("School", ((HottestPost) HottestInSchoolAdapter.this.post1.get(position)).getHoSchool());
                    i.putExtra("UserName", ((HottestPost) HottestInSchoolAdapter.this.post1.get(position)).getHoUsername());
                    HottestInSchoolAdapter.this.context.startActivity(i);
                }
            });
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.Name.setText(((HottestPost) this.post1.get(position)).getHoName());
        holder.UserName.setText(((HottestPost) this.post1.get(position)).getHoUsername());
        holder.AvgRate.setText(((HottestPost) this.post1.get(position)).getHoRating());
        this.imageLoader.DisplayImage(((HottestPost) this.post1.get(position)).getHoDp(), holder.picture);
        return view;
    }
}
