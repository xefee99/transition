package niad.kr.example50;

import android.content.Intent;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import niad.kr.example50.activity.AnimationActivity;
import niad.kr.example50.activity.RecyclerViewWithEndlessLoaderActivity;
import niad.kr.example50.activity.RevealActivity;
import niad.kr.example50.activity.SceneBasedAnimationActivity;
import niad.kr.example50.activity.SharedElementsActivity;
import niad.kr.example50.activity.TransitionActivity;
import niad.kr.example50.util.RequestQueueHolder;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupWindowAnimations();

        // NullPointerException 발생가능
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);
        findViewById(R.id.btn7).setOnClickListener(this);


        // request queue와 imageLoader 객체 생성
        RequestQueueHolder.init(this);
    }





    @Override
    public void onClick(View v) {
        Pair[] participants = createTransitionParticipants();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, participants);

        switch (v.getId()) {
            case R.id.btn1: {
                startActivity(new Intent(this, TransitionActivity.class), options.toBundle());
                break;
            }
            case R.id.btn2: {
                startActivity(new Intent(this, SharedElementsActivity.class), options.toBundle());
                break;
            }
            case R.id.btn3: {
                //TODO SharedElementsInFragments

            }
            case R.id.btn4: {
                startActivity(new Intent(this, AnimationActivity.class), options.toBundle());
                break;
            }
            case R.id.btn5: {
                // 주의 : transition animation 옵션으로 실행시키지 않으면 다음 activity에서 transition을 받지 못한다.
                startActivity(new Intent(this, SceneBasedAnimationActivity.class), options.toBundle());
                break;
            }
            case R.id.btn6: {
                startActivity(new Intent(this, RevealActivity.class), options.toBundle());
                break;
            }
            case R.id.btn7: {
                startActivity(new Intent(this, RecyclerViewWithEndlessLoaderActivity.class), options.toBundle());
                break;
            }

            default: {
                Toast.makeText(this, "정의된 액션이 없음", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Pair[] createTransitionParticipants() {
        List<Pair> pairs = new ArrayList<>();
        View decor = getWindow().getDecorView();
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);
        pairs.add(new Pair(navBar, navBar.getTransitionName()));
        return pairs.toArray(new Pair[pairs.size()]);
    }


    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupWindowAnimations() {
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(300);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }
}
