package cn.cloud.shopservicemember.impl;

import cn.cloud.shopapimemberdto.output.dto.UserOutDTO;
import cn.cloud.shopcommoncore.base.BaseApiService;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.shopcommoncore.bean.MiteBeanUtils;
import cn.cloud.shopcommoncore.constants.Constants;
import cn.cloud.shopcommoncore.token.GenerateToken;
import cn.cloud.shopcommoncore.type.TypeCastHelper;
import cn.cloud.shopserviceapimember.service.MemberService;
import cn.cloud.shopservicemember.mapper.UserMapper;
import cn.cloud.shopservicemember.mapper.entity.UserDo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberServiceImpl extends BaseApiService<UserOutDTO> implements MemberService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GenerateToken generateToken;

    @Override
    public BaseResponse<UserOutDTO> existMobile(String mobile) {
        // 1.验证参数
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        // 2.根据手机号码查询用户信息 单独定义code 表示是用户信息不存在把
        UserDo userEntity = userMapper.existMobile(mobile);
        if (userEntity == null) {
            return setResultError(Constants.HTTP_RES_CODE_EXISTMOBILE_203, "用户信息不存在!");
        }

        return setResultSuccess(MiteBeanUtils.doToDto(userEntity,UserOutDTO.class));
    }

    @Override
    public BaseResponse<UserOutDTO> getInfo(String token) {
        //1.验证token是否为空
        if (StringUtils.isEmpty(token)){
            return setResultError("token不能为空");
        }
        //2.去redis李查询token
        String redisUserId = generateToken.getToken(token);
        if (StringUtils.isEmpty(redisUserId)){
            return setResultError("token失效或者token错误");
        }

        Long userId = TypeCastHelper.toLong(redisUserId);
        UserDo userDo = userMapper.findByUserId(userId);
        if (userDo == null) {
            return setResultError("用户不存在!");
        }
        // 下节课将 转换代码放入在BaseApiService
        return setResultSuccess(MiteBeanUtils.doToDto(userDo, UserOutDTO.class));
    }

}