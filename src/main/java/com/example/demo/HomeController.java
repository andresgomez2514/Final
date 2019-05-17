package com.example.demo;

import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

@Controller
public class HomeController {
    @Autowired
    TwitterRepository twitterRepository;

    @Autowired
    CloudinaryConfig cloudc;

    @RequestMapping("/")
    public String listTwitters(Model model){
        model.addAttribute("twitters", twitterRepository.findAll());
        return "messages";
    }
    @GetMapping("/add")
    public String twitterForm(Model model) {
        model.addAttribute("twitter", new Twitter());
        return "twitterform";
    }

    @PostMapping("/process")
    public String processTwitterorm(@ModelAttribute Twitter twitter,
                              @RequestParam("file")MultipartFile file){
        if (file.isEmpty()){
            return "twitterform";
        }
        try {
            Map uploadResult = cloudc.upload(file.getBytes(),
                    ObjectUtils.asMap("resourcetype", "auto"));
            twitter.setPicture(uploadResult.get("url").toString());
            twitterRepository.save(twitter);
        }catch (IOException e){
            e.printStackTrace();
        }
        return "redirect:/";
        }
    @RequestMapping("detail/{id}")
    public String showTwitter(@PathVariable("id") long id, Model model)
    {
        model.addAttribute("twitter", twitterRepository.findById(id).get());
        return "show";
    }
    @RequestMapping("/update/{id}")
    public String updateTwitter(@PathVariable("id") long id, Model model){
        model.addAttribute("twitter", twitterRepository.findById(id).get());
        return "twitterform";
    }
    @RequestMapping("/delete/{id}")
    public String delTwitter(@PathVariable("id") long id){
        twitterRepository.deleteById(id);
        return "redirect:/";
    }
}
