package com.stu.feisuo.walldemo;

import com.larvalabs.svgandroid.SVG;
import com.larvalabs.svgandroid.SVGParser;
import android.content.res.AssetManager;
import android.content.res.Resources;

/**
 * Created by feisuo on 2015/6/20.
 */
public class SvgUtil {

    private AssetManager assetManager;
    private Resources resources;

    public SvgUtil(AssetManager assetManager, Resources resources) {
        this.assetManager=assetManager;
        this.resources=resources;
    }

    /**
     * Loads an SVG file for a given resource.
     * @param resource the resource ID.
     * @return the parsed SVG object.
     */
    public SVG getSVGForResource(int resource) {
        return getSVGForResource(resource, null, null);
    }

    /**
     * Loads an SVG file for a given resource.
     * @param resource the resource ID.
     * @return the parsed SVG object.
     * @param searchColor optionally a color to replace.
     * @param replaceColor the replacement color.
     */
    public SVG getSVGForResource(int resource, Integer searchColor, Integer replaceColor) {
        try {
            if (searchColor == null) {
                return SVGParser.getSVGFromResource(resources, resource);
            } else {
                return SVGParser.getSVGFromResource(resources, resource, searchColor, replaceColor);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
