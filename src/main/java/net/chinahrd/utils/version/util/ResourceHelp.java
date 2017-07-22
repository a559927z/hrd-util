package net.chinahrd.utils.version.util;

import java.util.ResourceBundle;

public class ResourceHelp {
	private ResourceBundle resource = null;

	public ResourceHelp() {

	}

	public ResourceHelp(String resourceName) {
		resource = ResourceBundle.getBundle(resourceName);
	}

	public ResourceHelp(ResourceBundle resource) {
		this.resource = resource;
	}

	public String get(String key) {
		String value = "";
		try {
			value = resource.getString(key).trim();
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return value;
	}

	public String[] get(String key, String split) {

		String value = "";
		try {
			value = resource.getString(key).trim();
		} catch (Exception e) {
			return null;
		}
		return value.split(split);

	}
}
