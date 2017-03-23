package enghack.motivateme.CustomViews;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/22/17.
 */

public class LabeledButton extends LinearLayout {
    @BindView(R.id.labeled_button_label_text_view)
    TextView _labelTextView;
    @BindView(R.id.labeled_button_button)
    ToggleButton _button;
    private String _label;
    private String _buttonText;

    public LabeledButton(Context context) {
        this(context, null);
    }

    public LabeledButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabeledButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (attrs != null) {
            TypedArray a = context.getTheme().obtainStyledAttributes(
                    attrs,
                    R.styleable.LabeledButton,
                    0, 0);

            try {
                _label = a.getString(R.styleable.LabeledButton_label_text);
                _buttonText = a.getString(R.styleable.LabeledButton_button_text);
            } finally {
                a.recycle();
            }
        }
        View view = inflate(getContext(), R.layout.labeled_button, this);
        ButterKnife.bind(this, view);
        _labelTextView.setText(_label);
        _button.setText(_buttonText);
        setClickable(true);
    }

    public void setLabelText(String text) {
        _labelTextView.setText(text);
    }

    public void setButtonText(String text) {
        _button.setTextOff(text);
        _button.setTextOn(text);
    }

    public void setPressedButton(boolean pressed) {
        _button.setChecked(pressed);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }
}