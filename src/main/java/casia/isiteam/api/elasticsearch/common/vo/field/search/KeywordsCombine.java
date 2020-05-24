package casia.isiteam.api.elasticsearch.common.vo.field.search;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: KeywordsCombine
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/14
 * Email: zhiyou_wang@foxmail.com
 */
public class KeywordsCombine {
    private int minimumMatch = 1;
    private List<KeyWordsBuider> keyWordsBuiders = new ArrayList<>();


    public KeywordsCombine(int minimumMatch, KeyWordsBuider ... keyWordsBuiders) {
        this.minimumMatch = minimumMatch;
        for(KeyWordsBuider keyWordsBuider: keyWordsBuiders){
            this.keyWordsBuiders.add(keyWordsBuider);
        }
    }

    public int getMinimumMatch() {
        return minimumMatch;
    }

    public KeywordsCombine setMinimumMatch(int minimumMatch) {
        this.minimumMatch = minimumMatch;
        return this;
    }

    public List<KeyWordsBuider> getKeyWordsBuiders() {
        return keyWordsBuiders;
    }

    public KeywordsCombine setKeyWordsBuiders(KeyWordsBuider ... keyWordsBuiders) {
        for(KeyWordsBuider keyWordsBuider: keyWordsBuiders){
            this.keyWordsBuiders.add(keyWordsBuider);
        }
        return this;
    }
}
