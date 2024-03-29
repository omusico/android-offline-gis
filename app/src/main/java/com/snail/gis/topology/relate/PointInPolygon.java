package com.snail.gis.topology.relate;

import com.snail.gis.enumeration.Location;
import com.snail.gis.algorithm.RobustDeterminant;
import com.snail.gis.geometry.Coordinate;

import java.util.List;

/**
 * 用射线法判断点是否在面内
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/24
 */
public class PointInPolygon
{
    private Coordinate point = null;
    private boolean isPointOnSegment = false;
    private int crossingCount = 0;
    private PointInPolygon(Coordinate point)
    {
        this.point = point;
    }

    public static int locationPointInRing(Coordinate point, List<Coordinate> lists)
    {
        Coordinate[] ring = lists.toArray(new Coordinate[lists.size()]);
        return locationPointInRing(point, ring);
    }

    public static int locationPointInRing(Coordinate point, Coordinate[] ring)
    {
        PointInPolygon pointInRing = new PointInPolygon(point);
        for (int i = 1; i < ring.length; i++)
        {
            Coordinate startPoint = ring[i];
            Coordinate endPoint = ring[i-1];

            pointInRing.crossSegment(startPoint, endPoint);
            if (pointInRing.isPointOnSegment)
            {
                return pointInRing.getLocation();
            }
        }
        return pointInRing.getLocation();
    }

    public void crossSegment(Coordinate startPoint, Coordinate endPoint)
    {
        //完全在右侧,完全在左侧
//        if ((point.x > startPoint.x && point.x < endPoint.x)
//                || (point.x < startPoint.x && point.x > endPoint.x))
        if ((point.x > startPoint.x && point.x > endPoint.x))
        {
            return;
        }

        //和结束点重合
        if(point.y == endPoint.y || point.x == endPoint.y)
        {
            isPointOnSegment = true;
            return;
        }

        //平行于x轴
        if (startPoint.y == point.y && endPoint.y == point.y)
        {
           if ((startPoint.x >= point.x && endPoint.x <= point.x)
                   || (point.x >= endPoint.x && point.x <=startPoint.x))
           {
               isPointOnSegment = true;
           }
            return;
        }

        if((startPoint.y > point.y && endPoint.y <= point.y)
                || (endPoint.y > point.y && startPoint.y <=point.y))
        {
            double x1 = startPoint.x - point.x;
            double x2 = endPoint.x - point.x;
            double y1 = startPoint.y - point.y;
            double y2 = endPoint.y - point.y;

            double xIntSign = RobustDeterminant.signOfDet2x2(x1, y1, x2, y2);

            if (xIntSign == 0.0)
            {
                isPointOnSegment = true;
                return;
            }

            if (y2 < y1)
            {
                xIntSign = -xIntSign;
            }
            if (xIntSign > 0.0)
            {
                crossingCount++;
            }
        }
    }

    public int getLocation()
    {
        if (isPointOnSegment)
        {
            return Location.BOUNDARY;
        }
        if ((crossingCount % 2) == 1)
        {
            return Location.INTERIOR;
        }
        return Location.EXTERIOR;
    }

    public boolean isOnSegment() { return isPointOnSegment; }
}
