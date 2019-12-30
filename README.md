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



`DF_Client` business object definition
--------------------------------------

Business Object for customer.
Objet metier pour client.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `idclient`                                                   | int(11)                                  | yes*     |           |          | -                                                                                |
| `nmclient`                                                   | char(20)                                 | yes      | yes       |          | -                                                                                |
| `mailclient`                                                 | email(100)                               | yes      | yes       |          | -                                                                                |
| `telephoneclient`                                            | phone(100)                               | yes      | yes       |          | -                                                                                |
| `adresseclient`                                              | char(30)                                 | yes      | yes       |          | -                                                                                |
| `villeclient`                                                | char(10)                                 | yes      | yes       |          | -                                                                                |
| `codepostclient`                                             | int(6)                                   | yes      | yes       |          | -                                                                                |
| `descriptionclient`                                          | text(100)                                |          | yes       |          | -                                                                                |
| `adresseclient2`                                             | char(30)                                 |          | yes       |          | -                                                                                |
| `id_sec_act_client`                                          | int(5)                                   |          | yes       |          | -                                                                                |
| `reg_client`                                                 | char(15)                                 |          | yes       |          | -                                                                                |
| `fax_client`                                                 | phone(100)                               |          | yes       |          | -                                                                                |
| `website_client`                                             | char(20)                                 |          | yes       |          | -                                                                                |

### Custom actions

No custom action

`DF_Commande` business object definition
----------------------------------------

