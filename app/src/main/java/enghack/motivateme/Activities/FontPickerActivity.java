package enghack.motivateme.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.viewpagerindicator.IconPagerAdapter;
import com.viewpagerindicator.PageIndicator;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/25/17.
 */

public class FontPickerActivity extends Activity {
    @BindView(R.id.font_picker_view_pager)
    ViewPager _fontPicker;
    @BindView(R.id.font_indicator)
    PageIndicator _fontIndicator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_picker_activity_layout);
        ButterKnife.bind(this);
        _fontPicker.setAdapter(new FontPagerAdapter(this));
        _fontIndicator.setViewPager(_fontPicker);
    }

    private enum FontPagerEnum {
        AMERICANA("Americana", "fonts/americana.ttf"),
        BLOCK("Block", "fonts/block.ttf"),
        CAVIAR("Caviar", "fonts/caviar.ttf"),
        DANCING("Dancing", "fonts/dancing.ttf"),
        SERIF("Serif", "fonts/serif.ttf"),
        TYPEWRITER("Typewriter", "fonts/typewriter.ttf");

        private String _name;
        private String _path;

        FontPagerEnum(String name, String path) {
            _name = name;
            _path = path;
        }

        public String getPath() {
            return _path;
        }

        public String getName() {
            return _name;
        }
    }

    private class FontPagerAdapter extends PagerAdapter implements IconPagerAdapter {
        private Context _context;
        private int[] _icons = new int[]{
                R.drawable.circle_one_states,
                R.drawable.circle_two_states,
                R.drawable.circle_three_states,
                R.drawable.circle_four_states,
                R.drawable.circle_five_states,
                R.drawable.circle_six_states,
        };

        public FontPagerAdapter(Context context) {
            _context = context;
        }

        @Override
        public Object instantiateItem(ViewGroup collection, int position) {
            FontPagerEnum customPagerEnum = FontPagerEnum.values()[position];
            TextView fontTextView = getFontTextView(customPagerEnum);
            collection.addView(fontTextView);
            return fontTextView;
        }

        private TextView getFontTextView(FontPagerEnum customPagerEnum) {
            TextView textView = (TextView) getLayoutInflater().inflate(R.layout.view_pager_text_view, null);
            textView.setText(customPagerEnum.getName());
            textView.setTypeface(Typeface.createFromAsset(getAssets(), customPagerEnum.getPath()));
            return textView;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }

        @Override
        public int getIconResId(int index) {
            return _icons[index];
        }

        @Override
        public int getCount() {
            return FontPagerEnum.values().length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
