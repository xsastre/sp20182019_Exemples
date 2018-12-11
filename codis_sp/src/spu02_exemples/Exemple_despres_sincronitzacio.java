package spu02_exemples;

//Example that shows multiple threads 
//can execute the same method but in 
//synchronized way. 
class Line2 
{ 

 // if multiple threads(trains) trying to access 
 // this synchronized method on the same Object 
 // but only one thread will be able 
 // to execute it at a time. 
 synchronized public void getLine2() 
 { 
     for (int i = 0; i < 3; i++) 
     { 
         System.out.println(i); 
         try
         { 
             Thread.sleep(400); 
         } 
         catch (Exception e) 
         { 
             System.out.println(e); 
         } 
     } 
 } 
} 

class Train2 extends Thread 
{ 
 // Reference variable of type Line2. 
 Line2 line; 

 Train2(Line2 line) 
 { 
     this.line = line; 
 } 

 @Override
 public void run() 
 { 
     line.getLine2(); 
 } 
} 

public class Exemple_despres_sincronitzacio 
{ 
 public static void main(String[] args) 
 { 
     Line2 obj = new Line2(); 

     // we are creating two threads which share 
     // same Object. 
     Train2 train1 = new Train2(obj); 
     Train2 train2 = new Train2(obj); 
     Train2 train3 = new Train2(obj); 
     Train2 train4 = new Train2(obj); 
     
     // both threads start executing . 
     train1.start(); 
     train2.start(); 
     train3.start(); 
     train4.start(); 
 } 
} 