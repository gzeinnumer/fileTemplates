package ${PACKAGE_NAME};

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akiniyalocts.pagingrecycler.PagingAdapter;

import java.util.ArrayList;
import java.util.List;

public class ${CLASS}Pager extends PagingAdapter {
    private final List<${MODEL}> list = new ArrayList<>();
    private Context context;
    
    private BaseCallBackAdapter<${MODEL}> baseDebugCallback;

    public void setBaseDebugCallback(BaseCallBackAdapter<${MODEL}> baseDebugCallback) {
        this.baseDebugCallback = baseDebugCallback;
    }

    public ${CLASS}Pager() {}

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        return new MyHolder(${BINDING}.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setAnimation(AnimationUtils.loadAnimation(holder.itemView.getContext(), BaseConstant.RV_ANIM));

        MyHolder myHolder = (MyHolder) holder;
        myHolder.bind(list.get(position), position, callBack);
        prepareSpace(myHolder.itemBinding.cv, position);
        
        if (baseDebugCallback!=null){
            if (isDebugActive()){
                myHolder.itemBinding.getRoot().setOnLongClickListener(v -> {
                    baseDebugCallback.onClick(1, position, list.get(position));
                    return false;
                });
            }
        }
    }

    @Override
    public int getPagingLayout() {
        return R.layout.${LAYOUTS};
    }

    @Override
    public int getPagingItemCount() {
        return list.size();
    }

    public void addNewItem(List<${MODEL}> data) {
        int lastSize = list.size();
        for (${MODEL} d: data){
            if (!list.contains(d))
                list.add(d);
        }
        notifyItemInserted(lastSize);
    }

    public int intToDp(int sizeInDPH) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sizeInDPH, context.getResources().getDisplayMetrics());
    }

    private void prepareSpace(CardView parent, int position) {
        ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) parent.getLayoutParams();

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
        parent.setLayoutParams(layoutParams);
    }

    private BaseCallBack<${MODEL}> callBack;

    public void setCallBack(BaseCallBack<${MODEL}> callBack) {
        this.callBack = callBack;
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        public ${BINDING} itemBinding;

        public MyHolder(@NonNull ${BINDING} itemView) {
            super(itemView.getRoot());
            this.itemBinding = itemView;
        }

        public void bind(${MODEL} data, int position, BaseCallBack<${MODEL}> callBack) {
        
            if (callBack != null) {
                itemBinding.getRoot().setOnClickListener(v -> {
                    callBack.onClick(1, position, data);
                });
            }
        }
    }
}