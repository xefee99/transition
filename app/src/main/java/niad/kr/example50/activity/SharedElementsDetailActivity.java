package niad.kr.example50.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import niad.kr.example50.R;
import niad.kr.example50.data.Friend;
import niad.kr.example50.util.RequestQueueHolder;

public class SharedElementsDetailActivity extends AppCompatActivity {

    private ImageLoader imageLoader;

    private NetworkImageView profileImageView;
    private TextView titleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_elements_detail);
        setupToolbar();
        setupWindowAnimations();

        imageLoader = RequestQueueHolder.getImageLoader();

        profileImageView = (NetworkImageView) findViewById(R.id.profile_image);
        titleView = (TextView) findViewById(R.id.title);


        Intent intent = getIntent();
        Friend friend = (Friend) intent.getSerializableExtra("friend");

//        Toast.makeText(this, "id: " + friend.getId() + ",name : " + friend.getName(), Toast.LENGTH_SHORT).show();

        profileImageView.setImageUrl(friend.getProfileImgUrl(), imageLoader);
        titleView.setText(friend.getName());

    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupWindowAnimations() {
        getWindow().getEnterTransition().setDuration(500);

//        Slide slideTransition = new Slide();
//        slideTransition.setSlideEdge(Gravity.RIGHT);
//        slideTransition.setDuration(300);
//
//        getWindow().setEnterTransition(slideTransition);
//        getWindow().setExitTransition(slideTransition);


//        getWindow().setReenterTransition(slideTransition);
//        getWindow().setReturnTransition(slideTransition);

    }

}
