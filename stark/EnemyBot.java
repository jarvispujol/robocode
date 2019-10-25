package stark;

import robocode.ScannedRobotEvent;

public class EnemyBot {
    private String name;
    private double bearing,distance,energy,heading,velocity;
    private boolean lives;

    public EnemyBot() {
        reset();
    }

    public void update(ScannedRobotEvent e){
        bearing = e.getBearing();
        distance = e.getDistance();
        energy = e.getEnergy();
        heading = e.getHeading();
        name = e.getName();
        velocity = e.getVelocity();
        lives = true;
    }

    public void reset(){
        bearing = 0.0;
        distance = 0.0;
        energy = 0.0;
        heading = 0.0;
        name = "";
        velocity = 0.0;
        lives = false;
    }

    public boolean none(){
        if(name.equals("")){
            return true;
        }else{
            return false;
        }
    }
    public String getName() {
        return name;
    }

    public double getBearing() {
        return bearing;
    }

    public double getDistance() {
        return distance;
    }

    public double getEnergy() {
        return energy;
    }

    public double getHeading() {
        return heading;
    }

    public double getVelocity() {
        return velocity;
    }

    public boolean isLives() {
        return lives;
    }
}
