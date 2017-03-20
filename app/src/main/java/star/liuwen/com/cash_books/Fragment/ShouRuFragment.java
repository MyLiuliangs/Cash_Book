package star.liuwen.com.cash_books.Fragment;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bigkoo.pickerview.TimePickerView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnItemChildLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemLongClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Activity.EditIncomeAndCostActivity;
import star.liuwen.com.cash_books.Adapter.PopWindowAdapter;
import star.liuwen.com.cash_books.Adapter.ZhiChuAdapter;
import star.liuwen.com.cash_books.Base.BaseFragment;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoChoiceAccount;
import star.liuwen.com.cash_books.Dao.DaoShouRuModel;
import star.liuwen.com.cash_books.Dao.DaoZhiChuModel;
import star.liuwen.com.cash_books.Dialog.TipandEditDialog;
import star.liuwen.com.cash_books.MainActivity;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.RxBus.RxBusResult;
import star.liuwen.com.cash_books.Utils.DateTimeUtil;
import star.liuwen.com.cash_books.Utils.SnackBarUtil;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.ShouRuModel;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/1/5.
 */
public class ShouRuFragment extends BaseFragment implements View.OnClickListener, BGAOnItemChildClickListener, BGAOnRVItemClickListener, BGAOnRVItemLongClickListener {

