package hu.ait.studybuddy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import hu.ait.studybuddy.adapter.PostsAdapter;
import hu.ait.studybuddy.data.Post;

public class ClassPostsActivity extends AppCompatActivity {
    private RecyclerView recyclerViewPosts;
    private PostsAdapter postsAdapter;
    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_posts);

        className = getIntent().getStringExtra(MainActivity.CLASS_NAME);

        recyclerViewPosts = (RecyclerView) findViewById(R.id.recylerViewPosts);
        postsAdapter = new PostsAdapter(this, className);

        LinearLayoutManager postsLayoutManager = new LinearLayoutManager(this);
        postsLayoutManager.setReverseLayout(true);
        postsLayoutManager.setStackFromEnd(true);
        recyclerViewPosts.setLayoutManager(postsLayoutManager);

        recyclerViewPosts.setAdapter(postsAdapter);
        initPostsListener();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intenttCreatePost = new Intent();
                intenttCreatePost.setClass(ClassPostsActivity.this, CreatePostActivity.class);
                intenttCreatePost.putExtra(MainActivity.CLASS_NAME, className);
                startActivity(intenttCreatePost);
            }
        });



        // toolbar

        String toolbarTitle = className.toUpperCase();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTest);
        // setSupportActionBar(toolbar);
        toolbar.setTitle(toolbarTitle);

    }

    public void initPostsListener() {
        DatabaseReference postsRef = FirebaseDatabase.getInstance().getReference().child(className.toString());
        postsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Post newPost = dataSnapshot.getValue(Post.class);


                // Date
                Date curr = new Date(System.currentTimeMillis());
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date dateObj = null;
                try {
                    dateObj = dateFormat.parse(newPost.getDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (curr.before(dateObj)) {
                    postsAdapter.addPost(newPost);
                    recyclerViewPosts.smoothScrollToPosition(postsAdapter.getItemCount() -1);
                }


            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // toolbar
        getMenuInflater().inflate(R.menu.post_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_users:
                Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();

                break;
            case R.id.action_stop:
                Toast.makeText(this, "Stop", Toast.LENGTH_SHORT).show();
                break;
        }

        return true;
    }
}

