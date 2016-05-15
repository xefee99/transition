package niad.kr.example50.activity;

import java.util.List;

import niad.kr.example50.data.Friend;


/**
 * Created by niad on 3/25/16.
 */
public interface FriendCallback {

    void addFriendsFirst(List<Friend> friends);

    void addFriendsLast(List<Friend> friends);

    void onErrorSwipeRefreshing(String errorMessage);

    void onErrorLoadMore(String errorMessage);

}
