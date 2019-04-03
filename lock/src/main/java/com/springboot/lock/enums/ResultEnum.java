package com.springboot.lock.enums;

import lombok.Getter;

/**
 * @Author: LiangZF
 * @Description:
 * @Date: Created in 22:39 2019/3/27
 * @Modified By: LiangZF
 */
@Getter
public enum ResultEnum {
    SUCCESS(0,"�ɹ�"),

    PARAM_ERROR(1,"��������ȷ"),

    PRODUCT_NOT_EXIST(10,"��Ʒ������"),
    PRODUCT_STOCK_ERROR(11,"��治��"),
    ORDER_NOT_EXIST(12,"����������"),
    ORDERDETAIL_NOT_EXIST(13,"�������鲻����"),
    ORDER_STATUS_ERROR(14,"����״̬����ȷ"),
    ORDER_UPDATE_FAIL(15,"��������ʧ��"),

    ORDER_DETAIL_EMPTY(16,"��������Ϊ��"),

    ORDER_PAY_STATUS_ERROR(17,"����֧��״̬����ȷ"),

    CART_EMPTY(18,"���ﳵΪ��"),

    ORDER_OWNER_ERROR(19,"�ö��������ڵ�ǰ�û�"),

    WECHAT_MP_ERROR(20,"΢�Ź����˺ŷ������"),

    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(21,"΢��֧���첽֪ͨ���У�鲻ͨ��"),

    ORDER_CANEL_SUCCESS(22,"����ȡ���ɹ�"),

    ORDER_FINISH_SUCCESS(23,"�������ɹ�"),

    PRODUCT_STATUS_ERROR(24,"��Ʒ״̬����ȷ"),

    PRODUCT_ONSALE_SUCCESS(25,"��Ʒ�ϼܳɹ�"),

    PRODUCT_OFFSALE_SUCCESS(26,"��Ʒ�¼ܳɹ�"),

    WECHAT_QR_ERROR(27,"΢�ſ���ƽ̨�˺ŷ������"),

    LOGIN_FAIL(28,"��¼ʧ��,��¼��Ϣ����ȷ"),
    LOGOUT_SUCCESS(29,"�ǳ��ɹ�"),
            ;
    private Integer code;
    private String message;

    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
