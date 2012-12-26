package netTopoSim;

class Host extends Device
{
    public IPAddress defaultGW;
    public String hostname;
    
    public Host(String hn,String ipadd, String snm, String def)
    {
        ip = new IPAddress(ipadd);
        subnet = new SubnetMask(snm);
        defaultGW = new IPAddress(def);
        hostname = hn;
    }
}
