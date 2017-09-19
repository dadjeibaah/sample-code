package com.onecreation.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StudentRecord implements Serializable {
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final long MILLIS_IN_YEAR = 31556952000L;
    private String lastName;    // Required
    private String firstName;   // Required
    private String id;          // Optional
    private String dateOfBirth; // Optional
    private int age;            // Optional

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public static class StudentRecordBuilder {
        private String firstName;
        private String lastName;
        private String id = "";
        private String dateOfBirth = "";
        private int age = 0;

        public StudentRecordBuilder(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public StudentRecordBuilder id(String s) {
            this.id = s;
            return this;
        }

        public StudentRecordBuilder dateOfBirth(String s) {
            this.dateOfBirth = s;
            return this;
        }

        public StudentRecord build() {
            return new StudentRecord(this);
        }

    }

    private StudentRecord(StudentRecordBuilder b) {
        this.id = b.id;
        this.lastName = b.lastName;
        this.firstName = b.firstName;
        this.dateOfBirth = b.dateOfBirth;
        this.age = b.age;
    }

    public void computeAge() {
        DateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        try {
            Date parseDate = dateFormat.parse(this.dateOfBirth);
            Date now = new Date();
            long millisDiff = now.getTime() - parseDate.getTime();
            this.age = (int) (millisDiff / MILLIS_IN_YEAR);
            System.out.println(String.format("Computed student age: %d", this.age));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return this.id;
    }

    public String getLastName(){return this.lastName;}
    public String getFirstName(){return this.firstName;}
    public String getAge(){return String.valueOf(age);}

    @Override
    public String toString() {
        return
                lastName + '\n' +
                        firstName + '\n' +
                        id + '\n' +
                        dateOfBirth + '\n' +
                        age + '\n';
    }
}
