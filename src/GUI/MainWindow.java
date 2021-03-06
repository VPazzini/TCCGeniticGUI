package GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
import main.Generator;
import main.Genetic;
import main.Individual;
import main.Population;
import main.Sequence;
import main.ValidateResult;

public class MainWindow extends javax.swing.JFrame {

    private Genetic g;
    private Thread t;
    private int genSizeMotif = 16;
    private int genNumSeqs = 20;
    private int genSeqSize = 500;
    private int genNumTests = 100;
    private boolean genNoise = false;
    private boolean genLowCons = false;
    private File folder;
    
    public MainWindow() {
        initComponents();
        setLocationRelativeTo(null);
        setExtendedState(MainWindow.MAXIMIZED_BOTH);

        jButtonSaveToFile.setVisible(false);

        try {
            File f = new File((new File("")).getAbsoluteFile() + "/input");
            jTextFieldFilePath.setText(f.listFiles()[0].getAbsolutePath());
        } catch (Exception e) {
        }
        jTextFieldFilePath.setText(new File("benchmark.fasta").getAbsolutePath());
        jTextFieldCompareFile.setText(new File("motif.fasta").getAbsolutePath());

        MouseListener mouseListener;
        mouseListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                JList theList = (JList) mouseEvent.getSource();
                if (mouseEvent.getClickCount() == 2) {
                    int index = theList.locationToIndex(mouseEvent.getPoint());
                    if (index >= 0) {
                        Object o = theList.getModel().getElementAt(index);
                        new IndividualDisplay((Individual) o);
                    }
                }
            }
        };
        jList1.addMouseListener(mouseListener);

        ButtonGroup group = new ButtonGroup();

        group.add(jRadioButtonSingleTest);
        group.add(jRadioButtonSeveralTests);
        group.add(jRadioButtonCompareCluster);
        
        folder = new File("D:\\wekatestes");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jSpinnerMotifSize = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        jSpinnerPopulationSize = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jSpinnerGenerations = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jSpinnerComparisonThreshold = new javax.swing.JSpinner();
        jComboBoxPopulationMethod = new javax.swing.JComboBox();
        jComboBoxCrossOverMethod = new javax.swing.JComboBox();
        jLabel17 = new javax.swing.JLabel();
        jSpinnerSurvivors = new javax.swing.JSpinner();
        jComboBoxSelectionMethod = new javax.swing.JComboBox();
        jTextFieldFilePath = new javax.swing.JTextField();
        jButtonFastaFile = new javax.swing.JButton();
        jLabelPopSize = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButtonRun = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabelGeneration = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabelSequences = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabelMeanLenght = new javax.swing.JLabel();
        jTextFieldMotifFilePath = new javax.swing.JTextField();
        jButtonMotifFile = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jButtonSaveToFile = new javax.swing.JButton();
        jCheckBoxReverseSearch = new javax.swing.JCheckBox();
        jCheckBoxOneOcurrence = new javax.swing.JCheckBox();
        jTextFieldCompareFile = new javax.swing.JTextField();
        jButtonCompareFile = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();
        jRadioButtonSingleTest = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonSeveralTests = new javax.swing.JRadioButtonMenuItem();
        jRadioButtonCompareCluster = new javax.swing.JRadioButtonMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(558, 472));

        jLabel2.setText("Motif Size");

        jSpinnerMotifSize.setModel(new javax.swing.SpinnerNumberModel(16, 1, 1000, 1));

        jLabel13.setText("Population Size");

        jSpinnerPopulationSize.setModel(new javax.swing.SpinnerNumberModel(100, 0, 100000, 50));

        jLabel14.setText("Max Generations");

        jSpinnerGenerations.setModel(new javax.swing.SpinnerNumberModel(100, 1, 100000, 50));

        jLabel15.setText("Comparasion Threshold");

        jSpinnerComparisonThreshold.setModel(new javax.swing.SpinnerNumberModel(0.7d, 0.0d, 1.0d, 0.05d));
        jSpinnerComparisonThreshold.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSpinnerComparisonThresholdStateChanged(evt);
            }
        });

        jComboBoxPopulationMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Population Creation Method", "Random", "Clustering" }));
        jComboBoxPopulationMethod.setSelectedIndex(2);

        jComboBoxCrossOverMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "CrossOver Method", "One Point CrossOver", "Two Point CrossOver", "Best of Each", "All of them" }));

        jLabel17.setText("Survaviors from Previous Generation");

        jSpinnerSurvivors.setModel(new javax.swing.SpinnerNumberModel(0.05d, 0.0d, 0.8d, 0.01d));

        jComboBoxSelectionMethod.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selection Method", "Random", "Roullete Wheel" }));
        jComboBoxSelectionMethod.setSelectedIndex(2);

        jButtonFastaFile.setText("FASTA File");
        jButtonFastaFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonFastaFileActionPerformed(evt);
            }
        });

        jLabelPopSize.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelPopSize.setText("0");

        jLabel4.setText("Population");

        jButtonRun.setText("Run");
        jButtonRun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRunActionPerformed(evt);
            }
        });

        jLabel5.setText("Generation");

        jLabelGeneration.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelGeneration.setText("0");

        jLabel1.setText("Sequences");

        jLabelSequences.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelSequences.setText("0");

        jLabel6.setText("Mean Lenght");

        jLabelMeanLenght.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabelMeanLenght.setText("0");

        jButtonMotifFile.setText("Motifs File");
        jButtonMotifFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMotifFileActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(jList1);

        jButtonSaveToFile.setText("Save to File");
        jButtonSaveToFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSaveToFileActionPerformed(evt);
            }
        });

        jCheckBoxReverseSearch.setSelected(true);
        jCheckBoxReverseSearch.setText("Search Reverse Seq");
        jCheckBoxReverseSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxReverseSearchActionPerformed(evt);
            }
        });

        jCheckBoxOneOcurrence.setSelected(true);
        jCheckBoxOneOcurrence.setText("One Ocurrence per Seq");
        jCheckBoxOneOcurrence.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBoxOneOcurrenceActionPerformed(evt);
            }
        });

        jButtonCompareFile.setText("Compare File");
        jButtonCompareFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCompareFileActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBoxCrossOverMethod, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jLabel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSpinnerMotifSize)
                                    .addComponent(jSpinnerPopulationSize, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                                    .addComponent(jSpinnerGenerations))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jSpinnerSurvivors, javax.swing.GroupLayout.DEFAULT_SIZE, 123, Short.MAX_VALUE)
                                    .addComponent(jSpinnerComparisonThreshold)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jComboBoxPopulationMethod, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxSelectionMethod, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jCheckBoxOneOcurrence, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jCheckBoxReverseSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addComponent(jTextFieldMotifFilePath, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(jTextFieldFilePath, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
                            .addComponent(jTextFieldCompareFile, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabelPopSize, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
                                    .addComponent(jLabelMeanLenght, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelSequences, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabelGeneration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(jButtonFastaFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonMotifFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonRun, javax.swing.GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
                            .addComponent(jButtonSaveToFile, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButtonCompareFile, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel1, jLabel4, jLabel5, jLabel6});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jSpinnerMotifSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15)
                    .addComponent(jSpinnerComparisonThreshold, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerPopulationSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel17)
                    .addComponent(jSpinnerSurvivors, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jSpinnerGenerations, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxPopulationMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxReverseSearch))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxCrossOverMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxSelectionMethod, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBoxOneOcurrence))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonFastaFile))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldMotifFilePath, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonMotifFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextFieldCompareFile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButtonCompareFile))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabelSequences))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabelMeanLenght))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabelGeneration))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabelPopSize)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 163, Short.MAX_VALUE)
                        .addComponent(jButtonSaveToFile)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonRun))
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenu3.setText("Mode");

        jRadioButtonSingleTest.setSelected(true);
        jRadioButtonSingleTest.setText("Single Test");
        jMenu3.add(jRadioButtonSingleTest);

        jRadioButtonSeveralTests.setSelected(true);
        jRadioButtonSeveralTests.setText("Several Tests");
        jMenu3.add(jRadioButtonSeveralTests);

        jRadioButtonCompareCluster.setSelected(true);
        jRadioButtonCompareCluster.setText("Compare Cluster Output");
        jMenu3.add(jRadioButtonCompareCluster);

        jMenuItem1.setText("Generator Config");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenu1.add(jMenu3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    private void jButtonRunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRunActionPerformed
        int numRuns = 1;
        if (jRadioButtonSeveralTests.isSelected() || jRadioButtonCompareCluster.isSelected()) {
            numRuns = genNumTests;
        }

        if (jButtonRun.getText().equals("Run")) {
            for (int i = 0; i < numRuns; i++) {
                
                int index = 0;
                if (!jRadioButtonCompareCluster.isSelected()) {
                    Generator gene = new Generator();
                    gene.generate(genSizeMotif, genNumSeqs, genSeqSize, genNoise, genLowCons);
                }else{
                    index = i;
                }

                jButtonSaveToFile.setVisible(false);

                g = new Genetic();

                if (jTextFieldFilePath.getText().length() == 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a fasta file");
                    return;
                }

                /*for (String s : jTextFieldFilePath.getText().split(";")) {
                 g.readFile(s);
                 }*/
                g.readFile(jTextFieldFilePath.getText().split(";")[index]);

                g.setUp((int) jSpinnerGenerations.getValue(), (int) jSpinnerPopulationSize.getValue(),
                        (int) jSpinnerMotifSize.getValue(), (int) jComboBoxPopulationMethod.getSelectedIndex(),
                        jTextFieldMotifFilePath.getText().split(";")[index],
                        (double) jSpinnerSurvivors.getValue(), (int) jComboBoxSelectionMethod.getSelectedIndex(),
                        this, jTextFieldCompareFile.getText().split(";")[index]);


                this.jLabelSequences.setText(g.getSequences().size() + "");
                float size = 0;
                for (Sequence s : g.getSequences()) {
                    size += s.lenght();
                }
                size = size / g.getSequences().size();
                this.jLabelMeanLenght.setText(size + "");

                Population.getInstance().setThresholdComparison((double) jSpinnerComparisonThreshold.getValue());

                if (jRadioButtonSeveralTests.isSelected() || jRadioButtonCompareCluster.isSelected()) {
                    g.run();
                } else {
                    t = new Thread(g);
                    t.start();
                }

                jButtonRun.setText("Stop");

            }
            if (jRadioButtonSeveralTests.isSelected()) {
                jButtonRun.setText("Run");
            }
            try {
                File file = new File("results.txt");
                BufferedWriter output = new BufferedWriter(new FileWriter(file, true));
                output.write("-----------------------------------------------------------\n");
                output.close();
            } catch (IOException ex) {
                Logger.getLogger(ValidateResult.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            if (t != null) {
                if (t.isAlive()) {
                    t.stop();
                }
            }
            jButtonRun.setText("Run");
        }
    }//GEN-LAST:event_jButtonRunActionPerformed

    private void jButtonFastaFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonFastaFileActionPerformed

        JFileChooser fileChooser = new JFileChooser((folder).getAbsoluteFile());
        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String s = "";
            for (File f : fileChooser.getSelectedFiles()) {
                s += f.getAbsolutePath() + ";";
            }
            this.jTextFieldFilePath.setText(s);
        }

    }//GEN-LAST:event_jButtonFastaFileActionPerformed

    private void jSpinnerComparisonThresholdStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSpinnerComparisonThresholdStateChanged
        double d = (double) jSpinnerComparisonThreshold.getValue();
        Population.getInstance().setThresholdComparison(d);
    }//GEN-LAST:event_jSpinnerComparisonThresholdStateChanged

    private void jButtonMotifFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMotifFileActionPerformed
        JFileChooser fileChooser = new JFileChooser((folder).getAbsoluteFile());

        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String s = "";
            for (File f : fileChooser.getSelectedFiles()) {
                s += f.getAbsolutePath() + ";";
            }
            this.jTextFieldMotifFilePath.setText(s);
        }

    }//GEN-LAST:event_jButtonMotifFileActionPerformed

    private void jButtonSaveToFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSaveToFileActionPerformed
        File f = new File(jTextFieldFilePath.getText().split(";")[0]);
        JFileChooser fileChooser = new JFileChooser((new File("")).getAbsoluteFile() + "/output/" + f.getName() + "/");

        fileChooser.setDialogTitle("Specify a file to save");
        fileChooser.setSelectedFile(new File("/output/output_S"
                + this.jSpinnerMotifSize.getValue() + "_"
                + (int) ((double) this.jSpinnerComparisonThreshold.getValue() * 100) + "_"
                + f.getName()));

        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();

            if (fileToSave.exists()) {
                int dialogResult = JOptionPane.showConfirmDialog(null, "Would You Like to Overwrite the existing file?", "Warning", JOptionPane.YES_NO_OPTION);
                if (dialogResult == JOptionPane.YES_OPTION) {
                    try {
                        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileToSave, false));
                    } catch (IOException ex) {
                        Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            Population.getInstance().getPopulation().stream().forEach((ind) -> {
                ind.writeToFile(fileToSave);
            });

        }
    }//GEN-LAST:event_jButtonSaveToFileActionPerformed

    private void jCheckBoxReverseSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxReverseSearchActionPerformed
        Population.getInstance().setSearchOnReverse(jCheckBoxReverseSearch.isSelected());
    }//GEN-LAST:event_jCheckBoxReverseSearchActionPerformed

    private void jCheckBoxOneOcurrenceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBoxOneOcurrenceActionPerformed
        Population.getInstance().setOneOccurence(jCheckBoxOneOcurrence.isSelected());
    }//GEN-LAST:event_jCheckBoxOneOcurrenceActionPerformed

    private void jButtonCompareFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCompareFileActionPerformed
        JFileChooser fileChooser = new JFileChooser((folder).getAbsoluteFile());

        fileChooser.setMultiSelectionEnabled(true);
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String s = "";
            for (File f : fileChooser.getSelectedFiles()) {
                s += f.getAbsolutePath() + ";";
            }
            this.jTextFieldCompareFile.setText(s);
        }

    }//GEN-LAST:event_jButtonCompareFileActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GeneratorConfig generatorConfig = new GeneratorConfig(this);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    public void attGeneration(ArrayList<Individual> list) {
        DefaultListModel listModel = new DefaultListModel();
        list.stream().forEach((ind) -> {
            listModel.addElement(ind);
        });

        jList1.setModel(listModel);
        this.jLabelGeneration.setText(g.getGen() + "");
        this.jLabelPopSize.setText(Population.getInstance().getPopulation().size() + "");

    }

    public void finished() {
        //JOptionPane.showMessageDialog(this, "Computation Finished");

        File f = new File(jTextFieldFilePath.getText().split(";")[0]);
        String path = (new File("")).getAbsoluteFile() + "/output/" + f.getName() + "/";
        File newDir = new File(path);
        if (!newDir.exists()) {
            newDir.mkdir();
        }

        jButtonSaveToFile.setVisible(true);

        jButtonRun.setText("Run");

        //fileChooser.showOpenDialog(this);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    public int getGenSizeMotif() {
        return genSizeMotif;
    }

    public void setGenSizeMotif(int genSizeMotif) {
        this.genSizeMotif = genSizeMotif;
    }

    public int getGenNumSeqs() {
        return genNumSeqs;
    }

    public void setGenNumSeqs(int genNumSeqs) {
        this.genNumSeqs = genNumSeqs;
    }

    public int getGenSeqSize() {
        return genSeqSize;
    }

    public void setGenSeqSize(int genSeqSize) {
        this.genSeqSize = genSeqSize;
    }

    public boolean isGenNoise() {
        return genNoise;
    }

    public void setGenNoise(boolean genNoise) {
        this.genNoise = genNoise;
    }

    public boolean isGenLowCons() {
        return genLowCons;
    }

    public void setGenLowCons(boolean genLowCons) {
        this.genLowCons = genLowCons;
    }

    public int getGenNumTests() {
        return genNumTests;
    }

    public void setGenNumTests(int genNumTests) {
        this.genNumTests = genNumTests;
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCompareFile;
    private javax.swing.JButton jButtonFastaFile;
    private javax.swing.JButton jButtonMotifFile;
    private javax.swing.JButton jButtonRun;
    private javax.swing.JButton jButtonSaveToFile;
    private javax.swing.JCheckBox jCheckBoxOneOcurrence;
    private javax.swing.JCheckBox jCheckBoxReverseSearch;
    private javax.swing.JComboBox jComboBoxCrossOverMethod;
    private javax.swing.JComboBox jComboBoxPopulationMethod;
    private javax.swing.JComboBox jComboBoxSelectionMethod;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabelGeneration;
    private javax.swing.JLabel jLabelMeanLenght;
    private javax.swing.JLabel jLabelPopSize;
    private javax.swing.JLabel jLabelSequences;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButtonMenuItem jRadioButtonCompareCluster;
    private javax.swing.JRadioButtonMenuItem jRadioButtonSeveralTests;
    private javax.swing.JRadioButtonMenuItem jRadioButtonSingleTest;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSpinner jSpinnerComparisonThreshold;
    private javax.swing.JSpinner jSpinnerGenerations;
    private javax.swing.JSpinner jSpinnerMotifSize;
    private javax.swing.JSpinner jSpinnerPopulationSize;
    private javax.swing.JSpinner jSpinnerSurvivors;
    private javax.swing.JTextField jTextFieldCompareFile;
    private javax.swing.JTextField jTextFieldFilePath;
    private javax.swing.JTextField jTextFieldMotifFilePath;
    // End of variables declaration//GEN-END:variables
}
