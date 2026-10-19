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
        .file-info {
            margin-top: 15px;
            padding: 10px 15px;
            background-color: #f8f9fa;
            border-left: 3px solid #17a2b8;
            border-radius: 4px;
        }
        .file-info h4 {
            margin: 0 0 5px 0;
            color: #333;
        }
        .file-info p {
            margin: 3px 0;
            color: #555;
            font-size: 14px;
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

        <!-- Sprint 8 : Affichage des fichiers uploadés -->
        <c:if test="${employe.rectoCin != null}">
            <div class="file-info">
                <h4>Recto CIN</h4>
                <p><strong>Fichier :</strong> ${employe.rectoCin.nom}.${employe.rectoCin.extension}</p>
                <p><strong>Extension :</strong> ${employe.rectoCin.extension}</p>
                <p><strong>Taille :</strong> ${employe.rectoCin.binaire != null ? employe.rectoCin.binaire.length : 0} octets</p>
            </div>
        </c:if>

        <c:if test="${employe.versoCin != null}">
            <div class="file-info">
                <h4>Verso CIN</h4>
                <p><strong>Fichier :</strong> ${employe.versoCin.nom}.${employe.versoCin.extension}</p>
                <p><strong>Extension :</strong> ${employe.versoCin.extension}</p>
                <p><strong>Taille :</strong> ${employe.versoCin.binaire != null ? employe.versoCin.binaire.length : 0} octets</p>
            </div>
        </c:if>
    </c:if>

    <p><a href="${pageContext.request.contextPath}/employe/form">Retour au formulaire</a></p>
</div>

</body>
</html>
