#  Sistema de Biblioteca Escolar

Este é um aplicativo mobile desenvolvido em **Java** com **Android Studio** e integração com **Firebase**. O sistema permite o **cadastro, consulta, empréstimo e devolução de livros**, além de manter um histórico completo das movimentações.

##  Funcionalidades

 Cadastro de livros com os seguintes campos:
- Título
- Autor
- Gênero
- Ano
- ID do aluno associado (quando "Lendo")
- Datas: Empréstimo, Devolução prevista, Devolução real

 Cadastro de alunos com os seguintes campos:
- Nome
- Série
- ID (gerado automaticamente)

 Listagem:
- Livros: por título, autor, gênero, ano e status
- Alunos: por nome, série, ID e status de leitura

 Fluxos de:
- Empréstimo de livros: com atualização automática das datas e status
- Devolução de livros: com registro da data real de devolução
- Histórico de movimentações: visualização completa de livros devolvidos

##  Estrutura do Projeto

- **Firebase Realtime Database / Firestore**: Armazenamento das informações de livros e alunos.
- **Firebase Storage**: Armazenamento das capas dos livros.
- **Java**: Lógica de programação e integração com Firebase.
- **Android Studio**: Ambiente de desenvolvimento.

##  Diagramas do Projeto

-  Fluxogramas de Empréstimo e Devolução
-  Diagramas de Atividades
-  Diagrama de Histórico

Todos os diagramas estão disponíveis no repositorio

## Tecnologias Utilizadas

- Java
- Android Studio
- Firebase Realtime Database ou Firestore
- Firebase Storage
- Material Design para interface

##  Fluxo de Empréstimo

1. Seleciona livro disponível.
2. Seleciona aluno.
3. Atualiza status do livro para "Lendo".
4. Registra data de empréstimo (data atual).
5. Define data prevista de devolução (+7 dias).
6. Atualiza status de leitura do aluno com ID do livro.

##  Fluxo de Devolução

1. Seleciona livro com status "Lendo".
2. Confirma devolução.
3. Atualiza status do livro para "Disponível".
4. Remove ID do aluno associado.
5. Registra data real de devolução.
6. Atualiza status do aluno para sem empréstimo.

##  Histórico

- Exibe lista de livros que já foram emprestados e devolvidos.
- Mostra: título, nome do aluno, datas de empréstimo, prevista e real de devolução.

##  Como executar

1. Clone este repositório.
2. Abra no Android Studio.
3. Configure o Firebase no projeto.
4. Compile e execute no emulador ou dispositivo real.

##  Autor

- Desenvolvido por [Kalled Abdala - Ra: 234846]

---

