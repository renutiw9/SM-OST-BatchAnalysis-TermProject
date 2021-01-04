    package cat.mhyark.uni.tfg;
	import java.io.Serializable;
	public class Network implements Serializable {
	    private static final long serialVersionUID = 1L;
	
	    private int No;
	    private double Time;
	    private string Souce;
	    private string Destination;
	    private string Protocol;
	    private int Length;
	    private string Info;


	
	    public void setNo(int no) {
	        this.No = no;
	    }
	
	    public void setTime(double time) {
	        this.Time = time;
	    }
	
	    public void setSource(string source) {
	        this.Source = source;
	    }
	
	    public void setDestination(string destination) {
	        this.Destination = destination;
	    }
	
	    public void setProtocol(string protocol) {
	        this.Protocol = protocol;
}
	    public void setLength(int length) {
	        this.Length = length;
}
	    public void setInfo(string info) {
	        this.Info = info;
	    }
	
        public void setApplications(String applications) {
	        this.applications = applications;
	    }
	
	    public int getNo() {
	        return No;
	    }
	
	    public double getTime() {
	        return Time;
	    }
	
	    public string getSrc() {
	        return Source;
    }
	    public string getDest() {
	        return Destination;
}

	    public string getProtocol() {
	        return Protocol;
}
	    public int getLength() {
	        return Length;
}
	    public string getInfo() {
	        return Info;
	    }	
	    public String getApplications() {
	        return applications;
	    }
	
	    @Override
	    public boolean equals(Object obj) {
	        return super.equals(obj);
	    }
	}

