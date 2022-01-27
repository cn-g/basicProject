package com.gcp.basicproject.util;

import org.apache.commons.lang3.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * redis工具类
 * @author Admin
 */
public class RedisUtil {

    private static final Logger log = LoggerFactory.getLogger(RedisUtil.class);

    private RedisTemplate<String, Object> redisTemplate;


    public RedisUtil() {
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public RedisTemplate<String, Object> getRedisTemplate() {
        return this.redisTemplate;
    }

    /**
     * 监听key
     * @param key
     */
    public void watch(String key){
        redisTemplate.watch(key);
    }

    /**
     * 监听多个key
     * @param key
     */
    public void watch(String... key){
        redisTemplate.watch(Arrays.asList(key));
    }

    /**
     * 开启事务
     */
    public void multi(){
        redisTemplate.multi();
    }

    /**
     * 执行事务
     */
    public Boolean exec(){
        List<Object> result = redisTemplate.exec();
        int i = 1;
        Boolean e = true;
        Boolean r = true;
        for (Object o : result) {
            if(ObjectUtils.equals(o,false)){
                e = false;
                r = false;
            }else{
                e = true;
            }
            log.info("语句{},执行{}",i++,e);
        }
        return r;
    }

    /**
     * 设置键位存活时间(s)
     * @param key
     * @param time
     * @return
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0L) {
                boolean var4 = this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }

            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    /**
     * 获取键位存活时间
     * @param key
     * @return
     */
    public long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否有对应的值
     * @param key
     * @return
     */
    public boolean hasKey(String key) {
        try {
            return this.redisTemplate.hasKey(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return false;
        }
    }

    /**
     * 批量删除key
     * @param key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                this.redisTemplate.delete(key[0]);
            } else {
                this.redisTemplate.delete(Arrays.asList(key));
            }
        }

    }

    /**
     * 获取key对应的值(object类型)
     * @param key
     * @return
     */
    public Object get(String key) {
        return key == null ? null : this.redisTemplate.opsForValue().get(key);
    }

    /**
     * 存储key和value(object)
     * @param key
     * @param value
     * @return
     */
    public boolean set(String key, Object value) {
        try {
            this.redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * 存储key和value，以及存活时间(s)
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0L) {
                this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                this.set(key, value);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     * @param key
     * @param delta
     * @return
     */
    public long incr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递增因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, delta);
        }
    }

    /**
     * 递减
     * @param key
     * @param delta
     * @return
     */
    public long decr(String key, long delta) {
        if (delta < 0L) {
            throw new RuntimeException("递减因子必须大于0");
        } else {
            return this.redisTemplate.opsForValue().increment(key, -delta);
        }
    }

    /**
     * 获取key对应的value
     * @param key
     * @param item
     * @return
     */
    public Object hget(String key, String item) {
        return this.redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取键值对
     * @param key
     * @return
     */
    public Map<Object, Object> hmget(String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    /**
     * 添加map
     * @param key
     * @param map
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * 添加map并设置存活时间
     * @param key
     * @param map
     * @param time
     * @return
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            this.redisTemplate.opsForHash().putAll(key, map);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 新增hashMap值
     * @param key
     * @param item
     * @param value
     * @return
     */
    public boolean hset(String key, String item, Object value) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception var5) {
            var5.printStackTrace();
            return false;
        }
    }

    /**
     * 新增hashMap值并设置存活时间
     * @param key
     * @param item
     * @param value
     * @param time
     * @return
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            this.redisTemplate.opsForHash().put(key, item, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var7) {
            var7.printStackTrace();
            return false;
        }
    }

    /**
     * 删除一个或多个hash表字段
     * @param key
     * @param item
     */
    public void hdel(String key, Object... item) {
        this.redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 查看hash表中指定字段是否存在
     * @param key
     * @param item
     * @return
     */
    public boolean hHasKey(String key, String item) {
        return this.redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 给hash表key中指定字段递增
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hincr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 给hash表key中指定字段递减
     * @param key
     * @param item
     * @param by
     * @return
     */
    public double hdecr(String key, String item, double by) {
        return this.redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 获取key集合中所有元素
     * @param key
     * @return
     */
    public Set<Object> sGet(String key) {
        try {
            return this.redisTemplate.opsForSet().members(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return null;
        }
    }

    /**
     * 判断key集合是否包含value
     * @param key
     * @param value
     * @return
     */
    public boolean sHasKey(String key, Object value) {
        try {
            return this.redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * 向key集合中添加元素
     * @param key
     * @param values
     * @return
     */
    public long sSet(String key, Object... values) {
        try {
            return this.redisTemplate.opsForSet().add(key, values);
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    /**
     * 向key集合中添加元素并设置存活时间
     * @param key
     * @param time
     * @param values
     * @return
     */
    public long sSetAndTime(String key, long time, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().add(key, values);
            if (time > 0L) {
                this.expire(key, time);
            }

            return count;
        } catch (Exception var6) {
            var6.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取集合大小
     * @param key
     * @return
     */
    public long sGetSetSize(String key) {
        try {
            return this.redisTemplate.opsForSet().size(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    /**
     * 移除key集合中的value
     * @param key
     * @param values
     * @return
     */
    public long setRemove(String key, Object... values) {
        try {
            Long count = this.redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception var4) {
            var4.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取key列表指定范围内的元素(start开始位置, 0是开始位置，end 结束位置, -1返回所有)
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<Object> lGet(String key, long start, long end) {
        try {
            return this.redisTemplate.opsForList().range(key, start, end);
        } catch (Exception var7) {
            var7.printStackTrace();
            return null;
        }
    }

    /**
     * 获取key列表的大小
     * @param key
     * @return
     */
    public long lGetListSize(String key) {
        try {
            return this.redisTemplate.opsForList().size(key);
        } catch (Exception var3) {
            var3.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过index索引获取key列表中的元素
     * @param key
     * @param index
     * @return
     */
    public Object lGetIndex(String key, long index) {
        try {
            return this.redisTemplate.opsForList().index(key, index);
        } catch (Exception var5) {
            var5.printStackTrace();
            return null;
        }
    }

    /**
     * 在key列表右边添加值
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, Object value) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * 在key列表右边添加值并设置存活时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, Object value, long time) {
        try {
            this.redisTemplate.opsForList().rightPush(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 批量存储值到key列表中
     * @param key
     * @param value
     * @return
     */
    public boolean lSet(String key, List<Object> value) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception var4) {
            var4.printStackTrace();
            return false;
        }
    }

    /**
     * 批量存储值到key列表中并设置存活时间
     * @param key
     * @param value
     * @param time
     * @return
     */
    public boolean lSet(String key, List<Object> value, long time) {
        try {
            this.redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0L) {
                this.expire(key, time);
            }

            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 向key列表中index索引位置添加（替换）value值
     * @param key
     * @param index
     * @param value
     * @return
     */
    public boolean lUpdateIndex(String key, long index, Object value) {
        try {
            this.redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception var6) {
            var6.printStackTrace();
            return false;
        }
    }

    /**
     * 删除key列表中 (count:0所有、1第一个、-1倒数第一个) 等于value的值
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long lRemove(String key, long count, Object value) {
        try {
            Long remove = this.redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception var6) {
            var6.printStackTrace();
            return 0L;
        }
    }

}
