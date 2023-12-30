public class Keyword {
	private String name;
    private float order;
    private int times; 
    
    public Keyword(String name, float order, int times){
		this.name = name;
		this.order = order;
		this.times = times;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public float getOrder()
    {
    	return order;
    }
    
    public int getTimes() {
    	return times;
    }
}