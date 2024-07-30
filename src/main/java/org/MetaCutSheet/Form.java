package org.MetaCutSheet;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class Form {



//    https://stackoverflow.com/questions/29371129/java-pdfbox-fill-out-pdf-form-append-it-to-pddocument-and-repeat

        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        JFrame frmCutSheetExpress;
        private JTextField txtType;
        private JButton btnDone;
        private JLabel lblManufacturer;
        private JLabel lblModel;
        private JLabel lblPart;
        private JLabel lblDescription;
        private JLabel lblWattage;
        private JLabel lblVoltage;
        private JLabel lblControl;
        private JLabel lblDimmable;
        private JLabel lblDate;
        private JLabel lblRevision;
        private JLabel lblApprovedBy;
        private JLabel lblLd;
        private JLabel lblDesignFirm;
        private JLabel lblProjectName;
        private JLabel lblProjectLocation;
        private JLabel lblNotes;
        private JTextField txtMfr;
        private JTextField txtModel;
        private JTextField txtPart;
        private JTextField txtWattage;
        private JTextField txtVoltage;
        private JTextField txtControl;
        private JTextField txtDimmable;
        private JTextField txtDate;
        private JTextField txtRev;
        private JTextField txtApprovedBy;
        private JTextField txtLD;
        private JTextField txtDesignFirm;
        private JTextField txtProjectName;
        private JTextField txtProjectLocation;
        private JTextArea txtNotes;
        private JTextArea txtDescription;
        private JLabel lblChoosePdf;
//        private static Cse cse;
        private JRadioButton rdbtnFixture;
        private JRadioButton rdbtnDevice;
        private JEditorPane statusField;
        private JLabel lblStatus;
        private JLabel lblNewLabel;
        private JLabel lblSupportMwymanwallcom;
        private JLabel lblCutsheetexpressV;
        private JTextField txtOutputDirectory;
        private JLabel lblOutputDirectory;
        private JButton btnChooseOutput;
        private String numCutSheets;
        private JLabel numCutSheetsLbl;
        private JButton btnOpenCutsheet;
        private final Action openCutSheetAction = new SwingAction();

        boolean is_fixture;

         Map<String, String> fields = new HashMap();

        String ds_path;
        static String cs_output_path;

//        public static void main(String[] args) {
////            cse = new Cse();
//
//            try {
//                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
//            } catch (Exception var2) {
//                System.out.println("Error setting native LAF: " + var2);
//            }
//
//            EventQueue.invokeLater(new Runnable() {
//                public void run() {
//                    try {
//                        Form window = new Form();
//                        window.frmCutSheetExpress.setVisible(true);
//                    } catch (Exception var2) {
//                        var2.printStackTrace();
//                    }
//
//                }
//            });
//        }

        public Form() {
            this.initialize();
        }

        private void initialize() {
            this.numCutSheets = this.prefs.get("numCutsheets", "0");
            this.frmCutSheetExpress = new JFrame();
            this.frmCutSheetExpress.getContentPane().setFont(new Font("Segoe UI", 0, 17));
            this.frmCutSheetExpress.setTitle("Cut Sheet Express");
            this.frmCutSheetExpress.setIconImage(Toolkit.getDefaultToolkit().getImage(Form.class.getResource("/cutsheetexpress/favicon.ico")));
            this.frmCutSheetExpress.setBounds(100, 100, 977, 924);
            this.frmCutSheetExpress.setDefaultCloseOperation(3);
            this.frmCutSheetExpress.getContentPane().setLayout(new FormLayout(new ColumnSpec[]{FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("center:max(80dlu;default)"), FormSpecs.RELATED_GAP_COLSPEC, FormSpecs.DEFAULT_COLSPEC, FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("50dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC, ColumnSpec.decode("150dlu:grow"), FormSpecs.RELATED_GAP_COLSPEC}, new RowSpec[]{FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("30dlu:grow"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, RowSpec.decode("50dlu:grow"), FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC, FormSpecs.DEFAULT_ROWSPEC, FormSpecs.RELATED_GAP_ROWSPEC}));
            this.lblNewLabel = new JLabel("");
            this.lblNewLabel.setVerticalAlignment(1);
            this.lblNewLabel.setIcon(new ImageIcon((new ImageIcon(Form.class.getResource("/cutsheetexpress/4walllogo.png"))).getImage().getScaledInstance(200, 192, 4)));
            this.frmCutSheetExpress.getContentPane().add(this.lblNewLabel, "2, 2, 1, 9, right, top");
            JLabel lblType = new JLabel("Type");
            this.frmCutSheetExpress.getContentPane().add(lblType, "4, 2, right, default");
            this.txtType = new JTextField();
            this.frmCutSheetExpress.getContentPane().add(this.txtType, "6, 2, 3, 1, fill, default");
            this.txtType.setColumns(10);
            this.lblManufacturer = new JLabel("Manufacturer");
            this.frmCutSheetExpress.getContentPane().add(this.lblManufacturer, "4, 4, right, default");
            this.txtMfr = new JTextField();
            this.frmCutSheetExpress.getContentPane().add(this.txtMfr, "6, 4, 3, 1, fill, default");
            this.txtMfr.setColumns(10);
            this.lblModel = new JLabel("Model #");
            this.frmCutSheetExpress.getContentPane().add(this.lblModel, "4, 6, right, default");
            this.txtModel = new JTextField();
            this.frmCutSheetExpress.getContentPane().add(this.txtModel, "6, 6, 3, 1, fill, default");
            this.txtModel.setColumns(10);
            this.lblPart = new JLabel("Part #");
            this.frmCutSheetExpress.getContentPane().add(this.lblPart, "4, 8, right, default");
            this.txtPart = new JTextField();
            this.frmCutSheetExpress.getContentPane().add(this.txtPart, "6, 8, 3, 1, fill, default");
            this.txtPart.setColumns(10);
            this.lblDescription = new JLabel("Description");
            this.frmCutSheetExpress.getContentPane().add(this.lblDescription, "4, 10, right, default");
            this.txtDescription = new JTextArea();
            this.txtDescription.setFont(new Font("Tahoma", 0, 13));
            this.txtDescription.setWrapStyleWord(true);
            this.txtDescription.setLineWrap(true);
            this.txtDescription.setFocusTraversalKeys(0, (Set)null);
            this.txtDescription.setFocusTraversalKeys(1, (Set)null);
            this.frmCutSheetExpress.getContentPane().add(this.txtDescription, "6, 10, 3, 1, fill, fill");
            this.lblWattage = new JLabel("Wattage");
            this.frmCutSheetExpress.getContentPane().add(this.lblWattage, "4, 12, right, default");
            this.txtWattage = new JTextField();
            this.txtWattage.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtWattage, "6, 12, 3, 1, fill, default");
            this.lblVoltage = new JLabel("Voltage");
            this.frmCutSheetExpress.getContentPane().add(this.lblVoltage, "4, 14, right, default");
            this.txtVoltage = new JTextField();
            this.txtVoltage.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtVoltage, "6, 14, 3, 1, fill, default");
            this.lblControl = new JLabel("Control");
            this.frmCutSheetExpress.getContentPane().add(this.lblControl, "4, 16, right, default");
            this.txtControl = new JTextField();
            this.txtControl.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtControl, "6, 16, 3, 1, fill, default");
            this.lblDimmable = new JLabel("Dimmable");
            this.frmCutSheetExpress.getContentPane().add(this.lblDimmable, "4, 18, right, default");
            this.txtDimmable = new JTextField();
            this.txtDimmable.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtDimmable, "6, 18, 3, 1, fill, default");
            LocalDate today = LocalDate.now();
            this.lblDate = new JLabel("Date");
            this.frmCutSheetExpress.getContentPane().add(this.lblDate, "4, 20, right, default");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            this.txtDate = new JTextField();
            this.txtDate.setText(dtf.format(today).toString());
            this.txtDate.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtDate, "6, 20, 3, 1, fill, default");
            this.lblRevision = new JLabel("Revision");
            this.frmCutSheetExpress.getContentPane().add(this.lblRevision, "4, 22, right, default");
            this.txtRev = new JTextField();
            this.txtRev.setColumns(10);
            this.txtRev.setText("1.0");
            this.frmCutSheetExpress.getContentPane().add(this.txtRev, "6, 22, 3, 1, fill, default");
            this.lblApprovedBy = new JLabel("Approved By:");
            this.frmCutSheetExpress.getContentPane().add(this.lblApprovedBy, "4, 24, right, default");
            this.txtApprovedBy = new JTextField();
            this.txtApprovedBy.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtApprovedBy, "6, 24, 3, 1, fill, default");
            this.lblLd = new JLabel("LD:");
            this.frmCutSheetExpress.getContentPane().add(this.lblLd, "4, 26, right, default");
            this.txtLD = new JTextField();
            this.txtLD.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtLD, "6, 26, 3, 1, fill, default");
            this.lblDesignFirm = new JLabel("Design Firm:");
            this.frmCutSheetExpress.getContentPane().add(this.lblDesignFirm, "4, 28, right, default");
            this.txtDesignFirm = new JTextField();
            this.txtDesignFirm.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtDesignFirm, "6, 28, 3, 1, fill, default");
            this.lblProjectName = new JLabel("Project Name:");
            this.frmCutSheetExpress.getContentPane().add(this.lblProjectName, "4, 30, right, default");
            this.txtProjectName = new JTextField();
            this.txtProjectName.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtProjectName, "6, 30, 3, 1, fill, default");
            this.lblProjectLocation = new JLabel("Project Location:");
            this.frmCutSheetExpress.getContentPane().add(this.lblProjectLocation, "4, 32, right, default");
            this.txtProjectLocation = new JTextField();
            this.lblProjectLocation.setLabelFor(this.txtProjectLocation);
            this.txtProjectLocation.setColumns(10);
            this.frmCutSheetExpress.getContentPane().add(this.txtProjectLocation, "6, 32, 3, 1, fill, default");
            this.lblNotes = new JLabel("Notes:");
            this.frmCutSheetExpress.getContentPane().add(this.lblNotes, "4, 34, right, default");
            this.txtNotes = new JTextArea();
            this.txtNotes.setFont(new Font("Tahoma", 0, 13));
            this.txtNotes.setWrapStyleWord(true);
            this.txtNotes.setLineWrap(true);
            this.txtNotes.setFocusTraversalKeys(0, (Set)null);
            this.txtNotes.setFocusTraversalKeys(1, (Set)null);
            this.frmCutSheetExpress.getContentPane().add(this.txtNotes, "6, 34, 3, 1, fill, fill");
            this.rdbtnFixture = new JRadioButton("Fixture");
            this.rdbtnFixture.setSelected(true);
            this.frmCutSheetExpress.getContentPane().add(this.rdbtnFixture, "6, 36");
            this.rdbtnDevice = new JRadioButton("Device");
            this.frmCutSheetExpress.getContentPane().add(this.rdbtnDevice, "8, 36");
            ButtonGroup group = new ButtonGroup();
            group.add(this.rdbtnDevice);
            group.add(this.rdbtnFixture);
            this.lblChoosePdf = new JLabel("Choose PDF");
            this.frmCutSheetExpress.getContentPane().add(this.lblChoosePdf, "4, 38, right, default");
            this.btnDone = new JButton("Open");
            this.btnDone.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    Form.this.btnOpenCutsheet.setEnabled(false);
                    Form.this.savePrefs();
                    System.out.println("type: " + Form.this.txtType.getText());
                    if (Form.this.txtType.getText().isEmpty()) {
                        Form.this.statusField.setText("Please assign Type.");
                        Form.this.statusField.setBackground(Color.RED);
                        Form.this.frmCutSheetExpress.revalidate();
                    } else {
                        Form.this.statusField.setText("Pick Cutsheet.");
                        Form.this.statusField.setBackground(Color.WHITE);
                        FileDialog fd = new FileDialog(Form.this.frmCutSheetExpress, "Choose Cut Sheet...", 0);
                        fd.setFile("*.pdf");
                        fd.setVisible(true);
                        if (fd.getFile() == null) {
                            Form.this.statusField.setBackground(Color.WHITE);
                            Form.this.statusField.setText("CANCELED");
                            Form.this.frmCutSheetExpress.revalidate();
                            System.out.println("CANCEL");
                        } else {
                            final String filePath = fd.getDirectory() + fd.getFile();
                            Form.this.statusField.setText("Generating PDF...");
                            Form.this.frmCutSheetExpress.setEnabled(false);

                            try {
                                SwingUtilities.invokeLater(new Runnable() {
                                    public void run() {
                                        System.out.println("Generating PDF...");
                                        Form.this.fillFields(filePath);
//                                            Form.cse.runCSE();
                                        Form.this.statusField.setBackground(Color.WHITE);
                                        Form.this.statusField.setText("Done! PDF Created at: " + Form.cs_output_path);
                                        Form.this.numCutSheets = Integer.toString(Integer.parseInt(Form.this.numCutSheets) + 1);
                                        Form.this.numCutSheetsLbl.setText("You've created " + Form.this.numCutSheets + " cutsheets.");
                                        Form.this.btnOpenCutsheet.setEnabled(true);
                                        Form.this.frmCutSheetExpress.revalidate();
                                        System.out.println("ALL DONE!");
                                        Form.this.savePrefs();
                                        Form.this.frmCutSheetExpress.setEnabled(true);

                                    }
                                });
                            } catch (Exception var5) {
                                var5.printStackTrace();
                                Form.this.frmCutSheetExpress.setEnabled(true);
                            }
                        }
                    }

                }
            });
            this.frmCutSheetExpress.getContentPane().add(this.btnDone, "6, 38, 2, 1");
            this.btnChooseOutput = new JButton("Choose Output Directory");
            this.btnChooseOutput.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("Choose Directory");
                    chooser.setFileSelectionMode(1);
                    chooser.setAcceptAllFileFilterUsed(false);
                    if (chooser.showOpenDialog(chooser) == 0) {
                        Form.this.txtOutputDirectory.setText(chooser.getSelectedFile().toString());
                        Form.this.prefs.put("outputDirectory", Form.this.txtOutputDirectory.getText());
                    }

                }
            });
            this.frmCutSheetExpress.getContentPane().add(this.btnChooseOutput, "2, 40, center, default");
            this.lblOutputDirectory = new JLabel("Output Directory:");
            this.frmCutSheetExpress.getContentPane().add(this.lblOutputDirectory, "4, 40, right, default");
            this.txtOutputDirectory = new JTextField();
            this.txtOutputDirectory.setEditable(false);
            this.txtOutputDirectory.setText(this.prefs.get("outputDirectory", (String)null));
            this.frmCutSheetExpress.getContentPane().add(this.txtOutputDirectory, "6, 40, 3, 1, fill, default");
            this.txtOutputDirectory.setColumns(10);
            this.numCutSheetsLbl = new JLabel("You've created " + this.numCutSheets + " cutsheets.");
            this.frmCutSheetExpress.getContentPane().add(this.numCutSheetsLbl, "2, 42, left, center");
            this.lblStatus = new JLabel("STATUS:");
            this.lblStatus.setFont(new Font("Segoe UI", 1, 15));
            this.frmCutSheetExpress.getContentPane().add(this.lblStatus, "4, 42, right, default");
            this.statusField = new JEditorPane();
            this.statusField.setBackground(Color.WHITE);
            this.statusField.setFont(new Font("Tahoma", 0, 16));
            this.statusField.setEditable(false);
            this.frmCutSheetExpress.getContentPane().add(this.statusField, "6, 42, 3, 3, fill, default");
            this.lblCutsheetexpressV = new JLabel("CutSheetExpress v1.0.0");
            this.frmCutSheetExpress.getContentPane().add(this.lblCutsheetexpressV, "2, 44, left, default");
            this.lblSupportMwymanwallcom = new JLabel("Support: mwyman@4wall.com");
            this.lblSupportMwymanwallcom.setHorizontalAlignment(0);
            this.lblSupportMwymanwallcom.setFont(new Font("Segoe UI", 1, 14));
            this.frmCutSheetExpress.getContentPane().add(this.lblSupportMwymanwallcom, "2, 46, left, default");
            this.btnOpenCutsheet = new JButton("Open New Cutsheet");
            this.btnOpenCutsheet.setAction(this.openCutSheetAction);
            this.btnOpenCutsheet.setEnabled(false);
            this.frmCutSheetExpress.getContentPane().add(this.btnOpenCutsheet, "6, 46");
            this.restorePrefs();
        }

        private void fillFields(String filePath) {
            fields.put("description", this.txtDescription.getText());
            fields.put("notes", this.txtNotes.getText());
            fields.put("projectName", this.txtProjectName.getText());
            fields.put("projectLocation", this.txtProjectLocation.getText());
            fields.put("type", this.txtType.getText());
            fields.put("ld", this.txtLD.getText());
            fields.put("approvedBy", this.txtApprovedBy.getText());
            fields.put("revision", this.txtRev.getText());
            fields.put("date", this.txtDate.getText());
            fields.put("dimmable", this.txtDimmable.getText());
            fields.put("control", this.txtControl.getText());
            fields.put("voltage", this.txtVoltage.getText());
            fields.put("wattage", this.txtWattage.getText());
            fields.put("manufacturer", this.txtMfr.getText());
            fields.put("modelNumber", this.txtModel.getText());
            fields.put("partNumber", this.txtPart.getText());
            fields.put("designFirm", this.txtDesignFirm.getText());
            ds_path = filePath;
            cs_output_path = this.txtOutputDirectory.getText() + "\\" + this.txtType.getText() + ".pdf";
            is_fixture = this.rdbtnFixture.isSelected();
        }

        private void savePrefs() {
            this.prefs.put("approvedBy", this.txtApprovedBy.getText());
            this.prefs.put("projectName", this.txtProjectName.getText());
            this.prefs.put("projectLocation", this.txtProjectLocation.getText());
            this.prefs.put("ld", this.txtLD.getText());
            this.prefs.put("designFirm", this.txtDesignFirm.getText());
            this.prefs.put("cseVersion", "1.0.0");
            this.prefs.put("numCutsheets", this.numCutSheets);
            System.out.println("Preferences Saved");
        }

        private void restorePrefs() {
            this.txtApprovedBy.setText(this.prefs.get("approvedBy", (String)null));
            this.txtProjectName.setText(this.prefs.get("projectName", (String)null));
            this.txtProjectLocation.setText(this.prefs.get("projectLocation", (String)null));
            this.txtLD.setText(this.prefs.get("ld", (String)null));
            this.txtDesignFirm.setText(this.prefs.get("designFirm", (String)null));
            this.numCutSheets = this.prefs.get("numCutsheets", "0");
            System.out.println("Preferences Restored");
        }

        private void clearPrefs() throws BackingStoreException {
            this.prefs.removeNode();
            System.out.println("Preferences Cleared");
        }

        private class SwingAction extends AbstractAction {
            private static final long serialVersionUID = 1L;

            public SwingAction() {
                this.putValue("Name", "Open New Cutsheet");
                this.putValue("ShortDescription", "Open Cutsheet.");
            }

            public void actionPerformed(ActionEvent e) {
                if (Desktop.isDesktopSupported()) {
                    try {
                        File myFile = new File(Form.cs_output_path);
                        Desktop.getDesktop().open(myFile);
                    } catch (IOException var3) {
                        Form.this.statusField.setText("ERROR: " + var3.getMessage());
                        Form.this.statusField.setBackground(Color.RED);
                        var3.printStackTrace();
                        Form.this.frmCutSheetExpress.setEnabled(true);
                        Form.this.btnOpenCutsheet.setEnabled(false);
                    }
                }

            }


            public PDDocument fillForm() throws IOException {
                String template_stream;
                if (is_fixture) {
                    template_stream = "/resources/Template_Fixture.pdf";
                } else {
                    template_stream = "/resources/Template_Device.pdf";
                }

                PDDocument template = Loader.loadPDF(new File(template_stream));
                PDDocumentCatalog docCatalog = template.getDocumentCatalog();
                PDAcroForm acroForm = docCatalog.getAcroForm();
                acroForm.setCacheFields(true);
                PDFont arnb_font = PDType0Font.load(template, Form.class.getResourceAsStream("/cutsheetexpress/ARIALNB.TTF"));
                PDFont arn_font = PDType0Font.load(template, Form.class.getResourceAsStream("/cutsheetexpress/ARIALN.TTF"));
                PDResources resources = new PDResources();
                resources.put(COSName.getPDFName("ArialNarrowBold"), arnb_font);
                resources.put(COSName.getPDFName("ArialNarrow"), arn_font);
                resources.put(COSName.getPDFName("Helv"), arn_font);
                acroForm.setDefaultResources(resources);
                Iterator var9 = fields.entrySet().iterator();

                while (var9.hasNext()) {
                    Map.Entry<String, String> field = (Map.Entry) var9.next();
                    acroForm.getField((String) field.getKey()).setValue((String) field.getValue());
                }

                System.out.println("Form Filled");
                return template;
            }


        }

}
