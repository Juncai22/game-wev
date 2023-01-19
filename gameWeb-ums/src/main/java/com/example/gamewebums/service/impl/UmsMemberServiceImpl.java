package com.example.gamewebums.service.impl;

import com.example.common.enumException.DirErrorCodeEnum;
import com.example.gamewebums.VO.MemberVo;
import com.example.gamewebums.entity.UmsMemberLevelEntity;
import com.example.gamewebums.service.UmsMemberLevelService;
import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.utils.PageUtils;
import com.example.common.utils.Query;

import com.example.gamewebums.dao.UmsMemberDao;
import com.example.gamewebums.entity.UmsMemberEntity;
import com.example.gamewebums.service.UmsMemberService;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;


@Service("umsMemberService")
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberDao, UmsMemberEntity> implements UmsMemberService {

    @Resource
    UmsMemberDao umsMemberDao;

    @Resource
    UmsMemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UmsMemberEntity> page = this.page(
                new Query<UmsMemberEntity>().getPage(params),
                new QueryWrapper<UmsMemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public int reg(MemberVo memberVo) {
        //定义需要保存的user用户
        UmsMemberEntity memberEntity = new UmsMemberEntity();
        //检查验证码
        String inviteCode = memberVo.getInviteCode();
        if (inviteCode == null)
            return DirErrorCodeEnum.UserCodeNotRight.getCode();

        int count = memberLevelService
                .count(new QueryWrapper<UmsMemberLevelEntity>().eq("name", inviteCode));

        if (count == 0)
            return DirErrorCodeEnum.UserCodeNotRight.getCode();
        else
            memberEntity.setLevelId
                    (Long.valueOf(memberLevelService.getOne(new QueryWrapper<UmsMemberLevelEntity>()
                            .eq("name", inviteCode)).getGrowthPoint()));


        if (Strings.isEmpty(memberVo.getNickName()))
            return DirErrorCodeEnum.UserRegNameEmityError.getCode();
        else if (Strings.isEmpty(memberVo.getPassWord()))
            return DirErrorCodeEnum.UserRegPassWordEmityError.getCode();
        else if (memberVo.getNickName().length() > 8)
            return DirErrorCodeEnum.UserRegNameToLongError.getCode();
        else if (18 < memberVo.getPassWord().length() || memberVo.getPassWord().length() < 6)
            return DirErrorCodeEnum.UserRegPassWordNotRightError.getCode();
        else if (Strings.isBlank(memberVo.getEmail()))
            return DirErrorCodeEnum.UserRegEmailEmityError.getCode();
        //进行设置默认数字
        memberEntity.setUsername(memberVo.getNickName());

        //检查是否有错误等
        if (checkUserName(memberVo.getNickName()) != 0) {
            return checkUserName(memberVo.getNickName());
        }
        String salt = String.valueOf(UUID.randomUUID()).substring(0, 8);
        String passWord = md5(memberVo.getPassWord() + salt);

        //添加进入member
        memberEntity.setSocialUid(salt);
        memberEntity.setPassword(passWord);
        memberEntity.setEmail(memberVo.getEmail());

        if (!this.save(memberEntity)) {
            return DirErrorCodeEnum.UserRegError.getCode();
        }

        return 0;
    }

    @Override
    public String login(MemberVo memberVo) {

        //进行登录的名字搜寻
        UmsMemberEntity memberEntity = umsMemberDao.selectOne(new QueryWrapper<UmsMemberEntity>()
                .eq("username", memberVo.getNickName()).or().eq("email", memberVo.getNickName()));
        //用户名没有则，证明返回错误码
        if (memberEntity == null) {
            return String.valueOf(DirErrorCodeEnum.UserNotHaveError.getCode());
        }
        //盐值
        String salt = memberEntity.getSocialUid();
        //密码不正确返回错误吗
        if (Objects.equals(memberEntity.getPassword(), md5(memberVo.getPassWord() + salt))) {
            return String.valueOf(0) + ";" + memberEntity.getLevelId();
        } else {
            return String.valueOf(DirErrorCodeEnum.UserNotInError.getCode());
        }
    }

    //进行用户名重复检查
    private int checkUserName(String nickName) {
        Integer integer = umsMemberDao.selectCount(new QueryWrapper<UmsMemberEntity>().eq("username", nickName));
        if (integer >= 1) {
            return DirErrorCodeEnum.UserSameError.getCode();
        }

        return 0;
    }


    //MD5盐值加密
    public static String md5(String password) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");//利用哈希算法，MD5
            //面向字节处理，所以可以处理字节的东西，如图片，压缩包。。
            byte[] input = password.getBytes();
            byte[] output = md.digest(input);
            //将md5处理后的output结果利用Base64转成原有的字符串,不会乱码
            String str = Base64.encodeBase64String(output);
//			String str = new String(output); //原有转换
            return str;
        } catch (NoSuchAlgorithmException e) {
            System.out.println("密码加密失败");
            return "";
        }

    }

}