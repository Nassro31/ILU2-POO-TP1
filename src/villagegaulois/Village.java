package villagegaulois;


import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	Marche marche;

	public Village(String nom, int nbVillageoisMaximum , int nbrEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nbrEtal);
	}
	
	private static class Marche{
		private Etal[] etals;
		
		private  Marche(int nbrEtal){
			this.etals = new Etal[nbrEtal];
			for (int i = 0; i < nbrEtal; i++) {
				etals[i] = new Etal();
			}
		}
		
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			this.etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		
		private int trouverEtalLibre(){
			int indiceRetourne = -1;
			for (int i = 0; i < etals.length; i++) {
				if(! etals[i].isEtalOccupe()) {
					indiceRetourne = i;
				}
			}
			return indiceRetourne;
		}
		
		private Etal[] trouverEtals(String produit){
		    int count = 0;
		    
		    for (int i = 0; i < etals.length; i++) {
		        if (etals[i].contientProduit(produit)) {
		            count++;
		        }
		    }


		    Etal[] etalsProduit = new Etal[count];
		    

		    int indexEtals = 0;
		    for (int j = 0; j < etals.length; j++) {
		        if (etals[j].contientProduit(produit)) {
		            etalsProduit[indexEtals] = etals[j];
		            indexEtals++; 
		        }
		    }
		    
		    return etalsProduit;
		}

	
		private Etal trouverVendeur(Gaulois gaulois) {
		    for (Etal etal : etals) {
		        if (etal.getVendeur() != null && etal.getVendeur().equals(gaulois)) {
		            return etal;
		        }
		    }
		    return null;
		}

		private String afficherMarche() {
			int count =0;
			for (int i = 0; i < etals.length; i++) {
				if(etals[i].isEtalOccupe()){
					etals[i].afficherEtal();
					}
				else {
					count++;
				}
				
			}
			return "il reste " + count + "�tals non utilis�s dans le march�.";
		}
		
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
	
	public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + produit);
		int pos = marche.trouverEtalLibre();
		marche.utiliserEtal(pos, vendeur, produit, nbProduit);
		chaine.append("Le vendeur " + vendeur.getNom() + "vend" + produit + "à l'étal" + pos);
		return chaine.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		Etal[] etalsProduits = marche.trouverEtals(produit);
		if(etalsProduits.length == 0 || etalsProduits.equals(null) ) {
			return "Il n'y a pas de vendeur qui propose des" + produit + "au marché." ;
		}
		if(etalsProduits.length == 1) {
			return "Seul le vendeur" + etalsProduits[0].getVendeur() + "propose des" + produit + "au marché." ;
		}
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des"+ produit +"sont :\n");
		for (int i = 0; i < etalsProduits.length; i++) {
			chaine.append("- "+etalsProduits[i].getVendeur());
		}
		return chaine.toString();
	}
	
	public Etal rechercherEtal(Gaulois vendeur) {
		return this.marche.trouverVendeur(vendeur);
	}
	
	public String partirVendeur(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur).libererEtal();
	}
}