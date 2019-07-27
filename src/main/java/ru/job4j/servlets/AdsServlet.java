package ru.job4j.servlets;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import ru.job4j.entity.ad.Ad;
import ru.job4j.entity.car.*;
import ru.job4j.entity.user.User;
import ru.job4j.service.ad.AdService;
import ru.job4j.service.car.*;
import ru.job4j.service.user.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MultipartConfig
public class AdsServlet extends HttpServlet {
    private static final AdService AD_SERVICE = AdService.getInstance();
    private static final BodyService BODY_SERVICE = BodyService.getInstance();
    private static final ColorService COLOR_SERVICE = ColorService.getInstance();
    private static final EngineService ENGINE_SERVICE = EngineService.getInstance();
    private static final YearService YEAR_SERVICE = YearService.getInstance();
    private static final UserService USER_SERVICE = UserService.getInstance();
    private static final CarService CAR_SERVICE = CarService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("showAds")) {
            Map<String, String> filter = new HashMap<>();
            this.fillFilterAndSetAttr(req, filter);
            List<Ad> ads = AD_SERVICE.getAdsByFilter(filter);
            req.setAttribute("ads", ads);
            HashMap<Integer, String> picturesBase64 = new HashMap<>(ads.size());
            ads.forEach(ad -> {
                picturesBase64.put(ad.getId(), Base64.encode(ad.getPicture()));
            });
            req.setAttribute("pictures", picturesBase64);
            req.getRequestDispatcher("/WEB-INF/views/ads.jsp").forward(req, resp);
        }
        if (req.getParameter("action").equals("goToUpdateAd")) {
            int id = Integer.valueOf(req.getParameter("id"));
            Ad ad = AD_SERVICE.getAdById(id);
            String base64String = Base64.encode(ad.getPicture());
            req.setAttribute("picture", base64String);
            req.setAttribute("ad", ad);
            req.setAttribute("bodies", BODY_SERVICE.getBodies());
            req.setAttribute("colors", COLOR_SERVICE.getColors());
            req.setAttribute("engines", ENGINE_SERVICE.getEngines());
            req.setAttribute("years", YEAR_SERVICE.getYears());
            req.getRequestDispatcher("/WEB-INF/views/updateAd.jsp").forward(req, resp);
        }
        if (req.getParameter("action").equals("goToCreateAd")) {
            req.setAttribute("bodies", BODY_SERVICE.getBodies());
            req.setAttribute("colors", COLOR_SERVICE.getColors());
            req.setAttribute("engines", ENGINE_SERVICE.getEngines());
            req.setAttribute("years", YEAR_SERVICE.getYears());
            req.getRequestDispatcher("/WEB-INF/views/createAd.jsp").forward(req, resp);
        }
        if (req.getParameter("action").equals("goToUserAds")) {
            int id = Integer.valueOf(req.getParameter("userId"));
            List<Ad> ads = AD_SERVICE.getAdsByUserId(id);
            req.setAttribute("ads", ads);
            HashMap<Integer, String> picturesBase64 = new HashMap<>(ads.size());
            ads.forEach(ad -> {
                picturesBase64.put(ad.getId(), Base64.encode(ad.getPicture()));
            });
            req.setAttribute("pictures", picturesBase64);
            req.getRequestDispatcher("/WEB-INF/views/userAds.jsp").forward(req, resp);
        }
    }

    private void fillFilterAndSetAttr(HttpServletRequest req, Map<String, String> filter) {
        if (req.getParameter("actual") != null && !req.getParameter("actual").equals("")) {
            filter.put("actual", null);
            req.setAttribute("checkedActual", true);
        } else {
            req.setAttribute("checkedActual", false);
        }

        if (req.getParameter("currentDay") != null && !req.getParameter("currentDay").equals("")) {
            filter.put("currentDay", null);
            req.setAttribute("checkedCurrentDay", true);
        } else {
            req.setAttribute("checkedCurrentDay", false);
        }
        String byName = req.getParameter("byName");
        if (byName != null && !byName.equals("")) {
            filter.put("byName", byName);
            req.setAttribute("model", byName);
        }

        if (req.getParameter("withPhoto") != null && !req.getParameter("withPhoto").equals("")) {
            filter.put("withPhoto", null);
            req.setAttribute("checkedWithPhoto", true);
        } else {
            req.setAttribute("checkedWithPhoto", false);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("action").equals("save") || req.getParameter("action").equals("update")) {
            int userId = Integer.valueOf(req.getParameter("userId"));
            User user = USER_SERVICE.getUserById(userId);

            int valueYear = Integer.valueOf(req.getParameter("year").equals("other...")
                    ? req.getParameter("yearAddit") : req.getParameter("year"));
            Year year;
            if (!YEAR_SERVICE.contains(valueYear)) {
                year = new Year();
                year.setValue(valueYear);
                year.setId(YEAR_SERVICE.save(year));
            } else {
                year = YEAR_SERVICE.getByValue(valueYear);
            }

            String nameBody = req.getParameter("body").equals("other...")
                    ? req.getParameter("bodyAddit") : req.getParameter("body");
            Body body;
            if (!BODY_SERVICE.contains(nameBody)) {
                body = new Body();
                body.setName(nameBody);
                body.setId(BODY_SERVICE.save(body));
            } else {
                body = BODY_SERVICE.getByName(nameBody);
            }

            String nameColor = req.getParameter("color").equals("other...")
                    ? req.getParameter("colorAddit") : req.getParameter("color");
            Color color;
            if (!COLOR_SERVICE.contains(nameColor)) {
                color = new Color();
                color.setName(nameColor);
                color.setId(COLOR_SERVICE.save(color));
            } else {
                color = COLOR_SERVICE.getByName(nameColor);
            }


            String nameEngine = req.getParameter("engine").equals("other...")
                    ? req.getParameter("engineAddit") : req.getParameter("engine");
            Engine engine;
            if (!ENGINE_SERVICE.contains(nameEngine)) {
                engine = new Engine();
                engine.setName(nameEngine);
                engine.setId(ENGINE_SERVICE.save(engine));
            } else {
                engine = ENGINE_SERVICE.getByName(nameEngine);
            }

            String carName = req.getParameter("model");
            Car car = new Car();
            car.setName(carName);
            car.setBody(body);
            car.setColor(color);
            car.setEngine(engine);
            car.setYear(year);
            car.setUser(user);

            if (req.getParameter("action").equals("update")) {
                List<Car> userCars = CAR_SERVICE.getCarsByUserId(userId);
                userCars.forEach(car1 -> {
                    if (car1.equalsWithoutId(car)) {
                        car.setId(car1.getId());
                    }
                });
            }

            if (req.getParameter("action").equals("save") || car.getId() == 0) {
                car.setId(CAR_SERVICE.save(car));
            }

            String desc = req.getParameter("description");
            byte[] picture = getPicture(req);

            if (req.getParameter("action").equals("update")) {
                boolean closed = Boolean.valueOf(req.getParameter("status"));
                int adId = Integer.valueOf(req.getParameter("adId"));
                Ad ad = AD_SERVICE.getAdById(adId);
                ad.setDescription(desc);
                ad.setCar(car);
                if (picture.length != 0) {
                    ad.setPicture(picture);
                }
                ad.setClosed(closed);
                AD_SERVICE.update(ad);

                resp.sendRedirect(String.format("%s/ads?action=goToUpdateAd&id=" + adId, req.getContextPath()));
            }

            if (req.getParameter("action").equals("save")) {
                Ad ad = new Ad();
                ad.setDescription(desc);
                ad.setUser(user);
                ad.setCar(car);
                if (picture.length != 0) {
                    ad.setPicture(picture);
                }
                AD_SERVICE.save(ad);

                resp.sendRedirect(String.format("%s/ads?action=goToCreateAd", req.getContextPath()));
            }
        }
        if (req.getParameter("action").contains("delete")) {
            int id = Integer.valueOf(req.getParameter("id"));
            Ad ad = AD_SERVICE.getAdById(id);
            AD_SERVICE.delete(ad);

            if (req.getParameter("action").equals("deleteFromAll")) {
                resp.sendRedirect(String.format("%s/ads?action=showAds&fromAds=ads&send=showActualAds", req.getContextPath()));
            }

            if (req.getParameter("action").equals("deleteFromMine")) {
                int userId = ad.getUser().getId();
                resp.sendRedirect(String.format("%s/ads?action=goToUserAds&userId=" + userId, req.getContextPath()));
            }
        }
    }

    private byte[] getPicture(HttpServletRequest req) throws IOException, ServletException {
        byte[] buffer;
        Part filePart = req.getPart("file"); // Извлекает <input type="file" name="file">
        InputStream fileContent = filePart.getInputStream();
        try (BufferedInputStream fin = new BufferedInputStream(fileContent)) {
            buffer = new byte[fin.available()];
            fin.read(buffer, 0, buffer.length);
        }
        return buffer;
    }
}

