package stark;

import robocode.ScannedRobotEvent;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;

public class GhostInTheShell extends Edith {

    Hashtable hostiles = new Hashtable();
    Quad q1, q2, q3, q4, q5, q6, q7, q8, q9, q10, q11, q12, q13, q14, q15, q16;
    double safeX, SafeY;

    public void onScannedRobot(ScannedRobotEvent e) {
        if (!hostiles.containsKey(e.getName())) {
            enemy.reset();
            enemy.update(e, this);
            hostiles.put(e.getName(), enemy);
        } else {
            enemy = (AdvancedEnemyBot) hostiles.get(e.getName());
        }
        if (enemy.none() || e.getName().equals(enemy.getName())) {
            enemy.update(e, this);
        }
        enemyFutureXY();
        if (e.getDistance() < 50) {
            moveDirection *= -1;
        }
        Set<String> scannedHostiles = hostiles.keySet();
        out.println("-------------------------------------------------------------------");
        for (String host : scannedHostiles) {
            //out.println(host);
        }
    }

    public void doMove() {
        getSafePos(getX(), getY());
    }

    public void getSafePos(double x1, double y1) {
        double bfWith = getBattleFieldWidth();
        double bfHeight = getBattleFieldHeight();
        Set<String> scannedHostiles = hostiles.keySet();
        for (String host : scannedHostiles) {
            AdvancedEnemyBot en;
            en = (AdvancedEnemyBot) hostiles.get(host);
            double enX = en.getX();
            double enY = en.getY();
            if (enX < (bfWith / 4) && enY < (bfHeight / 4)) {
                q1.setEnemiesCount(1);
            } else if ((bfWith / 4) <= enX && enX < (bfWith / 2) && enY < (bfHeight / 4)) {
                q2.setEnemiesCount(1);
            } else if ((bfWith / 2) <= enX && enX < (bfWith / 4 * 3) && enY < (bfHeight / 4)) {
                q3.setEnemiesCount(1);
            } else if (enX >= (bfWith / 4 * 3) && enY < (bfHeight / 4)) {
                q4.setEnemiesCount(1);
            } else if (enX < (bfWith / 4) && (bfHeight / 4) < enY && enY < (bfHeight / 2)) {
                q5.setEnemiesCount(1);
            } else if ((bfWith / 4) < enX && enX < (bfWith / 2) && (bfHeight / 4) < enY && enY < (bfHeight / 2)) {
                q6.setEnemiesCount(1);
            } else if ((bfWith / 2) < enX && enX < (bfWith / 4 * 3) && (bfHeight / 4) < enY && enY < (bfHeight / 2)) {
                q7.setEnemiesCount(1);
            } else if (enX >= (bfWith / 4 * 3) && (bfHeight / 4) < enY && enY < (bfHeight / 2)) {
                q8.setEnemiesCount(1);
            } else if (enX < (bfWith / 4) && (bfHeight / 2) < enY && enY < (bfHeight / 4 * 3)) {
                q9.setEnemiesCount(1);
            } else if ((bfWith / 4) < enX && enX < (bfWith / 2) && (bfHeight / 2) < enY && enY < (bfHeight / 4 * 3)) {
                q10.setEnemiesCount(1);
            } else if ((bfWith / 2) < enX && enX < (bfWith / 4 * 3) && (bfHeight / 2) < enY && enY < (bfHeight / 4 * 3)) {
                q11.setEnemiesCount(1);
            } else if (enX >= (bfWith / 4 * 3) && (bfHeight / 2) < enY && enY < (bfHeight / 4 * 3)) {
                q12.setEnemiesCount(1);
            } else if (enX < (bfWith / 4) && enY >= (bfHeight / 4 * 3)) {
                q13.setEnemiesCount(1);
            } else if ((bfWith / 4) <= enX && enX < (bfWith / 2) && enY >= (bfHeight / 4 * 3)) {
                q14.setEnemiesCount(1);
            } else if ((bfWith / 2) <= enX && enX < (bfWith / 4 * 3) && enY >= (bfHeight / 4 * 3)) {
                q15.setEnemiesCount(1);
            } else if (enX >= (bfWith / 4 * 3) && enY >= (bfHeight / 4 * 3)) {
                q16.setEnemiesCount(1);
            }
            out.println(host + " X :" + en.getX());
        }
    }

}


class Quad {
    static int id;
    private double centerX;
    private double centerY;
    public int enemiesCount;

    Quad(double cx, double cy, int enemies) {
        centerX = cx;
        centerY = cy;
        enemiesCount += enemies;
    }

    public void setEnemiesCount(int enemiesCount) {
        this.enemiesCount += enemiesCount;
    }
}