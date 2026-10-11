<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${titre != null ? titre : "Formulaire Employé"}</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f6f9;
            margin: 40px;
        }
        .container {
            max-width: 500px;
            background: #ffffff;
            padding: 25px 30px;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
        }
        h2 {
            margin-top: 0;
            color: #333;
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #555;
        }
        input[type="text"], input[type="number"] {
            width: 100%;
            padding: 8px 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            box-sizing: border-box;
        }
        .btn {
            background-color: #007bff;
            color: white;
            border: none;
            padding: 10px 15px;
            font-size: 14px;
            border-radius: 4px;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<div class="container">
    <h2>Formulaire Employé (Test Binding)</h2>

    <!-- Formulaire d'ajout ou mise à jour -->
    <form action="${pageContext.request.contextPath}/employe/save" method="POST">
        <div class="form-group">
            <label for="id">ID :</label>
            <input type="number" id="id" name="id" value="101" required />
        </div>

        <div class="form-group">
            <label for="nom">Nom :</label>
            <input type="text" id="nom" name="nom" value="Rakoto" required />
        </div>

        <div class="form-group">
            <label for="prenom">Prénom :</label>
            <input type="text" id="prenom" name="prenom" value="Jean" required />
        </div>

        <div class="form-group">
            <label for="salaire">Salaire :</label>
            <input type="number" step="0.01" id="salaire" name="salaire" value="1500000.0" required />
        </div>

        <button type="submit" class="btn">Enregistrer (POST /employe/save)</button>
    </form>
</div>

</body>
</html>
