package com.impllife.split.ui.custom;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import com.impllife.split.R;
import com.impllife.split.ui.custom.adapter.AltRecyclerViewListAdapter;

abstract public class SwipeToDeleteCallback extends ItemTouchHelper.Callback {
    private final Paint clearPaint;
    private final ColorDrawable background;
    private final Drawable deleteDrawable;
    private final int backgroundColor;
    private final int intrinsicWidth;
    private final int intrinsicHeight;

    public SwipeToDeleteCallback(Context context) {
        this.clearPaint = new Paint();
        this.clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        this.background = new ColorDrawable();
        this.deleteDrawable = ContextCompat.getDrawable(context, R.drawable.ic_svg_delete);
        this.backgroundColor = Color.parseColor("#b80f0a");
        this.intrinsicWidth = deleteDrawable.getIntrinsicWidth();
        this.intrinsicHeight = deleteDrawable.getIntrinsicHeight();
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (viewHolder instanceof AltRecyclerViewListAdapter.Holder) {
            int viewId = ((AltRecyclerViewListAdapter.Holder) viewHolder).getViewId();
            if (viewId == R.layout.view_notify_list_item_date) {
                return makeMovementFlags(0, 0);
            }
        }
        return makeMovementFlags(0, ItemTouchHelper.LEFT);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View view = viewHolder.itemView;
        int itemHeight = view.getHeight();

        boolean isCancelled = dX == 0 && !isCurrentlyActive;

        if (isCancelled) {
            clearCanvas(c, view.getRight() + dX, (float) view.getTop(), (float) view.getRight(), (float) view.getBottom());
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }

        background.setColor(backgroundColor);
        background.setBounds(view.getRight() + (int) dX, view.getTop(), view.getRight(), view.getBottom());
        background.draw(c);

        int deleteIconTop = view.getTop() + (itemHeight - intrinsicHeight) / 2;
        int deleteIconMargin = (itemHeight - intrinsicHeight) / 2;
        int deleteIconLeft = view.getRight() - deleteIconMargin - intrinsicWidth;
        int deleteIconRight = view.getRight() - deleteIconMargin;
        int deleteIconBottom = deleteIconTop + intrinsicHeight;

        deleteDrawable.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom);
        deleteDrawable.draw(c);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    private void clearCanvas(Canvas c, Float left, Float top, Float right, Float bottom) {
        c.drawRect(left, top, right, bottom, clearPaint);
    }

    @Override
    public float getSwipeThreshold(RecyclerView.ViewHolder viewHolder) {
        return 0.65f;
    }
}
