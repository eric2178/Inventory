package miss_class_objects;

import java.util.Date;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Tanisha
 */
public class Users
{

    private final int MIN_LENGTH = 8;
    private final int MAX_LENGTH = 16;
    private SimpleStringProperty employeeId;
    private SimpleStringProperty oldPassword; 
    private SimpleStringProperty newPassword;
    private SimpleStringProperty permissions;

    // Permissions
    // The NumberUppercase method accepts a string argument and returns
    // the number of uppercase letters it contains.
    public Users()
    {
        employeeId = new SimpleStringProperty();
        oldPassword = new SimpleStringProperty();
        newPassword = new SimpleStringProperty();
        permissions = new SimpleStringProperty();
    }

    private int numberOfUppercase(String str)
    {
        int upperCase = 0;

        // Count uppercase letters in str.
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isUpperCase(str.charAt(i)))
            {
                upperCase++;
            }

        }

        // Return the number of uppercase letters.
        return upperCase;
    }

    // The NumberLowercase method accepts a string argument and returns
    // the number of lowercase letters it contains.
    private int numberOfLowercase(String str)
    {
        int lowerCase = 0;
        // Count uppercase letters in str.
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isLowerCase(str.charAt(i)))
            {
                lowerCase++;
            }

        }
        // Return the number of uppercase letters.
        return lowerCase;
    }

    // The NumberDigits method accepts a string argument and returns
    // the number of digits letters it contains.
    private int numberOfDigits(String str)
    {
        int digit = 0;
        // Count uppercase letters in str.
        for (int i = 0; i < str.length(); i++)
        {
            if (Character.isDigit(str.charAt(i)))
            {
                digit++;
            }

        }
        // Return the number of uppercase letters.
        return digit;
    }

    public int specialCharacters(String str)
    {
        int count = 0;
        // Count uppercase letters in str.
        for (int i = 0; i < str.length(); i++)
        {
            if (str.charAt(i) == '@' || str.charAt(i) == '#' || str.charAt(i) == '$' || str.
                charAt(i) == '+')
            {
                count++;
            }

        }
        // Return the number of uppercase letters.
        return count;
    }

    public boolean isValidPassWord()
    {

        boolean isValid = false;
        if (this.getNewPassword().length() >= MIN_LENGTH && this.
            getNewPassword().length() <= MAX_LENGTH
            && numberOfUppercase(this.getNewPassword()) >= 1
            && numberOfLowercase(this.getNewPassword()) >= 1
            && numberOfDigits(this.getNewPassword()) >= 1 && specialCharacters(this.
                getNewPassword()) >= 1 && !this.getOldPassword().
            equals(this.getNewPassword()))
        {
            isValid = true;
        }

        return isValid;
    }

    public String getEmployeeId()
    {
        return employeeId.get();
    }

    public void setEmployeeId(String employeeId)
    {
        this.employeeId.set(employeeId);
    }

    public String getOldPassword()
    {
        return oldPassword.get();
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword.set(oldPassword);
    }

    public String getNewPassword()
    {
        return newPassword.get();
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword.set(newPassword);
    }

    public String getPermissions()
    {
        return permissions.get();
    }

    public void setPermissions(String permissions)
    {
        this.permissions.set(permissions);
    }
}
