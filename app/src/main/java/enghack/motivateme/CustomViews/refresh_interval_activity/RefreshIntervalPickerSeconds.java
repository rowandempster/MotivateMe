package enghack.motivateme.CustomViews.refresh_interval_activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by rowandempster on 3/22/17.
 */

class RefreshIntervalPickerSeconds extends RefreshIntervalPickerLabeledButton {
    public RefreshIntervalPickerSeconds(Context context) {
        this(context, null);
    }

    public RefreshIntervalPickerSeconds(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshIntervalPickerSeconds(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setButtonText("Seconds");
    }

    @Override
    public int getNumMarks() {
        return 60;
    }

    @Override
    public long getMillisInTimeUnit() {
        return 1000;
    }
}
