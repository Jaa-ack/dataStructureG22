public class Keyword {
	private String name;
    private int order;
    private int times; 
    
    public Keyword(String name, int order, int times){
		this.name = name;
		this.order = order;
		this.times = times;
    }
    
    public String getName()
    {
    	return name;
    }
    
    public int getOrder()
    {
    	return order;
    }
    
    public int getTimes() {
    	return times;
    }
}