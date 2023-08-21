package com.sap.showcase.common.security.masking;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@Service
public class MaskingServiceImpl implements MaskingService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void maskDataAccess(List<?> listObj) {
        for (Object obj : listObj) {
            Class[] cArg = new Class[1];
            cArg[0] = String.class;
            for (Field fld : obj.getClass().getDeclaredFields()) {
                if (fld.getType().getSimpleName().equals("String")) {
                    String fldName = fld.getName();
                    String nameMatcher = "set" + fldName.toUpperCase().charAt(0) + fldName.substring(1, fldName.length());
                    try {
                        Method mthd = obj.getClass().getMethod(nameMatcher, cArg);
                        try {
                            mthd.invoke(obj, "****");
                        } catch (IllegalAccessException e) {
                            logger.error("Error in masking " + e.getMessage());
                        } catch (IllegalArgumentException e) {
                            logger.error("Error in masking " + e.getMessage());
                        } catch (InvocationTargetException e) {
                            logger.error("Error in masking " + e.getMessage());
                        }
                    } catch (NoSuchMethodException e) {
                        logger.error("Error in masking " + e.getMessage());
                    } catch (SecurityException e) {
                        logger.error("Error in masking " + e.getMessage());
                    }
                }
            }
        }
    }
}

