package hu.ait.studybuddy;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.studybuddy.data.Post;

public class CreatePostActivity extends AppCompatActivity {
    @BindView(R.id.etTitle)
    EditText etTitle;
    @BindView(R.id.etBody)
    EditText etBody;
    @BindView(R.id.etDate)
    EditText etDate;

    private String className;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);
        className = getIntent().getStringExtra(MainActivity.CLASS_NAME);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.btnSend)
    public void sendClick() {
        String key = FirebaseDatabase.getInstance().
                getReference().child(className).push().getKey();

        Post post = new Post(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                etTitle.getText().toString(),
                etBody.getText().toString(),
                etDate.getText().toString(),
                new ArrayList<String>()
        );

        post.getListPeople().add(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());

        FirebaseDatabase.getInstance().getReference().
                child(className).child(key).setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CreatePostActivity.this, "Post created", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(CreatePostActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        finish();

    }


}
