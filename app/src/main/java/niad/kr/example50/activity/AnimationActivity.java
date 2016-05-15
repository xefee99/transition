package niad.kr.example50.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import niad.kr.example50.R;
import niad.kr.example50.util.RequestQueueHolder;

public class AnimationActivity extends AppCompatActivity implements View.OnClickListener {

    private final static int MAX_WIDTH = 320; //scale dip
    private final static int MAX_HEIGHT = 320; //scale dip

    private RequestQueue requestQueue;
    private ImageLoader imageLoader;

    private ViewGroup rootViewGroup;

    private NetworkImageView imageView;

    private int widthInDip;
    private int heightInDip;

    private int savedWidth;
    private int savedHeight;

    private boolean sizeChanged = false;
    private boolean locationChanged = false;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);

        requestQueue = RequestQueueHolder.getRequestQueue();
        imageLoader = RequestQueueHolder.getImageLoader();


        rootViewGroup = (ViewGroup) findViewById(R.id.root_view_group);
        imageView = (NetworkImageView) findViewById(R.id.profile_image);

        String url = "http://img.etnews.com/news/article/2016/02/08/article_08200515224045.jpg";
        imageView.setImageUrl(url, imageLoader);

        findViewById(R.id.btn_change_size).setOnClickListener(this);
        findViewById(R.id.btn_change_location).setOnClickListener(this);

        widthInDip = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MAX_WIDTH, getResources().getDisplayMetrics());
        heightInDip = (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MAX_HEIGHT, getResources().getDisplayMetrics());

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_size: {
                TransitionManager.beginDelayedTransition(rootViewGroup);
                ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
                if (sizeChanged) {
                    layoutParams.width = savedWidth;
                    layoutParams.height = savedHeight;
                } else {
                    savedWidth = layoutParams.width;
                    savedHeight = layoutParams.height;
                    layoutParams.width = widthInDip;
                    layoutParams.height = heightInDip;
                }

                sizeChanged = !sizeChanged;
                imageView.setLayoutParams(layoutParams);
                break;
            }
            case R.id.btn_change_location: {
                TransitionManager.beginDelayedTransition(rootViewGroup);

                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
                if (locationChanged) {
                    layoutParams.gravity = Gravity.CENTER;
                } else {
                    layoutParams.gravity = Gravity.LEFT;
                }
                locationChanged = !locationChanged;
                imageView.setLayoutParams(layoutParams);
                break;
            }
        }
    }
}
