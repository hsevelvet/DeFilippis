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
| `defiAfrNumero`                                              | char(36)                                 | yes      | yes       |          | -                                                                                |
| `defiClientRegion`                                           | char(32)                                 |          | yes       |          | -                                                                                |
| `defiAfrStatut`                                              | enum(7) using `DF_STATUT_AFFAIRE` list   |          | yes       |          | -                                                                                |
| `defiAfrLibelleChantier`                                     | char(36)                                 | yes      | yes       |          | -                                                                                |
| `defiAfrLieuAffaire`                                         | char(70)                                 |          | yes       |          | -                                                                                |
| `defiAfrDateDebut`                                           | date                                     |          | yes       |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientTelephone`_        | _phone(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientNom`_              | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Affaire_DF_Contact_id.defiContactNom`_             | _char(36)_                               |          |           |          | -                                                                                |
| `DF_Chantier_DF_Client_id` link to **`DF_Client`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientId`_               | _char(11)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientVille`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientPays`_             | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientCedex`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientAdresse3`_         | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientAdresse2`_         | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientCodePostal`_       | _int(8)_                                 |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientDescription`_      | _text(100)_                              |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientIdSecteurActivite`_ | _enum(7) using `DF_CLIENT_ID_SECTEUR_ACTIVITE` list_ |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientAdresse`_          | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Chantier_DF_Client_id.defiClientRegion`_           | _char(32)_                               |          |           |          | -                                                                                |
| `defiAfrCoefficientVenteInitial`                             | float(4, 2)                              | yes      | yes       |          | -                                                                                |
| `defiAfrMontantInitialCommandeHT`                            | float(9, 0)                              | yes      | yes       |          | -                                                                                |
| `DF_Affaire_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Affaire_DF_utilisateur_interne_id.usr_login`_      | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| `DF_Affaire_DF_Contact_id` link to **`DF_Contact`**          | id                                       |          | yes       |          | -                                                                                |
| `defiAffaireId`                                              | char(11)                                 | yes*     | yes       |          | -                                                                                |

### Lists

* `DF_STATUT_AFFAIRE`
    - `I` Initialisée
    - `C` Chantier
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

`DF_Client` business object definition
--------------------------------------

Business Object for customer.
Objet metier pour client.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiClientId`                                               | char(11)                                 | yes*     | yes       |          | -                                                                                |
| `defiClientNom`                                              | char(36)                                 | yes      | yes       |          | -                                                                                |
| `defiClientEmail`                                            | email(100)                               |          | yes       |          | -                                                                                |
| `defiClientTelephone`                                        | phone(100)                               | yes      | yes       |          | -                                                                                |
| `defiClientAdresse`                                          | char(32)                                 | yes      | yes       |          | -                                                                                |
| `defiClientVille`                                            | char(32)                                 | yes      | yes       |          | -                                                                                |
| `defiClientCodePostal`                                       | int(8)                                   | yes      | yes       |          | -                                                                                |
| `defiClientDescription`                                      | text(100)                                |          | yes       |          | -                                                                                |
| `defiClientAdresse2`                                         | char(32)                                 |          | yes       |          | -                                                                                |
| `defiClientIdSecteurActivite`                                | enum(7) using `DF_CLIENT_ID_SECTEUR_ACTIVITE` list |          | yes       |          | -                                                                                |
| `defiClientRegion`                                           | char(32)                                 |          | yes       |          | -                                                                                |
| `defiClientSiteWeb`                                          | char(255)                                |          | yes       |          | -                                                                                |
| `defiClientAdresse3`                                         | char(32)                                 |          | yes       |          | -                                                                                |
| `defiClientCedex`                                            | char(32)                                 |          | yes       |          | -                                                                                |
| `defiClientPays`                                             | char(32)                                 |          | yes       |          | -                                                                                |
| `defiClientTauxTransformation`                               | bigdec(4, 2)                             |          |           |          | -                                                                                |
| `defiClientSommeCommandes`                                   | int(4)                                   |          |           |          | -                                                                                |
| `defiClientIdKheops`                                         | char(12)                                 |          | yes       |          | -                                                                                |

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

`DF_Commande` business object definition
----------------------------------------

Business object for order.
objet metier pour commande.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| _Ref. `DF_Commande_DF_Affaire_id.defiAfrLibelleChantier`_    | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Commande_DF_Affaire_id.defiAfrLieuAffaire`_        | _char(70)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Commande_DF_Affaire_id.defiAfrNumero`_             | _char(36)_                               |          |           |          | -                                                                                |
| `defiCommandeId`                                             | char(50)                                 | yes*     | yes       |          | -                                                                                |
| `defiCommandeNumero`                                         | char(32)                                 | yes      | yes       |          | -                                                                                |
| `defiCommandeIdLivraison`                                    | int(11)                                  |          | yes       |          | -                                                                                |
| `defiCommandeIntituleAffaire`                                | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeLieuAffaire`                                    | char(100)                                |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Contact_id.defiContactNom`_            | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Commande_DF_utilisateur_interne_id.defiUsrNC`_     | _char(70)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Commande_DF_Contact_id.defiContactPrenom`_         | _char(32)_                               |          |           |          | -                                                                                |
| `defiCommandeIntituleCommande`                               | char(70)                                 |          | yes       |          | -                                                                                |
| `defiCommandeStatut`                                         | enum(7) using `DF_COMMANDE_STATUT` list  |          | yes       |          | -                                                                                |
| `DF_Commande_DF_Client_id` link to **`DF_Client`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Client_id.defiClientId`_               | _char(11)_                               |          |           |          | -                                                                                |
| `defiCommandeDetail`                                         | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeDate`                                           | date                                     | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Client_id.defiClientNom`_              | _char(36)_                               |          |           |          | -                                                                                |
| `defiCommandePieceJointe1`                                   | document                                 |          | yes       |          | -                                                                                |
| `defiCommandePieceJointe2`                                   | document                                 |          | yes       |          | -                                                                                |
| `defiCommandePoidsTotal`                                     | float(6, 2)                              |          | yes       |          | -                                                                                |
| `defiCommandeNombreCamions`                                  | float(6, 2)                              |          | yes       |          | -                                                                                |
| `defiCommandeSuiveurAffaire`                                 | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeTransporteur`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeDatePremierCamion`                              | date                                     |          | yes       |          | -                                                                                |
| `defiCommandeAdresseLivraison`                               | char(150)                                |          | yes       |          | -                                                                                |
| `defiCommandeContactLivraison`                               | char(70)                                 |          | yes       |          | -                                                                                |
| `defiCommandeContactTransporteur`                            | char(100)                                |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_utilisateur_interne_id.defiUsrTrigramme`_ | _char(5)_                                |          |           |          | -                                                                                |
| `defiCommandeCadenceLivraison`                               | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeMontantHT`                                      | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiCommandePieceJointeTransport`                           | document                                 |          | yes       |          | -                                                                                |
| `defiCommandePJCommandeTransport`                            | document                                 |          | yes       |          | -                                                                                |
| `defiCommandeContactFournisseur`                             | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandePJCommandeFournisseur`                          | document                                 |          | yes       |          | -                                                                                |
| `defiCommandePJFournisseur`                                  | document                                 |          | yes       |          | -                                                                                |
| `DF_Commande_DF_Affaire_id` link to **`DF_Affaire`**         | id                                       |          | yes       |          | -                                                                                |
| `defiCommandeTrelloId`                                       | char(30)                                 |          | yes       |          | -                                                                                |
| `DF_Commande_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Fournisseurs_id.defiFournId`_          | _char(11)_                               |          |           |          | -                                                                                |
| `DF_Commande_DF_Contact_id` link to **`DF_Contact`**         | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Contact_id.defiContactId`_             | _char(11)_                               |          |           |          | -                                                                                |
| `DF_Commande_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_utilisateur_interne_id.usr_login`_     | _regexp(100)_                            |          |           | yes      | _Login_                                                                          |
| `defiCommandeRedacteur`                                      | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeMontantLivraisons`                              | float(10, 2)                             |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Contact_id.defiContactEmail`_          | _email(100)_                             |          |           |          | -                                                                                |
| `defiCommandePackagingTransport`                             | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeIncotermPrix`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeAccompte`                                       | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeContenance`                                     | char(100)                                |          | yes       |          | -                                                                                |
| `defiCommandeAvcmt`                                          | float(11, 2)                             |          | yes       |          | -                                                                                |

