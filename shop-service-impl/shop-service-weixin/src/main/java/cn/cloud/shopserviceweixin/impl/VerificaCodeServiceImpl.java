package cn.cloud.shopserviceweixin.impl;

import cn.cloud.shopcommoncore.base.BaseApiService;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.shopcommoncore.constants.Constants;
import cn.cloud.shopcommoncore.util.RedisUtil;
import cn.cloud.shopserviceapiwixin.service.VerificaCodeService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificaCodeServiceImpl extends BaseApiService<JSONObject> implements VerificaCodeService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public BaseResponse<JSONObject> verificaWeixinCode(String phone, String weixincode) {
        if (StringUtils.isEmpty(phone)){
            return setResultError("手机号不能为空");
        }
        if (StringUtils.isEmpty(weixincode)) {
            return setResultError("注册码不能为空!");
        }
        String code = redisUtil.getString(Constants.WEIXINCODE_KEY + phone);
        if (StringUtils.isEmpty(code)) {
            return setResultError("注册码已经过期,请重新发送验证码");
        }
        if (!code.equals(weixincode)) {
            return setResultError("注册码不正确");
        }

        return setResultSuccess("注册码验证码正确");
    }
}
