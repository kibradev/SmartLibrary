package repository;

import db.Database;
import model.Loan;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanRepository {

    public void add(Loan loan) {
        String sql = "INSERT INTO loans(bookId, studentId, dateBorrowed, dateReturned) VALUES(?,?,?,?)";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getBookId());
            ps.setInt(2, loan.getStudentId());
            ps.setString(3, loan.getDateBorrowed());
            ps.setString(4, loan.getDateReturned());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Loan loan) {
        String sql = "UPDATE loans SET bookId=?, studentId=?, dateBorrowed=?, dateReturned=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, loan.getBookId());
            ps.setInt(2, loan.getStudentId());
            ps.setString(3, loan.getDateBorrowed());
            ps.setString(4, loan.getDateReturned());
            ps.setInt(5, loan.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM loans WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Loan getById(int id) {
        String sql = "SELECT * FROM loans WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Loan l = new Loan();
                l.setId(rs.getInt("id"));
                l.setBookId(rs.getInt("bookId"));
                l.setStudentId(rs.getInt("studentId"));
                l.setDateBorrowed(rs.getString("dateBorrowed"));
                l.setDateReturned(rs.getString("dateReturned"));
                return l;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Loan> getAll() {
        List<Loan> list = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Loan l = new Loan();
                l.setId(rs.getInt("id"));
                l.setBookId(rs.getInt("bookId"));
                l.setStudentId(rs.getInt("studentId"));
                l.setDateBorrowed(rs.getString("dateBorrowed"));
                l.setDateReturned(rs.getString("dateReturned"));
                list.add(l);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean isBookBorrowed(int bookId) {
        String sql = "SELECT id FROM loans WHERE bookId=? AND dateReturned IS NULL";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void returnBook(int loanId, String dateReturned) {
        String sql = "UPDATE loans SET dateReturned=? WHERE id=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dateReturned);
            ps.setInt(2, loanId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
