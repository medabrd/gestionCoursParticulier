package services;
import dataBase.AdminDAO;
import models.Admin;
import java.sql.SQLException;
import java.util.List;

public class AdminService {

    private final AdminDAO adminDAO;

    public AdminService(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public List<Admin> getAllAdmins() throws SQLException {
        return adminDAO.getAllAdmins();
    }

    public Admin getAdminById(int id) throws SQLException {
        return adminDAO.getAdminById(id);
    }

    public Admin getAdminByMail(String mail) throws SQLException {
        return adminDAO.getAdminByMail(mail);
    }

    public void saveAdmin(Admin admin) throws SQLException {
        adminDAO.saveAdmin(admin);
    }

    public void updateAdmin(Admin admin) throws SQLException {
        adminDAO.updateAdmin(admin);
    }

    public void deleteAdmin(int id) throws SQLException {
        adminDAO.deleteAdmin(id);
    }

    public void deleteAdmin(String mail) throws SQLException {
        adminDAO.deleteAdmin(mail);
    }
}
