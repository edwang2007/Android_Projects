package hu.ait.studybuddy.touch;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by Jana on 7/1/2017.
 */

public class ClassTouchHelperCallback extends ItemTouchHelper.Callback{
    private ClassTouchHelperAdapter classTouchHelperAdapter;

    public ClassTouchHelperCallback(ClassTouchHelperAdapter classTouchHelperAdapter) {
        this.classTouchHelperAdapter = classTouchHelperAdapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        classTouchHelperAdapter.onItemMove(viewHolder.getAdapterPosition(),
                target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        classTouchHelperAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }
}
