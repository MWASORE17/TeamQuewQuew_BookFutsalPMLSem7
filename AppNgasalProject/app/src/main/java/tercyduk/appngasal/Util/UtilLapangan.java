package tercyduk.appngasal.Util;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.util.ArrayList;
import java.util.List;

import tercyduk.appngasal.coresmodel.LapanganFutsal;

/**
 * Created by Tommy Aditya on 12/27/2017.
 */

public class UtilLapangan {
    /**
     * Force hide keyboard if open
     */
    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (imm != null && view != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Filtering followers by search char sequence
     * @param list source follower list
     * @param charString searching char sequence
     * @return filtered follower list
     */
    public static List<LapanganFutsal> searchLapanganFutsalFilter(List<LapanganFutsal> list, String charString) {
        List<LapanganFutsal> filteredTempList = new ArrayList<>();
        for (LapanganFutsal lapanganFutsal: list) {
            if (lapanganFutsal != null ) {
                // Filter by user name and user id
                if (containsIgnoreCase(lapanganFutsal.getFutsal_name(), charString) ||
                        containsIgnoreCase(lapanganFutsal.getDistricts(), charString))
                {
                    filteredTempList.add(lapanganFutsal);
                }
            }
        }
        return filteredTempList;
    }

    /**
     * Search if substring has char sequence in source string ignore case
     * @param src source string
     * @param charString substring for searching
     * @return true if has coincidence
     */
    public static boolean containsIgnoreCase(String src, String charString) {
        final int length = charString.length();
        if (length == 0) {
            return true;
        }
        for (int i = src.length() - length; i >= 0; i--) {
            if (src.regionMatches(true, i, charString, 0, length)) {
                return true;
            }
        }
        return false;
    }
}
