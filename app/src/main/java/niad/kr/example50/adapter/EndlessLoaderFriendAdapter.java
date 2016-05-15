package niad.kr.example50.adapter;

import android.content.Context;
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


/**
 * Created by niad on 3/23/16.
 */
public class EndlessLoaderFriendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static int VISIBLE_THRESHOLD = 2;

    private final static String TAG = EndlessLoaderFriendAdapter.class.getSimpleName();

    private final int VIEW_PROGRESS = 0;
    private final int VIEW_ITEM = 1;


    private Context context;
    private LayoutInflater inflater;
    private int layoutId;
    private List<Friend> items;


//    private int lastVisibleItem;
//    private int totalItemCount;
    private boolean loading;
    private OnLoadMoreListener onLoadMoreListener;

    private ImageLoader imageLoader;

    public EndlessLoaderFriendAdapter(Context context, int layoutId, List<Friend> items, RecyclerView recyclerView) {

        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.layoutId = layoutId;
        this.items = items;

        this.imageLoader = RequestQueueHolder.getImageLoader();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_ITEM) {
            View view = this.inflater.inflate(this.layoutId, parent, false);
            return new TransitionFriendViewHolder(context, view);
        }
        else {
            View view = this.inflater.inflate(R.layout.item_progress_bar, parent, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        Friend friend = this.items.get(position);

        if (holder instanceof TransitionFriendViewHolder) {

            TransitionFriendViewHolder friendHolder = (TransitionFriendViewHolder) holder;

            friendHolder.setItem(friend);
            friendHolder.getName().setText(friend.getName());
            friendHolder.getPhoneNo().setText(friend.getPhoneNo());

            if (friend.getProfileImgUrl() != null && friend.getProfileImgUrl().trim().length() > 0) {

                friendHolder.getProfile().setImageUrl(friend.getProfileImgUrl(), imageLoader);

            }
            else {
                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_anonymous);
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


    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public OnLoadMoreListener getOnLoadMoreListener() {
        return onLoadMoreListener;
    }

    //    public int getLastVisibleItem() {
//        return lastVisibleItem;
//    }
//
//    public int getTotalItemCount() {
//        return totalItemCount;
//    }
}
