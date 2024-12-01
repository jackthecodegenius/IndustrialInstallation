import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;

/**
 * The MainForm class represents the main user interface of the application.
 * It extends JFrame to handle user events.
 */
public class MainForm extends JFrame implements ActionListener {
    JLabel lblLegends, lblDangerous, lblConcerning, lblAcceptable, lblRecordedLevel, lblExport, lblWarehouse, lblDate, lblTime, lblLevels, lblTitle;
    int gridRows = 30;
    int gridColumns = 30;
    FileManager file = new FileManager();
    JTextField txtRecorded, txtWarehouse, txtDate, txtTime;
    JButton btnSulphur, btnNitrogen, btnCarbon, btnObstruction, btnLoad, btnExport, btnClose, btnSort;
    SpringLayout layout = new SpringLayout();
    private JScrollPane scrollPane;
    private JPanel gridPanel;
    JTextField[][] formGrid;
    /**
     * Constructs a new instance of the MainForm class.
     * This constructor initializes the UI components and sets up event listeners.
     */
    private boolean loadActionPerformed = false;
    private boolean fileLoadingInProgress = false;
    public MainForm ()
    {
        setTitle("Ian's Industrial Installation");
        setSize(1000,800);
        setLocationRelativeTo(null);
        setLayout(layout);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });



        lblTitle = UIBuilderLibray.BuildJLabelWithNorthWestAnchor("Ian's Industrial Installation",10,10, layout, this);

        lblTitle.setOpaque(true);
        Color rgbColor = new Color(108, 67, 0);
        lblTitle.setBackground(rgbColor);
        lblTitle.setForeground(Color.LIGHT_GRAY);
        Font font = lblTitle.getFont();
        lblTitle.setFont(font.deriveFont(25f));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        add(lblTitle);



        // Top row above the grid
        lblWarehouse = UIBuilderLibray.BuildJLabelInlineToRight("Warehouse",50,layout,lblTitle);
        add(lblWarehouse);
        txtWarehouse = UIBuilderLibray.BuildJTextFieldInlineToRight(10,5,layout,lblWarehouse);
        txtWarehouse.setText("7, Industry Pl.");
        add(txtWarehouse);
        lblDate = UIBuilderLibray.BuildJLabelInlineToRight("Date",50,layout,txtWarehouse);
        add(lblDate);
        txtDate = UIBuilderLibray.BuildJTextFieldInlineToRight(10,5,layout,lblDate);
        txtDate.setText("20/10/2027");
        add(txtDate);
        lblTime = UIBuilderLibray.BuildJLabelInlineToRight("Time",50,layout,txtDate);
        add(lblTime);
        txtTime = UIBuilderLibray.BuildJTextFieldInlineToRight(10,5,layout,lblTime);
        txtTime.setText("3:00pm");
        add(txtTime);

        //Navigation labels/buttons
        lblLevels = UIBuilderLibray.BuildJLabelInlineBelow("Sulphur Dioxide",30,layout,lblTitle);
        add(lblLevels);
        lblLegends = UIBuilderLibray.BuildJLabelInlineBelow("           Legend",30,layout,lblLevels);
        add(lblLegends);
        lblDangerous = UIBuilderLibray.BuildJLabelInlineBelow("DANGEROUS",5,layout,lblLegends);
        lblDangerous.setPreferredSize(new Dimension(115,25));
        lblDangerous.setOpaque(true);
        lblDangerous.setBackground(Color.RED);
        lblDangerous.setForeground(Color.WHITE);
        lblDangerous.setHorizontalAlignment(SwingConstants.CENTER);
        lblDangerous.setBorder(new LineBorder(Color.BLACK,3));
        add(lblDangerous);

        lblConcerning = UIBuilderLibray.BuildJLabelInlineBelow("CONCERNING",5,layout,lblDangerous);
        lblConcerning.setPreferredSize(new Dimension(115,25));
        lblConcerning.setOpaque(true);
        lblConcerning.setBackground(Color.ORANGE);
        lblConcerning.setForeground(Color.WHITE);
        lblConcerning.setHorizontalAlignment(SwingConstants.CENTER);
        lblConcerning.setBorder(new LineBorder(Color.BLACK,3));
        add(lblConcerning);

        lblAcceptable = UIBuilderLibray.BuildJLabelInlineBelow("ACCEPTABLE",5,layout,lblConcerning);
        lblAcceptable.setPreferredSize(new Dimension(115,25));
        lblAcceptable.setOpaque(true);
        lblAcceptable.setBackground(Color.GREEN);
        lblAcceptable.setForeground(Color.WHITE);
        lblAcceptable.setHorizontalAlignment(SwingConstants.CENTER);
        lblAcceptable.setBorder(new LineBorder(Color.BLACK,3));
        add(lblAcceptable);

        btnSulphur = UIBuilderLibray.BuildJButtonInlineBelow(115,25,"Sulphur Dioxide",30,this,layout,lblAcceptable);
        btnSulphur.setMargin(new Insets(0,0,0,0));
        add(btnSulphur);
        btnNitrogen = UIBuilderLibray.BuildJButtonInlineBelow(115,25,"Nitrogen Dioxide",10,this,layout,btnSulphur);
        btnNitrogen.setMargin(new Insets(0,0,0,0));
        add(btnNitrogen);
        btnCarbon = UIBuilderLibray.BuildJButtonInlineBelow(115,25,"Carbon Monoxide",10,this,layout,btnNitrogen);
        btnCarbon.setMargin(new Insets(0,0,0,0));
        add(btnCarbon);
        btnObstruction = UIBuilderLibray.BuildJButtonInlineBelow(115,25,"Obstructions",10,this,layout,btnCarbon);
        btnObstruction.setMargin(new Insets(0,0,0,0));
        add(btnObstruction);
        btnSort = UIBuilderLibray.BuildJButtonInlineBelow(115,25,"Sort",10,this,layout,btnObstruction);
        btnSort.setMargin(new Insets(0,0,0,0));
        add(btnSort);

        btnLoad = UIBuilderLibray.BuildJButtonInlineBelow(115,25,"Load File",300,this,layout,btnSort);
        btnLoad.setMargin(new Insets(0,0,0,0));
        add(btnLoad);
        btnLoad.addActionListener(this);

        lblRecordedLevel = UIBuilderLibray.BuildJLabelInlineToRight("Recorded Level:",20,layout,btnLoad);
        add(lblRecordedLevel);
        txtRecorded = UIBuilderLibray.BuildJTextFieldInlineToRight(10,5,layout,lblRecordedLevel);
        add(txtRecorded);

        lblExport = UIBuilderLibray.BuildJLabelInlineToRight("Export data to: .DAT, .CSV, .RPT, .RAF",100,layout,txtRecorded);
        add(lblExport);
        btnExport = UIBuilderLibray.BuildJButtonInlineToRight(115,25,"Export",350,this,layout,txtRecorded);
        btnExport.setMargin(new Insets(0,0,0,0));
        add(btnExport);

        btnClose = UIBuilderLibray.BuildJButtonInlineToRight(115,25,"Close",30,this,layout,btnExport);
        btnClose.setMargin(new Insets(0,0,0,0));
        add(btnClose);




        rebuildGrid();
        SulphurScreenDisplay();
        NitrogenScreenDisplay();
        CarbonScreenDisplay();
        ObstructionScreenDisplay();


        setVisible(true);
    }


    private void AddMouseListenerToJTextField(JTextField field) {
        field.addMouseListener(new MouseAdapter() {
            @Override

            public void mouseEntered(MouseEvent e) {
                JTextField textField = (JTextField) e.getSource();
                // Set font color to match the background color
                textField.setForeground(textField.getBackground());
                // Show the text
                txtRecorded.setText(textField.getText());
                // Set editable to true to allow editing
                textField.setEditable(true);
            }



        });
    }
    private void attachMouseListenersToGrid()
    {
        // Iterate through each row and column of the grid
        for (int y = 0; y < formGrid.length; y++)
        {
            for (int x = 0; x < formGrid[y].length; x++)
            {
                // Attach mouse listener to the current JTextField
                AddMouseListenerToJTextField(formGrid[y][x]);
            }
        }
    }




    @Override
    public void actionPerformed(ActionEvent e)
    {
        // focus listener for each text box whenever a box gains or loses focus
        formGrid[0][0].addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                // txt we are talking about
                JTextField currentField = (JTextField)e.getSource();
                // whenever something is selected clear
                currentField.setText("");

            }

            @Override
            public void focusLost(FocusEvent e)
            {
                // the txt being selected
                JTextField currentField = (JTextField)e.getSource();
                // changes the colour to red
                if (currentField.getText().equalsIgnoreCase("red")) {
                    currentField.setBackground(Color.RED);
                }
                else {
                    currentField.setBackground(Color.WHITE);
                }
            }

        });

        if (e.getSource() == btnExport)
        {
            String filePath = ChooseSaveFileLocation();
            if (filePath != null)
            {
                SaveToFile(filePath);
            }

        }

        if (e.getSource() == btnSort)
        {
            OpenSortingForm();
        }
        if (e.getSource() == btnClose)
        {
            System.exit(0);
        }
        if (fileLoadingInProgress) {
            return; // Exit method if loading is already in progress
        }

        if (e.getSource() == btnLoad)
        {
            fileLoadingInProgress = true;
            String[] supportedExtensions = {"csv", "raf", "dat", "rpt"};

            // Create a combo box to select the file type
            JComboBox<String> fileTypeComboBox = new JComboBox<>(supportedExtensions);
            JPanel panel = new JPanel(new GridLayout(0, 1));
            panel.add(new JLabel("Select file type:"));
            panel.add(fileTypeComboBox);

            // Display a dialog to select the file type
            int result = JOptionPane.showConfirmDialog(null, panel, "Select File", JOptionPane.OK_CANCEL_OPTION);

            // If the user selects OK
            if (result == JOptionPane.OK_OPTION)
            {
                // Get the selected file type
                String selectedFileType = (String) fileTypeComboBox.getSelectedItem();

                // Create a file chooser dialog
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Choose a file");
                fileChooser.setFileFilter(new FileNameExtensionFilter("Supported Files (*." + selectedFileType + ")", selectedFileType));
                fileChooser.setAcceptAllFileFilterUsed(false);

                // If the user selects a file
                if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                    // Get the selected file path
                    String filePath = fileChooser.getSelectedFile().getAbsolutePath();

                    // Read data from the selected file based on its type
                    FileData data = null;
                    if (selectedFileType.equals("csv"))
                    {
                        data = file.ReadFromCSV(filePath);
                    }
                    else if (selectedFileType.equals("raf"))
                    {
                        data = file.ReadFromRAF(filePath);
                    }
                    else if (selectedFileType.equals("dat"))
                    {
                        data = file.ReadFromDAT(filePath);
                    }
                    else if (selectedFileType.equals("rpt"))
                    {
                        data = file.ReadFromRPT(filePath);
                    }

                    // If data is successfully read
                    if (data != null) {
                        // Update UI components with the loaded data
                        txtWarehouse.setText(data.warehouse);
                        txtDate.setText(data.date);
                        txtTime.setText(data.time);
                        gridRows = data.rowCount;
                        gridColumns = data.columnCount;
                        rebuildGrid();
                        displayDataOnGrid(data);
                    }
                    else
                    {
                        // Show a message if data loading fails
                        JOptionPane.showMessageDialog(this, "Failed to load data from the file.");
                    }
                    // Mark that the load action has been performed So that the Dialog box doesn't show again

                }


            }
            fileLoadingInProgress = false;

        }





    }

    private void SaveToFile(String filePath)
    {
        //creates a new file data object so transferring data to the file manager
        FileData data = new FileData();
        //creates the 20 string array in the grid data object
        data.gridData = new String[gridRows][gridColumns];
        // copy the header into the file data object
        data.warehouse = txtWarehouse.getText();
        data.date = txtDate.getText();
        data.time = txtTime.getText();

        // copies our row and column count in the file data
        data.rowCount = gridRows;
        data.columnCount = gridColumns;

        for (int y = 0; y < formGrid.length; y++)
        {
            for (int x = 0; x < formGrid[y].length; x++) {
                //copies the text from each jtextfield in the UI array into the matching position of the file data object
                data.gridData[y][x] = formGrid[y][x].getText();
            }

        }

        if (filePath.endsWith(".csv"))
        {
            file.WriteToCSVFile(data,filePath);
        }
        else if (filePath.endsWith(".raf"))
        {
            file.WriteToRaFFile(data,filePath);
        }
        else if (filePath.endsWith(".dat"))
        {
            file.WriteToDATFile(data,filePath);
        }
        else if (filePath.endsWith(".rpt"))
        {
            file.WriteToRPTFile(data,filePath);
        }
        else
        {
            return;
        }
        // passes the file data object and file string to the file manager to save it
        file.WriteToRaFFile(data, "Population.raf");
        file.WriteToCSVFile(data,"Data.csv");
        file.WriteToDATFile(data, "Money.dat");
        file.WriteToDATFile(data, "Grid.dat");
        JOptionPane.showMessageDialog(this, "Data Saved!");
    }


    private boolean IsAnInteger(String input)
    {
        try
        {
            Integer.parseInt(input);
            return true;
        }
        catch (Exception ex)
        {
            return false;
        }
    }
    // the colours in my form grid, whatever value it is the cell weill change
    public Color DetermineCellColour(String input)
    {
        int number = Integer.parseInt(input);

        if (number > 20)
        {
            return Color.red;
        }
        else if (number > 10)
        {
            return Color.orange;
        }
        else if (number > 0)
        {
            return Color.green;
        }
        else
        {
            return Color.white;
        }




    }

    private void AddFocusListenerToGridField(JTextField field)
    {
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                JTextField currentField = (JTextField) e.getSource();
                String value = currentField.getText();
                String dataType = ""; // Adjust dataType as needed based on your logic
                String filePath = ""; // Adjust filePath as needed based on your logic

                if (IsAnInteger(value)) {
                    Color colour = DetermineCellColour(currentField.getText());
                    currentField.setBackground(colour);
                    currentField.setForeground(colour); // Set font color to match background color
                }
                else
                {
                    // Handle non-integer values (optional)
                }
            }
        });
    }



    private String ChooseSaveFileLocation()
    {
        //creates the file chooser object for the method
        JFileChooser fileChooser = new JFileChooser();
        //removes any file type filter form the chooser
        fileChooser.removeChoosableFileFilter(fileChooser.getAcceptAllFileFilter());
        // adding filters for the CSV and RAF file types
        fileChooser.setFileFilter(new FileNameExtensionFilter("CSV File","csv"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("RAF File","raf"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("DAT File","dat"));
        fileChooser.setFileFilter(new FileNameExtensionFilter("RPT File","rpt"));

        if(fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION)
        {
            if(fileChooser.getFileFilter() == null)
            {
                return fileChooser.getSelectedFile().getAbsolutePath() + "csv";
            }

            FileNameExtensionFilter filter = (FileNameExtensionFilter) fileChooser.getFileFilter();
            String extension = filter.getExtensions()[0];
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            if(filePath.endsWith(".csv") || filePath.endsWith(".raf") || filePath.endsWith(".dat") || filePath.endsWith(".rpt"))
            {
                return filePath;
            }

            return filePath + "." + extension;
        }


        // no file was chosen return files to what they originally were
        return null;
    }


    /**
     * Opens the sorting form to display and sort the cell data.
     */
    private void OpenSortingForm() {
        // Create a linked list to store cell data
        LinkedList<CellDetails> cellData = new LinkedList<>();

        // Iterate through the grid to collect cell data
        for (int y = 0; y < formGrid.length; y++)
        {
            for (int x = 0; x < formGrid[y].length; x++)
            {
                // Check if the cell contains an integer value other than 0
                if (IsAnInteger(formGrid[y][x].getText()) && !formGrid[y][x].getText().equals("0"))
                {
                    // Create a new CellDetails object and populate its attributes
                    CellDetails newCell = new CellDetails();
                    newCell.row = y;
                    newCell.column = x;
                    newCell.value = Integer.parseInt(formGrid[y][x].getText());
                    newCell.background = formGrid[y][x].getBackground();

                    // Add the cell data to the linked list
                    cellData.add(newCell);
                }
            }
        }

        // Create a new instance of SortingForm with the collected cell data
        SortingForm sortForm = new SortingForm(cellData);
        // Make the sorting form visible
        sortForm.setVisible(true);
    }

        public void SulphurScreenDisplay()
        {
            btnSulphur.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String filePath = "Ians_W07_S02_202710201500.csv";
                    lblLevels.setText("Sulphur Dioxide"); // Set label text

                    // Read data from the CSV file
                    FileData data = file.ReadFromCSV(filePath);

                    // Check if data was successfully loaded
                    if (data != null)
                    {
                        clearGrid();
                        gridRows = data.rowCount;
                        gridColumns = data.columnCount;
                        rebuildGrid();
                        displayDataOnGrid(data);// Update grid with loaded data

                    }
                    // If loading data failed, show error message
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Failed to load data from the file.");
                    }

                }
            });


        }
    public void NitrogenScreenDisplay()
    {
        btnNitrogen.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String filePath = "Ians_W07_NO2_202710201500.csv";
                lblLevels.setText("Nitrogen Dioxide"); // Set label text

                // Read data from the CSV file
                FileData data = file.ReadFromCSV(filePath);

                // Check if data was successfully loaded
                if (data != null)
                {
                    clearGrid();
                    gridRows = data.rowCount;
                    gridColumns = data.columnCount;
                    rebuildGrid();
                    displayDataOnGrid(data);// Update grid with loaded data

                }
                // If loading data failed, show error message
                else
                {
                    JOptionPane.showMessageDialog(null, "Failed to load data from the file.");
                }
            }
        });

    }
        public void CarbonScreenDisplay()
        {
            btnCarbon.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String filePath = "Ians_W07_CO_202710201500.csv";
                    lblLevels.setText("Carbon Monoxide"); // Set label text

                    // Read data from the CSV file
                    FileData data = file.ReadFromCSV(filePath);

                    // Check if data was successfully loaded
                    if (data != null)
                    {
                        clearGrid();
                        gridRows = data.rowCount;
                        gridColumns = data.columnCount;
                        rebuildGrid();
                        displayDataOnGrid(data);// Update grid with loaded data

                    }
                    // If loading data failed, show error message
                    else
                    {
                        JOptionPane.showMessageDialog(null, "Failed to load data from the file.");
                    }

                }
            });

    }

    public void ObstructionScreenDisplay()
    {
        btnObstruction.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String filePath = "Ians_W07_Obstruct_202710201500.csv";
                lblLevels.setText("Obstructions"); // Set label text

                // Read data from the CSV file
                FileData data = file.ReadFromCSV(filePath);

                // Check if data was successfully loaded
                if (data != null)
                {
                    clearGrid();
                    gridRows = data.rowCount;
                    gridColumns = data.columnCount;
                    rebuildGrid();
                    displayDataOnGrid(data); // Update grid with loaded data

                }
                // If loading data failed, show error message
                else
                {
                    JOptionPane.showMessageDialog(null, "Failed to load data from the file.");
                }

            }
        });
    }

    private void clearGrid()
    {
        // Check if the grid panel exists and remove all components from it
        if (gridPanel != null)
        {
            gridPanel.removeAll();
        }

        // Check if the formGrid array exists
        if (formGrid != null)
        {
            // Iterate through the formGrid array
            for (int y = 0; y < formGrid.length; y++)
            {
                for (int x = 0; x < formGrid[y].length; x++)
                {
                    // Remove each component from the main frame
                    remove(formGrid[y][x]);
                }
            }
        }
    }

    private void displayDataOnGrid(FileData data)
    {
        txtWarehouse.setText(data.warehouse);
        txtDate.setText(data.date);
        txtTime.setText(data.time);
        gridRows = data.rowCount;
        gridColumns = data.columnCount;

        // Rebuild the grid to match the updated row and column count
        rebuildGrid();

        // Populate grid with data from the loaded file
        for (int y = 0; y < gridRows; y++) {
            for (int x = 0; x < gridColumns; x++) {
                if (y < data.gridData.length && x < data.gridData[y].length) {
                    formGrid[y][x].setText(data.gridData[y][x]);
                    if (IsAnInteger(data.gridData[y][x])) {
                        Color cellColor = DetermineCellColour(data.gridData[y][x]);
                        formGrid[y][x].setBackground(cellColor);
                        formGrid[y][x].setForeground(cellColor); // Set font color to match background color
                    }
                } else {
                    System.out.println("Index out of bounds: y=" + y + ", x=" + x);
                }

            }
        }
        // Print a message indicating that the grid has been updated with new data
        System.out.println("Grid updated with new data.");
    }


    private void rebuildGrid()
    {


        if (gridPanel == null)
        {
            // Initialize a new gridPanel with GridLayout
            gridPanel = new JPanel();
            gridPanel.setLayout(new GridLayout(gridRows, gridColumns));
        }
        else
        {
            // Update the layout of the existing gridPanel and remove all components
            gridPanel.setLayout(new GridLayout(gridRows, gridColumns));
            gridPanel.removeAll();
        }

        // Rebuild the new grid by new size
        formGrid = new JTextField[gridRows][gridColumns];

        // Create JTextField components and add them to the gridPanel
        for (int y = 0; y < formGrid.length; y++)
        {
            for (int x = 0; x < formGrid[y].length; x++)
            {
                formGrid[y][x] = new JTextField();
                formGrid[y][x].setPreferredSize(new Dimension(27, 20));
                gridPanel.add(formGrid[y][x]);
                AddFocusListenerToGridField(formGrid[y][x]); // Attach focus listener

                AddHoverListenerToGridField(formGrid[y][x]);
            }
        }

        // Load CSV file after rebuilding the grid This is the csv file when the application first loads
        String filePath = "Ians_W07_S02_202710201500.csv"; // Update with your CSV file path
        FileData data = file.ReadFromCSV(filePath);
        if (data != null)
        {
            // Populate grid with data from CSV
            for (int y = 0; y < gridRows; y++)
            {
                for (int x = 0; x < gridColumns; x++)
                {
                    if (y < data.gridData.length && x < data.gridData[y].length)
                    {
                        formGrid[y][x].setText(data.gridData[y][x]);
                        if (IsAnInteger(data.gridData[y][x]))
                        {
                            Color cellColor = DetermineCellColour(data.gridData[y][x]);
                            formGrid[y][x].setBackground(cellColor);
                            formGrid[y][x].setForeground(cellColor); // Set font color to match background color
                        }
                    }
                    else
                    {
                        System.out.println("Index out of bounds: y=" + y + ", x=" + x);
                    }
                }
            }
        }
        else
        {
            // Handle case when CSV file couldn't be loaded
            System.out.println("Failed to load CSV file.");
        }

        // Update the scroll pane with the new grid
        if (scrollPane != null)
        {
            scrollPane.setViewportView(gridPanel);
        }
        else
        {
            // Create a new scroll pane if it doesn't exist
            scrollPane = new JScrollPane(gridPanel);
            layout.putConstraint(SpringLayout.NORTH, scrollPane, 100, SpringLayout.NORTH, this);
            layout.putConstraint(SpringLayout.WEST, scrollPane, 150, SpringLayout.WEST, this);
            add(scrollPane);
        }
        scrollPane.getViewport().setViewPosition(new Point(0, 0));

        // Reattach mouse listeners
        attachMouseListenersToGrid();
        revalidate();
        repaint();

        // Set focus to the first text field in the grid
        if (formGrid.length > 0 && formGrid[0].length > 0)
        {
            formGrid[0][0].requestFocus();
        }

    }
    private void AddHoverListenerToGridField(JTextField textField) {
        textField.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                // Example: Display recording level or any other information
                textField.setToolTipText("Recording Level: " + textField.getText());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Handle any necessary actions on mouse exit, if required
            }
        });
    }
}
