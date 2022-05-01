package com.ikaileblog.cache;

import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

//RedisMybatis 二级缓存
public class RedisMybatisCache implements Cache {

    private final String id;
    private static RedisTemplate<Object, Object> template;

    //注意构造方法必须带一个String类型的参数接收id
    public RedisMybatisCache(String id){
        this.id = id;
    }

    //初始化时通过配置类将RedisTemplate给过来
    public static void setTemplate(RedisTemplate<Object, Object> template) {
        RedisMybatisCache.template = template;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        //这里直接向Redis数据库中丢数据即可，o就是Key，o1就是Value，60秒为过期时间
        template.opsForValue().set(o, o1, 60, TimeUnit.SECONDS);
    }

    @Override
    public Object getObject(Object o) {
        //这里根据Key直接从Redis数据库中获取值即可
        System.out.println(template);
        return template.opsForValue().get(o);
    }

    @Override
    public Object removeObject(Object o) {
        //根据Key删除
        return template.delete(o);
    }

    @Override
    public void clear() {
        //由于template中没封装清除操作，只能通过connection来执行
        template.execute((RedisCallback<Void>) connection -> {
            //通过connection对象执行清空操作
            connection.flushDb();
            return null;
        });
    }

    @Override
    public int getSize() {
        //这里也是使用connection对象来获取当前的Key数量
        return template.execute(RedisServerCommands::dbSize).intValue();
    }
}