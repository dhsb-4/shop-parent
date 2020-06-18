package cn.cloud.shopportalweb.portal.controller;

import cn.cloud.base.BaseWebController;
import cn.cloud.constants.WebConstants;
import cn.cloud.shopportalweb.member.controller.feign.MemberServiceFeign;
import cn.cloud.shopapimemberdto.output.dto.UserOutDTO;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.web.utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController extends BaseWebController {

    @Autowired
    private MemberServiceFeign memberServiceFeign;

    private static final String MB_INDEX_PAGE = "index";

    /**
     * 跳转到首页
     *
     * @return
     */
    @RequestMapping("/")
    public String index(HttpServletRequest request, HttpServletResponse response, Model model) {
        // 1.从cookie 中 获取 会员token
        String token = CookieUtils.getCookieValue(request, WebConstants.LOGIN_TOKEN_COOKIENAME, true);
        if (!StringUtils.isEmpty(token)) {
            // 2.调用会员服务接口,查询会员用户信息
            BaseResponse<UserOutDTO> userInfo = memberServiceFeign.getInfo(token);
            if (isSuccess(userInfo)) {
                UserOutDTO data = userInfo.getData();
                if (data != null) {
                    String mobile = data.getMobile();
                    // 对手机号码实现脱敏
                    String desensMobile = mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                    model.addAttribute("desensMobile", desensMobile);
                }
            }
        }
        return MB_INDEX_PAGE;
    }
}
