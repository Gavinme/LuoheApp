package com.luohe.android.luohe.emoji;

import java.util.HashMap;

/**
 * Created by sj on 16/3/16.
 */
public class DefXhsEmoticons {
    private static String[] meanings = new String[]{"微笑", "害羞", "吐舌头", "偷笑", "爱慕", "大笑", "跳舞", "飞吻", "安慰", "抱抱", "加油", "胜利", "强", "亲亲", "花痴", "露齿笑", "查找", "呼叫", "算账", "财迷", "好主意", "鬼脸", "天使", "再见", "流口水", "享受", "色情狂", "呆若木鸡", "思考", "迷惑", "疑问", "没钱了", "无聊", "怀疑", "嘘", "小样", "摇头", "感冒", "尴尬", "傻笑", "不会吧", "无奈", "流汗", "凄凉", "困了", "晕", "忧伤", "委屈", "悲泣", "大哭", "痛哭", "I服了U", "对不起", "再见", "皱眉", "好累", "生病", "吐", "背", "惊讶", "惊愕", "闭嘴", "欠扁", "鄙视你", "大怒", "生气", "财神", "学习雷锋", "恭喜发财", "小二", "老大", "邪恶", "单挑", "CS", "隐形人", "炸弹", "惊声尖叫", "漂亮MM", "帅哥", "招财猫", "成交", "鼓掌", "握手", "红唇", "玫瑰", "残花", "爱心", "心碎", "钱", "购物", "礼物", "收邮件", "电话", "举杯庆祝", "时钟", "等待", "很晚了", "飞机", "支付宝"};

    public static String[] xhsEmoticonArray =new String[meanings.length];
//    {
//            "xhsemoji_1.png,[无语]",
//            "xhsemoji_2.png,[汗]",
//            "xhsemoji_3.png,[瞎]",
//            "xhsemoji_4.png,[口水]",
//            "xhsemoji_5.png,[酷]",
//            "xhsemoji_6.png,[哭] ",
//            "xhsemoji_7.png,[萌]",
//            "xhsemoji_8.png,[挖鼻孔]",
//            "xhsemoji_9.png,[好冷]",
//            "xhsemoji_10.png,[白眼]",
//            "xhsemoji_11.png,[晕]",
//            "xhsemoji_12.png,[么么哒]",
//            "xhsemoji_13.png,[哈哈]",
//            "xhsemoji_14.png,[好雷]",
//            "xhsemoji_15.png,[啊]",
//            "xhsemoji_16.png,[嘘]",
//            "xhsemoji_17.png,[震惊]",
//            "xhsemoji_18.png,[刺瞎]",
//            "xhsemoji_19.png,[害羞]",
//            "xhsemoji_20.png,[嘿嘿]",
//            "xhsemoji_21.png,[嘻嘻]"};

    public static final HashMap<String, String> sXhsEmoticonHashMap = new HashMap<>();

    static {
        int index;String tempIndex;
        for (int i=0;i<xhsEmoticonArray.length;i++){
            index=i+1;
            if (index<10){
                tempIndex="00"+index;
            }else {
                tempIndex="0"+index;
            }
            xhsEmoticonArray[i]= String.format("aliwx_s%1s.png,[%s]",tempIndex,meanings[i]);
            sXhsEmoticonHashMap.put(String.format("[%1s]",meanings[i]),String.format("aliwx_s%1s.png",tempIndex));
        }

//        sXhsEmoticonHashMap.put("[无语]", "xhsemoji_1.png");
//        sXhsEmoticonHashMap.put("[汗]", "xhsemoji_2.png");
//        sXhsEmoticonHashMap.put("[瞎]", "xhsemoji_3.png");
//        sXhsEmoticonHashMap.put("[口水]", "xhsemoji_4.png");
//        sXhsEmoticonHashMap.put("[酷]", "xhsemoji_5.png");
//        sXhsEmoticonHashMap.put("[哭]", "xhsemoji_6.png");
//        sXhsEmoticonHashMap.put("[萌]", "xhsemoji_7.png");
//        sXhsEmoticonHashMap.put("[挖鼻孔]", "xhsemoji_8.png");
//        sXhsEmoticonHashMap.put("[好冷]", "xhsemoji_9.png");
//        sXhsEmoticonHashMap.put("[白眼]", "xhsemoji_10.png");
//        sXhsEmoticonHashMap.put("[晕]", "xhsemoji_11.png");
//        sXhsEmoticonHashMap.put("[么么哒]", "xhsemoji_12.png");
//        sXhsEmoticonHashMap.put("[哈哈]", "xhsemoji_13.png");
//        sXhsEmoticonHashMap.put("[好雷]", "xhsemoji_14.png");
//        sXhsEmoticonHashMap.put("[啊]", "xhsemoji_15.png");
//        sXhsEmoticonHashMap.put("[嘘]", "xhsemoji_16.png");
//        sXhsEmoticonHashMap.put("[震惊]", "xhsemoji_17.png");
//        sXhsEmoticonHashMap.put("[刺瞎]", "xhsemoji_18.png");
//        sXhsEmoticonHashMap.put("[害羞]", "xhsemoji_19.png");
//        sXhsEmoticonHashMap.put("[嘿嘿]", "xhsemoji_10.png");
//        sXhsEmoticonHashMap.put("[嘻嘻]", "xhsemoji_21.png");
    }
}
