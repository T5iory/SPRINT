<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Détails Résultat</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 40px;
        }
        .card {
            max-width: 500px;
            background: #ffffff;
            padding: 25px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        .success {
            color: #28a745;
            font-weight: bold;
            font-size: 16px;
        }
        ul {
            list-style: none;
            padding: 0;
        }
        li {
            padding: 8px 0;
            border-bottom: 1px solid #eee;
        }
        .badge {
            background-color: #17a2b8;
            color: white;
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
        }
    </style>
</head>
<body>

<div class="card">
    <h2>Résultat Action Controller</h2>
    <p class="success">${message}</p>
    <p><span class="badge">Action : ${action}</span></p>

    <c:if test="${employe != null}">
        <h3>Données de l'objet lié (Binding Model) :</h3>
        <ul>
            <li><strong>ID :</strong> ${employe.id}</li>
            <li><strong>Nom :</strong> ${employe.nom}</li>
            <li><strong>Prénom :</strong> ${employe.prenom}</li>
            <li><strong>Salaire :</strong> ${employe.salaire}</li>
        </ul>
    </c:if>

    <p><a href="${pageContext.request.contextPath}/employe/form">Retour au formulaire</a></p>
</div>

</body>
</html>
