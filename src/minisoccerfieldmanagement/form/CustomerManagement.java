package minisoccerfieldmanagement.form;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Component;
import java.awt.Image;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableRowSorter;
import minisoccerfieldmanagement.model.Customer;
import minisoccerfieldmanagement.service.CustomerServiceImpl;
import minisoccerfieldmanagement.service.ICustomerService;
import minisoccerfieldmanagement.tabbed.TabbedForm;
import raven.alerts.MessageAlerts;
import raven.popup.component.PopupController;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.SwingConstants;
import javax.swing.table.TableCellRenderer;
import minisoccerfieldmanagement.model.Booking;
import minisoccerfieldmanagement.model.MemberShip;
import minisoccerfieldmanagement.service.BookingServiceImpl;
import minisoccerfieldmanagement.service.IBookingService;
import minisoccerfieldmanagement.service.IMemberShipService;
import minisoccerfieldmanagement.service.MemberShipServiceImpl;

public class CustomerManagement extends TabbedForm {

    private int index;
    private File tempPicture = null;
    ICustomerService customerService;
    IMemberShipService membershipService;
    IBookingService bookingService;
    DefaultTableModel customerModels;

    public CustomerManagement() {
        initComponents();
        FlatLaf.registerCustomDefaultsSource("tableview");
        customerService = new CustomerServiceImpl();
        membershipService = new MemberShipServiceImpl();
        bookingService = new BookingServiceImpl();
        customerModels = (DefaultTableModel) tblCustomer.getModel();
        applyTableStyle(tblCustomer);
        index = -1;
        loadDataCustomerManagement();
    }

    private void loadListBookingHistory(int customerId) {
        List<Booking> bookings = bookingService.findByCustomer(customerId);
        bookingHistorySection1.addData(bookings);
    }

    private void clearBookingHistory() {
        bookingHistorySection1.clearData();
    }

