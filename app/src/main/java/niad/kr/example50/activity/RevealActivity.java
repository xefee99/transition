package niad.kr.example50.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.transition.ArcMotion;
import android.transition.ChangeBounds;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import niad.kr.example50.R;

public class RevealActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener {

    private final static int DELAY = 100;

    private ViewGroup rootViewGroup;
    private TextView bodyTextView;

    private ImageView imgBlue;
    private ImageView imgPurple;
    private ImageView imgGreen;
    private ImageView imgOrange;

    private Interpolator linearOutSlowInInterpolator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        linearOutSlowInInterpolator = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);

        setupToolbar();
        setupLayout();
        setupWindowAnimations();
    }


    private void setupToolbar() {

    }

    private void setupLayout() {
        rootViewGroup = (ViewGroup) findViewById(R.id.root_view_group);
        bodyTextView = (TextView) findViewById(R.id.text_body);

        imgBlue = (ImageView) findViewById(R.id.img_blue);
        imgPurple = (ImageView) findViewById(R.id.img_purple);
        imgGreen = (ImageView) findViewById(R.id.img_green);
        imgOrange = (ImageView) findViewById(R.id.img_orange);

        // 터치된 건을 확인하기 위해 OnClickListener를 사용
        imgBlue.setOnClickListener(this);
        imgPurple.setOnClickListener(this);
        imgGreen.setOnClickListener(this);

        // 터치된 좌표를 얻기 위해 OnTouchListener를 사용
        imgOrange.setOnTouchListener(this);
    }

    private void setupWindowAnimations() {

    }


    // 버튼 클릭
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_blue: {
                // 이미지가 눌릴경우 가운데서 부터 색상이 변경

                // animate from center
                int centerX = (rootViewGroup.getLeft() + rootViewGroup.getRight()) / 2;
                int centerY = (rootViewGroup.getTop() + rootViewGroup.getBottom()) / 2;

                double radius = Math.hypot(rootViewGroup.getWidth(), rootViewGroup.getHeight());
                Animator animator = ViewAnimationUtils.createCircularReveal(rootViewGroup, centerX, centerY, 0, (float) radius);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();

                // change attribute
                int color = ContextCompat.getColor(this, android.R.color.holo_blue_bright);
                rootViewGroup.setBackgroundColor(color);
                bodyTextView.setText("Blue");

                break;
            }
            case R.id.img_purple: {
                // 눌린 이미지가 가운데로 이동후 종료

                // 이전 layoutParam 조회
                final ViewGroup.LayoutParams originalLayoutParams = imgPurple.getLayoutParams();

                // transition
                TransitionSet transitionSet = new TransitionSet();
                transitionSet.setInterpolator(new DecelerateInterpolator());
                transitionSet.setDuration(500);

                ArcMotion arcMotion = new ArcMotion();
                arcMotion.setMaximumAngle(90);
                arcMotion.setMinimumHorizontalAngle(90);
                arcMotion.setMinimumVerticalAngle(0);

                ChangeBounds changeBounds = new ChangeBounds();
                changeBounds.setPathMotion(arcMotion);

                transitionSet.addTransition(changeBounds);

                transitionSet.addListener(new Transition.TransitionListener() {
                    @Override
                    public void onTransitionStart(Transition transition) {
                    }

                    @Override
                    public void onTransitionEnd(Transition transition) {
                        //transition 종료후 적용될 animation

                        // 가운데에서 이미지가 펼쳐지도록
                        int centerX = (rootViewGroup.getLeft() + rootViewGroup.getRight()) / 2;
                        int centerY = (rootViewGroup.getTop() + rootViewGroup.getBottom()) / 2;

                        double radius = Math.hypot(rootViewGroup.getWidth(), rootViewGroup.getHeight());
                        Animator animator = ViewAnimationUtils.createCircularReveal(rootViewGroup, centerX, centerY, 0, (float) radius);
                        animator.setDuration(500);
                        animator.setInterpolator(new AccelerateDecelerateInterpolator());
                        animator.start();

                        // 애니메이션이 종료되면 다음 값으로 설정됨
                        int color = ContextCompat.getColor(RevealActivity.this, android.R.color.holo_purple);
                        rootViewGroup.setBackgroundColor(color);

                        bodyTextView.setText("버튼이 가운데로 이동후 색상이 변경됨");
                        imgPurple.setLayoutParams(originalLayoutParams);
                    }

                    @Override
                    public void onTransitionCancel(Transition transition) {
                    }

                    @Override
                    public void onTransitionPause(Transition transition) {
                    }

                    @Override
                    public void onTransitionResume(Transition transition) {
                    }
                });

                // 여기서부터 transition 시작
                TransitionManager.beginDelayedTransition(rootViewGroup, transitionSet);

                // 눌린 버튼을 가운데로 이동
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                imgPurple.setLayoutParams(layoutParams);

                break;
            }
            case R.id.img_green: {
                // 버튼 사라진뒤 상단에서 부터 원형으로 색상 변경
                animateButtonsOut();

                int centerX = (rootViewGroup.getLeft() + rootViewGroup.getRight()) / 2;
                int centerY = 0;

                double radius = Math.hypot(rootViewGroup.getWidth(), rootViewGroup.getHeight());
                Animator animator = ViewAnimationUtils.createCircularReveal(rootViewGroup, centerX, centerY, 0, (float) radius);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.start();

                // 애니메이션이 종료되면 버튼 보이기
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationButtonsIn();
                    }
                });

                int color = ContextCompat.getColor(RevealActivity.this, android.R.color.holo_green_dark);
                rootViewGroup.setBackgroundColor(color);
                bodyTextView.setText("상단에서 색상이 바뀌면서 하단으로 전파되도록 변경");

                break;
            }
            case R.id.img_orange: {
                // onTouch(View, MotionEvent) 에서 처리함
                break;
            }
        }
    }


    // 터치된 좌표를 획득
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v.getId() == R.id.img_orange) {
                animateButtonsOut();

                int x = (int) event.getRawX();
                int y = (int) event.getRawY();

                double radius = Math.hypot(rootViewGroup.getWidth(), rootViewGroup.getHeight());

                Animator animator = ViewAnimationUtils.createCircularReveal(rootViewGroup, x, y, 0, (float) radius);
                animator.setDuration(500);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        animationButtonsIn();
                    }
                });
                animator.start();

                int color = ContextCompat.getColor(RevealActivity.this, android.R.color.holo_orange_dark);
                rootViewGroup.setBackgroundColor(color);
                bodyTextView.setText("터치된 위치에서 원형 색상 변경 이벤트 발생");
            }
        }

        return false;
    }

    private void animationButtonsIn() {
        int size = rootViewGroup.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = rootViewGroup.getChildAt(i);
            child.animate()
                    .setStartDelay(100 + i * DELAY)
                    .setInterpolator(linearOutSlowInInterpolator)
                    .alpha(1)
                    .scaleX(1f)
                    .scaleY(1f);
        }
    }

    // 버튼 제거 animation 바로사라짐
    private void animateButtonsOut() {
        int size = rootViewGroup.getChildCount();
        for (int i = 0; i < size; i++) {
            View child = rootViewGroup.getChildAt(i);
            child.animate()
                    .setStartDelay(i)
                    .setInterpolator(linearOutSlowInInterpolator)
                    .alpha(0)
                    .scaleX(0)
                    .scaleY(0);
        }
    }

}
