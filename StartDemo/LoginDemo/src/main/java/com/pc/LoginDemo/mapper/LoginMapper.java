package com.pc.LoginDemo.mapper;

import com.pc.LoginDemo.entity.LoginTable;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-04-03
 */
@Mapper
public interface LoginMapper extends BaseMapper<LoginTable> {

}