    private void applyTableStyle(JTable table) {
        btnAddNew.setIcon(new FlatSVGIcon("minisoccerfieldmanagement/drawer/icon/add.svg", 0.35f));
        btnSave.setIcon(new FlatSVGIcon("minisoccerfieldmanagement/drawer/icon/edit.svg", 0.35f));
        btnDelete.setIcon(new FlatSVGIcon("minisoccerfieldmanagement/drawer/icon/delete.svg", 0.35f));
        btnPrint.setIcon(new FlatSVGIcon("minisoccerfieldmanagement/drawer/icon/excel.svg", 0.35f));
        btnUpload.setIcon(new FlatSVGIcon("minisoccerfieldmanagement/drawer/icon/upload.svg", 0.35f));
        txtSearch.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new FlatSVGIcon("minisoccerfieldmanagement/drawer/icon/search.svg", 0.35f));
        JScrollPane scroll = (JScrollPane) table.getParent().getParent();
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "background:$Table.background;"
                + "track:$Table.background;"
                + "trackArc:999");
        table.getTableHeader().putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        table.putClientProperty(FlatClientProperties.STYLE_CLASS, "table_style");
        table.getTableHeader().setDefaultRenderer(getAlignmentCellRender(table.getTableHeader().getDefaultRenderer(), true));
        table.setDefaultRenderer(Object.class, getAlignmentCellRender(table.getDefaultRenderer(Object.class), false));
        tfTotalSpend.setText("0");
        tfTotalSpend.setEnabled(false);
    }

    private void loadDataCustomerManagement() {
        customerModels.setRowCount(0);
        List<Customer> customers = new ArrayList<>();
        customers = customerService.findAll();
        for (Customer customer : customers) {
            customerModels.addRow(getRow(customer));
        }
    }

    private TableCellRenderer getAlignmentCellRender(TableCellRenderer oldRender, boolean header) {
        return new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component com = oldRender.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (com instanceof JLabel) {
                    JLabel label = (JLabel) com;
                    if (column == 0 || column == 3) {
                        label.setHorizontalAlignment(SwingConstants.CENTER);
                    } else if (column == 4) {
                        label.setHorizontalAlignment(SwingConstants.TRAILING);
                    } else {
                        label.setHorizontalAlignment(SwingConstants.LEADING);
                    }
                    if (header == false) {
                        if (isSelected) {
                            com.setForeground(table.getSelectionForeground());
                        } else {
                            com.setForeground(table.getForeground());
                        }

                    }
                }
                return com;
            }
        };
    }

    private Object[] getRow(Customer customer) {
        DecimalFormat df = new DecimalFormat("#,##0.##");
        String membershipName = "";
        MemberShip membership = membershipService.findById(customer.getMemberShipId());
        if (membership != null) {
            membershipName = membership.getName();
        }
        return new Object[]{customer.getId(), customer.getName(), membershipName, customer.getPhoneNumber(), df.format(customer.getTotalSpend()) + " VND"};
    }

    private void clearText() {
        lblId.setText("#Id");
        tfName.setText("");
        tfPhoneNumber.setText("");
        tfTotalSpend.setText("");
        tfName.requestFocus();
        tempPicture = null;
        ptbCustomerImage.setImage(new ImageIcon("src/minisoccerfieldmanagement/image/empty_service.png"));
        ptbCustomerImage.repaint();
        index = -1;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        crazyPanel1 = new raven.crazypanel.CrazyPanel();
        crazyPanel2 = new raven.crazypanel.CrazyPanel();
        txtSearch = new javax.swing.JTextField();
        btnPrint = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblCustomer = new javax.swing.JTable();
        crazyPanel3 = new raven.crazypanel.CrazyPanel();
        lblCustomer = new javax.swing.JLabel();
        btnUpload = new javax.swing.JButton();
        lblName = new javax.swing.JLabel();
        tfName = new javax.swing.JTextField();
        lblPhoneNumber = new javax.swing.JLabel();
        tfPhoneNumber = new javax.swing.JTextField();
        lblTotalSpend = new javax.swing.JLabel();
        tfTotalSpend = new javax.swing.JTextField();
        btnAddNew = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnDelete = new javax.swing.JButton();
        lblId = new javax.swing.JLabel();
        ptbCustomerImage = new minisoccerfieldmanagement.util.PictureBox();
        crazyPanel4 = new raven.crazypanel.CrazyPanel();
        lblBookingHistory = new javax.swing.JLabel();
        bookingHistorySection1 = new minisoccerfieldmanagement.util.BookingHistorySection();

        setMaximumSize(new java.awt.Dimension(1188, 696));
        setPreferredSize(new java.awt.Dimension(1188, 696));

        crazyPanel1.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel1.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "wrap,fill,insets 15",
            "[fill]",
            "[grow 0][fill]",
            null
        ));

        crazyPanel2.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background",
            new String[]{
                "JTextField.placeholderText=Search;background:@background",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1",
                "background:lighten(@background,8%);borderWidth:1"
            }
        ));
        crazyPanel2.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "[]push[][]",
            "",
            new String[]{
                "width 200"
            }
        ));

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });
        crazyPanel2.add(txtSearch);

        btnPrint.setText("Print");
        crazyPanel2.add(btnPrint);

        crazyPanel1.add(crazyPanel2);

        tblCustomer.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Membership", "PhoneNumber", "TotalSpend"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblCustomer.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblCustomerMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCustomer);

        crazyPanel1.add(jScrollPane1);

        crazyPanel3.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));
        crazyPanel3.setMigLayoutConstraints(new raven.crazypanel.MigLayoutConstraints(
            "",
            "",
            "",
            null
        ));
        crazyPanel3.setName(""); // NOI18N

        lblCustomer.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblCustomer.setText("Customer");

        btnUpload.setText("Upload");
        btnUpload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUploadActionPerformed(evt);
            }
        });

        lblName.setText("Name");

        lblPhoneNumber.setText("PhoneNumber");

        lblTotalSpend.setText("TotalSpend");

        btnAddNew.setText("Add New");
        btnAddNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddNewActionPerformed(evt);
            }
        });

        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblId.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblId.setForeground(new java.awt.Color(195, 204, 90));
        lblId.setText("#Id");

        ptbCustomerImage.setImage(new javax.swing.ImageIcon(getClass().getResource("/minisoccerfieldmanagement/image/empty_service.png"))); // NOI18N

        javax.swing.GroupLayout crazyPanel3Layout = new javax.swing.GroupLayout(crazyPanel3);
        crazyPanel3.setLayout(crazyPanel3Layout);
        crazyPanel3Layout.setHorizontalGroup(
            crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel3Layout.createSequentialGroup()
                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(crazyPanel3Layout.createSequentialGroup()
                        .addContainerGap(19, Short.MAX_VALUE)
                        .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel3Layout.createSequentialGroup()
                                .addComponent(btnAddNew)
                                .addGap(18, 18, 18)
                                .addComponent(btnSave)
                                .addGap(18, 18, 18)
                                .addComponent(btnDelete))
                            .addGroup(crazyPanel3Layout.createSequentialGroup()
                                .addComponent(lblTotalSpend)
                                .addGap(38, 38, 38)
                                .addComponent(tfTotalSpend, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(crazyPanel3Layout.createSequentialGroup()
                                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPhoneNumber)
                                    .addComponent(lblName))
                                .addGap(18, 18, 18)
                                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(tfPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, crazyPanel3Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(crazyPanel3Layout.createSequentialGroup()
                                .addGap(31, 31, 31)
                                .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(crazyPanel3Layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(btnUpload))
                            .addComponent(ptbCustomerImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(26, 26, 26))
        );
        crazyPanel3Layout.setVerticalGroup(
            crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel3Layout.createSequentialGroup()
                        .addComponent(lblCustomer, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, crazyPanel3Layout.createSequentialGroup()
                        .addComponent(ptbCustomerImage, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addComponent(btnUpload)
                .addGap(18, 18, 18)
                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblName))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblPhoneNumber)
                    .addComponent(tfPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTotalSpend)
                    .addComponent(tfTotalSpend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(26, 26, 26)
                .addGroup(crazyPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddNew)
                    .addComponent(btnSave)
                    .addComponent(btnDelete))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        crazyPanel4.setFlatLafStyleComponent(new raven.crazypanel.FlatLafStyleComponent(
            "background:$Table.background;[light]border:0,0,0,0,shade(@background,5%),,20;[dark]border:0,0,0,0,tint(@background,5%),,20",
            null
        ));

        lblBookingHistory.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBookingHistory.setText("Booking History");

        javax.swing.GroupLayout crazyPanel4Layout = new javax.swing.GroupLayout(crazyPanel4);
        crazyPanel4.setLayout(crazyPanel4Layout);
        crazyPanel4Layout.setHorizontalGroup(
            crazyPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel4Layout.createSequentialGroup()
                .addGroup(crazyPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(crazyPanel4Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(bookingHistorySection1, javax.swing.GroupLayout.PREFERRED_SIZE, 315, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(crazyPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblBookingHistory)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        crazyPanel4Layout.setVerticalGroup(
            crazyPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(crazyPanel4Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblBookingHistory)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bookingHistorySection1, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(13, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(crazyPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 818, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(crazyPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(crazyPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(8, 8, 8))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(crazyPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(crazyPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(crazyPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblCustomerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblCustomerMouseClicked
        index = tblCustomer.getSelectedRow();
        if (index != -1) {
            tempPicture = null;
            clearBookingHistory();
            lblId.setText("#" + customerModels.getValueAt(index, 0).toString());
            tfName.setText(customerModels.getValueAt(index, 1).toString());
            tfPhoneNumber.setText(customerModels.getValueAt(index, 3).toString());
            tfTotalSpend.setText(customerModels.getValueAt(index, 4).toString().replace(" VND", "").replace(",", ""));
            Customer customer = customerService.findById(Integer.parseInt(customerModels.getValueAt(index, 0).toString()));
            int customerId = Integer.parseInt(customerModels.getValueAt(index, 0).toString());
            loadListBookingHistory(customerId);
            if (customer.getImage() == null) {
                ptbCustomerImage.setImage(new ImageIcon("src/minisoccerfieldmanagement/image/empty_service.png"));
                ptbCustomerImage.repaint();
            } else {
                File file = new File("src/minisoccerfieldmanagement/image/customer/" + customer.getImage());
                if (!file.exists()) {
                    ptbCustomerImage.setImage(new ImageIcon("src/minisoccerfieldmanagement/image/empty_service.png"));
                    ptbCustomerImage.repaint();
                } else {
                    ptbCustomerImage.setImage(new ImageIcon("src/minisoccerfieldmanagement/image/customer/" + customer.getImage()));
                    ptbCustomerImage.repaint();
                }
            }
            System.out.println("pictureBox " + ptbCustomerImage.getImage().toString());
            File file = new File(ptbCustomerImage.getImage().toString());
            System.out.println("pictureBox " + file.getName());
        }
        tfTotalSpend.setEnabled(false);
    }//GEN-LAST:event_tblCustomerMouseClicked

    private void btnAddNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddNewActionPerformed
        clearText();
        index = -1;
        tfTotalSpend.setText("0");
        tfTotalSpend.setEnabled(false);
        clearBookingHistory();
    }//GEN-LAST:event_btnAddNewActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        clearBookingHistory();
        Customer customer = new Customer();
        String name = tfName.getText().trim();
        String phoneNumber = tfPhoneNumber.getText().trim();
        String totalSpendText = tfTotalSpend.getText().trim();
        if (name.isBlank() || phoneNumber.isBlank() || totalSpendText.isBlank()) {
            MessageAlerts.getInstance().showMessage("Wrong format", "Please do not leave fields blank", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                if (i == MessageAlerts.CLOSED_OPTION) {

                }
            });
        } else {
            if (!phoneNumber.matches("\\d{10}")) {
                MessageAlerts.getInstance().showMessage("Wrong format", "Phone number must contain exactly 10 digits, cannot contain special characters", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                    if (i == MessageAlerts.CLOSED_OPTION) {

                    }
                });
                return;
            }
            if (!totalSpendText.matches("\\d+")) {
                MessageAlerts.getInstance().showMessage("Wrong format", "Total spend cannot contain special characters, cannot be negative, must be a positive integer >= 0", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                    if (i == MessageAlerts.CLOSED_OPTION) {

                    }
                });
                return;
            }
            customer.setName(name);
            customer.setPhoneNumber(phoneNumber);
            BigDecimal totalSpend = new BigDecimal(totalSpendText);
            customer.setTotalSpend(totalSpend);
            if (tempPicture != null) {
                String picturePath = tempPicture.getAbsolutePath();
                String uuid = UUID.randomUUID().toString();
                String newName = uuid + picturePath.substring(picturePath.lastIndexOf('.'));
                customer.setImage(newName);
                if (!saveFile(tempPicture, newName)) {
                    return;
                }
            } else {
                customer.setImage(customer.getImage());
            }
            if (index == -1) {
                String membershipName = "Standard";
                int membershipId = membershipService.findIdByName(membershipName);
                customer.setMemberShipId(membershipId);
                if (customerService.checkPhoneNumberExist(phoneNumber)) {
                    MessageAlerts.getInstance().showMessage("Add failed", "This phone number already exists. Please use another phone number.", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                        if (i == MessageAlerts.CLOSED_OPTION) {

                        }
                    });
                    return;
                }
                if (customerService.add(customer)) {
                    MessageAlerts.getInstance().showMessage("Add Success", "Your data has been saved", MessageAlerts.MessageType.SUCCESS, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                        if (i == MessageAlerts.CLOSED_OPTION) {

                        }
                    });
                    loadDataCustomerManagement();
                } else {
                    MessageAlerts.getInstance().showMessage("Add failed", "Please check and try again", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                        if (i == MessageAlerts.CLOSED_OPTION) {

                        }
                    });
                }

            } else {
                int customerId = Integer.parseInt(customerModels.getValueAt(index, 0).toString());
                customer.setId(customerId);
                Customer cus = customerService.findById(customerId);
                customer.setMemberShipId(cus.getMemberShipId());
                if (customerService.checkPhoneNumberExistExceptCurrent(customer.getId(), phoneNumber)) {
                    MessageAlerts.getInstance().showMessage("Update failed", "This phone number already exists! Please use another phone number.", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                        if (i == MessageAlerts.CLOSED_OPTION) {

                        }
                    });
                    return;
                }
                if (tblCustomer.getSelectedRowCount() == 1) {
                    if (customerService.update(customer)) {
                        MessageAlerts.getInstance().showMessage("Update success", "Your data has been saved", MessageAlerts.MessageType.SUCCESS, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                            if (i == MessageAlerts.CLOSED_OPTION) {

                            }
                        });
                        clearText();
                        tfTotalSpend.setText("0");
                        loadDataCustomerManagement();
                    } else {
                        MessageAlerts.getInstance().showMessage("Updated failed", "Please check and try again", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                            if (i == MessageAlerts.CLOSED_OPTION) {

                            }
                        });
                    }
                } else {
                    if (tblCustomer.getSelectedRowCount() == 0) {
                        MessageAlerts.getInstance().showMessage("Update failed", "Please select a customer to update", MessageAlerts.MessageType.WARNING, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                            if (i == MessageAlerts.CLOSED_OPTION) {

                            }
                        });
                    }
                }
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyReleased
        DefaultTableModel tblModel = (DefaultTableModel) tblCustomer.getModel();
        TableRowSorter<DefaultTableModel> obj = new TableRowSorter<>(tblModel);
        tblCustomer.setRowSorter(obj);
        obj.setRowFilter(RowFilter.regexFilter(txtSearch.getText()));
    }//GEN-LAST:event_txtSearchKeyReleased

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (index != -1) {
            MessageAlerts.getInstance().showMessage("Delete customer", "You definitely want to delete the customer with id : " + customerModels.getValueAt(index, 0).toString(), MessageAlerts.MessageType.WARNING, MessageAlerts.YES_NO_OPTION, (PopupController pc, int i) -> {
                if (i == MessageAlerts.YES_OPTION) {
                    int id = Integer.parseInt(customerModels.getValueAt(index, 0).toString());
                    if (customerService.softDelete(id)) {
                        MessageAlerts.getInstance().showMessage("Deleted", "Successfully deleted customer", MessageAlerts.MessageType.SUCCESS, MessageAlerts.CLOSED_OPTION, (PopupController pc1, int i1) -> {
                            if (i1 == MessageAlerts.CLOSED_OPTION) {
                            }
                        });
                        customerModels.removeRow(index);
                        clearText();
                        tfTotalSpend.setText("0");
                        tfTotalSpend.setEnabled(false);
                        clearBookingHistory();
                    } else {
                        MessageAlerts.getInstance().showMessage("Delete failed", "There was an erro during deletion, please try again", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc1, int i1) -> {
                        });
                    }
                }
            });
        } else {
            MessageAlerts.getInstance().showMessage("Delete failed", "Please select a customer to delete", MessageAlerts.MessageType.WARNING, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                if (i == MessageAlerts.CLOSED_OPTION) {

                }
            });
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnUploadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUploadActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileNameExtensionFilter filter = new FileNameExtensionFilter("* .Image", "jpg", "png");
        fileChooser.addChoosableFileFilter(filter);
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (selectedFile != null) {
                String fileName = selectedFile.getName();
                if (fileName.endsWith(".jpg") || fileName.endsWith(".JPG") || fileName.endsWith(".png") || fileName.endsWith(".PNG")) {
                    String filePath = selectedFile.getAbsolutePath();
                    ImageIcon myImage = new ImageIcon(filePath);
                    Image image = myImage.getImage().getScaledInstance(ptbCustomerImage.getWidth(), ptbCustomerImage.getHeight(), Image.SCALE_SMOOTH);
                    ptbCustomerImage.setImage(new ImageIcon(image));
                    ptbCustomerImage.repaint();
                    tempPicture = fileChooser.getSelectedFile();
                    System.out.print(tempPicture);
                } else {
                    MessageAlerts.getInstance().showMessage("Select failed", "Select the image file is not suitable, please select the .jpg or .png file", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                    });
                }
            } else {
                MessageAlerts.getInstance().showMessage("Select failed", "Select the image file is not suitable, please select the .jpg or .png file", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                });
            }
        }
    }//GEN-LAST:event_btnUploadActionPerformed

    private boolean saveFile(File file, String fileName) {
        File destinationFolder = new File("src/minisoccerfieldmanagement/image/customer");
        System.out.println(destinationFolder.getAbsolutePath());
        try {
            if (!destinationFolder.exists()) {
                destinationFolder.mkdirs();
            }
            File destinationFile = new File(destinationFolder, fileName);
            System.out.println(destinationFile.getAbsolutePath());
            Files.copy(file.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            MessageAlerts.getInstance().showMessage("Upload image failed", "Please check and try again", MessageAlerts.MessageType.ERROR, MessageAlerts.CLOSED_OPTION, (PopupController pc, int i) -> {
                if (i == MessageAlerts.CLOSED_OPTION) {

                }
            });
            return false;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private minisoccerfieldmanagement.util.BookingHistorySection bookingHistorySection1;
    private javax.swing.JButton btnAddNew;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnPrint;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnUpload;
    private raven.crazypanel.CrazyPanel crazyPanel1;
    private raven.crazypanel.CrazyPanel crazyPanel2;
    private raven.crazypanel.CrazyPanel crazyPanel3;
    private raven.crazypanel.CrazyPanel crazyPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBookingHistory;
    private javax.swing.JLabel lblCustomer;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblPhoneNumber;
    private javax.swing.JLabel lblTotalSpend;
    private minisoccerfieldmanagement.util.PictureBox ptbCustomerImage;
    private javax.swing.JTable tblCustomer;
    private javax.swing.JTextField tfName;
    private javax.swing.JTextField tfPhoneNumber;
    private javax.swing.JTextField tfTotalSpend;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}
