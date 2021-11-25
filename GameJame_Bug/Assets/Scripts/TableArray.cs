using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class TableArray : MonoBehaviour
{
    public int lenght;
    int[,] listArray;

    public GameObject ParentPanel; //Parent Panel you want the new Images to be children of
    public Image prefab;

    private void Start()
    {
        listArray = new int[lenght, lenght];

        for (int i = 0; i < lenght; i++)
        {
            for (int j = 0; j < lenght; j++)
            {
                GameObject NewObj = new GameObject(); //Create the GameObject
                prefab = NewObj.AddComponent<Image>(); //Add the Image Component script
                NewObj.GetComponent<RectTransform>().SetParent(ParentPanel.transform); //Assign the newly created Image GameObject as a Child of the Parent Panel.
                NewObj.SetActive(true); //Activate the GameObject
            }
        }
    }

    private void Update()
    {

    }

}
