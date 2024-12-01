
import java.io.*;


/**
 * A class for managing file operations such as reading from and writing to CSV, RAF, DAT and RPT
 */
public class FileManager
{
    /**
     * Writes data from a FileData object to a .CSV file at the specified file path.
     * @param fileData The FileData object containing the data to be written.
     * @param filePath The path to the .CSV file to write.
     */
    public void WriteToCSVFile(FileData fileData, String filePath)
    {
        // will make sure something is printed but not nulls
        try
        {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
            //will print the header data from the screen
            bufferedWriter.write(fileData.warehouse + "\n");
            bufferedWriter.write(fileData.date + "\n");
            bufferedWriter.write(fileData.time + "\n");

            //cycles through the array of all the data from the grid
            for (int y = 0; y < fileData.gridData.length; y++)
            {
                for (int x = 0; x < fileData.gridData[y].length; x++)
                {
                    //if the cell is blank print a single space then comma
                    if (fileData.gridData[y][x].isEmpty())
                    {
                        bufferedWriter.write("0,");
                    }
                    //otherwise print the cell data followed by a comma
                    else
                    {
                        bufferedWriter.write(fileData.gridData[y][x] + ",");
                    }
                }
                //start a new line once loop is finished
                bufferedWriter.newLine();
            }
            bufferedWriter.close();
        }
        catch (Exception ex)
        {
            //will print the error to console
            System.out.println(ex.getMessage());
        }
    }
    public FileData ReadFromCSV(String filePath)
    {
        FileData fileData = new FileData();
        //ours will be 30 by 30
        fileData.gridData = new String[30][30];
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            //read the first 2 lines of the file to get our header data
            fileData.warehouse = reader.readLine();
            fileData.date = reader.readLine();
            fileData.time = reader.readLine();


            String line;
            //set a variable to count how many rows we have read so far, this will be used to also tracl  which index
            // we are up to on our outer array of the 2D array in the grid data.
            int count = 0;
            //read each line and run our loop if it ha text
            while((line = reader.readLine()) != null)
            {
                //split the line and store it in a string array
                String[] temp = line.split(",");
                //assign the string array to the crrent element in the 20 array as its sub array
                fileData.gridData[count] = temp;
                //increase the count value
                count++;
            }
            //use the count variable and the length of one of our rows to set the row and column
            // counts in the file data
            fileData.columnCount = fileData.gridData[0].length;
            fileData.rowCount = count;
            //close the reader so the reading process finishes correctly
            reader.close();
        }
        catch (Exception ex)
        {
            fileData = null;
            System.out.println(ex.getMessage());
        }
        // will return null or will return data properly
        return fileData;
    }

    /**
     * Writes data from a FileData object to a Random Access File (RAF) at the specified file path.
     * @param fileData The FileData object containing the data to be written.
     * @param filePath The path to the RAF file to write.
     */
    public void WriteToRaFFile(FileData fileData, String filePath)
    {
        try
        {
            //creates the random access file for reading and writing to RAF Files.
            //rw means read and write to file
            RandomAccessFile raf = new RandomAccessFile(filePath,"rw");
            //rest the length of the raf file before writing to stop ay risk of redunant data being
            // left at the end of the file if the number of entries being written is less
            // than previous writes, runs the whole data set
            raf.setLength(0);

            //header data
            raf.seek(0);
            // goes to region name data to check string
            raf.writeUTF(fileData.warehouse);
            raf.seek(32);
            raf.writeUTF(fileData.date);
            raf.seek(44);
            raf.writeUTF(fileData.time);
            raf.seek(56);
            raf.writeInt(fileData.rowCount);
            raf.seek(60);
            raf.writeInt(fileData.columnCount);

            //track how many grid entries have been written, used to calculate the starting index of each entry

            int count = 0;
            for (int y = 0; y < fileData.gridData.length; y++)
            {
                for (int x = 0; x < fileData.gridData[y].length; x++)
                {
                    if (fileData.gridData[y][x].isEmpty() == false)
                    {
                        // forumla: index = {entry number} * {Entry size} + {offset for header data}
                        int index = count * 20 + 64;
                        // move to the starting point of the entry which is the index calculated
                        raf.seek(index);
                        //write the y value from our outer loop which is row number
                        raf.writeInt(y);
                        //calculate the next writing position by adding the column field offset to the index
                        raf.seek(index + 4);
                        //write the x value from inner loop which is also our column number
                        raf.writeInt(x);
                        //find the position of the last field by adding its offeset to starting index
                        raf.seek(index + 8);
                        // write to cell content to  position
                        raf.writeUTF(fileData.gridData[y][x]);
                        //increment the counter to help with the next index calculation
                        count++;

                    }
                }

            }


            // close the raf file connection
            raf.close();

        }
        catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    public FileData ReadFromRAF(String filePath)
    {
        //create an emtpy file data object to put our data onto as you read
        FileData fileData = new FileData();
        try
        {
            //create the class for interacting from the RAF file, the r means read only
            RandomAccessFile raf = new RandomAccessFile(filePath, "r");
            //header data
            raf.seek(0);
            // goes to warehouse data to check string
            fileData.warehouse = raf.readUTF();
            raf.seek(32);
            fileData.date = raf.readUTF();
            raf.seek(44);
            fileData.time = raf.readUTF();
            raf.seek(56);
            fileData.rowCount = raf.readInt();
            raf.seek(60);
            fileData.columnCount = raf.readInt();
            //create the 2D array in the grid using the sizes extracted from the file
            fileData.gridData = new String[fileData.rowCount][fileData.columnCount];


            //stores the index of the current entry we are going to read
            int index = 0;
            //tracks how many enteris we have read so far, thos will be used to help caclulate the ondex after the loop
            int count = 0;

            //calculate the next index for reading loop will only run if index within the size of RAF fo;e array
            //while loops to keep running until we run out of data
            while ((index = count * 20 + 64) < raf.length()) {
                //go to the first index of the array and read the value at the position and store it
                raf.seek(index);
                int row = raf.readInt();
                //go to the index of the next field and read the value at the position and store it
                raf.seek(index + 4);
                int column = raf.readInt();
                raf.seek(index + 8);
                String value = raf.readUTF();
                // used to retrieve data and find the correct index and store it is the value into a element.
                fileData.gridData[row][column] = value;

                count++;
            }

            // closes the connection once the reading is finished
            raf.close();
        }
        catch (Exception ex)
        {
            fileData = null;
            System.out.println(ex.getMessage());
        }
        //returns tje fo;e data to where ever the method is called from
        return fileData;

    }
    /**
     * Writes data from a FileData object to an RPT (report) file at the specified file path.
     * @param fileData The FileData object containing the data to be written.
     * @param filePath The path to the RPT file to write.
     */
    public void WriteToRPTFile(FileData fileData, String filePath)
    {
        // for this write to file to write, client specifically asked for the file format to be like (colour1,count1, colour2,count2, colour3,count3,)
        //W,20
        //W,3,G,15,W,2
        //W,3,G,15,W,2
        //W,3,G,15,W,2
        //W,3,G,3,Y,5,G,7,W,2
        //W,6,Y,5,W,9



        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // Write metadata to the RPT file
            writer.write("Warehouse: " + fileData.warehouse);
            writer.newLine(); // Move to the next line after writing warehouse data
            writer.write("Date: " + fileData.date);
            writer.newLine(); // Move to the next line after writing date data
            writer.write("Time: " + fileData.time);
            writer.newLine(); // Move to the next line after writing time data

            // Write grid data to the RPT file
            for (int row = 0; row < fileData.rowCount; row++) {
                StringBuilder rowData = new StringBuilder(); // StringBuilder to store row data
                for (int col = 0; col < fileData.columnCount; col++) {
                    if (!fileData.gridData[row][col].isEmpty()) {
                        rowData.append(fileData.gridData[row][col]).append(","); // Append color
                        rowData.append(fileData.gridData[row][col + 1]).append(","); // Append count
                        col++; // Move to the next cell (skip count since it's already processed)
                    }
                }
                if (rowData.length() > 0)
                {
                    // Remove the last comma
                    rowData.deleteCharAt(rowData.length() - 1);
                    // Write rowData to the file
                    writer.write(rowData.toString());
                    writer.newLine(); // Move to the next line after writing each row
                }
            }
            System.out.println("Data successfully written to RPT file: " + filePath);
        }
        catch (IOException e)
        {
            // Print error message if an IOException occurs during file writing
            System.err.println("Error writing to RPT file: " + e.getMessage());
        }


    }

    public FileData ReadFromRPT(String filePath)
    {
        FileData fileData = new FileData();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
        {
            String line;
            int lineCount = 0; // Keep track of the line number to determine which metadata we're reading

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim leading and trailing whitespace

                if (line.isEmpty())
                {
                    continue; // Skip empty lines
                }

                if (lineCount == 0)
                {
                    // Warehouse metadata (first line)
                    fileData.warehouse = line.split(":\\s*")[1];
                }
                else if (lineCount == 1)
                {
                    // Date metadata (second line)
                    fileData.date = line.split(":\\s*")[1];
                }
                else if (lineCount == 2)
                {
                    // Time metadata (third line)
                    fileData.time = line.substring(line.indexOf(":") + 1).trim();
                }
                else
                {
                    // Grid data (remaining lines)
                    String[] values = line.split(",\\s*"); // Split line by commas, considering possible whitespace after comma
                    if (fileData.rowCount == 0) {
                        fileData.rowCount = 1; // First row encountered
                        fileData.columnCount = values.length; // Set the column count based on the first row
                        fileData.gridData = new String[100][fileData.columnCount]; // Initialize gridData array
                    }
                    fileData.gridData[fileData.rowCount - 1] = values; // Assign values to the gridData array
                    fileData.rowCount++; // Move to the next row
                }

                lineCount++; // Move to the next line
            }
        }
        catch (IOException e)
        {
            System.err.println("Error reading from RPT file: " + e.getMessage());
            return null; // Return null if there's an error reading the file
        }

        return fileData;
    }

    /**
     * Writes data from a FileData object to a DAT (data) file at the specified file path.
     * @param fileData The FileData object containing the data to be written.
     * @param filePath The path to the DAT file to write.
     */

    // for this write to dat file I needed to follow the scenario follows this format when saved
    // (x,y,colour)
    //...
    //1,1,W
    //2,1,W
    //3,1,W
    //4,1,G
    //...
    //7,2,G
    //8,2,G
    //9,2,Y
    //...
        public void WriteToDATFile(FileData fileData, String filePath)
        {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
            {
                // Write metadata to the DAT file
                writer.write("Warehouse: " + fileData.warehouse);
                writer.newLine();
                writer.write("Date: " + fileData.date);
                writer.newLine();
                writer.write("Time: " + fileData.time);
                writer.newLine();

                // Write grid data to the DAT file
                for (int row = 0; row < fileData.rowCount && row < 30; row++)
                {
                    for (int col = 0; col < fileData.columnCount && col < 30; col++)
                    {
                        // Check if the cell is not empty
                        if (!fileData.gridData[row][col].isEmpty())
                        {
                            // Write (row + 1), (col + 1), colour to the file in the specified format
                            writer.write((row + 1) + "," + (col + 1) + "," + fileData.gridData[row][col]);
                            writer.newLine(); // Move to the next line after writing each row
                        }
                    }
                }
            }
            catch (IOException e)
            {
                System.err.println("Error writing to DAT file: " + e.getMessage());
            }
        }


        public FileData ReadFromDAT(String filePath)
        {
            FileData fileData = new FileData();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath)))
            {
                String line;
                int lineCount = 0;
                // Read each line of the .DAT file
                while ((line = reader.readLine()) != null)
                {
                    switch (lineCount)
                    {
                        case 0:
                            // Parse warehouse information from the first line
                            fileData.warehouse = extractValue(line);
                            break;
                        case 1:
                            // Parse date information from the second line
                            fileData.date = extractValue(line);
                            break;
                        case 2:
                            // Parse time information from the third line
                            fileData.time = extractValue(line);
                            break;
                        default:
                            // Parse grid data from subsequent lines
                            String[] values = line.split(",");
                            if (values.length == 3)
                            {
                                int row = Integer.parseInt(values[0].trim()) - 1; // Convert to zero-based index
                                int col = Integer.parseInt(values[1].trim()) - 1; // Convert to zero-based index
                                String colour = values[2].trim();

                                // Ensure row and col are within bounds
                                if (row >= 0 && row < 30 && col >= 0 && col < 30)
                                {
                                    // Initialize gridData array if not already initialized
                                    if (fileData.gridData == null)
                                    {
                                        fileData.gridData = new String[30][30];
                                    }

                                    // Assign colour to gridData
                                    fileData.gridData[row][col] = colour;
                                }
                                else
                                {
                                    System.err.println("Error: Indices out of bounds: " + (row + 1) + "," + (col + 1));
                                }
                            }
                            break;
                    }
                    lineCount++;
                }
                // Set row and column counts based on the parsed grid data
                fileData.rowCount = 30; // Maximum rows
                fileData.columnCount = 30; // Maximum columns
            }
            catch (IOException e)
            {
                // Handle IOException if file reading fails
                System.err.println("Error reading from DAT file: " + e.getMessage());
            }
            return fileData; // Return the parsed FileData object
    }
    // used in the code above
    private String extractValue(String line)
    {
        int index = line.indexOf(":");
        if (index != -1 && index + 1 < line.length())
        {
            return line.substring(index + 1).trim(); // Extract and trim the value part
        }
        return ""; // Return an empty string if the line does not contain a colon or if it's at the end of the line
    }



}
