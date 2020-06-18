package cn.cloud.shopserviceapimember.service;

import cn.cloud.shopapimemberdto.input.dto.UserLoginInpDTO;
import cn.cloud.shopcommoncore.base.BaseResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Api(tags = "用户登陆服务接口")
public interface MemberLoginService {
    /**
     * 用户登陆接口
     *
     * @param userEntity
     * @return
     */
    @PostMapping("/login")
    @ApiOperation(value = "会员用户登陆信息接口")
    BaseResponse<JSONObject> login(@RequestBody UserLoginInpDTO userLoginInpDTO);
}
