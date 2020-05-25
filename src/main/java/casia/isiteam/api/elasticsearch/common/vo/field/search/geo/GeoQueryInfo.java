package casia.isiteam.api.elasticsearch.common.vo.field.search.geo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ClassName: GeoQueryInfo
 * Description: unknown
 * <p>
 * Created by casia.wzy on 2020/5/25
 * Email: zhiyou_wang@qq.com
 */
public class GeoQueryInfo {
    private Map<String,LonLat> box = new HashMap<>();

    private LonLat distanceGeo ;
    private String distance;
    private String from;
    private String to;

    private List<LonLat> polygon = new ArrayList<>();

    /**
     * 划定box区域
     * @param top_left
     * @param bottom_right
     */
    public GeoQueryInfo(LonLat top_left,LonLat bottom_right) {
        this.box .put("top_left",top_left);
        this.box .put("bottom_right",bottom_right);
    }
    public void addBox(LonLat top_left,LonLat bottom_right){
        this.box .put("top_left",top_left);
        this.box .put("bottom_right",bottom_right);
    }
    /**
     * 重心扩展半径为N的 圆形
     * @param lonLat
     * @param distance N ,例如： 10km
     */
    public GeoQueryInfo(LonLat lonLat, String distance) {
        this.distanceGeo = lonLat;
        this.distance = distance;
    }
    public GeoQueryInfo addDistance(LonLat lonLat, String distance) {
        this.distanceGeo = lonLat;
        this.distance = distance;
        return this;
    }

    /**
     * 重心扩展半径为 从 N1 到 N2 的 环形
     * @param lonLat
     * @param from N1 例如： 10km
     * @param to N2 例如： 10km
     */
    public GeoQueryInfo(LonLat lonLat, String from,String to) {
        this.distanceGeo = lonLat;
        this.from = from;
        this.to = to;
    }
    public GeoQueryInfo addDistanceRange(LonLat lonLat, String from,String to) {
        this.distanceGeo = lonLat;
        this.from = from;
        this.to = to;
        return this;
    }

    /**
     * 划定多边形区域，坐标位置必须大于等于三个
     * @param lonLats
     */
    public GeoQueryInfo(List<LonLat> lonLats) {
        lonLats.forEach(s->{
            if( !this.polygon.contains(s) ){
                this.polygon.add(s);
            }
        });
    }
    public GeoQueryInfo(LonLat ... lonLats) {
        for(LonLat lonLat:lonLats){
            if( !this.polygon.contains(lonLat) ){
                this.polygon.add(lonLat);
            }
        };
    }
    public void addPolygon(List<LonLat> lonLats) {
        lonLats.forEach(s->{
            if( !this.polygon.contains(s) ){
                this.polygon.add(s);
            }
        });
    }
    public GeoQueryInfo addPolygon(LonLat ... lonLats) {
        for(LonLat lonLat:lonLats){
            if( !this.polygon.contains(lonLat) ){
                this.polygon.add(lonLat);
            }
        };
        return this;
    }

    public Map<String, LonLat> getBox() {
        return box;
    }

    public LonLat getDistanceGeo() {
        return distanceGeo;
    }

    public String getDistance() {
        return distance;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public List<LonLat> getPolygon() {
        return polygon;
    }
}
