package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;
	
	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbEtal);
	}
	
	
	
	
	private class Marche{
		private Etal[] etals;
		
		
		public Marche(int nbEtal){
			etals = new Etal[nbEtal];
			for(int i =0; i<nbEtal ; i++) {
				etals[i] = new Etal();
			}
		}
		
		void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		int trouverEtalLibre(){
			for(int i=0;i<etals.length;i++) {
				if (etals[i].isEtalOccupe()){
					return i; 
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit){
			Etal[] trouverEtals = new Etal[etals.length];
			for(int i=0, j=0 ; i<etals.length ; i++) {
				if (etals[i].contientProduit(produit)){
					trouverEtals[j]= etals[i];
					j++;
				}
			}
			return trouverEtals;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			Etal iVendeur;
			iVendeur = null;
			for (int i = 0; i < etals.length; i++) {
				Gaulois vendeur = etals[i].getVendeur();
				if (vendeur.equals(gaulois)) {
				iVendeur = etals[i];
				break;
				}
			}
			return (iVendeur);
		}
	
	
		public String afficherMarche() {
			int nbEtalOccupe = 0;
			StringBuilder chaine = new StringBuilder();
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
					chaine.append("\n");
					nbEtalOccupe++;
				}
			}
			int nbEtalLibre = etals.length - nbEtalOccupe;
			chaine.append("Il reste " + nbEtalLibre + " étals non utilisés sur le marché. \n");
			return chaine.toString();

		}
	
	
	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}