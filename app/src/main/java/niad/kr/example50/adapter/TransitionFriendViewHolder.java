package niad.kr.example50.adapter;

/**
 * Created by niad on 4/23/16.
 */


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import niad.kr.example50.R;
import niad.kr.example50.activity.TransitionDetailActivity;
import niad.kr.example50.data.Friend;

/**
 * Item View Holder
 */
public class TransitionFriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private Context context;

    private View itemView;
    private NetworkImageView profile;
    private TextView name;
    private TextView phoneNo;

    private Friend item;

    public TransitionFriendViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        this.itemView.setOnClickListener(this);

        this.profile = (NetworkImageView) itemView.findViewById(R.id.profile_image);
        this.name = (TextView) itemView.findViewById(R.id.name);
        this.phoneNo = (TextView) itemView.findViewById(R.id.phone_no);
    }

    public TransitionFriendViewHolder(Context context, View itemView) {
        this(itemView);
        this.context = context;
    }

    // view onclick listener
    @Override
    public void onClick(View v) {
        // activity에 transition 부여 가능
        Intent intent = new Intent(context, TransitionDetailActivity.class);
        intent.putExtra("friend", item);
        context.startActivity(intent);
    }

    public void setItem(Friend item) {
        this.item = item;
    }

    public View getItemView() {
        return itemView;
    }

    public NetworkImageView getProfile() {
        return profile;
    }

    public TextView getName() {
        return name;
    }

    public TextView getPhoneNo() {
        return phoneNo;
    }
}