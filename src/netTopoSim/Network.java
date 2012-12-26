package netTopoSim;

import java.util.*;
import java.io.*;

public class Network
{
    public static LinkedList<Host> net;
    public static Router router;
    
    public static void main(String [] args)
    {
        Network net = new Network();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        int exit = 0;
        System.out.println("Welcome to netTopoSim.  Let's get started.");
        
        //Main Menu
        while(exit != 1)
        {
            System.out.println("What would you like to do? Please enter a number only.");
            System.out.println("1. Add a router (only one router allowed).\n2. Add a host.\n" +
            		"3. Configure current devices.\n4. Do Some pinging\n5. Exit");
            try
            {
                in = br.readLine();
                int choice = Integer.parseInt(in);
                if (choice == 1)
                    routerInput();
                else if (choice == 2)
                    hostInput();
                else if (choice == 3)
                    configDevices();
                else if (choice == 4)
                    pingInput();
                else if(choice == 5)
                {
                    exit = 1;
                    System.out.println("Peace out home-skillet");
                }
                
            }
            catch (IOException e)
            {
                System.out.println("Bad Input");
            }
            
            
        }
    }
    
    public static void routerInput()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        String hn;
        
        try
        {
            System.out.println("Please enter a hostname.");
            hn = br.readLine();
            System.out.println("How many ports on said router?");
            in = br.readLine();
            int numPorts = Integer.parseInt(in);
            router = new Router(numPorts);
            router.hostname = hn;
            String [] ips = new String[numPorts];
            String [] snms = new String[numPorts];
            System.out.println("Enter IP Addresses for each port.");
            for(int i = 0; i < numPorts; i++)
            {
                System.out.print((i + 1) +". ");
                in = br.readLine();
                router.ports[i].ip = new IPAddress(in);
            }
            System.out.println("Enter Subnet Masks for each port.");
            for(int i = 0; i < numPorts; i++)
            {
                System.out.print((i + 1) +". ");
                in = br.readLine();
                router.ports[i].subnet = new SubnetMask(in);
            }
            //addRouter(hn, numPorts, ips, snms);
        }
        catch (IOException e)
        {
            System.out.println("Bad Input");
        }
    }
    
    public static void hostInput()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        String ip;
        String snm;
        String dfg;
        String hn;
        try
        {
            System.out.println("Please Enter a Hostname.");
            hn=br.readLine();

            System.out.println("Enter IP Address, Subnet Mask and Default Gateway");
            System.out.print("IP:");
            ip = br.readLine();
            System.out.print("Subnet Mask:");
            snm = br.readLine();
            System.out.print("Default Gateway:");
            dfg = br.readLine();
            addHost(hn,ip, snm, dfg);
            
        }
        catch (IOException e)
        {
            System.out.println("Bad Input");
        }
        
        
    }
    
    public static void configDevices()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        int choice;
        try
        {
            System.out.println("1. Config Router\n2. Config Hosts.");
            in = br.readLine();
            choice = Integer.parseInt(in);
            if(choice == 1)
                configRouter();
            else if (choice == 2)
                configHost();
            else
            {
                System.out.println("Invalid Choice, returning to main menu");
                return;
            }
        }
        catch (IOException e)
        {
            System.out.println("Bad Input");
        }
    }
    
    public static void configRouter()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        int choice;
        try
        {
            System.out.println("Router Hostname:" + router.hostname);
            System.out.println("Router port IP Addresses and Subnet Masks");
            for(int i = 0; i < router.ports.length; i++)
            {
                System.out.println(i+1);
                System.out.println(router.ports[i].ip);
                System.out.println(router.ports[i].subnet);
                System.out.println();
            }
            System.out.println("1. Re-Configure Router\n2. Leave router the same and return to main menu.");
            in = br.readLine();
            choice = Integer.parseInt(in);
            if(choice == 1)
                routerInput();
            else
                return;
        }
        catch(IOException e)
        {
            System.out.println("Bad Input");
        }
    }
    
    public static void configHost()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        int choice;
        System.out.println("The hosts on you network are:");
        ListIterator<Host> li = net.listIterator();
        Host curr;
        int i = 1;
        String hn,ip,snm,dfg;
        while(li.hasNext())
        {
            curr = li.next();
            System.out.println("Hostname: " + curr.hostname + "IP Address: " + curr.ip + 
                    "Subnet Mask: " + curr.subnet + "Default Gateway: " + curr.defaultGW);
            System.out.println();
            System.out.println("Change this host?\n1. Yes\n2. No");
            try
            {
                in = br.readLine();
                choice = Integer.parseInt(in);
                if(choice == 1)
                {
                    System.out.println("Please Enter a Hostname.");
                    hn=br.readLine();
                    System.out.println("Enter IP Address, Subnet Mask and Default Gateway");
                    System.out.print("IP:");
                    ip = br.readLine();
                    System.out.print("Subnet Mask:");
                    snm = br.readLine();
                    System.out.print("Default Gateway:");
                    dfg = br.readLine();
                    curr.defaultGW = new IPAddress(dfg);
                    curr.ip = new IPAddress(ip);
                    curr.subnet = new SubnetMask(snm);
                    curr.hostname = hn;
                }
                else
                    continue;
            }
            catch (IOException e)
            {
                System.out.println("Bad Input");
            }
        }
    }
    
    public static void pingInput()
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String in;
        int choice=0;
        String hn1,hn2;
        IPAddress ip1,ip2;
        Host h1 = null;
        Host h2 = null;
        while(choice !=3)
        {
        System.out.println("Choose:\n1. Ping by hostname.\n2. Ping by IP Address\n3. Return to main menu");
        try
        {
            in = br.readLine();
            choice = Integer.parseInt(in);
            if (choice == 1)
            {
                boolean hn1val = false;
                boolean hn2val = false;
                System.out.println("Enter hostname where ping will originate.");
                hn1 = br.readLine();
                System.out.println("Enter hostname of destination of ping.");
                hn2 = br.readLine();
                
                ListIterator<Host> li = net.listIterator();
                while(li.hasNext())
                {
                    Host curr = li.next();
                    if(hn1.equals(curr.hostname))
                    {
                        hn1val = true;
                        h1 = curr;
                    }
                    else if(hn2.equals(curr.hostname))
                    {
                        hn2val = true;
                        h2 = curr;
                    }
                }
                if(!hn1val && !hn2val)
                    continue;
                else
                {
                    ping(h1,h2);
                }
                
            }
            else if (choice == 2)
            {
                boolean hn1val = false;
                boolean hn2val = false;
                System.out.println("Enter IP Address where ping will originate.");
                ip1 = new IPAddress(br.readLine());
                System.out.println("Enter IP Address of destination of ping.");
                ip2 = new IPAddress(br.readLine());
                
                ListIterator<Host> li = net.listIterator();
                while(li.hasNext())
                {
                    Host curr = li.next();
                    if(ip1.equals(curr.ip))
                    {
                        hn1val = true;
                        h1 = curr;
                    }
                    else if(ip2.equals(curr.ip))
                    {
                        hn2val = true;
                        h2 = curr;
                    }
                }
                if(!hn1val && !hn2val)
                    continue;
                else
                {
                    ping(h1,h2);
                }
            }
            else
                continue;
        }
        catch (IOException e)
        {
            System.out.println("Bad Input");
        }
        }
    }
    
    public static void ping(Host orig, Host other)
    {
        boolean sameNet = orig.onSameNetwork(other);
        boolean gw = orig.defaultGW.equals(other.defaultGW);
        if(sameNet && gw)
        {
            System.out.println("The devices are on the same network based on IP Address and Subnet Mask.");
            boolean routerPort = false;
            for(int i = 0; i < router.ports.length; i++)
            {
                routerPort = (orig.onSameNetwork(router.ports[i]) && orig.defaultGW.equals(router.ports[i].ip));
                if (routerPort)
                    break;
            }
            if(routerPort)
                System.out.println("Router is configured to route over this network.  Ping Successful");
            else
                System.out.println("Router or hosts are not configured properly.  Ping failed");
        }
        else
        {
            System.out.println("The devices are not on the same network based on IP Address and Subnet Mask, routing is necessary");
            boolean routerPortOrig = false; 
            boolean routerPortOther = false;
            for(int i = 0; i < router.ports.length; i++)
            {
                routerPortOrig = (orig.onSameNetwork(router.ports[i]) && orig.defaultGW.equals(router.ports[i].ip));
                if(routerPortOrig)
                    break;
            }
            for(int i = 0; i < router.ports.length; i++)
            {
                routerPortOther = (other.onSameNetwork(router.ports[i]) && other.defaultGW.equals(router.ports[i].ip));
                if(routerPortOther)
                    break;
            }
            if (routerPortOrig && routerPortOther)
                System.out.println("Router and hosts are configured properly, ping successful");
            else
                System.out.println("Router and/or hosts are not configured properly, ping failed!");
        }
        
    }
    
    public Network()
    {
        net = new LinkedList<Host>();
    }
    
    public static void addRouter(String hn, int numPorts, String [] ips, String [] snms)
    {
        router = new Router(hn, numPorts, ips, snms);
    }
    
    public static void addHost(String hn, String ip, String snm, String gateway)
    {
        
        net.add(new Host(hn, ip, snm, gateway));
    }
}