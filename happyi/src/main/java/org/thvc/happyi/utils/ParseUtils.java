package org.thvc.happyi.utils;

import com.google.gson.Gson;

import org.thvc.happyi.bean.ARefundBean;
import org.thvc.happyi.bean.AddCommentBean;
import org.thvc.happyi.bean.ChangePwdBean;
import org.thvc.happyi.bean.ClaimPartyBean;
import org.thvc.happyi.bean.CollectAddBean;
import org.thvc.happyi.bean.CollectBean;
import org.thvc.happyi.bean.CommentBean;
import org.thvc.happyi.bean.CommentPartyBean;
import org.thvc.happyi.bean.CommentPermissionsBean;
import org.thvc.happyi.bean.ConcernBean;
import org.thvc.happyi.bean.EditBean;
import org.thvc.happyi.bean.FansBean;
import org.thvc.happyi.bean.FeedackBean;
import org.thvc.happyi.bean.FoundationBean;
import org.thvc.happyi.bean.Foundationlistbean;
import org.thvc.happyi.bean.FundCollectListBean;
import org.thvc.happyi.bean.FundNativeDetailBean;
import org.thvc.happyi.bean.GetInFoBean;
import org.thvc.happyi.bean.HomeIndexBean;
import org.thvc.happyi.bean.HomePartBean;
import org.thvc.happyi.bean.InformationBean;
import org.thvc.happyi.bean.JoinPeopleBean;
import org.thvc.happyi.bean.LoginBean;
import org.thvc.happyi.bean.MessageListBean;
import org.thvc.happyi.bean.MoneyJoinNoityBean;
import org.thvc.happyi.bean.MoneyJoinPaynoBean;
import org.thvc.happyi.bean.MyPartyBean;
import org.thvc.happyi.bean.MyTicketBean;
import org.thvc.happyi.bean.NGOCollectBean;
import org.thvc.happyi.bean.NGOPartyBean;
import org.thvc.happyi.bean.NgoDetailbean;
import org.thvc.happyi.bean.NgoListBean;
import org.thvc.happyi.bean.NgoLoginBean;
import org.thvc.happyi.bean.NgoPartyListBean;
import org.thvc.happyi.bean.NoClaimPartyBean;
import org.thvc.happyi.bean.PartyAndNewBean;
import org.thvc.happyi.bean.PartyClaimBean;
import org.thvc.happyi.bean.PartyCollectBean;
import org.thvc.happyi.bean.PartyContentDetailBean;
import org.thvc.happyi.bean.PartyDetailBean;
import org.thvc.happyi.bean.PartyDetailappBean;
import org.thvc.happyi.bean.PartyIndexBean;
import org.thvc.happyi.bean.PartyListBean;
import org.thvc.happyi.bean.PartyRegistration;
import org.thvc.happyi.bean.PartyRegistrationBean;
import org.thvc.happyi.bean.PartySceneBean;
import org.thvc.happyi.bean.PartyTypeBean;
import org.thvc.happyi.bean.RefundHistoryBean;
import org.thvc.happyi.bean.RefundListBean;
import org.thvc.happyi.bean.RefundRecordBean;
import org.thvc.happyi.bean.RegisterCodeBean;
import org.thvc.happyi.bean.RegisterInformationBean;
import org.thvc.happyi.bean.SearchListBean;
import org.thvc.happyi.bean.SetInFoBean;
import org.thvc.happyi.bean.SetUpToReadBean;
import org.thvc.happyi.bean.SharePartyBean;
import org.thvc.happyi.bean.SignBean;
import org.thvc.happyi.bean.ToNgoBean;
import org.thvc.happyi.bean.TokenBean;
import org.thvc.happyi.bean.UpdataActivityBean;
import org.thvc.happyi.bean.UserRegisterBean;
import org.thvc.happyi.bean.VersionUpdateBean;

/**
 * 项目名称：klandroid
 * 类描述：解析json工具，一个接口返回json对应一个解析方法
 * 创建人：谢庆华.
 * 创建时间：2015/11/18 17:21
 * 修改人：Administrator
 * 修改时间：2015/11/18 17:21
 * 修改备注：
 */
public class ParseUtils {
    private static Gson gson = new Gson();

    /**
     * 登录返回字段的解析
     *
     * @param json
     */
    public static LoginBean parseLoginBean(String json) throws IllegalStateException {
        LoginBean loginBean = gson.fromJson(json, LoginBean.class);
        return loginBean;
    }

