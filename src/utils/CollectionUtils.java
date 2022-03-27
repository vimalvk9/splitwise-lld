package utils;

import java.util.Collection;

public final class CollectionUtils {

    public static Boolean isNotEmpty(Collection col) {
        if ( col == null || col.isEmpty()) {
            return false;
        }
        return true;
    }

    public static Boolean isEmpty(Collection col) {
        if ( col == null || col.isEmpty()) {
            return true;
        }
        return false;
    }
}
