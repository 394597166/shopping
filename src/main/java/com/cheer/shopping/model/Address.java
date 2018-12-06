package com.cheer.shopping.model;

/**
 * 地址类
 */
public class Address {
    //地址信息编号
    private Integer addressId;
    //收件人
    private String addressAddressee;
    //详细地址
    private String addressDetailed;
    //手机号码
    private String addressPhone;
    //座机号码
    private String addressTelephone;
    //邮箱地址
    private String addressMailbox;
    //地址别名
    private String addressAlias;

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("addressId=").append(addressId);
        sb.append(", addressAddressee='").append(addressAddressee).append('\'');
        sb.append(", addressDetailed='").append(addressDetailed).append('\'');
        sb.append(", addressPhone='").append(addressPhone).append('\'');
        sb.append(", addressTelephone='").append(addressTelephone).append('\'');
        sb.append(", addressMailbox='").append(addressMailbox).append('\'');
        sb.append(", addressAlias='").append(addressAlias).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public Integer getAddressId() {
        return addressId;
    }

    public Address setAddressId(Integer addressId) {
        this.addressId = addressId;
        return this;
    }

    public String getAddressAddressee() {
        return addressAddressee;
    }

    public Address setAddressAddressee(String addressAddressee) {
        this.addressAddressee = addressAddressee;
        return this;
    }

    public String getAddressDetailed() {
        return addressDetailed;
    }

    public Address setAddressDetailed(String addressDetailed) {
        this.addressDetailed = addressDetailed;
        return this;
    }

    public String getAddressPhone() {
        return addressPhone;
    }

    public Address setAddressPhone(String addressPhone) {
        this.addressPhone = addressPhone;
        return this;
    }

    public String getAddressTelephone() {
        return addressTelephone;
    }

    public Address setAddressTelephone(String addressTelephone) {
        this.addressTelephone = addressTelephone;
        return this;
    }

    public String getAddressMailbox() {
        return addressMailbox;
    }

    public Address setAddressMailbox(String addressMailbox) {
        this.addressMailbox = addressMailbox;
        return this;
    }

    public String getAddressAlias() {
        return addressAlias;
    }

    public Address setAddressAlias(String addressAlias) {
        this.addressAlias = addressAlias;
        return this;
    }
}
