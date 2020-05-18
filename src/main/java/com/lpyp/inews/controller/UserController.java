package com.lpyp.inews.controller;

import com.lpyp.inews.entity.User;
import com.lpyp.inews.repository.UserRepository;
import com.lpyp.inews.util.ResultRes;
import com.lpyp.inews.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@RestController
public class UserController {
    @Autowired
    UserRepository userR;

    String host="localhost:8080";
    @PostMapping("/signup")
    public ResultRes signUp(String userName,String password,String email){
        if (userName==null||password==null||email==null)
            return ResultUtil.queryError();
        User user=new User();
        user.setUserName(userName);
        user.setPassword(password);
        user.setEmail(email);
        user=userR.save(user);
        if (user==null)
            return ResultUtil.customStatus(500,"sql error!",null);
        return ResultUtil.success(user);

    }

    @PostMapping("/signin")
    public ResultRes signIn(int id,String password){
        if (id<=0||password==null)
            return ResultUtil.queryError();
        User user=userR.findById(id).get();
        if (user==null)
            return ResultUtil.customStatus(500,"sql error!",null);
        if (!user.getPassword().equals(password))
            return ResultUtil.customStatus(400,"password error!",null);
        user.setPassword(null);
        return ResultUtil.success(user);
    }

    @RequestMapping("/headupload")
    public ResultRes handleFileUpload(@RequestParam("file") MultipartFile file,@RequestParam("id") int id) {
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File(
                                System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images",
                                file.getOriginalFilename())));
                System.out.println(file.getName());
                out.write(file.getBytes());
                out.flush();
                out.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return ResultUtil.customStatus( 500,e.getMessage(),null);
            } catch (IOException e) {
                e.printStackTrace();
                return ResultUtil.customStatus( 500,e.getMessage(),null);
            }
            User user=new User();
            user.setId(id);
            user.setHeadUrl(host+System.getProperty("user.dir")+"\\src\\main\\resources\\static\\images"+file.getName());
            user=userR.save(user);
            if (user==null)
                return ResultUtil.serverError();
            return ResultUtil.customStatus(200,"上传成功",null);

        } else {
            return ResultUtil.customStatus(500,"空文件！",null);
        }
    }
}
