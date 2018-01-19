package tercyduk.appngasal.modules.auth.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by User on 12/1/2017.
 */

public class AuthUser {
    public static boolean isemailvalid(String email) {
        String _expression = "^[a-z]([a-z0-9-\\.]+)?+@[a-z]+\\.[a-z]{2,4}+(\\.[a-z]{2,4})?$";
        CharSequence _email = email;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_email);
        if (_mathcer.matches()) {
            return true;}return false;}
    public static boolean ispasswordvalid(String password) {
        String _expression = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[\\d])(?=.*[@!\\*?$&\\^#-])[\\w@!\\*?$&\\^#-]{8,}$";
        CharSequence _password = password;
        Pattern _pattern = Pattern.compile(_expression, Pattern.CASE_INSENSITIVE);
        Matcher _mathcer = _pattern.matcher(_password);
        if (_mathcer.matches()) {return true;}
        return false;}
}
