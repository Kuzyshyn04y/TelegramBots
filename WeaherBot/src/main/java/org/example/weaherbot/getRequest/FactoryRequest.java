package org.example.weaherbot.getRequest;

public class FactoryRequest {
    private static GetOnFiveDays sevenRequest;
    private static GetPoint point;

    static public GetOnFiveDays getOnFive(){
        if(sevenRequest == null)
            sevenRequest = new GetOnFiveDays();
        return sevenRequest;
    }

    static public GetPoint getPointEntity(){
        if(point == null)
            point = new GetPoint();
        return point;
    }
}
