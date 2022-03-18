package com.cell47.helicopterrope;

public class Elastic {
    public double k=-0.1f;

    public Elastic(double k) {
        this.k=-k;
    }

    AccelerationVector getAcceleration(RopeSectionDX startingRope, RopeSectionDX endingROpe){
        double dx= endingROpe.centerX-startingRope.centerX,
                dy= endingROpe.centerY-startingRope.centerY;
        double ux,uy;
        double modDSquare=dx*dx+dy*dy;
        double modD= Math.sqrt(modDSquare);
        ux=dx/modD;
        uy=dy/modD;
        double distance=modD- (endingROpe.radius+ startingRope.radius);
        double ax=k*distance*ux;
        double ay=k*distance*uy;
        return new AccelerationVector(ax, ay);
    }

    public void positionCorrection(RopeSectionDX preRope, RopeSectionDX ropeSectionDX) {
        double dx= ropeSectionDX.centerX-preRope.centerX,
                dy= ropeSectionDX.centerY-preRope.centerY;
        double ux,uy;
        double modDSquare=dx*dx+dy*dy;
        double modD= Math.sqrt(modDSquare);
        ux=dx/modD;
        uy=dy/modD;
        ropeSectionDX.centerX=preRope.centerX+(ropeSectionDX.radius+ preRope.radius)*ux;
        ropeSectionDX.centerY=preRope.centerY+(ropeSectionDX.radius+ preRope.radius)*uy;
    }
}
