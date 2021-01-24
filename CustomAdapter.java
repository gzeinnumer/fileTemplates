package ${PACKAGE_NAME};

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gzeinnumer.myapplication.databinding.ItemRvBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ${NAME} extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {

    private List<${ITEM_TYPE}> list;
    private List<${ITEM_TYPE}> listFilter;
    private OnItemClickListener onItemClickListener;

    private int emptyLayout = -1;

    public ${NAME}() {
        this.list = new ArrayList<>();
        this.listFilter = new ArrayList<>(list);
    }

    protected void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setList(List<${ITEM_TYPE}> list) {
        this.list = list;
        this.listFilter = new ArrayList<>(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_EMPTY) {
            return new ViewHolderEmpty(LayoutInflater.from(parent.getContext()).inflate(emptyLayout == -1 ? R.layout.empty_item : emptyLayout, parent, false));
        } else {
            return new MyHolder(${BINDING}.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), GblVariabel.anim));
        if (list.size() > 0) {
            ((MyHolder)holder).bind(position, list.get(position), onItemClickListener);
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ${BINDING} binding;

        public MyHolder(@NonNull ${BINDING} itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(int position, String data, OnItemClickListener onItemClickListener) {

            itemView.setOnClickListener(view -> {
                onItemClickListener.onItemClick(position);
            });
        }
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    
    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<${ITEM_TYPE}> fildteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                Collections.sort(listFilter, new Comparator<${ITEM_TYPE}>() {
                    @Override
                    public int compare(${ITEM_TYPE} o1, ${ITEM_TYPE} o2) {
//                        return o1.getStrTv2().toLowerCase().compareTo(o2.getStrTv2().toLowerCase());
                        return o1.toLowerCase().compareTo(o2.toLowerCase());
                    }
                });
                fildteredList.addAll(listFilter);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (${ITEM_TYPE} item : listFilter) {
                    if (item.toLowerCase().contains(filterPattern)) {
                        fildteredList.add(item);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = fildteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public static class ViewHolderEmpty extends RecyclerView.ViewHolder {
        public ViewHolderEmpty(@NonNull View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemCount() {
        return list.size() > 0 ? list.size() : 1;
    }
    
    private static int TYPE_NORMAL = 1;
    private static int TYPE_EMPTY = 0;

    @Override
    public int getItemViewType(int position) {
        if (list.size()>0){
            return TYPE_NORMAL;
        } else {
            return TYPE_EMPTY;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