    /**
     * 验证码接口返回字段的解析
     *
     * @param json
     */
    public static RegisterCodeBean parseRegisterCodeBean(String json) throws IllegalStateException {
        RegisterCodeBean registerCodeBean = gson.fromJson(json, RegisterCodeBean.class);
        return registerCodeBean;
    }

    /**
     * 注册账号返回字段解析
     *
     * @param json
     * @return
     */
    public static UserRegisterBean parseUserRegisterBean(String json) throws IllegalStateException {
        UserRegisterBean userRegisterBean = gson.fromJson(json, UserRegisterBean.class);
        return userRegisterBean;
    }

    /**
     * NGO用户的json字段的解析
     *
     * @param json
     */
    public static NgoLoginBean parseNgoLoginBean(String json) throws IllegalStateException {
        NgoLoginBean ngoLoginBean = gson.fromJson(json, NgoLoginBean.class);
        return ngoLoginBean;
    }

    /**
     * NGO列表字段的解析
     *
     * @param json
     */
    public static NgoListBean parseNgoListBean(String json) throws IllegalStateException {
        NgoListBean ngoListBean = gson.fromJson(json, NgoListBean.class);
        return ngoListBean;
    }

    /**
     * 首页内容bean，包含轮播图、活动标签、推荐活动
     *
     * @param json
     * @return
     */
    public static HomeIndexBean parseHomeIndexBean(String json) throws IllegalStateException {
        HomeIndexBean homeIndexBean = gson.fromJson(json, HomeIndexBean.class);
        return homeIndexBean;
    }

    /**
     * 活动列表查询，与主页的HomeIndexBean有重复
     *
     * @param json
     * @return
     */
    public static HomePartBean parseHomePartBean(String json) throws IllegalStateException {
        HomePartBean homePartBean = gson.fromJson(json, HomePartBean.class);
        return homePartBean;
    }

    /**
     * NGO信息字段的解析
     *
     * @param json
     */
    public static NgoDetailbean parseNgoDetailbean(String json) throws IllegalStateException {
        NgoDetailbean ngoDetailbean = gson.fromJson(json, NgoDetailbean.class);
        return ngoDetailbean;
    }

    /**
     * 修改密码的解析
     *
     * @param json
     */
    public static ChangePwdBean parseChangePwdBean(String json) throws IllegalStateException {
        ChangePwdBean changePwdBean = gson.fromJson(json, ChangePwdBean.class);
        return changePwdBean;
    }

    /**
     * 修改资料bean，NGO和个人共用此方法
     *
     * @param json
     * @return
     */
    public static EditBean parseEditBean(String json) throws IllegalStateException {
        EditBean editBean = gson.fromJson(json, EditBean.class);
        return editBean;
    }

    /**
     * 基金会信息字段的解析
     *
     * @param json
     */
    public static FoundationBean parseFoundationBean(String json) throws IllegalStateException {
        FoundationBean foundationBean = gson.fromJson(json, FoundationBean.class);
        return foundationBean;
    }

    /**
     * 基金会已认领活动字段的解析
     *
     * @param json
     */
    public static ClaimPartyBean parseClaimPartyBean(String json) throws IllegalStateException {
        ClaimPartyBean claimPartyBean = gson.fromJson(json, ClaimPartyBean.class);
        return claimPartyBean;
    }

    /**
     * 基金会待认领活动字段的解析
     *
     * @param json
     */
    public static NoClaimPartyBean parseNoClaimPartyBean(String json) throws IllegalStateException {
        NoClaimPartyBean noClaimPartyBean = gson.fromJson(json, NoClaimPartyBean.class);
        return noClaimPartyBean;
    }

    /**
     * 普通用户参与活动字段的解析
     *
     * @param json
     */
    public static MyPartyBean parseMyPartyBean(String json) throws IllegalStateException {
        MyPartyBean myPartyBean = gson.fromJson(json, MyPartyBean.class);
        return myPartyBean;
    }

    /**
     * 用户收藏的基金会列表
     *
     * @param json
     * @return
     */
    public static FundCollectListBean parseFundCollectListBean(String json) throws IllegalStateException {
        FundCollectListBean fundCollectListBean = gson.fromJson(json, FundCollectListBean.class);
        return fundCollectListBean;
    }


