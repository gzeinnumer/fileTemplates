package ${PACKAGE_NAME};

import android.content.Context;
import android.net.ConnectivityManager;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ${NAME}RepoImpl implements ${NAME}Repo {

//    private final ${NAME}RepoImpl repo;
//    repo = new ${NAME}RepoImpl(application.getApplicationContext());
    
    private final Context context;
    private final CompositeDisposable compositeDisposable;
    private final ConnectivityManager cm;

    public ${NAME}RepoImpl(Context applicationContext) {
        this.context = applicationContext;
        this.compositeDisposable = new CompositeDisposable();
        this.cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    private boolean isConnect() {
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}