package com.jon.demonstratesruntimepermission.utils;

import android.util.Log;

public class LogUtils {

	//==============================================
	//              Constants
	//==============================================

	private static final String TAG = "DEMO_RUNTIME_PERMISSION";

	//==============================================
	//              Public Static Methods
	//==============================================

	public static final void LogDebug() {
		LogDebug("");
	}

	public static final void LogDebug(String logText) {

		StackTraceElement[] stackTrace = Thread.currentThread()
		                                       .getStackTrace();
		if (stackTrace != null && stackTrace.length > 4) {

			StackTraceElement element = stackTrace[4];
			String fullClassName = element.getClassName();
			String className = fullClassName.substring(fullClassName.lastIndexOf(".") + 1); // no package

			//add class and method data to logText
			logText = "T:" + Thread.currentThread()
			                       .getId() + " | " + className + " , " + element.getMethodName() +
					"() | " + logText;
		}
		Log.d(TAG, logText);
	}
}
