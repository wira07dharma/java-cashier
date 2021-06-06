/*
 * LogDialog.java
 *
 * Created on March 10, 2006, 2:02 PM
 */

package com.dimata.pos.cashier;

import java.util.Vector;

/**
 *
 * @author  pulantara
 */
public class LogDialog extends javax.swing.JDialog {
    
    /** Creates new form LogDialog */
    public LogDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    
    /** Creates new form LogDialog */
    public LogDialog(java.awt.Frame parent, boolean modal,String[] logs) {
        super(parent, modal);
        initComponents();
        setLogs(logs);
    }
    
    /** Creates new form LogDialog */
     public LogDialog(java.awt.Frame parent, boolean modal,String logs) {
        super(parent, modal);
        initComponents();
        setLogs(logs);
    }
     
    /** Creates new form LogDialog */
     public LogDialog(java.awt.Frame parent, boolean modal,Vector logs) {
        super(parent, modal);
        initComponents();
        setLogs(logs);
    } 
    
    public void setLogs(String[] logs){
        logTextArea.setText("");
        for(int i=0; i<logs.length; i++){ 
            logTextArea.append(logs[i]+"\n");
        }
    }
    
    public void setLogs(Vector logs){
        logTextArea.setText("");
        for(int i=0; i<logs.size(); i++){
            logTextArea.append(logs.get(i)+"\n");
        }
    }
    
    public void setLogs(String log){
        logTextArea.setText(log);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents
        jScrollPane1 = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Log Dialog");
        jScrollPane1.setViewportView(logTextArea);

        getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-400)/2, (screenSize.height-300)/2, 400, 300);
    }//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        new LogDialog(new javax.swing.JFrame(), true).show();
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea logTextArea;
    // End of variables declaration//GEN-END:variables
    
}