    /**
     * 用户收藏的活动列表
     *
     * @param json
     * @return
     */
    public static PartyCollectBean parsePartyCollectBaen(String json) throws IllegalStateException {
        return gson.fromJson(json, PartyCollectBean.class);
    }

    /**
     * 用户收藏的NGO列表
     *
     * @param json
     * @return
     */
    public static NGOCollectBean parseNGOCollectBean(String json) throws IllegalStateException {
        return gson.fromJson(json, NGOCollectBean.class);
    }

    /**
     * 用户自己报名的报名信息列表
     *
     * @param json
     * @return
     */
    public static RegisterInformationBean parseRegisterInformationBean(String json) throws IllegalStateException {
        RegisterInformationBean registerInformationBean = gson.fromJson(json, RegisterInformationBean.class);
        return registerInformationBean;
    }

    /**
     * 添加意见反馈数据解析
     *
     * @param json
     * @return
     */
    public static FeedackBean parseFeedackBean(String json) throws IllegalStateException {
        FeedackBean feedackBean = gson.fromJson(json, FeedackBean.class);
        return feedackBean;
    }

    /**
     * CREATE_TOKEN接口返回获取七牛token
     *
     * @param result
     */
    public static TokenBean parseTokenBean(String result) throws IllegalStateException {
        TokenBean tokenBean = gson.fromJson(result, TokenBean.class);
        return tokenBean;
    }

    /**
     * 添加关注数据解析
     *
     * @param json
     * @return
     */
    public static CollectBean parseCollectBean(String json) throws IllegalStateException {
        CollectBean collectBean = gson.fromJson(json, CollectBean.class);
        return collectBean;
    }

    /**
     * 消息数据解析
     *
     * @param json
     * @return
     */
    public static MessageListBean parseMessageListBean(String json) throws IllegalStateException {
        MessageListBean messageListBean = gson.fromJson(json, MessageListBean.class);
        return messageListBean;
    }

    /**
     * 退款数据解析
     *
     * @param json
     * @return
     */
    public static RefundListBean parseRefundListBean(String json) throws IllegalStateException {
        RefundListBean refundListBean = gson.fromJson(json, RefundListBean.class);
        return refundListBean;
    }

    /**
     * 普通用户退款数据解析
     *
     * @param json
     * @return
     */
    public static RefundRecordBean parseRefundRecordBean(String json) throws IllegalStateException {
        RefundRecordBean refundRecordBean = gson.fromJson(json, RefundRecordBean.class);
        return refundRecordBean;
    }


    /**
     * 报名数据解析
     *
     * @param json
     * @return
     */
    public static PartyRegistrationBean parsePartyRegistrationBean(String json) throws IllegalStateException {
        PartyRegistrationBean partyRegistrationBean = gson.fromJson(json, PartyRegistrationBean.class);
        return partyRegistrationBean;
    }

    public static CollectAddBean parseCollectAddBean(String json) throws IllegalStateException {
        CollectAddBean collectAddBean = gson.fromJson(json, CollectAddBean.class);
        return collectAddBean;
    }

    /**
     * 评论
     *
     * @param json
     * @return
     */
    public static CommentBean parseCommentBean(String json) throws IllegalStateException {
        return gson.fromJson(json, CommentBean.class);
    }

    /**
     * 报名人员
     *
     * @param json
     * @return
     */
    public static JoinPeopleBean parseJoinPeopleBean(String json) throws IllegalStateException {
        return gson.fromJson(json, JoinPeopleBean.class);
    }

    /**
     * 活动现场
     *
     * @param json
     * @return
     */
    public static PartySceneBean parsePartySceneBean(String json) throws IllegalStateException {
        return gson.fromJson(json, PartySceneBean.class);
    }

    /**
     * 参与活动生成单据
     *
     * @param json
     * @return
     */
    public static MoneyJoinPaynoBean parseMoneyJoinPaynoBean(String json) throws IllegalStateException {
        MoneyJoinPaynoBean moneyJoinPaynoBean = gson.fromJson(json, MoneyJoinPaynoBean.class);
        return moneyJoinPaynoBean;
    }

    /**
     * 参与活动报名H5对接数据
     *
     * @param json
     * @return
     */
    public static PartyRegistration parsePartyRegistration(String json) throws IllegalStateException {
        PartyRegistration partyRegistration = gson.fromJson(json, PartyRegistration.class);
        return partyRegistration;
    }

