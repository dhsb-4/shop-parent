package cn.cloud.shopservicemember.impl;

import cn.cloud.shopapimemberdto.input.dto.UserInpDTO;
import cn.cloud.shopcommoncore.base.BaseApiService;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.shopcommoncore.bean.MiteBeanUtils;
import cn.cloud.shopcommoncore.constants.Constants;
import cn.cloud.shopcommoncore.util.MD5Util;
import cn.cloud.shopserviceapimember.service.MemberRegisterService;
import cn.cloud.shopservicemember.fegin.VerificaCodeServiceFeign;
import cn.cloud.shopservicemember.mapper.UserMapper;
import cn.cloud.shopservicemember.mapper.entity.UserDo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberRegisterServiceImpl extends BaseApiService<JSONObject> implements MemberRegisterService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private VerificaCodeServiceFeign verificaCodeServiceFeign;


    @Transactional
    public BaseResponse<JSONObject> register(UserInpDTO userInpDTO, String registCode) {
        // 1.验证参数
        String userName = userInpDTO.getUserName();
        if (StringUtils.isEmpty(userName)) {
            return setResultError("用户名称不能为空!");
        }
        String mobile = userInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        String newPassWord = MD5Util.MD5(password);
        // 将密码采用MD5加密
        // 调用微信接口,验证注册码是否正确
        BaseResponse<JSONObject> resultVerificaWeixinCode = verificaCodeServiceFeign.verificaWeixinCode(mobile,
                registCode);
        if (!resultVerificaWeixinCode.getRtnCode().equals(Constants.HTTP_RES_CODE_200)) {
            return setResultError(resultVerificaWeixinCode.getMsg());
        }

        userInpDTO.setPassword(newPassWord);
        UserDo userDo = MiteBeanUtils.dtoToDo(userInpDTO,UserDo.class);

        int registerResult = userMapper.register(userDo);
        return registerResult > 0 ? setResultSuccess("注册成功") : setResultSuccess("注册失败");
    }
}
