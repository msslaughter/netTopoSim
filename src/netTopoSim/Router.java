package netTopoSim;

public class Router
{
    public Device [] ports;
    public String hostname;
    
    public Router(int numports)
    {
        ports = new Device[numports];
        for(int i = 0; i < numports; i++)
        {
            ports[i] = new Device();
        }
    }
    
    
    public Router(String hn,int numPorts, String [] ips, String [] snms)
    {
        ports = new Device [numPorts];
        for(int i = 0; i < numPorts; i++)
        {
            ports[i] = new Device();
            ports[i].ip = new IPAddress(ips[i]);
            ports[i].ip = new SubnetMask(snms[i]);
        }
        hostname = hn;
        
    }
    
}
