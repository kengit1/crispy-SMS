package Model;
public class StudentDatabase extends AbstractDatabase<Student>{
    StudentDatabase(String fileName)
    {
        super(fileName);
    }

    @Override
    public Student createRecordFrom(String lineRecord) {
        String[] parts = lineRecord.split(",",-1) ;
        if(parts.length != 6){
            System.out.println("Invalid record in the EmployeeDatabase");
            return null ; // not a safe practice
        }

        return new Student(
                Integer.parseInt(parts[0].trim())
                , parts[1].trim()
                , Integer.parseInt(parts[2].trim())
                , parts[3].trim()
                , parts[4].trim()
                , Float.parseFloat(parts[5].trim())
        );
    }

    @Override
    public boolean contains(String Key)
    {
        return returnAllRecords().stream().anyMatch(record -> Key.equals(record.getSearchKey()));
        // better to avoid the null from the records
    }

    @Override
    public Student getRecord(String Key)
    {
        return returnAllRecords().stream().filter(
                        record -> Key.equals(record.getSearchKey()))
                .findFirst().orElse(null) ;
        /* converted the Collection of ArrayList into a Stream of data which
         *  Java will do searching on it instead of the OG For loop ❤️
         *  */
    }

    public Student getRecordByName(String name){
        return returnAllRecords().stream().filter(
                record -> name.equals(record.getName()))
                .findFirst().orElse(null);
    }
}
