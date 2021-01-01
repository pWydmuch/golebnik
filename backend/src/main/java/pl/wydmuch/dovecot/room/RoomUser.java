package pl.wydmuch.dovecot.room;

public class RoomUser {
    private String name;
    private boolean confirmationDone;
    private int number;

    public RoomUser(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isConfirmationDone() {
        return confirmationDone;
    }

    public void setConfirmationDone(boolean confirmationDone) {
        this.confirmationDone = confirmationDone;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
