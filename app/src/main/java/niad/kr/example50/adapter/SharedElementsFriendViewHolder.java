package niad.kr.example50.adapter;

/**
 * Created by niad on 4/23/16.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.animation.AnimatorCompatHelper;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

import niad.kr.example50.R;
import niad.kr.example50.activity.SharedElementsDetailActivity;
import niad.kr.example50.activity.TransitionDetailActivity;
import niad.kr.example50.data.Friend;
import niad.kr.example50.util.TransitionUtil;

/**
 * Item View Holder
 */
public class SharedElementsFriendViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private static final String TAG = SharedElementsFriendViewHolder.class.getSimpleName();

    private Activity activity;

    private View itemView;
    private NetworkImageView profile;
    private TextView name;
    private TextView phoneNo;

    private Friend item;

    public SharedElementsFriendViewHolder(View itemView) {
        super(itemView);

        this.itemView = itemView;
        this.itemView.setOnClickListener(this);

        this.profile = (NetworkImageView) itemView.findViewById(R.id.profile_image);
        this.name = (TextView) itemView.findViewById(R.id.name);
        this.phoneNo = (TextView) itemView.findViewById(R.id.phone_no);
    }

    public SharedElementsFriendViewHolder(Activity activity, View itemView) {
        this(itemView);
        this.activity = activity;
    }

    // view onclick listener
    @Override
    public void onClick(View v) {
        List<Pair<View, String>> participants = new ArrayList<>();
        participants.add(new Pair<View, String>(this.profile, this.profile.getTransitionName()));
        participants.add(new Pair<View, String>(this.name, this.name.getTransitionName()));

        ActivityOptionsCompat options = TransitionUtil.createActivityOptions(activity, participants);
/*
        Pair<View, String>[] pairs = participants.toArray(new Pair[participants.size()]);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
*/

        Intent intent = new Intent(activity, SharedElementsDetailActivity.class);
        intent.putExtra("friend", item);
        activity.startActivity(intent, options.toBundle());
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