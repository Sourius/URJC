using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FollowupCamera : MonoBehaviour {
	public GameObject player;
	private Vector3 offset;
    public Transform target;

    // Start is called before the first frame update
    void Start(){
    	offset = transform.position - player.transform.position;

    }

    // Update is called once per frame
    void Update(){}

    // LateUpdate runs after all items have been processsed in Update
    void LateUpdate(){
    	transform.position = player.transform.position + offset;
    }
}