    /**
     * 活动详情
     *
     * @param json
     * @return
     */
    public static PartyDetailBean parsePartyDetailBean(String json) throws IllegalStateException {
        PartyDetailBean partyDetailBean = gson.fromJson(json, PartyDetailBean.class);
        return partyDetailBean;
    }

    /**
     * 新版活动详情，活动参与人员，活动评论一起返回
     *
     * @param json
     * @return
     */
    public static PartyDetailappBean parsePartyDetailappBean(String json) throws IllegalStateException {
        PartyDetailappBean partyDetailappBean = gson.fromJson(json, PartyDetailappBean.class);
        return partyDetailappBean;
    }

    /**
     * 活动图文详情
     *
     * @param json
     * @return
     */
    public static PartyContentDetailBean parsePartyContentDetailBean(String json) throws IllegalStateException {
        PartyContentDetailBean partyContentDetailBean = gson.fromJson(json, PartyContentDetailBean.class);
        return partyContentDetailBean;
    }

    public static FansBean parseFansBean(String json) throws IllegalStateException {
        FansBean fansBean = gson.fromJson(json, FansBean.class);
        return fansBean;
    }

    public static NGOPartyBean parseNGOPartyBean(String json) throws IllegalStateException {
        NGOPartyBean ngoPartyBean = gson.fromJson(json, NGOPartyBean.class);
        return ngoPartyBean;
    }

    /**
     * 分享活动H5返回bean解析
     *
     * @param json
     * @return SharePartyBean
     */
    public static SharePartyBean parseSharePartyBean(String json) throws IllegalStateException {
        SharePartyBean sharePartyBean = gson.fromJson(json, SharePartyBean.class);
        return sharePartyBean;
    }

    /**
     * 评价活动H5返回bean解析
     *
     * @param json
     * @return CommentPartyBean
     */
    public static CommentPartyBean parseCommentPartyBean(String json) throws IllegalStateException {
        CommentPartyBean commentPartyBean = gson.fromJson(json, CommentPartyBean.class);
        return commentPartyBean;
    }

    /**
     * 详情页面点击NGO头像返回json的bean
     *
     * @param json
     * @return
     */
    public static ToNgoBean parseToNgoBean(String json) throws IllegalStateException {
        ToNgoBean toNgoBean = gson.fromJson(json, ToNgoBean.class);
        return toNgoBean;
    }

    /**
     * 确认退款
     *
     * @param json
     * @return
     */
    public static ARefundBean parseARefundBean(String json) throws IllegalStateException {
        ARefundBean aRefundBean = gson.fromJson(json, ARefundBean.class);
        return aRefundBean;
    }


    /**
     * 评论权限
     *
     * @param json
     * @return
     */
    public static CommentPermissionsBean parseCommentPermissionsBean(String json) throws IllegalStateException {
        CommentPermissionsBean commentPermissionsBean = gson.fromJson(json, CommentPermissionsBean.class);
        return commentPermissionsBean;
    }

    /**
     * 添加评论
     *
     * @param json
     * @return
     */
    public static AddCommentBean parseAddCommentBean(String json) throws IllegalStateException {
        AddCommentBean addCommentBean = gson.fromJson(json, AddCommentBean.class);
        return addCommentBean;
    }

    /**
     * 添加评论
     *
     * @param json
     * @return
     */
    public static PartyClaimBean parsePartyClaimBean(String json) throws IllegalStateException {
        PartyClaimBean partyClaimBean = gson.fromJson(json, PartyClaimBean.class);
        return partyClaimBean;
    }

    /**
     * 新增活动
     *
     * @param json
     * @return
     */
    public static UpdataActivityBean parseUpdataActivityBean(String json) throws IllegalStateException {
        UpdataActivityBean updataActivityBean = gson.fromJson(json, UpdataActivityBean.class);
        return updataActivityBean;
    }

    /**
     * 新增活动
     *
     * @param json
     * @return
     */
    public static PartyAndNewBean parsePartyAndNewBean(String json) throws IllegalStateException {
        PartyAndNewBean partyAndNewBean = gson.fromJson(json, PartyAndNewBean.class);
        return partyAndNewBean;
    }


    /**
     * 变更消息状态为已读
     *
     * @param json
     * @return
     */
    public static SetUpToReadBean parSetUpToReadBean(String json) throws IllegalStateException {
        SetUpToReadBean setUpToReadBean = gson.fromJson(json, SetUpToReadBean.class);
        return setUpToReadBean;
    }


