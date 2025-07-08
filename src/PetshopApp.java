import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
// import java.util.UUID; // Não será mais usado para gerar novos IDs

/**
 * Classe base abstrata para representar um Animal.
 * Demonstra o conceito de herança.
 */
abstract class Animal implements Serializable {
    private static final long serialVersionUID = 1L; // Necessário para serialização
    protected String id; // Alterado para protected para que PetshopService possa definir
    private String nome;
    private int idade;
    private String raca;

    // Construtor para novos animais (ID será atribuído pelo serviço)
    public Animal(String nome, int idade, String raca) {
        this.nome = nome;
        this.idade = idade;
        this.raca = raca;
        this.id = null; // ID será definido pelo PetshopService
    }

    // Construtor para carregar animais existentes (com ID já definido)
    public Animal(String id, String nome, int idade, String raca) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.raca = raca;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdade() {
        return idade;
    }

    public String getRaca() {
        return raca;
    }

    // Setters
    public void setId(String id) { // Adicionado setter para ID
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    /**
     * Método abstrato para demonstrar polimorfismo.
     * Cada subclasse implementará sua própria versão.
     */
    public abstract String emitirSom();

    @Override
    public String toString() {
        return "ID: " + id + ", Nome: " + nome + ", Idade: " + idade + ", Raça: " + raca;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(id, animal.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/**
 * Subclasse de Animal para representar um Cachorro.
 * Demonstra herança e polimorfismo.
 */
class Cachorro extends Animal {
    private static final long serialVersionUID = 1L;
    private String porte;

    // Construtor para novos cachorros
    public Cachorro(String nome, int idade, String raca, String porte) {
        super(nome, idade, raca);
        this.porte = porte;
    }

    // Construtor para carregar cachorros existentes
    public Cachorro(String id, String nome, int idade, String raca, String porte) {
        super(id, nome, idade, raca);
        this.porte = porte;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    @Override
    public String emitirSom() {
        return "Au Au!";
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Cachorro, Porte: " + porte + ", Som: " + emitirSom();
    }
}

/**
 * Subclasse de Animal para representar um Gato.
 * Demonstra herança e polimorfismo.
 */
class Gato extends Animal {
    private static final long serialVersionUID = 1L;
    private boolean castrado;

    // Construtor para novos gatos
    public Gato(String nome, int idade, String raca, boolean castrado) {
        super(nome, idade, raca);
        this.castrado = castrado;
    }

    // Construtor para carregar gatos existentes
    public Gato(String id, String nome, int idade, String raca, boolean castrado) {
        super(id, nome, idade, raca);
        this.castrado = castrado;
    }

    public boolean isCastrado() {
        return castrado;
    }

    public void setCastrado(boolean castrado) {
        this.castrado = castrado;
    }

    @Override
    public String emitirSom() {
        return "Miau!";
    }

    @Override
    public String toString() {
        return super.toString() + ", Tipo: Gato, Castrado: " + (castrado ? "Sim" : "Não") + ", Som: " + emitirSom();
    }
}

/**
 * Classe de serviço para gerenciar os animais do petshop.
 * Utiliza Collections (ArrayList) para armazenar os objetos Animal.
 */
class PetshopService {
    private List<Animal> animais;
    private final String ARQUIVO_DADOS = "animais.dat";
    private int nextSequentialId; // Contador para IDs sequenciais

    public PetshopService() {
        this.animais = new ArrayList<>();
        this.nextSequentialId = 1; // Inicia o contador de IDs sequenciais
        carregarDados(); // Carrega os dados ao iniciar o serviço
    }

    /**
     * Adiciona um novo animal à lista.
     * Atribui um ID sequencial se o animal ainda não tiver um (para novas entradas).
     * @param animal O objeto Animal a ser adicionado.
     */
    public void adicionarAnimal(Animal animal) {
        // Atribui um ID sequencial apenas se o animal não tiver um ID válido (ex: se foi carregado de um arquivo)
        if (animal.getId() == null || animal.getId().isEmpty() || !isNumeric(animal.getId())) {
            animal.setId(String.valueOf(nextSequentialId++));
        }
        animais.add(animal);
        salvarDados(); // Salva os dados após adicionar
    }

    /**
     * Lista todos os animais cadastrados.
     * @return Uma lista de objetos Animal.
     */
    public List<Animal> listarAnimais() {
        return new ArrayList<>(animais); // Retorna uma cópia para evitar modificações externas diretas
    }

    /**
     * Busca um animal pelo seu ID.
     * @param id O ID do animal a ser buscado.
     * @return O objeto Animal encontrado ou null se não for encontrado.
     */
    public Animal buscarAnimalPorId(String id) {
        for (Animal animal : animais) {
            if (animal.getId().equals(id)) {
                return animal;
            }
        }
        return null;
    }

    /**
     * Atualiza os dados de um animal existente.
     * @param animalAtualizado O objeto Animal com os dados atualizados.
     * @return true se o animal foi atualizado com sucesso, false caso contrário.
     */
    public boolean atualizarAnimal(Animal animalAtualizado) {
        for (int i = 0; i < animais.size(); i++) {
            if (animais.get(i).getId().equals(animalAtualizado.getId())) {
                animais.set(i, animalAtualizado);
                salvarDados(); // Salva os dados após atualizar
                return true;
            }
        }
        return false;
    }

    /**
     * Remove um animal da lista pelo seu ID.
     * @param id O ID do animal a ser removido.
     * @return true se o animal foi removido com sucesso, false caso contrário.
     */
    public boolean removerAnimal(String id) {
        boolean removido = animais.removeIf(animal -> animal.getId().equals(id));
        if (removido) {
            salvarDados(); // Salva os dados após remover
        }
        return removido;
    }

    /**
     * Salva a lista de animais em um arquivo usando serialização.
     */
    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARQUIVO_DADOS))) {
            oos.writeObject(animais);
            System.out.println("Dados salvos com sucesso em " + ARQUIVO_DADOS);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar dados: " + e.getMessage(), "Erro de E/S", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Carrega a lista de animais de um arquivo usando desserialização.
     * Também atualiza o contador de IDs sequenciais com base nos IDs existentes.
     */
    @SuppressWarnings("unchecked") // Suprime o aviso de tipo não verificado para o cast
    private void carregarDados() {
        File arquivo = new File(ARQUIVO_DADOS);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ARQUIVO_DADOS))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    this.animais = (List<Animal>) obj;
                    System.out.println("Dados carregados com sucesso de " + ARQUIVO_DADOS);

                    // Encontra o maior ID numérico existente para continuar a sequência
                    int maxId = 0;
                    for (Animal animal : animais) {
                        if (isNumeric(animal.getId())) { // Apenas considera IDs numéricos para o contador
                            try {
                                int currentId = Integer.parseInt(animal.getId());
                                if (currentId > maxId) {
                                    maxId = currentId;
                                }
                            } catch (NumberFormatException e) {
                                // Ignora IDs não numéricos (como os antigos UUIDs) para o cálculo do maxId
                            }
                        }
                    }
                    nextSequentialId = maxId + 1;
                    System.out.println("Próximo ID sequencial disponível: " + nextSequentialId);
                }
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erro ao carregar dados: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao carregar dados: " + e.getMessage(), "Erro de E/S", JOptionPane.ERROR_MESSAGE);
                this.animais = new ArrayList<>(); // Inicializa com lista vazia em caso de erro
                this.nextSequentialId = 1; // Reseta o contador
            }
        } else {
            System.out.println("Arquivo de dados não encontrado. Iniciando com lista vazia.");
            this.animais = new ArrayList<>();
            this.nextSequentialId = 1; // Inicia do 1 se não houver arquivo
        }
    }

    /**
     * Verifica se uma string pode ser convertida para um número inteiro.
     * @param str A string a ser verificada.
     * @return true se a string é numérica, false caso contrário.
     */
    private boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("-?\\d+"); // Regex para números inteiros (opcionalmente negativos)
    }
}

