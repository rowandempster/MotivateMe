package enghack.motivateme.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;

import com.github.shchurov.horizontalwheelview.HorizontalWheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.CustomViews.refresh_interval_activity.RefreshIntervalPickerLabeledButton;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/16/17.
 */

public class RefreshIntervalPickerActivity extends Activity {
    public static final String TIME_PICKED_EXTRA = "time";

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
    @BindView(R.id.refresh_time_activity_root)
    LinearLayout _root;

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
        initTimeBoxes(UserPreferencesTableInterface.readRefreshInterval());
        initSeeker();
        for (RefreshIntervalPickerLabeledButton box : _boxes) {
            if (box.getMark() > 0) {
                setSelection(box);
                break;
            }
        }
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
        for (RefreshIntervalPickerLabeledButton forBox : _boxes) {
            if (!forBox.equals(box)) {
                forBox.setPressedButton(false);
            } else {
                forBox.setPressedButton(true);
            }
        }
    }

    private void initTimeBoxes(long userRefreshTime) {
        for (RefreshIntervalPickerLabeledButton box : _boxes) {
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

    @OnClick(R.id.back_button)
    public void goBack(View v) {
        finish();
    }

    @OnClick(R.id.confirm_button)
    public void setTime(View v) {
        Intent passBack = new Intent();
        passBack.putExtra(TIME_PICKED_EXTRA, getCurrentTime());
        setResult(RESULT_OK, passBack);
        finish();
    }

    private long getCurrentTime() {
        long ret = -1;
        for (RefreshIntervalPickerLabeledButton box : _boxes) {
            ret += box.getMark() * box.getMillisInTimeUnit();
        }
        return ret + 1;
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
            if (_days.getMark() == 0 && _hours.getMark() == 0 && _minutes.getMark() < 5) {
                Snackbar snackbar = Snackbar.make(_root, "Please choose an interval over 5 minutes", Snackbar.LENGTH_LONG);
                snackbar.show();
                _minutes.setMark(5);
                _seeker.setDegreesAngle(360*(1/(double) 11));
            }

        }

        @Override
        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
        }
    }
}
