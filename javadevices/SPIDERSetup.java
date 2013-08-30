/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * SPIDERSetup.java
 *
 * Created on Feb 2, 2012, 2:19:27 PM
 */

/**
 *
 * @author manduchi
 */
public class SPIDERSetup extends DeviceSetup {

    /** Creates new form SPIDERSetup */
    public SPIDERSetup() {
        initComponents();
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        deviceButtons1 = new DeviceButtons();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        deviceField1 = new DeviceField();
        deviceField2 = new DeviceField();
        deviceField3 = new DeviceField();
        jPanel4 = new javax.swing.JPanel();
        deviceField4 = new DeviceField();
        deviceField5 = new DeviceField();
        deviceField6 = new DeviceField();
        jPanel5 = new javax.swing.JPanel();
        deviceField7 = new DeviceField();
        deviceField8 = new DeviceField();
        deviceField9 = new DeviceField();
        jPanel11 = new javax.swing.JPanel();
        deviceField14 = new DeviceField();
        deviceField15 = new DeviceField();
        deviceField16 = new DeviceField();
        jPanel2 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        deviceField10 = new DeviceField();
        deviceField11 = new DeviceField();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        deviceWave1 = new DeviceWave();
        jPanel8 = new javax.swing.JPanel();
        deviceWave2 = new DeviceWave();
        jPanel9 = new javax.swing.JPanel();
        deviceWave3 = new DeviceWave();
        jPanel10 = new javax.swing.JPanel();
        deviceWave4 = new DeviceWave();
        jPanel12 = new javax.swing.JPanel();
        deviceWave5 = new DeviceWave();

        setDeviceProvider("localhost");
        setDeviceTitle("Spider Demo Setup");
        setDeviceType("SPIDER");
        setHeight(300);
        setWidth(500);
        getContentPane().add(deviceButtons1, java.awt.BorderLayout.PAGE_END);

        jPanel1.setLayout(new java.awt.GridLayout(4, 1));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Camera Timing"));

        deviceField1.setIdentifier("");
        deviceField1.setLabelString("Start Time: ");
        deviceField1.setNumCols(4);
        deviceField1.setOffsetNid(3);
        jPanel3.add(deviceField1);

        deviceField2.setIdentifier("");
        deviceField2.setLabelString("Duration: ");
        deviceField2.setNumCols(4);
        deviceField2.setOffsetNid(4);
        jPanel3.add(deviceField2);

        deviceField3.setIdentifier("");
        deviceField3.setLabelString("Frequency: ");
        deviceField3.setNumCols(4);
        deviceField3.setOffsetNid(2);
        jPanel3.add(deviceField3);

        jPanel1.add(jPanel3);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("CAEN DT5720 Timing"));

        deviceField4.setIdentifier("");
        deviceField4.setLabelString("Start Time: ");
        deviceField4.setNumCols(4);
        deviceField4.setOffsetNid(6);
        jPanel4.add(deviceField4);

        deviceField5.setIdentifier("");
        deviceField5.setLabelString("Duration: ");
        deviceField5.setNumCols(4);
        deviceField5.setOffsetNid(7);
        jPanel4.add(deviceField5);

        deviceField6.setIdentifier("");
        deviceField6.setLabelString("Frequency: ");
        deviceField6.setNumCols(4);
        deviceField6.setOffsetNid(5);
        jPanel4.add(deviceField6);

