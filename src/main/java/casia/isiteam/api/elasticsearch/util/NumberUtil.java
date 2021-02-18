package casia.isiteam.api.elasticsearch.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: NumberUtil
 * @Description: unknown
 * @Vsersion: 1.0
 * <p>
 * Created by casia.wangzhiyou on 2021/2/18
 * Email: zhiyou_wang@foxmail.com
 */
public class NumberUtil {

    public static int reverseNum(int maxNum,int indexNum){
        List<Integer> list = new ArrayList<>();
        int c=0;
        for(int i=1; i<=maxNum; i++){
            if( indexNum==i ){c=i;}
            list.add(i);
        }
        return list.get(list.size()-c);
    }
}