Business object for order.
objet metier pour commande.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `id_commande`                                                | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `id_contact_commande`                                        | char(15)                                 | yes      | yes       |          | -                                                                                |
| `id_livraison_commande`                                      | int(5)                                   |          | yes       |          | -                                                                                |
| `num_cde`                                                    | char(10)                                 |          | yes       |          | -                                                                                |
| `redacteur_cmd`                                              | char(20)                                 |          | yes       |          | -                                                                                |
| `tx_tva_cmd`                                                 | float(100, 2)                            |          |           |          | -                                                                                |
| `prix_tonne_cmd`                                             | float(100, 2)                            |          |           |          | -                                                                                |
| `tx_remise_cmd`                                              | float(100, 2)                            |          |           |          | -                                                                                |
| `livraison_detail_cmd`                                       | char(100)                                |          |           |          | -                                                                                |
| `date_cmd`                                                   | datetime                                 | yes      |           |          | -                                                                                |
| `ref_chantier`                                               | char(15)                                 |          | yes       |          | -                                                                                |
| `delai_cmd`                                                  | char(10)                                 |          | yes       |          | -                                                                                |
| `fact_cmd`                                                   | char(10)                                 |          | yes       |          | -                                                                                |
| `paiement_cmd`                                               | char(10)                                 |          | yes       |          | -                                                                                |
| `validite_offre_cmd`                                         | int(5)                                   |          | yes       |          | -                                                                                |
| `idx_mon_cmd`                                                | char(10)                                 |          | yes       |          | -                                                                                |
| `DF_Commande_DF_Client_id` link to **`DF_Client`**           | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Client_id.idclient`_                   | _int(11)_                                |          |           |          | -                                                                                |
| `DF_Commande_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Commande_DF_Produit_Finis_id.idproduit`_           | _int(11)_                                |          |           |          | -                                                                                |

### Custom actions

No custom action

`DF_Contact` business object definition
---------------------------------------

Business Object for contact.
Objet metier pour contact.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `id_contact`                                                 | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `id_client_contact`                                          | id                                       | yes      | yes       |          | -                                                                                |
| `civilite_client_contact`                                    | enum(7) using `CIVILITE_CLIENT_CONTACT` list |          | yes       |          | -                                                                                |
| `nom_contact_c`                                              | char(20)                                 | yes      | yes       |          | -                                                                                |
| `prenom_contact_c`                                           | char(20)                                 | yes      | yes       |          | -                                                                                |
| `id_emploi`                                                  | int(100)                                 |          | yes       |          | -                                                                                |
| `tel_contact`                                                | phone(100)                               |          | yes       |          | -                                                                                |
| `portable_contact`                                           | phone(100)                               |          | yes       |          | -                                                                                |
| `fax_contact`                                                | phone(100)                               |          | yes       |          | -                                                                                |
| `mail_contact`                                               | email(100)                               |          | yes       |          | -                                                                                |
| `commentaire_contact`                                        | text(100)                                |          | yes       |          | -                                                                                |
| `__id` link to **`DF_Client`**                               | id                                       |          | yes       |          | -                                                                                |
| `DF_Contact_DF_Client_id` link to **`DF_Client`**            | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Client_id.idclient`_                    | _int(11)_                                |          |           |          | -                                                                                |
| `DF_Contact_DF_Commande_id` link to **`DF_Commande`**        | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Contact_DF_Commande_id.id_contact_commande`_       | _char(15)_                               |          |           |          | -                                                                                |

### Lists

* `CIVILITE_CLIENT_CONTACT`
    - `M` M
    - `F` F

### Custom actions

No custom action

`DF_Devis` business object definition
-------------------------------------

Business Object for quotes.
Objet metier pour devis.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `numerodevis`                                                | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `titredevis`                                                 | char(30)                                 | yes      | yes       |          | -                                                                                |
| `titreprojet`                                                | char(30)                                 | yes      | yes       |          | -                                                                                |
| `lieuprojet`                                                 | char(20)                                 | yes      | yes       |          | -                                                                                |
| `nomclientdevis`                                             | char(20)                                 | yes      | yes       |          | -                                                                                |
| `contactdevis`                                               | char(30)                                 | yes      | yes       |          | -                                                                                |
| `teldevis`                                                   | phone(100)                               | yes      | yes       |          | -                                                                                |
| `emaildevis`                                                 | email(100)                               | yes      | yes       |          | -                                                                                |
| `dateemisisondevis`                                          | datetime                                 | yes      | yes       |          | -                                                                                |
| `statut_devis`                                               | enum(7) using `STATUT_DEVIS` list        | yes      | yes       |          | -                                                                                |
| `prix_unitaire_devis`                                        | float(11, 2)                             |          |           |          | -                                                                                |
| `quantiteDevis`                                              | int(11)                                  | yes      | yes       |          | -                                                                                |
| `tvaDevis`                                                   | float(11, 2)                             |          |           |          | -                                                                                |
| `totalDevis`                                                 | int(11)                                  |          |           |          | -                                                                                |
| `commentaireDevis`                                           | text(100)                                |          | yes       |          | -                                                                                |
| `DF_Devis_DF_Client_id` link to **`DF_Client`**              | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.idclient`_                      | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Devis_DF_Client_id.nmclient`_                      | _char(20)_                               |          |           |          | -                                                                                |

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
| `idfournisseur`                                              | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `nomfournisseur`                                             | char(20)                                 | yes      | yes       |          | -                                                                                |
| `telfournisseur`                                             | phone(100)                               | yes      | yes       |          | -                                                                                |
| `emailfournisseur`                                           | email(100)                               | yes      | yes       |          | -                                                                                |
| `descriptionfournisseur`                                     | text(100)                                |          | yes       |          | -                                                                                |

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
| _Ref. `DF_Ligne_Devis_DF_Devis_id.numerodevis`_              | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.titredevis`_               | _char(30)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Devis_id.nomclientdevis`_           | _char(20)_                               |          |           |          | -                                                                                |
| `DF_Ligne_Devis_DF_Produit_Finis_id` link to **`DF_Produit_Finis`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.idproduit`_        | _int(11)_                                |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.nomproduit`_       | _char(15)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.longueur`_         | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.largeur`_          | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.designation`_      | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.heauteur`_         | _float(5, 2)_                            |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.finition`_         | _char(20)_                               |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.quantite_stock`_   | _int(20000)_                             |          |           |          | -                                                                                |
| _Ref. `DF_Ligne_Devis_DF_Produit_Finis_id.prd_price`_        | _float(100, 2)_                          |          |           |          | -                                                                                |

### Custom actions

No custom action

`DF_Produit_Finis` business object definition
---------------------------------------------

Business Object for Product.
Objet metier pour produit.

### Fields

| Name                                                         | Type                                     | Required | Updatable | Personal | Description                                                                      | 
| ------------------------------------------------------------ | ---------------------------------------- | -------- | --------- | -------- | -------------------------------------------------------------------------------- |
| `idproduit`                                                  | int(11)                                  | yes*     | yes       |          | -                                                                                |
| `nomproduit`                                                 | char(15)                                 | yes      | yes       |          | -                                                                                |
| `code_fournisseur`                                           | char(50)                                 | yes      | yes       |          | -                                                                                |
| `designation`                                                | char(20)                                 | yes      | yes       |          | -                                                                                |
| `longueur`                                                   | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `largeur`                                                    | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `heauteur`                                                   | float(5, 2)                              | yes      | yes       |          | -                                                                                |
| `finition`                                                   | char(20)                                 |          | yes       |          | -                                                                                |
| `commentaire`                                                | char(30)                                 |          | yes       |          | -                                                                                |
| `price`                                                      | bigdec(10, 2)                            | yes      | yes       |          | -                                                                                |
| `unite`                                                      | char(4)                                  | yes      | yes       |          | -                                                                                |
| `source`                                                     | char(10)                                 | yes      | yes       |          | -                                                                                |
| `prd_price`                                                  | float(100, 2)                            | yes      | yes       |          | -                                                                                |
| `quantite_stock`                                             | int(20000)                               | yes      | yes       |          | -                                                                                |
| `dispo_produit`                                              | boolean                                  |          | yes       |          | -                                                                                |
| `nom_fournisseur`                                            | char(100)                                | yes      | yes       |          | -                                                                                |
| `DF_Produit_Finis_DF_Fournisseurs_id` link to **`DF_Fournisseurs`** | id                                       |          | yes       |          | -                                                                                |
| _Ref. `DF_Produit_Finis_DF_Fournisseurs_id.idfournisseur`_   | _int(11)_                                |          |           |          | -                                                                                |

### Custom actions

No custom action

