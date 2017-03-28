package enghack.motivateme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;


import com.shawnlin.numberpicker.NumberPicker;
import com.viewpagerindicator.PageIndicator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.Adapters.FontPagerAdapter;
import enghack.motivateme.CustomViews.MotivateMeToggleButton;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.Models.FontPickerResult;
import enghack.motivateme.R;
import enghack.motivateme.Util.UserFontSize;
import io.reactivex.subjects.BehaviorSubject;

/**
 * Created by rowandempster on 3/25/17.
 */

public class FontPickerActivity extends Activity {
    public static final String FONT_PICKED_EXTRA = "font";
    @BindView(R.id.font_picker_view_pager)
    ViewPager _fontPicker;
    @BindView(R.id.font_indicator)
    PageIndicator _fontIndicator;
    @BindView(R.id.font_picker_bold)
    MotivateMeToggleButton _boldToggle;
    @BindView(R.id.font_picker_normal)
    MotivateMeToggleButton _normalToggle;
    @BindView(R.id.font_picker_italic)
    MotivateMeToggleButton _italicToggle;
    @BindView(R.id.font_picker_preview)
    TextView _preview;
    @BindView(R.id.font_picker_number_picker)
    NumberPicker _fontSizePicker;

    private MotivateMeToggleButton _selectedToggle;

    private List<MotivateMeToggleButton> _fontWeightList;
    private FontPagerAdapter _adapter;

    private int _fontStyle;
    private Typeface _fontTypeface;
    private int _fontSize = 75;

    BehaviorSubject _changeTimer;

    private Handler _debounceHandler = new Handler();
    private Runnable _debounceRunnable = () -> _fontSizePicker.setMaxValue(UserFontSize.getMaxFontSize(FontPickerActivity.this, _fontTypeface));;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_picker_activity_layout);
        ButterKnife.bind(this);

        initPreferences();

        setupFontViewPager();
        setupFontSizePicker();
        setupFontStyleToggles();

        updatePreview();
    }

    private void initPreferences() {
        _fontTypeface = Typeface.createFromAsset(getAssets(), UserPreferencesTableInterface.readTextFont());
        _fontSize = UserPreferencesTableInterface.readTextSize();
        _fontStyle = UserPreferencesTableInterface.readTextStyle();
    }

    private void setupFontViewPager() {
        _adapter = new FontPagerAdapter(getAssets(), getLayoutInflater());
        _fontPicker.setAdapter(_adapter);
        _fontIndicator.setViewPager(_fontPicker);
        _fontPicker.setCurrentItem(_adapter.getPositionFromAsset(UserPreferencesTableInterface.readTextFont()));
        _fontIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                _fontTypeface = _adapter.getTypefaceFromPosition(position);
                _debounceHandler.removeCallbacks(_debounceRunnable);
                _debounceHandler.postDelayed(_debounceRunnable, 500);
                updatePreview();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setupFontStyleToggles() {
        _fontWeightList = new ArrayList<>(Arrays.asList(_boldToggle, _normalToggle, _italicToggle));
        for (MotivateMeToggleButton toggle : _fontWeightList) {
            if (toggle.getTypeface().getStyle() == _fontStyle) {
                toggle.setChecked(true);
            }
            toggle.setOnClickListener(v -> {
                _selectedToggle = toggle;
                updateSelectedWeight();
            });
        }
    }

    private void setupFontSizePicker() {
        _fontSizePicker.setOnValueChangedListener((picker, oldVal, newVal) -> {
            _fontSize = newVal;
            updatePreview();
        });
        _fontSizePicker.setMaxValue(UserFontSize.getMaxFontSize(this, _fontTypeface));
        _fontSizePicker.setValue(_fontSize);
    }

    private void updatePreview() {
        _preview.setTypeface(_fontTypeface, _fontStyle);
        _preview.setTextSize(_fontSize);
    }

    private void updateSelectedWeight() {
        for (MotivateMeToggleButton toggle : _fontWeightList) {
            if (!toggle.equals(_selectedToggle)) {
                toggle.setChecked(false);
            } else {
                toggle.setChecked(true);
                _fontStyle = toggle.getTypeface().getStyle();
                updatePreview();
            }
        }
    }

    @OnClick(R.id.back_button)
    public void goBack(View v) {
        finish();
    }

    @OnClick(R.id.confirm_button)
    public void confirm(View v) {
        Intent passBackIntent = new Intent();
        String assetPath = _adapter.getAssetPathFromPosition(_fontPicker.getCurrentItem());
        Serializable settings = new FontPickerResult(assetPath, _fontStyle, _fontSize);
        passBackIntent.putExtra(FONT_PICKED_EXTRA, settings);
        setResult(RESULT_OK, passBackIntent);
        finish();
    }
}
