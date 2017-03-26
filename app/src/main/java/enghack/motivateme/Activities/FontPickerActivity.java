package enghack.motivateme.Activities;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.TextView;


import com.shawnlin.numberpicker.NumberPicker;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.Adapters.FontPagerAdapter;
import enghack.motivateme.CustomViews.MotivateMeToggleButton;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/25/17.
 */

public class FontPickerActivity extends Activity {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.font_picker_activity_layout);
        ButterKnife.bind(this);

        _adapter = new FontPagerAdapter(getAssets(), getLayoutInflater());
        _fontPicker.setAdapter(_adapter);
        _fontIndicator.setViewPager(_fontPicker);
        _fontIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                _fontTypeface = _adapter.getTypefaceFromPosition(position);
                updatePreview();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        _fontSizePicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                _fontSize = newVal;
                updatePreview();
            }
        });
        initView();
    }

    private void updatePreview() {
        _preview.setTypeface(_fontTypeface, _fontStyle);
        _preview.setTextSize(_fontSize);
    }

    private void initView() {
        _fontWeightList = new ArrayList<>(Arrays.asList(_boldToggle, _normalToggle, _italicToggle));
        _selectedToggle = _normalToggle;
        for(MotivateMeToggleButton toggle : _fontWeightList){
            toggle.setOnClickListener(v -> {
                _selectedToggle = toggle;
                updateSelectedWeight();
            });
        }
        updateSelectedWeight();
    }

    private void updateSelectedWeight() {
        for(MotivateMeToggleButton toggle : _fontWeightList){
            if(!toggle.equals(_selectedToggle)){
                toggle.setChecked(false);
            }
            else{
                toggle.setChecked(true);
                _fontStyle = toggle.getTypeface().getStyle();
                updatePreview();
            }
        }
    }


}
