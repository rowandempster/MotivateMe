package enghack.motivateme.Fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
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

public class ColourPickerFragment extends MotivateMeBaseFragment {

    @BindView(R.id.color_picker_view)
    ColorPickerView _pickerView;
    @BindView(R.id.v_lightness_slider)
    LightnessSlider _lightnessSlider;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colour_picker_fragment_layout, container, false);
        ButterKnife.bind(this, v);
        int currColor = UserPreferencesTableInterface.readTextColour();
        _pickerView.setColor(currColor, false);
        _lightnessSlider.getViewTreeObserver().addOnGlobalLayoutListener(()->{
            _lightnessSlider.setColor(currColor);
        });
        return v;
    }

    @OnClick(R.id.color_picked_button)
    public void writeColour(View v) {
        getFragmentManager().popBackStack();
        UserPreferencesTableInterface.writeTextColourAndRefreshWallpaper(_pickerView.getSelectedColor(), getActivity());
    }
}