package enghack.motivateme.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by rowandempster on 3/13/17.
 */

public class MaterialDesignTextView extends TextView {
    public MaterialDesignTextView(Context context) {
        super(context);
        initView();
    }

    private void initView() {

    }

    public MaterialDesignTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MaterialDesignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }
}
