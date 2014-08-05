package repository.util;

public class UrlUtil {

	/**
	 * 方法说明: 在完整的url路径中截取当前执行的Action名称
	 * @param
	 * @author E-mail:
	 * @return
	 * @date 2010-1-24 ����09:37:15
	 */
	public static String getAction(String url) {
		if (url.lastIndexOf(".") != -1) {
			return url
					.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
		}
		return "";
	}
}
