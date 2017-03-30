package enghack.motivateme.Adapters;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.viewpagerindicator.IconPagerAdapter;

import enghack.motivateme.Activities.FontPickerActivity;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/26/17.
 */

public class FontPagerAdapter extends PagerAdapter implements IconPagerAdapter {

    public int getPositionFromAsset(String path) {
        for (int i = 0; i < FontPagerEnum.values().length; i++) {
            if (FontPagerEnum.values()[i].getPath().equals(path)) {
                return i;
            }
        }
        return 0;
    }

    private enum FontPagerEnum {
        SERIF("Serif", "fonts/serif.ttf"),
        AMERICANA("Americana", "fonts/americana.ttf"),
        BLOCK("Block", "fonts/block.ttf"),
        CAVIAR("Caviar", "fonts/caviar.ttf"),
        DANCING("Dancing", "fonts/dancing.ttf"),
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

    public Typeface getTypefaceFromPosition(int pos) {
        FontPagerEnum[] fonts = FontPagerEnum.values();
        return Typeface.createFromAsset(_assets, fonts[pos].getPath());
    }

    public String getAssetPathFromPosition(int pos) {
        FontPagerEnum[] fonts = FontPagerEnum.values();
        return fonts[pos].getPath();
    }

    private AssetManager _assets;
    private LayoutInflater _inflator;
    private int[] _icons = new int[]{
            R.drawable.circle_one_states,
            R.drawable.circle_two_states,
            R.drawable.circle_three_states,
            R.drawable.circle_four_states,
            R.drawable.circle_five_states,
            R.drawable.circle_six_states,
    };

    public FontPagerAdapter(AssetManager assets, LayoutInflater layoutInflater) {
        _assets = assets;
        _inflator = layoutInflater;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {
        FontPagerEnum customPagerEnum = FontPagerEnum.values()[position];
        TextView fontTextView = getFontTextView(customPagerEnum);
        collection.addView(fontTextView);
        return fontTextView;
    }

    private TextView getFontTextView(FontPagerEnum customPagerEnum) {
        TextView textView = (TextView) _inflator.inflate(R.layout.view_pager_text_view, null);
        textView.setText(customPagerEnum.getName());
        textView.setTypeface(Typeface.createFromAsset(_assets, customPagerEnum.getPath()));
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
