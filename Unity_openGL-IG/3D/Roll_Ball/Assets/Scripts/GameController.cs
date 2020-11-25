using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class GameController : MonoBehaviour {
	
    public int level, players;
	public GameObject player1, player2;
    public Text msg, points1, points2;
    public string next;
    private bool finished;
    private int max, score1, score2;
    private int scoreP1, scoreP2;
	
 	// Start is called before the first frame update
   	public void Start(){
    	score1 = 0;
    	score2 = 0;
    	finished = false;
    	
    	if(level > 0){
    		scoreP1 = PlayerPrefs.GetInt("scoreP1",0);
    		if(players == 2) scoreP2 = PlayerPrefs.GetInt("scoreP2",0);
    	}
    	showPoints();
    	
        max = 12 + 5*level;
        msg.text = "START";
        Invoke("resetMessage",3f);

    }
    
    // Update is called once per frame
    public void Update(){}
    	
    // Fixed update is called before performing any physics calculation
    public void FixedUpdate(){
    	showPoints();
    }

    // LateUpdate runs after all items have been processsed in Update
    public void LateUpdate(){
    	// score of player1
    	score1 =  player1.GetComponent<ControllerP1>().getScore();

    	if(players == 1){
    		// one player
    		if(score1 == max) {
    			if(finished == false) setScore();
    			finished = true;
    		}
    	}
       	else{
       		// two players
       		// score of player2
    		score2 = player2.GetComponent<ControllerP2>().getScore();
    		if(score1+score2 >= max){
    			if(finished== false) setScore();
    			finished = true;
    		}
    	}

        if(finished){
        	if(level >= 2) showResult();
        	else msg.text = "Starting Next Level";
	    	// start next Level
            Invoke("nextLevel",2f);
        }
    }

    public void resetMessage(){
    	msg.text ="";
    }

    public void restartGame(){
    	//start next level
        SceneManager.LoadScene(next);
    }

    public void nextLevel(){
        Invoke("restartGame",3f);
    }

    public void showResult(){
        //game over
        if(players == 1){ 
        	//one player
        	msg.text = "You win!";
        } 
        else {
        	// two players
			if(scoreP1 > scoreP2) msg.text = "Player 1 wins by "+(scoreP1-scoreP2)+" points.";
			else if(scoreP1 < scoreP2) msg.text = "Player 2 wins by "+(scoreP2-scoreP1)+" points.";
        	else msg.text = "Draw!";
        } 
    }

    public void setScore(){
    	scoreP1 = scoreP1 + score1;
		PlayerPrefs.SetInt("scoreP1",scoreP1);
		
    	if(players == 2){
			scoreP2 = scoreP2 + score2;
			PlayerPrefs.SetInt("scoreP2",scoreP2);
    	}
    }

    public void showPoints(){
		int value;
		value = scoreP1;
		if(finished == false) value = value +score1;
		points1.text = "Points: "+value;
		
		if(players == 2){ 
			value = scoreP2;
			if(finished == false) value = value+score2;
			points2.text = "Points: "+value;
		}
    	
    }
}