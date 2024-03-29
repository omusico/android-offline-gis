package com.snail.gis.view.map;
import android.util.Log;

import com.snail.gis.geometry.Coordinate;
import com.snail.gis.geometry.primary.Envelope;
import com.snail.gis.view.map.util.Projection;
import com.snail.gis.tile.TileInfo;

/**
 * @author Young-Ken
 * @version 0.1
 * @since 2016/1/6
 */
public class MapController implements IMapController
{
    private BaseMap map = null;
    public MapController(BaseMap map)
    {
        this.map = map;
    }

    @Override
    public boolean mapScroll(double x, double y)
    {
        double moveX = map.getTileTool().moveX;
        double moveY = map.getTileTool().moveY;
        map.getTileTool().moveX = moveX + x;
        map.getTileTool().moveY = moveY + y;
        map.refresh();
        return false;
    }

    @Override
    public boolean zoomIn()
    {
        if(map.getLevel() == 0)
        {
            return false;
        }
        zoomTo(map.getMapCenter(), map.getLevel() - 1);
        return false;
    }

    @Override
    public boolean zoomOut()
    {
        if(map.getLevel() == map.getTileInfo().getResolutions().length)
        {
            return false;
        }
        zoomTo(map.getMapCenter(), map.getLevel() + 1);
        return false;
    }

    @Override
    public boolean zoomTo(Coordinate point, int level)
    {
        map.setMapCenter(point);
        setMapInfo(level);
        map.refresh();
        return false;
    }

    public boolean zoom(Envelope curEvn, Envelope lastEnv, Coordinate center)
    {
        Coordinate leftTop = new Coordinate(Projection.getInstance(map).toMapPoint((float)curEvn.getMinX(), (float)curEvn.getMinY()));
        Coordinate rightBoom = new Coordinate(Projection.getInstance(map).toMapPoint((float)curEvn.getMaxX(), (float)curEvn.getMaxY()));
        Envelope tempEnv = new Envelope(leftTop, rightBoom);

        zoomLevel(curEvn, lastEnv);

        Log.e("RUN", curEvn.getWidth() + "curEvn.getWidth()" + curEvn.getHeight() + "curEvn.getHeight()" + lastEnv.getWidth() + "lastEnv.getWidth()" + lastEnv.getHeight() + "lastEnv.getHeight()");

        map.refresh();

        return true;
    }

    public boolean zoomTemp(int d)
    {
        if(d > 0)
        {
            zoomOut();
        }else if(d < 0)
        {
            zoomIn();
        }
        map.refresh();
        return true;
    }

    private void zoomLevel(Envelope curEvn, Envelope lastEnv)
    {
        if (curEvn.getArea() > lastEnv.getArea())
        {
            zoomOut();
        }else {
            zoomIn();
        }
    }

    public void setMapInfo(int level)
    {
        TileInfo tileInfo = map.getTileInfo();
        double[] resolutions = tileInfo.getResolutions();
        map.getMapInfo().setCurrentLevel(level);
        map.getMapInfo().setCurrentResolution(resolutions[level]);
        map.getMapInfo().setCurrentScale(tileInfo.getScales()[level]);
        map.getMapInfo().setCurrentEnvelope(map.getMapInfo().calculationEnvelope());
    }

    public boolean scrollTo(double downX, double downY, double upX, double upY)
    {
        Projection projection = Projection.getInstance(map);
        Coordinate downPoint = projection.toMapPoint((float)downX, (float)downY);
        Coordinate upPoint = projection.toMapPoint((float)upX, (float)upY);

        Log.e("RUN",(upPoint.x - downPoint.x)+"  ddddddd  "+(upPoint.y - downPoint.y));
        Coordinate mapCenter = new Coordinate(map.getMapCenter().x - (upPoint.x - downPoint.x),
                map.getMapCenter().y - (upPoint.y - downPoint.y));
        zoomTo(mapCenter, map.getLevel());
        return true;
    }

    @Override
    public boolean refresh()
    {
        map.invalidate();
        return false;
    }
}
