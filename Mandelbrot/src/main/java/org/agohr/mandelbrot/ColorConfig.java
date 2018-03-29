package org.agohr.mandelbrot;

class ColorConfig {

    private final float saturation; // 0-1
    private final float brightness; // 0-1
    private final float hueOffset; // 0-1
    private final float hueRange; // 0-1

    ColorConfig() {
        this.saturation = 1.0f;
        this.brightness = 1.0f;
        this.hueOffset = 0.7f;
        this.hueRange = -0.2f;
    }

    /** @return the saturation */
    float saturation() {
        return this.saturation;
    }

    /** @return the brightness */
    float brightness() {
        return this.brightness;
    }

    /** @return the hueOffset */
    float hueOffset() {
        return this.hueOffset;
    }

    /** @return the hueRange */
    float hueRange() {
        return this.hueRange;
    }

}
