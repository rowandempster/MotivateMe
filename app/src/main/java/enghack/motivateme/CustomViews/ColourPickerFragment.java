package enghack.motivateme.CustomViews;

import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import enghack.motivateme.Activities.SettingsActivity;
import enghack.motivateme.Database.UserPreferencesTable.UserPreferencesTableInterface;
import enghack.motivateme.R;
import enghack.motivateme.R2;

/**
 * Created by rowandempster on 3/8/17.
 */

public class ColourPickerFragment extends MotivateMeBaseFragment {

//    @BindView(R.id.color_picker_view) ColorPickerView _pickerView;
//    @BindView(R.id.color_picked_button) Button _confirmButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.colour_picker, container, false);
//        ButterKnife.bind(this, v);
        return v;
    }



//    @OnClick(R.id.color_picked_button)
//    public void writeColour(View v) {
//        getFragmentManager().popBackStack();
//        UserPreferencesTableInterface.writeTextColourAndRefreshWallpaper(_pickerView.getSelectedColor(), getActivity());
//    }
}
