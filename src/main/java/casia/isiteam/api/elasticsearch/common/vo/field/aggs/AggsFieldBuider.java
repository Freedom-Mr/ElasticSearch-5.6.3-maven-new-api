package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.elasticsearch.common.enums.GeoLevel;
import casia.isiteam.api.toolutil.Validator;

import java.util.ArrayList;
import java.util.List;

/**
 * ClassName: AggsFieldBuider
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/19
 * Email: zhiyou_wang@foxmail.com
 */
public class AggsFieldBuider {

    private List<TypeInfo> cardinalitys = new ArrayList<>();
    private List<TermInfo> termInfos = new ArrayList<>();
    private List<OperationInfo> operationInfos = new ArrayList<>();
    private List<DateInfo> dateInfos = new ArrayList<>();
    private List<TopData> topDatas = new ArrayList<>();
    private List<GeoInfo> geoInfos = new ArrayList<>();

    public void addGeo(GeoInfo... geoInfo){
        if( Validator.check(geoInfo) ){
            for(GeoInfo info:geoInfo){
                if( !geoInfos.contains(info) ){
                    geoInfos.add(info);
                }
            }
        }
    }

    public void addType(TypeInfo... typeInfo){
        if( Validator.check(typeInfo) ){
            for(TypeInfo info:typeInfo){
                if( !cardinalitys.contains(info) ){
                    cardinalitys.add(info);
                }
            }
        }
    }

    public void addTerm(TermInfo ... termInfo){
        if( Validator.check(termInfo) ){
            for(TermInfo info:termInfo){
                if( !termInfos.contains(info) ){
                    termInfos.add(info);
                }
            }
        }
    }
    public void addOperation(OperationInfo ... operationInfo){
        if( Validator.check(operationInfo) ){
            for(OperationInfo info:operationInfo){
                if( !operationInfos.contains(info) ){
                    operationInfos.add(info);
                }
            }
        }
    }

    public void addDate(DateInfo ... dateInfo){
        if( Validator.check(dateInfo) ){
            for(DateInfo info:dateInfo){
                if( !dateInfos.contains(info) ){
                    dateInfos.add(info);
                }
            }
        }
    }
    public void addTopDatas(TopData ... topData){
        if( Validator.check(topData) ){
            for(TopData info:topData){
                if( !topDatas.contains(info) ){
                    topDatas.add(info);
                }
            }
        }
    }

    public List<TypeInfo> getCardinalitys() {
        return cardinalitys;
    }

    public List<TermInfo> getTermInfos() {
        return termInfos;
    }

    public List<OperationInfo> getOperationInfos() {
        return operationInfos;
    }

    public List<DateInfo> getDateInfos() {
        return dateInfos;
    }

    public List<TopData> getTopDatas() {
        return topDatas;
    }

    public List<GeoInfo> getGeoInfos() {
        return geoInfos;
    }
}
