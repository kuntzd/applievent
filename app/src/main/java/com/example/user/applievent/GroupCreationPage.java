package com.example.user.applievent;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

import com.example.user.applievent.DatabaseInterface.GroupManager;


/**
 * Created by Armand Pei on 27/03/2017.
 */

public class GroupCreationPage extends Activity{

    private GroupManager monGroupe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_creation);
    }


    public GroupCreationPage(GroupCreationPage monGroupe){  /*Enregistrer le nom du groupe entré par l'user*/

        EditText GroupNameEdit;
        String GroupName;

        GroupNameEdit = (EditText) this.findViewById(R.id.nom_groupe);
        GroupName = GroupNameEdit.getText().toString();
        GroupNameEdit.setText(GroupName);

    }
}

