package org.thvc.happyi.bean;



/**
 * Created by Administrator on 2015/12/4.
 */
public class MoneyJoinPaynoBean {
    /**
     * data : {"data":{"ispay":"2","isjoin":"1","sex":"2","userid":"d192a4c4e49e1237","isrefund":"2","realname":"张玺1","payfee":"100.00","dataid":"7","addtime":"1448259283","model":"","tel":"13786104732","id":"20","job":"互联网","age":"30","first":"1","party":{"totalpep":"100","small":"2","datetype":"1","actdata":"2015年11月1日 至 2015年11月5日 ","defnum":"5.0","keywords":"","getpre":"100.00","joinpep":"0","userid":"d192a4c4e49e4312","getpep":"100","good":"1","equity":"总体费用2W，人人可参与","isget":"2","totalfee":"20000.00","prefee":"200.00","id":"7","budget":"总体费用2W","enrollend":"1448364414","sort":"1","orgid":"41cab0d56c7b57e4","recnum":"1","enjoyment":"2","actbegin":"1448537214","adminid":"","detail":"岳阳","collect":"4","status":"2","joining":"2","actcontact":"小唐","actjob":"技术部","actemail":"tangbolong@vfund.org","line":"通平高速，安定收费站出口2Km处","fennum":"5.0","description":"安定老人院，关爱老年人，人人有责","creatment":"2","solevar":"473c7518f0c42ac6","title":"安定老人院，关爱老年人","issupport":"2","result":"老人需要定期去关怀","organize":"平江县民政局","cateid":"0","gettotal":"10000.00","isdel":"6","address":"湖南","acttel":"15387318982","spread":"2","site":null,"addtime":"1448110888","persistence":"2","step":"3"}},"modelid":"Party","paymenturl":"https://mapi.alipay.com/gateway.do?_input_charset=utf-8&body=%E5%BF%AB%E4%B9%90%E7%9B%8A&notify_url=http%3A%2F%2F192.168.100.212%2Fklapi%2Fwechat.php%2FOrder%2Fpaynotify%2Fpayno%2F151204111545854&out_trade_no=151204111545854&partner=2088911923491539&payment_type=1&return_url=http%3A%2F%2F192.168.100.212%2Fklapi%2Fwechat.php%2FOrder%2Fpaysuccess%2Fpayno%2F151204111545854&seller_email=happiyi01%40163.com&service=create_direct_pay_by_user&show_url=http%3A%2F%2F192.168.100.212%2Fklapi%2Fwechat.php%2FOrder%2Fpayment%2Fpayno%2F151204111545854&subject=%E5%BF%AB%E4%B9%90%E7%9B%8A&total_fee=0.01&sign=a974f48b80a71ceb05b333647d5cca78&sign_type=MD5","userid":"d192a4c4e49e1237","payfee":"100.00","payno":"151204111545854","site":"2","dataid":"20","addtime":1449198945,"siteid":"2","addid":"d192a4c4e49e1237","tableid":"Party","payment":"Alipay","id":106}
     * status : 1
     * info :
     */
    private DataEntity data;
    private int status;
    private String info;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public DataEntity getData() {
        return data;
    }

