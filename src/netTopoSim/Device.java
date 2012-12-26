/**
 * This class defines the characteristics of a network device. Every device has an IP address and a MAC address.
 * Sub classes, defined elsewhere, include the following: Host, Router, Switch.
 */
package netTopoSim;

import java.util.*;

/**
 * @author Marc Slaughter
 * @date 19 Nov 2012 (last modified)
 */
class Device
{
    public IPAddress ip;
    public SubnetMask subnet;
    
    public Device()
    {
        ip = null;
        subnet = null;
    }
    
    public Device(IPAddress addr, SubnetMask snm)
    {
        ip = addr;
        subnet = snm;
    }
    
    public IPAddress getNetworkAddress()
    {
        int one = ip.first & subnet.first;
        int two = ip.second & subnet.second;
        int three = ip.third & subnet.third;
        int four = ip.fourth & subnet.fourth;
        
        IPAddress ret = new IPAddress(one, two, three, four);
        return ret;
    }
    
    public boolean onSameNetwork(Device other)
    {
        boolean ret = false;
        IPAddress mine = this.getNetworkAddress();
        IPAddress theirs = other.getNetworkAddress();
        if(mine.equals(theirs))
            ret = true;
        return ret;
    }
}

class IPAddress
{
    public int first;
    public int second;
    public int third;
    public int fourth;
    
    public IPAddress()
    {
        first=second=third=fourth=0;
    }
    
    public IPAddress(int f, int s, int t, int fo)
    {
        
        first = f;
        second = s;
        third = t;
        fourth = fo;
            
        
    }
    
    public IPAddress(String s)
    {
        String [] t = s.split("\\.");
        
        try
        {
            first = Integer.parseInt(t[0]);
            second = Integer.parseInt(t[1]);
            third = Integer.parseInt(t[2]);
            fourth = Integer.parseInt(t[3]);
            
        }
        catch(Exception e)
        {
            System.out.println("Invalid IP Address.");
//            System.out.println(t[0]);
//            System.out.println(t[1]);
//            System.out.println(t[2]);
//            System.out.println(t[3]);
        }
    }
    
    public String toString()
    {
       String s = first + "." + second + "." + third + "." + fourth;
       return s;
    }
    
    public boolean isValid()
    {
        boolean ret = true;
        
        if (first < 0 || first > 255)
            ret = false;
        if (second < 0 || second > 255)
            ret = false;
        if (third < 0 || third > 255)
            ret = false;
        if (fourth < 0 || fourth > 255)
            ret = false;
        
        return ret;
    }
    
    public boolean equals(IPAddress other)
    {
        if(this.first == other.first && this.second == other.second && 
                this.third == other.third && this.fourth == other.fourth)
            return true;
        else
            return false;
    }
}

class SubnetMask extends IPAddress
{
    public SubnetMask(String s)
    {
        super(s);
    }
    public SubnetMask(int f, int s, int t, int fo)
    {
        super(f,s,t,fo);
    }
    
    public boolean isValid()
    {
        boolean ret = true;
        
        if(!(first == 255 || first == 254 || first == 252 || first == 248 || first == 240 || first == 224 || first == 192 || first == 128 || first == 0))
            ret = false;
        if(!(second == 255 || second == 254 || second == 252 || second == 248 || second == 240 || second == 224 || second == 192 || second == 128 || second == 0))
            ret = false;
        if(!(third == 255 || third == 254 || third == 252 || third == 248 || third == 240 || third == 224 || third == 192 || third == 128 || third == 0))
            ret = false;
        if(!(fourth == 255 || fourth == 254 || fourth == 252 || fourth == 248 || fourth == 240 || fourth == 224 || fourth == 192 || fourth == 128 || fourth == 0))
            ret = false;
        
        return ret;
    }
}

class MACAddress
{
    public char [] rep;
    
    
    public MACAddress()
    {
        Random rand = new Random();
        rep = new char[12];
        for(int i = 0; i < 12; i++)
        {
            int num = rand.nextInt(16);
            if (num < 10)
                rep[i] = (char)(num+48);
            else if (num == 10)
                rep[i]='a';
            else if(num == 11)
                rep[i] = 'b';
            else if (num == 12)
                rep[i] = 'c';
            else if (num == 13)
                rep[i] = 'd';
            else if (num == 14)
                rep[i] = 'e';
            else
                rep[i] = 'f';
        }
    }
    
    public boolean isValid()
    {
        if(rep.length != 16)
            return false;
        for(int i = 0; i < rep.length; i++)
        {
            int num = (int)rep[i];
            if(num < 48 || (num > 57 && num < 97) || num > 66)
                return false;
        }
        return true;
    }
    
    public String toString()
    {
        char[] strep = new char[17];
        strep[0] = rep[0];
        strep[1] = rep[1];
        strep[2] = ':';
        strep[3] = rep[2];
        strep[4] = rep[3];
        strep[5] = ':';
        strep[6] = rep[4];
        strep[7] = rep[5];
        strep[8] = ':';
        strep[9] = rep[6];
        strep[10] = rep[7];
        strep[11] = ':';
        strep[12] = rep[8];
        strep[13] = rep[9];
        strep[14] = ':';
        strep[15] = rep[10];
        strep[16] = rep[11];
        
        return new String(strep);
    }
}
