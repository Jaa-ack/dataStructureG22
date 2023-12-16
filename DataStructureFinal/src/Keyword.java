public class Keyword {
	public String name;
    public float order;
    
    public Keyword(String name, float order){
		this.name = name;
		this.order = order;
    }
    
    @Override
    public String toString(){
    	return "["+name+","+order+"]";
    }
    
    public String getName()
    {
    	return name;
    }
    
    public float getWeight()
    {
    	return order;
    }
}