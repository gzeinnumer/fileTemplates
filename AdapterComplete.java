package ${PACKAGE_NAME};

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ${NAME}Adapter extends RecyclerView.Adapter<${NAME}Adapter.MyHolder> implements Filterable {
    private final List<ItemBinding> holders;
    private final Context context;
    private List<${ITEM_TYPE}> list;
    private List<${ITEM_TYPE}> listFilter;
    private OnItemClickListener onItemClickListener;

    public ${NAME}Adapter(Context context, List<${ITEM_TYPE}> list) {
        this.context = context;
        this.list = new ArrayList<>(list);
        this.listFilter = new ArrayList<>(list);
        this.holders = new ArrayList<>(list.size());
        initHolders();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private final Filter exampleFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<${ITEM_TYPE}> fildteredList = new ArrayList<>();
            if (constraint != null || constraint.length() != 0) {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (${ITEM_TYPE} item : listFilter) {
                    if (item.getName().toLowerCase().contains(filterPattern)) {
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

    public void setList(List<${ITEM_TYPE}> list) {
        this.list = new ArrayList<>(list);
        this.listFilter = new ArrayList<>(list);
        initHolders();
        notifyDataSetChanged();
    }

    private void initHolders() {
        for (int i = 0; i < list.size(); i++) {
            holders.add(null);
        }
    }

    public List<ItemBinding> getHolders() {
        return holders;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyHolder(ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holders.set(position, ItemBinding.bind(holder.itemBinding.getRoot()));
        holder.bind(list.get(position), onItemClickListener);
        initKeepValue(holder, position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return exampleFilter;
    }
    
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    private void initKeepValue(MyHolder holder, int position) {
        //holder.itemBinding.ed.addTextChangedListener(new SimpleTextWatcher(s -> list.get(position).setName(s.toString())));

        if (list.get(position).getName() != null && list.get(position).getName().length() > 0) {
            holder.itemBinding.ed.setText(list.get(position).getName());
        } else {
            holder.itemBinding.ed.setText("");
        }
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ItemProductParetoReasonBinding itemBinding;

        public MyHolder(@NonNull ItemProductParetoReasonBinding itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        public void bind(${ITEM_TYPE} data, OnItemClickListener onItemClickListener) {
            if(onItemClickListener!=null){
                //performClick
            }
        }
    }
}