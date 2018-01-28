package hu.ait.studybuddy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hu.ait.studybuddy.MainActivity;
import hu.ait.studybuddy.R;
import hu.ait.studybuddy.data.Class;
import hu.ait.studybuddy.touch.ClassTouchHelperAdapter;

/**
 * Created by Jana on 7/1/2017.
 */

public class ClassRecyclerAdapter extends RecyclerView.Adapter<ClassRecyclerAdapter.ViewHolder>
        implements ClassTouchHelperAdapter {

    private List<Class> listClasses;
    private Context context;

    public ClassRecyclerAdapter(Context context) {
        this.context = context;
        listClasses = new ArrayList<Class>();


    }

    @Override
    public ClassRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View classRow = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.class_row, parent, false);
        return new ViewHolder(classRow);
    }

    @Override
    public void onBindViewHolder(final ClassRecyclerAdapter.ViewHolder holder, int position) {
        holder.tvClass.setText(listClasses.get(position).getClassName());
        holder.click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)context).showClassPosts((String) holder.tvClass.getText());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listClasses.size();
    }

    @Override
    public void onItemDismiss(final int position) {

        DatabaseReference refUser = FirebaseDatabase.getInstance().
                getReference("users/" + FirebaseAuth.getInstance().getCurrentUser().getUid());

        refUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapsot : dataSnapshot.getChildren()) {
                    if (snapsot.getValue().equals(listClasses.get(position).getClassName())) {
                        snapsot.getRef().removeValue();
                        listClasses.remove(position);
                        notifyDataSetChanged();
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listClasses, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listClasses, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
    }

    public void addClass(String className){
        Class newClass = new Class();
        newClass.setClassName(className);
        listClasses.add(newClass);
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvClass;
        private RelativeLayout click;

        public ViewHolder(View itemView) {
            super(itemView);
            tvClass = (TextView) itemView.findViewById(R.id.tvClass);
            click = (RelativeLayout) itemView.findViewById(R.id.relativeLayout);
        }
    }
}