### Lists

* `DF_COMMANDE_STATUT`
    - `IN` Initialisée
    - `EC` En cours
    - `LI` Livraison
    - `TE` Terminée
    - `AN` Annulée

### Custom actions

* `CommandeTrello`: 
* `Historiser-ARC`: 

`DF_Contact` business object definition
---------------------------------------

Business Object for contact.
Objet metier pour contact.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiContactId`                                              | char(11)                                 | yes*     | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Transport_id.defiTrspNom`_              | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Contact_DF_Fournisseurs_id.defiFournNom`_          | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Contact_DF_Client_id.defiClientNom`_               | _char(36)_                               |          |           |          | -                                                                                |
| `defiContactCivilite`                                        | enum(7) using `CIVILITE_CLIENT_CONTACT` list |          | yes       |          | -                                                                                |
| `defiContactNom`                                             | char(36)                                 | yes      | yes       |          | -                                                                                |
| `defiContactPrenom`                                          | char(32)                                 | yes      | yes       |          | -                                                                                |
| `defiContactIdEmploi`                                        | enum(7) using `DF_CONTACT_ID_EMPLOI` list |          | yes       |          | -                                                                                |
| `defiContactStatut`                                          | enum(7) using `DF_CONTACT_STATUT` list   | yes      | yes       |          | -                                                                                |
| `defiContactTelephone`                                       | phone(100)                               |          | yes       |          | -                                                                                |
| `defiContactPortable`                                        | phone(100)                               | yes      | yes       |          | -                                                                                |
| `defiContactEmail`                                           | email(100)                               | yes      | yes       |          | -                                                                                |
| `defiContactCommentaire`                                     | text(100)                                |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Client_id` link to **`DF_Client`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Client_id.defiClientId`_                | _char(11)_                               |          |           |          | -                                                                                |
| `DF_Contact_DF_Client_id` link to **`DF_Client`**            | id                                       |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Transport_id` link to **`DF_Transport`**      | id                                       |          | yes       |          | -                                                                                |
| `defiContactTypeContact`                                     | enum(7) using `DEFICONTACTTYPECONTACT` list | yes      | yes       |          | -                                                                                |
| `DF_Contact_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Fournisseurs_id.defiFournId`_           | _char(11)_                               |          |           |          | -                                                                                |
| `defiContactNomClient`                                       | char(100)                                |          | yes       |          | -                                                                                |

### Lists

* `CIVILITE_CLIENT_CONTACT`
    - `M` Mr
    - `Mr` Mr
    - `F` Mme
    - `Mme` Mme
* `DF_CONTACT_ID_EMPLOI`
    - `D` Directeur
    - `CA` Chef d'agence
    - `RC` Responsable Commercial
    - `C` Commercial
    - `A` Acheteur
    - `CS` Chef de secteur
    - `CT` Conducteur de travaux
    - `CC` Chef de chantier
    - `RE` Responsable d'étude
    - `CE` Chargé d'étude
    - `CP` Chargé de projet
    - `P` Particulier
    - `AT` Autre
* `DF_CONTACT_STATUT`
    - `O` Ouvert
    - `F` Fermé
    - `ET` En Traitement
* `DEFICONTACTTYPECONTACT`
    - `30` Fournisseur
    - `31` Client
    - `32` Transporteur

`DF_Devis` business object definition
-------------------------------------

Business Object for quotes.
Objet metier pour devis.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| _Ref. `DF_Devis_DF_Chantier_id.defiAfrStatut`_               | _enum(7) using `DF_STATUT_AFFAIRE` list_ |          |           |          | -                                                                                |
| `defiDevisCreatedDT`                                         | datetime                                 |          | yes       |          | -                                                                                |
| `defiDevisId`                                                | char(70)                                 | *        | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_utilisateur_interne_id.defiUsrTrigramme`_ | _char(5)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.defiAfrLibelleChantier`_      | _char(36)_                               |          |           |          | -                                                                                |
| `defiDevisTitre`                                             | char(100)                                | yes      |           |          | -                                                                                |
| `defiDevisStatut`                                            | enum(7) using `STATUT_DEVIS` list        | yes      | yes       |          | -                                                                                |
| `defiDevisTitreProjet`                                       | char(100)                                | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_utilisateur_interne_id.defiUsrNC`_        | _char(70)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.defiContactCivilite`_          | _enum(7) using `CIVILITE_CLIENT_CONTACT` list_ |          |           |          | -                                                                                |
| `defiDevisLieuProjet`                                        | char(70)                                 | yes      | yes       |          | -                                                                                |
| `defiDevisDateEmission`                                      | date                                     | yes      | yes       |          | -                                                                                |
| `DF_Devis_DF_Chantier_id` link to **`DF_Affaire`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.defiAfrNumero`_               | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.defiAfrDateDebut`_            | _date_                                   |          |           |          | -                                                                                |
| `defiDevisCoefficientGlobal`                                 | float(4, 2)                              |          |           |          | -                                                                                |
| `defiDevisPrixTotal`                                         | float(10, 2)                             |          | yes       |          | -                                                                                |
| `DF_Devis_DF_Client_id` link to **`DF_Client`**              | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.defiClientTelephone`_           | _phone(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.defiClientEmail`_               | _email(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.defiClientId`_                  | _char(11)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.defiClientNom`_                 | _char(36)_                               |          |           |          | -                                                                                |
| `defiDevisCommentaire`                                       | text(400)                                |          | yes       |          | -                                                                                |
| `defiDevisPrixTotalHT`                                       | bigdec(10, 2)                            |          |           |          | -                                                                                |
| `defiDevisPackagingTransport`                                | enum(7) using `DF_DEVIS_PACKAGING_TRANSPORT` list |          | yes       |          | -                                                                                |
| `defiDevisIncotermPrix`                                      | enum(7) using `DF_DEVIS_INCOTERM_PRIX` list |          | yes       |          | -                                                                                |
| `defiDevisPoidsTotal`                                        | float(9, 2)                              |          |           |          | -                                                                                |
| `defiDevisNombreCamions`                                     | float(9, 2)                              |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.defiAfrLieuAffaire`_          | _char(70)_                               |          |           |          | -                                                                                |
| `defiDevisFicheTechnique`                                    | document                                 |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_utilisateur_interne_id.defiUsrEmail`_     | _email(100)_                             |          |           |          | -                                                                                |
| `defiDevisDelaisPrevisionnel`                                | char(100)                                |          | yes       |          | -                                                                                |
| `defiDevisCadenceLivraison`                                  | char(100)                                |          | yes       |          | -                                                                                |
| `defiDevisOrdreFacturation`                                  | char(100)                                |          |           |          | -                                                                                |
| `defiDevisDateValiditeOffre`                                 | date                                     |          | yes       |          | -                                                                                |
| `defiDevisAccompte`                                          | enum(7) using `DF_DEVIS_ACCOMPTE` list   |          | yes       |          | -                                                                                |
| `defiDevisContenance`                                        | enum(7) using `DF_DEVIS_CONTENANCE` list |          | yes       |          | -                                                                                |
| `DF_Devis_DF_utilisateur_interne_id` link to **`DF_utilisateur_interne`** | id                                       |          | yes       |          | -                                                                                |
| `DF_Devis_DF_Contact_id` link to **`DF_Contact`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.defiContactId`_                | _char(11)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.defiContactNom`_               | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.defiContactPrenom`_            | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.defiContactPortable`_          | _phone(100)_                             |          |           |          | -                                                                                |
| `defiDevisNumero`                                            | char(30)                                 | yes      |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Contact_id.defiContactEmail`_             | _email(100)_                             |          |           |          | -                                                                                |
| `defiDevisPieceJointe1`                                      | document                                 |          | yes       |          | -                                                                                |
| `defiDevisDelaiPaiement`                                     | enum(7) using `DF_DEVIS_DELAI_PAIEMENT` list |          | yes       |          | -                                                                                |
| `defiDevisPieceJointe2`                                      | document                                 |          | yes       |          | -                                                                                |
| `defiDevisPieceJointe3`                                      | document                                 |          | yes       |          | -                                                                                |
| `defiDevisPieceJointe4`                                      | document                                 |          | yes       |          | -                                                                                |
| `defiDevisIndice`                                            | char(3)                                  |          |           |          | -                                                                                |
| `defiDevisCompteurDate`                                      | int(100)                                 |          |           |          | -                                                                                |
| `defiDevisRedacteur`                                         | char(70)                                 |          |           |          | -                                                                                |
| `defiDevisEmailPDF`                                          | document                                 |          | yes       |          | -                                                                                |
| `defiDevisTotalAchatTransport`                               | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiDevisTotalACFournHT`                                    | float(10, 2)                             |          |           |          | -                                                                                |
| `defiDevisTotalACHT`                                         | float(10, 2)                             |          |           |          | -                                                                                |

### Lists

* `DF_STATUT_AFFAIRE`
    - `I` Initialisée
    - `C` Chantier
* `STATUT_DEVIS`
    - `ES` Estimation
    - `ET` Eude
    - `NE` Négociation
    - `CH` Chantier
    - `PE` Perdu
    - `VR` Versionné
* `CIVILITE_CLIENT_CONTACT`
    - `M` Mr
    - `Mr` Mr
    - `F` Mme
    - `Mme` Mme
* `DF_DEVIS_PACKAGING_TRANSPORT`
    - `EI` Emballage Inclus
    - `HE` Hors coût d'emballage
* `DF_DEVIS_INCOTERM_PRIX`
    - `EXW` EXW - Sortie d’usine
    - `FOB` FOB - Chargé sur Navire
    - `DAP` DAP - Rendu Chantier Non Déchargé
    - `DPU` DPU - Rendu Chantier déchargé
    - `FCD` FCD - Franco chantier déchargé
    - `FCN` FCN - Franco chantier non déchargé
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

* `Envoyer-mail`: 
* `Historiser`: 
* `Initialisation-Commande`: 
* `versionner-devis`: 

`DF_Fournisseurs` business object definition
--------------------------------------------

Business Object for supplier.
Objet métier pour fournisseur.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiFournNom`                                               | char(36)                                 | yes      | yes       |          | -                                                                                |
| `defiFournId`                                                | char(11)                                 | *        | yes       |          | -                                                                                |
| `defiFournTelephone`                                         | phone(100)                               | yes      | yes       |          | -                                                                                |
| `defiFournEmail`                                             | email(100)                               | yes      | yes       |          | -                                                                                |
| `defiFournDescription`                                       | text(10000)                              |          | yes       |          | -                                                                                |
| `defiFournIdKheops`                                          | char(12)                                 |          | yes       |          | -                                                                                |

`DF_Hist_Docs` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiHistDocsId`                                             | char(100)                                | yes*     | yes       |          | -                                                                                |
| `defiHstTitre`                                               | char(200)                                |          | yes       |          | -                                                                                |
| `defiHstDocsNumDoc`                                          | int(7)                                   |          | yes       |          | -                                                                                |
| `defiHstDocsDevis`                                           | document                                 |          | yes       |          | -                                                                                |
| `defiHstDocsDateEmission`                                    | date                                     |          | yes       |          | -                                                                                |
| _Ref. `DF_Hist_Docs_DF_Devis_id.defiDevisTitre`_             | _char(100)_                              |          |           |          | -                                                                                |
| `DF_Hist_Docs_DF_Devis_id` link to **`DF_Devis`**            | id                                       |          | yes       |          | -                                                                                |
| `DF_Hist_Docs_DF_Commande_id` link to **`DF_Commande`**      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Hist_Docs_DF_Commande_id.defiCommandeId`_          | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Hist_Docs_DF_Commande_id.defiCommandeNumero`_      | _char(32)_                               |          |           |          | -                                                                                |

`DF_ligne_commande` business object definition
----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiLigneCommandeReferenceProduit`                          | char(100)                                |          | yes       |          | -                                                                                |
| `defiLigneCommandeAppellationCommerciale`                    | char(100)                                |          | yes       |          | -                                                                                |
| `defiLigneCommandeNmFourn`                                   | char(100)                                |          | yes       |          | -                                                                                |
| `defiLigneCommandeId`                                        | char(20)                                 | yes*     | yes       |          | -                                                                                |
| `defiLigneCommandeQuantite`                                  | float(11, 1)                             | yes      | yes       |          | -                                                                                |
| `defiLigneCommandeQteLivr`                                   | float(11, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneCommandeUnite`                                     | enum(7) using `DEFIPRDUNITE` list        |          | yes       |          | -                                                                                |
| `defiLigneCommandeFinitionFacesVues`                         | char(100)                                |          | yes       |          | -                                                                                |
| `defiLigneCommandeLongueur`                                  | char(20)                                 |          | yes       |          | -                                                                                |
| `defiLigneCommandeLargeur`                                   | char(20)                                 |          | yes       |          | -                                                                                |
| `defiLigneCommandeEpaisseur`                                 | char(10)                                 |          | yes       |          | -                                                                                |
| `defiLigneCommandePoidsUnitaire`                             | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneCommandePoidsTotal`                                | float(10, 1)                             |          | yes       |          | -                                                                                |
| `defiLigneCommandePrixEXWUnitaire`                           | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneCommandePrixTotalEXW`                              | float(100, 2)                            |          | yes       |          | -                                                                                |
| `DF_ligne_commande_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| `DF_ligne_commande_DF_Commande_id` link to **`DF_Commande`** | id                                       | *        | yes       |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Fournisseurs_id.defiFournNom`_   | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.defiCommandeId`_     | _char(50)_                               |          |           |          | -                                                                                |
| `defiLigneCommandeTypeGeologique`                            | enum(7) using `DEFIPRDTYPEGEOLOGIQUE` list |          | yes       |          | -                                                                                |
| `defiLigneCommandeDesignation`                               | text(200)                                |          | yes       |          | -                                                                                |
| `DF_ligne_commande_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Fournisseurs_id.defiFournId`_    | _char(11)_                               |          |           |          | -                                                                                |
| `defiLigneCommandeCatPrix`                                   | enum(7) using `DEFIPRDCATEGORIEPRIX` list |          | yes       |          | -                                                                                |
| `defiLigneCommandelivre`                                     | float(11, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneCommandeTrelloId`                                  | char(300)                                |          | yes       |          | -                                                                                |

### Lists

* `DEFIPRDUNITE`
    - `M2` m²
    - `ML` ml
    - `T` T
    - `U` U
* `DEFIPRDTYPEGEOLOGIQUE`
    - `GRA` Granit
    - `CAL` Calcaire
    - `GRE` Grès
    - `POR` Porphyre
    - `BAS` Basalte
    - `LUS` Luzerne
    - `TEC` Terre cuite
    - `PRE` Produit de réemploi
* `DEFIPRDCATEGORIEPRIX`
    - `2` Pierre
    - `1` FFT

`DF_Ligne_Devis` business object definition
-------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiLigneDevisRow`                                          | char(10)                                 |          |           |          | -                                                                                |
| `defiLigneDevisNPrix`                                        | char(10)                                 | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdAppellationCommerciale`_ | _char(200)_                              |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.defiDevisId`_              | _char(70)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdCategoriePrix`_ | _enum(7) using `DEFIPRDCATEGORIEPRIX` list_ |          |           |          | -                                                                                |
| `defiLigneDevisPrixTransportReference`                       | float(9, 2)                              | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdAutresFinitions`_ | _char(70)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdTypeProduit`_ | _enum(7) using `DEFIPRDTYPEPRODUIT` list_ |          |           |          | -                                                                                |
| `defiLigneDevisId`                                           | char(11)                                 | yes*     | yes       |          | -                                                                                |
| `defiLigneDevisQuantite`                                     | float(11, 1)                             | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdFinitionFacesVues`_ | _char(70)_                               |          |           |          | -                                                                                |
| `DF_Ligne_Devis_DF_Devis_id` link to **`DF_Devis`**          | id                                       | yes*     | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdMasseVolumique`_ | _bigdec(8, 2)_                           |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.defiDevisTitre`_           | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdFournisseur`_ | _char(100)_                              |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdCouleur`_   | _multi(100) using `DF_PRODUIT_COULEUR` list_ |          |           |          | -                                                                                |
| `defiLigneDevisDimensionJoints`                              | float(4, 2)                              | yes      | yes       |          | -                                                                                |
| `defiLigneDevisPoidsTotal`                                   | float(9, 2)                              |          |           |          | -                                                                                |
| `defiLigneDevisPrixTransportReference`                       | float(9, 2)                              | yes      | yes       |          | -                                                                                |
| `defiLigneDevisNombreCamions`                                | float(9, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisMasseUnitaireAcJoints`                        | float(8, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisMasseUnitaireSsJoints`                        | float(8, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixUnitaireSec`                              | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixTransportUnitaire`                        | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisTotalEXWHT`                                   | float(11, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisTotalAchatHT`                                 | float(11, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisCoef`                                         | float(6, 2)                              | yes      | yes       |          | -                                                                                |
| `defiLigneDevisPrixVenteCalcule`                             | float(6, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisNPrix`                                        | char(10)                                 | yes      | yes       |          | -                                                                                |
| `defiLigneDevisDesignation`                                  | text(100)                                | yes      |           |          | -                                                                                |
| `defiLigneDevisNombreElementsSsJoints`                       | float(5, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisNombreElementsAcJoints`                       | float(5, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixExwUnite`                                 | float(9, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixExwUnitaire`                              | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixExwTonne`                                 | float(9, 2)                              |          | yes       |          | -                                                                                |
| `DF_Ligne_Devis_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdId`_        | _char(11)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdTypeGeologique`_ | _enum(7) using `DEFIPRDTYPEGEOLOGIQUE` list_ |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdLongueur`_  | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdLargeur`_   | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdEpaisseur`_ | _char(5)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdPrixUnitaireHT`_ | _float(10, 2)_                           |          |           |          | -                                                                                |
| `defiLigneDevisLongueur`                                     | char(20)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixVenteImpose`                              | bigdec(15, 2)                            |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixUnitaireHT`                               | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisRangs`                                        | int(100)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisLargeur`                                      | char(20)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisConversionUnite`                              | enum(7) using `DEFILIGNEDEVISCONVERSIONUNITE` list |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixUnitaireImpose`                           | float(6, 2)                              |          | yes       |          | -                                                                                |
| `defiLigneDevisEpaisseur`                                    | char(10)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisDesignationProduit`                           | char(30) using `DEFIPRDTYPEPRODUIT` list |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdFinitionFacesVues`_ | _char(70)_                               |          |           |          | -                                                                                |
| `defiLigneDevisMasseVolumique`                               | int(10)                                  |          | yes       |          | -                                                                                |
| `defiLigneDevisNomProduit`                                   | char(30)                                 |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.defiPrdUnite`_     | _enum(7) using `DEFIPRDUNITE` list_      |          |           |          | -                                                                                |
| `defiLigneDevisPrixVenteImpose`                              | bigdec(15, 2)                            |          | yes       |          | -                                                                                |
| `DF_Ligne_Devis_DF_Prix_Transport_id` link to **`DF_Prix_Transport`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Prix_Transport_id.defiPrTspId`_     | _char(11)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Prix_Transport_id.defiPrTrspPrix`_  | _float(9, 2)_                            |          |           |          | -                                                                                |
| `defiLigneDevisFournisseur`                                  | char(100)                                |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixTrsp`                                     | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiLigneDevisCatPrix`                                      | enum(7) using `DEFIPRDCATEGORIEPRIX` list | yes      | yes       |          | -                                                                                |
| `defiLigneDevisAppellationCommerciale`                       | char(200)                                |          | yes       |          | -                                                                                |
| `defiLigneDevisUnite`                                        | enum(7) using `DEFIPRDUNITE` list        |          | yes       |          | -                                                                                |
| `defiLigneDevisTypePrd`                                      | enum(7) using `DEFIPRDTYPEPRODUIT` list  |          | yes       |          | -                                                                                |
| `defiLigneDevisTypeGeo`                                      | enum(7) using `DEFIPRDTYPEGEOLOGIQUE` list |          | yes       |          | -                                                                                |
| `defiLigneDevisFinitionFV`                                   | char(70)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisAutresFinitions`                              | char(70)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisU`                                            | char(70)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisTypePierre`                                   | char(70)                                 |          | yes       |          | -                                                                                |
| `defiLigneDevisPoidsUnitaire`                                | float(10, 2)                             |          |           |          | -                                                                                |
| `defiLigneDevisCouleur`                                      | multi(100) using `DF_PRODUIT_COULEUR` list |          | yes       |          | -                                                                                |
| `defiLigneDevisPrixTransportTotal`                           | float(10, 2)                             |          | yes       |          | -                                                                                |

### Lists

* `DEFIPRDCATEGORIEPRIX`
    - `2` Pierre
    - `1` FFT
* `DEFIPRDTYPEPRODUIT`
    - `PAVE` Pavé
    - `DALLE` Dalle
    - `BORDURE` Bordure
    - `BANC` Banc
    - `BORNE` Borne
    - `CANIVEAU` Caniveau
    - `PIECE` Pièce Spéciale
* `DF_PRODUIT_COULEUR`
    - `GR` Gris
    - `BE` Beige
    - `BL` Bleu
    - `RG` Rouge
    - `NO` Noir
    - `RS` Rose
    - `VE` Vert
    - `OC` Ocre
    - `JA` Jaune
    - `MA` Marron
* `DEFIPRDTYPEGEOLOGIQUE`
    - `GRA` Granit
    - `CAL` Calcaire
    - `GRE` Grès
    - `POR` Porphyre
    - `BAS` Basalte
    - `LUS` Luzerne
    - `TEC` Terre cuite
    - `PRE` Produit de réemploi
* `DEFILIGNEDEVISCONVERSIONUNITE`
    - `61` m²
    - `62` ml
* `DEFIPRDUNITE`
    - `M2` m²
    - `ML` ml
    - `T` T
    - `U` U

`DF_Livraison` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiLivraisonId`                                            | char(100)                                | yes*     | yes       |          | -                                                                                |
| `defiLivraisonIntituleCamion`                                | char(100)                                |          | yes       |          | -                                                                                |
| `defiLivraisonNumBL`                                         | char(10)                                 |          | yes       |          | -                                                                                |
| `df_livraison_statut`                                        | enum(7) using `DF_LIVRAISON_STATUT` list | yes      | yes       |          | -                                                                                |
| `defiLivraisonIdCommande`                                    | char(100)                                | yes      | yes       |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Affaire_id.defiAfrLibelleChantier`_   | _char(36)_                               |          |           |          | -                                                                                |
| `df_livraison_trellocardid`                                  | char(30)                                 |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Livraison_id`                                 | id                                       |          | yes       |          | -                                                                                |
| `df_livraison_statut`                                        | enum(7) using `DF_LIVRAISON_STATUT` list | yes      | yes       |          | -                                                                                |
| `df_livraison_quantite_chargee`                              | float(11, 1)                             |          | yes       |          | -                                                                                |
| `df_livraison_date_livraison_estimee`                        | date                                     |          | yes       |          | -                                                                                |
| `defiLivraisonNbrPalettes`                                   | int(100)                                 |          | yes       |          | -                                                                                |
| `df_livraison_num_bl_fournisseur`                            | char(11)                                 |          | yes       |          | -                                                                                |
| `df_livraison_trellocardid`                                  | char(30)                                 |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Livraison_id`                                 | id                                       |          | yes       |          | -                                                                                |
| `DF_Livraison_DF_Affaire_id` link to **`DF_Affaire`**        | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Affaire_id.defiAffaireId`_            | _char(11)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Affaire_id.defiAfrNumero`_            | _char(36)_                               |          |           |          | -                                                                                |
| `DF_Livraison_DF_Commande_id` link to **`DF_Commande`**      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Commande_id.defiCommandeId`_          | _char(50)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Commande_id.defiCommandeNumero`_      | _char(32)_                               |          |           |          | -                                                                                |
| `DF_Livraison_DF_Transport_id` link to **`DF_Transport`**    | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Livraison_DF_Transport_id.defiTrspNom`_            | _char(36)_                               |          |           |          | -                                                                                |
| `defiLivraisonTotalHT`                                       | float(10, 2)                             |          | yes       |          | -                                                                                |

### Lists

* `DF_LIVRAISON_STATUT`
    - `1` Non Plannifiée
    - `2` Planifiée - non chargée
    - `3` Chargé - En usine
    - `4` En cours d'achememinement
    - `5` Panne
    - `6` Livraison effecutée

### Custom actions

* `Historiser-BL`: 
* `Historiser-ODF`: 
* `ODF-PRNT`: 

`DF_Prix_Transport` business object definition
----------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `DF_Prix_Transport_DF_Transport_id` link to **`DF_Transport`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Prix_Transport_DF_Transport_id.defiTrspNom`_       | _char(36)_                               |          |           |          | -                                                                                |
| `defiPrTrspPrix`                                             | float(9, 2)                              | yes      | yes       |          | -                                                                                |
| `defiPrTrspLieuEnlevement`                                   | char(100)                                | yes      | yes       |          | -                                                                                |
| `defiPrTrspDepartementLivraison`                             | char(20)                                 | yes      | yes       |          | -                                                                                |
| `defiPrTspId`                                                | char(11)                                 | yes*     | yes       |          | -                                                                                |

`DF_Produit_Finis` business object definition
---------------------------------------------

Business Object for Product.
Objet metier pour produit.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiPrdId`                                                  | char(11)                                 | yes*     | yes       |          | -                                                                                |
| `defiPrdCategoriePrix`                                       | enum(7) using `DEFIPRDCATEGORIEPRIX` list | yes      | yes       |          | -                                                                                |
| `DF_Produit_Finis_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Produit_Finis_DF_Fournisseurs_id.defiFournNom`_    | _char(36)_                               |          |           |          | -                                                                                |
| `defiPrdLongueur`                                            | char(20)                                 |          | yes       |          | -                                                                                |
| `defiPrdTypeGeologique`                                      | enum(7) using `DEFIPRDTYPEGEOLOGIQUE` list | yes      | yes       |          | -                                                                                |
| `defiPrdCouleur`                                             | multi(100) using `DF_PRODUIT_COULEUR` list |          | yes       |          | -                                                                                |
| `defiPrdAppellationCommerciale`                              | char(200)                                |          | yes       |          | -                                                                                |
| `defiPrdTypeProduit`                                         | enum(7) using `DEFIPRDTYPEPRODUIT` list  |          | yes       |          | -                                                                                |
| `defiPrdFinitionFacesVues`                                   | char(70)                                 |          | yes       |          | -                                                                                |
| `defiPrdAutresFinitions`                                     | char(70)                                 |          | yes       |          | -                                                                                |
| `defiPrdLargeur`                                             | char(20)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_commentaire`                                     | char(200)                                |          | yes       |          | -                                                                                |
| `defiPrdEpaisseur`                                           | char(5)                                  | yes      | yes       |          | -                                                                                |
| `defiPrdSource`                                              | enum(7) using `DEFIPRDSOURCE` list       | yes      | yes       |          | -                                                                                |
| `defiPrdUnite`                                               | enum(7) using `DEFIPRDUNITE` list        | yes      | yes       |          | -                                                                                |
| `defiPrdPrixUnitaireHT`                                      | float(10, 2)                             | yes      | yes       |          | -                                                                                |
| `defiPrdMasseVolumique`                                      | bigdec(8, 2)                             | yes      | yes       |          | -                                                                                |
| `defiPrdFournisseur`                                         | char(100)                                |          | yes       |          | -                                                                                |

### Lists

* `DEFIPRDCATEGORIEPRIX`
    - `2` Pierre
    - `1` FFT
* `DEFIPRDTYPEGEOLOGIQUE`
    - `GRA` Granit
    - `CAL` Calcaire
    - `GRE` Grès
    - `POR` Porphyre
    - `BAS` Basalte
    - `LUS` Luzerne
    - `TEC` Terre cuite
    - `PRE` Produit de réemploi
* `DF_PRODUIT_COULEUR`
    - `GR` Gris
    - `BE` Beige
    - `BL` Bleu
    - `RG` Rouge
    - `NO` Noir
    - `RS` Rose
    - `VE` Vert
    - `OC` Ocre
    - `JA` Jaune
    - `MA` Marron
* `DEFIPRDTYPEPRODUIT`
    - `PAVE` Pavé
    - `DALLE` Dalle
    - `BORDURE` Bordure
    - `BANC` Banc
    - `BORNE` Borne
    - `CANIVEAU` Caniveau
    - `PIECE` Pièce Spéciale
* `DEFIPRDSOURCE`
    - `CP` Contrat Produit Fini
    - `CC` Contrat Calculé
    - `D` Devis
    - `PK` Programme Kamen Adapté
* `DEFIPRDUNITE`
    - `M2` m²
    - `ML` ml
    - `T` T
    - `U` U

`DF_Quantite` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiQuantiteId`                                             | char(30)                                 | yes*     | yes       |          | -                                                                                |
| `defiQuantiteNumCommande`                                    | char(100)                                |          | yes       |          | -                                                                                |
| `defiQuantiteRefProduit`                                     | char(30)                                 |          | yes       |          | -                                                                                |
| `defiQuantitePoidsUnitaire`                                  | float(5, 2)                              |          | yes       |          | -                                                                                |
| `defiQuantiteQte`                                            | float(11, 2)                             |          | yes       |          | -                                                                                |
| _Ref. `DF_Quantite_DF_ligne_commande_id.defiLigneCommandeDesignation`_ | _text(200)_                              |          |           |          | -                                                                                |
| _Ref. `DF_Quantite_DF_ligne_commande_id.defiLigneCommandeTypeGeologique`_ | _enum(7) using `DEFIPRDTYPEGEOLOGIQUE` list_ |          |           |          | -                                                                                |
| _Ref. `DF_Quantite_DF_Livraison_id.defiLivraisonNumBL`_      | _char(10)_                               |          |           |          | -                                                                                |
| `defiQuantiteTonnage`                                        | float(7, 1)                              |          | yes       |          | -                                                                                |
| _Ref. `DF_Quantite_DF_ligne_commande_id.defiLigneCommandeUnite`_ | _enum(7) using `DEFIPRDUNITE` list_      |          |           |          | -                                                                                |
| `defiQuantiteTrigrammeSuiveur`                               | char(10)                                 |          | yes       |          | -                                                                                |
| `DF_Quantite_DF_Livraison_id` link to **`DF_Livraison`**     | id                                       | *        | yes       |          | -                                                                                |
| _Ref. `DF_Quantite_DF_Livraison_id.defiLivraisonId`_         | _char(100)_                              |          |           |          | -                                                                                |
| `DF_Quantite_DF_ligne_commande_id` link to **`DF_ligne_commande`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Quantite_DF_ligne_commande_id.defiLigneCommandeId`_ | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Quantite_DF_ligne_commande_id.defiLigneCommandePrixEXWUnitaire`_ | _float(10, 2)_                           |          |           |          | -                                                                                |
| `defiQuantiteMontant`                                        | float(10, 2)                             |          | yes       |          | -                                                                                |
| `defiQuantitePrixUnitaire`                                   | float(10, 2)                             |          | yes       |          | -                                                                                |
| `DF_Quantite_DF_Commande_id` link to **`DF_Commande`**       | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Quantite_DF_Commande_id.defiCommandeId`_           | _char(50)_                               |          |           |          | -                                                                                |

### Lists

* `DEFIPRDTYPEGEOLOGIQUE`
    - `GRA` Granit
    - `CAL` Calcaire
    - `GRE` Grès
    - `POR` Porphyre
    - `BAS` Basalte
    - `LUS` Luzerne
    - `TEC` Terre cuite
    - `PRE` Produit de réemploi
* `DEFIPRDUNITE`
    - `M2` m²
    - `ML` ml
    - `T` T
    - `U` U

### Custom actions

* `PlanLivraison-CreateTicketTrello`: 

`DF_test` business object definition
------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |

`DF_Transport` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiTrspId`                                                 | int(11)                                  |          | yes       |          | -                                                                                |
| `defiTrspNom`                                                | char(36)                                 | yes*     | yes       |          | -                                                                                |
| `DF_Transport_DF_Chantier_id` link to **`DF_Affaire`**       | id                                       |          | yes       |          | -                                                                                |
| `defiTrspPaysOrigine`                                        | char(70)                                 |          | yes       |          | -                                                                                |
| `defiTrsptelephone`                                          | phone(100)                               |          | yes       |          | -                                                                                |
| `defiTrspEmail`                                              | email(100)                               |          | yes       |          | -                                                                                |
| `defiTrspAdresse`                                            | char(100)                                |          | yes       |          | -                                                                                |
| `defiTrspTrigramme`                                          | char(5)                                  |          | yes       |          | -                                                                                |

`DF_utilisateur_interne` business object definition
---------------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `defiUsrNC`                                                  | char(70)                                 |          | yes       |          | -                                                                                |
| `defiUsrTrigramme`                                           | char(5)                                  |          | yes       |          | -                                                                                |
| `defiUsrEmail`                                               | email(100)                               |          | yes       |          | -                                                                                |

`DF_Process_01` business process definition
-------------------------------------------



### Activities

* `Begin`: 
* `End`: 
* `SelectionClient`: 
* `SelectionProduit`: 
* `CreationLigneDevis`: 
* `SelectionDevis`: 

`ODFModification` business process definition
---------------------------------------------



### Activities

* `SelectionCommande`: 
* `CreationLigneCommande`: 
* `SelectionLigneCommande`: 
* `SelectionLivraison`: 
* `CreationLivraison`: 
* `CreationQuantite`: 
* `SelectionMultipleLivraisons`: 
* `PrintODF`: 
* `Begin`: 
* `End`: 
* `EndMessage`: 

`DFAccueil` external object definition
--------------------------------------




`Test` external object definition
---------------------------------




`WebhookLivraisonTrello` external object definition
---------------------------------------------------




