package enghack.motivateme.CustomViews.refresh_interval_activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

/**
 * Created by rowandempster on 3/22/17.
 */

class RefreshIntervalDays extends RefreshIntervalPickerLabeledButton {
    public RefreshIntervalDays(Context context) {
        this(context, null);
    }

    public RefreshIntervalDays(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshIntervalDays(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setButtonText("Days");
    }

    @Override
    public int getNumMarks() {
        return 23;
    }

    @Override
    protected long getMillisInTimeUnit() {
        return 1000*60*60*24;
    }

}
