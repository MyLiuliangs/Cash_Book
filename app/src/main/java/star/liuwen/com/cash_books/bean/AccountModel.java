package star.liuwen.com.cash_books.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;

/**
 * Created by liuwen on 2017/1/6.
 */
@Entity
public class AccountModel implements Serializable {
    private static final long serialVersionUID = 1L;//为了避免反序列化失败 加的一个uid
    @Id(autoincrement = true)
    private Long id;
    private String AccountType;//账户类型 (账户名)
    private String Data;//日期  2017-2-28
    private double Money; //消费或者支出金额
    private String ConsumeType; //消费类型 （信用卡还是储蓄卡）
    private int url; //图片(消费的图片或者是收入的图片)
    private String timeMinSec;//时分秒  精确到到时分秒消费  2017-2-28-12-30-31
    private String zhiChuShouRuType; //是否是支出或者是收入
    private float consumePercent;//百分比
    private double zhiCHuAdd; //总支出
    private double sHouRuAdd; //总收入
    private double AccountYuer;//账户余额
    private String ShowFirstDate;//用来标识是否显示开启了记账之路
    private long choiceAccountId;//用来标识查询账户的唯一数据
    private String budget;//预算金额

    public AccountModel() {
    }


    public AccountModel(String consumeType, int url, double money, float consumePercent, String data) {
        ConsumeType = consumeType;
        this.url = url;
        Money = money;
        this.consumePercent = consumePercent;
        Data = data;
    }

    public AccountModel(double money, String consumeType, int url) {
        Money = money;
        ConsumeType = consumeType;
        this.url = url;
    }

    public AccountModel(String accountType, String data, double money, String consumeType, int url, String timeMinSec, String zhiChuShouRuType, long id) {
        AccountType = accountType;
        Data = data;
        Money = money;
        ConsumeType = consumeType;
        this.url = url;
        this.timeMinSec = timeMinSec;
        this.zhiChuShouRuType = zhiChuShouRuType;
        this.choiceAccountId = id;
    }


    @Generated(hash = 1182184895)
    public AccountModel(Long id, String AccountType, String Data, double Money, String ConsumeType, int url, String timeMinSec, String zhiChuShouRuType,
                        float consumePercent, double zhiCHuAdd, double sHouRuAdd, double AccountYuer, String ShowFirstDate, long choiceAccountId, String budget) {
        this.id = id;
        this.AccountType = AccountType;
        this.Data = Data;
        this.Money = Money;
        this.ConsumeType = ConsumeType;
        this.url = url;
        this.timeMinSec = timeMinSec;
        this.zhiChuShouRuType = zhiChuShouRuType;
        this.consumePercent = consumePercent;
        this.zhiCHuAdd = zhiCHuAdd;
        this.sHouRuAdd = sHouRuAdd;
        this.AccountYuer = AccountYuer;
        this.ShowFirstDate = ShowFirstDate;
        this.choiceAccountId = choiceAccountId;
        this.budget = budget;
    }


    public long getChoiceAccountId() {
        return this.choiceAccountId;
    }


    public void setChoiceAccountId(long choiceAccountId) {
        this.choiceAccountId = choiceAccountId;
    }


    public String getShowFirstDate() {
        return this.ShowFirstDate;
    }


    public void setShowFirstDate(String ShowFirstDate) {
        this.ShowFirstDate = ShowFirstDate;
    }


    public double getAccountYuer() {
        return this.AccountYuer;
    }


    public void setAccountYuer(double AccountYuer) {
        this.AccountYuer = AccountYuer;
    }


    public double getSHouRuAdd() {
        return this.sHouRuAdd;
    }


    public void setSHouRuAdd(double sHouRuAdd) {
        this.sHouRuAdd = sHouRuAdd;
    }


    public double getZhiCHuAdd() {
        return this.zhiCHuAdd;
    }


    public void setZhiCHuAdd(double zhiCHuAdd) {
        this.zhiCHuAdd = zhiCHuAdd;
    }


    public float getConsumePercent() {
        return this.consumePercent;
    }


    public void setConsumePercent(float consumePercent) {
        this.consumePercent = consumePercent;
    }


    public String getZhiChuShouRuType() {
        return this.zhiChuShouRuType;
    }


    public void setZhiChuShouRuType(String zhiChuShouRuType) {
        this.zhiChuShouRuType = zhiChuShouRuType;
    }


    public String getTimeMinSec() {
        return this.timeMinSec;
    }


    public void setTimeMinSec(String timeMinSec) {
        this.timeMinSec = timeMinSec;
    }


    public int getUrl() {
        return this.url;
    }


    public void setUrl(int url) {
        this.url = url;
    }


    public String getConsumeType() {
        return this.ConsumeType;
    }


    public void setConsumeType(String ConsumeType) {
        this.ConsumeType = ConsumeType;
    }


    public double getMoney() {
        return this.Money;
    }


    public void setMoney(double Money) {
        this.Money = Money;
    }


    public String getData() {
        return this.Data;
    }


    public void setData(String Data) {
        this.Data = Data;
    }


    public String getAccountType() {
        return this.AccountType;
    }


    public void setAccountType(String AccountType) {
        this.AccountType = AccountType;
    }


    public Long getId() {
        return this.id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }


}
