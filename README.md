# Gerenciamento de Petshop - Paradigmas de Programação (UFMA)

Este projeto é uma aplicação de gerenciamento de petshop desenvolvida em Java, utilizando a biblioteca Swing para a interface gráfica de usuário (GUI). Ele foi criado como parte da terceira nota da disciplina de Paradigmas de Programação da Universidade Federal do Maranhão (UFMA), demonstrando conceitos como Programação Orientada a Objetos (POO), herança, polimorfismo, serialização de objetos e manipulação de coleções.

## Visão Geral

A aplicação permite o cadastro, alteração, exclusão e listagem de animais (cães e gatos) em um petshop. Os dados são persistidos em um arquivo binário (`animais.dat`) utilizando serialização, garantindo que as informações não sejam perdidas ao fechar a aplicação.

## Funcionalidades

- **Cadastro de Animais:** Adicione novos cães e gatos com informações como nome, idade, raça, e características específicas (porte para cães, castrado para gatos).
- **Listagem de Animais:** Visualize todos os animais cadastrados em uma tabela organizada.
- **Alteração de Dados:** Busque animais por ID e atualize suas informações.
- **Exclusão de Animais:** Remova animais do sistema utilizando seu ID.
- **Persistência de Dados:** Os dados dos animais são salvos automaticamente em um arquivo (`animais.dat`) e carregados ao iniciar a aplicação.
- **IDs Sequenciais:** Novos animais recebem IDs sequenciais para fácil identificação.

## Conceitos de Paradigmas de Programação Aplicados

### Programação Orientada a Objetos (POO)

- **Classes e Objetos:** `Animal`, `Cachorro`, `Gato`, `PetshopService`, `PetshopApp`, `CadastroPanel`, `AlteracaoPanel`, `ExclusaoPanel`.
- **Herança:** `Cachorro` e `Gato` herdam de `Animal`.
- **Polimorfismo:** O método `emitirSom()` é implementado de forma diferente em `Cachorro` e `Gato`.
- **Encapsulamento:** Atributos das classes são privados/protegidos com métodos getters e setters.
- **Abstração:** A classe `Animal` é abstrata.

### Serialização de Objetos

- Utiliza `ObjectOutputStream` e `ObjectInputStream` para salvar e carregar objetos `Animal` em arquivos.

### Coleções (Collections Framework)

- Utiliza `ArrayList` para armazenar e gerenciar a lista de objetos `Animal`.

### Interface Gráfica (Swing)

- Construção da GUI com componentes como `JFrame`, `JPanel`, `JTabbedPane`, `JTable`, `JTextField`, `JButton`, `JComboBox`, `JCheckBox`.

### Tratamento de Eventos

- Implementação de `ActionListener` para interações com os botões da GUI.

### Tratamento de Exceções

- Uso de blocos `try-catch` para lidar com erros de entrada de dados e operações de arquivo.

## Como Executar o Projeto

Para compilar e executar este projeto, você precisará ter o Java Development Kit (JDK) instalado em sua máquina.

Clone o repositório (ou baixe os arquivos):

```bash
<pre> ```git clone https://github.com/seu-usuario/PetshopApp.git``` </pre>
cd PetshopApp

<pre> ```bash git clone https://github.com/seu-usuario/PetshopApp.git cd PetshopApp ``` </pre>


