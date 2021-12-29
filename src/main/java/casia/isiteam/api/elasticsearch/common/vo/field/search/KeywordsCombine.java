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
    private String keyName = "";
    private List<KeyWordsBuider> keyWordsBuiders = new ArrayList<>();

    public KeywordsCombine(int minimumMatch, List<KeyWordsBuider> keyWordsBuiders) {
        this.minimumMatch = minimumMatch;
        for(KeyWordsBuider keyWordsBuider: keyWordsBuiders){
            this.keyWordsBuiders.add(keyWordsBuider);
        }
    }
    public KeywordsCombine(int minimumMatch, KeyWordsBuider ... keyWordsBuiders) {
        this.minimumMatch = minimumMatch;
        for(KeyWordsBuider keyWordsBuider: keyWordsBuiders){
            this.keyWordsBuiders.add(keyWordsBuider);
        }
    }
    public KeywordsCombine(int minimumMatch,String keyName, KeyWordsBuider ... keyWordsBuiders) {
        this.minimumMatch = minimumMatch;
        this.keyName = keyName;
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
    public KeywordsCombine setKeyWordsBuiders(List<KeyWordsBuider> keyWordsBuiders) {
        for(KeyWordsBuider keyWordsBuider: keyWordsBuiders){
            this.keyWordsBuiders.add(keyWordsBuider);
        }
        return this;
    }
<<<<<<< Updated upstream
=======

    public AggsFieldBuider getAggsFieldBuider() {
        return aggsFieldBuider;
    }

    public KeywordsCombine setAggsFieldBuider(AggsFieldBuider aggsFieldBuider) {
        this.aggsFieldBuider = aggsFieldBuider;
        return this;
    }
    public String getKeyName() {
        return keyName;
    }

>>>>>>> Stashed changes
}
