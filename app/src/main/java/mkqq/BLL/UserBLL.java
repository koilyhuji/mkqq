package mkqq.BLL;

import mkqq.DAO.UserDAO;
import mkqq.DTO.UserDTO;

public class UserBLL {
    UserDAO userDAO = new UserDAO();
    String loginStatusString = null;

    public boolean checkLogin(String name, String password) {

        boolean isValidUser = userDAO.login(name, password);
        if (isValidUser) {

            UserDTO currentUser = userDAO.getCurrentUserifLoggedIn();
            return true;
        } else {
            loginStatusString = "Tên đăng nhập hoặc mật khẩu không chính xác.";
            return false;
        }
    }
}
