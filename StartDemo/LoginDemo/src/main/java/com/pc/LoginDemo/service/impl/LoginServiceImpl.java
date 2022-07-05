package com.pc.LoginDemo.service.impl;

import com.aliyuncs.utils.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pc.LoginDemo.common.Jwt;
import com.pc.LoginDemo.entity.LoginTable;
import com.pc.LoginDemo.mapper.LoginMapper;
import com.pc.LoginDemo.service.LoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pc.LoginDemo.service.OrderInfoService;
import com.pc.common.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, LoginTable> implements LoginService {


    @Autowired
    RedisTemplate<String,String> redisTemplate;
    /**
     * 登录功能的实现
     *
     * @param loginData :登录实体类
     * @return 集合
     */
    @Override
    public List<LoginTable> login(LoginTable loginData) {
        //获取数据
        Integer uid = loginData.getUid();
        String password = loginData.getPassword();
        //封装成集合
        List<LoginTable> list = new ArrayList<LoginTable>();
        //构建条件
        QueryWrapper<LoginTable> query = new QueryWrapper<>();
        query.eq("uid", uid).eq("password", MD5.encrypt(password));
        //获取数据
        LoginTable loginTable = baseMapper.selectOne(query);
        LoginTable loginTable1 = baseMapper.selectOne(query);
        //密码或者用户名错误
        if (loginTable == null) {
            System.out.println("用户名错误" + "uid:" + uid + "password:" + password);
            loginData.setId(-1);
            list.add(loginData);
            return list;
        }
        //设置旧的登录时间
        loginTable.setLogintime(loginTable.getLogintime());
        //设置新的登录时间
        loginTable1.setLogintime(new Date());
        baseMapper.updateById(loginTable1);
        //以list格式返回
        list.add(loginTable);

        return list;
    }

    /**
     * 单点登录
     *
     * @param loginData ：用户信息
     * @return token值
     */
    @Override
    public String loginByToken(LoginTable loginData) {
        //获取数据
        Integer uid = loginData.getUid();
        String password = loginData.getPassword();
        //封装成集合
        List<LoginTable> list = new ArrayList<LoginTable>();
        //构建条件
        QueryWrapper<LoginTable> query = new QueryWrapper<>();
        query.eq("uid", uid).eq("password", MD5.encrypt(password));
        //获取数据
        LoginTable loginTable = baseMapper.selectOne(query);
        LoginTable loginTable1 = baseMapper.selectOne(query);
        //密码或者用户名错误
        if (loginTable == null) {
            System.out.println("用户名错误" + "uid:" + uid + "password:" + password);
            loginData.setId(-1);
            list.add(loginData);
        }
        //设置旧的登录时间
        assert loginTable != null;
        loginTable.setLogintime(loginTable.getLogintime());
        //设置新的登录时间
        loginTable1.setLogintime(new Date());
        baseMapper.updateById(loginTable1);
        //以list格式返回
        list.add(loginTable);
        return Jwt.getJwtToken(String.valueOf(loginTable.getId()), loginTable.getUsername(),loginTable.getUserId());
    }



    /**
     * 注册功能
     * @param loginTable ：信息
     */
    @Override
    public List<Integer> register(LoginTable loginTable) {
        Integer uid = loginTable.getUid();
        String username = loginTable.getUsername();
        String password = loginTable.getPassword();
        String collage = loginTable.getCollage();
        String grade = loginTable.getGrade();
        String classes = loginTable.getClasses();
        String inventcode = loginTable.getInventcode();

        QueryWrapper<LoginTable> query = new QueryWrapper<>();
        //判断是否存在相同的uid和username
        query.eq("username", username).eq("uid", uid);

        Integer count = baseMapper.selectCount(query);

        List<Integer> list = new ArrayList<>();
        if (count > 0) {
            list.add(-1);
            return list;
        }
        //判断邀请码是否正确
        if (!"5217798059".equals(inventcode)) {
            list.add(-1);
            return list;
        }
        //存储数据进行插入操作
        LoginTable loginData = new LoginTable();
        loginData.setUid(uid);
        //MD5数据加密
        loginData.setPassword(MD5.encrypt(password));
        loginData.setCollage(collage);
        loginData.setUsername(username);
        loginData.setGrade(grade);
        loginData.setClasses(classes);
        loginData.setInventcode(inventcode);
        baseMapper.insert(loginData);
        list.add(uid);
        return list;
    }

    /**
     * 更改是否提交的状态
     */
    @Override
    public void updateStatus(LoginTable loginTable) {
        loginTable.setStatus("0");
        baseMapper.updateById(loginTable);
    }

    @Autowired
    private OrderInfoService orderInfo;
    /**
     * 秒杀订单 扣除money
     *
     * @param uid 学号
     * @param goodsId 商品id
     * @return -1 扣钱失败 其它是剩余余额
     */
    @Override
    public int reducePropertyByUid(Integer uid,Long goodsId) {
        int res = 0;
        //构建条件
        QueryWrapper<LoginTable> query = new QueryWrapper<>();
        //根据uid 获取信息
        query.eq("uid",uid);
        //获取信息
        LoginTable loginTable = baseMapper.selectOne(query);
        //获取财产 判断是否足够扣除
        Integer myProperty = loginTable.getProperty();
        Integer goodProperty = orderInfo.getByUId(uid, goodsId).getGoodsPrice();
        if (myProperty<goodProperty){
            return -1;
        }else{
            res = myProperty-goodProperty;
            loginTable.setProperty(res);
        }
        baseMapper.update(loginTable, query);
        return res;
    }

    /**
     * 更新用户头像
     *
     * @param uid      用户学号
     * @param goodsImg 头像路径
     * @return 更新是否成功状态码 2000：成功 4000：失败
     */
    @Override
    public int updatePictureOss(Integer uid,String goodsImg) {
        //构建条件
        QueryWrapper<LoginTable> query = new QueryWrapper<>();
        //根据uid 获取信息
        query.eq("uid",uid);
        LoginTable loginTable = baseMapper.selectOne(query);
        //对是否提交截图做判断
        if ("1".equals(loginTable.getStatus())){
            loginTable.setPictureoss(goodsImg);
            baseMapper.update(loginTable,query);
            return 2000;
        }
        return 4000;
    }
}
