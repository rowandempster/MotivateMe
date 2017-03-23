package enghack.motivateme.CustomViews.refresh_interval_activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by rowandempster on 3/22/17.
 */

class RefreshIntervalPickerMinutes extends RefreshIntervalPickerLabeledButton {
    public RefreshIntervalPickerMinutes(Context context) {
        this(context, null);
    }

    public RefreshIntervalPickerMinutes(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshIntervalPickerMinutes(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setButtonText("Minutes");
    }

    @Override
    public int getNumMarks() {
        return 60;
    }

    @Override
    protected long getMillisInTimeUnit() {
        return 1000*60;
    }
}
