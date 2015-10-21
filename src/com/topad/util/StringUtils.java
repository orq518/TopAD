package com.topad.util;

import android.text.TextUtils;

/**
 * Created by lenovo on 2015/7/29.
 */
public class StringUtils {

    /**
     */
    public static boolean isMobileNO(String mobiles) {
		/*
		�ƶ���134��135��136��137��138��139��150��151��157(TD)��158��159��187��188
		��ͨ��130��131��132��152��155��156��185��186
		���ţ�133��153��180��189����1349��ͨ��
		���ţ�137
		�ܽ��������ǵ�һλ�ض�Ϊ1���ڶ�λ�ض�Ϊ3��5��7��8������λ�õĿ���Ϊ0-9
		*/
        String telRegex = "[1][3578]\\d{9}";//"[1]"�����1λΪ����1��"[358]"����ڶ�λ����Ϊ3��5��8�е�һ����"\\d{9}"��������ǿ�����0��9�����֣���9λ��
        if (TextUtils.isEmpty(mobiles)) return false;
        else return mobiles.matches(telRegex);
    }

    /**
     * 金额超过3位数需要有逗号分隔
     */
    public static String thousandSplit(String money)  {
        String str = Utils.getMoneyTextWithComma(money);
        return str;
    }
}
