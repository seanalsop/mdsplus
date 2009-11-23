package MDSplus;

public class Connection 
{
    static {
        try {
          System.loadLibrary("JavaMds");
        }catch(Throwable e)
        {
              System.out.println("Error loading library javamds: "+e);
              e.printStackTrace();
        }
    }
    int sockId;
    
    boolean checkArgs(Data args[])
    {
        for(int i = 0; i < args.length; i++)
            if(!(args[i] instanceof Scalar) && !(args[i] instanceof Array))
                return false;
        return true;
    }
    
    public Connection(java.lang.String mdsipAddr) throws MdsException
    {
        sockId = connectToMds(mdsipAddr);
        if(sockId < 0)
            throw new MdsException("Cannot connect to "+ mdsipAddr);
    }
    
    protected void finalize()
    {
        if(sockId >= 0)
            disconnectFromMds(sockId);
    }
    
    public void openTree(java.lang.String name, int shot) throws MdsException
    {
        openTree(sockId, name, shot);
    }
            
    public void closeAllTree() throws MdsException
    {
        closeTree(sockId);
    }
    
    public void closeTree(java.lang.String name, int shot) throws MdsException
    {
        closeTree(sockId);
    }
    
    public void setDefault(java.lang.String path) throws MdsException
    {
        setDefault(sockId, path);
    }
    
    public Data get(java.lang.String expr, Data args[]) throws MdsException
    {
        if(!checkArgs(args))
            throw new MdsException("Invalid arguments: only scalars and arrays arguments can be passed to COnnection.get()");
        return get(sockId, expr, args);
    }
    
    public Data get(java.lang.String expr)throws MdsException
    {
        return get(expr, new Data[0]);
    }
    
    public void put(java.lang.String path, java.lang.String expr, Data args[]) throws MdsException
    {
        if(!checkArgs(args))
            throw new MdsException("Invalid arguments: only scalars and arrays arguments can be passed to COnnection.put()");
        put(sockId, path, expr, args);
    }
    public void put(java.lang.String path, java.lang.String expr) throws MdsException
    {
        put(path, expr, new Data[0]);
    }
    public void put(java.lang.String path, Data data) throws MdsException
    {
        put(path, "$", new Data[]{data});
    }
    native int connectToMds(java.lang.String addr);
    native void disconnectFromMds(int sockId);
    public native void openTree(int sockId, java.lang.String name, int shot) throws MdsException;
    public native void closeTree(int sockId) throws MdsException;
    public native void setDefault(int sockId, java.lang.String path) throws MdsException;
    public native Data get(int sockId, java.lang.String expr, Data args[]) throws MdsException;
    public native void put(int sockId, java.lang.String path, java.lang.String expr, Data args[]) throws MdsException;
 
    public GetMany getMany()
    {
        return new GetManyInConnection();
    }
    public PutMany putMany()
    {
        return new PutManyInConnection();
    }
    //////////GetMany 
    class GetManyInConnection extends List implements GetMany
    {
        Dictionary evalRes;
        public void insert(int idx, java.lang.String name, java.lang.String expr, Data [] args)
        {
            Dictionary dict = new Dictionary();
            dict.setItem(new String("exp"), new String(expr));
            dict.setItem(new String("name"), new String(name));
            dict.setItem(new String("args"), new List(args));
            try {
                insert(idx, dict);
            }catch(Exception exc)
            {
                System.out.println("INTERNAL ERROR: GetMany insertion outsize List limits");
            }
        }
        public void append(java.lang.String name, java.lang.String expr, Data [] args)
        {
            insert(len(), name, expr, args);
        }
        
        public void insert(java.lang.String prevName, java.lang.String name, java.lang.String expr, Data [] args)
        {
            int idx;
            for(idx = 0; idx < len() && !getElementAt(idx).equals(new String(prevName)); idx++);
            insert(idx, name, expr, args);
        }
        
        public void remove(java.lang.String name)
        {
            String nameStr = new String(name);
            String nameKeyStr = new String("name");
            for(int i = 0; i < len(); i++)
            {
                Dictionary currDict = (Dictionary)getElementAt(i);
                if(currDict.getItem(nameKeyStr).equals(nameStr))
                {
                    remove(i);
                    return;
                }
            }
        }
        
        public void execute() throws MdsException
        {
            Data serializedIn = new Uint8Array(serialize());
            Data serializedOut = Connection.this.get("GetManyExecute($)", new Data[]{serializedIn});
            evalRes = (Dictionary)Data.deserialize(serializedOut.getByteArray());
        }
        
        public Data get(java.lang.String name) throws MdsException
        {
            if(evalRes == null)
                throw new MdsException("GetMany expressions not evaluated yet");
           Dictionary currDict = (Dictionary)evalRes.getItem(new String(name));
           Data retData = currDict.getItem(new String("value"));
           if(retData == null)
               throw new MdsException(currDict.getItem(new String("error")).getString());
           return retData;
        }
    }
    
    
    class PutManyInConnection extends List implements PutMany
    {
        Dictionary evalRes;
        public void insert(int idx, java.lang.String path, java.lang.String expr, Data [] args)
        {
            Dictionary dict = new Dictionary();
            dict.setItem(new String("exp"), new String(expr));
            dict.setItem(new String("node"), new String(path));
            dict.setItem(new String("args"), new List(args));
            try {
                insert(idx, dict);
            }catch(Exception exc)
            {
                System.out.println("INTERNAL ERROR: GetMany insertion outsize List limits");
            }
        }
        public void append(java.lang.String path, java.lang.String expr, Data [] args)
        {
            insert(len(), path, expr, args);
        }
        
        public void insert(java.lang.String prevPath, java.lang.String path, java.lang.String expr, Data [] args)
        {
            int idx;
            for(idx = 0; idx < len() && !getElementAt(idx).equals(new String(prevPath)); idx++);
            insert(idx, path, expr, args);
        }
        
        public void remove(java.lang.String path)
        {
            String pathStr = new String(path);
            String nodeKeyStr = new String("node");
            for(int i = 0; i < len(); i++)
            {
                Dictionary currDict = (Dictionary)getElementAt(i);
                if(currDict.getItem(nodeKeyStr).equals(pathStr))
                {
                    remove(i);
                    return;
                }
            }
        }
        
        public void execute() throws MdsException
        {
            Data serializedIn = new Uint8Array(serialize());
            Data serializedOut = Connection.this.get("PutManyExecute($)", new Data[]{serializedIn});
            evalRes = (Dictionary)Data.deserialize(serializedOut.getByteArray());
        }
        
        public void checkStatus(java.lang.String path) throws MdsException
        {
            if(evalRes == null)
                throw new MdsException("PutMany not executed yet");
           String retMsg = (String)evalRes.getItem(new String(path));
           if(!retMsg.equals(new String("Success")))
               throw new MdsException(retMsg.getString());
        }
    }
    
    
    
    
    
}
