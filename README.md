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



`DF_Chantier` business object definition
----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_chantier_id`                                             | int(9)                                   | yes*     | yes       |          | -                                                                                |
| `df_chantier_reference`                                      | char(36)                                 | yes      | yes       |          | -                                                                                |
| `df_chantier_date_debut`                                     | date                                     |          | yes       |          | -                                                                                |
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

### Lists

* `DF_CLIENT_ID_SECTEUR_ACTIVITE`
    - `T` Travaux
    - `V` VRD
    - `N` Négociant
    - `P` Pierre
    - `A` Aucun

### Custom actions

No custom action

`DF_Client` business object definition
--------------------------------------

Business Object for customer.
Objet metier pour client.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_client_id`                                               | int(11)                                  | yes*     |           |          | -                                                                                |
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
| `df_client_fax`                                              | phone(100)                               |          | yes       |          | -                                                                                |
| `df_client_site_web`                                         | char(255)                                |          | yes       |          | -                                                                                |
| `df_client_adresse_3`                                        | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_cedex`                                            | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_pays`                                             | char(32)                                 |          | yes       |          | -                                                                                |
| `df_client_tx_remise`                                        | float(100, 2)                            | yes      | yes       |          | -                                                                                |

### Lists

* `DF_CLIENT_ID_SECTEUR_ACTIVITE`
    - `T` Travaux
    - `V` VRD
    - `N` Négociant
    - `P` Pierre
    - `A` Aucun

### Custom actions

No custom action

`DF_Commande` business object definition
----------------------------------------

Business object for order.
objet metier pour commande.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_commande_id`                                             | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_commande_id_livraison`                                   | int(5)                                   |          | yes       |          | -                                                                                |
| `df_commande_numero`                                         | char(32)                                 |          | yes       |          | -                                                                                |
| `df_commande_redacteur`                                      | char(70)                                 |          | yes       |          | -                                                                                |
| `df_commande_taux_tva`                                       | float(100, 2)                            |          |           |          | -                                                                                |
| `df_commande_prix_tonne`                                     | float(100, 2)                            |          |           |          | -                                                                                |
| `df_commande_taux_de_remise`                                 | float(100, 2)                            |          |           |          | -                                                                                |
| `df_commande_detail_livraison`                               | char(100)                                |          |           |          | -                                                                                |
| `df_commande_date`                                           | datetime                                 | yes      |           |          | -                                                                                |
| `df_commande_ref_chantier`                                   | char(32)                                 |          | yes       |          | -                                                                                |
| `df_commande_delai`                                          | char(10)                                 |          | yes       |          | -                                                                                |
| `df_commande_facture`                                        | char(10)                                 |          | yes       |          | -                                                                                |
| `df_commande_paiement`                                       | char(10)                                 |          | yes       |          | -                                                                                |
| `df_commande_validite_offre`                                 | int(5)                                   |          | yes       |          | -                                                                                |
| `df_commande_indexation_monetaire`                           | char(10)                                 |          | yes       |          | -                                                                                |
| `DF_Commande_DF_Client_id` link to **`DF_Client`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Client_id.df_client_id`_               | _int(11)_                                |          |           |          | -                                                                                |

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
| `df_contact_fax`                                             | phone(100)                               |          | yes       |          | -                                                                                |
| `df_contact_email`                                           | email(100)                               |          | yes       |          | -                                                                                |
| `df_contact_commentaire`                                     | text(100)                                |          | yes       |          | -                                                                                |
| `__id` link to **`DF_Client`**                               | id                                       |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Client_id` link to **`DF_Client`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Client_id.df_client_id`_                | _int(11)_                                |          |           |          | -                                                                                |
| `DF_Contact_DF_Commande_id` link to **`DF_Commande`**        | id                                       |          | yes       |          | -                                                                                |
| `df_contact_statut`                                          | enum(7) using `DF_CONTACT_STATUT` list   | yes      | yes       |          | -                                                                                |
| `df_contact_type`                                            | enum(7) using `DF_CONTACT_TYPE` list     | yes      | yes       |          | -                                                                                |
| `df_contact_sous_type`                                       | enum(7) using `DF_CONTACT_SOUS_TYPE` list |          | yes       |          | -                                                                                |

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
| `df_devis_numero`                                            | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_devis_titre`                                             | char(100)                                | yes      | yes       |          | -                                                                                |
| `df_devis_titre_projet`                                      | char(100)                                | yes      | yes       |          | -                                                                                |
| `df_devis_lieu_projet`                                       | char(32)                                 | yes      | yes       |          | -                                                                                |
| `df_devis_contact`                                           | char(70)                                 | yes      | yes       |          | -                                                                                |
| `df_devis_telephone`                                         | phone(100)                               | yes      | yes       |          | -                                                                                |
| `df_devis_email`                                             | email(100)                               | yes      | yes       |          | -                                                                                |
| `df_devis_date_emission`                                     | datetime                                 | yes      | yes       |          | -                                                                                |
| `df_devis_statut`                                            | enum(7) using `STATUT_DEVIS` list        | yes      | yes       |          | -                                                                                |
| `df_devis_tva`                                               | float(11, 2)                             |          |           |          | -                                                                                |
| `df_devis_prix_total`                                        | int(11)                                  |          |           |          | -                                                                                |
| `df_devis_commentaire`                                       | text(100)                                |          | yes       |          | -                                                                                |
| `DF_Devis_DF_Client_id` link to **`DF_Client`**              | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_id`_                  | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.df_client_nom`_                 | _char(36)_                               |          |           |          | -                                                                                |
| `DF_Devis_DF_Chantier_id` link to **`DF_Chantier`**          | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_chantier_id`_              | _int(9)_                                 |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_chantier_reference`_       | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Chantier_id.df_chantier_date_debut`_      | _date_                                   |          |           |          | -                                                                                |

### Lists

* `STATUT_DEVIS`
    - `E` En Cours
    - `V` Validé
    - `R` Refusé
    - `A` Abandon

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
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_id`_ | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_nom`_ | _char(15)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_long`_ | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_larg`_ | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_haut`_ | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_finition`_ | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_unite`_ | _char(4)_                                |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Produit_Finis_id.df_produit_prix`_ | _float(100, 2)_                          |          |           |          | -                                                                                |
| `DF_ligne_commande_DF_Commande_id` link to **`DF_Commande`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_id`_     | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_numero`_ | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_ref_chantier`_ | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_ligne_commande_DF_Commande_id.df_commande_date`_   | _datetime_                               |          |           |          | -                                                                                |

### Custom actions

No custom action

`DF_Ligne_Devis` business object definition
-------------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_ligne_devis_id`                                          | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_ligne_devis_quantite`                                    | int(11)                                  | yes      | yes       |          | -                                                                                |
| `df_ligne_devis_prix_total_ht`                               | float(100, 2)                            |          |           |          | -                                                                                |
| `DF_Ligne_Devis_DF_Devis_id` link to **`DF_Devis`**          | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.df_devis_numero`_          | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.df_devis_titre`_           | _char(100)_                              |          |           |          | -                                                                                |
| `DF_Ligne_Devis_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_id`_    | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_nom`_   | _char(15)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_long`_  | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_larg`_  | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_designation`_ | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_haut`_  | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_finition`_ | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.df_produit_prix`_  | _float(100, 2)_                          |          |           |          | -                                                                                |

