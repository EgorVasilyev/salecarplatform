package ru.job4j.servlets;

import ru.job4j.entity.ad.Ad;
import ru.job4j.entity.car.Car;
import ru.job4j.entity.user.User;
import ru.job4j.service.ad.AdService;
import ru.job4j.service.car.CarService;
import ru.job4j.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UsersServlet extends HttpServlet {
    private static final UserService USER_SERVICE = UserService.getInstance();
    private static final AdService AD_SERVICE = AdService.getInstance();
    private static final CarService CAR_SERVICE = CarService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("goUpdateUser")) {
            int id = Integer.valueOf(req.getParameter("id"));
            User user = USER_SERVICE.getUserById(id);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/views/updateUser.jsp").forward(req, resp);
        }
        if (action.equals("showUsers")) {
            List<User> users = USER_SERVICE.getUsers();
            req.setAttribute("users", users);
            req.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(req, resp);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("update")) {
            int id = Integer.valueOf(req.getParameter("id"));
            String password = req.getParameter("password");
            String phone = req.getParameter("phone");
            String role = req.getParameter("role");

            User user = USER_SERVICE.getUserById(id);
            user.setPassword(password);
            user.setPhone(phone);
            user.setRole(role);

            USER_SERVICE.update(user);
            resp.sendRedirect(String.format("%s/users?action=goUpdateUser&id=" + id, req.getContextPath()));
        }
        if (action.contains("delete")) {
            int id = Integer.valueOf(req.getParameter("id"));

            List<Ad> userAds = AD_SERVICE.getAdsByUserId(id);
            for (int i = 0; i < userAds.size(); i++) {
                AD_SERVICE.delete(userAds.get(i));
            }

            List<Car> userCars = CAR_SERVICE.getCarsByUserId(id);
            for (int i = 0; i < userCars.size(); i++) {
                CAR_SERVICE.delete(userCars.get(i));
            }

            User user = USER_SERVICE.getUserById(id);
            USER_SERVICE.delete(user);

            if (action.equals("deleteFromAll")) {
                resp.sendRedirect(String.format("%s/users?action=showUsers", req.getContextPath()));
            }

            if (action.equals("deleteFromProfile")) {
                req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
            }
        }
    }
}
