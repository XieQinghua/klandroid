package org.thvc.happyi.http;

/**
 * 项目名称：klandroid
 * 类描述：API文档
 * 创建人：谢庆华.
 * 创建时间：2015/11/5 14:57
 * 修改人：huagnxinqi
 * 修改时间：2015/11/30 11:17
 * 修改备注：有的url含有空格，导致url错误，我把空格去掉了
 */
public class HappyiApi {
    //    private static final String HOST = "http://192.168.100.212/klapi/rest.php/";
    private static final String HOST = "http://www.happiyi.com/rest.php/";

    public static final String getHost() {
        return HOST;
    }

    //首页数据
    public static final String APP_INDEX_INDEXAPP = getHost() + "APP/Index/indexapp" + "?";

    //热门活动列表
    public static final String APP_INDEX_HOTLIST = getHost() + "APP/Index/hotList" + "?";
    //精品活动列表
    public static final String APP_INDEX_RECLIST = getHost() + "APP/Index/recList" + "?";
    //最新活动列表
    public static final String APP_INDEX_NEWLIST = getHost() + "APP/Index/newList" + "?";

    //七牛图片加载
    public static final String QINIU = "http://static.happiyi.com/";
    //生成Token
    public static final String CREATE_TOKEN = getHost() + "APP/Upload/createToken" + "?";
    //4.1.1	登录
    public static final String LOGIN = getHost() + "BDC/Account/login" + "?";
    //4.1.2	发送短信验证码
    public static final String SEND_CODE = getHost() + "WMS/TextSms/sendCode" + "?";
    //4.1.3	个人用户注册
    public static final String ORDINARY_REG = getHost() + "BDC/Account/reg" + "?";
    //4.1.4	验证用户数据唯一性
    public static final String EXIST = getHost() + "BDC/Account/exist";
    //4.1.5	NGO用户注册
    public static final String NGO_REG = getHost() + "BDC/Account/regNGO";
    //4.1.6	修改密码
    public static final String CHANGE_PASSWORD = getHost() + "BDC/Account/password" + "?";
    //4.1.7	忘记密码
    public static final String FORGET_PASSWORD = getHost() + "BDC/Account/fpwd" + "?";
    //4.1.8	修改个人头像
    public static final String CHANGE_HEADPIC = getHost() + "BDC/Account/headpic" + "?";
    //4.1.9	普通用户修改个人资料
    public static final String ORDINARY_EDIT = getHost() + "BDC/Account/edit" + "?";
    //4.1.10 NGO用户修改个人资料
    public static final String NGO_EDIT = getHost() + "BDC/Account/editNGO" + "?";
    //4.1.11 基金会用户修改个人资料
    public static final String FUND_EDIT = getHost() + "BDC/Account/editFund" + "?";
    //4.1.12 普通用户查询个人资料
    public static final String FIND_ORDINARY = getHost() + "BDC/Account/find" + "?";
    //4.1.13 NGO查询个人资料
    public static final String FIND_NGO = getHost() + "BDC/Account/findNGO" + "?";
    //4.1.14 基金会用户查询个人资料
    public static final String FIND_FUND = getHost() + "BDC/Account/findFund" + "?";
    //4.1.15 我的消息列表
    public static final String NOTICE = getHost() + "BDC/Account/notice" + "?";
    //4.1.16 变更消息状态为已读
    public static final String READ_NOTICE = getHost() + "BDC/Account/readNotice" + "?";
    //4.1.17 微信绑定
    public static final String BIND_OPENID = getHost() + "BDC/Account/bindOpenid";
    //4.1.18 微信登录
    public static final String WECHAT_LOGIN = getHost() + "BDC/Account/wechatLogin";
    //4.1.19 第三方登录验证接口
    public static final String OAUTH = getHost() + "BDC/Account/oauth" + "?";
    //4.1.20 绑定账号信息接口
    public static final String OAUTH_BIND = getHost() + "BDC/Account/oauthBind" + "?";
    //4.1.21 自动登录接口
    public static final String OAUTH_LOGIN = getHost() + "BDC/Account/oauthLogin" + "?";

    //4.2.1 活动列表查询
    public static final String PARTY_INDEX = getHost() + "APP/Party/index" + "?";
    //4.2.2	活动搜索
    public static final String PARTY_SEARCH = getHost() + "APP/Party/search" + "?";
    //4.2.3 NGO查询我发布的活动
    public static final String PARTYMYLIST = getHost() + "APP/Party/myList" + "?";
    //4.2.4 查询NGO发布的活动
    public static final String NGO_PARTY_LIST = getHost() + "APP/Party/ngoPartyList" + "?";
    //4.2.5 基金会查询待认领的活动
    public static final String NOCLAIMLIST = getHost() + "APP/Party/noclaimList" + "?";
    //4.2.6 基金会查询自己认领的活动
    public static final String CLAIMLIST = getHost() + "APP/Party/claimList" + "?";
    //4.2.26个人用户查询报名的活动券
    public static final String MYTICKET = getHost() + "APP/Party/myTicket" + "?";
    //4.2.7 查询我参与的活动
    public static final String JOIN_LIST = getHost() + "APP/Party/joinList" + "?";
    //4.2.8 查询活动详情
    public static final String PARTY_DETAIL = getHost() + "APP/Party/detail" + "?";
    //APP活动详情
    public static final String APP_DETAIL = getHost() + "APP/Party/detailapp" + "?";
    //APP活动图文详情
    public static final String CONTENT_DETAIL = getHost() + "APP/Party/contentdetail" + "?";//TODO 这个接口服务器没有加密

