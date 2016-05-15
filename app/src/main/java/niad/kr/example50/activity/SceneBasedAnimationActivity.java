package niad.kr.example50.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Scene;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.transition.TransitionValues;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import niad.kr.example50.R;

public class SceneBasedAnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = SceneBasedAnimationActivity.class.getSimpleName();

    private static final int DELAY = 100;

    private Scene scene0;
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    private Scene scene4;

    private final List<View> viewsToAnimate = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scene_based_animation);

        setupToolbar();
        setupLayout();

        setupWindowAnimations();
    }

    private void setupToolbar() {

    }

    private void setupLayout() {
        final ViewGroup rootViewGroup = (ViewGroup) findViewById(R.id.root_view_group);

        final ViewGroup buttonsViewGroup = (ViewGroup) findViewById(R.id.btns);

        scene0 = Scene.getSceneForLayout(rootViewGroup, R.layout.scene0, this);
        scene1 = Scene.getSceneForLayout(rootViewGroup, R.layout.scene1, this);
        scene2 = Scene.getSceneForLayout(rootViewGroup, R.layout.scene2, this);
        scene3 = Scene.getSceneForLayout(rootViewGroup, R.layout.scene3, this);
        scene4 = Scene.getSceneForLayout(rootViewGroup, R.layout.scene4, this);


        Button btn1 = (Button) findViewById(R.id.btn1);
        Button btn2 = (Button) findViewById(R.id.btn2);
        Button btn3 = (Button) findViewById(R.id.btn3);
        Button btn4 = (Button) findViewById(R.id.btn4);

        viewsToAnimate.add(btn1);
        viewsToAnimate.add(btn2);
        viewsToAnimate.add(btn3);
        viewsToAnimate.add(btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

        scene0.setEnterAction(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < viewsToAnimate.size(); i++) {
                    View view = viewsToAnimate.get(i);
                    view.animate().setStartDelay(i * DELAY).scaleX(1).scaleY(1);
                }
            }
        });

        scene0.setExitAction(new Runnable() {
            @Override
            public void run() {
                TransitionManager.beginDelayedTransition(buttonsViewGroup);
                View title = scene0.getSceneRoot().findViewById(R.id.scene0_title);
                title.setScaleX(0);
                title.setScaleY(0);
            }
        });


        findViewById(R.id.btn1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransitionManager.go(scene2, new ChangeBounds());
            }
        });

    }

    private void setupWindowAnimations() {

        // 이전 activity에서 transition으로 startActivity를 호출하지 않았을 경우 scene0가 실행되지 않으므로 주의할 것
        Slide transition = new Slide();
        transition.setSlideEdge(Gravity.BOTTOM);
        transition.addListener(new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                transition.removeListener(this);    // onCreate() 시에 호출하고 transition 종료후 리스너 삭제
                TransitionManager.go(scene0);
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}
        });

        getWindow().setEnterTransition(transition);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1: {
//                TransitionManager.go(scene1); // FadeOut -> FadeIn
                TransitionManager.go(scene1, new ChangeBounds());
                break;
            }
            case R.id.btn2: {

                /*
                // java
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.setDuration(500);
                transitionSet.addTransition(new ChangeBounds());
                transitionSet.addTransition(new Slide());

                TransitionManager.go(scene2,transitionSet);
                */

                // xml
                Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds);
                TransitionManager.go(scene2, transition);

                break;
            }
            case R.id.btn3: {

                /*
                // java
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.setDuration(500);
                transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
                transitionSet.addTransition(new Slide());
                transitionSet.addTransition(new ChangeBounds());

                TransitionManager.go(scene3, transitionSet);
                */

                // xml
                Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential);
                TransitionManager.go(scene3, transition);

                break;
            }
            case R.id.btn4: {

                /*
                // java
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
                transitionSet.setDuration(500);

                Slide slide = new Slide();
                slide.setInterpolator(new DecelerateInterpolator());

                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setInterpolator(new BounceInterpolator());

                // slide 뒤에 bound 가 변경됨
                transitionSet.addTransition(slide);
                transitionSet.addTransition(changeBounds);

                TransitionManager.go(scene4, transitionSet);
                */

                Transition transition = TransitionInflater.from(this).inflateTransition(R.transition.slide_and_changebounds_sequential_with_interpolating);
                TransitionManager.go(scene4, transition);

                break;
            }
        }
    }
}
