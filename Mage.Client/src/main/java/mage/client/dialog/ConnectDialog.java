/*
 * Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and should not be interpreted as representing official policies, either expressed
 * or implied, of BetaSteward_at_googlemail.com.
 */

 /*
 * ConnectDialog.java
 *
 * Created on 20-Jan-2010, 9:37:07 PM
 */
package mage.client.dialog;

import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import mage.choices.Choice;
import mage.choices.ChoiceImpl;
import mage.client.MageFrame;
import static mage.client.dialog.PreferencesDialog.KEY_CONNECTION_URL_SERVER_LIST;
import static mage.client.dialog.PreferencesDialog.KEY_CONNECT_AUTO_CONNECT;
import static mage.client.dialog.PreferencesDialog.KEY_CONNECT_FLAG;
import mage.client.preference.MagePreferences;
import mage.client.util.Config;
import mage.client.util.gui.countryBox.CountryItemEditor;
import mage.client.util.sets.ConstructedFormats;
import mage.remote.Connection;
import org.apache.log4j.Logger;

/**
 * @author BetaSteward_at_googlemail.com
 */
public class ConnectDialog extends MageDialog {

    private static final Logger logger = Logger.getLogger(ConnectDialog.class);
    private Connection connection;
    private ConnectTask task;
    private final RegisterUserDialog registerUserDialog;
    private final ResetPasswordDialog resetPasswordDialog;

    private final ActionListener connectAction = evt -> btnConnectActionPerformed(evt);

    /**
     * Creates new form ConnectDialog
     */
    public ConnectDialog() {
        initComponents();

        this.txtServer.addActionListener(connectAction);
        this.txtPort.addActionListener(connectAction);
        this.txtUserName.addActionListener(connectAction);
        this.txtPassword.addActionListener(connectAction);

        registerUserDialog = new RegisterUserDialog(this);
        MageFrame.getDesktop().add(registerUserDialog, JLayeredPane.MODAL_LAYER);

        resetPasswordDialog = new ResetPasswordDialog(this);
        MageFrame.getDesktop().add(resetPasswordDialog, JLayeredPane.MODAL_LAYER);
    }

