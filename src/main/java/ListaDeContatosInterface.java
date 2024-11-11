import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListaDeContatosInterface extends JFrame {
    private ArrayList<Contato> listaContatos;
    private JTextField nomeField;
    private JTextField telefoneField;
    private DefaultListModel<String> listModel;
    private JList<String> contatosList;

    public ListaDeContatosInterface(ArrayList<Contato> listaContatos) {
        this.listaContatos = listaContatos;

        setTitle("Minha Lista de Contatos");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de entrada de dados
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        inputPanel.add(new JLabel("Nome:"));
        nomeField = new JTextField();
        inputPanel.add(nomeField);

        inputPanel.add(new JLabel("Telefone:"));
        telefoneField = new JTextField();
        inputPanel.add(telefoneField);

        JButton addButton = new JButton("Salvar Contato");
        inputPanel.add(addButton);

        JButton editButton = new JButton("Atualizar Contato");
        inputPanel.add(editButton);

        add(inputPanel, BorderLayout.NORTH);

        // Área de listagem de contatos
        listModel = new DefaultListModel<>();
        atualizarListaContatos();
        contatosList = new JList<>(listModel);
        add(new JScrollPane(contatosList), BorderLayout.CENTER);

        // Painel de botões para exclusão e chamada
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("Remover Contato");
        JButton callButton = new JButton("Chamar Contato");
        buttonPanel.add(deleteButton);
        buttonPanel.add(callButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Ações dos botões com mensagens mais amigáveis
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarContato();
            }
        });

        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarContato();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                excluirContato();
            }
        });

        callButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ligarParaContato();
            }
        });
    }

    private void adicionarContato() {
        String nome = nomeField.getText();
        String telefone = telefoneField.getText();
        if (!nome.isEmpty() && !telefone.isEmpty()) {
            Contato contato = new Contato(nome, telefone);
            listaContatos.add(contato);
            atualizarListaContatos();
            nomeField.setText("");
            telefoneField.setText("");
            JOptionPane.showMessageDialog(this, "Contato de " + nome + " foi adicionado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos para salvar o contato.");
        }
    }

    private void editarContato() {
        int selectedIndex = contatosList.getSelectedIndex();
        if (selectedIndex != -1) {
            String novoNome = JOptionPane.showInputDialog("Atualize o nome:", listaContatos.get(selectedIndex).getNome());
            String novoTelefone = JOptionPane.showInputDialog("Atualize o telefone:", listaContatos.get(selectedIndex).getTelefone());
            if (novoNome != null && novoTelefone != null) {
                Contato contato = listaContatos.get(selectedIndex);
                contato.setNome(novoNome);
                contato.setTelefone(novoTelefone);
                atualizarListaContatos();
                JOptionPane.showMessageDialog(this, "Informações do contato atualizadas!");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para editar.");
        }
    }

    private void excluirContato() {
        int selectedIndex = contatosList.getSelectedIndex();
        if (selectedIndex != -1) {
            String nome = listaContatos.get(selectedIndex).getNome();
            listaContatos.remove(selectedIndex);
            atualizarListaContatos();
            JOptionPane.showMessageDialog(this, "O contato de " + nome + " foi removido.");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para remover.");
        }
    }

    private void ligarParaContato() {
        int selectedIndex = contatosList.getSelectedIndex();
        if (selectedIndex != -1) {
            Contato contato = listaContatos.get(selectedIndex);
            JOptionPane.showMessageDialog(this, "Iniciando chamada para " + contato.getNome() + "...");
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um contato para chamar.");
        }
    }

    private void atualizarListaContatos() {
        listModel.clear();
        for (Contato contato : listaContatos) {
            listModel.addElement("Índice: " + listaContatos.indexOf(contato) + " - " + contato.getNome() + " - " + contato.getTelefone());
        }
    }

    public static void main(String[] args) {
        ArrayList<Contato> listaContatos = new ArrayList<>();

        SwingUtilities.invokeLater(() -> {
            new ListaDeContatosInterface(listaContatos).setVisible(true);
        });
    }
}
