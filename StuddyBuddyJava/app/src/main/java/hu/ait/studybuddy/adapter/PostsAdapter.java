package hu.ait.studybuddy.adapter;

import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hu.ait.studybuddy.ClassPostsActivity;
import hu.ait.studybuddy.CreatePostActivity;
import hu.ait.studybuddy.MainActivity;
import hu.ait.studybuddy.data.Post;
import hu.ait.studybuddy.R;

/**
 * Created by Jana on 7/2/2017.
 */

public class PostsAdapter extends RecyclerView.Adapter<PostsAdapter.ViewHolder> {
    private List<Post> postList;
    private Context context;
    private String className;
    private boolean clicked = false;

    public PostsAdapter(Context context, String className) {
        this.context = context;
        postList = new ArrayList<Post>();
        this.className = className;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View viewRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.post_row,
                parent,
                false);

        return new ViewHolder(viewRow);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.tvAuthor.setText(postList.get(position).getAuthor());
        holder.tvTitle.setText(postList.get(position).getTitle());
        holder.tvBody.setText(postList.get(position).getBody());
        holder.tvDate.setText(postList.get(position).getDate());

        String currentUserID = postList.get(position).getAuthor();
        String testID = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        if (currentUserID.equals(FirebaseAuth.getInstance().getCurrentUser().getDisplayName())) {
            disableButton(holder);
        }
        holder.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // allow user to join that group
                    DatabaseReference refPost = FirebaseDatabase.getInstance().getReference().child(className);
                    refPost.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (DataSnapshot snapsot : dataSnapshot.getChildren()) {

                                if (snapsot.child("title").getValue().equals(postList.get(position).getTitle())) {

                                    snapsot.child("listPeople").child(postList.get(position).getListPeople().size() + "").getRef().setValue(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                    postList.get(position).getListPeople().add(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
                                    disableButton(holder);
                                    notifyDataSetChanged();
                                    break;

                                }
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date dateObj = null;
                    try {
                        dateObj = dateFormat.parse(postList.get(position).getDate());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar cal = Calendar.getInstance();
                    long startTime = dateObj.getTime();
                    long endTime = startTime + 2 * 60 * 60 * 1000;
                    Intent intent = new Intent(Intent.ACTION_EDIT);
                    intent.setType("vnd.android.cursor.item/event");

                    intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime);
                    intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                    intent.putExtra(CalendarContract.Events.TITLE, postList.get(position).getTitle());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, postList.get(position).getBody());
                    ((ClassPostsActivity) context).startActivity(intent);
                }
        });


        if (postList.get(position).getListPeople().size() > 0) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, postList.get(position).getListPeople());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(adapter);
        }
    }

    private void disableButton(ViewHolder holder) {
        holder.btnJoin.setVisibility(View.INVISIBLE);
        holder.btnJoin.setClickable(false);
        clicked = true;
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }

    public void addPost(Post post) {
        postList.add(post);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvTitle;
        public TextView tvBody;
        public TextView tvDate;
        public ImageView btnJoin;
        public Spinner spinner;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = (TextView) itemView.findViewById(R.id.tvAuthor);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            btnJoin = (ImageView) itemView.findViewById(R.id.btnJoin);
            spinner = (Spinner) itemView.findViewById(R.id.spinner);
        }
    }
}
