package com.mygdx.game.tools;

public class FPScutter {
    double deltaTime;
    double frameRate;

    public FPScutter(double frameRate) {
        this.frameRate = frameRate;
        this.deltaTime = 0;
    }

    public boolean isReadyToUpdate(double deltaTime) {
        this.deltaTime += deltaTime;

        if(60.0 / frameRate <= this.deltaTime) {
            deltaTime = 0;
            return true;
        }

        return false;
    }
}
