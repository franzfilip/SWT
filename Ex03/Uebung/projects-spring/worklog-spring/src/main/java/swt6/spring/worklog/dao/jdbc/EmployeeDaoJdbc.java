package swt6.spring.worklog.dao.jdbc;

import org.springframework.dao.DataAccessException;
import swt6.spring.worklog.domain.Employee;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDaoJdbc {

  private DataSource dataSource;

  // Version 1: Data access code without Spring
  public void insert(final Employee e) throws DataAccessException {
    final String sql =
      "insert into EMPLOYEE (FIRSTNAME, LASTNAME, DATEOFBIRTH) "
      + "values (?, ?, ?)";
    try (Connection conn = dataSource.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, e.getFirstName());
      stmt.setString(2, e.getLastName());
      stmt.setDate(3, Date.valueOf(e.getDateOfBirth()));
      stmt.executeUpdate();
    }
    catch (SQLException ex) {
      System.err.println(ex);
    }
  }
}
