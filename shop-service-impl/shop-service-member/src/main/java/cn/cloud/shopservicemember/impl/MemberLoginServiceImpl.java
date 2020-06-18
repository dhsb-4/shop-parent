package cn.cloud.shopservicemember.impl;

import cn.cloud.shopapimemberdto.input.dto.UserLoginInpDTO;
import cn.cloud.shopcommoncore.base.BaseApiService;
import cn.cloud.shopcommoncore.base.BaseResponse;
import cn.cloud.shopcommoncore.constants.Constants;
import cn.cloud.shopcommoncore.token.GenerateToken;
import cn.cloud.shopcommoncore.transaction.RedisDataSoureceTransaction;
import cn.cloud.shopcommoncore.util.MD5Util;
import cn.cloud.shopcommoncore.util.RedisUtil;
import cn.cloud.shopserviceapimember.service.MemberLoginService;
import cn.cloud.shopservicemember.mapper.UserMapper;
import cn.cloud.shopservicemember.mapper.UserTokenMapper;
import cn.cloud.shopservicemember.mapper.entity.UserDo;
import cn.cloud.shopservicemember.mapper.entity.UserTokenDo;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MemberLoginServiceImpl extends BaseApiService<JSONObject> implements MemberLoginService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private GenerateToken generateToken;
    @Autowired
    private UserTokenMapper userTokenMapper;

    @Autowired
    private RedisDataSoureceTransaction redisDataSoureceTransaction;

    @Autowired
    private RedisUtil redisUtil;



    @Override
    public BaseResponse<JSONObject> login(UserLoginInpDTO userLoginInpDTO) {
        // 1.验证参数
        String mobile = userLoginInpDTO.getMobile();
        if (StringUtils.isEmpty(mobile)) {
            return setResultError("手机号码不能为空!");
        }
        String password = userLoginInpDTO.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空!");
        }
        String loginType = userLoginInpDTO.getLoginType();
        if (StringUtils.isEmpty(loginType)) {
            return setResultError("登陆类型不能为空!");
        }
        if (!(loginType.equals(Constants.MEMBER_LOGIN_TYPE_ANDROID) || loginType.equals(Constants.MEMBER_LOGIN_TYPE_IOS)
                || loginType.equals(Constants.MEMBER_LOGIN_TYPE_PC))) {
            return setResultError("登陆类型出现错误!");
        }

        // 设备信息
        String deviceInfor = userLoginInpDTO.getDeviceInfor();
        if (StringUtils.isEmpty(deviceInfor)) {
            return setResultError("设备信息不能为空!");
        }
        String newPassWord = MD5Util.MD5(password);
        // 2.用户名称与密码登陆
        UserDo userDo = userMapper.login(mobile, newPassWord);
        if (userDo == null) {
            return setResultError("用户名称与密码错误!");
        }
        TransactionStatus transactionStatus = null;
        try{
            // 3.查询之前是否有过登陆
            Long userId = userDo.getUserId();

            String keyPrefix = Constants.MEMBER_TOKEN_KEYPREFIX + loginType;

            UserTokenDo userTokenDo = userTokenMapper.selectByUserIdAndLoginType(userId, loginType);

            transactionStatus = redisDataSoureceTransaction.begin();
            // ####开启手动事务
            if (userTokenDo != null) {
                // 4.清除之前的token
                String token = userTokenDo.getToken();
                generateToken.removeToken(token);
                int updateTokenAvailability = userTokenMapper.updateTokenAvailability(token);
                if (updateTokenAvailability < 0) {
                    redisDataSoureceTransaction.rollback(transactionStatus);
                    return setResultError("系统错误");
                }
            }
            //5.将用户生成的令牌插入到Token记录表中
            UserTokenDo userToken = new UserTokenDo();
            userToken.setUserId(userId);
            userToken.setLoginType(userLoginInpDTO.getLoginType());
            String newToken = generateToken.createToken(keyPrefix,userId+"");
            userToken.setToken(newToken);
            userToken.setDeviceInfor(deviceInfor);
            int result = userTokenMapper.insertUserToken(userToken);
            if (!toDaoResult(result)){
                redisDataSoureceTransaction.rollback(transactionStatus);
                return setResultError("系统错误！");
            }

            JSONObject tokenData = new JSONObject();
            tokenData.put("token", newToken);
            redisDataSoureceTransaction.commit(transactionStatus);
            //提交mysql和redis事务
            return setResultSuccess(tokenData);
        }catch(Exception e){
            try {
                // 回滚事务
                redisDataSoureceTransaction.rollback(transactionStatus);
            } catch (Exception e1) {

            }
            return setResultError("系统错误!");
        }
    }
}
