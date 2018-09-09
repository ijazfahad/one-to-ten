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

public class otherProfileAdapter extends BaseAdapter {
    String Name;
    private ArrayList<otherProfilePost> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<otherProfilePost> post1 = null;

    public class ViewHolder {
        TextView AvgRate;
        TextView Caption;
        TextView CreatedAt;
        TextView create;
        ImageView picture;
    }

    public otherProfileAdapter(Context context, List<otherProfilePost> post) {
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
            view = this.inflater.inflate(R.layout.listview_main, null);
            holder.AvgRate = (TextView) view.findViewById(R.id.Pcaption);
            holder.Caption = (TextView) view.findViewById(R.id.PLrating);
            holder.CreatedAt = (TextView) view.findViewById(R.id.Rdp);
            holder.picture = (ImageView) view.findViewById(R.id.button7);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.CreatedAt.setText(((otherProfilePost) this.post1.get(position)).getOpDate().toString());
        holder.Caption.setText(((otherProfilePost) this.post1.get(position)).getOpcaption());
        holder.AvgRate.setText(((otherProfilePost) this.post1.get(position)).getOpAverageRating());
        this.imageLoader.DisplayImage(((otherProfilePost) this.post1.get(position)).getOpPicture(), holder.picture);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(otherProfileAdapter.this.context, SingleItemView.class);
                intent.putExtra("ObjectId", ((otherProfilePost) otherProfileAdapter.this.post1.get(position)).getOpObjectId());
                intent.putExtra("Name", ((otherProfilePost) otherProfileAdapter.this.post1.get(position)).getName());
                intent.putExtra("Caption", ((otherProfilePost) otherProfileAdapter.this.post1.get(position)).getOpcaption());
                intent.putExtra("picture", ((otherProfilePost) otherProfileAdapter.this.post1.get(position)).getOpPicture());
                intent.putExtra("date", ((otherProfilePost) otherProfileAdapter.this.post1.get(position)).getOpDate().toString());
                intent.putExtra("dp", ((otherProfilePost) otherProfileAdapter.this.post1.get(position)).getOdp());
                otherProfileAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
