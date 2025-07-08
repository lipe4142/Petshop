#Gerenciamento de Petshop - Paradigmas de Programação (UFMA)#
Este projeto é uma aplicação de gerenciamento de petshop desenvolvida em Java, utilizando a biblioteca Swing para a interface gráfica de usuário (GUI). Ele foi criado como parte da terceira nota da disciplina de Paradigmas de Programação da Universidade Federal do Maranhão (UFMA), demonstrando conceitos como Programação Orientada a Objetos (POO), herança, polimorfismo, serialização de objetos e manipulação de coleções.

*Visão Geral*
A aplicação permite o cadastro, alteração, exclusão e listagem de animais (cães e gatos) em um petshop. Os dados são persistidos em um arquivo binário (animais.dat) utilizando serialização, garantindo que as informações não sejam perdidas ao fechar a aplicação.

*Funcionalidades*
Cadastro de Animais: Adicione novos cães e gatos com informações como nome, idade, raça, e características específicas (porte para cães, castrado para gatos).

Listagem de Animais: Visualize todos os animais cadastrados em uma tabela organizada.

Alteração de Dados: Busque animais por ID e atualize suas informações.

Exclusão de Animais: Remova animais do sistema utilizando seu ID.

Persistência de Dados: Os dados dos animais são salvos automaticamente em um arquivo (animais.dat) e carregados ao iniciar a aplicação.

IDs Sequenciais: Novos animais recebem IDs sequenciais para fácil identificação.

Conceitos de Paradigmas de Programação Aplicados
Programação Orientada a Objetos (POO):

Classes e Objetos: Animal, Cachorro, Gato, PetshopService, PetshopApp, CadastroPanel, AlteracaoPanel, ExclusaoPanel.

Herança: Cachorro e Gato herdam de Animal.

Polimorfismo: O método emitirSom() é implementado de forma diferente em Cachorro e Gato.

Encapsulamento: Atributos das classes são privados/protegidos com métodos getters e setters.

Abstração: A classe Animal é abstrata.

Serialização de Objetos: Utiliza ObjectOutputStream e ObjectInputStream para salvar e carregar objetos Animal em arquivos.

Coleções (Collections Framework): Utiliza ArrayList para armazenar e gerenciar a lista de objetos Animal.

Interface Gráfica (Swing): Construção da GUI com componentes como JFrame, JPanel, JTabbedPane, JTable, JTextField, JButton, JComboBox, JCheckBox.

Tratamento de Eventos: Implementação de ActionListener para interações com os botões da GUI.

Tratamento de Exceções: Uso de blocos try-catch para lidar com erros de entrada de dados e operações de arquivo.

Como Executar o Projeto
Para compilar e executar este projeto, você precisará ter o Java Development Kit (JDK) instalado em sua máquina.

Clone o repositório (ou baixe os arquivos):

git clone https://github.com/seu-usuario/PetshopApp.git
cd PetshopApp

Compile os arquivos Java:
Abra um terminal na pasta raiz do projeto e execute:

javac *.java

Execute a aplicação:

java PetshopApp

A janela da aplicação de gerenciamento de petshop será exibida.

Estrutura do Projeto
O projeto é composto pelos seguintes arquivos .java:

Animal.java: Classe abstrata base para todos os animais, define propriedades comuns e o método abstrato emitirSom().

Cachorro.java: Subclasse de Animal que representa um cachorro, com a propriedade porte.

Gato.java: Subclasse de Animal que representa um gato, com a propriedade castrado.

PetshopService.java: Classe de serviço que gerencia a lógica de negócio, incluindo adição, listagem, busca, atualização e remoção de animais, além da persistência de dados.

CadastroPanel.java: Painel da GUI para a funcionalidade de cadastro de novos animais.

AlteracaoPanel.java: Painel da GUI para a funcionalidade de alteração de dados de animais existentes.

ExclusaoPanel.java: Painel da GUI para a funcionalidade de exclusão de animais.

PetshopApp.java: Classe principal da aplicação, configura a janela principal (JFrame), as abas (JTabbedPane) e a tabela de exibição de dados (JTable).

**Tecnologias Utilizadas**
Linguagem: Java
GUI: Java Swing
Persistência: Serialização de Objetos Java
Contribuição
Este projeto foi desenvolvido para fins acadêmicos. Contribuições são bem-vindas, especialmente para melhorias na interface ou na robustez do código.
Autor
Joao Felipe Pereira Campos


Universidade Federal do Maranhão (UFMA)

Licença
Este projeto é licenciado sob a licença MIT. Veja o arquivo LICENSE para mais detalhes
