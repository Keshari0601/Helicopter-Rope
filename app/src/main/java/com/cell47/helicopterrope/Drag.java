package com.cell47.helicopterrope;

public class Drag {
    double dragConstant;
    public Drag (double dragConstant){
        this.dragConstant=dragConstant;
    }
    AccelerationVector getAcceleration(double vx, double vy , double mass){
        double ux=0,uy=0;
        double modVSquare=vx*vx+vy*vy;
        double modV= Math.sqrt(modVSquare);
        if(modV!=0) {
            if (vx != 0) {
                ux = vx / modV;
            }
            if (vy != 0) {
                uy = vy / modV;
            }
        }
        double force=-dragConstant*modVSquare/mass;
        return new AccelerationVector(force*ux,force*uy);
    }

    public AccelerationVector getAcceleration(RopeSectionDX ropeSectionDX) {
        return getAcceleration(ropeSectionDX.velocityX+ ropeSectionDX.helicopterSpeed,ropeSectionDX.velocityY, ropeSectionDX.mass);
    }
}
