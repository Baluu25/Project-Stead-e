-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Gép: 127.0.0.1
-- Létrehozás ideje: 2026. Ápr 29. 20:08
-- Kiszolgáló verziója: 10.4.32-MariaDB
-- PHP verzió: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `stead-e`
--

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `achievements`
--

CREATE TABLE `achievements` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `threshold_value` bigint(20) NOT NULL DEFAULT 0,
  `icon` varchar(255) DEFAULT NULL,
  `achievement_type` enum('Streaks','Milestones','Nutrition','Fitness','Mindfulness','Study','Work') NOT NULL DEFAULT 'Milestones',
  `progress` int(11) NOT NULL DEFAULT 0,
  `unlocked_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `achievements`
--

INSERT INTO `achievements` (`id`, `user_id`, `name`, `description`, `threshold_value`, `icon`, `achievement_type`, `progress`, `unlocked_at`, `created_at`, `updated_at`) VALUES
(1, 1, 'First step', 'Complete your first day', 1, 'first_step.png', 'Streaks', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(2, 1, 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3, 'getting_warmed_up.png', 'Streaks', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(3, 1, 'Locked In', 'Reach a 7-day streak', 7, 'locked_in.png', 'Streaks', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(4, 1, 'Habit Formed', 'Complete a habit 10 times total', 10, 'habit_formed.png', 'Milestones', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(5, 1, 'On a Roll', 'Complete 50 habits total', 50, 'on_a_roll.png', 'Milestones', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(6, 1, 'Century Club', 'Complete 100 habits total', 100, 'century_club.png', 'Milestones', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(7, 1, 'First Bite', 'Log your first nutrition habit', 1, 'first_bite.png', 'Nutrition', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(9, 1, 'Clean Plate', 'Track all meals for 5 full days', 5, 'clean_plate.png', 'Nutrition', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(10, 1, 'First Sweat', 'Complete your first workout habit', 1, 'first_sweat.png', 'Fitness', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(11, 1, 'Weekly Warrior', 'Work out 3 times in one week', 3, 'weekly_warrior.png', 'Fitness', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(12, 1, 'Iron Will', 'Complete 30 fitness habits', 30, 'iron_will.png', 'Fitness', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(16, 1, 'First Focus', 'Log your first study session', 1, 'first_focus.png', 'Study', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(17, 1, 'Study Streak', 'Study 5 days in a row', 5, 'study_streak.png', 'Study', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(18, 1, 'Consistency Builder', 'Study 10 total hours', 10, 'consistency_builder.png', 'Study', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 16:08:56'),
(19, 1, 'First Task', 'Complete your first work task', 1, 'first_task.png', 'Work', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 15:03:56'),
(20, 1, 'Getting Productive', 'Complete 5 tasks', 5, 'getting_productive.png', 'Work', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 15:03:56'),
(21, 1, 'Work Warrior', 'Complete 10 tasks', 10, 'work_warrior.png', 'Work', 0, NULL, '2026-04-20 18:50:31', '2026-04-28 15:03:56'),
(22, 2, 'First step', 'Complete your first day', 1, 'first_step.png', 'Streaks', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(23, 2, 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3, 'getting_warmed_up.png', 'Streaks', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(24, 2, 'Locked In', 'Reach a 7-day streak', 7, 'locked_in.png', 'Streaks', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(25, 2, 'Habit Formed', 'Complete a habit 10 times total', 10, 'habit_formed.png', 'Milestones', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(26, 2, 'On a Roll', 'Complete 50 habits total', 50, 'on_a_roll.png', 'Milestones', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(27, 2, 'Century Club', 'Complete 100 habits total', 100, 'century_club.png', 'Milestones', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(28, 2, 'First Bite', 'Log your first nutrition habit', 1, 'first_bite.png', 'Nutrition', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(30, 2, 'Clean Plate', 'Track all meals for 5 full days', 5, 'clean_plate.png', 'Nutrition', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(31, 2, 'First Sweat', 'Complete your first workout habit', 1, 'first_sweat.png', 'Fitness', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(32, 2, 'Weekly Warrior', 'Work out 3 times in one week', 3, 'weekly_warrior.png', 'Fitness', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(33, 2, 'Iron Will', 'Complete 30 fitness habits', 30, 'iron_will.png', 'Fitness', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(37, 2, 'First Focus', 'Log your first study session', 1, 'first_focus.png', 'Study', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(38, 2, 'Study Streak', 'Study 5 days in a row', 5, 'study_streak.png', 'Study', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(39, 2, 'Consistency Builder', 'Study 10 total hours', 10, 'consistency_builder.png', 'Study', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(40, 2, 'First Task', 'Complete your first work task', 1, 'first_task.png', 'Work', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(41, 2, 'Getting Productive', 'Complete 5 tasks', 5, 'getting_productive.png', 'Work', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(42, 2, 'Work Warrior', 'Complete 10 tasks', 10, 'work_warrior.png', 'Work', 0, NULL, '2026-04-21 06:59:09', '2026-04-21 06:59:09'),
(66, 1, 'Fuel Up', 'Complete 7 nutrition habits', 7, 'fuel_up.png', 'Nutrition', 0, NULL, '2026-04-22 07:16:43', '2026-04-28 16:08:56'),
(67, 1, 'Calm Start', 'Complete your first mindfulness habit', 1, 'calm_start.png', 'Mindfulness', 0, NULL, '2026-04-22 07:16:43', '2026-04-28 15:03:56'),
(68, 1, 'Mind & Body', 'Complete 3 mindfulness habits', 3, 'mind_and_body.png', 'Mindfulness', 0, NULL, '2026-04-22 07:16:43', '2026-04-28 15:03:56'),
(69, 1, 'Well-Being', 'Complete 7 mindfulness habits', 7, 'well_being.png', 'Mindfulness', 0, NULL, '2026-04-22 07:16:43', '2026-04-28 15:03:56'),
(70, 2, 'Fuel Up', 'Complete 7 nutrition habits', 7, 'fuel_up.png', 'Nutrition', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(71, 2, 'Calm Start', 'Complete your first mindfulness habit', 1, 'calm_start.png', 'Mindfulness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(72, 2, 'Mind & Body', 'Complete 3 mindfulness habits', 3, 'mind_and_body.png', 'Mindfulness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(73, 2, 'Well-Being', 'Complete 7 mindfulness habits', 7, 'well_being.png', 'Mindfulness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(74, 3, 'First step', 'Complete your first day', 1, 'first_step.png', 'Streaks', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(75, 3, 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3, 'getting_warmed_up.png', 'Streaks', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(76, 3, 'Locked In', 'Reach a 7-day streak', 7, 'locked_in.png', 'Streaks', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(77, 3, 'Habit Formed', 'Complete a habit 10 times total', 10, 'habit_formed.png', 'Milestones', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(78, 3, 'On a Roll', 'Complete 50 habits total', 50, 'on_a_roll.png', 'Milestones', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(79, 3, 'Century Club', 'Complete 100 habits total', 100, 'century_club.png', 'Milestones', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(80, 3, 'First Bite', 'Log your first nutrition habit', 1, 'first_bite.png', 'Nutrition', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(81, 3, 'Clean Plate', 'Track all meals for 5 full days', 5, 'clean_plate.png', 'Nutrition', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(82, 3, 'Fuel Up', 'Complete 7 nutrition habits', 7, 'fuel_up.png', 'Nutrition', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(83, 3, 'First Sweat', 'Complete your first workout habit', 1, 'first_sweat.png', 'Fitness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(84, 3, 'Weekly Warrior', 'Work out 3 times in one week', 3, 'weekly_warrior.png', 'Fitness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(85, 3, 'Iron Will', 'Complete 30 fitness habits', 30, 'iron_will.png', 'Fitness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(86, 3, 'Calm Start', 'Complete your first mindfulness habit', 1, 'calm_start.png', 'Mindfulness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(87, 3, 'Mind & Body', 'Complete 3 mindfulness habits', 3, 'mind_and_body.png', 'Mindfulness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(88, 3, 'Well-Being', 'Complete 7 mindfulness habits', 7, 'well_being.png', 'Mindfulness', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(89, 3, 'First Focus', 'Log your first study session', 1, 'first_focus.png', 'Study', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(90, 3, 'Study Streak', 'Study 5 days in a row', 5, 'study_streak.png', 'Study', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(91, 3, 'Consistency Builder', 'Study 10 total hours', 10, 'consistency_builder.png', 'Study', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(92, 3, 'First Task', 'Complete your first work task', 1, 'first_task.png', 'Work', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(93, 3, 'Getting Productive', 'Complete 5 tasks', 5, 'getting_productive.png', 'Work', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(94, 3, 'Work Warrior', 'Complete 10 tasks', 10, 'work_warrior.png', 'Work', 0, NULL, '2026-04-26 17:54:25', '2026-04-26 17:54:25'),
(200, 9, 'First step', 'Complete your first day', 1, 'first_step.png', 'Streaks', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(201, 9, 'Getting Warmed Up', 'Keep your habit alive for 3 days straight.', 3, 'getting_warmed_up.png', 'Streaks', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(202, 9, 'Locked In', 'Reach a 7-day streak', 7, 'locked_in.png', 'Streaks', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(203, 9, 'Habit Formed', 'Complete a habit 10 times total', 10, 'habit_formed.png', 'Milestones', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(204, 9, 'On a Roll', 'Complete 50 habits total', 50, 'on_a_roll.png', 'Milestones', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(205, 9, 'Century Club', 'Complete 100 habits total', 100, 'century_club.png', 'Milestones', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(206, 9, 'First Bite', 'Log your first nutrition habit', 1, 'first_bite.png', 'Nutrition', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(207, 9, 'Clean Plate', 'Track all meals for 5 full days', 5, 'clean_plate.png', 'Nutrition', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(208, 9, 'Fuel Up', 'Complete 7 nutrition habits', 7, 'fuel_up.png', 'Nutrition', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(209, 9, 'First Sweat', 'Complete your first workout habit', 1, 'first_sweat.png', 'Fitness', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(210, 9, 'Weekly Warrior', 'Work out 3 times in one week', 3, 'weekly_warrior.png', 'Fitness', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(211, 9, 'Iron Will', 'Complete 30 fitness habits', 30, 'iron_will.png', 'Fitness', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(212, 9, 'Calm Start', 'Complete your first mindfulness habit', 1, 'calm_start.png', 'Mindfulness', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(213, 9, 'Mind & Body', 'Complete 3 mindfulness habits', 3, 'mind_and_body.png', 'Mindfulness', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(214, 9, 'Well-Being', 'Complete 7 mindfulness habits', 7, 'well_being.png', 'Mindfulness', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(215, 9, 'First Focus', 'Log your first study session', 1, 'first_focus.png', 'Study', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(216, 9, 'Study Streak', 'Study 5 days in a row', 5, 'study_streak.png', 'Study', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(217, 9, 'Consistency Builder', 'Study 10 total hours', 10, 'consistency_builder.png', 'Study', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(218, 9, 'First Task', 'Complete your first work task', 1, 'first_task.png', 'Work', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(219, 9, 'Getting Productive', 'Complete 5 tasks', 5, 'getting_productive.png', 'Work', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50'),
(220, 9, 'Work Warrior', 'Complete 10 tasks', 10, 'work_warrior.png', 'Work', 0, NULL, '2026-04-28 18:53:50', '2026-04-28 18:53:50');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `goals`
--

CREATE TABLE `goals` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text DEFAULT NULL,
  `icon` varchar(255) NOT NULL DEFAULT 'sports',
  `category` varchar(255) NOT NULL DEFAULT '',
  `target_value` int(11) NOT NULL,
  `current_value` int(11) NOT NULL DEFAULT 0,
  `unit` varchar(50) NOT NULL DEFAULT 'times',
  `deadline` date DEFAULT NULL,
  `status` enum('in-progress','completed','not-started') NOT NULL DEFAULT 'not-started',
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `goals`
--

INSERT INTO `goals` (`id`, `user_id`, `title`, `description`, `icon`, `category`, `target_value`, `current_value`, `unit`, `deadline`, `status`, `created_at`, `updated_at`) VALUES
(2, 1, 'Reading 100', 'Read a total of 100 pages of a book', 'menu_book', 'Study', 100, 0, 'pages', '2026-08-09', 'not-started', '2026-04-26 19:57:53', '2026-04-27 16:36:01'),
(3, 1, 'Gym', 'Go to the gym for 30 days', 'fitness_center', 'Fitness', 30, 0, 'days', NULL, 'not-started', '2026-04-27 06:29:52', '2026-04-27 16:20:19'),
(5, 9, 'Read 10 books', 'Read 10 books this year', 'book', 'learning', 10, 0, 'books', '2026-12-31', 'not-started', '2026-04-28 19:09:08', '2026-04-28 19:09:08');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `habits`
--

CREATE TABLE `habits` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `goal_id` bigint(20) UNSIGNED DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `category` varchar(255) NOT NULL,
  `frequency` enum('daily','weekly','monthly','custom') NOT NULL,
  `scheduled_days` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`scheduled_days`)),
  `target_count` bigint(20) NOT NULL DEFAULT 1,
  `unit` varchar(50) DEFAULT NULL,
  `icon` varchar(255) NOT NULL DEFAULT 'star',
  `is_active` tinyint(1) NOT NULL DEFAULT 1,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `habits`
--

INSERT INTO `habits` (`id`, `user_id`, `goal_id`, `name`, `description`, `category`, `frequency`, `scheduled_days`, `target_count`, `unit`, `icon`, `is_active`, `created_at`, `updated_at`, `deleted_at`) VALUES
(1, 1, NULL, 'Walk the dog', 'Walk the dog for 30 min', 'Fitness', 'daily', NULL, 1, 'times', 'fa-solid fa-dog', 1, '2026-04-21 07:45:32', '2026-04-27 18:55:08', '2026-04-27 16:55:08'),
(2, 1, NULL, 'Read 10 pages', 'Read a total of 10 pages', 'Study', 'weekly', '[1,5]', 10, 'custom', 'fa-solid fa-book-open', 1, '2026-04-24 17:06:05', '2026-04-26 18:01:46', '2026-04-26 16:01:46'),
(3, 3, NULL, 'Walk the dog', 'walk the dog for 30 min', 'Fitness', 'daily', NULL, 1, 'times', 'star', 1, '2026-04-25 22:26:10', '2026-04-25 22:26:10', NULL),
(5, 1, NULL, 'Drink 2 liters of water', 'Drink a total of 2 liters of water daily', 'Nutrition', 'daily', NULL, 1, NULL, '💧', 1, '2026-04-26 15:32:34', '2026-04-27 18:34:18', '2026-04-27 16:34:18'),
(6, 1, NULL, 'Read 10 pages', 'read a total of 10 pages of a book', 'Study', 'daily', NULL, 10, 'pages', 'fa-solid fa-book-open', 1, '2026-04-26 18:02:19', '2026-04-26 21:15:24', '2026-04-26 19:15:24'),
(7, 1, NULL, 'Workout', 'Workout session in the gym', 'Fitness', 'weekly', '[1,2,3]', 1, 'times', 'fa-solid fa-dumbbell', 1, '2026-04-27 09:21:19', '2026-04-27 09:21:19', NULL),
(8, 1, NULL, 'Read 10 pages', 'Read a total of 10 pages of a book', 'Study', 'daily', NULL, 10, 'pages', 'fa-solid fa-book-open', 1, '2026-04-27 09:22:23', '2026-04-27 09:22:23', NULL),
(9, 1, NULL, 'Drink water', 'Drink 2 liters of water', 'Nutrition', 'daily', NULL, 2, 'liters', 'fa-solid fa-droplet', 1, '2026-04-27 18:58:33', '2026-04-27 18:58:33', NULL),
(10, 9, NULL, 'Evening run', '30 min jog', 'Fitness', 'daily', NULL, 1, 'session', 'run', 1, '2026-04-28 20:59:21', '2026-04-28 21:06:01', '2026-04-28 19:06:01'),
(11, 9, NULL, 'Morning run', '30 min jog', 'Fitness', 'daily', NULL, 1, 'session', 'run', 1, '2026-04-28 21:09:34', '2026-04-28 21:09:34', NULL);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `habit_completions`
--

CREATE TABLE `habit_completions` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `completed_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `quantity` bigint(20) NOT NULL DEFAULT 1,
  `notes` text DEFAULT NULL,
  `mood` varchar(255) DEFAULT NULL,
  `is_skipped` tinyint(1) NOT NULL DEFAULT 0,
  `habit_id` bigint(20) UNSIGNED NOT NULL,
  `user_id` bigint(20) UNSIGNED NOT NULL,
  `deleted_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `habit_completions`
--

INSERT INTO `habit_completions` (`id`, `completed_at`, `quantity`, `notes`, `mood`, `is_skipped`, `habit_id`, `user_id`, `deleted_at`, `created_at`, `updated_at`) VALUES
(1, '2026-04-21 05:46:44', 1, NULL, NULL, 0, 1, 1, NULL, '2026-04-21 05:46:44', '2026-04-21 05:46:44'),
(2, '2026-04-22 16:21:29', 1, NULL, NULL, 0, 1, 1, NULL, '2026-04-22 16:21:29', '2026-04-22 16:21:29'),
(3, '2026-04-23 06:26:01', 1, NULL, NULL, 0, 1, 1, NULL, '2026-04-23 06:26:01', '2026-04-23 06:26:01'),
(4, '2026-04-24 15:06:10', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:10', '2026-04-24 15:06:10'),
(5, '2026-04-24 15:06:11', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:11', '2026-04-24 15:06:11'),
(6, '2026-04-24 15:06:12', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:12', '2026-04-24 15:06:12'),
(7, '2026-04-24 15:06:13', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:13', '2026-04-24 15:06:13'),
(8, '2026-04-24 15:06:13', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:13', '2026-04-24 15:06:13'),
(9, '2026-04-24 15:06:14', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:14', '2026-04-24 15:06:14'),
(10, '2026-04-24 15:06:14', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:14', '2026-04-24 15:06:14'),
(11, '2026-04-24 15:06:15', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:15', '2026-04-24 15:06:15'),
(12, '2026-04-24 15:06:16', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:16', '2026-04-24 15:06:16'),
(13, '2026-04-24 15:06:16', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-24 15:06:16', '2026-04-24 15:06:16'),
(14, '2026-04-24 15:06:18', 1, NULL, NULL, 0, 1, 1, NULL, '2026-04-24 15:06:18', '2026-04-24 15:06:18'),
(15, '2026-04-26 15:04:16', 1, NULL, NULL, 0, 5, 1, NULL, '2026-04-26 15:04:16', '2026-04-26 15:04:16'),
(16, '2026-04-26 15:04:19', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:19', '2026-04-26 15:04:19'),
(17, '2026-04-26 15:04:20', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:20', '2026-04-26 15:04:20'),
(18, '2026-04-26 15:04:21', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:21', '2026-04-26 15:04:21'),
(19, '2026-04-26 15:04:21', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:21', '2026-04-26 15:04:21'),
(20, '2026-04-26 15:04:21', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:21', '2026-04-26 15:04:21'),
(21, '2026-04-26 15:04:22', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:22', '2026-04-26 15:04:22'),
(22, '2026-04-26 15:04:22', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:22', '2026-04-26 15:04:22'),
(23, '2026-04-26 15:04:22', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:22', '2026-04-26 15:04:22'),
(24, '2026-04-26 15:04:22', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:22', '2026-04-26 15:04:22'),
(25, '2026-04-26 15:04:23', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:23', '2026-04-26 15:04:23'),
(26, '2026-04-26 15:04:23', 1, NULL, NULL, 0, 2, 1, NULL, '2026-04-26 15:04:23', '2026-04-26 15:04:23'),
(27, '2026-04-26 15:04:25', 1, NULL, NULL, 0, 1, 1, NULL, '2026-04-26 15:04:25', '2026-04-26 15:04:25'),
(28, '2026-04-26 18:02:26', 1, NULL, NULL, 0, 6, 1, '2026-04-26 16:02:26', '2026-04-26 16:02:25', '2026-04-26 16:02:26'),
(29, '2026-04-26 17:58:39', 1, NULL, NULL, 0, 6, 1, NULL, '2026-04-26 17:58:39', '2026-04-26 17:58:39'),
(30, '2026-04-26 17:58:47', 10, NULL, NULL, 0, 6, 1, NULL, '2026-04-26 17:58:47', '2026-04-26 17:58:47'),
(31, '2026-04-26 17:59:21', 1, NULL, NULL, 0, 6, 1, NULL, '2026-04-26 17:59:21', '2026-04-26 17:59:21'),
(32, '2026-04-26 18:41:04', 1, NULL, NULL, 0, 6, 1, NULL, '2026-04-26 18:41:04', '2026-04-26 18:41:04'),
(33, '2026-04-26 19:15:19', 1, NULL, NULL, 0, 6, 1, NULL, '2026-04-26 19:15:19', '2026-04-26 19:15:19'),
(34, '2026-04-27 07:20:27', 1, NULL, NULL, 0, 5, 1, NULL, '2026-04-27 07:20:27', '2026-04-27 07:20:27'),
(35, '2026-04-27 07:22:29', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 07:22:29', '2026-04-27 07:22:29'),
(36, '2026-04-27 07:22:30', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 07:22:30', '2026-04-27 07:22:30'),
(37, '2026-04-27 07:22:30', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 07:22:30', '2026-04-27 07:22:30'),
(38, '2026-04-27 07:22:31', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 07:22:31', '2026-04-27 07:22:31'),
(39, '2026-04-27 16:42:43', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 16:42:43', '2026-04-27 16:42:43'),
(40, '2026-04-27 16:42:44', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 16:42:44', '2026-04-27 16:42:44'),
(41, '2026-04-27 16:42:44', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 16:42:44', '2026-04-27 16:42:44'),
(42, '2026-04-27 16:42:45', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 16:42:45', '2026-04-27 16:42:45'),
(43, '2026-04-27 16:42:46', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 16:42:46', '2026-04-27 16:42:46'),
(44, '2026-04-27 16:42:47', 1, NULL, NULL, 0, 8, 1, NULL, '2026-04-27 16:42:47', '2026-04-27 16:42:47'),
(45, '2026-04-27 16:54:44', 1, NULL, NULL, 0, 7, 1, NULL, '2026-04-27 16:54:44', '2026-04-27 16:54:44'),
(46, '2026-04-27 16:54:45', 1, NULL, NULL, 0, 1, 1, NULL, '2026-04-27 16:54:45', '2026-04-27 16:54:45'),
(47, '2026-04-28 15:03:56', 1, NULL, NULL, 0, 7, 1, NULL, '2026-04-28 15:03:56', '2026-04-28 15:03:56');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `migrations`
--

CREATE TABLE `migrations` (
  `id` int(10) UNSIGNED NOT NULL,
  `migration` varchar(255) NOT NULL,
  `batch` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `migrations`
--

INSERT INTO `migrations` (`id`, `migration`, `batch`) VALUES
(1, '0001_01_01_000000_create_users_table', 1),
(2, '2026_02_04_195927_add_profile_fields_to_users_table', 1),
(3, '2026_02_12_173121_create_habits_table', 1),
(4, '2026_02_12_175211_create_habit_completions_table', 1),
(5, '2026_02_12_180457_create_achievements_table', 1),
(6, '2026_03_19_144427_create_personal_access_tokens_table', 1),
(7, '2026_04_02_094334_create_goals_table', 1),
(8, '2026_04_14_111841_add_goal_id_to_habits_table', 1),
(9, '2026_04_16_105922_add_unit_to_habits_table', 1),
(10, '2026_04_19_100106_add_scheduled_days_to_habits_table', 1),
(11, '2026_04_20_203044_add_preferred_categories_to_users_table', 2),
(12, '2026_04_21_125729_fix_profile_picture_and_gender_enum', 3),
(13, '2026_04_21_184835_update_achievement_icons', 4),
(14, '2026_04_21_190457_deduplicate_and_unique_achievements', 5),
(15, '2026_04_22_092033_remove_obsolete_achievements', 6),
(16, '2026_04_23_210514_is_admin_to_users_table', 7),
(17, '2026_04_24_204159_change_unit_column_in_goals_table', 8),
(18, '2026_04_27_000000_migrate_goal_icons_to_material', 9),
(19, '2026_04_28_000000_drop_physical_fields_from_users_table', 10);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `password_reset_tokens`
--

CREATE TABLE `password_reset_tokens` (
  `email` varchar(255) NOT NULL,
  `token` varchar(255) NOT NULL,
  `created_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `personal_access_tokens`
