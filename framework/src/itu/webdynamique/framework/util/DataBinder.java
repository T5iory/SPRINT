package itu.webdynamique.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;

import itu.webdynamique.framework.MapFile;
import itu.webdynamique.framework.annotation.RequestParam;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

public class DataBinder {

    public static Object[] resolveMethodArguments(Method method, HttpServletRequest request) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        for (int i = 0; i < parameters.length; i++) {
            Parameter param = parameters[i];
            Class<?> type = param.getType();

            if (type.isAssignableFrom(HttpServletRequest.class)) {
                args[i] = request;
            } else if (type.isAssignableFrom(HttpSession.class)) {
                args[i] = request.getSession();
            } else if (type.isAssignableFrom(ServletContext.class)) {
                args[i] = request.getServletContext();
            } else if (isSimpleType(type)) {
                String paramName = getParamName(param);
                String rawValue = request.getParameter(paramName);
                args[i] = convertSimpleType(rawValue, type);
            } else if (type.equals(MapFile.class)) {
                String paramName = getParamName(param);
                args[i] = buildMapFile(paramName, request);
            } else {
                String paramName = getParamName(param);
                args[i] = bindObject(type, paramName, request);
            }
        }

        return args;
    }

    public static void bindControllerAttributes(Object controller, HttpServletRequest request) {
        if (controller == null || request == null) {
            return;
        }

        Field[] fields = controller.getClass().getDeclaredFields();
        for (Field field : fields) {
            String paramName = field.getName();
            String rawValue = request.getParameter(paramName);

            if (rawValue != null) {
                try {
                    Object convertedValue = convertSimpleType(rawValue, field.getType());
                    setProperty(controller, field.getName(), convertedValue);
                } catch (Exception ignored) {
                }
            } else if (!isSimpleType(field.getType()) && !field.getType().isPrimitive()) {
                try {
                    Object boundObj = bindObject(field.getType(), field.getName(), request);
                    if (boundObj != null) {
                        setProperty(controller, field.getName(), boundObj);
                    }
                } catch (Exception ignored) {
                }
            }
        }
    }

    public static Object bindObject(Class<?> clazz, String prefix, HttpServletRequest request) throws Exception {
        Object instance = clazz.getDeclaredConstructor().newInstance();
        boolean hasAnyPropertySet = false;

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            Class<?> fieldType = field.getType();

            if (isSimpleType(fieldType)) {
                String rawVal = null;
                if (prefix != null && !prefix.isEmpty()) {
                    rawVal = request.getParameter(prefix + "." + fieldName);
                }
                if (rawVal == null) {
                    rawVal = request.getParameter(fieldName);
                }

                if (rawVal != null) {
                    Object value = convertSimpleType(rawVal, fieldType);
                    setProperty(instance, fieldName, value);
                    hasAnyPropertySet = true;
                }
            } else if (fieldType.equals(MapFile.class)) {
                String partName = fieldName;
                if (prefix != null && !prefix.isEmpty()) {
                    MapFile mf = buildMapFile(prefix + "." + fieldName, request);
                    if (mf == null) {
                        mf = buildMapFile(fieldName, request);
                    }
                    if (mf != null) {
                        setProperty(instance, fieldName, mf);
                        hasAnyPropertySet = true;
                    }
                } else {
                    MapFile mf = buildMapFile(fieldName, request);
                    if (mf != null) {
                        setProperty(instance, fieldName, mf);
                        hasAnyPropertySet = true;
                    }
                }
            } else {
                String subPrefix = (prefix != null && !prefix.isEmpty()) ? prefix + "." + fieldName : fieldName;
                Object subObj = bindObject(fieldType, subPrefix, request);
                if (subObj != null) {
                    setProperty(instance, fieldName, subObj);
                    hasAnyPropertySet = true;
                }
            }
        }

        return instance;
    }

    public static void setProperty(Object target, String propertyName, Object value) {
        if (target == null) return;

        String setterName = "set" + Character.toUpperCase(propertyName.charAt(0)) + propertyName.substring(1);
        Class<?> clazz = target.getClass();

        for (Method m : clazz.getMethods()) {
            if (m.getName().equals(setterName) && m.getParameterCount() == 1) {
                try {
                    m.invoke(target, value);
                    return;
                } catch (Exception ignored) {
                }
            }
        }

        try {
            Field field = clazz.getDeclaredField(propertyName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (Exception ignored) {
        }
    }

    private static String getParamName(Parameter param) {
        if (param.isAnnotationPresent(RequestParam.class)) {
            RequestParam rp = param.getAnnotation(RequestParam.class);
            if (rp.value() != null && !rp.value().trim().isEmpty()) {
                return rp.value().trim();
            }
        }
        return param.getName();
    }

    public static boolean isSimpleType(Class<?> type) {
        return type.isPrimitive()
                || type.equals(String.class)
                || type.equals(Integer.class)
                || type.equals(Long.class)
                || type.equals(Double.class)
                || type.equals(Float.class)
                || type.equals(Boolean.class)
                || type.equals(Byte.class)
                || type.equals(Short.class)
                || type.equals(Character.class)
                || Date.class.isAssignableFrom(type)
                || type.equals(LocalDate.class)
                || type.equals(LocalDateTime.class);
    }

    public static Object convertSimpleType(String val, Class<?> targetType) {
        if (val == null) {
            if (targetType.isPrimitive()) {
                if (targetType.equals(boolean.class)) return false;
                if (targetType.equals(byte.class)) return (byte) 0;
                if (targetType.equals(short.class)) return (short) 0;
                if (targetType.equals(int.class)) return 0;
                if (targetType.equals(long.class)) return 0L;
                if (targetType.equals(float.class)) return 0.0f;
                if (targetType.equals(double.class)) return 0.0d;
                if (targetType.equals(char.class)) return '\0';
            }
            return null;
        }

        val = val.trim();

        if (targetType.equals(String.class)) {
            return val;
        }

        if (val.isEmpty()) {
            if (targetType.isPrimitive()) {
                return convertSimpleType(null, targetType);
            }
            return null;
        }

        if (targetType.equals(int.class) || targetType.equals(Integer.class)) {
            return Integer.parseInt(val);
        }
        if (targetType.equals(long.class) || targetType.equals(Long.class)) {
            return Long.parseLong(val);
        }
        if (targetType.equals(double.class) || targetType.equals(Double.class)) {
            return Double.parseDouble(val);
        }
        if (targetType.equals(float.class) || targetType.equals(Float.class)) {
            return Float.parseFloat(val);
        }
        if (targetType.equals(boolean.class) || targetType.equals(Boolean.class)) {
            return Boolean.parseBoolean(val) || "1".equals(val) || "on".equalsIgnoreCase(val);
        }
        if (targetType.equals(byte.class) || targetType.equals(Byte.class)) {
            return Byte.parseByte(val);
        }
        if (targetType.equals(short.class) || targetType.equals(Short.class)) {
            return Short.parseShort(val);
        }
        if (targetType.equals(char.class) || targetType.equals(Character.class)) {
            return val.charAt(0);
        }

        if (targetType.equals(Date.class)) {
            String[] formats = new String[] {
                    "yyyy-MM-dd",
                    "yyyy-MM-dd'T'HH:mm",
                    "yyyy-MM-dd HH:mm:ss",
                    "dd/MM/yyyy"
            };
            for (String f : formats) {
                try {
                    return new SimpleDateFormat(f).parse(val);
                } catch (Exception ignored) {
                }
            }
            return null;
        }

        if (targetType.equals(LocalDate.class)) {
            try {
                return LocalDate.parse(val);
            } catch (Exception ignored) {
            }
        }

        if (targetType.equals(LocalDateTime.class)) {
            try {
                return LocalDateTime.parse(val);
            } catch (Exception ignored) {
            }
        }

        return val;
    }

    public static MapFile buildMapFile(String partName, HttpServletRequest request) {
        try {
            Part part = request.getPart(partName);
            if (part == null || part.getSize() <= 0) {
                return null;
            }

            String submittedFileName = part.getSubmittedFileName();
            if (submittedFileName == null || submittedFileName.trim().isEmpty()) {
                return null;
            }

            String nom = submittedFileName;
            String extension = "";
            int dotIndex = submittedFileName.lastIndexOf('.');
            if (dotIndex >= 0) {
                extension = submittedFileName.substring(dotIndex + 1);
                nom = submittedFileName.substring(0, dotIndex);
            }

            byte[] binaire;
            try (InputStream is = part.getInputStream();
                 ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = is.read(buffer)) != -1) {
                    bos.write(buffer, 0, bytesRead);
                }
                binaire = bos.toByteArray();
            }

            MapFile mapFile = new MapFile();
            mapFile.setNom(nom);
            mapFile.setExtension(extension);
            mapFile.setBinaire(binaire);
            return mapFile;

        } catch (Exception e) {
            return null;
        }
    }
}
