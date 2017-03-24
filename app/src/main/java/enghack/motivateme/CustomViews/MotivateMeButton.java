package enghack.motivateme.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/24/17.
 */

public class MotivateMeButton extends android.support.v7.widget.AppCompatButton {
    public MotivateMeButton(Context context) {
        this(context, null);
    }

    public MotivateMeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MotivateMeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setBackground(getContext().getDrawable(R.drawable.motive_me_background_ripple));
        setAllCaps(false);
    }
}
