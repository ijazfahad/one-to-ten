package com.onetoten.fahad.newp;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Allocation.MipmapControl;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import com.squareup.picasso.Transformation;

public class Blur implements Transformation {
    protected static final int LOW_LIMIT = 1;
    protected static final int UP_LIMIT = 25;
    protected final int blurRadius;
    protected final Context context;

    public Blur(Context context, int radius) {
        this.context = context;
        if (radius < LOW_LIMIT) {
            this.blurRadius = LOW_LIMIT;
        } else if (radius > UP_LIMIT) {
            this.blurRadius = UP_LIMIT;
        } else {
            this.blurRadius = radius;
        }
    }

    public Bitmap transform(Bitmap source) {
        Bitmap sourceBitmap = source;
        Bitmap blurredBitmap = Bitmap.createBitmap(sourceBitmap);
        RenderScript renderScript = RenderScript.create(this.context);
        Allocation input = Allocation.createFromBitmap(renderScript, sourceBitmap, MipmapControl.MIPMAP_FULL, 128);
        Allocation output = Allocation.createTyped(renderScript, input.getType());
        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        script.setInput(input);
        script.setRadius((float) this.blurRadius);
        script.forEach(output);
        output.copyTo(blurredBitmap);
        return blurredBitmap;
    }

    public String key() {
        return "blurred";
    }
}
