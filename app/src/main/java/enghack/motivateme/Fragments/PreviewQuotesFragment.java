package enghack.motivateme.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.Adapters.CategoryQuoteListAdapter;
import enghack.motivateme.CustomViews.MotivateMeButton;
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
    FrameLayout _contentView;
    @BindView(R.id.preview_quotes_twitter_button)
    MotivateMeButton _twitterButton;

    private List<Status> _cachedQuotes;
    private String _account;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.preview_quotes_fragment_layout, container, false);
        ButterKnife.bind(this, layout);
        _account = getArguments().getString(PREVIEW_QUOTES_CATEGORY_KEY, Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0));

        _quotesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        _contentView.setVisibility(View.GONE);
        _loadingView.setVisibility(View.VISIBLE);
        pullQuotes();
        _twitterButton.setAlpha(0.925f);
        _twitterButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        return layout;
    }

    private void pullQuotes() {
        PullTweetsAndReturnTask tweetsTask = new PullTweetsAndReturnTask(new PullTweetsToReturnCallback() {
            @Override
            public void start() {

            }

            @Override
            public void done(List<Status> quotes) {
                _cachedQuotes = quotes;
                showQuotes();
            }
        });
        if(_cachedQuotes == null) {
            tweetsTask.execute(new PullTweetsParams(_account, 100));
        }
        else{
            showQuotes();
        }
    }

    private void showQuotes() {
        if(_cachedQuotes == null){
            Toast toast = Toast.makeText(getActivity(), "Error, please check your connection", Toast.LENGTH_LONG);
            toast.show();
            return;
        }
        _quotesRecyclerView.setAdapter(new CategoryQuoteListAdapter(_cachedQuotes));
        _loadingView.setVisibility(View.GONE);
        _contentView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.preview_quotes_twitter_button)
    public void goToTwitter(View v){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/"+_account));
        startActivity(browserIntent);
    }
}
