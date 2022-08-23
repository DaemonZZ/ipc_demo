package com.thangdn6.aidlserver;
import com.thangdn6.aidlserver.model.Student;

// Declare any non-default types here with import statements

interface IListener {
   void onChange();
   void notify(String message);
}