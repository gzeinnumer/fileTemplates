package ${PACKAGE_NAME};

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ${MODEL}PA extends PagedListAdapter<${MODEL}Response, ${MODEL}PA.MyHolder> {

    private Context context;
    public ${MODEL}PA() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyHolder(ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ${MODEL}Response current = getItem(position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), BaseConstant.RV_ANIM));
        holder.bind(current, onItemClickListener);

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.itemBinding.cv.getLayoutParams();
        prepareSpace(layoutParams, position, holder);
    }

    @SuppressLint("DiffUtilEquals")
    private static final DiffUtil.ItemCallback<${MODEL}Response> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<${MODEL}Response>() {
                @Override
                public boolean areItemsTheSame(${MODEL}Response oldMItem, ${MODEL}Response newMItem) {
                    return oldMItem.getId() == newMItem.getId();
                }

                @Override
                public boolean areContentsTheSame(${MODEL}Response oldMItem, @NonNull ${MODEL}Response newMItem) {
                    return oldMItem.equals(newMItem);
                }
            };

    public int intToDp(int sizeInDPH) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDPH, context.getResources().getDisplayMetrics());
    }

    private void prepareSpace(ViewGroup.MarginLayoutParams layoutParams, int position, MyHolder holder) {
        int topBottomRv = 10;
        int leftRightItem = 10;
        int spaceBetween = 10 / 2;
        if (position == 0) {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(topBottomRv), intToDp(leftRightItem), intToDp(spaceBetween));
        } else if (position == getItemCount() - 1) {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(spaceBetween), intToDp(leftRightItem), intToDp(topBottomRv));
        } else {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(spaceBetween), intToDp(leftRightItem), intToDp(spaceBetween));
        }
        holder.itemBinding.cv.setLayoutParams(layoutParams);
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public interface OnItemClickListener {
        void onItemClick(int position, ${MODEL}Response data);
    }
    
    public static class MyHolder extends RecyclerView.ViewHolder {
        public ItemBinding itemBinding;

        public MyHolder(@NonNull ItemBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        public void bind(${MODEL}Response data, OnItemClickListener onItemClickListener) {

            if (onItemClickListener != null) {

            }
        }
    }
}
