package org.biblioteca.utils;

import org.biblioteca.enums.controller.EnumGeneralController;

import java.util.ArrayList;
import java.util.List;

public class EnumControllerUtil {

    private EnumControllerUtil() {
    }

    public static String modelReference(EnumGeneralController enumOption, Object... values) {
        return String.format(enumOption.getValue(), values);
    }

    public static <T> List<T> listResponse(T modelResponse) {
        List<T> model = new ArrayList<>();
        model.add(modelResponse);
        return model;
    }

    public static <T> List<T> listResponse(List<T> modelResponse) {
        return modelResponse;
    }
}
