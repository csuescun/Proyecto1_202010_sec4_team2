package model.data_structures;

public interface IStack<T> extends Iterable<T> 
{

	/**
	 * 
	 * @return
	 */
	public boolean isEmpty(); 

	/**
	 * 
	 * @return
	 */
	public int darTamanio();

	/**
	 * 
	 * @param item
	 */
	public void push(T item); 


	/**
	 * 
	 * @return
	 */
	public T pop();


	/**
	 * 
	 * @return
	 */
	public T peek();

}
