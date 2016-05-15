package niad.kr.example50.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.toolbox.ImageLoader;

import java.util.List;

import niad.kr.example50.R;
import niad.kr.example50.data.Friend;
import niad.kr.example50.util.RequestQueueHolder;


public class SharedElementsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final static String TAG = SharedElementsAdapter.class.getSimpleName();

    private final int VIEW_PROGRESS = 0;
    private final int VIEW_ITEM = 1;


    private Activity activity;
    private LayoutInflater inflater;
    private int layoutId;
    private List<Friend> items;

    private int visibleThreshold = 2;
    private int lastVisibleItem;
    private int totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private ImageLoader imageLoader;

    public SharedElementsAdapter(Activity activity, int layoutId, List<Friend> items, RecyclerView recyclerView) {

        this.activity = activity;
        this.inflater = LayoutInflater.from(activity);
        this.layoutId = layoutId;
        this.items = items;

        this.imageLoader = RequestQueueHolder.getImageLoader();

        // 이 코드의 위치가 adapter에 있는 것이 맞는가?
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            // recyclerView 조회하다가 한계치에 다다르면 재조회
            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);

                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                    if (!loading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                        if (onLoadMoreListener != null) {
                            loading = true;
                            onLoadMoreListener.onLoadMore();
                        }
                    }
                }
            });

        }
        else {
            // 다른 LayoutManager의 경우 처리는?
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = this.inflater.inflate(this.layoutId, parent, false);
            return new SharedElementsFriendViewHolder(activity, view);
        }
        else {
            View view = this.inflater.inflate(R.layout.item_progress_bar, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Friend friend = this.items.get(position);

        if (holder instanceof SharedElementsFriendViewHolder) {

            SharedElementsFriendViewHolder friendHolder = (SharedElementsFriendViewHolder) holder;

            friendHolder.setItem(friend);
            friendHolder.getName().setText(friend.getName());
            friendHolder.getPhoneNo().setText(friend.getPhoneNo());

            if (friend.getProfileImgUrl() != null && friend.getProfileImgUrl().trim().length() > 0) {
                friendHolder.getProfile().setImageUrl(friend.getProfileImgUrl(), imageLoader);

            }
            else {
                Bitmap bitmap = BitmapFactory.decodeResource(activity.getResources(), R.drawable.ic_anonymous);
                friendHolder.getProfile().setImageBitmap(bitmap);
            }
        } else {

            ProgressViewHolder progressViewHolder = (ProgressViewHolder) holder;
            progressViewHolder.getProgressBar().setIndeterminate(true);

        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position) != null ? VIEW_ITEM : VIEW_PROGRESS;
    }

    @Override
    public long getItemId(int position) {
        if (this.items == null || this.items.size() == 0) return -1;
        return this.items.get(position).getId();
    }

    @Override
    public int getItemCount() {
        if (this.items == null) return 0;
        return this.items.size();
    }

    public void addFriendsFirst(List<Friend> friends) {
        this.items.addAll(0, friends);
        notifyDataSetChanged();
    }

    public void addFriendsLast(List<Friend> friends) {
        this.items.addAll(friends);
        notifyDataSetChanged();
    }


    public void addProgressItem() {
        this.items.add(null);
        notifyItemInserted(this.items.size() - 1);
    }

    // 프로그래스 바가 들어간 마지막 항목 제거
    public void removeProgressItem() {
        if (this.items.size() == 0) return;

        if (this.items.get(this.items.size() - 1) == null) {
            this.items.remove(this.items.size() - 1);
        }
        this.loading = false;

        //마지막 인덱스 항목이 빠졌으므로 삭제후 items.size() 와 삭제된 항목의 position이 일치함
        notifyItemRemoved(this.items.size());
    }

    public void setItems(List<Friend> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }




}
