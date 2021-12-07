package ${PACKAGE_NAME};

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

public class ${MODEL}DSFactory extends DataSource.Factory {

    private final MutableLiveData<PageKeyedDataSource<Integer, ${MODEL}Response>> itemLiveDataSource;
    private final Context applicationContext;
    
    public ${MODEL}DSFactory(Context context) {
        this.applicationContext = context;
        itemLiveDataSource = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, ${MODEL}Response> create() {
        ${MODEL}DS notificationDS = new ${MODEL}DS(applicationContext);
        itemLiveDataSource.postValue(notificationDS);
        return notificationDS;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, ${MODEL}Response>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
