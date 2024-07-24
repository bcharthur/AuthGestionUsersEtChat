package dev.bcharthur.loginetchat.service;

import dev.bcharthur.loginetchat.model.Utilisateur;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UtilisateurService {
    List<Utilisateur> consulterUtilisateurs();

    Utilisateur consulterUtilisateurParId(int no_utilisateur);

//    Utilisateur consulterUtilisateurParPseudo(String pseudo);

    void creerUtilisateur(Utilisateur utilisateur);

    void supprimerUtilisateurParId(int idUtilisateur);

    Utilisateur consulterUtilisateurParPseudo(String pseudo);

    void modifierUtilisateur(Utilisateur utilisateur);

}
