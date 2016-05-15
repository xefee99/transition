package niad.kr.example50.util;

import android.app.Activity;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niad on 4/23/16.
 */
public abstract class TransitionUtil {

    public static ActivityOptionsCompat createActivityOptions(Activity activity, List<Pair<View, String>> participants) {
        return createActivityOptions(activity, participants, false);
    }

    public static ActivityOptionsCompat createActivityOptions(Activity activity, List<Pair<View, String>> participants, boolean includeStatusBar) {
        Pair<View, String>[] pairs = createTransitionParticipants(activity, participants, includeStatusBar);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        return options;
    }

    public static Pair<View, String>[] createTransitionParticipants(Activity activity, List<Pair<View, String>> participants) {
        return createTransitionParticipants(activity, participants, false);
    }

    public static Pair<View, String>[] createTransitionParticipants(Activity activity, List<Pair<View, String>> participants, boolean includeStatusBar) {
        List<Pair> pairs = new ArrayList<>();
        View decor = activity.getWindow().getDecorView();
        View navBar = decor.findViewById(android.R.id.navigationBarBackground);
        pairs.add(new Pair<>(navBar, navBar.getTransitionName()));

        if (includeStatusBar) {
            View statusBar = decor.findViewById(android.R.id.statusBarBackground);
            pairs.add(new Pair<>(statusBar, statusBar.getTransitionName()));
        }

        if (participants != null) pairs.addAll(participants);
        return pairs.toArray(new Pair[pairs.size()]);
    }

}
