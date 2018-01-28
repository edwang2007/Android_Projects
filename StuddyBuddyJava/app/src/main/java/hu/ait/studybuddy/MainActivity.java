package hu.ait.studybuddy;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import hu.ait.studybuddy.adapter.ClassRecyclerAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import hu.ait.studybuddy.touch.ClassTouchHelperCallback;

public class MainActivity extends AppCompatActivity {
    public static final String CLASS_NAME = "CLASS_NAME";
    private ClassRecyclerAdapter classRecyclerAdapter;
    RecyclerView recyclerClass;
    Button btnAdd;
    EditText etSearch;
    private BottomNavigationView navigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    //Classes
                    recyclerClass.setVisibility(View.VISIBLE);
                    return true;

                case R.id.navigation_search:

                    showNewClassDialog();

                    return true;

                case R.id.navigation_logout:

                    showSignOut();
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        recyclerClass = (RecyclerView) findViewById(R.id.recylerView);

        // recycler
        recyclerClass.setHasFixedSize(true);
        final LinearLayoutManager layoutManager =
                new LinearLayoutManager(MainActivity.this);
        recyclerClass.setLayoutManager(layoutManager);
        classRecyclerAdapter = new ClassRecyclerAdapter(MainActivity.this);
        recyclerClass.setAdapter(classRecyclerAdapter);

        ItemTouchHelper.Callback callback = new ClassTouchHelperCallback(classRecyclerAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerClass);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initClassListener();
    }

    public void initClassListener(){
        DatabaseReference classesRef = FirebaseDatabase.getInstance().
                getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        classesRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String newClassName = dataSnapshot.getValue(String.class);
                classRecyclerAdapter.addClass(newClassName);
                recyclerClass.smoothScrollToPosition(classRecyclerAdapter.getItemCount() - 1);
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

    private void showSignOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to sign out?");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

            }
        });

        builder.show();

    }

    private void showNewClassDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter class here");

        final EditText etClass = new EditText(this);
        builder.setView(etClass);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String key = FirebaseDatabase.getInstance().
                        getReference().
                        child("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        push().getKey();

                FirebaseDatabase.getInstance().
                        getReference().
                        child("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid()).
                        child(key).setValue(etClass.getText().toString());
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void showClassPosts(String className){
        Intent intenttClassPosts = new Intent();
        intenttClassPosts.setClass(MainActivity.this, ClassPostsActivity.class);
        intenttClassPosts.putExtra(CLASS_NAME, className);
        startActivity(intenttClassPosts);
        return;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
