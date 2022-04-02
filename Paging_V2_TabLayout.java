package ${PACKAGE_NAME};

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Toast;

import com.akiniyalocts.pagingrecycler.PagingDelegate;

import java.util.ArrayList;
import java.util.List;

public class ${CLASSNAME}extends BaseActivity {

    private ${BINDING} binding;
    private ${CLASSNAME}VM vm;
    private PagingParams params = new PagingParams();
    private List<${MODEL}> list${MODEL} = new ArrayList<>();
    private String start_date = "";
    private String end_date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ${BINDING}.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        vm = new ViewModelProvider(this).get(${CLASSNAME}VM.class);

        initView();
        initTextWatcher();
        initObserver();
        initOnClick();
    }

    private void initView() {
        initPaging();
        initViewPager();
    }

    private void initTextWatcher() {

    }

    private void initObserver() {
        vm.get().observe(this, resource -> {
            switch (resource.status) {
                case STATUS_1_SUCCESS:
                    swipe(binding.swipeRefreshLayout, false);
                    list${MODEL} = resource.data;
                    emptyState(list${MODEL} != null ? list${MODEL}.size() : 0, binding.rv, binding.imgEmpty);
                    params.setTotalData(resource.total);
                    adapter.addNewItem(resource.data);
                    break;
                case STATUS_2_ERROR:
                    swipe(binding.swipeRefreshLayout, false);
                    emptyState(list${MODEL} != null ? list${MODEL}.size() : 0, binding.rv, binding.imgEmpty);
                    break;
                case STATUS_6_LOADING:
                    swipe(binding.swipeRefreshLayout, true);
                    emptyState(list${MODEL} != null ? list${MODEL}.size() : 0, binding.rv, binding.imgEmpty);
                    break;
            }
        });
    }

    private void initOnClick() {
        debugLocationActivity(binding.tvToolbar, getClass().getSimpleName());
        binding.btnBack.setOnClickListener(view -> {
            onBackPressed();
        });
        binding.btnPopup.setOnClickListener(view -> showFilterDialog());
        binding.swipeRefreshLayout.setOnRefreshListener(this::initPaging);

        binding.imgClearFilter.setOnClickListener(v -> {
            start_date = "";
            end_date = "";
            binding.tvFilterText.setText("");
            binding.llFilterText.setVisibility(View.GONE);
            initPaging();
        });
    }

    protected void initPaging() {
        params = new PagingParams();
        init${MODEL}Pager();
        vm.set(1,start_date,end_date);
    }

    private ${MODEL}Pager adapter;

    private void init${MODEL}Pager() {
        list${MODEL} = new ArrayList<>();
        adapter = new ${MODEL}Pager();
        adapter.setCallBack((type, position, data) -> {
            switch (type) {
                case 1:
                    Toast.makeText(getApplicationContext(), "Intent to detail "+data.getName(), Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    break;
            }
        });

        adapter.setBaseDebugCallback((type, position, data) -> {
            debugDialog(data.toString());
        });
        binding.rv.setAdapter(adapter);
        binding.rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        binding.rv.hasFixedSize();

        new PagingDelegate.Builder(adapter)
                .attachTo(binding.rv)
                .listenWith(new PagingDelegate.OnPageListener() {
                    @Override
                    public void onPage(int i) {
                        if (params.getCurrentPage() < params.getTotalPage()) {
                            params.addCurrentPage();
                            vm.set(params.getCurrentPage(), start_date,end_date);
                        } else {
                            onDonePaging();
                        }
                    }

                    @Override
                    public void onDonePaging() {

                    }
                })
                .build();
    }


    private void showFilterDialog() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment previous = getSupportFragmentManager().findFragmentByTag(${MODEL}FilterDialog.TAG);
        if (previous != null) {
            transaction.remove(previous);
        }
        ${MODEL}FilterDialog dialog = ${MODEL}FilterDialog.newInstance(start_date, end_date, (firstDate, lastDate) -> {
            this.start_date = firstDate;
            this.end_date = lastDate;

            if (start_date.length()>0 || end_date.length()>0){
                binding.tvFilterText.setText(start_date+" sd "+end_date);
                binding.llFilterText.setVisibility(View.VISIBLE);
            } else {
                binding.tvFilterText.setText("");
                binding.llFilterText.setVisibility(View.GONE);
            }
            initPaging();
        });
        dialog.show(transaction, ${MODEL}FilterDialog.TAG);
    }

    private void initViewPager() {
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                List<TabLayout.Tab> tabItems = new ArrayList<>();
                tabItems.add(binding.tabLayout.getTabAt(0));
                tabItems.add(binding.tabLayout.getTabAt(1));
                for (int i = 0; i < tabItems.size(); i++) {
                    tabItems.get(i).view.setEnabled(false);
                }

                switch (tab.getPosition()) {
                    case 0:
                        start_date = "";
                        end_date = "";
                        initPaging();
                        break;
                    case 1:
                        start_date = "2022-05-01";
                        end_date = "2022-05-31";
                        initPaging();
                        break;
                }

                new Handler().postDelayed(() -> {
                    for (int i = 0; i < tabItems.size(); i++) {
                        tabItems.get(i).view.setEnabled(true);
                    }
                }, 1500);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}