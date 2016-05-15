package niad.kr.example50.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import niad.kr.example50.R;

/**
 * Created by niad on 4/23/16.
 */

// progress view holder
public class ProgressViewHolder extends RecyclerView.ViewHolder {
    private ProgressBar progressBar;

    public ProgressViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

}

