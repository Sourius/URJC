﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Rotate : MonoBehaviour
{
    // Start is called before the first frame update
    void Start(){}

    // Update is called once per frame
    void Update()
    {
        //translate moves gameobject by transform
        //rotate rotates gameobject by transform
        transform.Rotate(new Vector3(15,30,45)*Time.deltaTime);
    }
}