    /**
     * 新增活动
     *
     * @param json
     * @return
     */
    public static InformationBean parseInformationBean(String json) throws IllegalStateException {
        InformationBean informationBean = gson.fromJson(json, InformationBean.class);
        return informationBean;
    }

    /**
     * 参与活动通知
     *
     * @param json
     * @return
     */
    public static MoneyJoinNoityBean parseMoneyJoinNoityBean(String json) throws IllegalStateException {
        MoneyJoinNoityBean moneyJoinNoityBean = gson.fromJson(json, MoneyJoinNoityBean.class);
        return moneyJoinNoityBean;
    }

    /**
     * 活动退款记录
     *
     * @param json
     * @return
     */
    public static RefundHistoryBean parseRefundHistoryBean(String json) throws IllegalStateException {
        RefundHistoryBean refundHistoryBean = gson.fromJson(json, RefundHistoryBean.class);
        return refundHistoryBean;
    }

    /**
     * H5关注和取消关注返回参数
     *
     * @param json
     * @return
     */
    public static ConcernBean parseConcernBean(String json) throws IllegalStateException {
        ConcernBean concernBean = gson.fromJson(json, ConcernBean.class);
        return concernBean;
    }

    /**
     * 激光推送，版本跟新返回数据
     */
    public static VersionUpdateBean parseVersionUpdateBean(String json) throws IllegalStateException {
        VersionUpdateBean versionUpdateBean = gson.fromJson(json, VersionUpdateBean.class);
        return versionUpdateBean;
    }

    /**
     * 推荐活动，返回数据
     */
    public static PartyIndexBean parsePartyIndexBean(String json) throws IllegalStateException {
        PartyIndexBean partyIndexBean = gson.fromJson(json, PartyIndexBean.class);
        return partyIndexBean;
    }


    /**
     * 搜索活动，返回数据
     */
    public static SearchListBean parseSearchListBean(String json) throws IllegalStateException {
        SearchListBean searchListBean = gson.fromJson(json, SearchListBean.class);
        return searchListBean;
    }

    /**
     * 我的活动劵，返回数据
     */
    public static MyTicketBean parseMyTicketBean(String json) throws IllegalStateException {
        MyTicketBean myTicketBean = gson.fromJson(json, MyTicketBean.class);
        return myTicketBean;
    }

    /**
     * Ngo下的活动，返回数据
     */
    public static NgoPartyListBean parseNgoPartyListBean(String json) throws IllegalStateException {
        NgoPartyListBean ngoPartyListBean = gson.fromJson(json, NgoPartyListBean.class);
        return ngoPartyListBean;
    }

    /**
     * 基金会列表，返回数据
     */
    public static Foundationlistbean parseFoundationlistbean(String json) throws IllegalStateException {
        Foundationlistbean foundationlistbean = gson.fromJson(json, Foundationlistbean.class);
        return foundationlistbean;
    }

    /**
     * 基金会详情，返回数据
     */
    public static FundNativeDetailBean parseFundNativeDetailBean(String json) throws IllegalStateException {
        FundNativeDetailBean fundNativeDetailBean = gson.fromJson(json, FundNativeDetailBean.class);
        return fundNativeDetailBean;
    }

    /**
     * 获取账户管理，返回数据
     */
    public static GetInFoBean parseGetInFoBean(String json) throws IllegalStateException {
        GetInFoBean getInFoBean = gson.fromJson(json, GetInFoBean.class);
        return getInFoBean;
    }

    /**
     * 设置账户管理，返回数据
     */
    public static SetInFoBean parseSetInFoBean(String json) throws IllegalStateException {
        SetInFoBean getInFoBean = gson.fromJson(json, SetInFoBean.class);
        return getInFoBean;
    }

    /**
     * 已签到和未签到，返回数据
     */
    public static SignBean parseSignBean(String json) throws IllegalStateException {
        SignBean signBean = gson.fromJson(json, SignBean.class);
        return signBean;
    }

    /**
     * 搜索活动，返回数据
     */
    public static PartyListBean parsePartyListBean(String json) throws IllegalStateException {
        PartyListBean partyListBean = gson.fromJson(json, PartyListBean.class);
        return partyListBean;
    }


    /****
     * 热门活动，精品活动，最新活动 返回参数
     */
    public static PartyTypeBean parsePartyTypeBean(String json) throws IllegalStateException {
        PartyTypeBean partyTypeBean = gson.fromJson(json, PartyTypeBean.class);
        return partyTypeBean;
    }

}
