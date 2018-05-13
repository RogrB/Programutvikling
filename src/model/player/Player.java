package model.player;

import assets.java.SoundManager;
import assets.java.Sprite;
import model.Entity;
import model.PowerUp;
import model.weapons.*;
import model.Shield;

import java.util.Timer;
import java.util.TimerTask;

import javafx.scene.image.Image;
import model.GameModel;
import multiplayer.MultiplayerHandler;
import view.GameView;
import view.ViewUtil;
import view.HUD;

/**
 * <h1>The Player class</h1>
 * The {@code Enemy} class extends the {@code Entity} class and handles
 * all the events for the Player object
 */
public class Player extends Entity {

    /**
     * The singleton object.
     */
    private static Player inst = new Player();
    
    /**
     * Method to access singleton class.
     * @return Returns a reference to the singleton object.
     */        
    public static Player getInst(){ return inst; }

    /**
     * If the player is currently immune - After taking damage the player gets
     * a couple of seconds of immunityframes
     */    
    private boolean immunity = false;
    
    /**
     * If the game is currently running - Used for pause functionality
     */        
    private boolean playing = false;
    
    /**
     * Counter for handling blinking animation during immunityframes
     */        
    private int blinkCounter;
    
    /**
     * The weapon type currently equipped - can be upgraded several times
     */        
    private String weaponType = "Bullet";
    
    /**
     * If the player currently has a shield equipped
     */        
    private boolean hasShield = false;
    
    /**
     * If the player is currently shooting
     */        
    private boolean shooting= false;
    
    /**
     * {@code Shield} object
     * @see Shield
     */        
    private Shield shield = new Shield(getX(), getY(), hasShield());
    
    /**
     * Score
     */        
    private int score;
    
    /**
     * Enemies killed counter - the amount of enemies killed by the player
     */        
    private int enemiesKilled;
    
    /**
     * Bullet hit counter - the amount of enemies hit by the player
     */        
    private int bulletsHit;
    
    /**
     * Bullet counter - the amount of shots fired by the player
     */        
    private int bulletCount;

    /**
     * {@code PlayerBehaviour} object
     * @see PlayerBehaviour
     */        
    private PlayerBehaviour playerBehaviour = new PlayerBehaviour();

    /**
     * <b>Constructor: </b>sets the player sprite, X and Y positions as well as health.
     */        
    private Player(){
        super(
                Sprite.PLAYER,
                40,
                ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER.src).getHeight() / 2,
                5
        );

