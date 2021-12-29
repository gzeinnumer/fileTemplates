package ${PACKAGE_NAME};

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ${ClassName} extends RecyclerView.Adapter<${ClassName}.MyHolder> {

    private Context context;
    private List<${Model}> list;
    private List<${Model}> listFilter;
    private List<${ItemBinding}> holders;

    public ${ClassName}(List<${Model}> list) {
        this.list = list;
        this.listFilter = list;
        this.holders = new ArrayList<>(list.size());
        initHolders();
    }

    private BaseCallBack<${Model}> callBack;

    public void setCallBack(BaseCallBack<${Model}> callBack) {
        this.callBack = callBack;
    }

    private void initHolders() {
        for (int i = 0; i < list.size(); i++) {
            holders.add(null);
        }
    }

    public void setList(List<${Model}> list) {
        this.list = list;
        this.listFilter = list;
        this.holders = new ArrayList<>(list.size());
        initHolders();
        notifyDataSetChanged();
    }

    public List<${Model}> getList() {
        return list;
    }

    public List<${ItemBinding}> getHolders() {
        return holders;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new MyHolder(${ItemBinding}.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holders.set(position, ${ItemBinding}.bind(holder.itemBinding.getRoot()));
        holder.bind(list.get(position), callBack);
        prepareSpace(holder.itemBinding.cv, position);
    }

    public int intToDp(int sizeInDPH) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDPH, context.getResources().getDisplayMetrics());
    }

    private void prepareSpace(CardView cv, int position) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) cv.getLayoutParams();

        int topBottomRv = 10;
        int leftRightItem = 10;
        int spaceBetween = 10 / 2;
        if (position == 0) {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(topBottomRv), intToDp(leftRightItem), intToDp(spaceBetween));
        } else if (position == list.size() - 1) {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(spaceBetween), intToDp(leftRightItem), intToDp(topBottomRv));
        } else {
            layoutParams.setMargins(intToDp(leftRightItem), intToDp(spaceBetween), intToDp(leftRightItem), intToDp(spaceBetween));
        }
        cv.setLayoutParams(layoutParams);
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<${Model}> fildteredList = new ArrayList<>();
            if (constraint != null || constraint.length() != 0) {
                String filterPattern = constraint.toString().toLowerCase().trim();

                for (${Model} item : listFilter) {
                    if (item.toString().toLowerCase().contains(filterPattern)) {
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

    public Filter getFilter() {
        return filter;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ${ItemBinding} itemBinding;

        public MyHolder(@NonNull ${ItemBinding} itemView) {
            super(itemView.getRoot());
            itemBinding = itemView;
        }

        public void bind(${Model} data, BaseCallBack<${Model}> callBack) {
            if (callBack!=null){

            }
        }
    }
}