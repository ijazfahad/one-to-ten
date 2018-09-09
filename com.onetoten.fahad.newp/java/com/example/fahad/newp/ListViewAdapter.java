package com.example.fahad.newp;

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
import com.onetoten.fahad.newp.R;
import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends BaseAdapter {
    private ArrayList<HomePost> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<HomePost> post = null;

    public class ViewHolder {
        TextView AvgRating;
        TextView Caption;
        TextView CreatedAt;
        TextView Fname;
        TextView Lname;
        TextView Name;
        String ObjectId;
        ImageView Ppicture;
        TextView create;
        ImageView picture;
    }

    public ListViewAdapter(Context context, List<HomePost> post) {
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

    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = this.inflater.inflate(R.layout.single_notification, null);
            holder.Name = (TextView) view.findViewById(R.id.PlistView);
            holder.Caption = (TextView) view.findViewById(R.id.textView7);
            holder.CreatedAt = (TextView) view.findViewById(R.id.dpPicture);
            holder.AvgRating = (TextView) view.findViewById(R.id.Rimage);
            holder.picture = (ImageView) view.findViewById(R.id.HCreatedAt);
            holder.Ppicture = (ImageView) view.findViewById(R.id.textView13);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.CreatedAt.setText(((HomePost) this.post.get(position)).getCreatedAt().toString());
        holder.Name.setText(((HomePost) this.post.get(position)).getName());
        holder.AvgRating.setText(((HomePost) this.post.get(position)).getAvgRating());
        holder.Caption.setText(((HomePost) this.post.get(position)).getCaption());
        holder.ObjectId = ((HomePost) this.post.get(position)).getObjectId();
        this.imageLoader.DisplayImage(((HomePost) this.post.get(position)).getPicture(), holder.picture);
        this.imageLoader.DisplayImage(((HomePost) this.post.get(position)).getPpicture(), holder.Ppicture);
        holder.Name.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent i = new Intent(ListViewAdapter.this.context, OthersProfile.class);
                i.putExtra("CUobjectId", ((HomePost) ListViewAdapter.this.post.get(position)).getCUobjectID());
                i.putExtra("Name", ((HomePost) ListViewAdapter.this.post.get(position)).getName());
                i.putExtra("School", ((HomePost) ListViewAdapter.this.post.get(position)).getSchool());
                i.putExtra("UserName", ((HomePost) ListViewAdapter.this.post.get(position)).getUsername());
                ListViewAdapter.this.context.startActivity(i);
                ((Activity) ListViewAdapter.this.context).finish();
            }
        });
        holder.picture.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(ListViewAdapter.this.context, SingleItemView.class);
                intent.putExtra("ObjectId", ((HomePost) ListViewAdapter.this.post.get(position)).getObjectId());
                intent.putExtra("Name", ((HomePost) ListViewAdapter.this.post.get(position)).getName());
                intent.putExtra("Caption", ((HomePost) ListViewAdapter.this.post.get(position)).getCaption());
                intent.putExtra("picture", ((HomePost) ListViewAdapter.this.post.get(position)).getPicture());
                intent.putExtra("dp", ((HomePost) ListViewAdapter.this.post.get(position)).getPpicture());
                intent.putExtra("date", ((HomePost) ListViewAdapter.this.post.get(position)).getCreatedAt().toString());
                ListViewAdapter.this.context.startActivity(intent);
            }
        });
        return view;
    }
}
