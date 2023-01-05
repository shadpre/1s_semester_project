package MovieCollection.DAL;

import MovieCollection.BE.Subject;

import java.util.ArrayList;

public interface ISubjectDAO {
    ArrayList<Subject> getAllSubjects() throws Exception;
    void deleteSubject(int iD) throws Exception;
    void createSubject(Subject subject) throws Exception;
    void updateSubject(Subject subject) throws Exception;
}
