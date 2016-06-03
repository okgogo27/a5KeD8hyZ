package demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import platform.menu.MenuTiles;

@Controller
@RequestMapping("login")
public class LoginController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("")
    public String login() {
        return "login";
    }

    @RequestMapping("validate")
    public String loginValidate(String userName, String passWord) {
        // userName or passWord 为空会抛出异常
        if (userName == null || passWord == null) {
            return "redirect:../login";
        }
        Subject subject = SecurityUtils.getSubject();
        // 防止浏览器后退键切换用户登录bug
        if (subject.getPrincipals() != null) {
            clearMenuAndExit();
        }
        // 执行realm doGetAuthenticationInfo方法
        UsernamePasswordToken token = new UsernamePasswordToken(userName, passWord);
        try {
            subject.login(token);
            if (subject.isAuthenticated()) {
                // 执行realm doGetAuthorizationInfo方法
                return "redirect:../main/welcome";
            }
        } catch (AuthenticationException ex) {
            logger.info(ex.getMessage());
        }
        return "redirect:../login";
    }

    @RequestMapping("exit")
    public String exitSystem() {

        clearMenuAndExit();

        return "redirect:../login";
    }

    // 清空菜单并退出，不太好的方法
    private void clearMenuAndExit() {
        MenuTiles.getMenu().clear();
        MenuTiles.getMenuLeaf().clear();
        SecurityUtils.getSubject().logout();
    }
}
