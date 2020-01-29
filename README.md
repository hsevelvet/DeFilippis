<!--
 ___ _            _ _    _ _    __
/ __(_)_ __  _ __| (_)__(_) |_ /_/
\__ \ | '  \| '_ \ | / _| |  _/ -_)
|___/_|_|_|_| .__/_|_\__|_|\__\___|
            |_| 
-->
![](https://docs.simplicite.io//logos/logo250.png)
* * *

`DeFilippis` module definition
==============================



`DF_Affaire` business object definition
---------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_client_region`                                           | char(32)                                 |          | yes       |          | -                                                                                |
| `df_affaire_numero`                                          | char(36)                                 | yes*     | yes       |          | -                                                                                |
| `df_affaire_date_debut`                                      | date                                     |          | yes       |          | -                                                                                |
| `DF_Chantier_DF_Client_id` link to **`DF_Client`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_id`_               | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_nom`_              | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_telephone`_        | _phone(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_ville`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_pays`_             | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_cedex`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_adresse_3`_        | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_adresse_2`_        | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_code_postal`_      | _int(8)_                                 |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_description`_      | _text(100)_                              |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_id_secteur_activite`_ | _enum(7) using `DF_CLIENT_ID_SECTEUR_ACTIVITE` list_ |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_adresse`_          | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.df_client_region`_           | _char(32)_                               |          |           |          | -                                                                                |
| `DF_Chantier_DF_Plan_Livraison_id` link to **`DF_Plan_Livraison`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Plan_Livraison_id.df_plan_lvr_id`_     | _int(11)_                                |          |           |          | -                                                                                |
| `df_affaire_libelle`                                         | char(36)                                 | yes      | yes       |          | -                                                                                |
| `df_chantier_nature_travaux`                                 | char(36)                                 |          | yes       |          | -                                                                                |
| `df_affaire_coefficient_initial`                             | float(4, 2)                              | yes      | yes       |          | -                                                                                |
| `df_affaire_montant_initial`                                 | float(9, 0)                              | yes      | yes       |          | -                                                                                |
| `df_affaire_statut`                                          | enum(7) using `DF_STATUT_AFFAIRE` list   |          | yes       |          | -                                                                                |
| `df_affaire_lieu`                                            | char(70)                                 |          | yes       |          | -                                                                                |
| `DF_Affaire_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Affaire_DF_utilisateur_interne_id.usr_login`_      | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| _Ref. `DF_Affaire_DF_utilisateur_interne_id.df_utilisateur_interne_nc`_ | _char(70)_                               |          |           |          | -                                                                                |

### Lists

* `DF_CLIENT_ID_SECTEUR_ACTIVITE`
    - `AR` Architecte
    - `MDO` Maitrise d'oeuvre
    - `MO` Maitrise Ouvrage
    - `ATPG` Agence TP Groupe
    - `T` Travaux
    - `ATP` Agence TP
    - `V` VRD
    - `N` Négociant
    - `EA` Entreprise Autre
    - `A` Aucun
    - `P` Particulier
* `DF_STATUT_AFFAIRE`
    - `I` Initialisée
    - `C` Chantier

### Custom actions

No custom action

`DF_Client` business object definition
--------------------------------------

Business Object for customer.
Objet metier pour client.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_client_id`                                               | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_client_nom`                                              | char(36)                                 | yes      | yes       |          | -                                                                                |
| `df_client_email`                                            | email(100)                               | yes      | yes       |          | -                                                                                |
| `df_client_telephone`                                        | phone(100)                               | yes      | yes       |          | -                                                                                |
| `df_client_adresse`                                          | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_client_ville`                                            | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_client_code_postal`                                      | int(8)                                   | yes      | yes       |          | -                                                                                |
| `df_client_description`                                      | text(100)                                |          | yes       |          | -                                                                                |
| `df_client_adresse_2`                                        | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_id_secteur_activite`                              | enum(7) using `DF_CLIENT_ID_SECTEUR_ACTIVITE` list |          | yes       |          | -                                                                                |
| `df_client_region`                                           | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_indice`                                           | int(10)                                  |          | yes       |          | -                                                                                |
| `df_client_site_web`                                         | char(255)                                |          | yes       |          | -                                                                                |
| `df_client_adresse_3`                                        | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_cedex`                                            | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_pays`                                             | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_indice`                                           | int(10)                                  |          | yes       |          | -                                                                                |
| `df_client_indice`                                           | int(10)                                  |          | yes       |          | -                                                                                |

### Lists

* `DF_CLIENT_ID_SECTEUR_ACTIVITE`
    - `AR` Architecte
    - `MDO` Maitrise d'oeuvre
    - `MO` Maitrise Ouvrage
    - `ATPG` Agence TP Groupe
    - `T` Travaux
    - `ATP` Agence TP
    - `V` VRD
    - `N` Négociant
    - `EA` Entreprise Autre
    - `A` Aucun
    - `P` Particulier

### Custom actions

No custom action

`DF_Commande` business object definition
----------------------------------------

Business object for order.
objet metier pour commande.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_commande_id`                                             | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `df_commande_id_livraison`                                   | int(11)                                  |          | yes       |          | -                                                                                |
| `df_commande_numero`                                         | char(32)                                 |          | yes       |          | -                                                                                |
| `DF_Commande_DF_Client_id` link to **`DF_Client`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Client_id.df_client_id`_               | _int(11)_                                |          |           |          | -                                                                                |
| `DF_Commande_DF_Contact_id` link to **`DF_Contact`**         | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Contact_id.df_contact_id`_             | _int(11)_                                |          |           |          | -                                                                                |
| `df_commande_detail`                                         | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_date`                                           | datetime                                 | yes      | yes       |          | -                                                                                |
| `df_commande_intitule`                                       | char(70)                                 |          | yes       |          | -                                                                                |
| `DF_Commande_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_utilisateur_interne_id.df_utilisateur_interne_nc`_ | _char(70)_                               |          |           |          | -                                                                                |
| `df_commande_pj1`                                            | document                                 |          | yes       |          | -                                                                                |
| `df_commande_pj2`                                            | document                                 |          | yes       |          | -                                                                                |
| `df_commande_pj3`                                            | document                                 |          | yes       |          | -                                                                                |
| `df_commande_intitule_affaire`                               | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_lieu_affaire`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_poids_total`                                    | float(6, 2)                              |          | yes       |          | -                                                                                |
| `df_commande_nb_camions`                                     | float(6, 2)                              |          | yes       |          | -                                                                                |
| `df_commande_transporteur`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_date_premier_cam`                               | date                                     |          | yes       |          | -                                                                                |
| `df_commande_adresse_livraison`                              | char(150)                                |          | yes       |          | -                                                                                |
| `df_commande_contact_livraison`                              | char(70)                                 |          | yes       |          | -                                                                                |
| `df_commande_contact_transporteur`                           | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_cadence`                                        | float(6, 2)                              |          | yes       |          | -                                                                                |
| `df_commande_montant_ht`                                     | float(10, 2)                             |          | yes       |          | -                                                                                |
| `df_commande_pj_transport`                                   | document                                 |          | yes       |          | -                                                                                |
| `df_commande_pj_co_tr`                                       | document                                 |          | yes       |          | -                                                                                |
| `df_commande_nom_fournisseur`                                | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_contact_fournisseur`                            | char(100)                                |          | yes       |          | -                                                                                |
| `df_commande_com_fournissseur`                               | document                                 |          | yes       |          | -                                                                                |
| `df_commande_pj_fournisseur`                                 | document                                 |          | yes       |          | -                                                                                |
| `df_commande_statut`                                         | enum(7) using `DF_COMMANDE_STATUT` list  |          | yes       |          | -                                                                                |

### Lists

* `DF_COMMANDE_STATUT`
    - `IN` Initialisée
    - `EC` En cours
    - `LI` Livraison
    - `TE` Terminée
    - `AN` Annulée

### Custom actions

No custom action

`DF_Contact` business object definition
---------------------------------------

Business Object for contact.
Objet metier pour contact.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_contact_id`                                              | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_contact_civilite`                                        | enum(7) using `CIVILITE_CLIENT_CONTACT` list |          | yes       |          | -                                                                                |
| `df_contact_nom`                                             | char(36)                                 | yes      | yes       |          | -                                                                                |
| `df_contact_prenom`                                          | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_contact_id_emploi`                                       | enum(7) using `DF_CONTACT_ID_EMPLOI` list |          | yes       |          | -                                                                                |
| `df_contact_telephone`                                       | phone(100)                               |          | yes       |          | -                                                                                |
| `df_contact_portable`                                        | phone(100)                               |          | yes       |          | -                                                                                |
| `df_contact_email`                                           | email(100)                               |          | yes       |          | -                                                                                |
| `df_contact_commentaire`                                     | text(100)                                |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Client_id` link to **`DF_Client`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Client_id.df_client_id`_                | _int(11)_                                |          |           |          | -                                                                                |
| `df_contact_statut`                                          | enum(7) using `DF_CONTACT_STATUT` list   | yes      | yes       |          | -                                                                                |
| `df_contact_type`                                            | enum(7) using `DF_CONTACT_TYPE` list     | yes      | yes       |          | -                                                                                |
| `df_contact_sous_type`                                       | enum(7) using `DF_CONTACT_SOUS_TYPE` list |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Client_id` link to **`DF_Client`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Client_id.df_client_nom`_               | _char(36)_                               |          |           |          | -                                                                                |
| `DF_Contact_DF_Transport_id` link to **`DF_Transport`**      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Transport_id.df_transport_nom`_         | _char(36)_                               |          |           |          | -                                                                                |

### Lists

* `CIVILITE_CLIENT_CONTACT`
    - `M` M
    - `F` F
* `DF_CONTACT_ID_EMPLOI`
    - `AS` Assistant
    - `CT` Conducteur de Travaux
    - `CE` Chargé d'études
    - `D` Directeur
    - `C` Commercial
    - `P` PDG
* `DF_CONTACT_STATUT`
    - `O` Ouvert
    - `F` Fermé
    - `ET` En Traitement
* `DF_CONTACT_TYPE`
    - `INF` Information
    - `DEM` Demande
    - `REC` Réclamation
    - `AUT` Autre
* `DF_CONTACT_SOUS_TYPE`
    - `ETATCMD` Etat de commande
    - `INFPRD` Information Produit
    - `ETATLVR` Etat de livraison

### Custom actions

No custom action

`DF_Devis` business object definition
-------------------------------------

Business Object for quotes.
Objet metier pour devis.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| _Ref. `DF_Devis_DF_Chantier_id.df_affaire_statut`_           | _enum(7) using `DF_STATUT_AFFAIRE` list_ |          |           |          | -                                                                                |
| `df_devis_titre`                                             | char(100)                                | yes      | yes       |          | -                                                                                |
| `df_devis_titre_projet`                                      | char(100)                                | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_indice`_              | _int(10)_                                |          |           |          | -                                                                                |
| `df_devis_lieu_projet`                                       | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_devis_date_emission`                                     | datetime                                 | yes      | yes       |          | -                                                                                |
| `df_devis_statut`                                            | enum(7) using `STATUT_DEVIS` list        | yes      | yes       |          | -                                                                                |
| `DF_Devis_DF_Chantier_id` link to **`DF_Affaire`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_affaire_numero`_           | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_affaire_date_debut`_       | _date_                                   |          |           |          | -                                                                                |
| `df_devis_prix_total`                                        | float(10, 2)                             |          | yes       |          | -                                                                                |
| `DF_Devis_DF_Client_id` link to **`DF_Client`**              | id                                       | *        | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_telephone`_           | _phone(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_email`_               | _email(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_id`_                  | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_nom`_                 | _char(36)_                               |          |           |          | -                                                                                |
| `df_devis_commentaire`                                       | text(100)                                |          | yes       |          | -                                                                                |
| `df_devi_prix_total_ht`                                      | bigdec(10, 2)                            |          | yes       |          | -                                                                                |
| `df_devis_packaging_transport`                               | enum(7) using `DF_DEVIS_PACKAGING_TRANSPORT` list |          | yes       |          | -                                                                                |
| `df_devis_incoterm_prix`                                     | enum(7) using `DF_DEVIS_INCOTERM_PRIX` list |          | yes       |          | -                                                                                |
| `df_devis_poids_total`                                       | float(9, 2)                              |          |           |          | -                                                                                |
| `df_devis_nombre_camions`                                    | float(9, 2)                              |          |           |          | -                                                                                |
| `df_devis_fiche_technique`                                   | document                                 |          | yes       |          | -                                                                                |
| `df_devis_delais_previsionnel`                               | char(32)                                 |          | yes       |          | -                                                                                |
| `df_devis_cadence_de_livraison`                              | char(100)                                |          | yes       |          | -                                                                                |
| `df_devis_ordre_facturation`                                 | char(100)                                |          |           |          | -                                                                                |
| `df_devis_validite_offre`                                    | date                                     |          | yes       |          | -                                                                                |
| `df_devis_accompte`                                          | enum(7) using `DF_DEVIS_ACCOMPTE` list   |          | yes       |          | -                                                                                |
| `df_devis_contenance`                                        | enum(7) using `DF_DEVIS_CONTENANCE` list |          | yes       |          | -                                                                                |
| `DF_Devis_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_utilisateur_interne_id.df_utilisateur_interne_nc`_ | _char(70)_                               |          |           |          | -                                                                                |
| `DF_Devis_DF_Contact_id` link to **`DF_Contact`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_id`_                | _int(11)_                                |          |           |          | -                                                                                |
| `df_devis_suiveur_affaire`                                   | char(70)                                 |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_nom`_               | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_prenom`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_portable`_          | _phone(100)_                             |          |           |          | -                                                                                |
| `df_devis_numero`                                            | char(30)                                 | yes*     | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_email`_             | _email(100)_                             |          |           |          | -                                                                                |
| `df_devis_coef_global`                                       | float(4, 2)                              |          |           |          | -                                                                                |
| `df_devis_pj1`                                               | document                                 |          | yes       |          | -                                                                                |
| `df_devis_delai_paiement`                                    | enum(7) using `DF_DEVIS_DELAI_PAIEMENT` list |          | yes       |          | -                                                                                |
| `df_devis_pj2`                                               | document                                 |          | yes       |          | -                                                                                |
| `df_devis_pj3`                                               | document                                 |          | yes       |          | -                                                                                |
| `df_devis_pj4`                                               | document                                 |          | yes       |          | -                                                                                |

### Lists

* `DF_STATUT_AFFAIRE`
    - `I` Initialisée
    - `C` Chantier
* `STATUT_DEVIS`
    - `PE` Perdu
    - `VR` Versionné
    - `ES` Estimation
    - `ET` Eude
    - `NE` Négociation
    - `CH` Chantier
* `DF_DEVIS_PACKAGING_TRANSPORT`
    - `EI` Emballage Inclus
    - `HE` Hors coût d'emballage
* `DF_DEVIS_INCOTERM_PRIX`
    - `FCD` Franco chantier déchargé
    - `FCN` Franco chantier non déchargé
    - `EXW` Ex Work
    - `FOB` FOB
* `DF_DEVIS_ACCOMPTE`
    - `000` Pas d'accompte
    - `030` 30% à la commande
    - `050` 50% à la commande
    - `100` 100% à la commande
* `DF_DEVIS_CONTENANCE`
    - `CC` Camion complet 24 tonnes
    - `PC` Palette complète
    - `GT` Groupage de X tonnes mininmum
* `DF_DEVIS_DELAI_PAIEMENT`
    - `45J` 45 jours fin du mois LME
    - `30I` 30 jours le 15 intragroup
    - `1CO` 100% à la commande

### Custom actions

* `Generation-ARC`: 

`DF_DevisHistoric` business object definition
---------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `row_idx`                                                    | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `row_ref_id` link to **`DF_Devis`**                          | id                                       | yes*     |           |          | -                                                                                |
| `created_by_hist`                                            | char(100)                                | yes*     |           |          | -                                                                                |
| `created_dt_hist`                                            | datetime                                 | yes*     |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_affaire_statut`_           | _enum(7) using `DF_STATUT_AFFAIRE` list_ |          |           |          | -                                                                                |
| `df_devis_titre`                                             | char(100)                                | yes      | yes       |          | -                                                                                |
| `df_devis_titre_projet`                                      | char(100)                                | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_indice`_              | _int(10)_                                |          |           |          | -                                                                                |
| `df_devis_lieu_projet`                                       | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_devis_date_emission`                                     | datetime                                 | yes      | yes       |          | -                                                                                |
| `df_devis_statut`                                            | enum(7) using `STATUT_DEVIS` list        | yes      | yes       |          | -                                                                                |
| `DF_Devis_DF_Chantier_id` link to **`DF_Affaire`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_affaire_numero`_           | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_affaire_date_debut`_       | _date_                                   |          |           |          | -                                                                                |
| `df_devis_prix_total`                                        | float(10, 2)                             |          | yes       |          | -                                                                                |
| `DF_Devis_DF_Client_id` link to **`DF_Client`**              | id                                       | *        | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_telephone`_           | _phone(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_email`_               | _email(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_id`_                  | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_nom`_                 | _char(36)_                               |          |           |          | -                                                                                |
| `df_devis_commentaire`                                       | text(100)                                |          | yes       |          | -                                                                                |
| `df_devi_prix_total_ht`                                      | bigdec(10, 2)                            |          | yes       |          | -                                                                                |
| `df_devis_packaging_transport`                               | enum(7) using `DF_DEVIS_PACKAGING_TRANSPORT` list |          | yes       |          | -                                                                                |
| `df_devis_incoterm_prix`                                     | enum(7) using `DF_DEVIS_INCOTERM_PRIX` list |          | yes       |          | -                                                                                |
| `df_devis_poids_total`                                       | float(9, 2)                              |          |           |          | -                                                                                |
| `df_devis_nombre_camions`                                    | float(9, 2)                              |          |           |          | -                                                                                |
| `df_devis_fiche_technique`                                   | document                                 |          | yes       |          | -                                                                                |
| `df_devis_delais_previsionnel`                               | char(32)                                 |          | yes       |          | -                                                                                |
| `df_devis_cadence_de_livraison`                              | char(100)                                |          | yes       |          | -                                                                                |
| `df_devis_ordre_facturation`                                 | char(100)                                |          |           |          | -                                                                                |
| `df_devis_validite_offre`                                    | date                                     |          | yes       |          | -                                                                                |
| `df_devis_accompte`                                          | enum(7) using `DF_DEVIS_ACCOMPTE` list   |          | yes       |          | -                                                                                |
| `df_devis_contenance`                                        | enum(7) using `DF_DEVIS_CONTENANCE` list |          | yes       |          | -                                                                                |
| `DF_Devis_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_utilisateur_interne_id.df_utilisateur_interne_nc`_ | _char(70)_                               |          |           |          | -                                                                                |
| `DF_Devis_DF_Contact_id` link to **`DF_Contact`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_id`_                | _int(11)_                                |          |           |          | -                                                                                |
| `df_devis_suiveur_affaire`                                   | char(70)                                 |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_nom`_               | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_prenom`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.df_contact_portable`_          | _phone(100)_                             |          |           |          | -                                                                                |
| `df_devis_numero`                                            | char(30)                                 | yes*     | yes       |          | -                                                                                |
| `df_devis_coef_global`                                       | float(4, 2)                              |          |           |          | -                                                                                |

### Lists

* `DF_STATUT_AFFAIRE`
    - `I` Initialisée
    - `C` Chantier
* `STATUT_DEVIS`
    - `PE` Perdu
    - `VR` Versionné
    - `ES` Estimation
    - `ET` Eude
    - `NE` Négociation
    - `CH` Chantier
* `DF_DEVIS_PACKAGING_TRANSPORT`
    - `EI` Emballage Inclus
    - `HE` Hors coût d'emballage
* `DF_DEVIS_INCOTERM_PRIX`
    - `FCD` Franco chantier déchargé
    - `FCN` Franco chantier non déchargé
    - `EXW` Ex Work
    - `FOB` FOB
* `DF_DEVIS_ACCOMPTE`
    - `000` Pas d'accompte
    - `030` 30% à la commande
    - `050` 50% à la commande
    - `100` 100% à la commande
* `DF_DEVIS_CONTENANCE`
    - `CC` Camion complet 24 tonnes
    - `PC` Palette complète
    - `GT` Groupage de X tonnes mininmum

### Custom actions

No custom action

`DF_Fournisseurs` business object definition
--------------------------------------------

Business Object for supplier.
Objet métier pour fournisseur.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_fournisseur_id`                                          | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_fournisseur_nom`                                         | char(36)                                 | yes      | yes       |          | -                                                                                |
| `df_fournisseur_telephone`                                   | phone(100)                               | yes      | yes       |          | -                                                                                |
| `df_fournisseur_email`                                       | email(100)                               | yes      | yes       |          | -                                                                                |
| `df_fournisseur_description`                                 | text(100)                                |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`DF_ligne_commande` business object definition
----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_ligne_commande_id`                                       | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_ligne_commande_qte`                                      | int(11)                                  | yes      | yes       |          | -                                                                                |
| `df_ligne_commande_prix_total_ht`                            | float(100, 2)                            |          | yes       |          | -                                                                                |
| `DF_ligne_commande_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_nom`_ | _char(15)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_long`_ | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_larg`_ | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_haut`_ | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_finition`_ | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_unite`_ | _char(4)_                                |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_prix`_ | _float(10, 2)_                           |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_appellation_co`_ | _char(70)_                               |          |           |          | -                                                                                |
| `DF_ligne_commande_DF_Commande_id` link to **`DF_Commande`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_id`_     | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_numero`_ | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_ref_chantier`_ | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_date`_   | _datetime_                               |          |           |          | -                                                                                |
| `df_ligne_commande_prix_exw_u`                               | float(10, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_commande_poids_unitaire`                           | float(10, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_commande_ref_prod`                                 | char(100)                                |          | yes       |          | -                                                                                |
| `df_ligne_commande_type_produit`                             | char(100)                                |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`DF_Ligne_Devis` business object definition
-------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_ligne_devis_id`                                          | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_ligne_devis_quantite`                                    | int(11)                                  | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_designation`_ | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_finition`_ | _char(20)_                               |          |           |          | -                                                                                |
| `DF_Ligne_Devis_DF_Devis_id` link to **`DF_Devis`**          | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_masse_volumique`_ | _bigdec(8, 2)_                           |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.df_devis_titre`_           | _char(100)_                              |          |           |          | -                                                                                |
| `df_ligne_devis_dim_joints`                                  | float(4, 2)                              | yes      | yes       |          | -                                                                                |
| `df_ligne_devis_poids_total`                                 | float(9, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_transport_reference`                    | float(9, 2)                              | yes      | yes       |          | -                                                                                |
| `df_ligne_devis_nombre_camions`                              | float(9, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_masse_unitaire_ac_joint`                     | float(8, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_masse_unitaire_ss_joint`                     | float(8, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_debourse_sec`                           | float(10, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_transport_unitaire`                     | float(10, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_devis_total_achat_reference_ht`                    | float(11, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_devis_total_achat_ht`                              | float(11, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_devis_coef`                                        | float(6, 2)                              | yes      | yes       |          | -                                                                                |
| `df_ligne_devis_prix_vente_calcule`                          | float(6, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_vente_impose`                           | float(6, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_ref`                                         | int(5)                                   |          | yes       |          | -                                                                                |
| `df_ligne_devis_designation`                                 | text(100)                                | yes      | yes       |          | -                                                                                |
| `df_ligne_devis_nb_elt_ss_joint`                             | float(5, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_nb_elt_ac_joint`                             | float(5, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_exw_u`                                  | float(9, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_exw_unite`                              | float(10, 2)                             |          | yes       |          | -                                                                                |
| `df_ligne_devis_prix_exw_t`                                  | float(9, 2)                              |          | yes       |          | -                                                                                |
| `df_ligne_devis_t_prod`                                      | char(10)                                 |          | yes       |          | -                                                                                |
| `DF_Ligne_Devis_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_id`_    | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_nom`_   | _char(15)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_long`_  | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_larg`_  | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_haut`_  | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_unite`_ | _char(4)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_prix`_  | _float(10, 2)_                           |          |           |          | -                                                                                |

### Custom actions

No custom action

`DF_Livraison` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_livraison_id`                                            | int(100)                                 | yes*     | yes       |          | -                                                                                |
| `df_livraison_statut`                                        | enum(7) using `DF_LIVRAISON_STATUT` list | yes      | yes       |          | -                                                                                |
| `df_livraison_id_ligne_commande`                             | int(11)                                  |          | yes       |          | -                                                                                |
| `df_livraison_nom_transporteur`                              | char(100)                                |          | yes       |          | -                                                                                |
| `DF_Livraison_DF_Plan_Livraison_id` link to **`DF_Plan_Livraison`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Plan_Livraison_id.df_plan_lvr_id`_    | _int(11)_                                |          |           |          | -                                                                                |
| `df_livraison_trellocardid`                                  | char(30)                                 |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Livraison_id`                                 | id                                       |          | yes       |          | -                                                                                |
| `df_livraison_statut`                                        | enum(7) using `DF_LIVRAISON_STATUT` list | yes      | yes       |          | -                                                                                |
| `df_livraison_adresse`                                       | char(200)                                |          | yes       |          | -                                                                                |
| `df_livraison_id`                                            | int(100)                                 | yes*     | yes       |          | -                                                                                |
| `DF_Livraison_DF_Chantier_id` link to **`DF_Affaire`**       | id                                       |          | yes       |          | -                                                                                |
| `df_livraison_quantite_chargee`                              | float(11, 0)                             |          | yes       |          | -                                                                                |
| `df_livraison_contact_transporteur`                          | char(100)                                |          | yes       |          | -                                                                                |
| `df_livraison_num_bl_client`                                 | char(11)                                 |          | yes       |          | -                                                                                |
| `df_livraison_adresse_de_livraison_confirmee`                | char(300)                                |          | yes       |          | -                                                                                |
| `df_livraison_date_livraison_estimee`                        | date                                     |          | yes       |          | -                                                                                |
| `df_livraison_contact_dechargement_privilegie`               | char(100)                                |          | yes       |          | -                                                                                |
| `df_livraison_num_bl_fournisseur`                            | char(11)                                 |          | yes       |          | -                                                                                |
| `df_livraison_adresse_enlevement`                            | char(300)                                |          | yes       |          | -                                                                                |
| `df_livraison_num_zeepo_transporteur`                        | char(100)                                |          | yes       |          | -                                                                                |
| `df_livraison_contact_en_cas_de_probleme`                    | char(100)                                |          | yes       |          | -                                                                                |
| `DF_Livraison_DF_Plan_Livraison_id` link to **`DF_Plan_Livraison`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Plan_Livraison_id.df_plan_lvr_id`_    | _int(11)_                                |          |           |          | -                                                                                |
| `df_livraison_trellocardid`                                  | char(30)                                 |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Livraison_id`                                 | id                                       |          | yes       |          | -                                                                                |
| `df_livraison_adresse`                                       | char(200)                                |          | yes       |          | -                                                                                |
| `DF_Livraison_DF_Chantier_id` link to **`DF_Affaire`**       | id                                       |          | yes       |          | -                                                                                |

### Lists

* `DF_LIVRAISON_STATUT`
    - `1` Non Plannifiée
    - `2` Planifiée - non chargée
    - `3` Chargé - En usine
    - `4` En cours d'achememinement
    - `5` Panne
    - `6` Livraison effecutée

### Custom actions

* `Livraison-CreateTicketTrello`: 

`DF_Plan_Livraison` business object definition
----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_plan_lvr_id`                                             | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_plan_livraison_date_chargement`                          | date                                     |          | yes       |          | -                                                                                |
| `df_plan_livraison_date_livraison`                           | date                                     |          | yes       |          | -                                                                                |

### Custom actions

* `PlanLivraison-CreateTicketTrello`: 

`DF_Prix_Transport` business object definition
----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `DF_Prix_Transport_DF_Transport_id` link to **`DF_Transport`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Prix_Transport_DF_Transport_id.df_transport_id`_   | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Prix_Transport_DF_Transport_id.df_transport_nom`_  | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Prix_Transport_DF_Transport_id.DF_Transport_DF_Client_id`_ | _id_                                     |          |           |          | -                                                                                |
| `df_prix_transport_prix`                                     | float(9, 2)                              | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_region`_          | _char(32)_                               |          |           |          | -                                                                                |
| `df_prix_transport_origine`                                  | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_prix_transport_dpt_livraison`                            | char(20)                                 |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`DF_Produit_Finis` business object definition
---------------------------------------------

Business Object for Product.
Objet metier pour produit.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_produit_id`                                              | int(11)                                  | *        | yes       |          | -                                                                                |
| `df_produit_nom`                                             | char(15)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_designation`                                     | char(20)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_long`                                            | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `df_produit_larg`                                            | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `df_produit_haut`                                            | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `df_produit_finition`                                        | char(20)                                 |          | yes       |          | -                                                                                |
| `df_produit_commentaire`                                     | char(30)                                 |          | yes       |          | -                                                                                |
| `df_produit_unite`                                           | char(4)                                  | yes      | yes       |          | -                                                                                |
| `df_produit_source`                                          | char(10)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_prix`                                            | float(10, 2)                             | yes      | yes       |          | -                                                                                |
| `df_produit_couleur`                                         | enum(7) using `DF_PRODUIT_COULEUR` list  |          | yes       |          | -                                                                                |
| `DF_Produit_Finis_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Produit_Finis_DF_Fournisseurs_id.df_fournisseur_id`_ | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Produit_Finis_DF_Fournisseurs_id.df_fournisseur_nom`_ | _char(36)_                               |          |           |          | -                                                                                |
| `df_produit_appellation_co`                                  | char(70)                                 |          | yes       |          | -                                                                                |
| `df_produit_masse_volumique`                                 | bigdec(8, 2)                             | yes      | yes       |          | -                                                                                |
| `df_produit_type`                                            | enum(7) using `DF_PRODUIT_TYPE` list     |          | yes       |          | -                                                                                |
| `df_produit_autres_finitions`                                | char(30)                                 |          | yes       |          | -                                                                                |

### Lists

* `DF_PRODUIT_COULEUR`
    - `A` code A
    - `B` code B
    - `C` code C
* `DF_PRODUIT_TYPE`
    - `FFT` FFT
    - `Pierre` Pierre

### Custom actions

No custom action

`DF_test` business object definition
------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

### Custom actions

No custom action

`DF_Transport` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_transport_id`                                            | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_transport_nom`                                           | char(36)                                 | yes      | yes       |          | -                                                                                |
| `DF_Transport_DF_Chantier_id` link to **`DF_Affaire`**       | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Transport_DF_Chantier_id.df_affaire_numero`_       | _char(36)_                               |          |           |          | -                                                                                |
| `DF_Transport_DF_Livraison_id` link to **`DF_Livraison`**    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Transport_DF_Livraison_id.df_livraison_id`_        | _int(100)_                               |          |           |          | -                                                                                |
| `df_transport_pays_origine`                                  | char(70)                                 |          | yes       |          | -                                                                                |
| `df_transport_telephone`                                     | phone(100)                               |          | yes       |          | -                                                                                |
| `df_transport_email`                                         | email(100)                               |          | yes       |          | -                                                                                |
| `df_transport_adresse`                                       | char(100)                                |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`DF_utilisateur_interne` business object definition
---------------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_utilisateur_interne_nc`                                  | char(70)                                 | yes*     | yes       |          | -                                                                                |
| `df_utilisateur_interne_trig`                                | char(5)                                  |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`DF_Process_01` business process definition
-------------------------------------------



### Activities

* `Begin`: 
* `End`: 
* `SelectionClient`: 
* `SelectionProduit`: 
* `CreationLigneDevis`: 
* `SelectionDevis`: 

`DFAccueil` external object definition
--------------------------------------




`Test` external object definition
---------------------------------




`WebhookLivraisonTrello` external object definition
---------------------------------------------------




