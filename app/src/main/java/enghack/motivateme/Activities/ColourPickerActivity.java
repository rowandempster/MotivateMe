package enghack.motivateme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.slider.LightnessSlider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.CustomViews.MotivateMeButton;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/8/17.
 */

public class ColourPickerActivity extends Activity {

    public static final String COLOUR_PICKED_EXTRA = "colour";

    @BindView(R.id.color_picker_view)
    ColorPickerView _pickerView;
    @BindView(R.id.v_lightness_slider)
    LightnessSlider _lightnessSlider;
    @BindView(R.id.confirm_button)
    MotivateMeButton _confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colour_picker_activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        int currColor = UserPreferencesTableInterface.readTextColour();
        _confirmButton.setBackgroundColor(currColor);
        _pickerView.setColor(currColor, false);
        _lightnessSlider.getViewTreeObserver().addOnGlobalLayoutListener(() -> _lightnessSlider.setColor(currColor));
        _lightnessSlider.setOnTouchListener((view, action) -> {
            _confirmButton.setBackgroundColor(_pickerView.getSelectedColor());
            return false;
        });
        _pickerView.setOnTouchListener((view, action) -> {
            _confirmButton.setBackgroundColor(_pickerView.getSelectedColor());
            return false;
        });
    }

    @OnClick(R.id.confirm_button)
    public void writeColour(View v) {
        Intent passBack = new Intent();
        passBack.putExtra(COLOUR_PICKED_EXTRA, _pickerView.getSelectedColor());
        setResult(RESULT_OK, passBack);
        finish();
    }

    @OnClick(R.id.back_button)
    public void back(View view){
        finish();
    }
}
