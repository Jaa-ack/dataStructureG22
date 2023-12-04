
public class Keyword { // 各個關鍵字資料
	private String name;
    private double weight;
    
    public Keyword(String name, int count, double weight){
		this.name = name;
		this.weight = weight;
    }
    
    @Override
    public String toString(){
    	return "["+name+", "+weight+"]";
    }
    
    public String getName()
    {
    	return name;
    }
    
    public double getWeight()
    {
    	return weight;
    }
}