    public void showDialog() {
        String serverAddress = MagePreferences.getServerAddressWithDefault(Config.serverName);
        this.txtServer.setText(serverAddress);
        this.txtPort.setText(Integer.toString(MagePreferences.getServerPortWithDefault(Config.port)));
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));
        this.chkAutoConnect.setSelected(Boolean.parseBoolean(MageFrame.getPreferences().get(KEY_CONNECT_AUTO_CONNECT, "false")));
        this.chkForceUpdateDB.setSelected(false); // has always to be set manually to force comparison

        String selectedFlag = MageFrame.getPreferences().get(KEY_CONNECT_FLAG, "world");
        // set the selected country/flag
        for (int i = 0; i < cbFlag.getItemCount(); i++) {
            String[] name = (String[]) cbFlag.getItemAt(i);
            if (name[1].equals(selectedFlag)) {
                cbFlag.setSelectedIndex(i);
                break;
            }
        }
        this.setModal(true);
        this.setLocation(50, 50);
        this.setVisible(true);
    }

    private void saveSettings() {
        String serverAddress = txtServer.getText().trim();
        MagePreferences.setServerAddress(serverAddress);
        MagePreferences.setServerPort(Integer.parseInt(txtPort.getText().trim()));
        MagePreferences.setUserName(serverAddress, txtUserName.getText().trim());
        MagePreferences.setPassword(serverAddress, txtPassword.getText().trim());
        MageFrame.getPreferences().put(KEY_CONNECT_AUTO_CONNECT, Boolean.toString(chkAutoConnect.isSelected()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblServer = new javax.swing.JLabel();
        txtServer = new javax.swing.JTextField();
        btnFind = new javax.swing.JButton();
        lblPort = new javax.swing.JLabel();
        txtPort = new javax.swing.JTextField();
        lblUserName = new javax.swing.JLabel();
        txtUserName = new javax.swing.JTextField();
        lblPassword = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();
        lblFlag = new javax.swing.JLabel();
        chkAutoConnect = new javax.swing.JCheckBox();
        chkForceUpdateDB = new javax.swing.JCheckBox();
        jProxySettingsButton = new javax.swing.JButton();
        btnConnect = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        lblStatus = new javax.swing.JLabel();
        btnRegister = new javax.swing.JButton();
        btnForgotPassword = new javax.swing.JButton();
        btnFind1 = new javax.swing.JButton();
        btnFind2 = new javax.swing.JButton();
        btnFind3 = new javax.swing.JButton();
        lblFastConnect = new javax.swing.JLabel();
        panelFlag = new javax.swing.JPanel();
        cbFlag = new mage.client.util.gui.countryBox.CountryComboBox();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(5, 0), new java.awt.Dimension(4, 0), new java.awt.Dimension(5, 32767));
        btnFlagSearch = new javax.swing.JButton();

        setTitle("Connect to server");
        setNormalBounds(new java.awt.Rectangle(100, 100, 410, 307));

        lblServer.setLabelFor(txtServer);
        lblServer.setText("Server:");

        btnFind.setText("Find...");
        btnFind.setToolTipText("Shows the list of public servers");
        btnFind.setName("findServerBtn"); // NOI18N
        btnFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                findPublicServerActionPerformed(evt);
            }
        });

        lblPort.setLabelFor(txtPort);
        lblPort.setText("Port:");

        txtPort.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                ConnectDialog.this.keyTyped(evt);
            }
        });

        lblUserName.setLabelFor(txtUserName);
        lblUserName.setText("User name:");

        lblPassword.setLabelFor(txtPassword);
        lblPassword.setText("Password:");

        lblFlag.setLabelFor(txtUserName);
        lblFlag.setText("User flag:");

        chkAutoConnect.setText("Automatically connect to this server next time");
        chkAutoConnect.setToolTipText("<HTML>If active this connect dialog will not be shown if you choose to connect.<br>\nInstead XMage tries to connect to the last server you were connected to.");
        chkAutoConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkAutoConnectActionPerformed(evt);
            }
        });

        chkForceUpdateDB.setText("Force update of card database");
        chkForceUpdateDB.setToolTipText("<HTML>If active the comparison of the server cards database to the client database will be enforced.<br>If not, the comparison will only done if the database version of the client is lower than the version of the server.");
        chkForceUpdateDB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkForceUpdateDBActionPerformed(evt);
            }
        });

        jProxySettingsButton.setText("Proxy Settings...");
        jProxySettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jProxySettingsButtonActionPerformed(evt);
            }
        });

        btnConnect.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnConnect.setText("Connect to server");
        btnConnect.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnConnect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConnectActionPerformed(evt);
            }
        });

        btnCancel.setText("Cancel");
        btnCancel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCancel.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        btnRegister.setText("Register new user...");
        btnRegister.setToolTipText("<html>XMage now supports user authentication.<br>Register your account before you log in.<html>");
        btnRegister.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegisterActionPerformed(evt);
            }
        });

        btnForgotPassword.setText("Forgot password...");
        btnForgotPassword.setToolTipText("<html>You can reset your password if you have registered<br>your account with an email address.</html>");
        btnForgotPassword.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnForgotPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnForgotPasswordActionPerformed(evt);
            }
        });

        btnFind1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flags/de.png"))); // NOI18N
        btnFind1.setText("X");
        btnFind1.setToolTipText("Connect to xmage.de (Europe, most popular)");
        btnFind1.setActionCommand("connectXmageDe");
        btnFind1.setAlignmentY(0.0F);
        btnFind1.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFind1.setMaximumSize(new java.awt.Dimension(42, 23));
        btnFind1.setMinimumSize(new java.awt.Dimension(42, 23));
        btnFind1.setName("connectXmageDeBtn"); // NOI18N
        btnFind1.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFind1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectXmageDe(evt);
            }
        });

        btnFind2.setText("L");
        btnFind2.setToolTipText("Connect to localhost (local server)");
        btnFind2.setActionCommand("connectLocalhost");
        btnFind2.setAlignmentY(0.0F);
        btnFind2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnFind2.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFind2.setName("connectLocalhostBtn"); // NOI18N
        btnFind2.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFind2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectLocalhost(evt);
            }
        });

        btnFind3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/flags/us.png"))); // NOI18N
        btnFind3.setText("W");
        btnFind3.setToolTipText("Connect to Woogerworks (USA)");
        btnFind3.setActionCommand("connectWoogerworks");
        btnFind3.setAlignmentY(0.0F);
        btnFind3.setMargin(new java.awt.Insets(2, 2, 2, 2));
        btnFind3.setName("connectWoogerworksBtn"); // NOI18N
        btnFind3.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFind3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectWoogerworks(evt);
            }
        });

        lblFastConnect.setLabelFor(btnFind1);
        lblFastConnect.setText("Fast connect to:");
        lblFastConnect.setName(""); // NOI18N

        panelFlag.setPreferredSize(new java.awt.Dimension(189, 30));
        panelFlag.setLayout(new javax.swing.BoxLayout(panelFlag, javax.swing.BoxLayout.LINE_AXIS));

        cbFlag.setEditable(true);
        cbFlag.setMaximumRowCount(16);
        cbFlag.setAlignmentX(0.0F);
        cbFlag.setMinimumSize(new java.awt.Dimension(50, 18));
        cbFlag.setPreferredSize(new java.awt.Dimension(278, 15));
        panelFlag.add(cbFlag);
        panelFlag.add(filler1);

        btnFlagSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/buttons/search_24.png"))); // NOI18N
        btnFlagSearch.setToolTipText("Fast search your flag");
        btnFlagSearch.setAlignmentX(1.0F);
        btnFlagSearch.setPreferredSize(new java.awt.Dimension(23, 23));
        btnFlagSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFlagSearchActionPerformed(evt);
            }
        });
        panelFlag.add(btnFlagSearch);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(29, 29, 29)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(lblPort)
                                .addComponent(lblServer)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(lblFlag)))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblUserName)
                            .addComponent(lblPassword, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblStatus, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(chkForceUpdateDB, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jProxySettingsButton)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(chkAutoConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelFlag, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtServer, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtUserName, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtPassword, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                                .addComponent(lblFastConnect)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFind1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFind3, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnFind2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, 0)
                        .addComponent(btnFind)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblServer)
                    .addComponent(txtServer, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPort)
                    .addComponent(btnFind1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFind3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFastConnect))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUserName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblUserName))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPassword))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelFlag, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFlag, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chkAutoConnect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkForceUpdateDB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jProxySettingsButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnRegister, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnForgotPassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnConnect, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCancel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(23, 23, 23))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        MageFrame.getPreferences().put("autoConnect", Boolean.toString(chkAutoConnect.isSelected()));
        MageFrame.getPreferences().put(KEY_CONNECT_FLAG, ((CountryItemEditor) cbFlag.getEditor()).getImageItem());
        if (task != null && !task.isDone()) {
            task.cancel(true);
        } else {
            this.hideDialog();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    private void btnConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConnectActionPerformed

        if (txtServer.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a server address");
            return;
        }
        if (txtPort.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a port number");
            return;
        }
        if (txtUserName.getText().isEmpty()) {
            JOptionPane.showMessageDialog(rootPane, "Please provide a user name");
            return;
        }
        // txtPassword is not checked here, because authentication might be disabled by the server config.
        if (Integer.valueOf(txtPort.getText()) < 1 || Integer.valueOf(txtPort.getText()) > 65535) {
            JOptionPane.showMessageDialog(rootPane, "Invalid port number");
            txtPort.setText(Integer.toString(MagePreferences.getServerPortWithDefault(Config.port)));
            return;
        }

        char[] input = new char[0];
        try {
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            connection = new Connection();
            connection.setHost(this.txtServer.getText().trim());
            connection.setPort(Integer.valueOf(this.txtPort.getText().trim()));
            connection.setUsername(this.txtUserName.getText().trim());
            connection.setPassword(this.txtPassword.getText().trim());
            connection.setForceDBComparison(this.chkForceUpdateDB.isSelected());
            String allMAC = "";
            try {
                allMAC = connection.getMAC();
            } catch (SocketException ex) {
            }
            connection.setUserIdStr(System.getProperty("user.name") + ":" + System.getProperty("os.name") + ":" + MagePreferences.getUserNames() + ":" + allMAC);
            MageFrame.getPreferences().put(KEY_CONNECT_FLAG, ((CountryItemEditor) cbFlag.getEditor()).getImageItem());
            PreferencesDialog.setProxyInformation(connection);

            // pref settings
            MageFrame.getInstance().setUserPrefsToConnection(connection);

            logger.debug("connecting: " + connection.getProxyType() + ' ' + connection.getProxyHost() + ' ' + connection.getProxyPort());
            task = new ConnectTask();
            task.execute();
        } finally {
            Arrays.fill(input, '0');
        }

    }//GEN-LAST:event_btnConnectActionPerformed

    private class ConnectTask extends SwingWorker<Boolean, Void> {

        private boolean result = false;

        private static final int CONNECTION_TIMEOUT_MS = 2100;

        @Override
        protected Boolean doInBackground() throws Exception {
            lblStatus.setText("Connecting...");
            btnConnect.setEnabled(false);
            result = MageFrame.connect(connection);
            return result;
        }

        @Override
        protected void done() {
            try {
                get(CONNECTION_TIMEOUT_MS, TimeUnit.MILLISECONDS);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                if (result) {
                    lblStatus.setText("");
                    connected();
                    MageFrame.getInstance().prepareAndShowTablesPane();
                } else {
                    lblStatus.setText("Could not connect");
                }
            } catch (InterruptedException ex) {
                logger.fatal("Update Players Task error", ex);
            } catch (ExecutionException ex) {
                logger.fatal("Update Players Task error", ex);
            } catch (CancellationException ex) {
                logger.info("Connect was canceled");
                lblStatus.setText("Connect was canceled");
            } catch (TimeoutException ex) {
                logger.fatal("Connection timeout: ", ex);
            } finally {
                MageFrame.stopConnecting();
                btnConnect.setEnabled(true);
            }
        }
    }

    private void connected() {
        this.saveSettings();
        this.hideDialog();
        ConstructedFormats.ensureLists();
    }

    private void keyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_keyTyped
        char c = evt.getKeyChar();
        if (!Character.isDigit(c)) {
            evt.consume();
        }
    }//GEN-LAST:event_keyTyped

    private void chkAutoConnectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkAutoConnectActionPerformed

        // TODO add your handling code here:
    }//GEN-LAST:event_chkAutoConnectActionPerformed

    private void findPublicServerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BufferedReader in = null;
        try {
            String serverUrl = PreferencesDialog.getCachedValue(KEY_CONNECTION_URL_SERVER_LIST, "http://xmage.de/files/server-list.txt");
            if (serverUrl.contains("xmage.info/files/")) {
                serverUrl = serverUrl.replace("xmage.info/files/", "xmage.de/files/"); // replace old URL if still saved
                PreferencesDialog.saveValue(KEY_CONNECTION_URL_SERVER_LIST, serverUrl);
            }
            URL serverListURL = new URL(serverUrl);

            Connection.ProxyType configProxyType = Connection.ProxyType.valueByText(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_TYPE, "None"));
            Proxy p = null;
            Proxy.Type type = Proxy.Type.DIRECT;
            switch (configProxyType) {
                case HTTP:
                    type = Proxy.Type.HTTP;
                    break;
                case SOCKS:
                    type = Proxy.Type.SOCKS;
                    break;
                case NONE:
                default:
                    p = Proxy.NO_PROXY;
                    break;
            }

            if (p == null || !p.equals(Proxy.NO_PROXY)) {
                try {
                    String address = PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_ADDRESS, "");
                    Integer port = Integer.parseInt(PreferencesDialog.getCachedValue(PreferencesDialog.KEY_PROXY_PORT, "80"));
                    p = new Proxy(type, new InetSocketAddress(address, port));
                } catch (Exception ex) {
                    throw new RuntimeException("Gui_DownloadPictures : error 1 - " + ex);
                }
            }

            if (p == null) {
                JOptionPane.showMessageDialog(null, "Couldn't configure Proxy object!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean URLNotFound = false;
            try {
                in = new BufferedReader(new InputStreamReader(serverListURL.openConnection(p).getInputStream()));
            } catch (SocketTimeoutException | FileNotFoundException | UnknownHostException ex) {
                logger.warn("Could not read serverlist from: " + serverListURL.toString());
                File f = new File("serverlist.txt");
                if (f.exists() && !f.isDirectory()) {
                    logger.info("Using buffered serverlist: serverlist.txt");
                    URLNotFound = true;
                    in = new BufferedReader(new FileReader("serverlist.txt"));
                }
            }
            List<String> servers = new ArrayList<>();
            if (in != null) {
                Writer output = null;
                if (!URLNotFound) {
                    // write serverlist to be able to read if URL is not available
                    File file = new File("serverlist.txt");
                    if (file.exists() && !file.isDirectory()) {
                        file.delete();
                    }
                    output = new BufferedWriter(new FileWriter(file));
                }

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    logger.debug("Found server: " + inputLine);
                    servers.add(inputLine);
                    if (output != null) {
                        output.append(inputLine).append('\n');

                    }
                }
                if (output != null) {
                    output.close();
                }
                in.close();
            }
            if (servers.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Couldn't find any server.");
                return;
            }

            String selectedServer = (String) JOptionPane.showInputDialog(null,
                    "Choose XMage Public Server:", "Input",
                    JOptionPane.INFORMATION_MESSAGE, null, servers.toArray(),
                    servers.get(0));
            if (selectedServer != null) {
                String[] params = selectedServer.split(":");
                if (params.length == 3) {
                    String serverAddress = params[1];
                    this.txtServer.setText(serverAddress);
                    this.txtPort.setText(params[2]);
                    // Update userName and password according to the chosen server.
                    this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
                    this.txtPassword.setText(MagePreferences.getPassword(serverAddress));
                } else {
                    JOptionPane.showMessageDialog(null, "Wrong server data format.");
                }
            }

        } catch (Exception ex) {
            logger.error(ex, ex);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jProxySettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jProxySettingsButtonActionPerformed
        PreferencesDialog.main(new String[]{PreferencesDialog.OPEN_CONNECTION_TAB});
    }//GEN-LAST:event_jProxySettingsButtonActionPerformed

    private void chkForceUpdateDBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkForceUpdateDBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_chkForceUpdateDBActionPerformed

    private void txtUserNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUserNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUserNameActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegisterActionPerformed
        registerUserDialog.showDialog();
    }//GEN-LAST:event_btnRegisterActionPerformed

    private void btnForgotPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnForgotPasswordActionPerformed
        resetPasswordDialog.showDialog();
    }//GEN-LAST:event_btnForgotPasswordActionPerformed

    private void connectXmageDe(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind1findPublicServerActionPerformed
        String serverAddress = "xmage.de";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));

    }//GEN-LAST:event_btnFind1findPublicServerActionPerformed

    private void connectLocalhost(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFind2findPublicServerActionPerformed
        String serverAddress = "localhost";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));

    }//GEN-LAST:event_btnFind2findPublicServerActionPerformed

    private void connectWoogerworks(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectWoogerworks
        String serverAddress = "xmage.woogerworks.com";
        this.txtServer.setText(serverAddress);
        this.txtPort.setText("17171");
        // Update userName and password according to the chosen server.
        this.txtUserName.setText(MagePreferences.getUserName(serverAddress));
        this.txtPassword.setText(MagePreferences.getPassword(serverAddress));
    }//GEN-LAST:event_connectWoogerworks

    private void btnFlagSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFlagSearchActionPerformed
        doFastFlagSearch();
    }//GEN-LAST:event_btnFlagSearchActionPerformed

    private void doFastFlagSearch(){
        Choice choice = new ChoiceImpl(false);

        // collect data from country combobox String[name][code]
        Map<String, String> choiceItems = new LinkedHashMap<>();
        DefaultComboBoxModel flagModel = (DefaultComboBoxModel)cbFlag.getModel();
        String[] flagItem;
        
        for(int i = 0; i < flagModel.getSize(); i++){
            flagItem = (String[])flagModel.getElementAt(i);
            choiceItems.put(flagItem[1], flagItem[0]);
        }
        
        choice.setKeyChoices(choiceItems);
        choice.setMessage("Select your country");
        
        // current selection value restore
        String needSelectValue = null;
        flagItem = (String[])flagModel.getSelectedItem();
        if (flagItem != null){
            needSelectValue = flagItem[1];            
        }

        // ask for new value
        PickChoiceDialog dlg = new PickChoiceDialog();
        dlg.setWindowSize(300, 500);
        dlg.showDialog(choice, needSelectValue);
        if(choice.isChosen()){
            flagItem = new String[2];
            flagItem[0] = choice.getChoiceValue();
            flagItem[1] = choice.getChoiceKey();            
            flagModel.setSelectedItem(flagItem);            
        }
    }
            
    public String getServer() {
        return this.txtServer.getText();
    }

    public String getPort() {
        return this.txtPort.getText();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConnect;
    private javax.swing.JButton btnFind;
    private javax.swing.JButton btnFind1;
    private javax.swing.JButton btnFind2;
    private javax.swing.JButton btnFind3;
    private javax.swing.JButton btnFlagSearch;
    private javax.swing.JButton btnForgotPassword;
    private javax.swing.JButton btnRegister;
    private mage.client.util.gui.countryBox.CountryComboBox cbFlag;
    private javax.swing.JCheckBox chkAutoConnect;
    private javax.swing.JCheckBox chkForceUpdateDB;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jProxySettingsButton;
    private javax.swing.JLabel lblFastConnect;
    private javax.swing.JLabel lblFlag;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblServer;
    private javax.swing.JLabel lblStatus;
    private javax.swing.JLabel lblUserName;
    private javax.swing.JPanel panelFlag;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtServer;
    private javax.swing.JTextField txtUserName;
    // End of variables declaration//GEN-END:variables

}
