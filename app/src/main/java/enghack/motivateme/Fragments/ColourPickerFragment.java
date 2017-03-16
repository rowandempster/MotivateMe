package enghack.motivateme.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.slider.LightnessSlider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.CustomViews.MotivateMeBaseFragment;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/8/17.
 */

public class ColourPickerFragment extends Activity {

    @BindView(R.id.color_picker_view)
    ColorPickerView _pickerView;
    @BindView(R.id.v_lightness_slider)
    LightnessSlider _lightnessSlider;
    @BindView(R.id.color_picked_button)
    Button _confirmButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.colour_picker_fragment_layout);
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

    @OnClick(R.id.color_picked_button)
    public void writeColour(View v) {
        Intent passBack = new Intent();
        passBack.putExtra("color", _pickerView.getSelectedColor());
        setResult(RESULT_OK, passBack);
        finish();
    }

    @OnClick(R.id.colour_picker_activity_back_buttton)
    public void back(View view){
        finish();
    }
}
