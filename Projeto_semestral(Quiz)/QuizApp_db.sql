CREATE DATABASE IF NOT EXISTS quizdb;
USE quizdb;

-- DROP TABLE users;
CREATE TABLE users (
    name VARCHAR(255) PRIMARY KEY,
    senha VARCHAR(255) NOT NULL,
    score INT DEFAULT 0
);

DROP TABLE IF EXISTS questions;

CREATE TABLE questions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    optionA VARCHAR(100) NOT NULL,
    optionB VARCHAR(100) NOT NULL,
    optionC VARCHAR(100) NOT NULL,
    optionD VARCHAR(100) NOT NULL,
    correctOption INT NOT NULL
);

INSERT INTO questions (question, optionA, optionB, optionC, optionD, correctOption) VALUES
('Qual é a capital da França?', 'Paris', 'Londres', 'Roma', 'Berlim', 0),
('Qual é o maior planeta do sistema solar?', 'Terra', 'Marte', 'Júpiter', 'Saturno', 2),
('Quem escreveu "Dom Quixote"?', 'Machado de Assis', 'Miguel de Cervantes', 'José de Alencar', 'Eça de Queirós', 1),
('Qual é o elemento químico representado pelo símbolo "O"?', 'Ouro', 'Oxigênio', 'Ozônio', 'Osmium', 1),
('Quem pintou a Mona Lisa?', 'Vincent van Gogh', 'Michelangelo', 'Leonardo da Vinci', 'Pablo Picasso', 2),
('Em que ano o homem pisou na Lua pela primeira vez?', '1965', '1969', '1972', '1980', 1),
('Qual é o animal terrestre mais rápido do mundo?', 'Leopardo', 'Guepardo', 'Tigre', 'Cavalo', 1),
('Qual é o menor país do mundo?', 'Mônaco', 'Malta', 'Vaticano', 'Liechtenstein', 2),
('Qual linguagem é usada para desenvolver aplicativos Android nativos?', 'Java', 'Python', 'Swift', 'Kotlin', 0),
('Quem desenvolveu a Teoria da Relatividade?', 'Isaac Newton', 'Nikola Tesla', 'Albert Einstein', 'Stephen Hawking', 2);

