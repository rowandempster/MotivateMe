package enghack.motivateme.Activities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.github.shchurov.horizontalwheelview.HorizontalWheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.CustomViews.LabeledButton;
import enghack.motivateme.CustomViews.refresh_interval_activity.RefreshIntervalPickerLabeledButton;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/16/17.
 */

public class RefreshIntervalPickerActivity extends Activity {
    @BindView(R.id.time_seeker)
    HorizontalWheelView _seeker;
    @BindView(R.id.refresh_time_activity_days)
    RefreshIntervalPickerLabeledButton _days;
    @BindView(R.id.refresh_time_activity_hours)
    RefreshIntervalPickerLabeledButton _hours;
    @BindView(R.id.refresh_time_activity_minutes)
    RefreshIntervalPickerLabeledButton _minutes;
    @BindView(R.id.refresh_time_activity_seconds)
    RefreshIntervalPickerLabeledButton _seconds;

    private RefreshIntervalPickerLabeledButton _selectedButton;

    private List<RefreshIntervalPickerLabeledButton> _boxes;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_time_activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        _boxes = new ArrayList<>(Arrays.asList(_days, _hours, _minutes, _seconds));
        initTimeBoxes(1000*60*60*10);
        initSeeker();
        setSelection(_hours);
    }

    private void initSeeker() {
        _seeker.setOnlyPositiveValues(true);
        _seeker.setEndLock(true);
        _seeker.setSnapToMarks(true);
        _seeker.setShowActiveRange(false);
        _seeker.setActiveColor(getResources().getColor(R.color.colorPrimaryDark));
        _seeker.setNormaColor(getResources().getColor(R.color.colorAccent));
        _seeker.setListener(new MyListener());
    }

    private void setSelection(RefreshIntervalPickerLabeledButton box) {
        _selectedButton = box;
        selectBox(box);
        updateSeeker(box);
    }

    private void selectBox(RefreshIntervalPickerLabeledButton box) {
        for(RefreshIntervalPickerLabeledButton forBox : _boxes){
            if(!forBox.equals(box)){
                forBox.setPressedButton(false);
            }
            else{
                forBox.setPressedButton(true);
            }
        }
    }

    private void initTimeBoxes(long userRefreshTime) {
        for(RefreshIntervalPickerLabeledButton box : _boxes){
            userRefreshTime = box.initValue(userRefreshTime);
            box.setOnClickListener(view -> {
                setSelection((RefreshIntervalPickerLabeledButton) view);
            });
        }
    }

    private void updateSeeker(RefreshIntervalPickerLabeledButton box) {
        _seeker.setMarksCount(box.getNumMarks());
        _seeker.setDegreesAngle(box.getAngle());
    }

    private class MyListener extends HorizontalWheelView.Listener {
        int _currProgress = -1;

        @Override
        public void onRotationChanged(double radians) {
            super.onRotationChanged(radians);
            int newProgress = (int) Math.round(_selectedButton.getNumMarks() * _seeker.getCompleteTurnFraction());
            if (_currProgress == newProgress) return;
            _currProgress = newProgress;
            _selectedButton.setMark(_currProgress);


        }

        @Override
        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
        }
    }
}
