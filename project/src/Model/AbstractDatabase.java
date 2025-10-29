package Model;

import java.io.*;
import java.util.ArrayList;

public abstract class AbstractDatabase<T extends Record> {
    private ArrayList<T> records ;
    protected String fileName ;
    //To call super()
    AbstractDatabase(String filename)
    {
        this.fileName = filename ;
        this.records = new ArrayList<>() ;
    }

    public abstract T createRecordFrom(String lineRecord) ;
    public void readFromFile()
    {
        try(BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            System.out.println("*File handled successfully!");
            String line ;
            while ((line = reader.readLine()) != null)
            {
                T iniRecord = createRecordFrom(line) ;
                if(iniRecord != null)
                    records.add(iniRecord) ;
            }
            //System.out.println("*Done acquiring the data from the database") ;
            reader.close(); // redundant ?
        } catch (FileNotFoundException e) {
            System.out.println("File was not found");
        } catch (IOException e) {
            System.out.println("*Something went wrong");
        }
    }

    public ArrayList<T> returnAllRecords()
    {
        return records ; //needs this ?
    }

    public  boolean contains(String key)
    {
        return returnAllRecords().stream().anyMatch(record -> key.equals(record.getSearchKey()));
    }
    public    T getRecord(String key){
        return returnAllRecords().stream().filter(
                        record -> key.equals(record.getSearchKey()))
                .findFirst().orElse(null) ;
    }
    public boolean insertRecord(T record) {
        // Safe from Duplications
        if (!contains(record.getSearchKey())) {
            records.add(record);
            return true;
        }
        else {
            //System.out.println("*Duplicate record, process terminated");
            return false;
        }
    }
    public void deleteRecord(String Key)
    {
        T record = getRecord(Key) ;
        if(record != null)
            records.remove(record) ;
        else
            System.out.println("*There is no such record.") ;
    }
    public void saveToFile()
    {
        try (FileWriter writer = new FileWriter(fileName)){
            for (T record : records){
                writer.write(record.lineRepresentation()+"\n");
                writer.flush();
            }
            //System.out.println("*Done writing the file");
        }catch (FileNotFoundException e) {
            System.out.println("*File was not found");
        } catch (IOException e) {
            System.out.println("*Something went Wrong while attempting to open the file");
        }
    }
}