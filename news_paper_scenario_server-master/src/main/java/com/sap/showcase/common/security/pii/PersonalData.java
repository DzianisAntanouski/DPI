package com.sap.showcase.common.security.pii;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, METHOD, PARAMETER, TYPE})
@Retention(RUNTIME)
public @interface PersonalData {    
    public enum PersInfoType {
		PII,//Identifiable 
		SPI,//Sensitive
		RPI //Related
	}
	public enum LogType {
		Read, //Read Access Log 
		Change//Change Log 
	}
	public enum MaskMode {
		On,  
		Off 
	}
	PersInfoType PIType() default PersInfoType.SPI; 
	LogType LogType() default LogType.Read;
	MaskMode MaskMode() default MaskMode.Off;
}
