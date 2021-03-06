package com.defence.costomapp.activity.statistics;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.defence.costomapp.R;
import com.defence.costomapp.base.BaseActivity;
import com.defence.costomapp.fragment.DingdanFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DingdanNewActivity extends BaseActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.liear_left)
    LinearLayout liearLeft;
    @BindView(R.id.middle_title)
    TextView middleTitle;
    @BindView(R.id.right_title)
    TextView rightTitle;
    @BindView(R.id.right_icon)
    ImageView rightIcon;
    @BindView(R.id.liear_right)
    LinearLayout liearRight;

    @BindView(R.id.usertj_LL)
    LinearLayout usertjLL;

    private PopupWindow pop;
    private int type = 4;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dingdan_new);
        ButterKnife.bind(this);
        middleTitle.setText("交易成功");
        rightIcon.setImageResource(R.mipmap.all);
        if (fragmentManager == null)
            fragmentManager = DingdanNewActivity.this.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.usertj_LL, DingdanFragment.newInstance(type)).commitAllowingStateLoss();
        initpopdialog();

    }

    private void initpopdialog() {

        DialogOnclickListener dialogOnclickListenerdd = new DialogOnclickListener();

        View view = getLayoutInflater().inflate(R.layout.pop_dialog_dingd, null);
        pop = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
//
        view.findViewById(R.id.tv_jiaoyisucc).setOnClickListener(dialogOnclickListenerdd);
        view.findViewById(R.id.tv_daichuhuo).setOnClickListener(dialogOnclickListenerdd);
        view.findViewById(R.id.tv_tuikuan).setOnClickListener(dialogOnclickListenerdd);
        view.findViewById(R.id.tv_alllist).setOnClickListener(dialogOnclickListenerdd);

        pop.setOutsideTouchable(true);
        pop.setFocusable(true);// 点击back退出pop
        pop.setAnimationStyle(R.style.add_new_style);

        pop.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1f;
                getWindow().setAttributes(lp);
            }
        });
        pop.setBackgroundDrawable(new ColorDrawable(Color.argb(136, 0, 0, 0)));// 设置背景透明，点击back退出pop
        rightIcon.setImageResource(R.mipmap.all);
        rightIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (pop.isShowing()) {
                    pop.dismiss();
                } else {
                    pop.showAtLocation(view, Gravity.BOTTOM, 0, -560);//在父控件下方出来
                    pop.showAsDropDown(view);
                    // 设置背景颜色变暗
                    WindowManager.LayoutParams lp = getWindow().getAttributes();
                    lp.alpha = 0.7f;
                    getWindow().setAttributes(lp);
                }
            }
        });

    }

    @OnClick(R.id.liear_left)
    public void onViewClicked() {
        finish();
    }

    private void replaceFragment(FragmentTransaction tf, int type) {
        tf.replace(R.id.usertj_LL, DingdanFragment.newInstance(type));
        tf.commitAllowingStateLoss();
    }

    private class DialogOnclickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {

            FragmentTransaction tf = fragmentManager.beginTransaction();

            switch (view.getId()) {
                case R.id.tv_jiaoyisucc:
                    middleTitle.setText("交易成功");
                    type = 4;
                    replaceFragment(tf, type);
                    break;
                case R.id.tv_daichuhuo:
                    middleTitle.setText("待出货");
                    type = 3;
                    replaceFragment(tf, type);
                    break;
                case R.id.tv_tuikuan:
                    middleTitle.setText("退款成功");
                    type = 5;
                    replaceFragment(tf, type);
                    break;
                case R.id.tv_alllist:
                    middleTitle.setText("全部订单");
                    type = 0;
                    replaceFragment(tf, type);
                    break;
            }
            if (pop.isShowing()) {
                pop.dismiss();
            }
        }
    }
}
