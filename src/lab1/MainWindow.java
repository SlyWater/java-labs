package lab1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import javax.swing.filechooser.FileNameExtensionFilter;

public class MainWindow extends javax.swing.JFrame {
    
    private static ArrayList<RecIntegral> tableContent = new ArrayList<>();
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(MainWindow.class.getName());
//    private static final 

    private final File CURRENT_DIR = Paths.get("").toAbsolutePath().toFile();

    public MainWindow() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ResultTable = new javax.swing.JTable();
        AddButton = new javax.swing.JButton();
        DeleteButton = new javax.swing.JButton();
        CalculateButton = new javax.swing.JButton();
        formulaLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        LowLevelTextField = new javax.swing.JTextField();
        HighLevelTextField = new javax.swing.JTextField();
        StepTextField = new javax.swing.JTextField();
        LowLabel = new javax.swing.JLabel();
        HighLabel = new javax.swing.JLabel();
        StepLabel = new javax.swing.JLabel();
        ClearTableButton = new javax.swing.JButton();
        AddFromCollectionButton = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        loadBinMenuItem = new javax.swing.JMenuItem();
        loadTxtMenuItem = new javax.swing.JMenuItem();
        saveBinMenuItem = new javax.swing.JMenuItem();
        saveTxtMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        jScrollPane1.setBackground(new java.awt.Color(255, 153, 153));

        ResultTable.setBackground(new java.awt.Color(102, 102, 102));
        ResultTable.setForeground(new java.awt.Color(255, 255, 255));
        ResultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "LOWER LIMIT", "HIGH LIMIT", "STEP", "RESULT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        ResultTable.setGridColor(new java.awt.Color(255, 51, 51));
        jScrollPane1.setViewportView(ResultTable);

        AddButton.setText("ADD");
        AddButton.addActionListener(this::AddButtonActionPerformed);

        DeleteButton.setText("DELETE");
        DeleteButton.addActionListener(this::DeleteButtonActionPerformed);

        CalculateButton.setText("CALCULATE");
        CalculateButton.addActionListener(this::CalculateButtonActionPerformed);

        formulaLabel.setBackground(new java.awt.Color(255, 255, 255));
        formulaLabel.setForeground(new java.awt.Color(255, 255, 255));
        formulaLabel.setText("f(x) = cos(x^2)");

        jPanel2.setBackground(new java.awt.Color(51, 51, 51));

        LowLabel.setBackground(new java.awt.Color(255, 255, 255));
        LowLabel.setForeground(new java.awt.Color(255, 255, 255));
        LowLabel.setText("LL");

        HighLabel.setBackground(new java.awt.Color(255, 255, 255));
        HighLabel.setForeground(new java.awt.Color(255, 255, 255));
        HighLabel.setText("HL");

