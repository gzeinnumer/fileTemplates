package ${PACKAGE_NAME};

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class DummyAdapterV2 extends RecyclerView.Adapter<DummyAdapterV2.MyHolder> {

    private int layout;
    private int size = 20;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public DummyAdapterV2(int layout, int size, OnItemClickListener onItemClickListener) {
        this.layout = layout;
        this.size = size;
        this.onItemClickListener = onItemClickListener;
    }

    public DummyAdapterV2(int layout, OnItemClickListener onItemClickListener) {
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), GblVariabel.anim));
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });

        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) holder.cardView.getLayoutParams();

        prepareSpace(layoutParams, position, holder);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cv);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public int intToDp(int sizeInDPH){
        return  (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDPH, context.getResources().getDisplayMetrics());
    }

    private void prepareSpace(ViewGroup.MarginLayoutParams layoutParams, int position, MyHolder holder) {
        int topBottomRv = 10;
        int leftRightItem = 10;
        int spaceBetween = 10/2;
        if (position == 0) {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(topBottomRv), intToDp(leftRightItem), intToDp(spaceBetween));
        } else if (position == size-1){
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(spaceBetween), intToDp(leftRightItem), intToDp(topBottomRv));
        } else {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(spaceBetween), intToDp(leftRightItem), intToDp(spaceBetween));
        }
        holder.cardView.setLayoutParams(layoutParams);
    }
}