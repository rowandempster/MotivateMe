package enghack.motivateme.Util;

/**
 * Created by rowandempster on 2/27/17.
 */

public class StringUtils {
    public static boolean isNullOrEmpty(String string){
        if(string == null){
            return true;
        }
        if("".equals(string)){
            return true;
        }
        return false;
    }
}
