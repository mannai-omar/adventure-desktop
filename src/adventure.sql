-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 12 mai 2024 à 15:03
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `adventure1`
--

-- --------------------------------------------------------

--
-- Structure de la table `activity`
--

CREATE TABLE `activity` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `duration` int(11) NOT NULL,
  `type` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `max_guests` int(11) NOT NULL,
  `min_age` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `activity`
--

INSERT INTO `activity` (`id`, `name`, `location`, `price`, `duration`, `type`, `description`, `max_guests`, `min_age`) VALUES
(1, 'Hiking Adventure', 'Mountain Peak', 150, 2, 'Outdoor', 'Embark on a thrilling hiking adventure to the mountain peak . Feel the anticipation as you ascend through lush forests and rugged terrain . Experience the triumph of reaching the summit and the awe-inspiring views.', 20, 12),
(4, 'Scuba Diving Adventure', 'Caribbean Sea', 150, 3, 'Water', 'Explore the vibrant underwater world with our certified instructors. Witness colorful coral reefs and diverse marine life.', 8, 12),
(5, 'Hot Air Balloon Ride', 'Napa Valley, California', 250, 2, 'Air', 'Soar above picturesque vineyards and rolling hills in a serene hot air balloon ride. Experience breathtaking views of the landscape below.', 4, 8),
(6, 'Wilderness Camping', 'Yellowstone National Park, Wyoming', 30, 24, 'Camping', 'Immerse yourself in nature with a camping trip in the heart of Yellowstone. Pitch your tent under the stars and enjoy outdoor activities like hiking and wildlife spotting.', 20, 12),
(50, 'symfonyTest', '4', 3, 3, '3', '3	3', 3, 3);

-- --------------------------------------------------------

--
-- Structure de la table `activity_images`
--

