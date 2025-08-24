package com.bo.shirodemo.utils;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @Description:
 * @Author yb zheng
 * @Date 2025/7/9 10:02
 * @Version 1.0
 */

public class Ognl {
    /**
     * 可以用于判断String,Map,Collection,Array是否为空
     *
     * @param o
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        if (o == null)
            return true;

        if (o instanceof String) {
            if (((String) o).length() == 0) {
                return true;
            }
        } else if (o instanceof Collection) {
            if (((Collection) o).isEmpty()) {
                return true;
            }
        } else if (o.getClass().isArray()) {
            if (Array.getLength(o) == 0) {
                return true;
            }
        } else if (o instanceof Map) {
            if (((Map) o).isEmpty()) {
                return true;
            }
        } else {
            return false;
            // throw new
            // IllegalArgumentException("Illegal argument type,must be : Map,Collection,Array,String. but was:"+o.getClass());
        }

        return false;
    }

    public static boolean mapIsNotEmpty(Map<String, Object> map) {
        return !mapIsEmpty(map);
    }

    public static boolean mapIsEmpty(Map<String, Object> map) {
        boolean isEmpty = true;
        Set<String> keys = map.keySet();
        if (isNotEmpty(keys)) {
            for (String key : keys) {
                Object value = map.get(key);
                if (isNotEmpty(value)) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }

    /**
     * 可以用于判断 Map,Collection,String,Array是否不为空
     *
     * @param
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    public static boolean isNotBlank(Object o) {
        return !isBlank(o);
    }

    public static boolean isNotEquals(Object o1, Object o2) {
        return !isEquals(o1, o2);
    }

    public static boolean isEquals(Object o1, Object o2) {
        return o1.equals(o2);
    }

    public static boolean isNumber(Object o) {
        if (o == null)
            return false;
        if (o instanceof Number) {
            return true;
        }
        if (o instanceof String) {
            String str = (String) o;
            if (str.length() == 0)
                return false;
            if (str.trim().length() == 0)
                return false;

            try {
                Double.parseDouble(str);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        return false;
    }

    public static boolean isBlank(Object o) {
        if (o == null)
            return true;
        if (o instanceof String) {
            String str = (String) o;
            return isBlank(str);
        }
        return false;
    }

    public static boolean isBlank(String str) {
        if (str == null || str.length() == 0) {
            return true;
        }

        for (int i = 0; i < str.length(); i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isZero(String str) {
        if (StringUtils.equals("0", str) || StringUtils.equals("00", str)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isNotZero(String str) {
        if (str != null && str.trim().length() > 0 && !("0".equals(str))
                && !("00".equals(str))) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAirport(String type) {
        if (type == null) {
            return true;
        } else if (type.equals("0")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCity(String type) {
        if (type == null) {
            return false;
        } else if (type.equals("2")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCountry(String type) {
        if (type == null) {
            return false;
        } else if (type.equals("4")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isContinent(String type) {
        if (type == null) {
            return false;
        } else if (type.equals("5")) {
            return true;
        } else {
            return false;
        }
    }
}
