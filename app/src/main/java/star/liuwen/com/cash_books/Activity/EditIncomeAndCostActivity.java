package star.liuwen.com.cash_books.Activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGAOnRVItemClickListener;
import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;
import star.liuwen.com.cash_books.Base.BaseActivity;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.Dao.DaoShouRuModel;
import star.liuwen.com.cash_books.Dao.DaoZhiChuModel;
import star.liuwen.com.cash_books.Enage.DataEnige;
import star.liuwen.com.cash_books.R;
import star.liuwen.com.cash_books.RxBus.RxBus;
import star.liuwen.com.cash_books.Utils.ToastUtils;
import star.liuwen.com.cash_books.bean.ShouRuModel;
import star.liuwen.com.cash_books.bean.ZhiChuModel;

/**
 * Created by liuwen on 2017/3/8.
 */
public class EditIncomeAndCostActivity extends BaseActivity {
    private RecyclerView mRecyclerView;
    private EditText editName;
    private ImageView imageName;
    private EditCostAdapter mAdapter;
    private List<ZhiChuModel> mList;
    private String type;
    private int url;

    @Override
    public int activityLayoutRes() {
        return R.layout.edit_income_and_cost_activity;
    }

    @Override
    public void initView() {
        setBackView();
        setLeftImage(R.mipmap.fanhui_lan);
        setTitle(getString(R.string.edit_category));
        setLeftText(getString(R.string.back));
        setRightText(getString(R.string.sure), new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSure(type);
            }
        });
        type = this.getIntent().getStringExtra(Config.OTHER);
        mRecyclerView = (RecyclerView) findViewById(R.id.edit_recyclerView);
        editName = (EditText) findViewById(R.id.edit_name);
        imageName = (ImageView) findViewById(R.id.imag_name);
        mAdapter = new EditCostAdapter(mRecyclerView);
        final GridLayoutManager manager = new GridLayoutManager(EditIncomeAndCostActivity.this, 6, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(manager);
        mList = new ArrayList<>();
        mList = DataEnige.getZhiChuData();
        mAdapter.setData(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnRVItemClickListener(new BGAOnRVItemClickListener() {
            @Override
            public void onRVItemClick(ViewGroup parent, View itemView, int position) {
                editName.setText(mList.get(position).getNames());
                imageName.setImageResource(mList.get(position).getUrl());
                url = mList.get(position).getUrl();

            }
        });
    }

    private void onSure(String type) {
        String editTypeName = editName.getText().toString();
        if (TextUtils.isEmpty(editTypeName.trim())) {
            ToastUtils.showToast(EditIncomeAndCostActivity.this, "名称不能为空");
            return;
        }
        if (type.equals(Config.ZHI_CHU)) {
            ZhiChuModel model = new ZhiChuModel();
            model.setId(DaoZhiChuModel.getCount() + 1);
            model.setUrl(url);
            model.setNames(editTypeName);
            DaoZhiChuModel.insertZhiChu(model);
            RxBus.getInstance().post(Config.RxToZhiChu, model);
            this.finish();
        } else if (type.equals(Config.SHOU_RU)) {
            ShouRuModel model = new ShouRuModel();
            model.setId(DaoZhiChuModel.getCount()+1);
            model.setUrl(url);
            model.setName(editTypeName);
            RxBus.getInstance().post(Config.RxToSHouRu, model);
            this.finish();
        }
    }
    private class EditCostAdapter extends BGARecyclerViewAdapter<ZhiChuModel> {

        public EditCostAdapter(RecyclerView recyclerView) {
            super(recyclerView, R.layout.item_zhichu_fragment);
        }

        @Override
        protected void fillData(BGAViewHolderHelper helper, int position, ZhiChuModel model) {
            helper.setImageResource(R.id.item_imag, model.getUrl());
            helper.setText(R.id.item_name, model.getNames());
        }
    }

}
