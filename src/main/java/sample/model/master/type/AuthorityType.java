package sample.model.master.type;

public enum AuthorityType {
    /** 0:管理者 */
    ADMIN,
    /** 1:ユーザ */
    USER;

    public boolean isUser() {
        return this == USER;
    }

    public boolean isAdmin() {
        return this == ADMIN;
    }

}