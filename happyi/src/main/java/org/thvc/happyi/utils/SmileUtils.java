/**
 * Copyright (C) 2013-2014 EaseMob Technologies. All rights reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.thvc.happyi.utils;

import android.content.Context;
import android.text.Spannable;
import android.text.Spannable.Factory;
import android.text.style.ImageSpan;


import org.thvc.happyi.R;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SmileUtils {


    public static final String e_1 = "[微笑]";
    public static final String e_2 = "[撇嘴]";
    public static final String e_3 = "[色]";
    public static final String e_4 = "[发呆]";
    public static final String e_5 = "[得意]";
    public static final String e_6 = "[流泪]";
    public static final String e_7 = "[害羞]";
    public static final String e_8 = "[闭嘴]";
    public static final String e_9 = "[睡]";
    public static final String e_10 = "[大哭]";
    public static final String e_11 = "[尴尬]";
    public static final String e_12 = "[发怒]";
    public static final String e_13 = "[调皮]";
    public static final String e_14 = "[呲牙]";


    public static final String e_15 = "[惊讶]";
    public static final String e_16 = "[难过]";
    public static final String e_17 = "[酷]";
    public static final String e_18 = "[冷汗]";
    public static final String e_19 = "[抓狂]";
    public static final String e_20 = "[吐]";
    public static final String e_21 = "[偷笑]";
    public static final String e_22 = "[愉快]";
    public static final String e_23 = "[白眼]";
    public static final String e_24 = "[傲慢]";
    public static final String e_25 = "[饥饿]";
    public static final String e_26 = "[困]";
    public static final String e_27 = "[惊恐]";


    public static final String e_28 = "[流汗]";
    public static final String e_29 = "[憨笑]";
    public static final String e_30 = "[悠闲]";
    public static final String e_31 = "[奋斗]";
    public static final String e_32 = "[咒骂]";
    public static final String e_33 = "[疑问]";
    public static final String e_34 = "[嘘]";
    public static final String e_35 = "[晕]";


    public static final String e_36 = "[疯了]";
    public static final String e_37 = "[衰]";
    public static final String e_38 = "[骷髅]";
    public static final String e_39 = "[敲打]";
    public static final String e_40 = "[再见]";
    public static final String e_41 = "[擦汗]";


    public static final String e_42 = "[抠鼻]";
    public static final String e_43 = "[鼓掌]";
    public static final String e_44 = "[糗大了]";
    public static final String e_45 = "[坏笑]";
    public static final String e_46 = "[左哼哼]";
    public static final String e_47 = "[右哼哼]";
    public static final String e_48 = "[哈欠]";
    public static final String e_49 = "[鄙视]";
    public static final String e_50 = "[委屈]";
    public static final String e_51 = "[快哭了]";
    public static final String e_52 = "[阴险]";
    public static final String e_53 = "[亲亲]";


    public static final String e_54 = "[吓]";
    public static final String e_55 = "[可怜]";
    public static final String e_56 = "[菜刀]";
    public static final String e_57 = "[西瓜]";
    public static final String e_58 = "[啤酒]";
    public static final String e_59 = "[篮球]";
    public static final String e_60 = "[乒乓]";
    public static final String e_61 = "[咖啡]";
    public static final String e_62 = "[饭]";
    public static final String e_63 = "[猪头]";
    public static final String e_64 = "[玫瑰]";
    public static final String e_65 = "[凋谢]";
    public static final String e_66 = "[嘴唇]";
    public static final String e_67 = "[爱心]";


    public static final String e_68 = "[心碎]";
    public static final String e_69 = "[蛋糕]";
    public static final String e_70 = "[闪电]";
    public static final String e_71 = "[炸弹]";
    public static final String e_72 = "[刀]";
    public static final String e_73 = "[足球]";
    public static final String e_74 = "[瓢虫]";
    public static final String e_75 = "[便便]";
    public static final String e_76 = "[月亮]";
    public static final String e_77 = "[太阳]";
    public static final String e_78 = "[礼物]";
    public static final String e_79 = "[拥抱]";
    public static final String e_80 = "[强]";
    public static final String e_81 = "[弱]";
    public static final String e_82 = "[握手]";

    public static final String e_83 = "[胜利]";
    public static final String e_84 = "[抱拳]";
    public static final String e_85 = "[勾引]";
    public static final String e_86 = "[拳头]";
    public static final String e_87 = "[差劲]";
    public static final String e_88 = "[爱你]";
    public static final String e_89 = "[NO]";
    public static final String e_90 = "[OK]";
    public static final String e_91 = "[爱情]";
    public static final String e_92 = "[飞吻]";
    public static final String e_93 = "[跳跳]";
    public static final String e_94 = "[发抖]";
    public static final String e_95 = "[怄火]";
    public static final String e_96 = "[转圈]";
    public static final String e_97 = "[磕头]";
    public static final String e_98 = "[回头]";
    public static final String e_99 = "[跳绳]";
    public static final String e_100 = "[投降]";
    public static final String e_101 = "[蹦蹦跳跳]";
    public static final String e_102 = "[加油]";
    public static final String e_103 = "[观察]";
    public static final String e_104 = "[左太极]";
    public static final String e_105 = "[右太极]";


    private static final Factory spannableFactory = Factory
            .getInstance();

    private static final Map<Pattern, Integer> emoticons = new HashMap<Pattern, Integer>();

    static {
        addPattern(emoticons, e_1, R.drawable.e_1);
        addPattern(emoticons, e_2, R.drawable.e_2);
        addPattern(emoticons, e_3, R.drawable.e_3);
        addPattern(emoticons, e_4, R.drawable.e_4);
        addPattern(emoticons, e_5, R.drawable.e_5);
        addPattern(emoticons, e_6, R.drawable.e_6);
        addPattern(emoticons, e_7, R.drawable.e_7);
        addPattern(emoticons, e_8, R.drawable.e_8);
        addPattern(emoticons, e_9, R.drawable.e_9);
        addPattern(emoticons, e_10, R.drawable.e_10);
        addPattern(emoticons, e_11, R.drawable.e_11);
        addPattern(emoticons, e_12, R.drawable.e_12);
        addPattern(emoticons, e_13, R.drawable.e_13);
        addPattern(emoticons, e_14, R.drawable.e_14);
        addPattern(emoticons, e_15, R.drawable.e_15);
        addPattern(emoticons, e_16, R.drawable.e_16);
        addPattern(emoticons, e_17, R.drawable.e_17);
        addPattern(emoticons, e_18, R.drawable.e_18);
        addPattern(emoticons, e_19, R.drawable.e_19);
        addPattern(emoticons, e_20, R.drawable.e_20);
        addPattern(emoticons, e_21, R.drawable.e_21);
        addPattern(emoticons, e_22, R.drawable.e_22);
        addPattern(emoticons, e_23, R.drawable.e_23);
        addPattern(emoticons, e_24, R.drawable.e_24);
        addPattern(emoticons, e_25, R.drawable.e_25);
        addPattern(emoticons, e_26, R.drawable.e_26);
        addPattern(emoticons, e_27, R.drawable.e_27);
        addPattern(emoticons, e_28, R.drawable.e_28);
        addPattern(emoticons, e_29, R.drawable.e_29);
        addPattern(emoticons, e_30, R.drawable.e_30);
        addPattern(emoticons, e_31, R.drawable.e_31);
        addPattern(emoticons, e_32, R.drawable.e_32);
        addPattern(emoticons, e_33, R.drawable.e_33);
        addPattern(emoticons, e_34, R.drawable.e_34);
        addPattern(emoticons, e_35, R.drawable.e_35);


        addPattern(emoticons, e_36, R.drawable.e_36);
        addPattern(emoticons, e_37, R.drawable.e_37);
        addPattern(emoticons, e_38, R.drawable.e_38);
        addPattern(emoticons, e_39, R.drawable.e_39);
        addPattern(emoticons, e_40, R.drawable.e_40);
        addPattern(emoticons, e_41, R.drawable.e_41);
        addPattern(emoticons, e_42, R.drawable.e_42);
        addPattern(emoticons, e_43, R.drawable.e_43);
        addPattern(emoticons, e_44, R.drawable.e_44);
        addPattern(emoticons, e_45, R.drawable.e_45);
        addPattern(emoticons, e_46, R.drawable.e_46);
        addPattern(emoticons, e_47, R.drawable.e_47);
        addPattern(emoticons, e_48, R.drawable.e_48);
        addPattern(emoticons, e_49, R.drawable.e_49);
        addPattern(emoticons, e_50, R.drawable.e_50);
        addPattern(emoticons, e_51, R.drawable.e_51);
        addPattern(emoticons, e_52, R.drawable.e_52);
        addPattern(emoticons, e_53, R.drawable.e_53);
        addPattern(emoticons, e_54, R.drawable.e_54);
        addPattern(emoticons, e_55, R.drawable.e_55);
        addPattern(emoticons, e_56, R.drawable.e_56);
        addPattern(emoticons, e_57, R.drawable.e_57);
        addPattern(emoticons, e_58, R.drawable.e_58);
        addPattern(emoticons, e_59, R.drawable.e_59);
        addPattern(emoticons, e_60, R.drawable.e_60);
        addPattern(emoticons, e_61, R.drawable.e_61);
        addPattern(emoticons, e_62, R.drawable.e_62);
        addPattern(emoticons, e_63, R.drawable.e_63);
        addPattern(emoticons, e_64, R.drawable.e_64);
        addPattern(emoticons, e_65, R.drawable.e_65);
        addPattern(emoticons, e_66, R.drawable.e_66);
        addPattern(emoticons, e_67, R.drawable.e_67);
        addPattern(emoticons, e_68, R.drawable.e_68);
        addPattern(emoticons, e_69, R.drawable.e_69);
        addPattern(emoticons, e_70, R.drawable.e_70);
        addPattern(emoticons, e_71, R.drawable.e_71);
        addPattern(emoticons, e_72, R.drawable.e_72);
        addPattern(emoticons, e_73, R.drawable.e_73);
        addPattern(emoticons, e_74, R.drawable.e_74);
        addPattern(emoticons, e_75, R.drawable.e_75);
        addPattern(emoticons, e_76, R.drawable.e_76);
        addPattern(emoticons, e_77, R.drawable.e_77);
        addPattern(emoticons, e_78, R.drawable.e_78);
        addPattern(emoticons, e_79, R.drawable.e_79);
        addPattern(emoticons, e_80, R.drawable.e_80);
        addPattern(emoticons, e_81, R.drawable.e_81);
        addPattern(emoticons, e_82, R.drawable.e_82);
        addPattern(emoticons, e_83, R.drawable.e_83);
        addPattern(emoticons, e_84, R.drawable.e_84);
        addPattern(emoticons, e_85, R.drawable.e_85);
        addPattern(emoticons, e_86, R.drawable.e_86);
        addPattern(emoticons, e_87, R.drawable.e_87);
        addPattern(emoticons, e_88, R.drawable.e_88);
        addPattern(emoticons, e_89, R.drawable.e_89);
        addPattern(emoticons, e_90, R.drawable.e_90);
        addPattern(emoticons, e_91, R.drawable.e_91);
        addPattern(emoticons, e_92, R.drawable.e_92);
        addPattern(emoticons, e_93, R.drawable.e_93);
        addPattern(emoticons, e_94, R.drawable.e_94);
        addPattern(emoticons, e_95, R.drawable.e_95);
        addPattern(emoticons, e_96, R.drawable.e_96);
        addPattern(emoticons, e_97, R.drawable.e_97);
        addPattern(emoticons, e_98, R.drawable.e_98);
        addPattern(emoticons, e_99, R.drawable.e_99);
        addPattern(emoticons, e_100, R.drawable.e_100);
        addPattern(emoticons, e_101, R.drawable.e_101);
        addPattern(emoticons, e_102, R.drawable.e_102);
        addPattern(emoticons, e_103, R.drawable.e_103);
        addPattern(emoticons, e_104, R.drawable.e_104);
        addPattern(emoticons, e_105, R.drawable.e_105);

    }

    private static void addPattern(Map<Pattern, Integer> map, String smile,
                                   int resource) {
        map.put(Pattern.compile(Pattern.quote(smile)), resource);
    }

    /**
     * replace existing spannable with smiles
     *
     * @param context
     * @param spannable
     * @return
     */
    public static boolean addSmiles(Context context, Spannable spannable) {
        boolean hasChanges = false;
        for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(spannable);
            while (matcher.find()) {
                boolean set = true;
                for (ImageSpan span : spannable.getSpans(matcher.start(),
                        matcher.end(), ImageSpan.class))
                    if (spannable.getSpanStart(span) >= matcher.start()
                            && spannable.getSpanEnd(span) <= matcher.end())
                        spannable.removeSpan(span);
                    else {
                        set = false;
                        break;
                    }
                if (set) {
                    hasChanges = true;
                    spannable.setSpan(new ImageSpan(context, entry.getValue()),
                            matcher.start(), matcher.end(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            }
        }
        return hasChanges;
    }

    public static Spannable getSmiledText(Context context, CharSequence text) {
        Spannable spannable = spannableFactory.newSpannable(text);
        addSmiles(context, spannable);
        return spannable;
    }

    public static boolean containsKey(String key) {
        boolean b = false;
        for (Entry<Pattern, Integer> entry : emoticons.entrySet()) {
            Matcher matcher = entry.getKey().matcher(key);
            if (matcher.find()) {
                b = true;
                break;
            }
        }
        return b;
    }
}
