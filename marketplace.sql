-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : ven. 30 juin 2023 à 12:52
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `marketplace`
--

-- --------------------------------------------------------

--
-- Structure de la table `adresse`
--

DROP TABLE IF EXISTS `adresse`;
CREATE TABLE IF NOT EXISTS `adresse` (
  `IDADRESSE` int NOT NULL AUTO_INCREMENT,
  `LOCALISATION` text NOT NULL,
  PRIMARY KEY (`IDADRESSE`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `adresse`
--

INSERT INTO `adresse` (`IDADRESSE`, `LOCALISATION`) VALUES
(1, 'admaoua-chefferie massoc ancienne 3eme'),
(2, 'Buéa-face immeuble marché central'),
(3, 'douala-bonnaberi entrée zone industrielle'),
(4, 'yaoundé-marché vog mbi ');

-- --------------------------------------------------------

--
-- Structure de la table `adresseuser`
--

DROP TABLE IF EXISTS `adresseuser`;
CREATE TABLE IF NOT EXISTS `adresseuser` (
  `IDADRESSE` int NOT NULL,
  `IDUSER` int NOT NULL,
  KEY `FKghtaoi95n9sxhwd2kurms75xo` (`IDADRESSE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `adresseuser`
--

INSERT INTO `adresseuser` (`IDADRESSE`, `IDUSER`) VALUES
(4, 1),
(1, 1),
(4, 2),
(3, 2);

-- --------------------------------------------------------

--
-- Structure de la table `boncommande`
--

DROP TABLE IF EXISTS `boncommande`;
CREATE TABLE IF NOT EXISTS `boncommande` (
  `IDBONCOMMANDE` int NOT NULL AUTO_INCREMENT,
  `IDCOMMANDE` int NOT NULL,
  `IDCLIENT` int NOT NULL,
  `CODE` text NOT NULL,
  PRIMARY KEY (`IDBONCOMMANDE`),
  UNIQUE KEY `I_FK_BONCOMMANDE_COMMANDES` (`IDCOMMANDE`),
  KEY `I_FK_BONCOMMANDE_CLIENTS` (`IDCLIENT`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `boncommande`
--

INSERT INTO `boncommande` (`IDBONCOMMANDE`, `IDCOMMANDE`, `IDCLIENT`, `CODE`) VALUES
(5, 6, 1, '86U21CFKMM'),
(6, 7, 1, 'J6E7DR747O'),
(7, 8, 1, 'QA99WICEYX'),
(8, 9, 1, '4Q3SDNCS73'),
(9, 10, 1, 'AGIGVHAA6E'),
(10, 11, 1, '22NKW7A1CS');

-- --------------------------------------------------------

--
-- Structure de la table `boutiquefavorie`
--

DROP TABLE IF EXISTS `boutiquefavorie`;
CREATE TABLE IF NOT EXISTS `boutiquefavorie` (
  `IDBOUTIQUE` int NOT NULL,
  `IDFAVORIES` int NOT NULL,
  KEY `FKqedrn5xb4stqteomnlfvrckg6` (`IDBOUTIQUE`),
  KEY `FK748u52t6xoaqtagrvo6iwubxd` (`IDFAVORIES`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `boutiquefavorie`
--

INSERT INTO `boutiquefavorie` (`IDBOUTIQUE`, `IDFAVORIES`) VALUES
(6, 10);

-- --------------------------------------------------------

--
-- Structure de la table `boutiques`
--

DROP TABLE IF EXISTS `boutiques`;
CREATE TABLE IF NOT EXISTS `boutiques` (
  `IDBOUTIQUE` int NOT NULL AUTO_INCREMENT,
  `IDUTILISATEUR` int NOT NULL,
  `IDTYPEBOUTIQUE` int NOT NULL,
  `NOM` text NOT NULL,
  `EMPLACEMENT` text NOT NULL,
  `SLOGAN` text NOT NULL,
  `LOGO` text NOT NULL,
  `IMMATRICULATIONRCCM` char(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`IDBOUTIQUE`),
  UNIQUE KEY `I_FK_BOUTIQUES_UTILISATEURS` (`IDUTILISATEUR`),
  KEY `I_FK_BOUTIQUES_TYPEBOUTIQUE` (`IDTYPEBOUTIQUE`)
) ENGINE=MyISAM AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `boutiques`
--

INSERT INTO `boutiques` (`IDBOUTIQUE`, `IDUTILISATEUR`, `IDTYPEBOUTIQUE`, `NOM`, `EMPLACEMENT`, `SLOGAN`, `LOGO`, `IMMATRICULATIONRCCM`) VALUES
(6, 2, 1, 'ma boutique', 'douala mabeke avenue 2', 'tel père tel fils', 'Screenshot_20230430-185507.png', '1234567890');

-- --------------------------------------------------------

--
-- Structure de la table `categorieboutique`
--

DROP TABLE IF EXISTS `categorieboutique`;
CREATE TABLE IF NOT EXISTS `categorieboutique` (
  `IDCATEGORIE` int NOT NULL,
  `IDBOUTIQUE` int NOT NULL,
  KEY `FKb0s0yglnu8jar21s8jci4rfwy` (`IDBOUTIQUE`),
  KEY `FKhd5waoi8b3qrt52qlchcnsbt0` (`IDCATEGORIE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categories`
--

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `IDCATEGORIE` int NOT NULL AUTO_INCREMENT,
  `LIBELLE` varchar(125) NOT NULL,
  `QUANTITE` int NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `IMAGE` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`IDCATEGORIE`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `categories`
--

INSERT INTO `categories` (`IDCATEGORIE`, `LIBELLE`, `QUANTITE`, `DESCRIPTION`, `IMAGE`) VALUES
(1, 'vetements', 0, 'categorie des vetements', 'vetements.jpg\r\n\r\n'),
(2, 'chaussures', 0, 'cette session porte vos chaussure...utilisez la pour charger ce type de produit et augmenter votre visibilité grace a votre style et vos services', 'chaussures.jpg'),
(3, 'accessoires', 0, 'ici vous devez creer des articles de type accessoires tels que des montres ou des bijoux tout en restant vigilant dans vos choix d\'images\r\n', 'accessoires.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `clients`
--

DROP TABLE IF EXISTS `clients`;
CREATE TABLE IF NOT EXISTS `clients` (
  `IDUSER` int NOT NULL,
  `BUDGET` char(255) NOT NULL,
  `NOM` char(255) NOT NULL,
  `PRENOM` char(255) DEFAULT NULL,
  `DATENAISSANCE` char(255) NOT NULL,
  `EMAIL` char(255) NOT NULL,
  `TELEPHONE1` char(255) NOT NULL,
  `TELEPHONE2` char(255) DEFAULT NULL,
  `PASSWORD` char(255) NOT NULL,
  `ISVALIDE` tinyint(1) NOT NULL,
  `confirmpassword` varchar(255) DEFAULT NULL,
  `ISTELEPHONE1VALIDE` tinyint(1) NOT NULL,
  `ISTELEPHONE2VALIDE` tinyint(1) NOT NULL,
  KEY `I_FK_CLIENTS_USER` (`IDUSER`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `clients`
--

INSERT INTO `clients` (`IDUSER`, `BUDGET`, `NOM`, `PRENOM`, `DATENAISSANCE`, `EMAIL`, `TELEPHONE1`, `TELEPHONE2`, `PASSWORD`, `ISVALIDE`, `confirmpassword`, `ISTELEPHONE1VALIDE`, `ISTELEPHONE2VALIDE`) VALUES
(1, '500 000', 'waffo', 'mohamed brayant', '2002-10-06', 'brayantmohamed@gmail.com', '698765585', '687677678', '$2a$10$lEYdShyik7XMEocFbg.voeAygONJt0m16Qri0/5cdXZJSiyE.NvVW', 1, '$2a$10$1jB64SaidEuJqbF4lk55uuZdpAWHv9IAwke7HBYhA5naTISXvPhSq', 1, 1);

-- --------------------------------------------------------

--
-- Structure de la table `commandes`
--

DROP TABLE IF EXISTS `commandes`;
CREATE TABLE IF NOT EXISTS `commandes` (
  `IDCOMMANDE` int NOT NULL AUTO_INCREMENT,
  `IDUTILISATEUR` int NOT NULL,
  `IDCLIENT` int NOT NULL,
  `DATECOMMANDE` text NOT NULL,
  `ETAT` varchar(128) NOT NULL,
  `MOYENPAIEMENT` varchar(128) NOT NULL,
  `DATEBUTOIRE` date NOT NULL,
  `PRIX` float NOT NULL,
  PRIMARY KEY (`IDCOMMANDE`),
  KEY `I_FK_COMMANDES_UTILISATEURS` (`IDUTILISATEUR`),
  KEY `I_FK_COMMANDES_CLIENTS` (`IDCLIENT`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `commandes`
--

INSERT INTO `commandes` (`IDCOMMANDE`, `IDUTILISATEUR`, `IDCLIENT`, `DATECOMMANDE`, `ETAT`, `MOYENPAIEMENT`, `DATEBUTOIRE`, `PRIX`) VALUES
(6, 2, 1, '2023-06-03 22:57:53.04', 'livrer', '698765588', '2023-06-12', 12000),
(7, 2, 1, '2023-06-03 23:10:05.853', 'livrer', '698765588', '2023-06-12', 22600),
(8, 2, 1, '2023-06-12 15:47:00.562', 'en attente', '698765585', '2023-06-22', 4600),
(9, 2, 1, '2023-06-15 10:27:23.052', 'en attente', '698765585', '2023-06-25', 11600),
(10, 2, 1, '2023-06-16 17:40:14.466', 'en attente', '687677678', '2023-06-26', 27200),
(11, 2, 1, '2023-06-27 21:06:02.983', 'livrer', '687677678', '2023-07-06', 78999);

-- --------------------------------------------------------

--
-- Structure de la table `couleurproduit`
--

DROP TABLE IF EXISTS `couleurproduit`;
CREATE TABLE IF NOT EXISTS `couleurproduit` (
  `IDPRODUIT` int NOT NULL,
  `IDCOULEUR` int NOT NULL,
  PRIMARY KEY (`IDPRODUIT`,`IDCOULEUR`),
  KEY `I_FK_COULEURPRODUIT_PRODUITS` (`IDPRODUIT`),
  KEY `I_FK_COULEURPRODUIT_COULEURS` (`IDCOULEUR`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `couleurproduit`
--

INSERT INTO `couleurproduit` (`IDPRODUIT`, `IDCOULEUR`) VALUES
(8, 2),
(8, 3),
(8, 6);

-- --------------------------------------------------------

--
-- Structure de la table `couleurs`
--

DROP TABLE IF EXISTS `couleurs`;
CREATE TABLE IF NOT EXISTS `couleurs` (
  `IDCOULEUR` int NOT NULL AUTO_INCREMENT,
  `NOM` varchar(128) NOT NULL,
  PRIMARY KEY (`IDCOULEUR`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `couleurs`
--

INSERT INTO `couleurs` (`IDCOULEUR`, `NOM`) VALUES
(8, 'violet'),
(6, 'jaune'),
(4, 'vert\r\n'),
(3, 'rouge'),
(5, 'gris'),
(2, 'blanc'),
(7, 'maron'),
(1, 'noir'),
(9, 'bleu'),
(10, 'gris');

-- --------------------------------------------------------

--
-- Structure de la table `favories`
--

DROP TABLE IF EXISTS `favories`;
CREATE TABLE IF NOT EXISTS `favories` (
  `IDFAVORIES` int NOT NULL AUTO_INCREMENT,
  `IDCLIENT` int NOT NULL,
  `NOMBRES` int NOT NULL,
  PRIMARY KEY (`IDFAVORIES`),
  KEY `I_FK_FAVORIES_CLIENTS` (`IDCLIENT`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `favories`
--

INSERT INTO `favories` (`IDFAVORIES`, `IDCLIENT`, `NOMBRES`) VALUES
(11, 1, 0),
(10, 1, 0);

-- --------------------------------------------------------

--
-- Structure de la table `images`
--

DROP TABLE IF EXISTS `images`;
CREATE TABLE IF NOT EXISTS `images` (
  `IDIMAGE` int NOT NULL AUTO_INCREMENT,
  `IDPRODUIT` int DEFAULT NULL,
  `NOM` text NOT NULL,
  `CHEMIN` text NOT NULL,
  PRIMARY KEY (`IDIMAGE`),
  KEY `I_FK_IMAGES_PRODUITS` (`IDPRODUIT`)
) ENGINE=MyISAM AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `imagescategorie`
--

DROP TABLE IF EXISTS `imagescategorie`;
CREATE TABLE IF NOT EXISTS `imagescategorie` (
  `IDIMAGECATEGORIE` int NOT NULL,
  `IDCATEGORIE` int NOT NULL,
  `NOM` text NOT NULL,
  `CHEMIN` text NOT NULL,
  PRIMARY KEY (`IDIMAGECATEGORIE`),
  KEY `I_FK_IMAGESCATEGORIE_CATEGORIES` (`IDCATEGORIE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `lignecommandes`
--

DROP TABLE IF EXISTS `lignecommandes`;
CREATE TABLE IF NOT EXISTS `lignecommandes` (
  `IDLIGNECOMMANDE` int NOT NULL AUTO_INCREMENT,
  `IDCOMMANDE` int NOT NULL,
  `IDPRODUIT` int NOT NULL,
  `QUANTITE` int NOT NULL,
  `PRIX` int NOT NULL,
  `TAILLE` varchar(128) NOT NULL,
  `COULEUR` varchar(128) NOT NULL,
  PRIMARY KEY (`IDLIGNECOMMANDE`),
  KEY `I_FK_LIGNECOMMANDES_COMMANDES` (`IDCOMMANDE`),
  KEY `I_FK_LIGNECOMMANDES_PRODUITS` (`IDPRODUIT`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `lignecommandes`
--

INSERT INTO `lignecommandes` (`IDLIGNECOMMANDE`, `IDCOMMANDE`, `IDPRODUIT`, `QUANTITE`, `PRIX`, `TAILLE`, `COULEUR`) VALUES
(13, 6, 13, 2, 12000, 'SMALL', 'bleu'),
(14, 7, 13, 3, 18000, 'ORDINNARY', 'blanc'),
(15, 7, 9, 1, 4600, 'XL', 'rouge'),
(16, 8, 9, 1, 4600, 'M\r\n', 'maron'),
(17, 9, 10, 1, 6000, 'XL', 'noir'),
(18, 9, 9, 1, 5600, 'XXM', 'noir'),
(19, 10, 12, 2, 16000, 'SMALL', 'noir'),
(20, 10, 9, 2, 11200, 'XXM', 'noir'),
(21, 11, 21, 1, 78999, 'XXL', 'maron');

-- --------------------------------------------------------

--
-- Structure de la table `lignepaniers`
--

DROP TABLE IF EXISTS `lignepaniers`;
CREATE TABLE IF NOT EXISTS `lignepaniers` (
  `IDLIGNEPANIER` int NOT NULL AUTO_INCREMENT,
  `IDPANIER` int NOT NULL,
  `IDPRODUIT` int NOT NULL,
  `QUANTITE` int NOT NULL,
  `PRIX` int NOT NULL,
  `couleur` varchar(255) DEFAULT NULL,
  `taille` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`IDLIGNEPANIER`),
  KEY `I_FK_LIGNEPANIERS_PANIER` (`IDPANIER`),
  KEY `I_FK_LIGNEPANIERS_PRODUITS` (`IDPRODUIT`)
) ENGINE=MyISAM AUTO_INCREMENT=66 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `livraison`
--

DROP TABLE IF EXISTS `livraison`;
CREATE TABLE IF NOT EXISTS `livraison` (
  `IDLIVRAISON` int NOT NULL AUTO_INCREMENT,
  `IDCOMMANDE` int NOT NULL,
  `DATELIVRAISON` text NOT NULL,
  `LIEU` text NOT NULL,
  `ETAT` tinyint NOT NULL,
  PRIMARY KEY (`IDLIVRAISON`),
  UNIQUE KEY `I_FK_LIVRAISON_COMMANDES` (`IDCOMMANDE`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `livraison`
--

INSERT INTO `livraison` (`IDLIVRAISON`, `IDCOMMANDE`, `DATELIVRAISON`, `LIEU`, `ETAT`) VALUES
(5, 6, '2023-06-13 22:57:53.04', 'douala-bonnaberi entrée zone industrielle', 1),
(6, 7, '2023-06-13 23:10:05.853', 'douala-bonnaberi entrée zone industrielle', 1),
(7, 8, '2023-06-22 15:47:00.562', 'Buéa-face immeuble marché central', 0),
(8, 9, '2023-06-25 10:27:23.052', 'yaoundé-marché vog mbi ', 0),
(9, 10, '2023-06-26 17:40:14.466', 'yaoundé-marché vog mbi ', 0),
(10, 11, '2023-07-07 21:06:02.983', 'yaoundé-marché vog mbi ', 1);

-- --------------------------------------------------------

--
-- Structure de la table `newsletter`
--

DROP TABLE IF EXISTS `newsletter`;
CREATE TABLE IF NOT EXISTS `newsletter` (
  `IDNEWSLETTER` int NOT NULL,
  `NOMBREDINSCRIT` varchar(128) NOT NULL,
  PRIMARY KEY (`IDNEWSLETTER`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `panier`
--

DROP TABLE IF EXISTS `panier`;
CREATE TABLE IF NOT EXISTS `panier` (
  `IDPANIER` int NOT NULL AUTO_INCREMENT,
  `IDCLIENT` int NOT NULL,
  `DATEPANIER` text NOT NULL,
  `VALIDE` tinyint(1) NOT NULL,
  `ABOUTI` tinyint(1) NOT NULL,
  `PRIXTOTAL` float NOT NULL,
  PRIMARY KEY (`IDPANIER`),
  UNIQUE KEY `I_FK_PANIER_CLIENTS` (`IDCLIENT`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `panier`
--

INSERT INTO `panier` (`IDPANIER`, `IDCLIENT`, `DATEPANIER`, `VALIDE`, `ABOUTI`, `PRIXTOTAL`) VALUES
(6, 1, '2023-06-27 21:05:22.86', 1, 0, 0);

-- --------------------------------------------------------

--
-- Structure de la table `produitfavorie`
--

DROP TABLE IF EXISTS `produitfavorie`;
CREATE TABLE IF NOT EXISTS `produitfavorie` (
  `IDPRODUIT` int NOT NULL,
  `IDFAVORIES` int NOT NULL,
  KEY `FKoy06erm9dxs82dycdlup11nuv` (`IDFAVORIES`),
  KEY `FKpixt07iasxqvjbah2us3q2fri` (`IDPRODUIT`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `produits`
--

DROP TABLE IF EXISTS `produits`;
CREATE TABLE IF NOT EXISTS `produits` (
  `IDPRODUIT` int NOT NULL AUTO_INCREMENT,
  `IDTYPEPRODUIT` int NOT NULL,
  `IDUTILISATEUR` int NOT NULL,
  `IDCATEGORIE` int NOT NULL,
  `LIBELLE` text NOT NULL,
  `PRIX` int NOT NULL,
  `REFERENCE` text NOT NULL,
  `DESCRIPTION` text NOT NULL,
  `DATEAJOUT` date NOT NULL,
  `QUANTITE` int NOT NULL,
  `SEUIL` int NOT NULL,
  PRIMARY KEY (`IDPRODUIT`),
  KEY `I_FK_PRODUITS_TYPEPRODUIT` (`IDTYPEPRODUIT`),
  KEY `I_FK_PRODUITS_UTILISATEURS` (`IDUTILISATEUR`),
  KEY `I_FK_PRODUITS_CATEGORIES` (`IDCATEGORIE`)
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `roles`
--

DROP TABLE IF EXISTS `roles`;
CREATE TABLE IF NOT EXISTS `roles` (
  `IDROLE` int NOT NULL,
  `NOM` text NOT NULL,
  PRIMARY KEY (`IDROLE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `roles`
--

INSERT INTO `roles` (`IDROLE`, `NOM`) VALUES
(1, 'CLIENT'),
(2, 'UTILISATEUR');

-- --------------------------------------------------------

--
-- Structure de la table `tailleproduit`
--

DROP TABLE IF EXISTS `tailleproduit`;
CREATE TABLE IF NOT EXISTS `tailleproduit` (
  `IDPRODUIT` int NOT NULL,
  `IDTAILLE` int NOT NULL,
  PRIMARY KEY (`IDPRODUIT`,`IDTAILLE`),
  KEY `I_FK_TAILLEPRODUIT_PRODUITS` (`IDPRODUIT`),
  KEY `I_FK_TAILLEPRODUIT_TAILLES` (`IDTAILLE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `tailleproduit`
--

INSERT INTO `tailleproduit` (`IDPRODUIT`, `IDTAILLE`) VALUES
(8, 1),
(8, 2),
(8, 3);

-- --------------------------------------------------------

--
-- Structure de la table `tailles`
--

DROP TABLE IF EXISTS `tailles`;
CREATE TABLE IF NOT EXISTS `tailles` (
  `IDTAILLE` int NOT NULL AUTO_INCREMENT,
  `IDCATEGORIE` int NOT NULL,
  `LIBELLE` text NOT NULL,
  PRIMARY KEY (`IDTAILLE`),
  KEY `FKmug955grtqxt1xscl3s6270b7` (`IDCATEGORIE`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `tailles`
--

INSERT INTO `tailles` (`IDTAILLE`, `IDCATEGORIE`, `LIBELLE`) VALUES
(4, 1, 'M\r\n'),
(5, 1, 'XM'),
(6, 1, 'XXM'),
(8, 2, '15-20'),
(7, 2, '10-15'),
(16, 1, 'L'),
(10, 2, '25-30'),
(11, 2, '20-25'),
(12, 2, '30-35'),
(13, 2, '35-40'),
(14, 2, '40-45'),
(15, 2, '45-50'),
(17, 1, 'MM'),
(18, 3, 'SMALL'),
(20, 3, 'MEDIUM'),
(19, 3, 'LARGE'),
(21, 3, 'VERY SMALL'),
(22, 3, 'VERY LARGE'),
(23, 3, 'ORDINNARY'),
(1, 1, 'XL'),
(2, 1, 'XXL'),
(3, 1, 'XXXL'),
(9, 1, 'X');

-- --------------------------------------------------------

--
-- Structure de la table `typeboutique`
--

DROP TABLE IF EXISTS `typeboutique`;
CREATE TABLE IF NOT EXISTS `typeboutique` (
  `IDTYPEBOUTIQUE` int NOT NULL,
  `LIBELLE` text NOT NULL,
  `LIVRABLE` tinyint(1) NOT NULL,
  PRIMARY KEY (`IDTYPEBOUTIQUE`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `typeboutique`
--

INSERT INTO `typeboutique` (`IDTYPEBOUTIQUE`, `LIBELLE`, `LIVRABLE`) VALUES
(1, 'Hommes', 1),
(2, 'Femmes', 1),
(3, 'Enfants', 1),
(4, 'Mixte', 1);

-- --------------------------------------------------------

--
-- Structure de la table `typeproduit`
--

DROP TABLE IF EXISTS `typeproduit`;
CREATE TABLE IF NOT EXISTS `typeproduit` (
  `IDTYPEPRODUIT` int NOT NULL AUTO_INCREMENT,
  `IDCATEGORIE` int NOT NULL,
  `NOM` varchar(128) NOT NULL,
  PRIMARY KEY (`IDTYPEPRODUIT`),
  KEY `I_FK_TYPEPRODUIT_CATEGORIES` (`IDCATEGORIE`)
) ENGINE=MyISAM AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `typeproduit`
--

INSERT INTO `typeproduit` (`IDTYPEPRODUIT`, `IDCATEGORIE`, `NOM`) VALUES
(1, 1, 'short'),
(2, 1, 'jogging'),
(3, 1, 'pull over'),
(4, 1, 'tee-shirt'),
(5, 1, 'ensemble uniforme'),
(6, 1, 'robe'),
(7, 1, 'ensemble haut pantalon'),
(8, 2, 'talon'),
(9, 2, 'basket'),
(10, 2, 'tenis de sport'),
(11, 2, 'chaussure de marque'),
(12, 3, 'sac a main'),
(13, 3, 'montre de luxe');

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `IDUSER` int NOT NULL AUTO_INCREMENT,
  `IDNEWSLETTER` int DEFAULT NULL,
  `NOM` char(255) NOT NULL,
  `PRENOM` char(255) DEFAULT NULL,
  `DATENAISSANCE` char(255) NOT NULL,
  `EMAIL` char(255) NOT NULL,
  `TELEPHONE1` char(255) NOT NULL,
  `TELEPHONE2` char(255) DEFAULT NULL,
  `PASSWORD` char(255) NOT NULL,
  `ISVALIDE` tinyint(1) NOT NULL,
  `ISTELEPHONE1VALIDE` tinyint(1) NOT NULL,
  `ISTELEPHONE2VALIDE` tinyint(1) NOT NULL,
  PRIMARY KEY (`IDUSER`),
  KEY `I_FK_USER_NEWSLETTER` (`IDNEWSLETTER`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `userrole`
--

DROP TABLE IF EXISTS `userrole`;
CREATE TABLE IF NOT EXISTS `userrole` (
  `IDROLE` int NOT NULL,
  `IDUSER` int NOT NULL,
  PRIMARY KEY (`IDROLE`,`IDUSER`),
  KEY `I_FK_USERROLE_ROLES` (`IDROLE`),
  KEY `I_FK_USERROLE_USER` (`IDUSER`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `userrole`
--

INSERT INTO `userrole` (`IDROLE`, `IDUSER`) VALUES
(1, 1),
(2, 2),
(2, 102);

-- --------------------------------------------------------

--
-- Structure de la table `user_seq`
--

DROP TABLE IF EXISTS `user_seq`;
CREATE TABLE IF NOT EXISTS `user_seq` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `user_seq`
--

INSERT INTO `user_seq` (`next_val`) VALUES
(201);

-- --------------------------------------------------------

--
-- Structure de la table `utilisateurs`
--

DROP TABLE IF EXISTS `utilisateurs`;
CREATE TABLE IF NOT EXISTS `utilisateurs` (
  `IDUSER` int NOT NULL,
  `NUMEROCNI` int NOT NULL,
  `NOM` char(255) NOT NULL,
  `PRENOM` char(255) DEFAULT NULL,
  `DATENAISSANCE` char(255) NOT NULL,
  `EMAIL` char(255) NOT NULL,
  `TELEPHONE1` char(255) NOT NULL,
  `TELEPHONE2` char(255) DEFAULT NULL,
  `PASSWORD` char(255) NOT NULL,
  `ISVALIDE` tinyint(1) NOT NULL,
  `confirmpassword` varchar(255) DEFAULT NULL,
  `ISTELEPHONE1VALIDE` tinyint(1) NOT NULL,
  `ISTELEPHONE2VALIDE` tinyint(1) NOT NULL,
  KEY `I_FK_UTILISATEURS_USER` (`IDUSER`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `utilisateurs`
--

INSERT INTO `utilisateurs` (`IDUSER`, `NUMEROCNI`, `NOM`, `PRENOM`, `DATENAISSANCE`, `EMAIL`, `TELEPHONE1`, `TELEPHONE2`, `PASSWORD`, `ISVALIDE`, `confirmpassword`, `ISTELEPHONE1VALIDE`, `ISTELEPHONE2VALIDE`) VALUES
(2, 1234567890, 'mohamed', 'brayant', '2023-05-18', 'brayantkamdem0067@gmail.com', '687677678', '687677678', '$2a$10$LGmEcahWcUmS5Z9D8SGMxeMfn7S1SiLyu8p8FO2xgH6fuDt6n5LZu', 1, '$2a$10$YLta2jg5E6u3e3ZikNzQM.RfTP3ou9Fete5h2RfUvVlf8eHeDTlDG', 1, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