### Custom actions

No custom action

`DF_Produit_Finis` business object definition
---------------------------------------------

Business Object for Product.
Objet metier pour produit.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_produit_id`                                              | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_produit_nom`                                             | char(15)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_designation`                                     | char(20)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_long`                                            | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `df_produit_larg`                                            | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `df_produit_haut`                                            | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `df_produit_finition`                                        | char(20)                                 |          | yes       |          | -                                                                                |
| `df_produit_commentaire`                                     | char(30)                                 |          | yes       |          | -                                                                                |
| `df_produit_unite`                                           | char(4)                                  | yes      | yes       |          | -                                                                                |
| `df_produit_source`                                          | char(10)                                 | yes      | yes       |          | -                                                                                |
| `df_produit_prix`                                            | float(100, 2)                            | yes      | yes       |          | -                                                                                |
| `DF_Produit_Finis_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Produit_Finis_DF_Fournisseurs_id.df_fournisseur_id`_ | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Produit_Finis_DF_Fournisseurs_id.df_fournisseur_nom`_ | _char(36)_                               |          |           |          | -                                                                                |

### Custom actions

No custom action

`DF_Transport` business object definition
-----------------------------------------



### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `df_transport_id`                                            | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `df_transport_nom`                                           | char(36)                                 | yes      | yes       |          | -                                                                                |
| `DF_Transport_DF_Client_id` link to **`DF_Client`**          | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_id`_              | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_nom`_             | _char(36)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_telephone`_       | _phone(100)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_adresse`_         | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_ville`_           | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_code_postal`_     | _int(8)_                                 |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_adresse_2`_       | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_region`_          | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_adresse_3`_       | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_cedex`_           | _char(32)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Client_id.df_client_pays`_            | _char(32)_                               |          |           |          | -                                                                                |
| `DF_Transport_DF_Chantier_id` link to **`DF_Chantier`**      | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Transport_DF_Chantier_id.df_chantier_id`_          | _int(9)_                                 |          |           |          | -                                                                                |
| _Ref. `DF_Transport_DF_Chantier_id.df_chantier_reference`_   | _char(36)_                               |          |           |          | -                                                                                |

### Custom actions

No custom action

