package org.thvc.happyi.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import org.thvc.happyi.R;
import org.thvc.happyi.activity.PaymentActivity;
import org.thvc.happyi.bean.RegisterInformationBean;
import org.thvc.happyi.utils.ImgUtils;
import org.thvc.happyi.view.CancelPartyDialogNew;
import org.thvc.happyi.view.CircleImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by huangxinqi on 2016/3/25.
 */
public class RegisterInfoAdapter extends BaseAdapter {
    private String dataId;
    private Context context;
    private List<RegisterInformationBean.DataEntity.ListEntity> list;
    private String title;
    private Handler handler;
    private String isdel;

    public RegisterInfoAdapter(Handler handler, Context context, List<RegisterInformationBean.DataEntity.ListEntity> list, String title, String dataId, String isdel) {
        this.context = context;
        this.list = list;
        this.dataId = dataId;
        this.title = title;
        this.handler = handler;
        this.isdel = isdel;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_information_new, null);
            vh.iv_my_img = (CircleImageView) convertView.findViewById(R.id.iv_my_img);
            vh.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            vh.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            vh.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            vh.iv_more = (ImageView) convertView.findViewById(R.id.iv_more);
            vh.tv_ispay = (TextView) convertView.findViewById(R.id.tv_ispay);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (list.get(position).getIspay().equals("1")) {
            vh.tv_ispay.setText("付款成功");
            vh.tv_money.setText(list.get(position).getPayfee());
        } else if (list.get(position).getIspay().equals("2")) {
            vh.tv_money.setText("未支付");
            vh.tv_ispay.setText("");
        }
        vh.tv_name.setText(list.get(position).getRealname());
        vh.tv_time.setText(list.get(position).getTime());
        ImgUtils.setHeadImage(vh.iv_my_img, list.get(position).getHeadpic());
        vh.iv_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyPartyBottomDialog myPartyBottomDialog=new MyPartyBottomDialog(context,list.get(position),handler,title,dataId,getStringTime(list.get(position).getAddtime()),list.get(position).getId(),list.get(position).getIspay());
                new PopupWindows(context, v, list.get(position));
            }
        });

        return convertView;
    }

    class ViewHolder {
        CircleImageView iv_my_img;
        TextView tv_name;
        TextView tv_money;
        TextView tv_time;
        ImageView iv_more;
        TextView tv_ispay;
    }

    public String getStringTime(String cc_time) {
        String re_Strtime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        long lcc_time = Long.valueOf(cc_time);
        re_Strtime = sdf.format(new Date(lcc_time * 1000L));
        return re_Strtime;
    }

    public class PopupWindows extends PopupWindow {
        public PopupWindows(Context mContext, View parent, final RegisterInformationBean.DataEntity.ListEntity bean) {
            View view = View.inflate(mContext, R.layout.item_popup_my_party, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view.findViewById(R.id.ll_popup);
            ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext, R.anim.push_bottom_in));
            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            //修改高度显示，解决被手机底部虚拟键挡住的问题
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            //实例化一个ColorDrawable颜色为半透明
            setBackgroundDrawable(new ColorDrawable(0xb0000000));
            //menuview添加ontouchlistener监听判断获取触屏位置如果在选择框外面则销毁弹出框
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int height = view.findViewById(R.id.ll_popup).getTop();
                    int y = (int) motionEvent.getY();
                    if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                        if (y < height) {
                            dismiss();
                        }
                    }
                    return true;
                }
            });
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            update();
            Button btn_qr_code = (Button) view.findViewById(R.id.btn_qr_code);//立即支付
            Button btn_cancel_party = (Button) view.findViewById(R.id.btn_cancel_party);//取消活动
            Button btn_cancel = (Button) view.findViewById(R.id.btn_cancel);//取消按钮
            if (bean.getIspay().equals("1")) {
                btn_qr_code.setVisibility(View.GONE);
                btn_cancel_party.setBackgroundResource(R.drawable.btn_oval_sharp_selector);
            }
            btn_qr_code.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    double price = Double.parseDouble(bean.getPayfee());
                    if (bean.getIspay().equals("2") && price == 0) {
                        Toast.makeText(context, "经过配比后价格为0元，无需支付", Toast.LENGTH_SHORT).show();
                    } else if (bean.getIspay().equals("2") && price > 0) {
                        context.startActivity(new Intent(context, PaymentActivity.class).putExtra("joined", bean.getId()).putExtra("payment", "erci").putExtra("dataId", dataId));
                    }
                    dismiss();
                }
            });
            btn_cancel_party.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    CancelPartyDialogNew.CancelPartyListener myListener = new CancelPartyDialogNew.CancelPartyListener() {
                        @Override
                        public void onCancel() {
                            new Thread() {
                                @Override
                                public void run() {
                                    //list.clear(); 不记得这句话是干啥的...
                                    handler.sendEmptyMessage(1000);
                                }
                            }.start();
                        }
                    };
                    CancelPartyDialogNew cancelPartyDialog = new CancelPartyDialogNew(myListener, context, title, dataId, getStringTime(bean.getAddtime()), bean.getIspay(), bean.getId(), bean.getPayfee());
                    dismiss();

                }
            });

            btn_cancel.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
}
