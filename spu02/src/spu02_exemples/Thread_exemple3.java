package spu02_exemples;

public class Thread_exemple3 {
	int[] aleatoris=new int[10];
	public static void main(String[] args){
		System.out.println(Thread.currentThread().getName());
		for(int i=0; i<10; i++){
			new Thread("" + i){
				public void run(){
					System.out.println("Thread: " + getName() + " començant");
					try {
						Thread.sleep((int)(Math.random()*10000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println("Thread: " + getName() + " acabant");
				}
			}.start();
		}
	}
}