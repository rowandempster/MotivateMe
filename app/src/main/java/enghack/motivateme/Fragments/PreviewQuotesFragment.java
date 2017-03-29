package enghack.motivateme.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.Adapters.CategoryQuoteListAdapter;
import enghack.motivateme.R;
import enghack.motivateme.Tasks.PullTweets.PullTweetsAndPutInDbTask;
import enghack.motivateme.Tasks.PullTweets.PullTweetsAndReturnTask;
import enghack.motivateme.Tasks.PullTweets.PullTweetsCallback;
import enghack.motivateme.Tasks.PullTweets.PullTweetsParams;
import enghack.motivateme.Tasks.PullTweets.PullTweetsToReturnCallback;
import enghack.motivateme.Util.Constants;
import twitter4j.Status;

/**
 * Created by rowandempster on 3/28/17.
 */

public class PreviewQuotesFragment extends Fragment {
    public final static String PREVIEW_QUOTES_CATEGORY_KEY = "category_key";

    @BindView(R.id.preview_quotes_recycler_view)
    RecyclerView _quotesRecyclerView;
    @BindView(R.id.preview_quotes_loading_view)
    LinearLayout _loadingView;
    @BindView(R.id.preview_quotes_content_view)
    LinearLayout _contentView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.preview_quotes_fragment_layout, container, false);
        ButterKnife.bind(this, layout);
        _quotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        _contentView.setVisibility(View.GONE);
        _loadingView.setVisibility(View.VISIBLE);
        pullQuotes();
        return layout;
    }

    private void pullQuotes() {
        PullTweetsAndReturnTask tweetsTask = new PullTweetsAndReturnTask(new PullTweetsToReturnCallback() {
            @Override
            public void start() {

            }

            @Override
            public void done(List<Status> quotes) {
                _quotesRecyclerView.setAdapter(new CategoryQuoteListAdapter(quotes));
                _loadingView.setVisibility(View.GONE);
                _contentView.setVisibility(View.VISIBLE);
            }
        });
        String account = getArguments().getString(PREVIEW_QUOTES_CATEGORY_KEY, Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0));
        tweetsTask.execute(new PullTweetsParams(account, 30));
    }
}
