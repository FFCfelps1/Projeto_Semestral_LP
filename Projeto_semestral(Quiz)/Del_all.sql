-- Seleciona o banco de dados
USE quizdb;

-- Apaga todas as tabelas do banco de dados
DROP TABLE IF EXISTS quiz_questions;
DROP TABLE IF EXISTS quizzes;
DROP TABLE IF EXISTS results;
DROP TABLE IF EXISTS questions;
DROP TABLE IF EXISTS users;

-- Apaga o banco de dados
DROP DATABASE IF EXISTS quizdb;