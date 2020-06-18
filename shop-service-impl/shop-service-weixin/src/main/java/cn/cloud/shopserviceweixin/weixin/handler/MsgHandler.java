package cn.cloud.shopserviceweixin.weixin.handler;


import cn.cloud.shopapimemberdto.output.dto.UserOutDTO;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.shopserviceweixin.feing.MemberServiceFeing;
import cn.cloud.shopserviceweixin.weixin.builder.TextBuilder;
import cn.cloud.shopcommoncore.constants.Constants;
import cn.cloud.shopcommoncore.util.RedisUtil;
import cn.cloud.shopcommoncore.util.RegexUtils;
import entity.UserEntity;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Map;

import static me.chanjar.weixin.common.api.WxConsts.XmlMsgType;

/**
 * @author Binary Wang(https://github.com/binarywang)
 */
@Component
public class MsgHandler extends AbstractHandler {

    @Value("${dh}")
    private String dh;
    @Value("${sb}")
    private String sb;

    @Autowired
    private MemberServiceFeing memberServiceFeing;

    @Autowired
    private RedisUtil redisUtil;



    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage,
                                    Map<String, Object> context, WxMpService weixinService,
                                    WxSessionManager sessionManager) {

        if (!wxMessage.getMsgType().equals(XmlMsgType.EVENT)) {
            //TODO 可以选择将消息保存到本地
        }

        //当用户输入关键词如“你好”，“客服”等，并且有客服在线时，把消息转发给在线客服
        try {
            if (StringUtils.startsWithAny(wxMessage.getContent(), "你好", "客服")
                && weixinService.getKefuService().kfOnlineList()
                .getKfOnlineList().size() > 0) {
                return WxMpXmlOutMessage.TRANSFER_CUSTOMER_SERVICE()
                    .fromUser(wxMessage.getToUser())
                    .toUser(wxMessage.getFromUser()).build();
            }
        } catch (WxErrorException e) {
            e.printStackTrace();
        }

        //TODO 组装回复消息
        String content = wxMessage.getContent();

        if (RegexUtils.checkMobile(content)){
            //验证手机号是否存在，去数据库查询
            BaseResponse<UserOutDTO> resultUser = memberServiceFeing.existMobile(content);
            //判断是否为空
            if (resultUser.getRtnCode().equals(Constants.HTTP_RES_CODE_200)){
                return new TextBuilder().build("该手机号码"+content+"已存在",wxMessage,weixinService);
            }

            if (!resultUser.getRtnCode().equals(Constants.HTTP_RES_CODE_EXISTMOBILE_203)){
                return new TextBuilder().build(resultUser.getMsg(),wxMessage,weixinService);
            }


            //发送消息为手机号码类型，则发送短信代码
            int registCode = registCode();
            String retContext = dh.replaceAll("%s",registCode+"");

            redisUtil.setString(Constants.WEIXINCODE_KEY+content,registCode+"",Constants.WEIXINCODE_TIMEOUT);

            return new TextBuilder().build(retContext,wxMessage,weixinService);
        }

        return new TextBuilder().build(sb, wxMessage, weixinService);

    }

    //获取注册吗
    private int registCode(){
        int registCode = (int)(Math.random()*9000+1000);
        return registCode;
    }

}
