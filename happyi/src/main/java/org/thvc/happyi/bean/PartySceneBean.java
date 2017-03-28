package org.thvc.happyi.bean;

import java.util.List;

/**
 * 项目名称：klandroid
 * 类描述：活动现场bean
 * 创建人：谢庆华.
 * 创建时间：2016/1/6 20:14
 * 修改人：Administrator
 * 修改时间：2016/1/6 20:14
 * 修改备注：
 */
public class PartySceneBean {
    /**
     * status : 1
     * data : {"count":"5","maxPage":1,"list":[{"id":"151","solevar":"2be6d16fee36f95f","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"铁路局了","addtime":"1452926284","isdel":"2","pic":["Party/14529264665171452844687162.jpg","Party/14529264665291452847623215.jpg"]},{"id":"150","solevar":"52281f80e242ff66","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"咯哦哦下午","addtime":"1452925571","isdel":"2","pic":null},{"id":"149","solevar":"bd73de3c7dfe57e1","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"咯哦哦下午","addtime":"1452925571","isdel":"2","pic":null},{"id":"145","solevar":"643e13274f916003","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"8他踏踏拒绝","addtime":"1452907710","isdel":"2","pic":["Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpg","Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpgParty//storage/emulated/0/DCIM/Camera/IMG_20151023_132502_BURST1.jpg","Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpg","Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpgParty//storage/emulated/0/DCIM/Camera/IMG_20151023_132502_BURST1.jpgParty//storage/emulated/0/DCIM/Camera/IMG_20151023_132502_BURST2.jpg"]},{"id":"144","solevar":"2855dc6d93130401","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"得得客厅陆军","addtime":"1452907640","isdel":"2","pic":["Party//storage/emulated/0/BRDownload/1444894634375y.png","Party//storage/emulated/0/BRDownload/1444894634375y.pngParty//storage/emulated/0/image.jpg"]}]}
     * info :
     */

    private int status;
    /**
     * count : 5
     * maxPage : 1
     * list : [{"id":"151","solevar":"2be6d16fee36f95f","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"铁路局了","addtime":"1452926284","isdel":"2","pic":["Party/14529264665171452844687162.jpg","Party/14529264665291452847623215.jpg"]},{"id":"150","solevar":"52281f80e242ff66","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"咯哦哦下午","addtime":"1452925571","isdel":"2","pic":null},{"id":"149","solevar":"bd73de3c7dfe57e1","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"咯哦哦下午","addtime":"1452925571","isdel":"2","pic":null},{"id":"145","solevar":"643e13274f916003","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"8他踏踏拒绝","addtime":"1452907710","isdel":"2","pic":["Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpg","Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpgParty//storage/emulated/0/DCIM/Camera/IMG_20151023_132502_BURST1.jpg","Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpg","Party//storage/emulated/0/DCIM/Camera/IMG_20151023_145054.jpgParty//storage/emulated/0/DCIM/Camera/IMG_20151023_132502_BURST1.jpgParty//storage/emulated/0/DCIM/Camera/IMG_20151023_132502_BURST2.jpg"]},{"id":"144","solevar":"2855dc6d93130401","userid":"37bf4def170e3c31","model":"","dataid":"48","content":"得得客厅陆军","addtime":"1452907640","isdel":"2","pic":["Party//storage/emulated/0/BRDownload/1444894634375y.png","Party//storage/emulated/0/BRDownload/1444894634375y.pngParty//storage/emulated/0/image.jpg"]}]
     */

    private DataEntity data;
    private String info;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setInfo(String info) {
        this.info = info;
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

    public static class DataEntity {
        private String count;
        private int maxPage;
        /**
         * id : 151
         * solevar : 2be6d16fee36f95f
         * userid : 37bf4def170e3c31
         * model :
         * dataid : 48
         * content : 铁路局了
         * addtime : 1452926284
         * isdel : 2
         * pic : ["Party/14529264665171452844687162.jpg","Party/14529264665291452847623215.jpg"]
         */

        private List<ListEntity> list;

        public void setCount(String count) {
            this.count = count;
        }

        public void setMaxPage(int maxPage) {
            this.maxPage = maxPage;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public String getCount() {
            return count;
        }

        public int getMaxPage() {
            return maxPage;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private String id;
            private String solevar;
            private String userid;
            private String model;
            private String dataid;
            private String content;
            private String addtime;
            private String isdel;
            private List<String> pic;

            public void setId(String id) {
                this.id = id;
            }

            public void setSolevar(String solevar) {
                this.solevar = solevar;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setIsdel(String isdel) {
                this.isdel = isdel;
            }

            public void setPic(List<String> pic) {
                this.pic = pic;
            }

            public String getId() {
                return id;
            }

            public String getSolevar() {
                return solevar;
            }

            public String getUserid() {
                return userid;
            }

            public String getModel() {
                return model;
            }

            public String getDataid() {
                return dataid;
            }

            public String getContent() {
                return content;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getIsdel() {
                return isdel;
            }

            public List<String> getPic() {
                return pic;
            }
        }
    }
}
