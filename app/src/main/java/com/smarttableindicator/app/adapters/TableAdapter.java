package com.smarttableindicator.app.adapters;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.smarttableindicator.app.R;
import com.smarttableindicator.app.config.Constants;
import com.smarttableindicator.app.models.TableModel;

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.TableViewHolder> {

    private static final String TAG = Constants.TAG_TABLE_ADAPTER;
    private final List<TableModel> tableList;
    private final Context context;

    public TableAdapter(List<TableModel> tableList, Context context) {
        this.tableList = tableList;
        this.context = context;
    }

    @NonNull
    @Override
    public TableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_table, parent, false);
        return new TableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TableViewHolder holder, int position) {
        TableModel table = tableList.get(position);
        holder.textViewTableNumber.setText(
                context.getString(R.string.table_number, table.getTableNumber())
        );

        // Cancel any previous animation to prevent memory leaks
        if (holder.colorAnimator != null) {
            holder.colorAnimator.cancel();
            holder.colorAnimator = null;
        }

        if (table.getStatus() == TableModel.Status.AVAILABLE) {
            holder.imageViewStatus.setColorFilter(ContextCompat.getColor(context, R.color.table_available));
        } else {
            int colorOn, colorOff;
            if (table.getStatus() == TableModel.Status.OCCUPIED) {
                colorOn = ContextCompat.getColor(context, R.color.table_occupied_on);
                colorOff = ContextCompat.getColor(context, R.color.table_occupied_off);
            } else { // DIRTY
                colorOn = ContextCompat.getColor(context, R.color.table_dirty_on);
                colorOff = ContextCompat.getColor(context, R.color.table_dirty_off);
            }

            // Animate between colorOff and colorOn
            ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
            animator.setDuration(Constants.ANIMATION_DURATION_MS); // ms, adjust for faster/slower pulse
            animator.setRepeatMode(ValueAnimator.REVERSE);
            animator.setRepeatCount(ValueAnimator.INFINITE);
            animator.addUpdateListener(animation -> {
                float fraction = (float) animation.getAnimatedValue();
                int animatedColor = blendColors(colorOff, colorOn, fraction);
                holder.imageViewStatus.setColorFilter(animatedColor);
            });
            animator.start();
            holder.colorAnimator = animator;
        }
    }

    @Override
    public void onViewRecycled(@NonNull TableViewHolder holder) {
        if (holder.colorAnimator != null) {
            holder.colorAnimator.cancel();
            holder.colorAnimator = null;
        }
        super.onViewRecycled(holder);
    }

    @Override
    public int getItemCount() {
        return tableList.size();
    }

    // Helper to blend two colors
    private int blendColors(int colorFrom, int colorTo, float ratio) {
        final float inverseRatio = 1f - ratio;
        float r = Color.red(colorFrom) * inverseRatio + Color.red(colorTo) * ratio;
        float g = Color.green(colorFrom) * inverseRatio + Color.green(colorTo) * ratio;
        float b = Color.blue(colorFrom) * inverseRatio + Color.blue(colorTo) * ratio;
        return Color.rgb((int) r, (int) g, (int) b);
    }

    public static class TableViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewStatus;
        TextView textViewTableNumber;
        ValueAnimator colorAnimator; // Add this to keep track of the animator

        public TableViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewStatus = itemView.findViewById(R.id.imageViewStatus);
            textViewTableNumber = itemView.findViewById(R.id.textViewTableNumber);
        }
    }
}