        jPanel1.add(jPanel4);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("NI 6259 Timing"));

        deviceField7.setIdentifier("");
        deviceField7.setLabelString("Start Time: ");
        deviceField7.setNumCols(4);
        deviceField7.setOffsetNid(9);
        jPanel5.add(deviceField7);

        deviceField8.setIdentifier("");
        deviceField8.setLabelString("Duration");
        deviceField8.setNumCols(4);
        deviceField8.setOffsetNid(10);
        jPanel5.add(deviceField8);

        deviceField9.setIdentifier("");
        deviceField9.setLabelString("Frequency: ");
        deviceField9.setNumCols(4);
        deviceField9.setOffsetNid(8);
        jPanel5.add(deviceField9);

        jPanel1.add(jPanel5);

        jPanel11.setBorder(javax.swing.BorderFactory.createTitledBorder("NI 6368 Timing"));

        deviceField14.setIdentifier("");
        deviceField14.setLabelString("Start Time: ");
        deviceField14.setNumCols(4);
        deviceField14.setOffsetNid(12);
        jPanel11.add(deviceField14);

        deviceField15.setIdentifier("");
        deviceField15.setLabelString("Duration");
        deviceField15.setNumCols(4);
        deviceField15.setOffsetNid(13);
        jPanel11.add(deviceField15);

        deviceField16.setIdentifier("");
        deviceField16.setLabelString("Frequency: ");
        deviceField16.setNumCols(4);
        deviceField16.setOffsetNid(11);
        jPanel11.add(deviceField16);

        jPanel1.add(jPanel11);

        jTabbedPane1.addTab("Timing", jPanel1);

        jPanel2.setLayout(new java.awt.BorderLayout());

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Breakdown Management"));

        deviceField10.setIdentifier("");
        deviceField10.setLabelString("Dead time Time: ");
        deviceField10.setNumCols(4);
        deviceField10.setOffsetNid(14);
        jPanel6.add(deviceField10);

        deviceField11.setIdentifier("");
        deviceField11.setLabelString("Recover time: ");
        deviceField11.setNumCols(4);
        deviceField11.setOffsetNid(15);
        jPanel6.add(deviceField11);

        jPanel2.add(jPanel6, java.awt.BorderLayout.PAGE_START);

        jPanel7.setLayout(new java.awt.BorderLayout());

        deviceWave1.setIdentifier("");
        deviceWave1.setOffsetNid(17);
        deviceWave1.setUpdateExpression("");
        jPanel7.add(deviceWave1, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Wave 1", jPanel7);

        jPanel8.setLayout(new java.awt.BorderLayout());

        deviceWave2.setIdentifier("");
        deviceWave2.setOffsetNid(23);
        deviceWave2.setUpdateExpression("");
        jPanel8.add(deviceWave2, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Wave 2", jPanel8);

        jPanel9.setLayout(new java.awt.BorderLayout());

        deviceWave3.setIdentifier("");
        deviceWave3.setOffsetNid(29);
        deviceWave3.setUpdateExpression("");
        jPanel9.add(deviceWave3, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Wave 3", jPanel9);

        jPanel10.setLayout(new java.awt.BorderLayout());

        deviceWave4.setIdentifier("");
        deviceWave4.setOffsetNid(35);
        deviceWave4.setUpdateExpression("");
        jPanel10.add(deviceWave4, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Wave 4", jPanel10);

        jPanel12.setLayout(new java.awt.BorderLayout());

        deviceWave5.setIdentifier("");
        deviceWave5.setOffsetNid(41);
        deviceWave5.setUpdateExpression("");
        jPanel12.add(deviceWave5, java.awt.BorderLayout.CENTER);

        jTabbedPane2.addTab("Recover Wave", jPanel12);

        jPanel2.add(jTabbedPane2, java.awt.BorderLayout.CENTER);

        jTabbedPane1.addTab("Waveforms", jPanel2);

        getContentPane().add(jTabbedPane1, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private DeviceButtons deviceButtons1;
    private DeviceField deviceField1;
    private DeviceField deviceField10;
    private DeviceField deviceField11;
    private DeviceField deviceField14;
    private DeviceField deviceField15;
    private DeviceField deviceField16;
    private DeviceField deviceField2;
    private DeviceField deviceField3;
    private DeviceField deviceField4;
    private DeviceField deviceField5;
    private DeviceField deviceField6;
    private DeviceField deviceField7;
    private DeviceField deviceField8;
    private DeviceField deviceField9;
    private DeviceWave deviceWave1;
    private DeviceWave deviceWave2;
    private DeviceWave deviceWave3;
    private DeviceWave deviceWave4;
    private DeviceWave deviceWave5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables

}
