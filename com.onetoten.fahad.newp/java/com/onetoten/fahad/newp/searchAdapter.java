package com.onetoten.fahad.newp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class searchAdapter extends BaseAdapter {
    String Name;
    private ArrayList<searchPost> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<searchPost> post1 = null;

    public class ViewHolder {
        TextView AvgRate;
        TextView Name;
        TextView UserName;
        ImageView picture;
    }

    public searchAdapter(Context context, List<searchPost> post) {
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
            view = this.inflater.inflate(R.layout.single_search_item, null);
            holder.AvgRate = (TextView) view.findViewById(R.id.SearchRating);
            holder.Name = (TextView) view.findViewById(R.id.SearchName);
            holder.UserName = (TextView) view.findViewById(R.id.SearchUsername);
            holder.picture = (ImageView) view.findViewById(R.id.SearchDp);
            holder.Name.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    if (ParseUser.getCurrentUser().get("SchoolName").toString().equals(((searchPost) searchAdapter.this.post1.get(position)).getSearchSchool())) {
                        Intent i = new Intent(searchAdapter.this.context, OthersProfile.class);
                        i.putExtra("CUobjectId", ((searchPost) searchAdapter.this.post1.get(position)).getSearchObjectId());
                        i.putExtra("Name", ((searchPost) searchAdapter.this.post1.get(position)).getSearchName());
                        i.putExtra("School", ((searchPost) searchAdapter.this.post1.get(position)).getSearchSchool());
                        i.putExtra("UserName", ((searchPost) searchAdapter.this.post1.get(position)).getSearchUserName());
                        searchAdapter.this.context.startActivity(i);
                        return;
                    }
                    Toast.makeText(searchAdapter.this.context, "Cannot open other school's profiles", 1).show();
                }
            });
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.Name.setText(((searchPost) this.post1.get(position)).getSearchName());
        holder.UserName.setText(((searchPost) this.post1.get(position)).getSearchUserName());
        holder.AvgRate.setText(((searchPost) this.post1.get(position)).getSearchRating());
        this.imageLoader.DisplayImage(((searchPost) this.post1.get(position)).getSearchDp(), holder.picture);
        return view;
    }
}
