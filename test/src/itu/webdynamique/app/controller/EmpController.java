package itu.webdynamique.app.controller;

import itu.webdynamique.app.model.Employe;
import itu.webdynamique.framework.ModelAndView;
import itu.webdynamique.framework.annotation.Controller;
import itu.webdynamique.framework.annotation.RequestParam;
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

    /**
     * Action pour afficher le formulaire d'ajout
     */
    @UrlMapping("/employe/form")
    public ModelAndView form() {
        ModelAndView mv = new ModelAndView();
        mv.setUrl("employe/form");
        mv.setAttribute("titre", "Nouveau formulaire Employé");
        return mv;
    }

    /**
     * Action Save : Binding direct de l'objet Employe envoyé par le formulaire
     * Sprint 7 & 7bis : binding objet
     */
    @UrlMapping(value = "/employe/save", method = "POST")
    public ModelAndView save(Employe emp) {
        System.out.println("[EmpController] Sauvegarde de l'employe : " + emp);

        ModelAndView mv = new ModelAndView();
        mv.setUrl("employe/details");
        mv.setAttribute("message", "Employé enregistré avec succès !");
        mv.setAttribute("employe", emp);
        mv.setAttribute("action", "SAVE");
        return mv;
    }

    /**
     * Action Update : Binding objet Employe
     */
    @UrlMapping(value = "/employe/update", method = "POST")
    public ModelAndView update(Employe emp) {
        System.out.println("[EmpController] Modification de l'employe : " + emp);

        ModelAndView mv = new ModelAndView();
        mv.setUrl("employe/details");
        mv.setAttribute("message", "Employé mis à jour avec succès !");
        mv.setAttribute("employe", emp);
        mv.setAttribute("action", "UPDATE");
        return mv;
    }

    /**
     * Action Delete : Binding simple d'un paramètre id
     */
    @UrlMapping(value = "/employe/delete", method = "GET")
    public ModelAndView delete(@RequestParam("id") int id) {
        System.out.println("[EmpController] Suppression de l'employé avec ID : " + id);

        ModelAndView mv = new ModelAndView();
        mv.setUrl("employe/details");
        mv.setAttribute("message", "Employé #" + id + " supprimé avec succès !");
        mv.setAttribute("deletedId", id);
        mv.setAttribute("action", "DELETE");
        return mv;
    }
}