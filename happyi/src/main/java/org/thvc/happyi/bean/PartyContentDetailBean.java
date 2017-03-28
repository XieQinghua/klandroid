package org.thvc.happyi.bean;

/**
 * 项目名称：klandroid
 * 类描述：活动图文详情
 * 创建人：谢庆华.
 * 创建时间：2016/3/29 15:48
 * 修改人：Administrator
 * 修改时间：2016/3/29 15:48
 * 修改备注：
 */
public class PartyContentDetailBean {

    /**
     * status : 1
     * data : {"content":"<img class=\"recommend_bg\" src=\"http://static.happiyi.com/party/A2ciEizDZBpA\"><br />报名资格<br />1. 团队组要求每2人或者4人组一队。报名者按照自愿组合原则组建队伍。<br />2.家庭亲子组要求2-5人一队，并要求其中至少一名位14周年以下少年儿童。<br />3. 多人团队组：公司企业团队组、协会社团团队（10人以上）<br />4. 所有参与者须确保自己在2015年9月25日前年满18周岁。未满18岁需监护人全程陪同参与。<br />█ █  所有参加活动者必须行走、停留在指定的线路上。  <br />█ █  本次活动必须用徒步的方式完成，不得借助于任何外力（包括人力）的帮助。<br />█ █  参与者必须在规定路线并在规定的时间内完成所有签到点的签到任务，方视为成绩有效。<br />█ █  必须按指定顺序逐一签到，不得跳签或代签。"}
     * info :
     * version : 1.3
     */

    private int status;
    /**
     * content : <img class="recommend_bg" src="http://static.happiyi.com/party/A2ciEizDZBpA"><br />报名资格<br />1. 团队组要求每2人或者4人组一队。报名者按照自愿组合原则组建队伍。<br />2.家庭亲子组要求2-5人一队，并要求其中至少一名位14周年以下少年儿童。<br />3. 多人团队组：公司企业团队组、协会社团团队（10人以上）<br />4. 所有参与者须确保自己在2015年9月25日前年满18周岁。未满18岁需监护人全程陪同参与。<br />█ █  所有参加活动者必须行走、停留在指定的线路上。  <br />█ █  本次活动必须用徒步的方式完成，不得借助于任何外力（包括人力）的帮助。<br />█ █  参与者必须在规定路线并在规定的时间内完成所有签到点的签到任务，方视为成绩有效。<br />█ █  必须按指定顺序逐一签到，不得跳签或代签。
     */

    private DataEntity data;
    private String info;
    private String version;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public int getStatus() {
        return status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getInfo() {
        return info;
    }

    public String getVersion() {
        return version;
    }

    public static class DataEntity {
        private String content;

        public void setContent(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
    }
}
