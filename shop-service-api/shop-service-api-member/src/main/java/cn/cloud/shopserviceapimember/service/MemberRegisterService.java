package cn.cloud.shopserviceapimember.service;

import cn.cloud.shopapimemberdto.input.dto.UserInpDTO;
import cn.cloud.shopcommoncore.base.BaseResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "会员注册接口")
public interface MemberRegisterService {
    /**
     * 会员接口
     */
    @PostMapping("register")
    @ApiOperation("会员注册信息接口")
    BaseResponse<JSONObject> register(@RequestBody UserInpDTO userInpDTO,
                                      @RequestParam("regisCode")String registCode);
}
