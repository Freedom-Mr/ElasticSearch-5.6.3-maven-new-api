package casia.isiteam.api.elasticsearch.common.vo.field.aggs;

import casia.isiteam.api.elasticsearch.common.enums.GeoLevel;
import casia.isiteam.api.elasticsearch.common.vo.field.search.KeywordsCombine;
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
    private List<PriceInfo> priceInfos = new ArrayList<>();
    private List<IpRangeInfo> ipRangeInfos = new ArrayList<>();
    private List<GridInfo> gridInfos = new ArrayList<>();
    private List<KeywordsCombine> keywordsCombines = new ArrayList<>();
    private List<MatrixInfo> matrixInfos = new ArrayList<>();

    public AggsFieldBuider(){

    }
    public AggsFieldBuider(TypeInfo ... typeInfo) {
        if( Validator.check(typeInfo) ){
            for(TypeInfo info:typeInfo){
                if( !cardinalitys.contains(info) ){
                    cardinalitys.add(info);
                }
            }
        }
    }
    /*public AggsFieldBuider(List<TypeInfo> typeInfo) {
        if( Validator.check(typeInfo) ){
            for(TypeInfo info:typeInfo){
                if( !cardinalitys.contains(info) ){
                    cardinalitys.add(info);
                }
            }
        }
    }*/

    public AggsFieldBuider addType(TypeInfo... typeInfo){
        if( Validator.check(typeInfo) ){
            for(TypeInfo info:typeInfo){
                if( !cardinalitys.contains(info) ){
                    cardinalitys.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addType(List<TypeInfo> typeInfo){
        if( Validator.check(typeInfo) ){
            for(TypeInfo info:typeInfo){
                if( !cardinalitys.contains(info) ){
                    cardinalitys.add(info);
                }
            }
        }
        return this;
    }


    public AggsFieldBuider(GeoInfo ... geoInfo) {
        if( Validator.check(geoInfo) ){
            for(GeoInfo info:geoInfo){
                if( !geoInfos.contains(info) ){
                    geoInfos.add(info);
                }
            }
        }
    }
   /* public AggsFieldBuider(List<GeoInfo> geoInfo) {
        if( Validator.check(geoInfo) ){
            for(GeoInfo info:geoInfo){
                if( !geoInfos.contains(info) ){
                    geoInfos.add(info);
                }
            }
        }
    }*/
    public AggsFieldBuider addGeo(GeoInfo... geoInfo){
        if( Validator.check(geoInfo) ){
            for(GeoInfo info:geoInfo){
                if( !geoInfos.contains(info) ){
                    geoInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addGeo(List<GeoInfo> geoInfo){
        if( Validator.check(geoInfo) ){
            for(GeoInfo info:geoInfo){
                if( !geoInfos.contains(info) ){
                    geoInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(TermInfo ... termInfo){
        if( Validator.check(termInfo) ){
            for(TermInfo info:termInfo){
                if( !termInfos.contains(info) ){
                    termInfos.add(info);
                }
            }
        }
    }
   /* public AggsFieldBuider(List<TermInfo> termInfo){
        if( Validator.check(termInfo) ){
            for(TermInfo info:termInfo){
                if( !termInfos.contains(info) ){
                    termInfos.add(info);
                }
            }
        }
    }*/
    public AggsFieldBuider addTerm(TermInfo ... termInfo){
        if( Validator.check(termInfo) ){
            for(TermInfo info:termInfo){
                if( !termInfos.contains(info) ){
                    termInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addTerm(List<TermInfo> termInfo){
        if( Validator.check(termInfo) ){
            for(TermInfo info:termInfo){
                if( !termInfos.contains(info) ){
                    termInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(OperationInfo ... operationInfo){
        if( Validator.check(operationInfo) ){
            for(OperationInfo info:operationInfo){
                if( !operationInfos.contains(info) ){
                    operationInfos.add(info);
                }
            }
        }
    }
    /*public AggsFieldBuider(List<OperationInfo> operationInfo){
        if( Validator.check(operationInfo) ){
            for(OperationInfo info:operationInfo){
                if( !operationInfos.contains(info) ){
                    operationInfos.add(info);
                }
            }
        }
    }*/
    public AggsFieldBuider addOperation(OperationInfo ... operationInfo){
        if( Validator.check(operationInfo) ){
            for(OperationInfo info:operationInfo){
                if( !operationInfos.contains(info) ){
                    operationInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addOperation(List<OperationInfo> operationInfo){
        if( Validator.check(operationInfo) ){
            for(OperationInfo info:operationInfo){
                if( !operationInfos.contains(info) ){
                    operationInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(DateInfo ... dateInfo){
        if( Validator.check(dateInfo) ){
            for(DateInfo info:dateInfo){
                if( !dateInfos.contains(info) ){
                    dateInfos.add(info);
                }
            }
        }
    }
   /* public AggsFieldBuider(List<DateInfo> dateInfo){
        if( Validator.check(dateInfo) ){
            for(DateInfo info:dateInfo){
                if( !dateInfos.contains(info) ){
                    dateInfos.add(info);
                }
            }
        }
    }*/
    public AggsFieldBuider addDate(DateInfo ... dateInfo){
        if( Validator.check(dateInfo) ){
            for(DateInfo info:dateInfo){
                if( !dateInfos.contains(info) ){
                    dateInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addDate(List<DateInfo> dateInfo){
        if( Validator.check(dateInfo) ){
            for(DateInfo info:dateInfo){
                if( !dateInfos.contains(info) ){
                    dateInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(TopData ... topData){
        if( Validator.check(topData) ){
            for(TopData info:topData){
                if( !topDatas.contains(info) ){
                    topDatas.add(info);
                }
            }
        }
    }
    /*public AggsFieldBuider(List<TopData> topData){
        if( Validator.check(topData) ){
            for(TopData info:topData){
                if( !topDatas.contains(info) ){
                    topDatas.add(info);
                }
            }
        }
    }*/
    public AggsFieldBuider addTopDatas(TopData ... topData){
        if( Validator.check(topData) ){
            for(TopData info:topData){
                if( !topDatas.contains(info) ){
                    topDatas.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(PriceInfo ... priceInfo){
        if( Validator.check(priceInfo) ){
            for(PriceInfo info:priceInfo){
                if( !priceInfos.contains(info) ){
                    priceInfos.add(info);
                }
            }
        }
    }
    public AggsFieldBuider addPriceInfos(PriceInfo ... priceInfo){
        if( Validator.check(priceInfo) ){
            for(PriceInfo info:priceInfo){
                if( !priceInfos.contains(info) ){
                    priceInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addPriceInfos(List<PriceInfo> priceInfo){
        if( Validator.check(priceInfo) ){
            for(PriceInfo info:priceInfo){
                if( !priceInfos.contains(info) ){
                    priceInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(IpRangeInfo ... ipRangeInfo){
        if( Validator.check(ipRangeInfo) ){
            for(IpRangeInfo info:ipRangeInfo){
                if( !this.ipRangeInfos.contains(info) ){
                    this.ipRangeInfos.add(info);
                }
            }
        }
    }
    public AggsFieldBuider addIpRangeInfos(IpRangeInfo ... ipRangeInfo){
        if( Validator.check(ipRangeInfo) ){
            for(IpRangeInfo info:ipRangeInfo){
                if( !this.ipRangeInfos.contains(info) ){
                    this.ipRangeInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addIpRangeInfos(List<IpRangeInfo> ipRangeInfo){
        if( Validator.check(ipRangeInfo) ){
            for(IpRangeInfo info:ipRangeInfo){
                if( !this.ipRangeInfos.contains(info) ){
                    this.ipRangeInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(GridInfo ... gridInfo){
        if( Validator.check(gridInfo) ){
            for(GridInfo info:gridInfo){
                if( !this.gridInfos.contains(info) ){
                    this.gridInfos.add(info);
                }
            }
        }
    }
    public AggsFieldBuider addGridInfos(GridInfo ... gridInfo){
        if( Validator.check(gridInfo) ){
            for(GridInfo info:gridInfo){
                if( !this.gridInfos.contains(info) ){
                    this.gridInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addGridInfos(List<GridInfo> gridInfo){
        if( Validator.check(gridInfo) ){
            for(GridInfo info:gridInfo){
                if( !this.gridInfos.contains(info) ){
                    this.gridInfos.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(KeywordsCombine ... keywordsCombine){
        if( Validator.check(keywordsCombine) ){
            for(KeywordsCombine info:keywordsCombine){
                if( !this.keywordsCombines.contains(info) ){
                    this.keywordsCombines.add(info);
                }
            }
        }
    }
    public AggsFieldBuider addKeywordsCombines(KeywordsCombine ... keywordsCombine){
        if( Validator.check(keywordsCombine) ){
            for(KeywordsCombine info:keywordsCombine){
                if( !this.keywordsCombines.contains(info) ){
                    this.keywordsCombines.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addKeywordsCombines(List<KeywordsCombine> keywordsCombine){
        if( Validator.check(keywordsCombine) ){
            for(KeywordsCombine info:keywordsCombine){
                if( !this.keywordsCombines.contains(info) ){
                    this.keywordsCombines.add(info);
                }
            }
        }
        return this;
    }

    public AggsFieldBuider(MatrixInfo ... matrixInfo){
        if( Validator.check(matrixInfo) ){
            for(MatrixInfo info:matrixInfo){
                if( !this.matrixInfos.contains(info) ){
                    this.matrixInfos.add(info);
                }
            }
        }
    }
    public AggsFieldBuider addMatrixInfos(MatrixInfo ... matrixInfo){
        if( Validator.check(matrixInfo) ){
            for(MatrixInfo info:matrixInfo){
                if( !this.matrixInfos.contains(info) ){
                    this.matrixInfos.add(info);
                }
            }
        }
        return this;
    }
    public AggsFieldBuider addMatrixInfos(List<MatrixInfo> matrixInfo){
        if( Validator.check(matrixInfo) ){
            for(MatrixInfo info:matrixInfo){
                if( !this.matrixInfos.contains(info) ){
                    this.matrixInfos.add(info);
                }
            }
        }
        return this;
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
    public List<PriceInfo> getPriceInfos() {
        return priceInfos;
    }

    public List<IpRangeInfo> getIpRangeInfos() {
        return ipRangeInfos;
    }

    public List<GridInfo> getGridInfos() {
        return gridInfos;
    }

    public List<KeywordsCombine> getKeywordsCombines() {
        return keywordsCombines;
    }

    public List<MatrixInfo> getMatrixInfos() {
        return matrixInfos;
    }
}