        StepLabel.setBackground(new java.awt.Color(255, 255, 255));
        StepLabel.setForeground(new java.awt.Color(255, 255, 255));
        StepLabel.setText("STEP");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 132, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(StepLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(StepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(HighLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(HighLevelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(LowLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(LowLevelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(4, 4, 4)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(LowLabel)
                        .addComponent(LowLevelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(HighLabel)
                        .addComponent(HighLevelTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(StepLabel)
                        .addComponent(StepTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        ClearTableButton.setText("CLEAR TABLE");
        ClearTableButton.addActionListener(this::ClearTableButtonActionPerformed);

        AddFromCollectionButton.setText("ADD FROM COLLECTION");
        AddFromCollectionButton.addActionListener(this::AddFromCollectionButtonActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ClearTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddFromCollectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CalculateButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                            .addComponent(DeleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(formulaLabel)
                .addGap(152, 152, 152))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(AddButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(DeleteButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CalculateButton)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(formulaLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(AddFromCollectionButton)
                    .addComponent(ClearTableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jMenu1.setText("File");

        loadBinMenuItem.setText("Load SER");
        loadBinMenuItem.addActionListener(this::loadBinMenuItemActionPerformed);
        jMenu1.add(loadBinMenuItem);

        loadTxtMenuItem.setText("Load TXT");
        loadTxtMenuItem.addActionListener(this::loadTxtMenuItemActionPerformed);
        jMenu1.add(loadTxtMenuItem);

        saveBinMenuItem.setText("Save SER");
        saveBinMenuItem.addActionListener(this::saveBinMenuItemActionPerformed);
        jMenu1.add(saveBinMenuItem);

        saveTxtMenuItem.setText("Save TXT");
        saveTxtMenuItem.addActionListener(this::saveTxtMenuItemActionPerformed);
        jMenu1.add(saveTxtMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void clearTable() {
        DefaultTableModel tModel = (DefaultTableModel) ResultTable.getModel();
        tModel.setRowCount(0);
    }
    
    private void addFromCollection() {
        DefaultTableModel tModel = (DefaultTableModel) ResultTable.getModel();
        tModel.setRowCount(0);
        for(RecIntegral ri: tableContent){
            tModel.addRow(new Object[] {ri.getLowLimit(), ri.getHighLimit(), ri.getStep(), ri.getResult()});
        }
    }
    
    private void loadBinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadBinMenuItemActionPerformed
        try {
            FileNameExtensionFilter jnef = new FileNameExtensionFilter("SER file","ser");
            JFileChooser jfc = new JFileChooser(CURRENT_DIR);
            jfc.setFileFilter(jnef);
            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
                tableContent.clear();
                tableContent = (ArrayList<RecIntegral>) in.readObject();
                addFromCollection();       
            }
            
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
        }
        catch (ClassNotFoundException ex) {
            System.getLogger(MainWindow.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }//GEN-LAST:event_loadBinMenuItemActionPerformed

    private void loadTxtMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadTxtMenuItemActionPerformed
        try {
            FileNameExtensionFilter jnef = new FileNameExtensionFilter("TXT file","txt");
            JFileChooser jfc = new JFileChooser(CURRENT_DIR);
            jfc.setFileFilter(jnef);
            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                List<String> lines;
                try (FileReader fileR = new FileReader(file)) {
                    lines = fileR.readAllLines();
                }
                tableContent.clear();
                for (String line : lines) {
                    String[] tokens = line.split(" ");
                    RecIntegral ri = new RecIntegral(
                            Double.parseDouble(tokens[0].strip().replace(",", ".")), 
                            Double.parseDouble(tokens[1].strip().replace(",", ".")), 
                            Double.parseDouble(tokens[2].strip().replace(",", ".")),
                            Double.parseDouble(tokens[3].strip().replace(",", ".")));
                    tableContent.add(ri);
                } 
                addFromCollection();
            }
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);

        } 
        catch (InvalidInputException | NumberFormatException e){
            JOptionPane.showMessageDialog(this, e.getMessage() + "\nIncorrect data in the file", "Error", ERROR_MESSAGE);
        }  
    }//GEN-LAST:event_loadTxtMenuItemActionPerformed

    private void saveBinMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBinMenuItemActionPerformed
        try {
            FileNameExtensionFilter jnef = new FileNameExtensionFilter("SER file","ser");
            JFileChooser jfc = new JFileChooser(CURRENT_DIR);
            jfc.setFileFilter(jnef);
            if(jfc.showSaveDialog(this)==JFileChooser.APPROVE_OPTION){
                File file = jfc.getSelectedFile();
                if (!file.getName().endsWith(".ser")) {
                    file = new File(file.getAbsolutePath() + ".ser");
                }
                
                try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
                    out.writeObject(tableContent);
                }
            }
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveBinMenuItemActionPerformed

    private void saveTxtMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveTxtMenuItemActionPerformed
        try {
            FileNameExtensionFilter jnef = new FileNameExtensionFilter("TXT file","txt");
            JFileChooser jfc = new JFileChooser(CURRENT_DIR);
            jfc.setFileFilter(jnef);
            if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                File file = jfc.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                try (FileWriter fileW = new FileWriter(file)) {
                    String string = "";
                    for(RecIntegral ri: tableContent){
                        string += ri.toString();
                    }
                    fileW.write(string);
                }
            }
            
        }
        catch (IOException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_saveTxtMenuItemActionPerformed

    private void AddFromCollectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddFromCollectionButtonActionPerformed
        addFromCollection();
    }//GEN-LAST:event_AddFromCollectionButtonActionPerformed

    private void ClearTableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ClearTableButtonActionPerformed
        clearTable();
    }//GEN-LAST:event_ClearTableButtonActionPerformed

    private void CalculateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalculateButtonActionPerformed
        DefaultTableModel tModel = (DefaultTableModel) ResultTable.getModel();
        int row = ResultTable.getSelectedRow();
        if(row != -1 && ResultTable.getRowCount() == tableContent.size()){
            try {
                double lowLimit = Double.parseDouble(tModel.getValueAt(row, 0).toString());
                double highLimit = Double.parseDouble(tModel.getValueAt(row, 1).toString());
                double step = Double.parseDouble(tModel.getValueAt(row, 2).toString());
                RecIntegral ri = new RecIntegral(lowLimit, highLimit, step);
                tableContent.set(row, ri);
                tModel.setValueAt(tableContent.get(row).calculate(), row, 3);
            }
            catch (InvalidInputException | NumberFormatException e) {
                JOptionPane.showMessageDialog(this, e.getMessage() + "\nIncorrect data in the table", "Error", ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_CalculateButtonActionPerformed

    private void DeleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DeleteButtonActionPerformed
        DefaultTableModel tModel = (DefaultTableModel) ResultTable.getModel();
        int row = ResultTable.getSelectedRow();
        int difference = tableContent.size()-tModel.getRowCount();
        if(row != -1){
            tModel.removeRow(row);
            tableContent.remove(difference + row);
        }
    }//GEN-LAST:event_DeleteButtonActionPerformed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        double lowLimit, highLimit, step;
        try {
            lowLimit = Double.parseDouble(LowLevelTextField.getText());
            highLimit = Double.parseDouble(HighLevelTextField.getText());
            step = Double.parseDouble(StepTextField.getText());
            DefaultTableModel tModel = (DefaultTableModel) ResultTable.getModel();
                tableContent.add(new RecIntegral(lowLimit, highLimit, step));
                tModel.addRow(new Object[] {lowLimit, highLimit, step, 0.0});
                LowLevelTextField.setText("");
                HighLevelTextField.setText("");
                StepTextField.setText("");
        }
        catch (InvalidInputException e){
            JOptionPane.showMessageDialog(this, "You entered "+e.getInvalidField() + '\n'+ e.getMessage(), "Error", ERROR_MESSAGE);
        }
        catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, e.getMessage() + "\nEnter a number.", "Error", ERROR_MESSAGE);
        }
    }//GEN-LAST:event_AddButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(() -> new MainWindow().setVisible(true));
    }
// 9 вариант cos(x^2)

    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton AddFromCollectionButton;
    private javax.swing.JButton CalculateButton;
    private javax.swing.JButton ClearTableButton;
    private javax.swing.JButton DeleteButton;
    private javax.swing.JLabel HighLabel;
    private javax.swing.JTextField HighLevelTextField;
    private javax.swing.JLabel LowLabel;
    private javax.swing.JTextField LowLevelTextField;
    private javax.swing.JTable ResultTable;
    private javax.swing.JLabel StepLabel;
    private javax.swing.JTextField StepTextField;
    private javax.swing.JLabel formulaLabel;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JMenuItem loadBinMenuItem;
    private javax.swing.JMenuItem loadTxtMenuItem;
    private javax.swing.JMenuItem saveBinMenuItem;
    private javax.swing.JMenuItem saveTxtMenuItem;
    // End of variables declaration//GEN-END:variables
}

