package emre.cimen.core.utilies;

public class Msg {
    public static final String CREATED = "Record added.";
    public static final String NOT_FOUND = "No Data Found.";
    public static final String VALIDATE_ERROR = "Data Validation Error.";
    public static final String OK = "Transaction successful.";
    public static final String SAME_PHONE = "A record already exists with this phone number.";
    public static final String SAME_EMAIL = "A record already exists with this email address.";
    public static final String FOUND_BY_NAME = "There is the Same Data in the Database.";
    public static final String NOT_FOUND_BY_NAME = "There is no data for this name.";

    public static String getEntityForMsg(Class<?> entity){
        return entity.getSimpleName() + " The Table Contains the Same Data.";
    }
}
