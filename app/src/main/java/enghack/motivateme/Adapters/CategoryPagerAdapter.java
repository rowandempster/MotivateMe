package enghack.motivateme.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;

import enghack.motivateme.Activities.CategoryPickerActivity;
import enghack.motivateme.Fragments.PreviewQuotesFragment;
import enghack.motivateme.R;
import enghack.motivateme.Util.Constants;

/**
 * Created by rowandempster on 3/26/17.
 */

public class CategoryPagerAdapter extends FragmentStatePagerAdapter {

    private Context _context;

    public CategoryPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        _context = context;
    }

    private enum CategoryPagerEnum {
        INSPIRATIONAL("Inspirational", Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(0)),
        LOVE("Love", Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(1)),
        SPORTS("Sports", Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(2)),
        BOOKS("Books", Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(3)),
        UPLIFTING("Uplifting", Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(4)),
        PHILOSOPHY("Philosophy", Constants.QUOTE_CATEGORY_TWITTER_ACCOUNT_MAP.get(5));

        private String _name;
        private String _twitter;

        CategoryPagerEnum(String name, String twitter) {
            _name = name;
            _twitter = twitter;
        }

        public String getTwitter() {
            return _twitter;
        }

        public String getName() {
            return _name;
        }
    }

    public String getNameFromPosition(int pos) {
        return CategoryPagerEnum.values()[pos].getName();
    }

    public String getTwitterFromPosition(int pos) {
        return CategoryPagerEnum.values()[pos].getTwitter();
    }


    @Override
    public Fragment getItem(int position) {
        PreviewQuotesFragment fragment = new PreviewQuotesFragment();
        Bundle arguments = new Bundle();
        arguments.putString(PreviewQuotesFragment.PREVIEW_QUOTES_CATEGORY_KEY, getTwitterFromPosition(position));
        fragment.setArguments(arguments);
        return fragment;
    }

    @Override
    public int getCount() {
        return CategoryPagerEnum.values().length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return CategoryPagerEnum.values()[position].getName();
    }
}
