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

		// Full logging method used in class is available -
		// https://gist.github.com/dors/4651b8ecff2f76c012ae
		// Give a STAR to the developer :)

		Log.d(TAG, logText);
	}
}
