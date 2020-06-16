package cn.common.util.redis;

import cn.common.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 */
@Component
public  class RedisUtil {
	private static Logger log = LoggerFactory.getLogger(RedisUtil.class);
	private static RedisUtil redisUtil;
	@Autowired
	private RedisTemplate<Object, Object> redisTemplate;
	/**
	 * 初始化 init
	 */
	@PostConstruct
	public void init(){
		redisUtil = this;
		redisUtil.redisTemplate = this.redisTemplate;
	}

	// =============================pojo============================
	/**
	 * 指定缓存失效时间
	 * @param key 键
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean expire(String key, long time) {
		try {
			if (time > 0) {
				redisUtil.redisTemplate.expire(key, time, TimeUnit.SECONDS);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据key 获取过期时间
	 * @param key 键 不能为null
	 * @return 时间(秒) 返回0代表为永久有效
	 */
	public static long getExpire(String key) {
		return redisUtil.redisTemplate.getExpire(key, TimeUnit.SECONDS);
	}
	/**
	 * 判断key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public static boolean hasKey(String key) {
		try {
			return redisUtil.redisTemplate.hasKey(key);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 删除缓存
	 * @param key 可以传一个值 或多个
	 */
	@SuppressWarnings("unchecked")
	public static void del(String... key) {
		if (key != null && key.length > 0) {
			if (key.length == 1) {
				redisUtil.redisTemplate.delete(key[0]);
			} else {
				redisUtil.redisTemplate.delete(CollectionUtils.arrayToList(key));
			}
		}
	}
	// ============================String=============================
	/**
	 * 普通缓存获取
	 * @param key 键
	 * @return 值
	 */
	public static Object get(String key) {
		return key == null ? null : redisUtil.redisTemplate.opsForValue().get(key);
	}

	public static String get(String key,String value) {
		if(key==null){
			return value;
		}else {
			Object o=redisUtil.redisTemplate.opsForValue().get(key);
			if(o==null){
				return value;
			}else {
				return o.toString();
			}
		}
	}

