package enghack.motivateme.CustomViews;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.ToggleButton;

import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/23/17.
 */

public class MotivateMeToggleButton extends ToggleButton {
    private boolean _wasActive;

    public MotivateMeToggleButton(Context context) {
        this(context, null);
    }

    public MotivateMeToggleButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MotivateMeToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public void setChecked(boolean checked) {
        if (checked && !_wasActive) {
            TransitionDrawable transition = (TransitionDrawable) getContext().getDrawable(R.drawable.toggle_button_active_transition);
            setBackground(transition);
            transition.startTransition(300);
        } else if(!checked && _wasActive) {
            TransitionDrawable transition = (TransitionDrawable) getContext().getDrawable(R.drawable.toggle_button_inactive_transition);
            setBackground(transition);
            transition.reverseTransition(300);
        }
        else if(!checked && !_wasActive){
            setBackgroundDrawable(getContext().getDrawable(R.drawable.toggle_button_state_unselected));
        }
        _wasActive = checked;

    }
}
