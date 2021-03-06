package cn.cloud.shopportalweb.member.controller.req;

import cn.cloud.base.BaseWebController;
import cn.cloud.bean.utils.MeiteBeanUtils;
import cn.cloud.shopportalweb.member.controller.feign.MemberRegisterServiceFeign;
import cn.cloud.shopportalweb.member.controller.req.vo.RegisterVo;
import cn.cloud.shopapimemberdto.input.dto.UserInpDTO;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.web.utils.RandomValidateCodeUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class RegisterController extends BaseWebController {

    private static final String MB_REGISTER_PAGE = "member/register";

    @Autowired
    private MemberRegisterServiceFeign memberRegisterServiceFeign;

    private static final String MB_LOGIN_PAGE = "member/login";

    @GetMapping("/register")
    public String getRegister(){
        return MB_REGISTER_PAGE;
    }

    @PostMapping("register")
    public String postRegister(@ModelAttribute("registerVo") @Validated RegisterVo registerVo,
                               BindingResult bindingResult, Model model, HttpSession httpSession) {
        // 1.接受表单参数 (验证码) 创建对象接受参数 vo do dto
        if (bindingResult.hasErrors()) {
            // 如果参数有错误的话
            // 获取第一个错误!
            String errorMsg = bindingResult.getFieldError().getDefaultMessage();
            setErrorMsg(model, errorMsg);
            return MB_REGISTER_PAGE;
        }
        // 建议不要if lese 判断 嵌套判断统一return
        // 2.判断图形验证码是否正确
        String graphicCode = registerVo.getGraphicCode();
        Boolean checkVerify = RandomValidateCodeUtil.checkVerify(graphicCode, httpSession);
        if (!checkVerify) {
            setErrorMsg(model, "图形验证码不正确!");
            return MB_REGISTER_PAGE;
        }
        // 3.调用会员服务接口实现注册 将前端提交vo 转换dto
        UserInpDTO userInpDTO = MeiteBeanUtils.voToDto(registerVo, UserInpDTO.class);
        BaseResponse<JSONObject> register = memberRegisterServiceFeign.register(userInpDTO, registerVo.getRegistCode());
        if (!isSuccess(register)) {
            setErrorMsg(model, register.getMsg());
            return MB_REGISTER_PAGE;
        }

        // 4.跳转到登陆页面
        return MB_LOGIN_PAGE;
    }
}
