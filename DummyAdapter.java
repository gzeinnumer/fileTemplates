package ${PACKAGE_NAME};

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DummyAdapter extends RecyclerView.Adapter<DummyAdapter.MyHolder> {

    int layout;
    OnItemClickListener onItemClickListener;

    public DummyAdapter(int layout, OnItemClickListener onItemClickListener) {
        this.layout = layout;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        //holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), GblVariabel.anim));
        holder.itemView.setOnClickListener(view -> {
            onItemClickListener.onItemClick(position);
        });
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public MyHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
