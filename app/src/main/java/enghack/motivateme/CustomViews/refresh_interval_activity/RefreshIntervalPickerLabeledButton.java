package enghack.motivateme.CustomViews.refresh_interval_activity;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import enghack.motivateme.CustomViews.LabeledButton;

/**
 * Created by rowandempster on 3/22/17.
 */

public abstract class RefreshIntervalPickerLabeledButton extends LabeledButton {
    private int currMark;
    public RefreshIntervalPickerLabeledButton(Context context) {
        super(context);
    }

    public RefreshIntervalPickerLabeledButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshIntervalPickerLabeledButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract int getNumMarks();

    public int getMark(){
        return currMark;
    };

    public void setMark(int mark){
        super.setLabelText(Integer.toString(mark));
        currMark = mark;
    }

    //returns time after max amount of time unit has been extracted and set
    public long initValue(long userRefreshInterval){
        int days = ((int) (userRefreshInterval/getMillisInTimeUnit()));
        setMark(days);
        return userRefreshInterval - getMillisInTimeUnit()*days;
    };

    public abstract long getMillisInTimeUnit();

    public double getAngle() {
        return (getMark()/((double) getNumMarks()))*360;
    }
}
