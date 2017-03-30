package enghack.motivateme.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 2/4/17.
 */

public class SettingOption extends LinearLayout {
    @BindView(R.id.setting_name) TextView _titleTextView;
    @BindView(R.id.setting_image) ImageView _image;

    private String _label;
    private Drawable _drawable;

    public SettingOption(Context context) {
        super(context);
        initView();
    }

    private void initView() {
        View v = inflate(getContext(), R.layout.settings_option, this);
        ButterKnife.bind(this, v);
        _titleTextView.setText(_label);
        _image.setImageDrawable(_drawable);

    }

    public SettingOption(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SettingOption,
                0, 0);

        try {
            _label = a.getString(R.styleable.SettingOption_label);
            _drawable = a.getDrawable(R.styleable.SettingOption_settingIcon);
        } finally {
            a.recycle();
        }
        initView();
    }

    public SettingOption(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

}
