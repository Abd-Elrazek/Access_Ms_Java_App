import java.sql.Date;

public class Table_View{
//Variables 
	private long Serialn, Nbon, Quantitybon, Counter, Distance, Nnote;
	private String Typefuel, Namedriver, Nameresponsible, Codemachine;
	private Date Dateexchange;
	
//Constructor 
    //Defualt Constructor
	public Table_View(){
		
	}
    public Table_View(long nbon , long nnote){
		this.Nbon = nbon;
		this.Nnote = nnote;
	}
	//Construcor for two variables (String code , long counter)
	public Table_View(long serialn ,long counter,String code_machine){
		this.Counter = counter;
		this.Serialn = serialn;
		this.Codemachine = code_machine;
	}
	//Constructor for all variables
    public Table_View(long Serialn, long Nbon,Date Dateexchange,String Typefuel, long Quantitybon,long Counter,long Distance, String Namedriver,long Nnote, String Nameresponsible,String Codemachine){
	 this.Serialn = Serialn;
	 this.Nbon = Nbon;
	 this.Dateexchange = Dateexchange;
	 this.Typefuel = Typefuel;
	 this.Quantitybon = Quantitybon;
	 this.Counter = Counter;
	 this.Distance = Distance;
	 this.Namedriver = Namedriver;
	 this.Nnote = Nnote;
	 this.Nameresponsible = Nameresponsible;
	 this.Codemachine = Codemachine;
	}
	
	 //funcs of get Vars
	public long getSerialn(){
	return Serialn;	
	}
	public long getNbon(){
	 return Nbon;
	}
	public Date getDateexchange(){
	 return Dateexchange;	
	}
	public String getTypefuel(){
	  return Typefuel;	
	}
	public long getQuantitybon(){
	  return Quantitybon;	
	}
	public long getCounter(){
	  return Counter;
	}
	public long getDistance(){
	  return Distance;
	}
	public String getNamedriver(){
	  return Namedriver;
	}
	public long getNnote(){
		return Nnote;
	}
	public String getNameresponsible(){
	 return Nameresponsible;	
	}
	public String getCodemachine(){
	 return Codemachine;	
	}
	//End funcs Set and Get 
	
}