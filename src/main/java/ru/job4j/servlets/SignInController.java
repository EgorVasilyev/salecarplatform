package ru.job4j.servlets;

import ru.job4j.entity.car.Body;
import ru.job4j.entity.car.Color;
import ru.job4j.entity.car.Engine;
import ru.job4j.entity.car.Year;
import ru.job4j.entity.user.User;
import ru.job4j.service.car.BodyService;
import ru.job4j.service.car.ColorService;
import ru.job4j.service.car.EngineService;
import ru.job4j.service.car.YearService;
import ru.job4j.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SignInController extends HttpServlet {
    private static final UserService USER_SERVICE = UserService.getInstance();
    private static final BodyService BODY_SERVICE = BodyService.getInstance();
    private static final ColorService COLOR_SERVICE = ColorService.getInstance();
    private static final EngineService ENGINE_SERVICE = EngineService.getInstance();
    private static final YearService YEAR_SERVICE = YearService.getInstance();

    @Override
    public void init() throws ServletException {
        super.init();
        List<String> colors = Arrays.asList("Black", "White", "Grey", "Red", "Blue", "Green", "Yellow");
        for (String colorName : colors) {
            if (!COLOR_SERVICE.contains(colorName)) {
                Color color = new Color();
                color.setName(colorName);
                COLOR_SERVICE.save(color);
            }
        }
        List<String> bodies = Arrays.asList("Sedan", "Pickup", "Hatchback", "Supercar", "Minivan", "Roadster", "Track");
        for (String bodyName : bodies) {
            if (!BODY_SERVICE.contains(bodyName)) {
                Body body = new Body();
                body.setName(bodyName);
                BODY_SERVICE.save(body);
            }
        }
        List<String> engines = Arrays.asList("Diesel", "Hybrid", "Gas", "Gasoline");
        for (String engineName : engines) {
            if (!ENGINE_SERVICE.contains(engineName)) {
                Engine engine = new Engine();
                engine.setName(engineName);
                ENGINE_SERVICE.save(engine);
            }
        }
        List<Integer> years = IntStream.range(1960, 2020).collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        for (int yearValue : years) {
            if (!YEAR_SERVICE.contains(yearValue)) {
                Year year = new Year();
                year.setValue(yearValue);
                YEAR_SERVICE.save(year);
            }
        }
        if (!USER_SERVICE.contains("guest")) {
            User guest = new User();
            guest.setLogin("guest");
            guest.setPassword("guest");
            guest.setPhone("0000000000");
            guest.setRole("guest");
            USER_SERVICE.save(guest);
        }
        if (!USER_SERVICE.contains("user")) {
            User user = new User();
            user.setLogin("user");
            user.setPassword("user");
            user.setPhone("8(495)630-10-10");
            user.setRole("user");
            USER_SERVICE.save(user);
        }
        if (!USER_SERVICE.contains("admin")) {
            User admin = new User();
            admin.setLogin("admin");
            admin.setPassword("admin");
            admin.setPhone("8(495)950-40-40");
            admin.setRole("admin");
            USER_SERVICE.save(admin);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action.equals("signIn")) {
            String login = req.getParameter("login");
            String password = req.getParameter("password");
            User user = USER_SERVICE.isCredential(login, password);
            if (user != null) {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("activeUser", user);
                resp.sendRedirect(String.format("%s/ads?action=showAds", req.getContextPath()));
            } else {
                req.setAttribute("error", "Invalid user");
                doGet(req, resp);
            }
        }
        if (action.equals("signUp")) {
            String login = req.getParameter("newLogin");
            if (!USER_SERVICE.contains(login)) {
                String password = req.getParameter("newPassword");
                String phone = req.getParameter("newPhone");

                User user = new User();
                user.setLogin(login);
                user.setPassword(password);
                user.setPhone(phone);
                user.setRole("user");
                int id = USER_SERVICE.save(user);
                user.setId(id);
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("activeUser", user);
                resp.sendRedirect(String.format("%s/ads?action=showAds", req.getContextPath()));
            } else {
                req.setAttribute("error", "This login already exists!");
                doGet(req, resp);
            }
        }
    }
}
