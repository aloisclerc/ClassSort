package io.github.uottahack_team_2019.classstart;

import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class NoteScreen {
    private static MainActivity activity;

    public static String courseCode;

    public NoteScreen(final MainActivity activity, final String courseCode) {
        NoteScreen.activity = activity;
        NoteScreen.courseCode = courseCode;

        activity.setContentView(R.layout.activity_note);
        ((TextView) activity.findViewById(R.id.titleNote)).setText(courseCode + ": Notes");

        Button addNote = (Button) activity.findViewById(R.id.addNote);

        addNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Note(activity, courseCode, new String[] {"", "", (int) (Math.random() * 10000000f) + ""});
            }
        });

        final File[] files = activity.fileManager.getNotes(courseCode);
        if (files != null) {
            try {
                final String[] fileNames = new String[files.length];
                for (int i = 0; i < files.length; i++) {
                    BufferedReader reader = new BufferedReader(new FileReader(files[i]));
                    fileNames[i] = reader.readLine();
                    reader.close();
                }
                Button[] classes = new Button[files.length * 2];
                for (int i = 0; i < classes.length - 1; i = i + 2) {
                    makeBtn(activity, classes, i, fileNames[i/2]);
                    final int courseIndex = i / 2;
                    classes[i].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            activity.fileManager.openNote(files[courseIndex], courseCode);
                        }
                    });
                    classes[i + 1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            files[courseIndex].delete();
                            new NoteScreen(activity, courseCode);
                        }
                    });
                }
            } catch (IOException e) {
                //give up
            }
        }
    }
    public void makeBtn(MainActivity activity, Button classes[], int i, String name) {
        classes[i] = new Button(activity);

        classes[i].setX(32);
        classes[i].setY(450 + 125*i);
        classes[i].setText(name);


        ConstraintLayout b1 = (ConstraintLayout)activity.findViewById(R.id.noteID);
        ConstraintLayout.LayoutParams l1 = new ConstraintLayout.LayoutParams(800, 200);
        b1.addView(classes[i], l1);



        classes[i+1] = new Button(activity);

        classes[i+1].setX(850);
        classes[i+1].setY(450 + 125*i);
        classes[i+1].setText("X");
        classes[i+1].setTextColor(Color.RED);

        ConstraintLayout b2 = (ConstraintLayout)activity.findViewById(R.id.noteID);
        ConstraintLayout.LayoutParams l2 = new ConstraintLayout.LayoutParams(200, 200);
        b2.addView(classes[i+1], l2);

    }
}
