package org.primesoft.common.base.tools.json;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 包装一个alibaba.fastjson对象，提供一些方便的方法
 * 
 * @author RP_S
 * @since 2016/12/20
 */
public class EasyJSON {
	private JSONObject json;

	public EasyJSON(JSONObject json) {
		this.json = json;
	}

	/**
	 * 返回原始json对象
	 */
	public JSONObject origin() {
		return json;
	}

	/**
	 * 构造一个EasyJSON对象。
	 * 
	 * @param json 原始json
	 * @return EasyJSON对象
	 */
	public static EasyJSON from(JSONObject json) {
		return new EasyJSON(json);
	}

	/**
	 * 构造一个EasyJSON对象。
	 * 
	 * @param json 原始json
	 * @return EasyJSON对象
	 */
	public static EasyJSON from(String jsonString) {
		return new EasyJSON(JSONObject.parseObject(jsonString));
	}

	@Override
	public String toString() {
		return String.valueOf(json);
	}

	/**
	 * 使用 a.b.c的形式获取属性。若结构不对或出现不可预料的异常，会打印异常并返回空
	 * 
	 * @param input js点形式的字符串
	 * @return 属性值。若"非最后一个属性"不是json对象，返回null
	 */
	public Object get(String input) {
		return get(json, input);
	}

	/**
	 * 尝试获取并转换成字符串
	 * 
	 * @see EasyJSON#get(String)
	 */
	public String getString(String input) {
		return getString(json, input);
	}

	/**
	 * 尝试获取并转换成json对象，若不是json对象，返回null
	 * 
	 * @see EasyJSON#get(String)
	 */
	public JSONObject getJSONObject(String input) {
		return getJSONObject(json, input);
	}

	/**
	 * 尝试获取并转换成jsonArray对象，若不是jsonArray对象，返回null
	 * 
	 * @see EasyJSON#get(String)
	 */
	public JSONArray getJSONArray(String input) {
		return getJSONArray(json, input);
	}

	/**
	 * 尝试获取并转换成List<String>，若不是List<String>，返回null
	 * 
	 * @see EasyJSON#get(String)
	 */
	public List<String> getStringList(String input) {
		return getStringList(json, input);
	}

	/**
	 * 使用 a.b.c的形式获取属性。若结构不对或出现不可预料的异常，会打印异常并返回空
	 * 
	 * @param input js点形式的字符串
	 * @return 属性值。若"非最后一个属性"不是json对象，返回null
	 */
	public static Object get(JSONObject json, String input) {
		try {
			if (json == null || input == null || input.length() == 0) {
				return null;
			}

			String[] arr = input.split("\\.");

			Object o = json.get(arr[0]);

			if (arr.length == 1) {
				return o;
			}

			for (int i = 1; i < arr.length; i++) {
				if (!(o instanceof JSONObject)) {
					return null;
				}
				o = ((JSONObject) o).get(arr[i]);
			}
			return o;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 尝试获取并转换成字符串
	 * 
	 * @see EasyJSON#get(String)
	 */
	public static String getString(JSONObject json, String input) {
		Object o = get(json, input);
		if (o instanceof String) {
			return (String) o;
		}
		return String.valueOf(get(json, input));
	}

	/**
	 * 尝试获取并转换成json对象，若不是json对象，返回null
	 * 
	 * @see EasyJSON#get(String)
	 */
	public static JSONObject getJSONObject(JSONObject json, String input) {
		Object o = get(json, input);
		if (o instanceof JSONObject) {
			return (JSONObject) o;
		}
		return null;
	}

	/**
	 * 尝试获取并转换成jsonArray对象，若不是jsonArray对象，返回null
	 * 
	 * @see EasyJSON#get(String)
	 */
	public static JSONArray getJSONArray(JSONObject json, String input) {
		Object o = get(json, input);
		if (o instanceof JSONArray) {
			return (JSONArray) o;
		}
		return null;
	}

	/**
	 * 尝试获取并转换成List<String>，若不是List<String>，返回null
	 * 
	 * @see EasyJSON#get(String)
	 */
	public static List<String> getStringList(JSONObject json, String input) {
		JSONArray array = getJSONArray(json, input);
		if (array == null) {
			return null;
		}
		List<String> list = new ArrayList<String>(array.size());
		for (Object o : array) {
			list.add(String.valueOf(o));
		}
		return list;
	}
}
