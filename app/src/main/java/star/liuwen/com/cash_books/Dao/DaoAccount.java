package star.liuwen.com.cash_books.Dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import star.liuwen.com.cash_books.Base.App;
import star.liuwen.com.cash_books.Base.Config;
import star.liuwen.com.cash_books.bean.AccountModel;
import star.liuwen.com.cash_books.bean.AccountModelDao;
import star.liuwen.com.cash_books.bean.SaveMoneyPlanModel;

/**
 * Created by liuwen on 2017/2/27.
 */
public class DaoAccount {


    /**
     * 插入对象数据
     * 插入对象为AccountModel
     *
     * @param model
     */
    public static void insertAccount(AccountModel model) {
        App.getDaoInstant().getAccountModelDao().insert(model);
    }


    public static void deleteAccountByModel(AccountModel model) {
        App.getDaoInstant().getAccountModelDao().delete(model);
    }

    /**
     * 删除所有
     */
    public static void deleteAllData() {
        App.getDaoInstant().getAccountModelDao().deleteAll();
    }


    /**
     * 更新数据
     *
     * @param model
     */
    public static void updateAccount(AccountModel model) {
        App.getDaoInstant().getAccountModelDao().update(model);
    }

    /**
     * 查询 SaveMoneyPlanModel的集合对象
     *
     * @return
     */
    public static List<AccountModel> query() {
        List<AccountModel> list = new ArrayList<>();
        list = App.getDaoInstant().getAccountModelDao().queryBuilder().list();
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getTimeMinSec().compareTo(model1.getTimeMinSec());
            }
        });
        return list;
    }

    /**
     * 查询条件
     *
     * @param accountType 账户名称
     * @return
     */
    public static List<AccountModel> queryByAccountType(String accountType) {
        List<AccountModel> list = new ArrayList<>();
        if (accountType.equals("信用卡")) {
            // list=App.getDaoInstant().getAccountModelDao().queryBuilder().whereOr(AccountModelDao.Properties.AccountType.eq(accountType),AccountModelDao.Properties.Data.eq())
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals("储蓄卡")) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals("现金")) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        } else if (accountType.equals("支付宝")) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.AccountType.eq(accountType)).build().list();
        }

        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getData().compareTo(model1.getData());
            }
        });
        return list;
    }


    /**
     * 支出或者是收入类型
     *
     * @param zhichushouruType
     * @return
     */
    public static List<AccountModel> queryByZhiChuSHouRuType(String zhichushouruType) {
        List<AccountModel> list = new ArrayList<>();
        if (zhichushouruType.equals(Config.ZHI_CHU)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.ZhiChuShouRuType.eq(zhichushouruType)).build().list();
        } else if (zhichushouruType.equals(Config.SHOU_RU)) {
            list = App.getDaoInstant().getAccountModelDao().queryBuilder().where(AccountModelDao.Properties.ZhiChuShouRuType.eq(zhichushouruType)).build().list();
        }
        Collections.sort(list, new Comparator<AccountModel>() {
            @Override
            public int compare(AccountModel model1, AccountModel model2) {
                return model2.getData().compareTo(model1.getData());
            }
        });
        return list;
    }


    /**
     * 获取总数
     * SaveMoneyPlanModel的总数
     *
     * @return
     */
    public static long getCount() {
        return App.getDaoInstant().getAccountModelDao().count();
    }


}
