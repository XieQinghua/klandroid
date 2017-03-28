package org.thvc.happyi.bean;

/**
 * 项目名称：klandroid
 * 类描述：分享活动H5返回bean
 * 创建人：谢庆华.
 * 创建时间：2015/12/5 15:07
 * 修改人：Administrator
 * 修改时间：2015/12/5 15:07
 * 修改备注：
 */
public class SharePartyBean {

    /**
     * image : http://static.happiyi.com/party/wDHXQKEi3Px8
     * description : “爱在金秋，亲吻草原—城步南山牧场亲子“游学”之旅”是以“亲近自然、快乐学习”为主题的室外亲子体验游活动。
     * shareurl : http://www.happiyi.com/Party/detail.html?id=25
     * title : 爱在金秋，拥抱草原—城步南山牧场亲子“游学”之旅
     * content : “爱在金秋，亲吻草原—城步南山牧场亲子“游学”之旅”是以“亲近自然、快乐学习”为主题的室外亲子体验游活动。
     */
    private String image;
    private String description;
    private String shareurl;
    private String title;
    private String content;

    public void setImage(String image) {
        this.image = image;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setShareurl(String shareurl) {
        this.shareurl = shareurl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getShareurl() {
        return shareurl;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
}
