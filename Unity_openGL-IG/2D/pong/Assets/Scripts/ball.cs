using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ball : MonoBehaviour
{
	int dirx, diry;
	public float speed;
	Rigidbody rb;
	Vector3 initialPos;
	Quaternion initialRot;
   
    // Start is called before the first frame update
    void Start()
    {
    	//posicion inicial
    	initialPos = transform.position;
    	initialRot = transform.rotation;

    	//velocidad
    	speed = Random.Range(5f,10f);
    	
    	// dirección
        dirx=Random.Range(0,2);
        if(dirx == 0) dirx =1; 
        else dirx = -1;
        
        diry=Random.Range(0,2);
        if(diry == 0) diry = 1;
        else diry= -1;

       rb = GetComponent<Rigidbody>();
       rb.velocity = new Vector3(dirx*speed, diry*speed, 0);
    }

    void OnCollisionEnter(Collision collision){
        if(collision.gameObject.tag == "Goal"){
            //eliminar bola 
            Destroy(gameObject,0f); 
            Instantiate(gameObject);
            // restablecer bola
            gameObject.transform.position = initialPos;
            gameObject.transform.rotation = initialRot;
        }
        else if(collision.gameObject.tag=="Player"){
        	speed += 1.5f;
        }
    }

    // Update is called once per frame
    void Update()
    {
    		
    }
}
