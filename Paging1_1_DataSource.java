package ${PACKAGE_NAME};

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

public class ${MODEL}DS extends PageKeyedDataSource<Integer, ${MODEL}Response> {

    public static final int PAGE_SIZE = 10;
    private static final int FIRST_PAGE = 1;

    private final Context context;
    private final ApiService apiService;
    private final CompositeDisposable compositeDisposable;

    public ${MODEL}DS(Context applicationContext) {
        context = applicationContext;
        apiService = RetroServer.getInstance(applicationContext);
        compositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, ${MODEL}Response> callback) {
        compositeDisposable.add(
                apiService.get${MODEL}Pager(FIRST_PAGE, PAGE_SIZE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            BaseListResponse<${MODEL}Response> res = response.body();
                            if (res.getData() != null) {
                                callback.onResult(res.getData(), null, FIRST_PAGE + 1);
                            }
                        }, throwable -> {

                        })
        );
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ${MODEL}Response> callback) {
        compositeDisposable.add(
                apiService.get${MODEL}Pager(FIRST_PAGE, PAGE_SIZE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            BaseListResponse<${MODEL}Response> res = response.body();
                            Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                            if (res.getData() != null) {
                                callback.onResult(res.getData(), adjacentKey);
                            }
                        }, throwable -> {

                        })
        );
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, ${MODEL}Response> callback) {
        compositeDisposable.add(
                apiService.get${MODEL}Pager(FIRST_PAGE, PAGE_SIZE)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(response -> {
                            BaseListResponse<${MODEL}Response> res = response.body();
                            if (res.getData() != null) {
                                Integer key = params.key + 1;
                                callback.onResult(res.getData(), key);
                            }
                        }, throwable -> {

                        })
        );
    }
}
