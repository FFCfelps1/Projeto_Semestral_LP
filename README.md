# **Projeto Semestral - Quiz Interativo (Inspirado no Kahoot)**

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

Bem-vindo ao repositório do nosso projeto semestral! Este sistema é um **quiz interativo**, inspirado no **Kahoot**, desenvolvido inteiramente em **Java** com suporte a **banco de dados MySQL**. O objetivo é criar uma plataforma divertida e educativa, com foco na interação entre professor e alunos em sala de aula.

---

## 📋 **Sumário**
- [Descrição do Projeto](#descrição-do-projeto)
- [Funcionalidades](#funcionalidades)
- [Tecnologias Utilizadas](#tecnologias-utilizadas)
- [Modelagem do Banco de Dados](#modelagem-do-banco-de-dados)
- [Como Executar](#como-executar)
- [Equipe](#equipe)

---

## 📝 **Descrição do Projeto**

Este sistema permite que um **professor crie quizzes** e que os **alunos participem ao vivo** respondendo as perguntas via aplicativo desktop. Cada participante recebe pontuação baseada na **velocidade e acertos**. Ao final, os resultados são exibidos em um ranking.

Nosso foco é desenvolver um sistema **multiplayer, simples e interativo**, aproximando a experiência do Kahoot, mas 100% feito em Java.

---

## 🚀 **Funcionalidades**

✅ Cadastro de usuários (professor e aluno)  
✅ Login de usuário com autenticação via MySQL  
✅ Criação de quizzes com múltiplas perguntas e alternativas  
✅ Execução de quizzes em tempo real  
✅ Interface gráfica amigável (Java Swing/JavaFX)  
✅ Controle de tempo para cada pergunta  
✅ Sistema de pontuação automática  
✅ Ranking final com os melhores colocados  
✅ Persistência de dados no banco MySQL  

---

## 🛠 **Tecnologias Utilizadas**

- ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat&logo=java&logoColor=white) **Java** (versão X.X) – Backend e Frontend
- ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=flat&logo=mysql&logoColor=white) **MySQL** (versão X.X) – Banco de dados
- **JDBC** – Conexão Java ↔ MySQL
- **(JavaFX ou Swing)** – Interface gráfica (a definir/conforme projeto)
- **Maven/Gradle** – Gerenciamento de dependências (se aplicável)

---

## 🗄 **Modelagem do Banco de Dados** (resumo)

| Tabela        | Campos principais                     |
|---------------|-------------------------------------|
| `usuarios`     | id_usuario, nome, email, senha, tipo (professor/aluno) |
| `quizzes`      | id_quiz, titulo, id_professor       |
| `perguntas`    | id_pergunta, texto, id_quiz         |
| `alternativas` | id_alternativa, texto, correta, id_pergunta |
| `respostas`    | id_resposta, id_aluno, id_pergunta, id_alternativa, tempo_resposta |

*(Diagrama completo será adicionado na pasta `/docs`)*

---

## 💻 **Como Executar**

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/nome-do-repositorio.git
```
