package com.snail.gis.geometry;

import com.snail.gis.geometry.primary.Geometry;
import com.snail.gis.geometry.primary.Line;

/**
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/12
 */
public class LineSegment extends Line
{
    @Override
    public Envelope getEnvelope()
    {
        return null;
    }

    @Override
    public boolean equals(Geometry geometry)
    {
        return false;
    }

    @Override
    public double getDistance(Geometry geometry)
    {
        return 0;
    }

    @Override
    public double getLength()
    {
        return 0;
    }

    @Override
    public boolean isClose()
    {
        return false;
    }

    @Override
    public boolean isRing()
    {
        return false;
    }

}
