package niad.kr.example50.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import niad.kr.example50.R;
import niad.kr.example50.adapter.EndlessLoaderFriendAdapter;
import niad.kr.example50.adapter.OnLoadMoreListener;
import niad.kr.example50.data.Friend;
import niad.kr.example50.manager.FriendManager;

public class RecyclerViewWithEndlessLoaderActivity extends AppCompatActivity implements FriendCallback {

    private RecyclerView recyclerView;

    private EndlessLoaderFriendAdapter adapter;

    private SwipeRefreshLayout swipeRefreshLayout;

    private FriendManager friendManager;
    // error 메시지 안내용 toast 객체
    private Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_with_endless_loader);

        setupToolbar();
        setupWindowAnimations();

        friendManager = new FriendManager(this, this);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);



        adapter = new EndlessLoaderFriendAdapter(this, R.layout.item_friend, new ArrayList<Friend>(), recyclerView);
        recyclerView.setAdapter(adapter);

        // recyclerView 조회하다가 한계치에 다다르면 재조회
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!adapter.isLoading() && totalItemCount <= (lastVisibleItem + EndlessLoaderFriendAdapter.VISIBLE_THRESHOLD)) {
                    if (adapter.getOnLoadMoreListener() != null) {
                        adapter.setLoading(true);
                        adapter.getOnLoadMoreListener().onLoadMore();
                    }
                }
            }
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 최근 id 이후 데이터를 조회
                friendManager.findFriends(adapter.getItemId(0) > 0 ? adapter.getItemId(0): null, true);

            }
        });


        this.adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                // 현재 화면에 존재하는 마지막 아이디 기준으로 이전데이터 조회
                friendManager.findFriends(adapter.getItemId(adapter.getItemCount() - 1), false);

                // progressbar 표시
                adapter.addProgressItem();
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
            }
        });


        friendManager.findFriends();
    }





    // HomeAsUp touch event handler
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }




    // callback
    @Override
    public void addFriendsFirst(List<Friend> friends) {
        this.adapter.addFriendsFirst(friends);
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void addFriendsLast(List<Friend> friends) {
        this.adapter.removeProgressItem();
        this.adapter.addFriendsLast(friends);
    }

    @Override
    public void onErrorSwipeRefreshing(String errorMessage) {
        showToast(errorMessage);
        this.swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onErrorLoadMore(String errorMessage) {
        showToast(errorMessage);
        this.adapter.removeProgressItem();
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupWindowAnimations() {
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.RIGHT);
        slideTransition.setDuration(300);

        getWindow().setEnterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);


//        getWindow().setReenterTransition(slideTransition);
//        getWindow().setReturnTransition(slideTransition);

    }



    private void showToast(String errorMessage) {
        if (toast == null) {
            toast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        }
        toast.cancel();

        toast.setText(errorMessage);
        toast.show();
    }

}
