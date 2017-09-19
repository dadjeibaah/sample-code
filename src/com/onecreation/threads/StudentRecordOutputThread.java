package com.onecreation.threads;

import com.onecreation.datasource.Datasource;
import com.onecreation.model.StudentRecord;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;

import static java.lang.System.out;

public class StudentRecordOutputThread extends Thread {
    private String outputFile;
    private InputStream inputStream;
    private Datasource datasource;

    public StudentRecordOutputThread(String outputFile, InputStream inputStream, Datasource datasource) {
        this.outputFile = outputFile;
        this.inputStream = inputStream;
        this.datasource = datasource;


    }

    @Override
    public void run() {
        ObjectInputStream in = null;
        StudentRecord record = null;

        try {
            in = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace(out);
            System.exit(1);
        }


        while (true) {
            try {
                record = (StudentRecord) in.readObject();
            } catch (ClassNotFoundException | IOException e) {
                e.printStackTrace(out);
            }
            if (getRecordFromDatabase(record)) break;
        }


    }

    private boolean getRecordFromDatabase(StudentRecord record) {
        if (record.getId().equals("0")) {
            out.println("Database Process Done");
            out.println(new Date().toString());
            return true;
        } else {
            if (record.getLastName().equals("BLANK")) {
                ArrayList<StudentRecord> results = datasource.findById(record.getId());
                if (results.size() > 0) {
                    out.println("Query result");
                    out.println(results.get(0));
                    out.println("====================");
                } else {
                    out.println("Query result");
                    out.println("No record found");
                    out.println("====================");
                }
            } else {
                out.println("====================");
                out.println("Writing to Database...");
                out.println(record.toString());
                datasource.addRecord(record);
                out.println("====================");
            }

        }
        return false;
    }
}