    public int getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public static class DataEntity {
        /**
         * data : {"ispay":"2","isjoin":"1","sex":"2","userid":"d192a4c4e49e1237","isrefund":"2","realname":"张玺1","payfee":"100.00","dataid":"7","addtime":"1448259283","model":"","tel":"13786104732","id":"20","job":"互联网","age":"30","first":"1","party":{"totalpep":"100","small":"2","datetype":"1","actdata":"2015年11月1日 至 2015年11月5日 ","defnum":"5.0","keywords":"","getpre":"100.00","joinpep":"0","userid":"d192a4c4e49e4312","getpep":"100","good":"1","equity":"总体费用2W，人人可参与","isget":"2","totalfee":"20000.00","prefee":"200.00","id":"7","budget":"总体费用2W","enrollend":"1448364414","sort":"1","orgid":"41cab0d56c7b57e4","recnum":"1","enjoyment":"2","actbegin":"1448537214","adminid":"","detail":"岳阳","collect":"4","status":"2","joining":"2","actcontact":"小唐","actjob":"技术部","actemail":"tangbolong@vfund.org","line":"通平高速，安定收费站出口2Km处","fennum":"5.0","description":"安定老人院，关爱老年人，人人有责","creatment":"2","solevar":"473c7518f0c42ac6","title":"安定老人院，关爱老年人","issupport":"2","result":"老人需要定期去关怀","organize":"平江县民政局","cateid":"0","gettotal":"10000.00","isdel":"6","address":"湖南","acttel":"15387318982","spread":"2","site":null,"addtime":"1448110888","persistence":"2","step":"3"}}
         * modelid : Party
         * paymenturl : https://mapi.alipay.com/gateway.do?_input_charset=utf-8&body=%E5%BF%AB%E4%B9%90%E7%9B%8A&notify_url=http%3A%2F%2F192.168.100.212%2Fklapi%2Fwechat.php%2FOrder%2Fpaynotify%2Fpayno%2F151204111545854&out_trade_no=151204111545854&partner=2088911923491539&payment_type=1&return_url=http%3A%2F%2F192.168.100.212%2Fklapi%2Fwechat.php%2FOrder%2Fpaysuccess%2Fpayno%2F151204111545854&seller_email=happiyi01%40163.com&service=create_direct_pay_by_user&show_url=http%3A%2F%2F192.168.100.212%2Fklapi%2Fwechat.php%2FOrder%2Fpayment%2Fpayno%2F151204111545854&subject=%E5%BF%AB%E4%B9%90%E7%9B%8A&total_fee=0.01&sign=a974f48b80a71ceb05b333647d5cca78&sign_type=MD5
         * userid : d192a4c4e49e1237
         * payfee : 100.00
         * payno : 151204111545854
         * site : 2
         * dataid : 20
         * addtime : 1449198945
         * siteid : 2
         * addid : d192a4c4e49e1237
         * tableid : Party
         * payment : Alipay
         * id : 106
         */
        private DataEntity1 data;
        private String modelid;
        private String paymenturl;
        private String userid;
        private String payfee;
        private String payno;
        private String site;
        private String dataid;
        private int addtime;
        private String siteid;
        private String addid;
        private String tableid;
        private String payment;
        private int id;

        public void setData(DataEntity1 data) {
            this.data = data;
        }

        public void setModelid(String modelid) {
            this.modelid = modelid;
        }

        public void setPaymenturl(String paymenturl) {
            this.paymenturl = paymenturl;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public void setPayfee(String payfee) {
            this.payfee = payfee;
        }

        public void setPayno(String payno) {
            this.payno = payno;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public void setDataid(String dataid) {
            this.dataid = dataid;
        }

        public void setAddtime(int addtime) {
            this.addtime = addtime;
        }

        public void setSiteid(String siteid) {
            this.siteid = siteid;
        }

        public void setAddid(String addid) {
            this.addid = addid;
        }

        public void setTableid(String tableid) {
            this.tableid = tableid;
        }

        public void setPayment(String payment) {
            this.payment = payment;
        }

        public void setId(int id) {
            this.id = id;
        }

        public DataEntity1 getData() {
            return data;
        }

        public String getModelid() {
            return modelid;
        }

        public String getPaymenturl() {
            return paymenturl;
        }

        public String getUserid() {
            return userid;
        }

        public String getPayfee() {
            return payfee;
        }

        public String getPayno() {
            return payno;
        }

        public String getSite() {
            return site;
        }

        public String getDataid() {
            return dataid;
        }

        public int getAddtime() {
            return addtime;
        }

        public String getSiteid() {
            return siteid;
        }

        public String getAddid() {
            return addid;
        }

        public String getTableid() {
            return tableid;
        }

        public String getPayment() {
            return payment;
        }

        public int getId() {
            return id;
        }

        public static class DataEntity1 {
            /**
             * ispay : 2
             * isjoin : 1
             * sex : 2
             * userid : d192a4c4e49e1237
             * isrefund : 2
             * realname : 张玺1
             * payfee : 100.00
             * dataid : 7
             * addtime : 1448259283
             * model :
             * tel : 13786104732
             * id : 20
             * job : 互联网
             * age : 30
             * first : 1
             * party : {"totalpep":"100","small":"2","datetype":"1","actdata":"2015年11月1日 至 2015年11月5日 ","defnum":"5.0","keywords":"","getpre":"100.00","joinpep":"0","userid":"d192a4c4e49e4312","getpep":"100","good":"1","equity":"总体费用2W，人人可参与","isget":"2","totalfee":"20000.00","prefee":"200.00","id":"7","budget":"总体费用2W","enrollend":"1448364414","sort":"1","orgid":"41cab0d56c7b57e4","recnum":"1","enjoyment":"2","actbegin":"1448537214","adminid":"","detail":"岳阳","collect":"4","status":"2","joining":"2","actcontact":"小唐","actjob":"技术部","actemail":"tangbolong@vfund.org","line":"通平高速，安定收费站出口2Km处","fennum":"5.0","description":"安定老人院，关爱老年人，人人有责","creatment":"2","solevar":"473c7518f0c42ac6","title":"安定老人院，关爱老年人","issupport":"2","result":"老人需要定期去关怀","organize":"平江县民政局","cateid":"0","gettotal":"10000.00","isdel":"6","address":"湖南","acttel":"15387318982","spread":"2","site":null,"addtime":"1448110888","persistence":"2","step":"3"}
             */
            private String ispay;
            private String isjoin;
            private String sex;
            private String userid;
            private String isrefund;
            private String realname;
            private String payfee;
            private String dataid;
            private String addtime;
            private String model;
            private String tel;
            private String id;
            private String job;
            private String age;
            private String first;
            private PartyEntity party;

            public void setIspay(String ispay) {
                this.ispay = ispay;
            }

            public void setIsjoin(String isjoin) {
                this.isjoin = isjoin;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public void setUserid(String userid) {
                this.userid = userid;
            }

            public void setIsrefund(String isrefund) {
                this.isrefund = isrefund;
            }

            public void setRealname(String realname) {
                this.realname = realname;
            }

            public void setPayfee(String payfee) {
                this.payfee = payfee;
            }

            public void setDataid(String dataid) {
                this.dataid = dataid;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public void setModel(String model) {
                this.model = model;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setJob(String job) {
                this.job = job;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public void setFirst(String first) {
                this.first = first;
            }

            public void setParty(PartyEntity party) {
                this.party = party;
            }

            public String getIspay() {
                return ispay;
            }

            public String getIsjoin() {
                return isjoin;
            }

            public String getSex() {
                return sex;
            }

            public String getUserid() {
                return userid;
            }

            public String getIsrefund() {
                return isrefund;
            }

            public String getRealname() {
                return realname;
            }

            public String getPayfee() {
                return payfee;
            }

            public String getDataid() {
                return dataid;
            }

            public String getAddtime() {
                return addtime;
            }

            public String getModel() {
                return model;
            }

            public String getTel() {
                return tel;
            }

            public String getId() {
                return id;
            }

            public String getJob() {
                return job;
            }

            public String getAge() {
                return age;
            }

            public String getFirst() {
                return first;
            }

            public PartyEntity getParty() {
                return party;
            }

            public static class PartyEntity {
                /**
                 * totalpep : 100
                 * small : 2
                 * datetype : 1
                 * actdata : 2015年11月1日 至 2015年11月5日
                 * defnum : 5.0
                 * keywords :
                 * getpre : 100.00
                 * joinpep : 0
                 * userid : d192a4c4e49e4312
                 * getpep : 100
                 * good : 1
                 * equity : 总体费用2W，人人可参与
                 * isget : 2
                 * totalfee : 20000.00
                 * prefee : 200.00
                 * id : 7
                 * budget : 总体费用2W
                 * enrollend : 1448364414
                 * sort : 1
                 * orgid : 41cab0d56c7b57e4
                 * recnum : 1
                 * enjoyment : 2
                 * actbegin : 1448537214
                 * adminid :
                 * detail : 岳阳
                 * collect : 4
                 * status : 2
                 * joining : 2
                 * actcontact : 小唐
                 * actjob : 技术部
                 * actemail : tangbolong@vfund.org
                 * line : 通平高速，安定收费站出口2Km处
                 * fennum : 5.0
                 * description : 安定老人院，关爱老年人，人人有责
                 * creatment : 2
                 * solevar : 473c7518f0c42ac6
                 * title : 安定老人院，关爱老年人
                 * issupport : 2
                 * result : 老人需要定期去关怀
                 * organize : 平江县民政局
                 * cateid : 0
                 * gettotal : 10000.00
                 * isdel : 6
                 * address : 湖南
                 * acttel : 15387318982
                 * spread : 2
                 * site : null
                 * addtime : 1448110888
                 * persistence : 2
                 * step : 3
                 */
                private String totalpep;
                private String small;
                private String datetype;
                private String actdata;
                private String defnum;
                private String keywords;
                private String getpre;
                private String joinpep;
                private String userid;
                private String getpep;
                private String good;
                private String equity;
                private String isget;
                private String totalfee;
                private String prefee;
                private String id;
                private String budget;
                private String enrollend;
                private String sort;
                private String orgid;
                private String recnum;
                private String enjoyment;
                private String actbegin;
                private String adminid;
                private String detail;
                private String collect;
                private String status;
                private String joining;
                private String actcontact;
                private String actjob;
                private String actemail;
                private String line;
                private String fennum;
                private String description;
                private String creatment;
                private String solevar;
                private String title;
                private String issupport;
                private String result;
                private String organize;
                private String cateid;
                private String gettotal;
                private String isdel;
                private String address;
                private String acttel;
                private String spread;
                private String site;
                private String addtime;
                private String persistence;
                private String step;

                public void setTotalpep(String totalpep) {
                    this.totalpep = totalpep;
                }

                public void setSmall(String small) {
                    this.small = small;
                }

                public void setDatetype(String datetype) {
                    this.datetype = datetype;
                }

                public void setActdata(String actdata) {
                    this.actdata = actdata;
                }

                public void setDefnum(String defnum) {
                    this.defnum = defnum;
                }

                public void setKeywords(String keywords) {
                    this.keywords = keywords;
                }

                public void setGetpre(String getpre) {
                    this.getpre = getpre;
                }

                public void setJoinpep(String joinpep) {
                    this.joinpep = joinpep;
                }

                public void setUserid(String userid) {
                    this.userid = userid;
                }

                public void setGetpep(String getpep) {
                    this.getpep = getpep;
                }

                public void setGood(String good) {
                    this.good = good;
                }

                public void setEquity(String equity) {
                    this.equity = equity;
                }

                public void setIsget(String isget) {
                    this.isget = isget;
                }

                public void setTotalfee(String totalfee) {
                    this.totalfee = totalfee;
                }

                public void setPrefee(String prefee) {
                    this.prefee = prefee;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public void setBudget(String budget) {
                    this.budget = budget;
                }

                public void setEnrollend(String enrollend) {
                    this.enrollend = enrollend;
                }

                public void setSort(String sort) {
                    this.sort = sort;
                }

                public void setOrgid(String orgid) {
                    this.orgid = orgid;
                }

                public void setRecnum(String recnum) {
                    this.recnum = recnum;
                }

                public void setEnjoyment(String enjoyment) {
                    this.enjoyment = enjoyment;
                }

                public void setActbegin(String actbegin) {
                    this.actbegin = actbegin;
                }

                public void setAdminid(String adminid) {
                    this.adminid = adminid;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public void setCollect(String collect) {
                    this.collect = collect;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public void setJoining(String joining) {
                    this.joining = joining;
                }

                public void setActcontact(String actcontact) {
                    this.actcontact = actcontact;
                }

                public void setActjob(String actjob) {
                    this.actjob = actjob;
                }

                public void setActemail(String actemail) {
                    this.actemail = actemail;
                }

                public void setLine(String line) {
                    this.line = line;
                }

                public void setFennum(String fennum) {
                    this.fennum = fennum;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public void setCreatment(String creatment) {
                    this.creatment = creatment;
                }

                public void setSolevar(String solevar) {
                    this.solevar = solevar;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setIssupport(String issupport) {
                    this.issupport = issupport;
                }

                public void setResult(String result) {
                    this.result = result;
                }

                public void setOrganize(String organize) {
                    this.organize = organize;
                }

                public void setCateid(String cateid) {
                    this.cateid = cateid;
                }

                public void setGettotal(String gettotal) {
                    this.gettotal = gettotal;
                }

                public void setIsdel(String isdel) {
                    this.isdel = isdel;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public void setActtel(String acttel) {
                    this.acttel = acttel;
                }

                public void setSpread(String spread) {
                    this.spread = spread;
                }

                public void setSite(String site) {
                    this.site = site;
                }

                public void setAddtime(String addtime) {
                    this.addtime = addtime;
                }

                public void setPersistence(String persistence) {
                    this.persistence = persistence;
                }

                public void setStep(String step) {
                    this.step = step;
                }

                public String getTotalpep() {
                    return totalpep;
                }

                public String getSmall() {
                    return small;
                }

                public String getDatetype() {
                    return datetype;
                }

                public String getActdata() {
                    return actdata;
                }

                public String getDefnum() {
                    return defnum;
                }

                public String getKeywords() {
                    return keywords;
                }

                public String getGetpre() {
                    return getpre;
                }

                public String getJoinpep() {
                    return joinpep;
                }

                public String getUserid() {
                    return userid;
                }

                public String getGetpep() {
                    return getpep;
                }

                public String getGood() {
                    return good;
                }

                public String getEquity() {
                    return equity;
                }

                public String getIsget() {
                    return isget;
                }

                public String getTotalfee() {
                    return totalfee;
                }

                public String getPrefee() {
                    return prefee;
                }

                public String getId() {
                    return id;
                }

                public String getBudget() {
                    return budget;
                }

                public String getEnrollend() {
                    return enrollend;
                }

                public String getSort() {
                    return sort;
                }

                public String getOrgid() {
                    return orgid;
                }

                public String getRecnum() {
                    return recnum;
                }

                public String getEnjoyment() {
                    return enjoyment;
                }

                public String getActbegin() {
                    return actbegin;
                }

                public String getAdminid() {
                    return adminid;
                }

                public String getDetail() {
                    return detail;
                }

                public String getCollect() {
                    return collect;
                }

                public String getStatus() {
                    return status;
                }

                public String getJoining() {
                    return joining;
                }

                public String getActcontact() {
                    return actcontact;
                }

                public String getActjob() {
                    return actjob;
                }

                public String getActemail() {
                    return actemail;
                }

                public String getLine() {
                    return line;
                }

                public String getFennum() {
                    return fennum;
                }

                public String getDescription() {
                    return description;
                }

                public String getCreatment() {
                    return creatment;
                }

                public String getSolevar() {
                    return solevar;
                }

                public String getTitle() {
                    return title;
                }

                public String getIssupport() {
                    return issupport;
                }

                public String getResult() {
                    return result;
                }

                public String getOrganize() {
                    return organize;
                }

                public String getCateid() {
                    return cateid;
                }

                public String getGettotal() {
                    return gettotal;
                }

                public String getIsdel() {
                    return isdel;
                }

                public String getAddress() {
                    return address;
                }

                public String getActtel() {
                    return acttel;
                }

                public String getSpread() {
                    return spread;
                }

                public String getSite() {
                    return site;
                }

                public String getAddtime() {
                    return addtime;
                }

                public String getPersistence() {
                    return persistence;
                }

                public String getStep() {
                    return step;
                }
            }
        }
    }


}
