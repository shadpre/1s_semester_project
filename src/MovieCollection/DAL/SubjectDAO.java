package MovieCollection.DAL;

import MovieCollection.BE.Subject;
import MovieCollection.BLL.DatabaseConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class SubjectDAO implements ISubjectDAO{
    @Override
    public ArrayList<Subject> getAllSubjects() throws Exception {
        ArrayList<Subject> allSubjects = new ArrayList<>();

        try (Connection connection = DatabaseConnector.getInstance().getConnection();
             Statement stmt = connection.createStatement()){

            String sql = "SELECT * FROM Subject";

            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()) {
                String name = rs.getString("Name");
                int iD = rs.getInt("Id");

                Subject subject = new Subject(name,iD);

                allSubjects.add(subject);

            }
        }
        return allSubjects;
    }

    @Override
    public void deleteSubject(int iD) throws Exception {

    }

    @Override
    public void createSubject(Subject subject) throws Exception {

    }

    @Override
    public void updateSubject(Subject subject) throws Exception {

    }
}
