package project.main.tool;

import javafx.util.Pair;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Random;
import java.util.UUID;

public class Utility {
    private static final int CHECK_CODE_WIDTH = 120;
    private static final int CHECK_CODE_HEIGHT = 35;

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 16).toUpperCase();
    }

    /**
     * Write key-value to cookie without expiration
     *
     * @param response
     * @param key
     * @param value
     * @throws UnsupportedEncodingException
     */
    public static void writeCookie(HttpServletResponse response, String key, String value) throws UnsupportedEncodingException {
        writeCookie(response, key, value, -1);
    }

    /**
     * Write key-value to cookie with expire time
     *
     * @param response
     * @param key
     * @param value
     * @param expires
     * @throws UnsupportedEncodingException
     */
    public static void writeCookie(HttpServletResponse response, String key, String value, int expires) throws UnsupportedEncodingException {
        value = URLEncoder.encode(value, "UTF-8");
        Cookie newCookie = new Cookie(key, value);
        if (expires > 0)
            expires = expires * 60;
        newCookie.setPath("/");
        newCookie.setMaxAge(expires);
        response.addCookie(newCookie);
    }

    /**
     * Read the value correspond to key in cookie
     *
     * @param request
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String readCookie(HttpServletRequest request, String key) throws UnsupportedEncodingException {
        String value = "";
        Cookie[] ck = request.getCookies();
        if (ck == null)
            return "";
        for (Cookie c : ck) {
            if (c.getName().equals(key)) {
                value = URLDecoder.decode(c.getValue(), "UTF-8");
                break;
            }
        }
        return value;
    }

    /**
     * Read file
     *
     * @param filepath
     * @return
     */
    public static String readFile(String filepath) {
        File file = new File(filepath);
        FileInputStream fin = null;
        try {
            fin = new FileInputStream(file);
        } catch (FileNotFoundException e1) {
            System.err.println(e1.getMessage());
        }
        int length = 0;
        byte[] buf = new byte[1024];
        StringBuffer sb = new StringBuffer();
        try {
            while ((length = fin.read(buf)) != -1) {
                sb.append(new String(buf, 0, length, "UTF-8"));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                fin.close();
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return sb.toString();
    }

    /**
     * Create a new check code
     *
     * @return
     */
    public static Pair<BufferedImage, String> createCheckCode() {
        BufferedImage image = new BufferedImage(CHECK_CODE_WIDTH, CHECK_CODE_HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        setBackGround(g);
        setBorder(g);
        drawRandomLine(g);
        String random = drawRandomRum((Graphics2D) g);
        return new Pair<BufferedImage, String>(image, random);
    }

    private static String drawRandomRum(Graphics2D g) {
        g.setColor(Color.RED);
        g.setFont(new Font(null, Font.BOLD, 20));
        String base = "1234567890abcdefghijklmnopqrstuvwxyz";
        StringBuffer sb = new StringBuffer();
        int x = 5, y = 20;
        for (int i = 0; i < 4; i++) {
            int degree = new Random().nextInt() % 30;
            String ch = base.charAt(new Random().nextInt(base.length())) + "";
            sb.append(ch);
            g.rotate(degree * Math.PI / 180, x, y);
            g.drawString(ch, x, y);
            g.rotate(-degree * Math.PI / 180, x, y);
            x += 30;
        }
        return sb.toString();
    }

    private static void drawRandomLine(Graphics g) {
        g.setColor(Color.GREEN);
        for (int i = 0; i < 5; i++) {
            int x1 = new Random().nextInt(CHECK_CODE_WIDTH);
            int y1 = new Random().nextInt(CHECK_CODE_HEIGHT);
            int x2 = new Random().nextInt(CHECK_CODE_WIDTH);
            int y2 = new Random().nextInt(CHECK_CODE_HEIGHT);
            g.drawLine(x1, y1, x2, y2);
        }
    }

    private static void setBorder(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(1, 1, CHECK_CODE_WIDTH - 2, CHECK_CODE_HEIGHT - 2);
    }

    private static void setBackGround(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CHECK_CODE_WIDTH, CHECK_CODE_HEIGHT);
    }

    /**
     * Validate the check code
     *
     * @param request
     * @return
     */
    public static boolean validateCheckCode(HttpServletRequest request) {
        String client = request.getParameter("checkCode");
        String server = (String) request.getSession().getAttribute("checkCode");
        if (client != null && server != null && client.equals(server)) {
            return true;
        } else {
            return false;
        }
    }
}
