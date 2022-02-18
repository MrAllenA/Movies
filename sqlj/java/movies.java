
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class movies {

         Connection conn = null;
    private Connection connect() {  //connection to database movies

        try {
            // db parameters
            String url = "jdbc:sqlite:movies.db";
            // create a connection to the database
            conn = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

          return conn;
    }

 public void create(){  //creating table movies

   String s1="CREATE TABLE IF NOT EXISTS movies(\n"+"ID INTEGER PRIMARY KEY,\n"+"NAME TEXT NOT NULL,\n"+"ACTOR TEXT,\n"+"ACTRESS TEXT,\n"+"YEAR TEXT,\n"+"DIRECTOR TEXT);";
   try{
        Statement smt = conn.createStatement();
        smt.execute(s1);
    } catch (SQLException e) {
   System.out.println(e.getMessage());
  }
 }

    public  void Insert(int ID,String NAME,String ACTOR,String ACTRESS,String YEAR,String DIRECTOR){   //inserting values into table movies

      String s2 = "INSERT INTO movies(ID,NAME,ACTOR,ACTRESS,YEAR,DIRECTOR) VALUES(?,?,?,?,?,?)";
      try (
          PreparedStatement pstmt = conn.prepareStatement(s2)) {
      pstmt.setInt(1, ID);
      pstmt.setString(2, NAME );
      pstmt.setString(3, ACTOR);
      pstmt.setString(4, ACTRESS);
      pstmt.setString(5, YEAR);
      pstmt.setString(6, DIRECTOR);
      pstmt.executeUpdate();
  } catch (SQLException e) {
      System.out.println(e.getMessage());
  }

}
public void select(String ACTOR){        // select query for parameter actor
  String sql = "SELECT ID, NAME, ACTOR, ACTRESS, YEAR, DIRECTOR FROM movies WHERE ACTOR="+"\""+ACTOR+"\"";

  try (
       Statement stmt  = conn.createStatement();
       ResultSet rs    = stmt.executeQuery(sql)){
      System.out.println("DETAILS OF MOVIES IN WHICH ACTOR "+ACTOR+" HAS PERFORMED");
    System.out.println("ID"+"\t"+"NAME"+"\t"+"ACTOR"+"\t"+"ACTRESS"+"\t"+"YEAR"+"\t"+"DIRECTOR"+"\t");
      // loop through the result set
      while (rs.next()) {
          System.out.println(rs.getInt("ID") +  "\t" +
                             rs.getString("NAME") + "\t" +
                             rs.getString("ACTOR") + "\t" +
                             rs.getString("ACTRESS") + "\t" +
                             rs.getString("YEAR") + "\t" +
                             rs.getString("DIRECTOR"));
      }
  } catch (SQLException e) {
      System.out.println(e.getMessage());
  }


}
public void selectAll(){  // displays all data in table movies
        String sql = "SELECT ID, NAME, ACTOR, ACTRESS, YEAR, DIRECTOR FROM movies";

        try (
             Statement stmt  = conn.createStatement();
             ResultSet rs    = stmt.executeQuery(sql)){
          System.out.println("ALL DATA IN MOVIES TABLE");
          System.out.println("ID"+"\t"+"NAME"+"\t"+"ACTOR"+"\t"+"ACTRESS"+"\t"+"YEAR"+"\t"+"DIRECTOR"+"\t");
            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("ID") +  "\t" +
                                   rs.getString("NAME") + "\t" +
                                   rs.getString("ACTOR") + "\t" +
                                   rs.getString("ACTRESS") + "\t" +
                                   rs.getString("YEAR") + "\t" +
                                   rs.getString("DIRECTOR"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
      }

    public static void main(String[] args) {

     movies mov = new movies();
     mov.connect();
     mov.create();
        mov.Insert(1,"titanic","leonardo","kate","1997","james cameron");
        mov.Insert(2,"avatar","sam","zoe","2009","james cameron");
       mov.Insert(3,"interstellar","matthew","anne","2014","christopher nolan");
       mov.Insert(4,"wolf of wall street","leonardo","margot","2013","martin scorsese");
       mov.selectAll();
       mov.select("leonardo");
    }
}
