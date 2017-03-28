package enghack.motivateme.Models;

import java.io.Serializable;

/**
 * Created by rowandempster on 3/27/17.
 */

public class FontPickerResult implements Serializable {
    String font;
    int style;
    int size;

    public FontPickerResult(String font, int style, int size) {
        this.font = font;
        this.style = style;
        this.size = size;
    }

    public String getFont() {
        return font;
    }

    public int getStyle() {
        return style;
    }

    public int getSize() {
        return size;
    }
}
