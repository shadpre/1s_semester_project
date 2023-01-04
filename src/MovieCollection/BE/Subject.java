package MovieCollection.BE;

public class Subject {
    String subjectName;
    int iD;

    public Subject(String subjectName, int iD) {
        this.subjectName = subjectName;
        this.iD = iD;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public int getiD() {
        return iD;
    }
}
