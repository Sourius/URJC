using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class GameStart : MonoBehaviour
{
	public string start;
    public string start2;

    // Start is called before the first frame update
    public void Start(){}

    // Update is called once per frame
    public void Update(){}

 	public void OnePlayerGame(){
    	NewGame(start);
    }
    
    public void TwoPlayerGame(){
        NewGame(start2);
    }

    public void NewGame(string scene){
    	SceneManager.LoadScene(scene);
    }

    public void QuitGame(){
    	Application.Quit();
    }

    public void notImplementedYet(){
        SceneManager.LoadScene("NotImplemented");
    }
}
