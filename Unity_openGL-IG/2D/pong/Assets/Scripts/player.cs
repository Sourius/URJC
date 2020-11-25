using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class player : MonoBehaviour
{

	public KeyCode up, down;
	float speed;
	Rigidbody rb;

    // Start is called before the first frame update
    void Start()
    {	        
    	speed = Random.Range(5f,10f);
    	rb = GetComponent<Rigidbody>();
    }

    // Update is called once per frame
    void Update()
    {
        if(Input.GetKey(up)){
    		rb.velocity=new Vector3(0,speed,0);
        }
        else if(Input.GetKey(down)){
        	rb.velocity=new Vector3(0,-speed,0);
        }
        else{
        	rb.velocity= new Vector3(0,0,0);
        }
    }
}
