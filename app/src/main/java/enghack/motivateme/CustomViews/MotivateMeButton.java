package enghack.motivateme.CustomViews;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.annotation.ColorInt;
import android.util.AttributeSet;
import android.widget.Button;

import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/24/17.
 */

public class MotivateMeButton extends android.support.v7.widget.AppCompatButton {
    Drawable _background;
    public MotivateMeButton(Context context) {
        this(context, null);
    }

    public MotivateMeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MotivateMeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        setAllCaps(false);
    }

    @Override
    public void setBackgroundColor(@ColorInt int color) {
        if(_background == null){
            _background = getContext().getDrawable(R.drawable.motive_me_background_ripple);
        }
        _background.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
        setBackground(_background);
    }
}
