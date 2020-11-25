using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class ControllerP2 : MonoBehaviour
{
	public float speed;
    //public Text score2;
    private Rigidbody rb;
    private int points2;

    // Start is called before the first frame update
    public void Start(){
        rb = GetComponent<Rigidbody>();
        points2 = 0;
        //setPoints();
    }
    
    // Update is called once per frame
    public void Update(){}

    // Fixed update is called before performing any physics calculation
    public void FixedUpdate(){
        float mh, mv;
        mh = Input.GetAxis("P2_Horizontal");
        mv = Input.GetAxis("P2_Vertical");
        Vector3 movement = new Vector3(mh,0.0f,mv);
        rb.AddForce(movement*speed);
    }

    // LateUpdate runs after all items have been processsed in Update
    public void LateUpdate(){}

    // OnTriggerEnter is called when the other collider enters the trigger --> when Player first touches other object
    public void OnTriggerEnter(Collider other) {
        //Destroy(other.gameObject); --> destroy object from game
        if(other.gameObject.CompareTag("Pickup")){
            other.gameObject.SetActive(false); // remove object from game
            points2 = points2 + 1;
            //setPoints();
        }
    }

/*    public void setPoints(){
        score2.text = "Points: "+points2;
    }
*/
    public int getScore(){
        return points2;
    }
}
