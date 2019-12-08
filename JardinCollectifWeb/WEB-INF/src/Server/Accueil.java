package Server;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Gestionnaires.*;
import JardinCollectif.*;

/**
 * Servlet qui g�re la connexion d'un utilisateur au syst�me de gestion de
 * biblioth�que
 * 
 * <pre>
 * Vincent Ducharme
 * Universit� de Sherbrooke
 * Version 1.0 - 11 novembre 2018
 * IFT287 - Exploitation de BD relationnelles et OO
 * </pre>
 */

public class Accueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet Accueil : POST");
		try {
			if (!BiblioHelper.peutProcederLogin(getServletContext(), request, response)) {
				System.out.println("Servlet Accueil : POST ne peut pas proc�der.");
				// Le dispatch sera fait par BiblioHelper.peutProceder
				return;
			}

			HttpSession session = request.getSession();

			// Si c'est la premi�re fois qu'on essaie de se logguer, ou
			// d'inscrire quelqu'un
			if (!BiblioHelper.gestionnairesCrees(session)) {
				System.out.println("Servlet Accueil : POST Cr�ation des gestionnaires");
				BiblioHelper.creerGestionnaire(getServletContext(), session);
			}

			if (request.getParameter("connecter") != null) {
				System.out.println("Servlet Accueil : POST - Connecter");
				try {
					// lecture des param�tres du formulaire login.jsp
					String userId = request.getParameter("userId");
					String motDePasse = request.getParameter("motDePasse");

					request.setAttribute("userId", userId);
					request.setAttribute("motDePasse", motDePasse);

					if (userId == null || userId.equals(""))
						throw new IFT287Exception("Le nom d'utilisateur ne peut pas �tre nul!");
					if (motDePasse == null || motDePasse.equals(""))
						throw new IFT287Exception("Le mot de passe ne peut pas �tre nul!");

					if (BiblioHelper.getBiblioInterro(session).getGestionMembre()
							.informationsConnexionValide(Integer.parseInt(userId), motDePasse)) {
						session.setAttribute("userID", userId);
						if (BiblioHelper.getBiblioInterro(session).getGestionMembre()
								.utilisateurEstAdministrateur(Integer.parseInt(userId)))
							session.setAttribute("admin", true);
						session.setAttribute("etat", new Integer(BiblioConstantes.CONNECTE));

						System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
						dispatcher.forward(request, response);
					} else {
						throw new IFT287Exception("Les informations de connexion sont erronées.");
					}
				} catch (Exception e) {
					List<String> listeMessageErreur = new LinkedList<String>();
					listeMessageErreur.add(e.getMessage());
					request.setAttribute("listeMessageErreur", listeMessageErreur);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
					dispatcher.forward(request, response);
					// pour d�boggage seulement : afficher tout le contenu de l'exception
					e.printStackTrace();
				}
			} else if (request.getParameter("inscrire") != null) {
				System.out.println("Servlet Accueil : POST - Inscrire");
				try {
					// lecture des param�tres du formulaire de creerCompte.jsp
					String userId = request.getParameter("userId");
					String motDePasse = request.getParameter("motDePasse");
					String prenom = request.getParameter("prenom");
					String nom = request.getParameter("nom");

					request.setAttribute("userId", userId);
					request.setAttribute("motDePasse", motDePasse);
					request.setAttribute("prenom", prenom);
					request.setAttribute("nom", nom);

					if (userId == null || userId.equals(""))
						throw new IFT287Exception("Vous devez entrer un nom d'utilisateur!");
					if (motDePasse == null || motDePasse.equals(""))
						throw new IFT287Exception("Vous devez entrer un mot de passe!");
					if (prenom == null || prenom.equals(""))
						throw new IFT287Exception("Vous devez entrer un prenom!");
					if (nom == null || nom.equals(""))
						throw new IFT287Exception("Vous devez entrer un nom!");

					String accesS = request.getParameter("acces");
					int acces = 1;
					if (accesS != null)
						acces = BiblioHelper.ConvertirInt(accesS, "Le niveau d'acc�s");

					GestionnaireJardinCollectif biblioUpdate = BiblioHelper.getBiblioUpdate(session);
					synchronized (biblioUpdate) {
						biblioUpdate.getGestionMembre().InscrireMembre(prenom, nom, motDePasse,
								Integer.parseInt(userId), acces);
					}

					// S'il y a d�j� un userID dans la session, c'est parce
					// qu'on est admin et qu'on inscrit un nouveau membre
					if (session.getAttribute("userID") == null) {
						session.setAttribute("userID", userId);
						if (acces == 0)
							session.setAttribute("admin", acces == 0);
						session.setAttribute("etat", new Integer(BiblioConstantes.CONNECTE));

						System.out.println("Servlet Accueil : POST dispatch vers accueil.jsp");
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
						dispatcher.forward(request, response);
					} else {
						// Vers gestionMembre?
						RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
						dispatcher.forward(request, response);
					}
				} catch (Exception e) {
					List<String> listeMessageErreur = new LinkedList<String>();
					listeMessageErreur.add(e.getMessage());
					request.setAttribute("listeMessageErreur", listeMessageErreur);
					RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/creerCompte.jsp");
					dispatcher.forward(request, response);
					// pour d�boggage seulement : afficher tout le contenu de l'exception
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			List<String> listeMessageErreur = new LinkedList<String>();
			listeMessageErreur.add(e.getMessage());
			request.setAttribute("listeMessageErreur", listeMessageErreur);
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/login.jsp");
			dispatcher.forward(request, response);
			// pour d�boggage seulement : afficher tout le contenu de l'exception
			e.printStackTrace();
		}
	}

	// Dans les formulaires, on utilise la m�thode POST
	// donc, si le servlet est appel� avec la m�thode GET
	// c'est que l'adresse a �t� demand� par l'utilisateur.
	// On proc�de si la connexion est actives seulement, sinon
	// on retourne au login
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Servlet Accueil : GET");
		if (BiblioHelper.peutProceder(getServletContext(), request, response)) {
			System.out.println("Servlet Accueil : GET dispatch vers accueil.jsp");
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
			dispatcher.forward(request, response);
		}
	}

} // class
