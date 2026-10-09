package itu.webdynamique.app.controller;

import itu.webdynamique.framework.ModelAndView;
import itu.webdynamique.framework.annotation.Controller;
import itu.webdynamique.framework.annotation.UrlMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmpController {

    @UrlMapping("/employe/liste")
    public ModelAndView afficherListe() {

        
        List<String> employes = new ArrayList<>();
        employes.add("ETU003957");
        employes.add("Tsiory Niry-Avo");
        employes.add("MBola Talenta");

        
        ModelAndView mv = new ModelAndView();

        
        mv.setUrl("employe/liste");

        
        mv.setAttribute("employes", employes);
        mv.setAttribute("titre", "Liste des personnes");
        mv.setAttribute("anarana", "Test");

        return mv;
    }
}