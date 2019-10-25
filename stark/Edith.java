package stark;

import robocode.*;

import java.awt.*;
import java.awt.geom.Point2D;


public class Edith extends AdvancedRobot {
    AdvancedEnemyBot enemy = new AdvancedEnemyBot();
    double firePower, absDeg, hyp;
    int totalEnemys, missedBullet;
    byte moveDirection = 1;


    public void run(){
        setBodyColor(new java.awt.Color(113, 169, 247,1));
        setAdjustGunForRobotTurn(true);
        setAdjustRadarForGunTurn(true);

        enemy.reset();

        while(true) {
            totalEnemys = getOthers();
            doMove();
            doScan();
            doFirePower();
            doGun();
            if (getGunHeat () == 0 && Math.abs (getGunTurnRemaining ()) < 25 && !enemy.none() ) {
                fire(firePower);
            }
            execute();
        }
    }

    public void doMove(){
        if(totalEnemys == 1){
            if (getTime() % 20 == 0) {
                moveDirection *= -1;
                setAhead(150 * moveDirection);
            }
            setTurnRight(normalizeBearing(enemy.getBearing() + 90 - (25 * moveDirection)));
            setAhead(150 * moveDirection);

        }else{
            if (getTime() % 20 == 0) {
                moveDirection *= -1;
                setAhead(150 * moveDirection);
            }
            setTurnRight(normalizeBearing(enemy.getBearing() + 90 - (80 * moveDirection)));
            setAhead(150 * moveDirection);
        }
    }



    public void doFirePower(){
        firePower = Math.min(400 / enemy.getDistance(), 3);
    }

    public void doScan(){
        setTurnRadarLeftRadians(Math.PI*2);
    }

    public void doGun(){
        setTurnGunRight(normalizeBearing(absDeg - getGunHeading()));
    }

    public void onScannedRobot(ScannedRobotEvent e) {
        if (enemy.none() || e.getName().equals(enemy.getName())) {
            enemy.update(e,this);
        }
        enemyFutureXY();
        if(e.getDistance() < 50){
            moveDirection *= -1;
        }
    }

    double absoluteBearing(double x1, double y1, double x2, double y2) {
        double xo = x2-x1;
        double yo = y2-y1;
        hyp = Point2D.distance(x1, y1, x2, y2);
        double arcSin = Math.toDegrees(Math.asin(xo / hyp));
        double bearing = 0;

        if (xo > 0 && yo > 0) { // both pos: lower-Left
            bearing = arcSin;
        } else if (xo < 0 && yo > 0) { // x neg, y pos: lower-right
            bearing = 360 + arcSin; // arcsin is negative here, actually 360 - ang
        } else if (xo > 0 && yo < 0) { // x pos, y neg: upper-left
            bearing = 180 - arcSin;
        } else if (xo < 0 && yo < 0) { // both neg: upper-right
            bearing = 180 - arcSin; // arcsin is negative here, actually 180 + ang
        }

        return bearing;
    }

    public void enemyFutureXY(){
        if (enemy.none()) {
            return;
        }
        double bulletSpeed = 20 - firePower * 3;
        long time = (long)(enemy.getDistance() / bulletSpeed);

        double futureX = enemy.getFutureX(time);
        double futureY = enemy.getFutureY(time);
        absDeg = absoluteBearing(getX(), getY(), futureX, futureY);
    }

    public double normalizeBearing(double angle) {
        while (angle >  180) angle -= 360;
        while (angle < -180) angle += 360;
        return angle;
    }

    public void onRobotDeath(RobotDeathEvent event){
        if(event.getName().equals(enemy.getName())){
            enemy.reset();
        }
    }

    public void onHitWall(HitWallEvent event) {
        if (event.getBearing() > -90 && event.getBearing() <= 90) {
            setBack(100);
        } else {
            setAhead(100);
        }
    }

    public void onHitRobot(HitRobotEvent event) {
        if (event.getBearing() > -90 && event.getBearing() <= 90) {
            setBack(100);
        } else {
            setAhead(100);
        }
    }

    public void onBulletMissed(BulletMissedEvent event) {
        missedBullet += 1;
        if(missedBullet == 2){
            enemy.reset();
        }
    }

}