--

CREATE TABLE `personal_access_tokens` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `tokenable_type` varchar(255) NOT NULL,
  `tokenable_id` bigint(20) UNSIGNED NOT NULL,
  `name` text NOT NULL,
  `token` varchar(64) NOT NULL,
  `abilities` text DEFAULT NULL,
  `last_used_at` timestamp NULL DEFAULT NULL,
  `expires_at` timestamp NULL DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `personal_access_tokens`
--

INSERT INTO `personal_access_tokens` (`id`, `tokenable_type`, `tokenable_id`, `name`, `token`, `abilities`, `last_used_at`, `expires_at`, `created_at`, `updated_at`) VALUES
(1, 'App\\Models\\User', 3, 'android-app', '2bdeadff75e65bb2003c6dcd5edc80cd35b4e405799eab973e094b27dba6f8fc', '[\"*\"]', '2026-04-25 20:34:58', NULL, '2026-04-25 20:25:16', '2026-04-25 20:34:58'),
(2, 'App\\Models\\User', 4, 'android-app', 'dc663261ad7e469a7dd244a77aad005e7c253f47bcc49681cc7b06906e6c80b7', '[\"*\"]', NULL, NULL, '2026-04-26 08:38:31', '2026-04-26 08:38:31'),
(3, 'App\\Models\\User', 5, 'android-app', '387a5f5f8a5867b4d12a955c8240df88b94988b3f04e526e116a0ffbcfcce2c6', '[\"*\"]', NULL, NULL, '2026-04-26 08:38:44', '2026-04-26 08:38:44'),
(4, 'App\\Models\\User', 5, 'android-app', '217f07ca787712efc8dc1efa48dac41cad09e82a996884b12b644a8627be8416', '[\"*\"]', NULL, NULL, '2026-04-26 08:38:58', '2026-04-26 08:38:58'),
(5, 'App\\Models\\User', 1, 'android-app', '8abae676466fe15c4a81954df9f6960fbd7fe7fd0e4382f53d2a1ba3c3596a2b', '[\"*\"]', NULL, NULL, '2026-04-26 08:48:00', '2026-04-26 08:48:00'),
(7, 'App\\Models\\User', 5, 'android-app', '728eeeca3de3dbbfc70e377785a0cf4bdf8f8e0b7cb226229023294d773da7f0', '[\"*\"]', '2026-04-26 09:18:15', NULL, '2026-04-26 09:00:44', '2026-04-26 09:18:15'),
(8, 'App\\Models\\User', 1, 'android-app', 'ba8e3c0ad02a81de937d4f5aa2f1def69687d01729e992abfa61cddb07da0832', '[\"*\"]', '2026-04-28 16:08:59', NULL, '2026-04-28 16:08:56', '2026-04-28 16:08:59'),
(9, 'App\\Models\\User', 6, 'android-app', '5d02de1fb588b37a2ede1d6e58d3db82654951a8e2e275e320c8461fe63c5d7e', '[\"*\"]', NULL, NULL, '2026-04-28 17:37:34', '2026-04-28 17:37:34'),
(10, 'App\\Models\\User', 7, 'android-app', '22ef3a6dbd3c2a4219a442af2d5e1359e353f9fc8ddc6ee5a9b29b2444cc2d67', '[\"*\"]', NULL, NULL, '2026-04-28 18:12:32', '2026-04-28 18:12:32'),
(11, 'App\\Models\\User', 7, 'android-app', 'f0ac5f8fffa3106883fc443ba26864388593a86b5aefe02ee1c0fb0389ade533', '[\"*\"]', NULL, NULL, '2026-04-28 18:17:32', '2026-04-28 18:17:32'),
(12, 'App\\Models\\User', 8, 'android-app', '31a0273638c86f4bf9981ed21988f839df4ffa4bfd95f4e568ea08f0ff0ac854', '[\"*\"]', NULL, NULL, '2026-04-28 18:19:18', '2026-04-28 18:19:18'),
(13, 'App\\Models\\User', 8, 'android-app', 'bb5e7103e1fa4efaf9ad69bf2d256b7e4f03484a43d536f6c933f445f8a6f9c2', '[\"*\"]', '2026-04-28 18:44:39', NULL, '2026-04-28 18:20:12', '2026-04-28 18:44:39'),
(14, 'App\\Models\\User', 9, 'android-app', '11cda644cdfb4c987ebfaec5608e06238791976c173224790d93ca6c42a08e7d', '[\"*\"]', '2026-04-28 19:24:01', NULL, '2026-04-28 18:53:50', '2026-04-28 19:24:01'),
(15, 'App\\Models\\User', 9, 'android-app', 'e99ad4167ee6041e35b67c7b6c56b9f797ae512fd92ca4c8d3f53c484138370b', '[\"*\"]', NULL, NULL, '2026-04-28 18:54:30', '2026-04-28 18:54:30');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `sessions`
--

