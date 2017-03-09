package enghack.motivateme.CustomViews;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jrummyapps.android.colorpicker.ColorPickerDialogListener;

import enghack.motivateme.R;

/**
 * Created by rowandempster on 3/8/17.
 */

public class ColourPickerFragment extends MotivateMeBaseFragment implements ColorPickerDialogListener {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.colour_picker, null);
    }

    @Override
    public void onColorSelected(int dialogId, @ColorInt int color) {

    }

    @Override
    public void onDialogDismissed(int dialogId) {

    }
}
