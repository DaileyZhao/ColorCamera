package com.zcm.camera.net;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.zcm.camera.R;
import com.zcm.library.net.CustomObserver;
import com.zcm.library.net.NetManager;
import com.zcm.ui.basearch.BaseActivity;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedListActivity extends BaseActivity {
    @BindView(R.id.feed_refresh)
    SwipeRefreshLayout feedRefresh;
    @BindView(R.id.feed_list)
    RecyclerView feedList;
    FeedAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        adapter=new FeedAdapter(mThisActivity);
        feedList.setAdapter(adapter);
        feedList.setLayoutManager(new LinearLayoutManager(mThisActivity));
        feedList.setItemAnimator(new DefaultItemAnimator());
        reFreshData();
        feedRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                reFreshData();
            }
        });
    }
    void reFreshData(){
        NetManager.getInstance().getRetrofit().create(NetApi.class)
                .getTravel("86a28560b4dd8234233b390691884b36",20, new Random().nextInt(10))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CustomObserver<List<FeedModel.FeedItem>>(mThisActivity) {
                    @Override
                    public void onSuccess(List<FeedModel.FeedItem> result) {
                        adapter.setFeedList(result);
                        feedRefresh.setRefreshing(false);
                    }

                    @Override
                    public void onFailure(Throwable e, String errorMsg) {
                        Toast.makeText(mThisActivity,errorMsg,Toast.LENGTH_SHORT).show();
                        feedRefresh.setRefreshing(false);
                    }
                });
    }
}
