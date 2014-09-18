package com.example.exp.app.utils;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Bundle;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by zhuodong on 3/27/14.
 */
public class ResourcePairUnits {

    private ResourcePairUnits() {
    }

    public static Bundle getResourceExtras(Resources res,int resId) {
        XmlResourceParser parser = res.getXml(resId);
        Bundle b = new Bundle();
        int type;
        try {
            while ((type = parser.next()) != XmlPullParser.START_TAG && type != XmlPullParser.END_DOCUMENT) {
                // Empty loop
            }
            res.parseBundleExtras(parser, b);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return b;
    }
}
