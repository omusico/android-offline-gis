package com.snail.gis.tile.downtile.tileurl;

import com.snail.gis.tile.downtile.factory.IURLEnum;

/**
 * @author Young Ken
 * @since 2015/8/26.
 */
public enum GoogleTiledTypes implements IURLEnum
{
    /** * Google vector map service by city map*/
    GOOGLE_VECTOR,
    GOOGLE_IMAGE,
    GOOGLE_TOPO,
    GOOGLE_NET_ROAD;

    @Override
    public String getName()
    {
        return this.name().toString();
    }
}
