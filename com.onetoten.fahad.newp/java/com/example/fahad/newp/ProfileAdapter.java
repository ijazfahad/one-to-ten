package com.example.fahad.newp;

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
import com.onetoten.fahad.newp.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;

public class ProfileAdapter extends BaseAdapter {
    String Name;
    private ArrayList<ProfilePost> array;
    Context context;
    ImageLoader imageLoader;
    LayoutInflater inflater;
    List<ParseObject> ob;
    private List<ProfilePost> post1 = null;

    public class ViewHolder {
        TextView AvgRate;
        TextView Caption;
        TextView CreatedAt;
        TextView create;
        ImageView picture;
    }

    public ProfileAdapter(Context context, List<ProfilePost> post) {
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
            view = this.inflater.inflate(R.layout.pop_up_menu, null);
            holder.AvgRate = (TextView) view.findViewById(R.id.Pcaption);
            holder.Caption = (TextView) view.findViewById(R.id.PLrating);
            holder.CreatedAt = (TextView) view.findViewById(R.id.Rdp);
            holder.picture = (ImageView) view.findViewById(R.id.button7);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.CreatedAt.setText(((ProfilePost) this.post1.get(position)).getpDate().toString());
        holder.Caption.setText(((ProfilePost) this.post1.get(position)).getPcaption());
        holder.AvgRate.setText(((ProfilePost) this.post1.get(position)).getpAverageRating());
        this.imageLoader.DisplayImage(((ProfilePost) this.post1.get(position)).getpPicture(), holder.picture);
        holder.picture.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(ProfileAdapter.this.context, SingleItemView.class);
                intent.putExtra("ObjectId", ((ProfilePost) ProfileAdapter.this.post1.get(position)).getpObjectId());
                intent.putExtra("Name", ((ProfilePost) ProfileAdapter.this.post1.get(position)).getName());
                intent.putExtra("Caption", ((ProfilePost) ProfileAdapter.this.post1.get(position)).getPcaption());
                intent.putExtra("picture", ((ProfilePost) ProfileAdapter.this.post1.get(position)).getpPicture());
                intent.putExtra("date", ((ProfilePost) ProfileAdapter.this.post1.get(position)).getpDate().toString());
                intent.putExtra("dp", ((ProfilePost) ProfileAdapter.this.post1.get(position)).getDp());
                ProfileAdapter.this.context.startActivity(intent);
            }
        });
        holder.picture.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                String s = "Delete Picture?";
                new Builder(ProfileAdapter.this.context).setTitle("Delete entry").setMessage("Are you sure you want to delete this picture...CHICKEN? :p").setPositiveButton(17039379, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String p = ParseUser.getCurrentUser().getUsername();
                        String picId = ((ProfilePost) ProfileAdapter.this.post1.get(position)).getPicObjectId();
                        ParseQuery<ParseObject> query1 = ParseQuery.getQuery("TexasState");
                        query1.whereEqualTo("username", p);
                        query1.getInBackground(picId, new GetCallback<ParseObject>() {
                            public void done(ParseObject object, ParseException e) {
                                if (object == null) {
                                    Log.d("score", "The getFirst request failed." + e);
                                    return;
                                }
                                object.getObjectId();
                                object.deleteInBackground();
                                Toast.makeText(ProfileAdapter.this.context, "Picture deleted, refresh to see changes", 0).show();
                            }
                        });
                        ParseQuery<ParseObject> query2 = ParseQuery.getQuery("RatingsAndNames");
                        query2.whereEqualTo("rate", picId);
                        try {
                            ProfileAdapter.this.ob = query2.find();
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (ParseObject rating : ProfileAdapter.this.ob) {
                            rating.deleteInBackground();
                        }
                        ProfileAdapter.this.context.startActivity(new Intent(ProfileAdapter.this.context, Profile.class));
                    }
                }).setNegativeButton(17039369, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }).setIcon(17301543).show();
                return true;
            }
        });
        return view;
    }
}