CREATE TABLE `sessions` (
  `id` varchar(255) NOT NULL,
  `user_id` bigint(20) UNSIGNED DEFAULT NULL,
  `ip_address` varchar(45) DEFAULT NULL,
  `user_agent` text DEFAULT NULL,
  `payload` longtext NOT NULL,
  `last_activity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `sessions`
--

INSERT INTO `sessions` (`id`, `user_id`, `ip_address`, `user_agent`, `payload`, `last_activity`) VALUES
('HbqUayHbHcmgvg3625kwLWPEDBbdD6BbKeoVvr2D', NULL, '127.0.0.1', 'Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:150.0) Gecko/20100101 Firefox/150.0', 'YTozOntzOjY6Il90b2tlbiI7czo0MDoiVmJJcjZ3N0pBMWtuTUlTOTdCZEZIaDY1bWIzWXdWRHdCaWRkTVFWNSI7czo5OiJfcHJldmlvdXMiO2E6Mjp7czozOiJ1cmwiO3M6Mjk6Imh0dHA6Ly8xMjcuMC4wLjE6ODAwMC9jb250YWN0IjtzOjU6InJvdXRlIjtzOjc6ImNvbnRhY3QiO31zOjY6Il9mbGFzaCI7YToyOntzOjM6Im9sZCI7YTowOnt9czozOiJuZXciO2E6MDp7fX19', 1777482560);

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `users`
--

CREATE TABLE `users` (
  `id` bigint(20) UNSIGNED NOT NULL,
  `name` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `profile_picture` varchar(255) DEFAULT NULL,
  `email` varchar(255) NOT NULL,
  `gender` enum('male','female','other') DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `user_goal` enum('weight_loss','consistency','quit_habit','explore') DEFAULT NULL,
  `preferred_categories` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL CHECK (json_valid(`preferred_categories`)),
  `is_admin` tinyint(1) NOT NULL DEFAULT 0,
  `email_verified_at` timestamp NULL DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `remember_token` varchar(100) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT NULL,
  `updated_at` timestamp NULL DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- A tábla adatainak kiíratása `users`
--

INSERT INTO `users` (`id`, `name`, `username`, `profile_picture`, `email`, `gender`, `birthdate`, `user_goal`, `preferred_categories`, `is_admin`, `email_verified_at`, `password`, `remember_token`, `created_at`, `updated_at`) VALUES
(1, 'John Doe', 'john24', 'profile_pictures/F6pKocSPARtbcdskatRUMfCNXpaaM90d2zGuZRUJ.png', 'johndoe@gmail.com', 'male', '1999-02-13', 'consistency', '[\"Fitness\"]', 0, NULL, '$2y$12$qwW7IgwmRH98rSUww/kNe.LXfguIE2qdw9.xVb/mKoBYgdDWt7KZe', NULL, '2026-04-20 18:50:30', '2026-04-28 15:03:33'),
(2, 'Admin', 'Admin', NULL, 'dudasbalazs1@gmail.com', NULL, NULL, 'explore', '[\"Fitness\"]', 1, NULL, '$2y$12$2zRO.Q/3E6fbJB.EKLx3hO2NKgdG086udsOk5GSIeOK/5PPv26wWa', NULL, '2026-04-21 06:59:09', '2026-04-23 19:36:14'),
(3, 'Dudas Balazs', 'Baluu', NULL, 'dudasbalazs2@gmail.com', NULL, NULL, NULL, NULL, 1, NULL, '$2y$12$.JuBsIdQSwMFPOoxKQx9XeGqKoscRGBXW5ABCKSHx3h7KKSgPVxJa', NULL, '2026-04-25 20:25:15', '2026-04-28 18:52:59'),
(9, 'Test Elek', 'testelek', NULL, 'test@elek.com', 'male', '2000-01-01', 'consistency', '[\"Fitness\",\"Nutrition\"]', 0, NULL, '$2y$12$KovJkWfZXSbEP0Tnr0mHSOUNdLJHaCV8kmBxHZ9MI07aHMn.vdVcK', NULL, '2026-04-28 18:53:50', '2026-04-28 19:24:01');

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `achievements`
--
ALTER TABLE `achievements`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `achievements_user_id_name_unique` (`user_id`,`name`);

--
-- A tábla indexei `goals`
--
ALTER TABLE `goals`
  ADD PRIMARY KEY (`id`),
  ADD KEY `goals_user_id_foreign` (`user_id`);

--
-- A tábla indexei `habits`
--
ALTER TABLE `habits`
  ADD PRIMARY KEY (`id`),
  ADD KEY `habits_user_id_foreign` (`user_id`),
  ADD KEY `habits_goal_id_foreign` (`goal_id`);

--
-- A tábla indexei `habit_completions`
--
ALTER TABLE `habit_completions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `habit_completions_habit_id_foreign` (`habit_id`),
  ADD KEY `habit_completions_user_id_foreign` (`user_id`);

--
-- A tábla indexei `migrations`
--
ALTER TABLE `migrations`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `password_reset_tokens`
--
ALTER TABLE `password_reset_tokens`
  ADD PRIMARY KEY (`email`);

--
-- A tábla indexei `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `personal_access_tokens_token_unique` (`token`),
  ADD KEY `personal_access_tokens_tokenable_type_tokenable_id_index` (`tokenable_type`,`tokenable_id`),
  ADD KEY `personal_access_tokens_expires_at_index` (`expires_at`);

--
-- A tábla indexei `sessions`
--
ALTER TABLE `sessions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `sessions_user_id_index` (`user_id`),
  ADD KEY `sessions_last_activity_index` (`last_activity`);

--
-- A tábla indexei `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `users_email_unique` (`email`),
  ADD UNIQUE KEY `users_username_unique` (`username`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `achievements`
--
ALTER TABLE `achievements`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=221;

--
-- AUTO_INCREMENT a táblához `goals`
--
ALTER TABLE `goals`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT a táblához `habits`
--
ALTER TABLE `habits`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT a táblához `habit_completions`
--
ALTER TABLE `habit_completions`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=48;

--
-- AUTO_INCREMENT a táblához `migrations`
--
ALTER TABLE `migrations`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT a táblához `personal_access_tokens`
--
ALTER TABLE `personal_access_tokens`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT a táblához `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- Megkötések a kiírt táblákhoz
--

--
-- Megkötések a táblához `achievements`
--
ALTER TABLE `achievements`
  ADD CONSTRAINT `achievements_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Megkötések a táblához `goals`
--
ALTER TABLE `goals`
  ADD CONSTRAINT `goals_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Megkötések a táblához `habits`
--
ALTER TABLE `habits`
  ADD CONSTRAINT `habits_goal_id_foreign` FOREIGN KEY (`goal_id`) REFERENCES `goals` (`id`) ON DELETE SET NULL,
  ADD CONSTRAINT `habits_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;

--
-- Megkötések a táblához `habit_completions`
--
ALTER TABLE `habit_completions`
  ADD CONSTRAINT `habit_completions_habit_id_foreign` FOREIGN KEY (`habit_id`) REFERENCES `habits` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `habit_completions_user_id_foreign` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
