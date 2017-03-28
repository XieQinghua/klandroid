package org.thvc.happyi.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.security.MessageDigest;

public class MD5 {

	private MD5() {}
	
	public final static String getMessageDigest(byte[] buffer) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(buffer);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取应用的MD5签名方法
	 * @param context
	 * @param packageName
	 * @return
	 */
	public static String getSign(Context context, String packageName) {
		Signature[] signs = getRawSignature(context, packageName);
		if ((signs == null) || (signs.length == 0)) {
			return null;
		} else {
			Signature sign = signs[0];
			String signMd5 = MD5.getMessageDigest(sign.toByteArray());
			return signMd5;
		}
	}


	public static Signature[] getRawSignature(Context context, String packageName) {
		if ((packageName == null) || (packageName.length() == 0)) {
			return null;
		}
		PackageManager pkgMgr = context.getPackageManager();
		PackageInfo info = null;
		try {
			info = pkgMgr.getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
		} catch (PackageManager.NameNotFoundException e) {
			return null;
		}
		if (info == null) {
			return null;
		}
		return info.signatures;
	}
}