CREATE TABLE `activity_images` (
  `id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `url` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `activity_images`
--

INSERT INTO `activity_images` (`id`, `activity_id`, `url`) VALUES
(1, 1, 'hommes-femmes-randonnant-ensemble-au-sommet-montagne-generes-par-ia-188544-31096-65e9b710ef45d.jpg'),
(2, 1, 'hiking-65ddd1fe814df.jpg'),
(3, 1, 'homme-trekking-dans-montagnes-23-2148106701-65e9b710efaf2.jpg'),
(4, 1, 'homme-trekking-dans-nature-23-2148106953-65e9b710effbb.jpg'),
(5, 1, 'vue-arriere-du-couple-long-route-foret-femme-homme-aux-cheveux-longs-portant-sacs-dos-randonnee-nature-ensemble-arbres-verts-fond-concept-tourisme-aventure-vacances-ete-74855-11515-65e9b710f02fa.jpg'),
(8, 4, 'beaux-poissons-nageant-autour-coraux-sous-mer-181624-10112-65eaa651f39fa.jpg'),
(9, 4, 'femme-apnee-palmes-sous-eau-23-2148976490-65eaa65201aca.jpg'),
(10, 4, 'homme-nageant-sous-eau-pendant-journee-ensoleillee-181624-42142-65eaa65201de7.jpg'),
(11, 4, 'selfie-plongee-sous-marine-selfie-tourne-baton-selfie-mer-bleu-profond-prise-vue-grand-angle-1253-1537-65eaa652020a1.jpg'),
(12, 5, 'aerostat-ballon-93675-132519-65eaa6abafd5b.jpg'),
(13, 5, 'colore-chaud-fleurs-printanieres-nature-1172-185-65eaa6abb1171.jpg'),
(14, 5, 'homme-debout-son-bras-son-poing-leves-air-montgolfiere-volant-arriere-plan-181624-2112-65eaa6abb145d.jpg'),
(15, 5, 'vol-montgolfiere-649448-666-65eaa6abb16cd.jpg'),
(16, 6, 'friends-sitting-near-bonfire-smiling-embracing-resting-playing-guitar-176420-4178-65eaa70d189c3.jpg'),
(17, 6, 'hiker-stand-camping-near-orange-tent-backpack-mountains-1150-9161-65eaa70d1a1ee.jpg'),
(18, 6, 'man-tent-looking-campfire-23-2148301480-65eaa70d1a639.jpg'),
(19, 6, 'tents-camping-site-kilimanjaro-mountain-181624-37110-65eaa70d1ab14.jpg'),
(59, 50, 'homme-trekking-dans-nature_23-2148106953.jpg'),
(60, 50, 'man-tent-looking-campfire_23-2148301480.jpg'),
(61, 50, 'quels-sont-les-types-d-hebergements-en-camping-1.jpg'),
(62, 50, 'vol-montgolfiere_649448-666.jpg'),
(63, 50, 'vue-arriere-du-couple-long-route-foret-femme-homme-aux-cheveux-longs-portant.jpg');

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

CREATE TABLE `avis` (
  `id` int(11) NOT NULL,
  `blog_id` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `commentaire` varchar(255) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `likes` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `avis`
--

INSERT INTO `avis` (`id`, `blog_id`, `note`, `created_at`, `updated_at`, `commentaire`, `nom`, `likes`) VALUES
(1, 1, 3, '2024-03-07 22:43:46', '2024-03-07 22:43:46', 'hey ****', 'omar', 1);

-- --------------------------------------------------------

--
-- Structure de la table `blog`
--

CREATE TABLE `blog` (
  `id` int(11) NOT NULL,
  `titre` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `image` varchar(255) NOT NULL,
  `contenu` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `blog`
--

INSERT INTO `blog` (`id`, `titre`, `description`, `image`, `contenu`, `created_at`, `updated_at`) VALUES
(1, 'Galite – Tabarka\r\n', 'Probablement le spot de camping le plus convoité ! Galite est un archipel constitué d’un ensemble d\'îles rocheuses. L’endroit, calme et idyllique', 'https://www.marhba.com/images/Galite.jpg', 'Probablement le spot de camping le plus convoité ! Galite est un archipel constitué d’un ensemble d\'îles rocheuses. L’endroit, calme et idyllique, offre un moment de détente et de tranquillité pour les campeurs.\r\n\r\n', '2024-03-07 22:38:08', '2024-03-07 22:38:08'),
(2, 'Ain Kanassira – Nabeul\r\n', 'Réservez cette expérience en privé : 2 jours à Aïn Kanassira avec les locaux\r\n\r\n', 'https://www.marhba.com/images/alternatif/alternatif2020/Camping_Tunisie_Ain_Kanassira_.jpg', 'Située à Korbous à environ 1h30 de la capitale Tunis, Ain Kanassira offre un paysage entre la mer et la montagne. Elle est connue pour sa source d’eau chaude de 44°C et ses vertus thérapeutiques.\r\n\r\n', '2024-03-07 22:38:08', '2024-03-07 22:38:08'),
(3, 'hhhhh', 'Située à Korbous à environ 1h30 de la capitale Tunis, Ain Kanassira offre un paysage entre la mer et la montagne. Elle est connue pour sa source d’eau chaude de 44°C et ses vertus thérapeutiques.', '', 'Située à Korbous à environ 1h30 de la capitale Tunis, Ain Kanassira offre un paysage entre la mer et la montagne. Elle est connue pour sa source d’eau chaude de 44°C et ses vertus thérapeutiques.', '2024-03-07 22:46:02', '2024-03-07 22:46:02'),
(4, 'tttttttt', 'Située à Korbous à environ 1h30 de la capitale Tunis, Ain Kanassira offre un paysage entre la mer et la montagne. Elle est connue pour sa source d’eau chaude de 44°C et ses vertus thérapeutiques.', '', 'Située à Korbous à environ 1h30 de la capitale Tunis, Ain Kanassira offre un paysage entre la mer et la montagne. Elle est connue pour sa source d’eau chaude de 44°C et ses vertus thérapeutiques.', '2024-03-07 22:54:37', '2024-03-07 22:54:37');

-- --------------------------------------------------------

--
-- Structure de la table `comment`
--

CREATE TABLE `comment` (
  `id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(255) NOT NULL,
  `text` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `rating` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `comment`
--

INSERT INTO `comment` (`id`, `activity_id`, `email`, `name`, `text`, `created_at`, `rating`) VALUES
(1, 1, 'mannai@gmail.com', 'ahmed', 'enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit sed quia consequuntur magne doloreseos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor si amet consectetur adipisci velit sed quian numquam', '2024-03-07 17:52:24', 3),
(10, 1, 'email@gmail.com', 'omar', 'jjnjnjn', '2024-04-25 16:19:14', 5),
(12, 1, 'email@gmail.com', 'omar', 'jjjjj', '2024-04-28 18:46:08', 5),
(14, 1, 'email@gmail.com', 'omar', 'test time ago ', '2024-05-03 11:47:27', 5),
(15, 1, 'email@gmail.com', 'omar', 'azeazeaze', '2024-05-05 02:09:11', 3),
(16, 4, 'email@gmail.com', 'omar', 'testUpdate', '2024-05-05 11:06:45', 4),
(17, 4, 'email@gmail.com', 'omar', 'test time', '2024-05-05 11:07:00', 2),
(18, 4, 'email@gmail.com', 'omar', '555', '2024-05-05 11:19:45', 3),
(19, 4, 'email@gmail.com', 'omar', 'test', '2024-05-05 11:23:06', 4),
(20, 4, 'email@gmail.com', 'aziz mannai', 'test initials', '2024-05-05 12:18:08', 4),
(21, 5, 'email@gmail.com', 'aziz mannai', 'aaa', '2024-05-05 12:51:26', 4),
(22, 5, 'email@gmail.com', 'aziz mannai', 'u', '2024-05-05 12:51:35', 4),
(23, 5, 'email@gmail.com', 'aziz mannai', '4', '2024-05-05 12:51:41', 4),
(24, 5, 'email@gmail.com', 'aziz mannai', '4', '2024-05-05 12:51:48', 4),
(25, 1, 'email@gmail.com', 'aziz mannai', 'test order', '2024-05-06 14:33:16', 4),
(26, 4, 'email@gmail.com', 'aziz mannai', 'integ', '2024-05-07 21:22:05', 2),
(27, 1, 'email@gmail.com', 'aziz mannai', 'TEST INT', '2024-05-09 15:27:52', 3),
(28, 1, 'mohamed.soua@esprit.tn', 'mohamed.soua', 'symfony test', '2024-05-11 13:16:53', 4),
(30, 50, 'mohamed.soua@esprit.tn', 'mohamed.soua', 'symfonyyy', '2024-05-11 13:38:45', 5),
(31, 6, 'mannai@gmail.com', 'ahmed', 'enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit sed quia consequuntur magne doloreseos qui ratione voluptatem sequi nesciunt. Neque porro quisquam est, qui dolorem ipsum quia dolor si amet consectetur adipisci velit sed quian numquam', '2024-05-11 15:10:55', 1);

-- --------------------------------------------------------

--
-- Structure de la table `commodite`
--

CREATE TABLE `commodite` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `disponible` tinyint(1) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `doctrine_migration_versions`
--

CREATE TABLE `doctrine_migration_versions` (
  `version` varchar(191) NOT NULL,
  `executed_at` datetime DEFAULT NULL,
  `execution_time` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--
-- Déchargement des données de la table `doctrine_migration_versions`
--

INSERT INTO `doctrine_migration_versions` (`version`, `executed_at`, `execution_time`) VALUES
('DoctrineMigrations\\Version20240307123522', '2024-03-07 13:35:36', 995);

-- --------------------------------------------------------

--
-- Structure de la table `fav_activities`
--

CREATE TABLE `fav_activities` (
  `id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `user_email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `fav_activities`
--

INSERT INTO `fav_activities` (`id`, `activity_id`, `user_email`) VALUES
(50, 50, 'mannaiomar28@gmail.com'),
(51, 1, 'email@test.com');

-- --------------------------------------------------------

--
-- Structure de la table `hebergement`
--

CREATE TABLE `hebergement` (
  `id` int(11) NOT NULL,
  `type_hebergement_id` int(11) NOT NULL,
  `host_service_id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `prix_adulte` double NOT NULL,
  `prix_jeune` double NOT NULL,
  `prix_enfant` double NOT NULL,
  `adresse` varchar(255) NOT NULL,
  `nbr_place` int(11) NOT NULL,
  `image` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `hebergement`
--

INSERT INTO `hebergement` (`id`, `type_hebergement_id`, `host_service_id`, `nom`, `description`, `prix_adulte`, `prix_jeune`, `prix_enfant`, `adresse`, `nbr_place`, `image`, `created_at`, `updated_at`) VALUES
(1, 1, 1, 'validation', 'validationvalidation', 20, 10, 0, 'validationvalidation', 20, '9fffb34194ed8c379a2448c5bff95151Controllerjpg', '2024-03-08 09:45:10', NULL),
(2, 1, 1, 'validation222', 'validationvalidationvalidation', 20, 10, 0, 'validationvalidationvalidation222', 20, '1ac2df8f0dcb7c1f2c4f167320aa8ba0Controllerjpg', '2024-03-08 09:45:57', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `host_service`
--

CREATE TABLE `host_service` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `host_service`
--

INSERT INTO `host_service` (`id`, `nom`, `description`, `created_at`, `updated_at`) VALUES
(1, 'validation', 'validationvalidation', '2024-03-08 09:44:28', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `messenger_messages`
--

CREATE TABLE `messenger_messages` (
  `id` bigint(20) NOT NULL,
  `body` longtext NOT NULL,
  `headers` longtext NOT NULL,
  `queue_name` varchar(190) NOT NULL,
  `created_at` datetime NOT NULL,
  `available_at` datetime NOT NULL,
  `delivered_at` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `order`
--

CREATE TABLE `order` (
  `id` int(11) NOT NULL,
  `status` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL,
  `update_at` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `order`
--

INSERT INTO `order` (`id`, `status`, `created_at`, `update_at`) VALUES
(1, 'cart', '2024-03-08 10:20:36', '2024-03-08 10:20:36');

-- --------------------------------------------------------

--
-- Structure de la table `order_item`
--

CREATE TABLE `order_item` (
  `id` int(11) NOT NULL,
  `product_id` int(11) NOT NULL,
  `order_ref_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `order_item`
--

INSERT INTO `order_item` (`id`, `product_id`, `order_ref_id`, `quantity`) VALUES
(1, 1, 1, 3);

-- --------------------------------------------------------

--
-- Structure de la table `product`
--

CREATE TABLE `product` (
  `id` int(11) NOT NULL,
  `category_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` int(11) NOT NULL,
  `image` varchar(255) DEFAULT NULL,
  `quantity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `product`
--

INSERT INTO `product` (`id`, `category_id`, `name`, `description`, `price`, `image`, `quantity`) VALUES
(1, 1, 'tent', 'tent for 2 people', 50, 'hiker-stand-camping-near-orange-tent-backpack-mountains-1150-9161-65ead7fd2f184.jpg', 5),
(2, 1, 'big tent', 'tent for 4 people', 50, 'man-tent-looking-campfire-23-2148301480-65ead81494e22.jpg', 5),
(3, 2, 'parachute', 'parachute for sky diving', 150, 'colore-chaud-fleurs-printanieres-nature-1172-185-65ead83d0ebd5.jpg', 5);

-- --------------------------------------------------------

--
-- Structure de la table `product_cat`
--

CREATE TABLE `product_cat` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `product_cat`
--

INSERT INTO `product_cat` (`id`, `name`) VALUES
(1, 'camping'),
(2, 'sky diving');

-- --------------------------------------------------------

--
-- Structure de la table `rent`
--

CREATE TABLE `rent` (
  `id` int(11) NOT NULL,
  `start_date` date NOT NULL,
  `finish_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `id` int(11) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `date` date NOT NULL,
  `nbr_tickets` int(11) NOT NULL,
  `user_email` varchar(255) NOT NULL,
  `status` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`id`, `activity_id`, `date`, `nbr_tickets`, `user_email`, `status`) VALUES
(1, 1, '2025-01-01', 3, 'omar.mannai@esprit.tn', 'Canceled'),
(2, 1, '2026-01-01', 3, 'omar.mannai@esprit.tn', 'Canceled'),
(3, 1, '2025-01-01', 4, 'omar.mannai@esprit.tn', 'Canceled'),
(4, 1, '2025-01-01', 5, 'omar.mannai@esprit.tn', 'Canceled'),
(6, 1, '2025-01-01', 2, 'omar.mannai@esprit.tn', 'Confirmed'),
(7, 1, '2024-04-30', 7, 'email@gmail.com', 'Pending'),
(8, 4, '2024-05-04', 8, 'email@gmail.com', 'Pending'),
(9, 1, '2024-05-02', 2, 'email@gmail.com', 'Pending'),
(10, 1, '2024-05-12', 5, 'email@gmail.com', 'Pending'),
(11, 1, '2024-05-05', 3, 'email@gmail.com', 'Pending'),
(12, 1, '2024-05-12', 5, 'email@gmail.com', 'Pending'),
(13, 1, '2024-05-01', 9, 'email@gmail.com', 'Pending'),
(14, 1, '2024-05-12', 8, 'email@gmail.com', 'Pending'),
(15, 1, '2024-05-02', 8, 'email@gmail.com', 'Pending'),
(16, 1, '2024-05-11', 8, 'email@gmail.com', 'Pending'),
(17, 1, '2024-05-01', 6, 'email@gmail.com', 'Pending'),
(18, 1, '2024-05-01', 3, 'email@gmail.com', 'Pending'),
(19, 1, '2024-05-12', 77, 'email@gmail.com', 'Pending'),
(20, 4, '2024-05-01', 5, 'email@gmail.com', 'Pending'),
(21, 1, '2024-05-01', 10, 'email@gmail.com', 'Pending'),
(22, 4, '2024-05-11', 5151, 'email@gmail.com', 'Pending'),
(23, 1, '2024-05-04', 55, 'email@gmail.com', 'Pending'),
(24, 1, '2024-05-08', 78788, 'email@gmail.com', 'Pending'),
(25, 1, '2024-05-11', 222, 'email@gmail.com', 'Pending'),
(26, 4, '2024-05-03', 888, 'email@gmail.com', 'Pending'),
(27, 1, '2024-05-05', 77, 'email@gmail.com', 'Pending'),
(28, 1, '2024-06-09', 888, 'email@gmail.com', 'Pending'),
(29, 1, '2024-06-09', 7575, 'email@gmail.com', 'Pending'),
(30, 1, '2025-06-07', 88, 'email@gmail.com', 'Pending'),
(31, 4, '2026-05-09', 77777, 'email@gmail.com', 'Pending'),
(32, 4, '2027-05-01', 7272, 'email@gmail.com', 'Pending'),
(33, 1, '2024-06-09', 85, 'email@gmail.com', 'Pending'),
(34, 4, '2024-06-02', 1, 'email@gmail.com', 'Pending'),
(35, 1, '2026-05-16', 82, 'email@gmail.com', 'Pending'),
(36, 5, '2024-06-09', 3, 'email@gmail.com', 'Pending'),
(37, 4, '2024-06-02', 5, 'email@gmail.com', 'Pending'),
(38, 6, '2024-05-10', 2, 'email@gmail.com', 'Pending'),
(39, 1, '2024-06-02', 88, 'email@gmail.com', 'Pending'),
(40, 1, '2028-05-14', 5, 'email@gmail.com', 'Pending'),
(41, 1, '2031-05-04', 7, 'email@gmail.com', 'Pending'),
(42, 5, '2025-05-09', 7, 'email@gmail.com', 'Pending'),
(43, 5, '2025-05-02', 7, 'email@gmail.com', 'Pending'),
(44, 5, '2024-05-16', 8, 'email@gmail.com', 'Pending'),
(45, 5, '2024-05-09', 4, 'email@gmail.com', 'Pending'),
(46, 1, '2024-05-11', 77, 'email@gmail.com', 'Pending'),
(47, 1, '2024-05-09', 4, 'email@gmail.com', 'Pending'),
(48, 1, '2024-05-09', 77, 'email@gmail.com', 'Pending'),
(49, 1, '2024-05-16', 5, 'email@gmail.com', 'Pending'),
(50, 1, '2024-05-09', 8, 'email@gmail.com', 'Pending'),
(51, 1, '2024-05-09', 88, 'email@gmail.com', 'Pending'),
(52, 4, '2024-05-11', 2, 'email@gmail.com', 'Pending'),
(53, 1, '2024-05-09', 222, 'email@gmail.com', 'Pending'),
(54, 1, '2024-05-17', 55, 'email@gmail.com', 'Pending'),
(55, 1, '2024-05-09', 55, 'email@gmail.com', 'Pending'),
(56, 1, '2024-05-09', 55, 'email@gmail.com', 'Pending'),
(57, 1, '2024-05-09', 88, 'email@gmail.com', 'Pending'),
(58, 1, '2026-05-10', 5, 'email@gmail.com', 'Pending');

-- --------------------------------------------------------

--
-- Structure de la table `reservation_hebergement`
--

CREATE TABLE `reservation_hebergement` (
  `id` int(11) NOT NULL,
  `hebergement_id` int(11) DEFAULT NULL,
  `date_deb` varchar(255) NOT NULL,
  `date_fin` varchar(255) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)',
  `etat` varchar(255) NOT NULL,
  `nbr_adulte` int(11) NOT NULL,
  `nbr_jeune` int(11) NOT NULL,
  `nbr_enfant` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `reservation_hebergement`
--

INSERT INTO `reservation_hebergement` (`id`, `hebergement_id`, `date_deb`, `date_fin`, `created_at`, `updated_at`, `etat`, `nbr_adulte`, `nbr_jeune`, `nbr_enfant`) VALUES
(1, 1, '03/07/2024', '03/07/2024', '2024-03-08 09:49:58', NULL, 'En Attente', 5, 5, 1),
(2, 1, '03/07/2024', '03/07/2024', '2024-03-08 09:51:53', NULL, 'En Attente', 5, 5, 1),
(3, 1, '03/07/2024', '03/07/2024', '2024-03-08 09:52:34', '2024-03-08 09:54:42', 'Confirmée', 5, 5, 1);

-- --------------------------------------------------------

--
-- Structure de la table `reset_password_request`
--
-- Erreur de lecture de structure pour la table adventure1.reset_password_request : #1932 - Table &#039;adventure1.reset_password_request&#039; doesn&#039;t exist in engine
-- Erreur de lecture des données pour la table adventure1.reset_password_request : #1064 - Erreur de syntaxe près de &#039;FROM `adventure1`.`reset_password_request`&#039; à la ligne 1

-- --------------------------------------------------------

--
-- Structure de la table `temporary_user`
--

CREATE TABLE `temporary_user` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `age` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Structure de la table `type_hebergement`
--

CREATE TABLE `type_hebergement` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) NOT NULL,
  `description` varchar(2000) NOT NULL,
  `created_at` datetime NOT NULL COMMENT '(DC2Type:datetime_immutable)',
  `updated_at` datetime DEFAULT NULL COMMENT '(DC2Type:datetime_immutable)'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `type_hebergement`
--

INSERT INTO `type_hebergement` (`id`, `nom`, `description`, `created_at`, `updated_at`) VALUES
(1, 'aaaaaaaaaaaaaaaaaaaaa', 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaa', '2024-03-08 09:42:58', NULL);

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `email` varchar(180) NOT NULL,
  `roles` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '(DC2Type:json)' CHECK (json_valid(`roles`)),
  `password` varchar(255) NOT NULL,
  `is_verified` tinyint(1) NOT NULL,
  `google_id` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`id`, `email`, `roles`, `password`, `is_verified`, `google_id`) VALUES
(1, 'cyrineboughzou0703@gmail.com', '[]', '$2y$13$LzzRGyAfoL31bhDMeulEt.nsOGdzJjgZhqgvUQZEIm96jCmQbt93q', 0, '112001939693569968281'),
(2, 'cyrine.boughzou@esprit.tn', '[]', 'fifi', 0, '101212573390796029730'),
(5, 'balkis@gmail.com', '{\"role\": \"user\"}', 'bibou', 0, '0'),
(7, 'test@gmail.com', '{\"role\": \"client\"}', 'test', 0, '0'),
(8, 'neila@gmail.com', '{\"role\": \"client\"}', 'pedro1', 1, '0'),
(10, 'nour@gmail.com', '{\"role\": \"client\"}', 'nour', 0, '0'),
(14, 'admin@gmail.com', '{\"role\": \"admin\"}', 'admin', 0, '0'),
(17, 'client@gmail.com', '{\"role\": \"client\"}', 'client', 0, '0'),
(20, 'youssef@gmail.com', '{\"role\": \"client\"}', 'azerty123', 0, '0'),
(21, 'med@gmail.com', '{\"role\": \"client\"}', 'qsdfgh', 0, '0'),
(24, 'elyes@gmail.com', '{\"role\": \"client\"}', 'Elyesse2001', 0, '0'),
(26, 'cycy@gmail.com', '{\"role\": \"client\"}', 'didoudidou2002', 0, '0'),
(28, 'mehdi.bouadlia@esprit.tn', '{\"role\": \"client\"}', 'mahdouch2001', 0, '0'),
(29, 'mehdi.bouadila@esprit.tn', '{\"role\": \"client\"}', 'mahdouch2001', 0, '0'),
(31, 'soua@gmail.com', '{\"role\": \"client\"}', 'Cristiano7+', 0, '0'),
(35, 'omar@gmail.com', '{\"role\": \"client\"}', 'azerty321+', 0, '0'),
(36, 'mohamed.soua@esprit', '{\"role\": \"client\"}', 'Cristiano7', 0, '0'),
(37, 'mohamed.soua@esprit.tn', '{\"role\": \"client\"}', 'CYRINE2002', 0, '0');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `activity`
--
ALTER TABLE `activity`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `activity_images`
--
ALTER TABLE `activity_images`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_B2695E6A81C06096` (`activity_id`);

--
-- Index pour la table `avis`
--
ALTER TABLE `avis`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_8F91ABF0DAE07E97` (`blog_id`);

--
-- Index pour la table `blog`
--
ALTER TABLE `blog`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `comment`
--
ALTER TABLE `comment`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_9474526C81C06096` (`activity_id`);

--
-- Index pour la table `commodite`
--
ALTER TABLE `commodite`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `doctrine_migration_versions`
--
ALTER TABLE `doctrine_migration_versions`
  ADD PRIMARY KEY (`version`);

--
-- Index pour la table `fav_activities`
--
ALTER TABLE `fav_activities`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_8B74E681C06096` (`activity_id`);

--
-- Index pour la table `hebergement`
--
ALTER TABLE `hebergement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_4852DD9C757826F2` (`type_hebergement_id`),
  ADD KEY `IDX_4852DD9C45CFF3EF` (`host_service_id`);

--
-- Index pour la table `host_service`
--
ALTER TABLE `host_service`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_75EA56E0FB7336F0` (`queue_name`),
  ADD KEY `IDX_75EA56E0E3BD61CE` (`available_at`),
  ADD KEY `IDX_75EA56E016BA31DB` (`delivered_at`);

--
-- Index pour la table `order`
--
ALTER TABLE `order`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `order_item`
--
ALTER TABLE `order_item`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_52EA1F094584665A` (`product_id`),
  ADD KEY `IDX_52EA1F09E238517C` (`order_ref_id`);

--
-- Index pour la table `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_D34A04AD12469DE2` (`category_id`);

--
-- Index pour la table `product_cat`
--
ALTER TABLE `product_cat`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `rent`
--
ALTER TABLE `rent`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_42C8495581C06096` (`activity_id`);

--
-- Index pour la table `reservation_hebergement`
--
ALTER TABLE `reservation_hebergement`
  ADD PRIMARY KEY (`id`),
  ADD KEY `IDX_843E00C023BB0F66` (`hebergement_id`);

--
-- Index pour la table `temporary_user`
--
ALTER TABLE `temporary_user`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `type_hebergement`
--
ALTER TABLE `type_hebergement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UNIQ_8D93D649E7927C74` (`email`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `activity`
--
ALTER TABLE `activity`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT pour la table `activity_images`
--
ALTER TABLE `activity_images`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT pour la table `avis`
--
ALTER TABLE `avis`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `blog`
--
ALTER TABLE `blog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT pour la table `comment`
--
ALTER TABLE `comment`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT pour la table `commodite`
--
ALTER TABLE `commodite`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `fav_activities`
--
ALTER TABLE `fav_activities`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=52;

--
-- AUTO_INCREMENT pour la table `hebergement`
--
ALTER TABLE `hebergement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `host_service`
--
ALTER TABLE `host_service`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `messenger_messages`
--
ALTER TABLE `messenger_messages`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `order`
--
ALTER TABLE `order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `order_item`
--
ALTER TABLE `order_item`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `product`
--
ALTER TABLE `product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `product_cat`
--
ALTER TABLE `product_cat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `rent`
--
ALTER TABLE `rent`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=59;

--
-- AUTO_INCREMENT pour la table `reservation_hebergement`
--
ALTER TABLE `reservation_hebergement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT pour la table `temporary_user`
--
ALTER TABLE `temporary_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `type_hebergement`
--
ALTER TABLE `type_hebergement`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `activity_images`
--
ALTER TABLE `activity_images`
  ADD CONSTRAINT `FK_B2695E6A81C06096` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `FK_8F91ABF0DAE07E97` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`);

--
-- Contraintes pour la table `comment`
--
ALTER TABLE `comment`
  ADD CONSTRAINT `FK_9474526C81C06096` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Contraintes pour la table `fav_activities`
--
ALTER TABLE `fav_activities`
  ADD CONSTRAINT `FK_8B74E681C06096` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`);

--
-- Contraintes pour la table `hebergement`
--
ALTER TABLE `hebergement`
  ADD CONSTRAINT `FK_4852DD9C45CFF3EF` FOREIGN KEY (`host_service_id`) REFERENCES `host_service` (`id`),
  ADD CONSTRAINT `FK_4852DD9C757826F2` FOREIGN KEY (`type_hebergement_id`) REFERENCES `type_hebergement` (`id`);

--
-- Contraintes pour la table `order_item`
--
ALTER TABLE `order_item`
  ADD CONSTRAINT `FK_52EA1F094584665A` FOREIGN KEY (`product_id`) REFERENCES `product` (`id`),
  ADD CONSTRAINT `FK_52EA1F09E238517C` FOREIGN KEY (`order_ref_id`) REFERENCES `order` (`id`);

--
-- Contraintes pour la table `product`
--
ALTER TABLE `product`
  ADD CONSTRAINT `FK_D34A04AD12469DE2` FOREIGN KEY (`category_id`) REFERENCES `product_cat` (`id`);

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `FK_42C8495581C06096` FOREIGN KEY (`activity_id`) REFERENCES `activity` (`id`);

--
-- Contraintes pour la table `reservation_hebergement`
--
ALTER TABLE `reservation_hebergement`
  ADD CONSTRAINT `FK_843E00C023BB0F66` FOREIGN KEY (`hebergement_id`) REFERENCES `hebergement` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