	/**
	 * 普通缓存放入
	 * @param key 键
	 * @param value 值
	 * @return true成功 false失败
	 */
	public static boolean set(String key, Object value) {
		try {
			redisUtil.redisTemplate.opsForValue().set(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 普通缓存放入并设置时间
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒) time要大于0 如果time小于等于0 将设置无限期
	 * @return true成功 false 失败
	 */
	public static boolean set(String key, Object value, long time) {
		try {
			if (time > 0) {
				redisUtil.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
			} else {
				set(key, value);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 递增
	 * @param key 键
	 * @param delta 要增加几(大于0)
	 * @return
	 */
	public static long incr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递增因子必须大于0");
		}
		return redisUtil.redisTemplate.opsForValue().increment(key, delta);
	}
	/**
	 * 递减
	 * @param key 键
	 * @param delta 要减少几(小于0)
	 * @return
	 */
	public static long decr(String key, long delta) {
		if (delta < 0) {
			throw new RuntimeException("递减因子必须大于0");
		}
		return redisUtil.redisTemplate.opsForValue().increment(key, -delta);
	}
	// ================================Map=================================
	/**
	 * HashGet
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return 值
	 */
	public static Object hget(String key, String item) {
		return redisUtil.redisTemplate.opsForHash().get(key, item);
	}
	/**
	 * 获取hashKey对应的所有键值
	 * @param key 键
	 * @return 对应的多个键值
	 */
	public static Map<Object, Object> hmget(String key) {
		return redisUtil.redisTemplate.opsForHash().entries(key);
	}
	/**
	 * HashSet
	 * @param key 键
	 * @param map 对应多个键值
	 * @return true 成功 false 失败
	 */
	public static boolean hmset(String key, Map<String, Object> map) {
		try {
			redisUtil.redisTemplate.opsForHash().putAll(key, map);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * HashSet 并设置时间
	 * @param key 键
	 * @param map 对应多个键值
	 * @param time 时间(秒)
	 * @return true成功 false失败
	 */
	public static boolean hmset(String key, Map<String, Object> map, long time) {
		try {
			redisUtil.redisTemplate.opsForHash().putAll(key, map);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 向一张hash表中放入数据,如果不存在将创建
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @return true 成功 false失败
	 */
	public static boolean hset(String key, String item, Object value) {
		try {
			redisUtil.redisTemplate.opsForHash().put(key, item, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
229
	 * 向一张hash表中放入数据,如果不存在将创建
230
	 * @param key 键
	 * @param item 项
	 * @param value 值
	 * @param time 时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
	 * @return true 成功 false失败
	 */
	public static boolean hset(String key, String item, Object value, long time) {
		try {
			redisUtil.redisTemplate.opsForHash().put(key, item, value);
			if (time > 0) {
				expire(key, time);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
250
	 * 删除hash表中的值
	 * @param key 键 不能为null
	 * @param item 项 可以使多个 不能为null
	 */
	public static void hdel(String key, Object... item) {
		redisUtil.redisTemplate.opsForHash().delete(key, item);
	}
	/**
	 * 判断hash表中是否有该项的值
	 * @param key 键 不能为null
	 * @param item 项 不能为null
	 * @return true 存在 false不存在
	 */
	public static boolean hHasKey(String key, String item) {
		return redisUtil.redisTemplate.opsForHash().hasKey(key, item);
	}
	/**
	 * hash递增 如果不存在,就会创建一个 并把新增后的值返回
	 * @param key 键
	 * @param item 项
	 * @param by 要增加几(大于0)
	 * @return
	 */
	public static double hincr(String key, String item, double by) {
		return redisUtil.redisTemplate.opsForHash().increment(key, item, by);
	}
	/**
	 * hash递减
	 * @param key 键
	 * @param item 项
	 * @param by 要减少记(小于0)
	 * @return
	 */
	public static double hdecr(String key, String item, double by) {
		return redisUtil.redisTemplate.opsForHash().increment(key, item, -by);
	}
	// ============================set=============================
	/**
	 * 根据key获取Set中的所有值
	 * @param key 键
	 * @return
	 */
	public static Set<Object> sGet(String key) {
		try {
			return redisUtil.redisTemplate.opsForSet().members(key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 根据value从一个set中查询,是否存在
	 * @param key 键
	 * @param value 值
	 * @return true 存在 false不存在
	 */
	public static boolean sHasKey(String key, Object value) {
		try {
			return redisUtil.redisTemplate.opsForSet().isMember(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 将数据放入set缓存
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public static long sSet(String key, Object... values) {
		try {
			return redisUtil.redisTemplate.opsForSet().add(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 将set数据放入缓存
	 * @param key 键
	 * @param time 时间(秒)
	 * @param values 值 可以是多个
	 * @return 成功个数
	 */
	public static long sSetAndTime(String key, long time, Object... values) {
		try {
			Long count = redisUtil.redisTemplate.opsForSet().add(key, values);
			if (time > 0)
				expire(key, time);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 获取set缓存的长度
	 * @param key 键
	 * @return
	 */
	public static long sGetSetSize(String key) {
		try {
			return redisUtil.redisTemplate.opsForSet().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 移除值为value的
	 * @param key 键
	 * @param values 值 可以是多个
	 * @return 移除的个数
	 */
	public static long setRemove(String key, Object... values) {
		try {
			Long count = redisUtil.redisTemplate.opsForSet().remove(key, values);
			return count;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	// ===============================list=================================
	/**
	 * 获取list缓存的内容
	 * @param key 键
	 * @param start 开始
	 * @param end 结束 0 到 -1代表所有值
	 * @return
	 */
	public static List<Object> lGet(String key, long start, long end) {
		try {
			return redisUtil.redisTemplate.opsForList().range(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 获取list缓存的长度
	 * @param key 键
	 * @return
	 */
	public static long lGetListSize(String key) {
		try {
			return redisUtil.redisTemplate.opsForList().size(key);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	/**
	 * 通过索引 获取list中的值
	 * @param key 键
	 * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
	 * @return
	 */
	public static Object lGetIndex(String key, long index) {
		try {
			return redisUtil.redisTemplate.opsForList().index(key, index);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static boolean lSet(String key, Object value) {
		try {
			redisUtil.redisTemplate.opsForList().rightPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 左进右出
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean lLeftPushAndRightPop(String key, Object value) {
		try {
			redisUtil.redisTemplate.opsForList().rightPopAndLeftPush(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * 左边push
	 * @param key
	 * @param value
	 * @return
	 */
	public static boolean lLeftPush(String key, Object value) {
		try {
			redisUtil.redisTemplate.opsForList().leftPush(key,value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean lSet(String key, Object value, long time) {
		try {
			redisUtil.redisTemplate.opsForList().rightPush(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 将list放入缓存
	 * @param key 键
	 * @param value 值
	 * @return
	 */
	public static boolean lSet(String key, List<Object> value) {
		try {
			redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 将list放入缓存
	 * 
	 * @param key 键
	 * @param value 值
	 * @param time 时间(秒)
	 * @return
	 */
	public static boolean lSet(String key, List<Object> value, long time) {
		try {
			redisUtil.redisTemplate.opsForList().rightPushAll(key, value);
			if (time > 0)
				expire(key, time);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 根据索引修改list中的某条数据
	 * @param key 键
	 * @param index 索引
	 * @param value 值
	 * @return
	 */
	public static boolean lUpdateIndex(String key, long index, Object value) {
		try {
			redisUtil.redisTemplate.opsForList().set(key, index, value);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * 移除N个值为value
	 * @param key 键
	 * @param count 移除多少个
	 * @param value 值
	 * @return 移除的个数
	 */
	public static long lRemove(String key, long count, Object value) {
		try {
			Long remove = redisUtil.redisTemplate.opsForList().remove(key, count, value);
			return remove;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 根据实体类 获取 redis key
	 * @param classDO 实体类
	 * @param cloum  要存的实体类 列名
	 */
	public static <T> String getKey(T classDO,String cloum) {
		StringBuffer keyBuffer=new StringBuffer();
		try {
			String lowerClassString= StringUtils.getClassNameToLower(classDO);
			Field[] fields = classDO.getClass().getDeclaredFields();   
			Field pkField=fields[0];
			String pk=pkField.getName();
			Object pkValue=getFieldValueByName(pk, classDO);
			keyBuffer.append(lowerClassString).append(":")
			.append(pk.toLowerCase()).append(":").append(pkValue).append(":").append(cloum);
			/*for (Field field : fields) {
				String filedName=field.getName();
				if(filedName.equalsIgnoreCase(cloum)) {
					keyBuffer.append(getFieldValueByName(filedName, classDO));
					break;
				}
			}*/
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return keyBuffer.toString();
	}

	/**
	 * 根据实体类 获取 redis key
	 */
	public static <T> String getKey(String method,T classDO) {
		StringBuffer keyBuffer=new StringBuffer();
		try {
			Field[] fields = classDO.getClass().getDeclaredFields();   
			Field pkField=fields[0];
			String pk=pkField.getName();
			Object pkValue=getFieldValueByName(pk, classDO);
			keyBuffer.append(method).append(":")
			.append(pk.toLowerCase()).append(":").append(pkValue);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return keyBuffer.toString();
	}

	/**
	 * 根据实体类 获取 redis key
	 */
	public static <T> String getKey(String method,String pkValue) {
		StringBuffer keyBuffer=new StringBuffer();
		try {
			keyBuffer.append(method.toLowerCase()).append(":").append(pkValue);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

		return keyBuffer.toString();
	}



	/** 
	 * 根据属性名获取属性值 
	 * */  
	private static  Object getFieldValueByName(String fieldName, Object o) {  
		try {    
			String firstLetter = fieldName.substring(0, 1).toUpperCase();    
			String getter = "get" + firstLetter + fieldName.substring(1);    
			Method method = o.getClass().getMethod(getter, new Class[] {});    
			Object value = method.invoke(o, new Object[] {});    
			return value;    
		} catch (Exception e) {    
			return null;    
		}    
	}

	/**
	 * setnx 的含义就是 SET if Not Exists，其主要有两个参数 setnx(key, value)。该方法是原子的，如果 key 不存在，
	 * 则设置当前 key 成功，返回 true；如果当前 key 已经存在，则设置当前 key 失败，返回 false。
	 * @param key
	 * @param value
	 * @return
	 */

	public static Boolean setNx(final String key, final String value) {
		Boolean b = false;
		try {
			b = redisUtil.redisTemplate.execute((final RedisConnection c) -> {
				final StringRedisSerializer serializer = new StringRedisSerializer();
				final Boolean success = c.setNX(serializer.serialize(key), serializer.serialize(value));
				c.close();
				return success;
			});
		} catch (Exception e) {
			log.error("setNX redis error, key : {}", key);
		}
		return b;
	}

	/**
	 * 这个命令主要有两个参数 getset(key，newValue)。该方法是原子的，对 key 设置 newValue 这个值，
	 * 并且返回 key 原来的旧值。假设 key 原来是不存在的，那么多次执行这个命令，会出现下边的效果：
	 * getset(key, “value1”) 返回 null 此时 key 的值会被设置为 value1
	 * getset(key, “value2”) 返回 value1 此时 key 的值会被设置为 value2
	 * 依次类推！
	 * @param key
	 * @param value
	 * @return
	 */
	public static String getSet(final String key, final String value) {
		String obj = null;
		try {
			obj =redisUtil.redisTemplate.execute((final RedisConnection c) -> {
				final StringRedisSerializer serializer = new StringRedisSerializer();
				final byte[] ret = c.getSet(serializer.serialize(key), serializer.serialize(value));
				c.close();
				return serializer.deserialize(ret);
			});
		} catch (Exception ex) {
			log.error("setNX redis error, key : {}", key);
		}
		return obj;
	}
}