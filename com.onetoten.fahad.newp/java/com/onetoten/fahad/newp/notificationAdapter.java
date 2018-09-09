package com.onetoten.fahad.newp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class notificationAdapter extends BaseAdapter {
    private ArrayList<notificationPost> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<notificationPost> post = null;

    public class ViewHolder {
        TextView CreatedAt;
        TextView CreatedBy;
        TextView create;
        TextView name;
        ImageView picture;
        TextView rating;
    }

    public notificationAdapter(Context context, List<notificationPost> post) {
        this.context = context;
        this.post = post;
        this.inflater = LayoutInflater.from(context);
        this.array = new ArrayList();
        this.array.addAll(post);
        this.imageLoader = new ImageLoader(context);
    }

    public int getCount() {
        return this.post.size();
    }

    public Object getItem(int position) {
        return this.post.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.inflater.inflate(R.layout.single_notification, null);
            holder.rating = (TextView) view.findViewById(R.id.nRating);
            holder.name = (TextView) view.findViewById(R.id.createdBy);
            holder.picture = (ImageView) view.findViewById(R.id.nPicture);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.rating.setText(((notificationPost) this.post.get(position)).getnRating());
        holder.name.setText(((notificationPost) this.post.get(position)).getCreatedby());
        this.imageLoader.DisplayImage(((notificationPost) this.post.get(position)).getCreatedbyDp(), holder.picture);
        view.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                notificationAdapter.this.context.startActivity(new Intent(notificationAdapter.this.context, Profile.class));
                ((Activity) notificationAdapter.this.context).finish();
            }
        });
        return view;
    }
}
