package org.remoteme.utils.general;

import java.util.Objects;

public class Counter {
	int number=0;


	public int get(){
		return number;
	}

	public   void increase(){
		increase(1);
	}

	public synchronized  void increase(int number){
		this.number+=number;
	}
}