        setCanShoot(true);
        //getImageView().relocate(getX(), getY());
        weapon = Weapon.PLAYER_BASIC;
    }
    
    /**
     * Method to initiate the object.
     * This method is called when starting a new game, either for the
     * first time or after death.
     */        
    public void init() {
        inst = new Player();
        if (hasShield) {
            removeShield();
        }
        setHealth(5);
        setY(ViewUtil.VIEW_HEIGHT / 2 - (int) new Image(Sprite.PLAYER.src).getHeight() / 2);
        setAlive();
        immunity = false;
        shooting = false;
        score = 0;
        this.weaponType = playerBehaviour.powerUp("Reset");
        enemiesKilled = 0;
        bulletsHit = 0;
        bulletCount = 0;
        canShoot = true;
    }

    /**
     * Method for resuming play 
     * either after pausing the game or 
     * continuing to the next level
     */        
    public void resume(){
        immunity = false;
        shooting = false;
        canShoot = true;
    }

    /**
     * Method that handles updating the player
     * for each tick of the animationtimer in the {@code GameController} class.
     */        
    public void update(){
        if(!playerIsOutOfBounds()){
            setY(getY() + playerBehaviour.next());
            GameView.getInstance().render(this);
        }
        if(shield.isBroken() && hasShield()) {
            removeShield();
        }
        if(shooting){
            shoot();
        }
    }

    /**
     * @return if the player is out of bounds 
     * to keep the player from moving off the screen
     */        
    private boolean playerIsOutOfBounds(){
        if(getY() + playerBehaviour.next() < 10) {
            playerBehaviour.moveStop();
            return true;
        }

        if(getY() + this.height + playerBehaviour.next() >= ViewUtil.VIEW_HEIGHT) {
            playerBehaviour.moveStop();
            return true;
        }
        return false;
    }

    /**
     * Method that handles the powerup events.
     * There are three types of powerups available: Weapon upgrades, health and shield. 
     * @param powerUp {@code PowerUp} object that decides which type
     * of powerup to process
     */        
    public void powerUp(PowerUp powerUp) {
        switch(powerUp.getName()) {
            case "WEAPON_POWERUP":
                powerUp();
                HUD.getInstance().renderPowerUpText("Weapon Upgraded!");
                SoundManager.getInst().upgradeWeapon();
                break;
            case "HEALTH_POWERUP":
                setHealth(getHealth() + 1);
                HUD.getInstance().renderPowerUpText("Health up!");
                SoundManager.getInst().upgradeHealth();
                break;
            case "SHIELD_POWERUP":
                setShield();
                HUD.getInstance().renderPowerUpText("Shield!");
                SoundManager.getInst().upgradeShield();
                break;
        }
    }
    
    /**
     * Method that handles weapon upgrades.
     * If multiplayer is active the method sends a powerup notification to player2, so
     * that the player2 client renders the correct weapon projectiles
     */        
    public void powerUp() {
        this.weaponType = playerBehaviour.powerUp(weaponType);
        if(GameModel.getInstance().getMultiplayerStatus()) {
            MultiplayerHandler.getInstance().send("PowerUp", 0, 0);
        }
    }
    
    /**
     * Method for handling Player movement
     * @param dir sets the direction of motion
     */        
    public void move(String dir){
        switch(dir){
            case "UP":
                playerBehaviour.move(-1);
                break;
            case "DOWN":
                playerBehaviour.move(1);
                break;
            case "STOP":
                playerBehaviour.move(0);
        }
    }

    /**
     * Method that handles shooting events for the player.
     * calls the shoot() method in the {@code PlayerBehaviour} class.
     * then calls a shotDelayTimer to start a countdown for when the player is
     * able to shoot again. This is to limit the amount of times a player can shoot
     * each second.
     * If multiplayer is active the method sends a shot notification to the player2 client.
     */        
    @Override
    public void shoot() {
        if(canShoot()) {
            SoundManager.getInst().shotPlayer();
            playerBehaviour.shoot(this.weaponType, getX(), getY(), this.width, this.height, weapon);
            setCanShoot(false);
            shotDelayTimer();
            bulletCount++;
            if (GameModel.getInstance().getMultiplayerStatus()) {
                GameModel.getInstance().getMP().send("Shoot", getX(), getY());
            }
        }
    }
    
    /**
     * Starts a timer that enables the player to shoot again
     */        
    private void shotDelayTimer() {
        Timer shotTimer = new Timer();
        shotTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                setCanShoot(true);
                this.cancel();
            }
        }, 300);
    }

    /**
     * Method that handles player damage.
     * Checks if the player currently has immunityframes or a shield and
     * calls audio accordingly
     */    
    @Override
    public void takeDamage(){
        if (!hasShield()) {
            if(!isImmune()){
                SoundManager.getInst().impactPlayer();
                super.takeDamage();
                if (isAlive()) {
                    initImmunity();
                }
            }
        }
        else {
            if (!shield.isImmune()) {
                shield.takeDamage();

                SoundManager.getInst().impactShield();
            }
        }
    }

    /**
     * Initiates immunityframes. To give the player immunity for
     * a few seconds after taking damage
     */        
    private void initImmunity(){
        setImmunity(true);
        immunityBlinkAnimation();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                setImmunity(false);
            }
        }, getImmunityTime());

    }

    /**
     * Sets shooting = true
     */        
    public void isShooting(){
        shooting = true;
    }

    /**
     * Sets shooting = false
     */        
    public void isNotShooting(){
        shooting = false;
    }

    /**
     * @return if player currently has immunityframes
     */        
    private boolean isImmune() {
        return this.immunity;
    }
    
    /**
     * @param immunity sets immunity
     */        
    private void setImmunity(boolean immunity) { this.immunity = immunity; }
    
    /**
     * @return gets immunity time in MS
     */        
    private int getImmunityTime() {
        return 2000;
    }

    /**
     * Method to animate a blinking effect during the
     * players immunityframes. To visually communicate that
     * the player has taken damage and is currently immune for a few seconds.
     * This method changes the players sprite based on the images defined in the
     * {@code Sprite} class
     */        
    private void immunityBlinkAnimation() {
        Timer blinkTimer = new Timer();
        blinkTimer.schedule(new TimerTask() {
            
            @Override
            public void run() {
                blinkCounter++;           
                switch(blinkCounter) {
                    case 5:
                        newSprite(Sprite.PLAYER_BLINK1);
                        break;
                    case 10:
                        newSprite(Sprite.PLAYER_BLINK2);
                        break;
                    case 15:
                        newSprite(Sprite.PLAYER_BLINK1);
                        break;
                    case 20:
                        newSprite(Sprite.PLAYER);
                        blinkCounter = 0;
                        break;
                }
                if (!immunity) {
                    this.cancel();
                    newSprite(Sprite.PLAYER);
                }
            }
        }, 0, 20);
    }

    /**
     * Method that sets a shield when the player gets a shield powerup. 
     * Expands the player width by 10 pixels, so that the player hitbox 
     * is wider when a shield is equipped.
     */        
    public void setShield() {
        if (!hasShield()) {
            this.width = getWidth() + 10;
        }
        this.hasShield = true;
        shield = new Shield(this.getX(), this.getY(), true);
    }
    
    /**
     * Method that removes the shield and resets the player width
     */        
    private void removeShield() {
        this.width = getWidth() - 10;
        shield.newSprite(Sprite.CLEAR);
        this.hasShield = false;
    }

    /**
     * Method that sets {@code this} object active 
     * after resuming from pause
     */        
    public void isPlaying(){
        setImmunity(false);
        playing = true; }
    
    /**
     * Method that sets {@code this} object inactive
     * when game is paused
     */        
    public void isNotPlaying() {
        setImmunity(true);
        playing = false;
        isNotShooting();
    }
    
    /**
     * @return if the game is active
     */        
    public boolean getPlaying(){return playing;}
    
    /**
     * @return whether the player currently has a shield equipped
     */        
    public boolean hasShield() {
        return this.hasShield;
    }
    
    /**
     * @return the shield sprite
     */        
    public Image getShieldSprite() {
        return shield.getImage();
    }
    
    /**
     * @return the amount of charges left on the shield
     */        
    public int getShieldCharges() {
        return shield.getCharges();
    }
    
    /**
     * @return the {@code Shield} object
     */        
    public Shield shield() {
        return this.shield;
    }
    
    /**
     * @return the current score
     */        
    public int getScore() {
        return this.score;
    }
    
    /**
     * @param score sets the score
     */        
    public void setScore(int score) {
        this.score = score;
    }
    
    /**
     * @return gets the current weapon type equipped
     */        
    public String getWeaponType() {
        return this.weaponType;
    }
    
    /**
     * @return the amount of enemies killed
     */        
    public int getEnemiesKilled() {
        return this.enemiesKilled;
    }
    
    /**
     * @param e sets the amount of enemies killed
     */        
    public void setEnemiesKilled(int e) {
        this.enemiesKilled = e;
    }
    
    /**
     * @return gets amount of player projectiles that has hit a target
     */        
    public int getBulletsHit() {
        return this.bulletsHit;
    }
    
    /**
     * @param hit sets the amount of player projectiles that has hit a target
     */        
    public void setBulletsHit(int hit) {
        this.bulletsHit = hit;
    }
    
    /**
     * @return the amount of shots fired by the player
     */        
    public int getBulletCount() {
        return this.bulletCount;
    }
    
}