    //4.2.13 查询活动标题接口
    public static final String FIND_TITLE = getHost() + "APP/Party/findTitle" + "?";
    //4.2.14 审核活动
    public static final String CHECK_PARTY = getHost() + "APP/Party/check";
    //4.2.15 认领活动
    public static final String GET_PARTY = getHost() + "APP/Party/partyGet" + "?";
    //4.2.16 活动报名
    public static final String JOIN_PARTY = getHost() + "APP/Party/join" + "?";
    //家庭报名
    public static final String JOIN_FAMILY = getHost() + "APP/Party/joinfamily" + "?";
    //4.2.17 活动退款
    public static final String APPLY_REFUND = getHost() + "APP/Party/applyRefund" + "?";
    //4.2.18 NGO退款列表
    public static final String REFUND_INDEX = getHost() + "APP/Party/refundIndex" + "?";

    //4.2.20 个人用户查询自己的退款列表
    public static final String MY_REFUND_INDEX = getHost() + "APP/Party/myRefundIndex" + "?";
    //4.2.21 NGO确认退款
    public static final String CONFIRM_REFUND = getHost() + "APP/Party/confirmRefund" + "?";

    //4.2.24 查询活动参加人列表
    public static final String PARTY_JOIN_PEOPLE = getHost() + "APP/Party/partyJoinPeople" + "?";
    //查询签到和未签到
    public static final String PARTY_JOIN_Party_PEOPLE = getHost() + "APP/Party/joinPartyPeople" + "?";
    //4.2.25 个人用户查询自己报名的报名信息
    public static final String MY_PARTY_JOIN_PEOPLE = getHost() + "APP/Party/myPartyJoinPeople" + "?";
    //4.2.26 活动进展新增
    public static final String CONTENT_ADD = getHost() + "APP/Party/contentAdd" + "?";
    //4.2.28 活动现场列表
    public static final String CONTENT_LIST = getHost() + "APP/Party/contentList" + "?";
    //4.2.29 参与活动生成单据
    public static final String JOIN_PAY_NO = getHost() + "OMS/Money/joinPayno" + "?";

    //4.3.1	NGO列表
    public static final String NGO_LIST = getHost() + "APP/NGO/index" + "?";
    //4.3.2	查询NGO详情与4.1.13NGO查询个人资料相同

    //4.4.1	查询基金会列表
    public static final String FUND_INDEX = getHost() + "APP/Fund/index" + "?";
    //4.4.2	查询基金会详情与4.1.14基金会用户查询个人资料

    //4.5.1	用户签到检查二维码接口
    public static final String CHECKJOIN = getHost() + "APP/Index/checkJoin" + "?";
    //4.5.2	首页查询
    public static final String HOME_INDEX = getHost() + "APP/Index/index" + "?";

    //4.6.1	查询关注的活动
    public static final String COLLECT_PARTY = getHost() + "MMS/Collect/party" + "?";
    //4.6.2	查询关注的NGO
    public static final String COLLECT_NGO = getHost() + "MMS/Collect/ngo" + "?";
    //4.6.3	查询关注的基金会
    public static final String COLLECT_FUND = getHost() + "MMS/Collect/fund" + "?";

    //4.6.4 查询粉丝
    public static final String FANS = getHost() + "MMS/Collect/fans" + "?";
    //4.6.5	点赞/取消赞
    public static final String COLLECT_GOOD = getHost() + "MMS/Collect/good";


    //4.6.6	添加/取消关注
    public static final String COLLECT_ADD = getHost() + "MMS/Collect/add" + "?";


    //4.7.1	评价权限查询
    public static final String COMMENT_CHECK = getHost() + "MMS/Comment/check" + "?";
    //4.7.2	添加活动评价
    public static final String COMMENT_ADD = getHost() + "MMS/Comment/add" + "?";
    //4.7.3	活动评价查询
    public static final String COMMENT_INDEX = getHost() + "MMS/Comment/index" + "?";
    //4.7.4	评价审核
    public static final String COMMENT_AUT = getHost() + "MMS/Comment/authenticate" + "?";
    //4.7.5	我的评价查询
    public static final String COMMENT_MY = getHost() + "MMS/Comment/my" + "?";

    //4.9.1	参与活动通知
    public static final String JOIN_NOITY = getHost() + "OMS/Money/joinNoity" + "?";
    //4.10.1 新增一条反馈
    public static final String ADDFEEDBACK = getHost() + "APP/System/addFeedback" + "?";
    //4.10.2查询所有反馈
    public static final String SHOWFEEDBACK = getHost() + "APP/System/showFeedback";

    //待认领列表
    public static final String NOCHECK_LIST = getHost() + "APP/Party/nocheckList" + "?";
    //待认领列表(其实是去让利的列表)
    public static final String NOSET_LIST = getHost() + "APP/Party/nosetList" + "?";
    //已认领列表
    public static final String CHECK_LIST = getHost() + "APP/Party/checkList" + "?";

    //获取账户管理信息
    public static final String FUND_GETINFO = getHost() + "APP/Fund/getinfo" + "?";
    //设置账户管理信息
    public static final String FUND_SETINFO = getHost() + "APP/Fund/setinfo" + "?";

    //NGO设置让利
    public static final String NGO_SET_MONEY = getHost() + "APP/Party/setmoney" + "?";
}
