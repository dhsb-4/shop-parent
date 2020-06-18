package cn.cloud.shopserviceapiwixin.service;


import cn.cloud.shopcommoncore.base.BaseResponse;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Api(tags = "微信注册接口")
public interface VerificaCodeService {

    @ApiOperation(value = "根据手机号码验证码token是否正确")
    @PostMapping("/verificaWeixinCode")
    @ApiImplicitParams({
            // @ApiImplicitParam(paramType="header",name="name",dataType="String",required=true,value="用户的姓名",defaultValue="zhaojigang"),
            @ApiImplicitParam(paramType = "query", name = "phone", dataType = "String", required = true, value = "用户手机号码"),
            @ApiImplicitParam(paramType = "query", name = "weixinCode", dataType = "String", required = true, value = "微信注册码") })
    public BaseResponse<JSONObject> verificaWeixinCode(@RequestParam("phone") String phone,
                                                       @RequestParam("weixinCode") String weixinCode);
}