INSERT INTO questions (question, optionA, optionB, optionC, optionD, correctOption) VALUES
('Qual é o rio mais longo do mundo?', 'Amazonas', 'Nilo', 'Yangtzé', 'Mississipi', 1),
('Quem foi o primeiro presidente do Brasil?', 'Getúlio Vargas', 'Marechal Deodoro da Fonseca', 'Juscelino Kubitschek', 'Dom Pedro II', 1),
('Qual é o continente com mais países?', 'África', 'Ásia', 'Europa', 'América', 0),
('Qual é a fórmula da água?', 'H2O', 'CO2', 'NaCl', 'O2', 0),
('Em que país se originou o futebol?', 'Alemanha', 'Brasil', 'Inglaterra', 'Espanha', 2),
('Quantos planetas há no sistema solar?', '7', '8', '9', '10', 1),
('Qual é a moeda oficial do Japão?', 'Yuan', 'Won', 'Iene', 'Rupia', 2),
('Quem pintou o teto da Capela Sistina?', 'Donatello', 'Michelangelo', 'Rafael', 'Leonardo da Vinci', 1),
('Qual é a montanha mais alta do mundo?', 'K2', 'Everest', 'Mont Blanc', 'Aconcágua', 1),
('Em que continente está o deserto do Saara?', 'Ásia', 'América', 'África', 'Europa', 2),
('Qual é o maior oceano do planeta?', 'Atlântico', 'Índico', 'Ártico', 'Pacífico', 3),
('Qual é o nome do satélite natural da Terra?', 'Sol', 'Marte', 'Lua', 'Estrela', 2),
('Quem descobriu o Brasil?', 'Pedro Álvares Cabral', 'Cristóvão Colombo', 'Vasco da Gama', 'Fernão de Magalhães', 0),
('Quantos lados tem um quadrado?', '3', '4', '5', '6', 1),
('Qual é o plural de “pão”?', 'Pões', 'Pães', 'Pãos', 'Pãez', 1),
('Quem escreveu "O Pequeno Príncipe"?', 'Antoine de Saint-Exupéry', 'J.K. Rowling', 'Paulo Coelho', 'Monteiro Lobato', 0),
('Qual é o símbolo químico do ouro?', 'Au', 'Ag', 'O', 'Fe', 0),
('Qual é o resultado de 7 x 8?', '54', '56', '58', '64', 1),
('Qual desses animais é um mamífero?', 'Jacaré', 'Tubarão', 'Golfinho', 'Pato', 2),
('Qual país tem o formato de uma bota?', 'Brasil', 'Itália', 'França', 'Austrália', 1),
('Quem inventou a lâmpada elétrica?', 'Einstein', 'Newton', 'Thomas Edison', 'Tesla', 2),
('Qual é a cor da clorofila?', 'Azul', 'Vermelha', 'Verde', 'Amarela', 2),
('Qual planeta é conhecido como o "planeta vermelho"?', 'Mercúrio', 'Vênus', 'Marte', 'Júpiter', 2),
('Quem escreveu "A Odisséia"?', 'Sófocles', 'Aristóteles', 'Homero', 'Platão', 2),
('Qual é o maior mamífero do mundo?', 'Elefante', 'Hipopótamo', 'Baleia azul', 'Rinoceronte', 2),
('Qual a capital do Japão?', 'Pequim', 'Tóquio', 'Seul', 'Bangkok', 1),
('Quantas cores tem o arco-íris?', '5', '6', '7', '8', 2),
('Qual é o idioma oficial da Argentina?', 'Português', 'Espanhol', 'Francês', 'Inglês', 1),
('Quem foi o criador do Facebook?', 'Bill Gates', 'Steve Jobs', 'Mark Zuckerberg', 'Elon Musk', 2),
('Qual desses animais é um réptil?', 'Cobra', 'Cavalo', 'Tubarão', 'Gato', 0),
('Em que continente fica o Brasil?', 'Ásia', 'América do Sul', 'Europa', 'África', 1),
('Qual é o número pi (π) com duas casas decimais?', '3.12', '3.14', '3.15', '3.16', 1),
('Qual desses é um instrumento de corda?', 'Violão', 'Pandeiro', 'Flauta', 'Bateria', 0),
('Quem foi Albert Einstein?', 'Médico', 'Engenheiro', 'Cientista', 'Artista', 2),
('Em que estado brasileiro fica o Cristo Redentor?', 'São Paulo', 'Bahia', 'Rio de Janeiro', 'Minas Gerais', 2),
('Qual é o coletivo de lobos?', 'Alcateia', 'Manada', 'Bando', 'Rebanho', 0),
('Qual é o nome do processo pelo qual as plantas produzem seu alimento?', 'Respiração', 'Digestão', 'Fotossíntese', 'Transpiração', 2),
('Quantos segundos há em um minuto?', '30', '60', '90', '120', 1),
('Quem é conhecido como o "Pai da Computação"?', 'Alan Turing', 'Bill Gates', 'Steve Jobs', 'Tim Berners-Lee', 0),
('Qual é a capital da Austrália?', 'Sydney', 'Melbourne', 'Perth', 'Canberra', 3);



select * from questions;
select * from users; 
DESCRIBE questions;

-- DROP TABLE quizzes;
CREATE TABLE IF NOT EXISTS quizzes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- Tabela de associação quiz-pergunta
CREATE TABLE IF NOT EXISTS quiz_questions (
    quiz_id INT NOT NULL,
    question_id INT NOT NULL,
    FOREIGN KEY (quiz_id) REFERENCES quizzes(id),
    FOREIGN KEY (question_id) REFERENCES questions(id)
);

CREATE TABLE results (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_name VARCHAR(255) NOT NULL,
    quiz_name VARCHAR(255) NOT NULL,
    score INT NOT NULL
);


select * from quizzes;
select * from results;