package com.snail.gis.topology.visitor;

import com.snail.gis.geometry.primary.Geometry;

/**
 * 短路判断相交
 * @author Young Ken
 * @version 0.1
 * @since 2015/11/23
 */
public abstract class ShortCircuitedGeometryVisitor
{
    private boolean isDone = false;

    public ShortCircuitedGeometryVisitor()
    {

    }

    public void applyTo(Geometry geometry)
    {
        visit(geometry);
        if(isDone())
        {
            isDone = true;
            return;
        }
    }

    protected abstract void visit(Geometry element);

    protected abstract boolean isDone();
}
