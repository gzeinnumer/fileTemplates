package ${PACKAGE_NAME};

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;


public class ${NAME} extends RecyclerView.Adapter<${NAME}.${VIEW_HOLDER_CLASS}> {
    
    private List<${ITEM_TYPE}> items = new ArrayList<>();
    private int itemLayout = ${LAYOUT};
    
    public ${NAME}(List<${ITEM_TYPE}> items) {
        this.items = items;
    }
    
    @Override
    public ${VIEW_HOLDER_CLASS} onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ${VIEW_HOLDER_CLASS}(LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false));
    }
    
    @Override
    public void onBindViewHolder(${NAME}.${VIEW_HOLDER_CLASS} holder, int position) {
         holder.bind(items.get(position));
    }
    
    public void setItems(List<${ITEM_TYPE}> items) {
        this.items = items;
        notifyDataSetChanged();
    }
    
    @Override
    public int getItemCount() {
        return items == null ? 0: items.size();
    }
    
    public class ${VIEW_HOLDER_CLASS} extends RecyclerView.ViewHolder {
        
        // TODO: add itemView component
    
        public ${VIEW_HOLDER_CLASS}(View itemView) {
            super(itemView);
            bindView();
        }
        
        private void bindView() {
            // TODO: bind itemLayout xml to itemView component
        }
        public void bind(${ITEM_TYPE} item) {
            // TODO: bind itemView component to it's data
        }
    }
}