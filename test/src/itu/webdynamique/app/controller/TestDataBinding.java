package itu.webdynamique.app.controller;

import itu.webdynamique.app.model.Employe;
import itu.webdynamique.framework.ModelAndView;
import itu.webdynamique.framework.util.DataBinder;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Test unitaire automatisé simulant la requête HTTP, le Data Binding et l'exécution du Controller
 */
public class TestDataBinding {

    // Mock simple de HttpServletRequest pour tester sans serveur Tomcat
    static class MockHttpServletRequest {
        private final Map<String, String> parameters = new HashMap<>();

        public void setParameter(String key, String value) {
            parameters.put(key, value);
        }

        public String getParameter(String name) {
            return parameters.get(name);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("====== TEST BINDING SPRINT 7 / 7BIS ======");

        // Test 1: Binding d'objet Employe
        Employe emp = (Employe) DataBinder.bindObject(
                Employe.class,
                null,
                createMockRequest("id", "42", "nom", "Andria", "prenom", "Koto", "salaire", "2500000.5")
        );

        System.out.println("Objet Employe lié :");
        System.out.println("  ID      = " + emp.getId());
        System.out.println("  Nom     = " + emp.getNom());
        System.out.println("  Prenom  = " + emp.getPrenom());
        System.out.println("  Salaire = " + emp.getSalaire());

        assert emp.getId() == 42 : "ID doit valoir 42";
        assert "Andria".equals(emp.getNom()) : "Nom doit valoir Andria";
        assert "Koto".equals(emp.getPrenom()) : "Prenom doit valoir Koto";
        assert emp.getSalaire() == 2500000.5 : "Salaire doit valoir 2500000.5";

        // Test 2: Appel de la méthode save(Employe) du contrôleur
        EmpController controller = new EmpController();
        ModelAndView mvSave = controller.save(emp);
        System.out.println("\nRetour Save : URL = " + mvSave.getUrl());
        System.out.println("  Message = " + mvSave.getData().get("message"));
        assert "employe/details".equals(mvSave.getUrl());
        assert mvSave.getData().get("employe") == emp;

        // Test 3: Appel de la méthode delete(int id)
        ModelAndView mvDelete = controller.delete(42);
        System.out.println("\nRetour Delete : URL = " + mvDelete.getUrl());
        System.out.println("  DeletedId = " + mvDelete.getData().get("deletedId"));
        assert Integer.valueOf(42).equals(mvDelete.getData().get("deletedId"));

        System.out.println("\n====== TOUS LES TESTS DE BINDING ONT REUSSI ! ======");
    }

    private static jakarta.servlet.http.HttpServletRequest createMockRequest(String... keyValues) {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < keyValues.length; i += 2) {
            map.put(keyValues[i], keyValues[i + 1]);
        }

        // Création d'un proxy dynamique pour HttpServletRequest
        return (jakarta.servlet.http.HttpServletRequest) java.lang.reflect.Proxy.newProxyInstance(
                jakarta.servlet.http.HttpServletRequest.class.getClassLoader(),
                new Class<?>[]{jakarta.servlet.http.HttpServletRequest.class},
                (proxy, method, methodArgs) -> {
                    if ("getParameter".equals(method.getName()) && methodArgs != null && methodArgs.length == 1) {
                        return map.get((String) methodArgs[0]);
                    }
                    return null;
                }
        );
    }
}
