package com.onecreation.datasource;

import com.onecreation.model.StudentRecord;

import java.sql.*;
import java.util.ArrayList;

public class Datasource {

    private static Datasource instance = null;
    private Connection connection;

    private Datasource() {
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:tcp://localhost/~/test", "sa", "");
            connection.createStatement().execute("DROP TABLE IF EXISTS students");
            try {
                connection.createStatement()
                        .execute("CREATE TABLE students (id VARCHAR(255) PRIMARY KEY, first_name VARCHAR(255), last_name VARCHAR(255), date_of_birth DATE, age VARCHAR )");
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }


        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace(System.out);
        }

    }

    public static Datasource getInstance() {
        if (instance == null) {
            instance = new Datasource();
        }
        return instance;
    }

    public ArrayList<StudentRecord> findById(String id) {
        ArrayList<StudentRecord> matchingRecords = new ArrayList<>();
        try {
            Statement s = connection.createStatement();
            ResultSet r = s.executeQuery("SELECT * FROM students WHERE id ='" + id + "'");
            while (r.next()) {
                StudentRecord record = new StudentRecord
                        .StudentRecordBuilder(r.getString("first_name"), r.getString("last_name"))
                        .id(r.getString("id"))
                        .dateOfBirth(r.getDate("date_of_birth").toString()).build();
                record.computeAge();
                matchingRecords.add(record);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matchingRecords;
    }

    public boolean addRecord(StudentRecord record) {
        try {
            Statement s = connection.createStatement();
            int affectedRows = 0;
            ArrayList<StudentRecord> results = this.findById(record.getId());
            if (results.size() > 0) {
                StudentRecord firstStudent = results.get(0);
                affectedRows = s.executeUpdate(String.format("UPDATE students SET first_name = '%s', last_name = '%s', date_of_birth = '%s', age = '%s' WHERE id = '%s'", firstStudent.getFirstName(),firstStudent.getLastName(), firstStudent.getDateOfBirth(), firstStudent.getAge(),firstStudent.getId()));
            } else {
                affectedRows = s.executeUpdate(String.format("INSERT INTO students (id, first_name, last_name, date_of_birth, age) VALUES ('%s', '%s', '%s','%s', '%s')", record.getId(), record.getFirstName(), record.getLastName(), record.getDateOfBirth(), record.getAge()));
            }

            return affectedRows > 0;


        } catch (SQLException e) {
            return false;
        }
    }

}

