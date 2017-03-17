package enghack.motivateme.Activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.github.shchurov.horizontalwheelview.HorizontalWheelView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/16/17.
 */

public class RefreshIntervalPickerActivity extends Activity {
    @BindView(R.id.time_seeker)
    HorizontalWheelView _seeker;
    @BindView(R.id.time_seeker_hour_progress)
    Button _hourProgress;
    @BindView(R.id.time_seeker_minutes_progress)
    Button _minuteProgress;

    private Button _selectedButton;

    private ArrayList<Button> _buttons = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refresh_time_activity_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        setupSeeker();
        _buttons.add(_hourProgress);
        _buttons.add(_minuteProgress);
        updateSelectedButton(_hourProgress);
    }

    private void updateSelectedButton(Button toSelect){
        _selectedButton = toSelect;
        for(Button button : _buttons){
            if(button != _selectedButton){
                button.setPressed(false);
            }
            else{
                button.setPressed(true);
            }
        }
    }

    private void setupSeeker(int marks) {
        _seeker.setOnlyPositiveValues(true);
        _seeker.setEndLock(true);
        _seeker.setMarksCount(24);
        _seeker.setSnapToMarks(true);
        _seeker.setShowActiveRange(false);
        _seeker.setListener(new MyListener());
    }

    private class MyListener extends HorizontalWheelView.Listener {
        int _currProgress = -1;

        @Override
        public void onRotationChanged(double radians) {
            super.onRotationChanged(radians);
            int newProgress = (int) Math.round(24 * _seeker.getCompleteTurnFraction());
            if (_currProgress == newProgress) return;
            _currProgress = newProgress;
            _selectedButton.setText(String.valueOf(_currProgress));


        }

        @Override
        public void onScrollStateChanged(int state) {
            super.onScrollStateChanged(state);
        }
    }
}
