public class MyThread implements Runnable {
    @Override
    public void run() {
     int a=0;
     for(int i=0;i<1000000;i++){
         a++;
     }


    }
}
