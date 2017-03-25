package enghack.motivateme.CustomViews.refresh_interval_activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by rowandempster on 3/22/17.
 */

class RefreshIntervalHours extends RefreshIntervalPickerLabeledButton {
    public RefreshIntervalHours(Context context) {
        this(context, null);
    }

    public RefreshIntervalHours(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshIntervalHours(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setButtonText("Hours");
    }

    @Override
    public int getNumMarks() {
        return 24;
    }

    @Override
    public long getMillisInTimeUnit() {
        return 1000*60*60;
    }
}
