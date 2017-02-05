package enghack.motivateme.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 2/4/17.
 */

public class SettingOptionCustomView extends LinearLayout {
    TextView _titleTextView;
    ImageView _image;
    private String _title;
    private int _resId;

    public SettingOptionCustomView(Context context, String title, int resId) {
        super(context);
        _title = title;
        _resId = resId;
        initView(context);
    }

    private void initView(Context context) {
        inflate(getContext(), R.layout.settings_option, this);
        _titleTextView = (TextView) findViewById(R.id.setting_name);
        _image = (ImageView) findViewById(R.id.setting_image);
        setTitle(_title);
        setImage(_resId);



    }


    public void setTitle(String title){
        _titleTextView.setText(title);
    }

    public void setImage(int resId){
        _image.setImageResource(resId);
    }

}
