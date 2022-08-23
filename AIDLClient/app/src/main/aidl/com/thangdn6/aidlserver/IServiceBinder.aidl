package com.thangdn6.aidlserver;
import com.thangdn6.aidlserver.IListener;
import com.thangdn6.aidlserver.model.Student;


interface IServiceBinder {

    void sayHello(String s);

    void setStudentInforChangedListener(IListener listener);

     List<Student> getListStudents();

    void saveStudent(inout Student std,int flag);

     Student getStdById(int id);
}