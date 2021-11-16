package pers.jenche.convertdomain.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 *
 * @author jenche E-mail:jenchecn@outlook.com
 * @project convertdomain
 * @date 2021/11/15 12:12
 * @description baseController 一些公共的Controller方法放到此处
 */
@RestController
//允许跨域
@CrossOrigin
//一级目录
@RequestMapping("/api")
public class BaseController {
}