/**
 * Painel para o cadastro de novos animais.
 */
class CadastroPanel extends JPanel {
    private JTextField txtNome, txtIdade, txtRaca, txtPorte;
    private JCheckBox chkCastrado;
    private JComboBox<String> cmbTipoAnimal;
    private PetshopService service;
    private JTable tabelaAnimais;
    private DefaultTableModel tableModel;

    public CadastroPanel(PetshopService service, JTable tabelaAnimais, DefaultTableModel tableModel) {
        this.service = service;
        this.tabelaAnimais = tabelaAnimais;
        this.tableModel = tableModel;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Cadastrar Novo Animal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1; // Reset gridwidth

        // Nome
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        add(txtNome, gbc);

        // Idade
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        txtIdade = new JTextField(20);
        add(txtIdade, gbc);

        // Raça
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Raça:"), gbc);
        gbc.gridx = 1;
        txtRaca = new JTextField(20);
        add(txtRaca, gbc);

        // Tipo de Animal (Cachorro/Gato)
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Tipo de Animal:"), gbc);
        gbc.gridx = 1;
        String[] tipos = {"Cachorro", "Gato"};
        cmbTipoAnimal = new JComboBox<>(tipos);
        add(cmbTipoAnimal, gbc);

        // Campos específicos para Cachorro/Gato (inicialmente ocultos)
        // Porte (para Cachorro)
        gbc.gridx = 0;
        gbc.gridy = 5;
        JLabel lblPorte = new JLabel("Porte:");
        add(lblPorte, gbc);
        gbc.gridx = 1;
        txtPorte = new JTextField(20);
        add(txtPorte, gbc);

        // Castrado (para Gato)
        gbc.gridx = 0;
        gbc.gridy = 6;
        JLabel lblCastrado = new JLabel("Castrado:");
        add(lblCastrado, gbc);
        gbc.gridx = 1;
        chkCastrado = new JCheckBox();
        add(chkCastrado, gbc);

        // Lógica para mostrar/ocultar campos específicos
        cmbTipoAnimal.addActionListener(e -> {
            String tipoSelecionado = (String) cmbTipoAnimal.getSelectedItem();
            if ("Cachorro".equals(tipoSelecionado)) {
                lblPorte.setVisible(true);
                txtPorte.setVisible(true);
                lblCastrado.setVisible(false);
                chkCastrado.setVisible(false);
            } else if ("Gato".equals(tipoSelecionado)) {
                lblPorte.setVisible(false);
                txtPorte.setVisible(false);
                lblCastrado.setVisible(true);
                chkCastrado.setVisible(true);
            }
        });
        // Inicializa para Cachorro
        cmbTipoAnimal.setSelectedItem("Cachorro");
        lblCastrado.setVisible(false);
        chkCastrado.setVisible(false);


        // Botão Cadastrar
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.gridwidth = 2;
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String nome = txtNome.getText();
                    int idade = Integer.parseInt(txtIdade.getText());
                    String raca = txtRaca.getText();
                    String tipo = (String) cmbTipoAnimal.getSelectedItem();

                    if (nome.isEmpty() || raca.isEmpty()) {
                        JOptionPane.showMessageDialog(CadastroPanel.this, "Por favor, preencha todos os campos obrigatórios.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                        return;
                    }

                    Animal novoAnimal = null;
                    if ("Cachorro".equals(tipo)) {
                        String porte = txtPorte.getText();
                        if (porte.isEmpty()) {
                            JOptionPane.showMessageDialog(CadastroPanel.this, "Por favor, preencha o porte do cachorro.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                            return;
                        }
                        novoAnimal = new Cachorro(nome, idade, raca, porte); // ID será atribuído no service
                    } else if ("Gato".equals(tipo)) {
                        boolean castrado = chkCastrado.isSelected();
                        novoAnimal = new Gato(nome, idade, raca, castrado); // ID será atribuído no service
                    }

                    if (novoAnimal != null) {
                        service.adicionarAnimal(novoAnimal);
                        JOptionPane.showMessageDialog(CadastroPanel.this, "Animal cadastrado com sucesso! ID: " + novoAnimal.getId());
                        limparCampos();
                        atualizarTabela(); // Atualiza a tabela após o cadastro
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(CadastroPanel.this, "Idade deve ser um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(CadastroPanel.this, "Erro ao cadastrar animal: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(btnCadastrar, gbc);
    }

    /**
     * Limpa os campos do formulário após o cadastro.
     */
    private void limparCampos() {
        txtNome.setText("");
        txtIdade.setText("");
        txtRaca.setText("");
        txtPorte.setText("");
        chkCastrado.setSelected(false);
        cmbTipoAnimal.setSelectedItem("Cachorro"); // Reseta para o padrão
    }

    /**
     * Atualiza os dados na JTable principal.
     */
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes
        for (Animal animal : service.listarAnimais()) {
            Object[] rowData;
            if (animal instanceof Cachorro cachorro) {
                rowData = new Object[]{
                        cachorro.getId(),
                        cachorro.getNome(),
                        cachorro.getIdade(),
                        cachorro.getRaca(),
                        "Cachorro",
                        cachorro.getPorte(),
                        "N/A", // Não se aplica a castrado
                        cachorro.emitirSom()
                };
            } else if (animal instanceof Gato gato) {
                rowData = new Object[]{
                        gato.getId(),
                        gato.getNome(),
                        gato.getIdade(),
                        gato.getRaca(),
                        "Gato",
                        "N/A", // Não se aplica a porte
                        gato.isCastrado() ? "Sim" : "Não",
                        gato.emitirSom()
                };
            } else {
                rowData = new Object[]{
                        animal.getId(),
                        animal.getNome(),
                        animal.getIdade(),
                        animal.getRaca(),
                        "Desconhecido",
                        "N/A",
                        "N/A",
                        animal.emitirSom()
                };
            }
            tableModel.addRow(rowData);
        }
    }
}

/**
 * Painel para a alteração de registros de animais.
 */
class AlteracaoPanel extends JPanel {
    private JTextField txtIdBusca, txtNome, txtIdade, txtRaca, txtPorte;
    private JCheckBox chkCastrado;
    private JComboBox<String> cmbTipoAnimal;
    private JButton btnBuscar, btnSalvar;
    private PetshopService service;
    private Animal animalEmEdicao; // Armazena o animal atualmente em edição
    private JTable tabelaAnimais;
    private DefaultTableModel tableModel;

    public AlteracaoPanel(PetshopService service, JTable tabelaAnimais, DefaultTableModel tableModel) {
        this.service = service;
        this.tabelaAnimais = tabelaAnimais;
        this.tableModel = tableModel;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Alterar Dados do Animal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // Busca por ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("ID do Animal:"), gbc);
        gbc.gridx = 1;
        txtIdBusca = new JTextField(20);
        add(txtIdBusca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        btnBuscar = new JButton("Buscar");
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtIdBusca.getText();
                animalEmEdicao = service.buscarAnimalPorId(id);
                if (animalEmEdicao != null) {
                    preencherCampos(animalEmEdicao);
                    btnSalvar.setEnabled(true);
                } else {
                    JOptionPane.showMessageDialog(AlteracaoPanel.this, "Animal com ID " + id + " não encontrado.", "Animal Não Encontrado", JOptionPane.WARNING_MESSAGE);
                    limparCampos();
                    btnSalvar.setEnabled(false);
                }
            }
        });
        add(btnBuscar, gbc);

        // Campos de Edição (inicialmente desabilitados)
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Nome:"), gbc);
        gbc.gridx = 1;
        txtNome = new JTextField(20);
        txtNome.setEnabled(false);
        add(txtNome, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Idade:"), gbc);
        gbc.gridx = 1;
        txtIdade = new JTextField(20);
        txtIdade.setEnabled(false);
        add(txtIdade, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Raça:"), gbc);
        gbc.gridx = 1;
        txtRaca = new JTextField(20);
        txtRaca.setEnabled(false);
        add(txtRaca, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        add(new JLabel("Tipo de Animal:"), gbc);
        gbc.gridx = 1;
        String[] tipos = {"Cachorro", "Gato"};
        cmbTipoAnimal = new JComboBox<>(tipos);
        cmbTipoAnimal.setEnabled(false); // Tipo não pode ser alterado após o cadastro
        add(cmbTipoAnimal, gbc);

        // Campos específicos para Cachorro/Gato
        gbc.gridx = 0;
        gbc.gridy = 7;
        JLabel lblPorte = new JLabel("Porte:");
        add(lblPorte, gbc);
        gbc.gridx = 1;
        txtPorte = new JTextField(20);
        txtPorte.setEnabled(false);
        add(txtPorte, gbc);

        gbc.gridx = 0;
        gbc.gridy = 8;
        JLabel lblCastrado = new JLabel("Castrado:");
        add(lblCastrado, gbc);
        gbc.gridx = 1;
        chkCastrado = new JCheckBox();
        chkCastrado.setEnabled(false);
        add(chkCastrado, gbc);

        // Lógica para mostrar/ocultar campos específicos
        cmbTipoAnimal.addActionListener(e -> {
            String tipoSelecionado = (String) cmbTipoAnimal.getSelectedItem();
            if ("Cachorro".equals(tipoSelecionado)) {
                lblPorte.setVisible(true);
                txtPorte.setVisible(true);
                lblCastrado.setVisible(false);
                chkCastrado.setVisible(false);
            } else if ("Gato".equals(tipoSelecionado)) {
                lblPorte.setVisible(false);
                txtPorte.setVisible(false);
                lblCastrado.setVisible(true);
                chkCastrado.setVisible(true);
            }
        });

        // Botão Salvar Alterações
        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.setEnabled(false); // Desabilitado inicialmente
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (animalEmEdicao != null) {
                    try {
                        animalEmEdicao.setNome(txtNome.getText());
                        animalEmEdicao.setIdade(Integer.parseInt(txtIdade.getText()));
                        animalEmEdicao.setRaca(txtRaca.getText());

                        if (animalEmEdicao instanceof Cachorro cachorro) {
                            cachorro.setPorte(txtPorte.getText());
                        } else if (animalEmEdicao instanceof Gato gato) {
                            gato.setCastrado(chkCastrado.isSelected());
                        }

                        if (service.atualizarAnimal(animalEmEdicao)) {
                            JOptionPane.showMessageDialog(AlteracaoPanel.this, "Animal atualizado com sucesso!");
                            limparCampos();
                            btnSalvar.setEnabled(false);
                            atualizarTabela(); // Atualiza a tabela após a alteração
                        } else {
                            JOptionPane.showMessageDialog(AlteracaoPanel.this, "Erro ao atualizar animal.", "Erro", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(AlteracaoPanel.this, "Idade deve ser um número inteiro.", "Erro de Entrada", JOptionPane.ERROR_MESSAGE);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(AlteracaoPanel.this, "Erro ao salvar alterações: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(btnSalvar, gbc);

        limparCampos(); // Garante que os campos estejam limpos e desabilitados no início
    }

    /**
     * Preenche os campos do formulário com os dados do animal encontrado.
     * @param animal O objeto Animal cujos dados serão exibidos.
     */
    private void preencherCampos(Animal animal) {
        txtNome.setText(animal.getNome());
        txtIdade.setText(String.valueOf(animal.getIdade()));
        txtRaca.setText(animal.getRaca());

        // Habilita os campos de edição
        txtNome.setEnabled(true);
        txtIdade.setEnabled(true);
        txtRaca.setEnabled(true);
        cmbTipoAnimal.setEnabled(false); // Tipo não pode ser alterado

        if (animal instanceof Cachorro cachorro) {
            cmbTipoAnimal.setSelectedItem("Cachorro");
            txtPorte.setText(cachorro.getPorte());
            txtPorte.setEnabled(true);
            chkCastrado.setSelected(false);
            chkCastrado.setEnabled(false);
        } else if (animal instanceof Gato gato) {
            cmbTipoAnimal.setSelectedItem("Gato");
            txtPorte.setText("");
            txtPorte.setEnabled(false);
            chkCastrado.setSelected(gato.isCastrado());
            chkCastrado.setEnabled(true);
        }
    }

    /**
     * Limpa os campos do formulário e os desabilita.
     */
    private void limparCampos() {
        txtIdBusca.setText("");
        txtNome.setText("");
        txtIdade.setText("");
        txtRaca.setText("");
        txtPorte.setText("");
        chkCastrado.setSelected(false);
        cmbTipoAnimal.setSelectedItem("Cachorro"); // Reseta para o padrão

        txtNome.setEnabled(false);
        txtIdade.setEnabled(false);
        txtRaca.setEnabled(false);
        txtPorte.setEnabled(false);
        chkCastrado.setEnabled(false);
        cmbTipoAnimal.setEnabled(false);
    }

    /**
     * Atualiza os dados na JTable principal.
     */
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes
        for (Animal animal : service.listarAnimais()) {
            Object[] rowData;
            if (animal instanceof Cachorro cachorro) {
                rowData = new Object[]{
                        cachorro.getId(),
                        cachorro.getNome(),
                        cachorro.getIdade(),
                        cachorro.getRaca(),
                        "Cachorro",
                        cachorro.getPorte(),
                        "N/A", // Não se aplica a castrado
                        cachorro.emitirSom()
                };
            } else if (animal instanceof Gato gato) {
                rowData = new Object[]{
                        gato.getId(),
                        gato.getNome(),
                        gato.getIdade(),
                        gato.getRaca(),
                        "Gato",
                        "N/A", // Não se aplica a porte
                        gato.isCastrado() ? "Sim" : "Não",
                        gato.emitirSom()
                };
            } else {
                rowData = new Object[]{
                        animal.getId(),
                        animal.getNome(),
                        animal.getIdade(),
                        animal.getRaca(),
                        "Desconhecido",
                        "N/A",
                        "N/A",
                        animal.emitirSom()
                };
            }
            tableModel.addRow(rowData);
        }
    }
}

/**
 * Painel para a exclusão de registros de animais.
 */
class ExclusaoPanel extends JPanel {
    private JTextField txtIdExclusao;
    private JButton btnExcluir;
    private PetshopService service;
    private JTable tabelaAnimais;
    private DefaultTableModel tableModel;

    public ExclusaoPanel(PetshopService service, JTable tabelaAnimais, DefaultTableModel tableModel) {
        this.service = service;
        this.tabelaAnimais = tabelaAnimais;
        this.tableModel = tableModel;
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Excluir Animal", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        gbc.gridwidth = 1;

        // ID para exclusão
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("ID do Animal:"), gbc);
        gbc.gridx = 1;
        txtIdExclusao = new JTextField(20);
        add(txtIdExclusao, gbc);

        // Botão Excluir
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtIdExclusao.getText();
                if (id.isEmpty()) {
                    JOptionPane.showMessageDialog(ExclusaoPanel.this, "Por favor, digite o ID do animal para exclusão.", "Erro de Validação", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(ExclusaoPanel.this, "Tem certeza que deseja excluir o animal com ID: " + id + "?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    if (service.removerAnimal(id)) {
                        JOptionPane.showMessageDialog(ExclusaoPanel.this, "Animal excluído com sucesso!");
                        txtIdExclusao.setText("");
                        atualizarTabela(); // Atualiza a tabela após a exclusão
                    } else {
                        JOptionPane.showMessageDialog(ExclusaoPanel.this, "Animal com ID " + id + " não encontrado ou erro ao excluir.", "Erro de Exclusão", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        add(btnExcluir, gbc);
    }

    /**
     * Atualiza os dados na JTable principal.
     */
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes
        for (Animal animal : service.listarAnimais()) {
            Object[] rowData;
            if (animal instanceof Cachorro cachorro) {
                rowData = new Object[]{
                        cachorro.getId(),
                        cachorro.getNome(),
                        cachorro.getIdade(),
                        cachorro.getRaca(),
                        "Cachorro",
                        cachorro.getPorte(),
                        "N/A", // Não se aplica a castrado
                        cachorro.emitirSom()
                };
            } else if (animal instanceof Gato gato) {
                rowData = new Object[]{
                        gato.getId(),
                        gato.getNome(),
                        gato.getIdade(),
                        gato.getRaca(),
                        "Gato",
                        "N/A", // Não se aplica a porte
                        gato.isCastrado() ? "Sim" : "Não",
                        gato.emitirSom()
                };
            } else {
                rowData = new Object[]{
                        animal.getId(),
                        animal.getNome(),
                        animal.getIdade(),
                        animal.getRaca(),
                        "Desconhecido",
                        "N/A",
                        "N/A",
                        animal.emitirSom()
                };
            }
            tableModel.addRow(rowData);
        }
    }
}

/**
 * Classe principal da aplicação de Gerenciamento de Petshop.
 * Configura a janela principal e os painéis da interface gráfica.
 */
public class PetshopApp extends JFrame {
    private PetshopService service;
    private JTable tabelaAnimais;
    private DefaultTableModel tableModel;

    public PetshopApp() {
        service = new PetshopService();
        setTitle("Gerenciamento de Petshop");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza a janela

        // Cria as abas para Cadastro, Alteração e Exclusão
        JTabbedPane tabbedPane = new JTabbedPane();

        // Configura a tabela de exibição de animais
        String[] colunas = {"ID", "Nome", "Idade", "Raça", "Tipo", "Porte", "Castrado", "Som"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Torna as células não editáveis
            }
        };
        tabelaAnimais = new JTable(tableModel);
        tabelaAnimais.setFillsViewportHeight(true); // Preenche a altura da viewport
        JScrollPane scrollPane = new JScrollPane(tabelaAnimais);

        // Adiciona os painéis às abas
        tabbedPane.addTab("Cadastrar Animal", new CadastroPanel(service, tabelaAnimais, tableModel));
        tabbedPane.addTab("Alterar Animal", new AlteracaoPanel(service, tabelaAnimais, tableModel));
        tabbedPane.addTab("Excluir Animal", new ExclusaoPanel(service, tabelaAnimais, tableModel));

        // Layout principal da janela
        setLayout(new BorderLayout());
        add(tabbedPane, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH); // Tabela na parte inferior

        // Atualiza a tabela inicialmente
        atualizarTabela();
    }

    /**
     * Atualiza os dados na JTable principal.
     */
    private void atualizarTabela() {
        tableModel.setRowCount(0); // Limpa todas as linhas existentes
        for (Animal animal : service.listarAnimais()) {
            Object[] rowData;
            if (animal instanceof Cachorro cachorro) {
                rowData = new Object[]{
                        cachorro.getId(),
                        cachorro.getNome(),
                        cachorro.getIdade(),
                        cachorro.getRaca(),
                        "Cachorro",
                        cachorro.getPorte(),
                        "N/A", // Não se aplica a castrado
                        cachorro.emitirSom()
                };
            } else if (animal instanceof Gato gato) {
                rowData = new Object[]{
                        gato.getId(),
                        gato.getNome(),
                        gato.getIdade(),
                        gato.getRaca(),
                        "Gato",
                        "N/A", // Não se aplica a porte
                        gato.isCastrado() ? "Sim" : "Não",
                        gato.emitirSom()
                };
            } else {
                rowData = new Object[]{
                        animal.getId(),
                        animal.getNome(),
                        animal.getIdade(),
                        animal.getRaca(),
                        "Desconhecido",
                        "N/A",
                        "N/A",
                        animal.emitirSom()
                };
            }
            tableModel.addRow(rowData);
        }
    }

    public static void main(String[] args) {
        // Garante que a GUI seja criada na Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            new PetshopApp().setVisible(true);
        });
    }
}
