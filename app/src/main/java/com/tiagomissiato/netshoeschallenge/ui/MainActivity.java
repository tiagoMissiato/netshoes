package com.tiagomissiato.netshoeschallenge.ui;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tiagomissiato.netshoeschallenge.utils.EndlessRecyclerOnScrollListener;
import com.tiagomissiato.netshoeschallenge.adapter.GistAdapter;
import com.tiagomissiato.netshoeschallenge.R;
import com.tiagomissiato.netshoeschallenge.utils.SimpleDividerItemDecoration;
import com.tiagomissiato.netshoeschallenge.models.Gist;
import com.tiagomissiato.netshoeschallenge.models.GistFile;
import com.tiagomissiato.netshoeschallenge.models.GistItem;
import com.tiagomissiato.netshoeschallenge.rest.RestClient;
import com.tiagomissiato.netshoeschallenge.rest.callbacks.RestCallback;
import com.tiagomissiato.netshoeschallenge.rest.models.RestError;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.gist_list)
    public RecyclerView mGistList;
    @Bind(R.id.no_data)
    public LinearLayout noData;
    @Bind(R.id.swiperefresh)
    SwipeRefreshLayout swipeRefreshLayout;

    List<GistItem> mItems = new ArrayList<>();
    GistAdapter adapter;

    int mPage= 0;

    public RestCallback<List<Gist>> gistCallback = new RestCallback<List<Gist>>() {
        @Override
        public void failure(RestError restError) {
            swipeRefreshLayout.setRefreshing(false);
            try {
                Toast.makeText(MainActivity.this, restError.getStrMessage(), Toast.LENGTH_LONG).show();
            }catch (Exception ignored){}
            showNoData(View.VISIBLE, View.GONE);
        }

        @Override
        public void success(List<Gist> gists, Response response) {
            swipeRefreshLayout.setRefreshing(false);
            for(Gist g : gists) {
                for(GistFile f : g.files.gists) {
                    GistItem gItem = new GistItem();
                    gItem.gist = f;
                    gItem.owner = g.owner;
                    mItems.add(gItem);
                }
            }
            if(adapter == null) {
                adapter = new GistAdapter(MainActivity.this, mItems);
                mGistList.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }

            mPage++;
            showNoData(View.GONE, View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        //Recycler view configuration
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mGistList.setHasFixedSize(true);
        mGistList.setLayoutManager(linearLayoutManager);
        mGistList.addItemDecoration(new SimpleDividerItemDecoration(this));
        mGistList.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                getGistList(mPage);
            }
        });

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startGistList();
            }
        });
        getGistList(mPage);
    }

    private void startGistList() {
        mPage = 0;
        mItems = new ArrayList<>();
        adapter = null;

        getGistList(mPage);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.try_again)
    public void tryAgain(){
        startGistList();
    }

    private void showNoData(int visible, int gone) {
        noData.setVisibility(visible);
        mGistList.setVisibility(gone);
    }

    public void getGistList(int page){

        RestClient.getGist(this, page, gistCallback);

    }
}
