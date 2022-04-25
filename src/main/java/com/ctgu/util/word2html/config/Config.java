package com.ctgu.util.word2html.config;

import com.ctgu.common.Const;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class Config {
	private final static String CONFIG_PROPERTY_FILENAME = "application.properties";

	private static Properties properties;

	public static String DOC_BASE_DIR;
	public static String DOC_IMAGE_DIR;

	static {
		try {
			loadConfig();

			DOC_BASE_DIR = get("baseDir");
			DOC_IMAGE_DIR = DOC_BASE_DIR + "image";

		} catch (IOException e) {
			throw new IllegalStateException("load message resource " + CONFIG_PROPERTY_FILENAME + " error", e);
		}
	}

	private static void loadConfig() throws IOException {
		if (properties != null) {
			return;
		}
		properties = new Properties();
		properties.load(getInputStream());
	}

	/**
	 * 加载资源文件
	 * 
	 * @return
	 */
	private static InputStream getInputStream() {

		// 从当前类加载器中加载资源
		InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_PROPERTY_FILENAME);
		if (is != null) {
			return is;
		}

		// 从系统类加载器中加载资源
		is = ClassLoader.getSystemResourceAsStream(CONFIG_PROPERTY_FILENAME);
		if (is != null) {
			return is;
		}
		throw new IllegalStateException("cannot find " + CONFIG_PROPERTY_FILENAME + " anywhere");
	}

	public static String get(String name) {
		return Const.UPLOAD_FILE_HTML_PATH;
//		return properties.getProperty(name);
	}

	public static String getDir(String folder) {
		return Config.DOC_BASE_DIR + FormartUtil.getDateFolder() + "/" + folder;
	}

	public static String getPath() {
		return Config.DOC_BASE_DIR;
	}

	static class FormartUtil {
		private static final SimpleDateFormat dfm = new SimpleDateFormat("yyyy/M/d");

		protected static String getDateFolder() {
			return dfm.format(new Date());
		}
	}
}