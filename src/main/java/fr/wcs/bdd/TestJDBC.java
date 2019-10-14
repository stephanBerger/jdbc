package fr.wcs.bdd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJDBC {
	/* La liste qui contiendra tous les résultats de nos essais */
	private List<String> messages = new ArrayList<String>();
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TestJDBC.class);

	public List<String> executerTests(HttpServletRequest request) {
		/* Chargement du driver JDBC pour MySQL */
		try {
			messages.add("Chargement du driver...");
			Class.forName("com.mysql.jdbc.Driver");
			messages.add("Driver chargé !");
			TestJDBC.LOGGER.info("On charge le driver JDBC");
		} catch (ClassNotFoundException e) {
			messages.add("Erreur lors du chargement : le driver n'a pas été trouvé dans le classpath ! <br/>"
					+ e.getMessage());

		}
		/* Connexion à la base de données */
		String url = "jdbc:mysql://localhost:3306/starwars?serverTimezone=UTC";
		String utilisateur = "root";
		String motDePasse = "07091975";
		Connection connexion = null;
		Statement statement = null;
		ResultSet resultat = null;
		try {
			messages.add("Connexion à la base de données...");
			connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
			messages.add("Connexion réussie !");

			/* Création de l'objet gérant les requêtes */
			statement = connexion.createStatement();
			messages.add("Objet requête créé !");

			/* Exécution d'une requête de lecture */
			resultat = statement.executeQuery("SELECT id, name, surname FROM jedi_masters;");
			messages.add("Requête \"SELECT  id, name, surname FROM jedi_masters;\" effectuée !");

			/* Récupération des données du résultat de la requête de lecture */
			while (resultat.next()) {
				int idUtilisateur = resultat.getInt("id");
				String name = resultat.getString("name");
				String surname = resultat.getString("surname");
				/* Formatage des données pour affichage dans la JSP finale. */
				messages.add(
						"Données retournées par la requête : id = " + idUtilisateur + ", name = " + name
								+ ", surname = " + surname);
			}
		} catch (SQLException e) {
			messages.add("Erreur lors de la connexion : <br/>" + e.getMessage());
			TestJDBC.LOGGER.error("Erreur lors de la connexion");
		} finally {
			messages.add("Fermeture de l'objet ResultSet.");
			if (resultat != null) {
				try {
					resultat.close();
				} catch (SQLException ignore) {
				}
			}
			messages.add("Fermeture de l'objet Statement.");
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException ignore) {
				}
			}
			messages.add("Fermeture de l'objet Connection.");
			if (connexion != null) {
				try {
					connexion.close();
				} catch (SQLException ignore) {
				}
			}
		}

		return messages;
	}
}