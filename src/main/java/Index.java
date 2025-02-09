import java.util.Optional;
import java.util.Comparator;
import java.util.List;

class Index<O, V>{
	private ValueGetter<O, V> g;
	private Comparator<V> c;
	
	private O[] arr;//should be sorted
	public Index(List<O> list, Comparator<V> comparator, ValueGetter<O, V> getter){
		g=getter;
		c=comparator;
		//TODO make sure it is sorted
		//O[] arr=new O[list.size()];
		//for(int i=0; i<arr.lenght; i++){
		//	arr[i]=list.get(i);
		//}
		//list.toArray(a);
		arr=list.toArray(arr);;
		//arr=list.toArray(new O[0]);
	};
	public V getValue(int i){
		return g.get(arr[i]);
	}
	public O getObject(int i){
		return arr[i];
	}
	
	private int compObjVal(int index, V value){//compare object with value
		return c.compare(getValue(index),value);
	}
	private boolean eqObjVal(int index, V value){//equals object with value
		return compObjVal(index,value)==0;
	}
	
	private enum half{
		LOWER,
		UPPER,
		NEIGHTER,
		CENTER
	}/*
	private half whichHalf(int upper, int lower, V value){
		int middle =lower+(upper-lower)/2;
		if (middle==lower || middle==upper){
			if(eqObjVal(lower, value)){return half.LOWER;}
			if(eqObjVal(upper, value)){return half.UPPER;}
			return half.NEIGHTER;
		}
		
	}*/
	public Optional<O> findObject(V value){
		int lower_index=0;
		int upper_index=arr.length-1;
		int middle_index;
		//make sure that value is in bounds
		if (
			compObjVal(lower_index,value)>0 ||
			compObjVal(upper_index,value)<0
		){
			return Optional.empty();
		}
		while(upper_index-lower_index>1){
			middle_index=lower_index+(upper_index-lower_index)/2;
			//switch(whichHalf(lower_index, upper_index, value)){
			int foo=compObjVal(middle_index, value);
			if (foo==0) {
				return Optional.of(getObject(middle_index));
			}
			if (foo<0){
				lower_index=middle_index;
				continue;
			}
			if (foo>0){
				upper_index=middle_index;
				continue;
			}
			//TODO comparator gave us something strange,throw error
			/*
				case 0:
					return Optional.of( getObject(middle_index));
					break;
				case -1:
					lower_index=middle_index;
					break;
				case 1:
					
				default:
					//TODO we checked that value is in range but didn't found it, throw expection
					break;
			}*/
		}
		for(int i=lower_index; i<=upper_index; i++){
			if (eqObjVal(i,value)) {return Optional.of( getObject(i));}
		}
		//TODO we checked that value is in range but didn't found it, throw expection
		return Optional.empty();
	}
	public static void main(String []args){
		System.out.println("Wrong main, Dummy!");
	}
}