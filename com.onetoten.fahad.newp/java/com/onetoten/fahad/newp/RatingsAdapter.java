package com.onetoten.fahad.newp;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class RatingsAdapter extends BaseAdapter {
    private ArrayList<SingleItemRating> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    private List<SingleItemRating> post = null;

    public class ViewHolder {
        TextView CreatedAt;
        String ObjectId;
        ImageView Ppicture;
        TextView create;
        TextView name;
        ImageView picture;
        TextView rating;
    }

    public RatingsAdapter(Context context, List<SingleItemRating> post) {
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
            view = this.inflater.inflate(R.layout.ratings_singlepost, null);
            holder.rating = (TextView) view.findViewById(R.id.ratingsss);
            holder.name = (TextView) view.findViewById(R.id.rName);
            holder.CreatedAt = (TextView) view.findViewById(R.id.RcreatedAt);
            holder.picture = (ImageView) view.findViewById(R.id.Rdp);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.rating.setText(((SingleItemRating) this.post.get(position)).getRating());
        holder.name.setText(((SingleItemRating) this.post.get(position)).getName());
        holder.CreatedAt.setText(((SingleItemRating) this.post.get(position)).getCreatedAt().toString());
        this.imageLoader.DisplayImage(((SingleItemRating) this.post.get(position)).getpPicture(), holder.picture);
        holder.name.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                String a = ((SingleItemRating) RatingsAdapter.this.post.get(position)).getName();
                String c = "anonymous";
                if (a.equals("anon") || a.equals(c)) {
                    Toast.makeText(RatingsAdapter.this.context, "haha, nice try :p", 1).show();
                    return;
                }
                Intent i = new Intent(RatingsAdapter.this.context, OthersProfile.class);
                i.putExtra("CUobjectId", ((SingleItemRating) RatingsAdapter.this.post.get(position)).getCUobjectId());
                i.putExtra("Name", ((SingleItemRating) RatingsAdapter.this.post.get(position)).getName());
                i.putExtra("School", ((SingleItemRating) RatingsAdapter.this.post.get(position)).getSchool());
                i.putExtra("UserName", ((SingleItemRating) RatingsAdapter.this.post.get(position)).getUserName());
                RatingsAdapter.this.context.startActivity(i);
                ((Activity) RatingsAdapter.this.context).finish();
            }
        });
        view.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                ParseUser f = ParseUser.getCurrentUser();
                String g = f.get("Name").toString();
                String h = ((SingleItemRating) RatingsAdapter.this.post.get(position)).getName();
                String i = f.getUsername();
                String j = ((SingleItemRating) RatingsAdapter.this.post.get(position)).getUserName();
                if (g.equals(h) || i.equals(j)) {
                    new Builder(RatingsAdapter.this.context).setTitle("Delete entry").setMessage("Are you sure you want to delete this Rating?").setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            String p = ParseUser.getCurrentUser().getUsername();
                            String RId = ((SingleItemRating) RatingsAdapter.this.post.get(position)).getRatingId();
                            ParseQuery<ParseObject> query1 = ParseQuery.getQuery("RatingsAndNames");
                            query1.whereEqualTo("username", p);
                            query1.getInBackground(RId, new GetCallback<ParseObject>() {
                                public void done(ParseObject object, ParseException e) {
                                    if (object == null) {
                                        Log.d("score", "The  request failed." + e);
                                        return;
                                    }
                                    object.getObjectId();
                                    object.deleteInBackground();
                                    Toast.makeText(RatingsAdapter.this.context, "Rating deleted, refresh to see changes", 0).show();
                                }
                            });
                        }
                    }).setNegativeButton(17039369, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(17301543).show();
                } else {
                    Toast.makeText(RatingsAdapter.this.context, "cant delete someone elses rating", 0).show();
                }
                return true;
            }
        });
        return view;
    }
}
