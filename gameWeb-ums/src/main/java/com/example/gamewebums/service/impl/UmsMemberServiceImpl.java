package com.example.gamewebums.service.impl;

import com.example.common.enumException.DirErrorCodeEnum;
import com.example.gamewebums.VO.MemberVo;
import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebums.dao.UmsMemberDao;
import com.example.gamewebums.entity.UmsMemberEntity;
import com.example.gamewebums.service.UmsMemberService;

import javax.annotation.Resource;


@Service("umsMemberService")
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberDao, UmsMemberEntity> implements UmsMemberService {

    @Resource
    UmsMemberDao umsMemberDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UmsMemberEntity> page = this.page(
                new Query<UmsMemberEntity>().getPage(params),
                new QueryWrapper<UmsMemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void reg(MemberVo memberVo) {
        UmsMemberEntity memberEntity = new UmsMemberEntity();

        memberEntity.setLevelId(1L);
        memberEntity.setUsername(memberVo.getNickName());

        checkUserName(memberVo.getNickName());
    }

    private void checkUserName(String nickName) {
        Integer integer = umsMemberDao.selectCount(new QueryWrapper<UmsMemberEntity>().eq("username", nickName));
        if (integer > 1){
//            throw DirErrorCodeEnum.UserSameError;
        }
    }

}