package vo;

public class User {
    private String userName;
    private String password;
    private String chrName;
    private String role;

    public User() {
        super();
    }

    public User(String userName,String password,String chrName,String role) {
        this.userName=userName;
        this.password=password;
        this.chrName=chrName;
        this.role=role;
    }

    public void setUserName(String userName) {
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }
    public void setPassword(String password) {
        this.password=password;
    }

    public String getPassword() {
        return password;
    }
    public void setChrName(String chrName) {
        this.chrName=chrName;
    }

    public String getChrName() {
        return chrName;
    }
    public void setRole(String role) {
        this.role=role;
    }

    public String getRole() {
        return role;
    }

    public String toString() {
        return "vo.User{"+
                "userName="+userName+","+"password="+password+","+"chrName="+chrName+","+"role="+role;
    }
}
