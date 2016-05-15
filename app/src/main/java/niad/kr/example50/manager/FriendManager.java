
package niad.kr.example50.manager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import niad.kr.example50.activity.FriendCallback;
import niad.kr.example50.data.Friend;
import niad.kr.example50.util.RequestQueueHolder;


/**
 * Created by niad on 3/25/16.
 */
public class FriendManager {
    private static final String TAG = FriendManager.class.getSimpleName();

    private final String SERVER_URL = "http://192.168.0.15:8080";
//    private final String SERVER_URL = "http://192.168.1.29:8080";
//    private final String SERVER_URL = "http://192.168.1.102:8080";
    private final String FRIEND_LIST_URL = SERVER_URL + "/friend";
    private final String FRIEND_REGISTER_URL = SERVER_URL + "/friend";

    private final int DEFAULT_LIMIT = 10;

    private Context context;
    private RequestQueue requestQueue;
    private FriendCallback friendCallback;

    public FriendManager(Context context, FriendCallback callback) {
        this.context = context;

        this.friendCallback = callback;
        this.requestQueue = RequestQueueHolder.getRequestQueue();
    }

    public void findFriends() {
        findFriends(null, false);
    }

    // 시작 ID와 limit 갯수를 필요로 페이징 단위로 데이터를 조회하여야 함
    /**
     *
     * @param baseId 조회할 기준 아이디
     * @param recent 기준아이디를 중심으로 이후 데이터를 조회할 것인지
     */
    public void findFriends(final Long baseId, final boolean recent) {

        StringBuilder sb = new StringBuilder(FRIEND_LIST_URL);
        sb.append("?");
        if (baseId != null) {
            sb.append("baseId=").append(baseId);
            sb.append("&recent=").append(recent);
            sb.append("&limit=").append(DEFAULT_LIMIT);
        }
        else {
            sb.append("limit=").append(DEFAULT_LIMIT);
        }
        final String url = sb.toString();

        StringRequest request = new StringRequest(
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String responseText) {

                        try {
                            JSONArray response = new JSONArray(responseText);
                            List<Friend> friends = new ArrayList<>();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    Friend friend = new Friend();
                                    friend.setId(jsonObject.getLong("id"));
                                    friend.setName(jsonObject.getString("name"));
                                    friend.setPhoneNo(jsonObject.getString("phoneNo"));
                                    friend.setProfileImgUrl(jsonObject.getString("profileImageUrl"));

                                    friends.add(friend);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            if (recent) {
                                friendCallback.addFriendsFirst(friends);
                            } else {
                                friendCallback.addFriendsLast(friends);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                            if (recent) {
                                friendCallback.onErrorLoadMore("통신중 에러가 발생했습니다.");
                            }
                            else {
                                friendCallback.onErrorLoadMore("통신중 에러가 발생했습니다.");
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (recent) {
                            friendCallback.onErrorLoadMore("통신중 에러가 발생했습니다.");
                        }
                        else {
                            friendCallback.onErrorLoadMore("통신중 에러가 발생했습니다.");
                        }
                    }
                }

        ){
            // post 전송에만 사용됨
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return null;
            }

            // 헤더를 변경해야할 요청에만 사용됨
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return Collections.emptyMap();
            }
        };

        this.requestQueue.add(request);

    }


    public void registerFriend(Friend friend) {

        String url = FRIEND_REGISTER_URL;

        Map<String, String> params = new HashMap<>();
        params.put("name", "신규친구");
        params.put("phoneNo", "010-0000-0000");
        params.put("profileImgUrl", friend.getProfileImgUrl());

        CustomRequest request = new CustomRequest(
                Request.Method.POST,
                url,
                null,
                params,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, response.toString(), Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, error.toString());
                    }
                }
        );


        requestQueue.add(request);
        Toast.makeText(context, "신규 요청 완료", Toast.LENGTH_SHORT).show();

    }





}