    private List<ShouRuModel> mList;
    private RecyclerView mRecyclerView;
    private ZhiChuAdapter mAdapter;
    private EditText edName;
    private ImageView imageName;
    private TextView txtName, tvData, tvZhanghu, tvSure;
    private int position, AccountUrl;
    private PopupWindow window;
    private List<AccountModel> homListData;
    private String AccountType, AccountData, AccountConsumeType;
    private ListView mListView;
    private PopWindowAdapter mPopWindowAdapter;
    private TimePickerView pvTime;
    private boolean isShowDelete = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        setContentView(R.layout.fragment_shouru);
        initView();
        initData();
        return getContentView();
    }

    private void initView() {
        mRecyclerView = (RecyclerView) getContentView().findViewById(R.id.f_shouru_recycler);
        tvData = (TextView) getContentView().findViewById(R.id.f_shouru_data);
        tvZhanghu = (TextView) getContentView().findViewById(R.id.f_shouru_zhanghu);
        tvSure = (TextView) getContentView().findViewById(R.id.f_shouru_sure);
        tvData.setOnClickListener(this);
        tvZhanghu.setOnClickListener(this);
        tvSure.setOnClickListener(this);


        View headView = View.inflate(getActivity(), R.layout.zhichu_shouru_head, null);
        edName = (EditText) headView.findViewById(R.id.zhichu_name);
        imageName = (ImageView) headView.findViewById(R.id.imag_name);
        txtName = (TextView) headView.findViewById(R.id.txt_name);

        mAdapter = new ZhiChuAdapter(mRecyclerView);
        mAdapter.addHeaderView(headView);

        final GridLayoutManager manager = new GridLayoutManager(getActivity(), 5, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mList = new ArrayList<>();
        if (DaoShouRuModel.query().size() != 0) {
            mList = DaoShouRuModel.query();
            mAdapter.setData(mList);
            mRecyclerView.setAdapter(mAdapter.getHeaderAndFooterAdapter());
        }
        mAdapter.addLastItem(new ShouRuModel(DaoShouRuModel.getCount(), R.mipmap.icon_add, "编辑"));

        mAdapter.setOnRVItemLongClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setOnRVItemClickListener(this);
    }

    @Override
    public void onItemChildClick(ViewGroup parent, View childView, int position) {
        if (childView.getId() == R.id.item_cha) {
            mAdapter.removeItem(position);
            DaoShouRuModel.deleteShouRuByModel(DaoShouRuModel.query().get(position));
        }
    }

    @Override
    public void onRVItemClick(ViewGroup parent, View itemView, int position) {
        if (mAdapter.getItemCount() - 1 == position) {
            Intent intent = new Intent(getActivity(), EditIncomeAndCostActivity.class);
            intent.putExtra(Config.OTHER, Config.SHOU_RU);
            startActivity(intent);
        } else {
            txtName.setText(mList.get(position).getName());
            imageName.setImageResource(mList.get(position).getUrl());
            AccountConsumeType = mList.get(position).getName();
            AccountUrl = mList.get(position).getUrl();
        }
    }

    @Override
    public boolean onRVItemLongClick(ViewGroup parent, View itemView, int position) {
        if (isShowDelete) {
            isShowDelete = false;
        } else {
            isShowDelete = true;
        }
        mAdapter.setShowDelete(isShowDelete);
        mAdapter.setClickItemIndex(position);
        return true;
    }

    private void initData() {
        RxBus.getInstance().toObserverableOnMainThread(Config.RxToSHouRu, new RxBusResult() {
            @Override
            public void onRxBusResult(Object o) {
                ShouRuModel model = (ShouRuModel) o;
                mAdapter.addItem(mList.size() - 1, model);

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getInstance().removeObserverable(Config.RxToSHouRu);
        ToastUtils.removeToast();
    }


    @Override
    public void onClick(View v) {
        if (v == edName) {

        } else if (v == tvZhanghu) {
            showZhanghu();
            if (window.isShowing()) {
                window.dismiss();
            } else {
                window.showAtLocation(tvZhanghu, Gravity.BOTTOM, 0, 0);
                backgroundAlpha(0.5f);
            }
        } else if (v == tvData) {
            showData();
        } else if (v == tvSure) {
            doSure();
        }
    }

    private void doSure() {
        homListData = new ArrayList<>();
        String mEdName = edName.getText().toString();
        if (TextUtils.isEmpty(mEdName.trim())) {
            ToastUtils.showToast(getActivity(), "请输入金额");
            return;
        }
        if (tvZhanghu.getText().toString().equals("账户")) {
            ToastUtils.showToast(getActivity(), "请选择账户");
            return;
        }

        if (tvData.getText().toString().equals("选择日期")) {
            ToastUtils.showToast(getActivity(), "请选择日期");
        }

        homListData.add(new AccountModel(AccountType, AccountData, Double.parseDouble(mEdName), AccountConsumeType, AccountUrl, DateTimeUtil.getCurrentTime_Today(), Config.SHOU_RU));
        RxBus.getInstance().post("AccountModel", homListData);
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.putExtra("id", 1);
        startActivity(intent);
        getActivity().finish();
    }


    private void showZhanghu() {
        View popView = View.inflate(getActivity(), R.layout.pop_zhanghu_dialog, null);
        mListView = (ListView) popView.findViewById(R.id.lv_popup_list);
        window = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        mPopWindowAdapter = new PopWindowAdapter(getActivity(), R.layout.item_pop_account);
        if (DaoChoiceAccount.query().size() != 0 || DaoChoiceAccount.query() != null) {
            mPopWindowAdapter.setData(DaoChoiceAccount.query());
        }
        mListView.setAdapter(mPopWindowAdapter);
        mPopWindowAdapter.removeItem(mPopWindowAdapter.getLastItem());
        mPopWindowAdapter.removeItem(mPopWindowAdapter.getItem(4));
        window.setFocusable(true);
        window.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        window.setAnimationStyle(R.style.AnimBottom);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                window.dismiss();
                AccountType = mPopWindowAdapter.getItem(position).getAccountName();
                tvZhanghu.setText(AccountType);
            }
        });

        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setOutsideTouchable(true);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha(1f);
            }
        });
    }

    /**
     * 设置添加屏幕的背景透明
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }


    public void showData() {
        //时间选择器
        pvTime = new TimePickerView(getActivity(), TimePickerView.Type.YEAR_MONTH_DAY);
        //设置标题
        pvTime.setTitle("选择日期");
        //控制时间范围
        Calendar calendar = Calendar.getInstance();
        pvTime.setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 30);
        pvTime.setTime(new Date());
        //设置是否循环
        pvTime.setCyclic(false);
        //设置是否可以关闭
        pvTime.setCancelable(true);
        //设置选择监听
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                tvData.setText(DateTimeUtil.getYearMonthDay_(date));
                AccountData = DateTimeUtil.getYearMonthDay_(date);
            }
        });
        //显示
        pvTime.show();
    }


    private class ZhiChuAdapter extends BGARecyclerViewAdapter<ShouRuModel> {
        private boolean isShowDelete;//根据这个变量来判断是否显示删除图标，true是显示，false是不显示
        private int clickItemIndex = -1;//根据这个变量来辨识选中的current值

        protected void setShowDelete(boolean isShowDelete) {
            this.isShowDelete = isShowDelete;
            mAdapter.notifyDataSetChangedWrapper();
        }

        protected boolean getShowDelete() {
            return isShowDelete;
        }

        protected void setClickItemIndex(int postion) {
            this.clickItemIndex = postion;
        }


        public ZhiChuAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_zhichu_fragment);
        }

        @Override
        protected void setItemChildListener(BGAViewHolderHelper helper, int viewType) {
            super.setItemChildListener(helper, viewType);
            helper.setItemChildClickListener(R.id.item_cha);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ShouRuModel model) {
            TranslateAnimation animation = new TranslateAnimation(1, 4, 1, 2);
            animation.setInterpolator(new OvershootInterpolator());
            animation.setDuration(100);
            animation.setRepeatCount(Animation.INFINITE);
            animation.setRepeatMode(Animation.REVERSE);
            helper.setImageResource(R.id.item_imag, model.getUrl());
            helper.setText(R.id.item_name, model.getName());
            if (position == mList.size() - 1) {
                helper.setVisibility(R.id.item_cha, View.GONE);
            } else {
                helper.setVisibility(R.id.item_cha, isShowDelete ? View.VISIBLE : View.GONE);
                if (isShowDelete) {
                    helper.getImageView(R.id.item_imag).startAnimation(animation);
                }
            }
        }

//    private void showData() {
//        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
//        dialog.show();
//        DatePicker picker = new DatePicker(getActivity());
//        picker.setDate(Integer.parseInt(DateTimeUtil.getShowCurrentTime()[0]), Integer.parseInt(DateTimeUtil.getShowCurrentTime()[1]));
//        picker.setMode(DPMode.SINGLE);
//        picker.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
//            @Override
//            public void onDatePicked(String date) {
//                tvData.setText(date);
//                AccountData = date;
//                dialog.dismiss();
//            }
//        });
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setContentView(picker, params);
//        dialog.getWindow().setGravity(Gravity.CENTER);
//    }

    }
}
