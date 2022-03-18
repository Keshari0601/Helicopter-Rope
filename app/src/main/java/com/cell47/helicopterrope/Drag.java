package com.cell47.helicopterrope;

public class Drag {
    private final double maxDragAcceleration=100;
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
        double force=-Constant.dragConstant*modVSquare/mass;
        if(force>maxDragAcceleration){
            force=maxDragAcceleration;
        }
        else if(force< -maxDragAcceleration){
            force=-maxDragAcceleration;
        }
        return new AccelerationVector(force*ux,force*uy);
    }

    public AccelerationVector getAcceleration(RopeSectionDX ropeSectionDX) {
        return getAcceleration(ropeSectionDX.velocityX+ Constant.helicopterSpeed,ropeSectionDX.velocityY, Constant.mass);
    }
}
