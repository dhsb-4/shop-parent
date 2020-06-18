package cn.cloud.shopportalweb.member.controller.req;

import cn.cloud.base.BaseWebController;
import cn.cloud.bean.utils.MeiteBeanUtils;
import cn.cloud.constants.WebConstants;
import cn.cloud.shopportalweb.member.controller.feign.MemberLoginServiceFeign;
import cn.cloud.shopportalweb.member.controller.req.vo.LoginVo;
import cn.cloud.shopapimemberdto.input.dto.UserLoginInpDTO;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.shopcommoncore.constants.Constants;
import cn.cloud.web.utils.CookieUtils;
import cn.cloud.web.utils.RandomValidateCodeUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController extends BaseWebController {

    private static final String MB_LOGIN_PAGE = "member/login";

    @Autowired
    private MemberLoginServiceFeign memberLoginServiceFeign;

    private static final String REDIRECT_INDEX = "redirect:/";


    @GetMapping("/login")
    public String getLogin(){
        return MB_LOGIN_PAGE;
    }

    @PostMapping("/login")
    public String postLogin(@ModelAttribute("loginVo")LoginVo loginVo, HttpServletRequest request,
                            HttpServletResponse response, Model model, HttpSession session){
        // 1.图形验证码判断
        String graphicCode = loginVo.getGraphicCode();
        if (!RandomValidateCodeUtil.checkVerify(graphicCode, session)) {
            setErrorMsg(model, "图形验证码不正确!");
            return MB_LOGIN_PAGE;
        }

        // 2.将vo转换dto调用会员登陆接口
        UserLoginInpDTO userLoginInpDTO = MeiteBeanUtils.voToDto(loginVo, UserLoginInpDTO.class);
        userLoginInpDTO.setLoginType(Constants.MEMBER_LOGIN_TYPE_PC);
        String info = webBrowserInfo(request);
        userLoginInpDTO.setDeviceInfor(info);
        BaseResponse<JSONObject> login = memberLoginServiceFeign.login(userLoginInpDTO);
        if (!isSuccess(login)) {
            setErrorMsg(model, login.getMsg());
            return MB_LOGIN_PAGE;
        }
        // 3.登陆成功之后如何处理 保持会话信息 将token存入到cookie 里面 首页读取cookietoken 查询用户信息返回到页面展示
        JSONObject data = login.getData();
        String token = data.getString("token");
        CookieUtils.setCookie(request, response, WebConstants.LOGIN_TOKEN_COOKIENAME, token);
        return REDIRECT_INDEX;
    }
}
