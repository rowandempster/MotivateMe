package enghack.motivateme.CustomViews;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/13/17.
 */

public class MaterialDesignTextView extends TextView {
    public static final Map<Integer, String> FONT_MAP = new HashMap<Integer, String>()
    {{
        put(0, "Roboto-Black.ttf");
        put(1, "Roboto-BlackItalic.ttf");
        put(2, "Roboto-Bold.ttf");
        put(3, "Roboto-BoldItalic.ttf");
        put(4, "Roboto-Italic.ttf");
        put(5, "Roboto-Light.ttf");
        put(6, "Roboto-LightItalic.ttf");
        put(7, "Roboto-Medium.ttf");
        put(8, "Roboto-MediumItalic.ttf");
        put(9, "Roboto-Regular.ttf");
        put(10, "Roboto-Thin.ttf");
        put(11, "Roboto-ThinItalic.ttf");
        put(12, "RobotoCondensed-Bold.ttf");
        put(13, "RobotoCondensed-BoldItalic.ttf");
        put(14, "RobotoCondensed-Italic.ttf");
        put(15, "RobotoCondensed-Light.ttf");
        put(16, "RobotoCondensed-LightItalic.ttf");
        put(17, "RobotoCondensed-Regular.ttf");
    }};
    public MaterialDesignTextView(Context context) {
        this(context, null);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.MaterialDesignTextView);
        String font = FONT_MAP.get(array.getInt(R.styleable.MaterialDesignTextView_roboto_font, 9));
        Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/roboto/"+font);
        setTypeface(typeface);
    }

    public MaterialDesignTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialDesignTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }
}
