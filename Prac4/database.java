import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.*;
import java.sql.*;
import java.util.regex.*;

//Usually you will require both swing and awt packages
// even if you are working with just swings.
import java.awt.*;
class database {
    public static void main(String args[]) {
        //Creating the Frame
        JFrame frame = new JFrame("Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Text Area at the Center
        //Create tabs
        JTextField ta = new JTextField();
        ta.setPreferredSize(new Dimension(100, 20));
        JPanel tab1 = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        JButton submitFilters = new JButton("Filter");
        c.gridx = 0;
        c.gridy = 0;
        JPanel tab1Text = new JPanel();
        JPanel tab1Table = new JPanel();
        tab1Text.add(new JLabel("Filter:"));
        tab1Text.add(ta);
        tab1Text.add(submitFilters);
        tab1.add(tab1Text, c);
        c.gridy = 1;
        tab1.add(tab1Table, c);
        ta.setVisible(true);
        JPanel tab2 = new JPanel(new BorderLayout());
        // Add a button to trigger the pop up for adding new data to the Films tab
        JButton addButton = new JButton("Add Film");
        
        tab2.add(addButton, BorderLayout.NORTH);
        JPanel filmsPanel = new JPanel();

        tab2.add(filmsPanel, BorderLayout.CENTER);
        JPanel tab3 = new JPanel();
        JPanel tab4 = new JPanel();
        JTabbedPane tabPan = new JTabbedPane();
        tabPan.setBounds(0, 0, 1200, 800);
        tabPan.addTab("Staff", tab1);
        tabPan.addTab("Films", tab2);
        tabPan.addTab("Report", tab3);
        tabPan.addTab("Notifications", tab4);
        frame.add(tabPan);
        frame.setLayout(null);
        frame.setVisible(true);

        //Adding Components to the frame.
        frame.setVisible(true);

        

        //Connecting to Database
        Connection connection = null;
        try {
            // below two lines are used for connectivity.
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3307/u04954336_u22561154_sakila",
                "<username>", "<password>");
                    String [] colNames = new String [10];
                    colNames[0] = "First Name";
                    colNames[1] = "Last Name";
                    colNames[2] = "Address";
                    colNames[3] = "Address 2";
                    colNames[4] = "District";
                    colNames[5] = "City";
                    colNames[6] = "Postal Code";
                    colNames[7] = "Phone";
                    colNames[8] = "Store";
                    colNames[9] = "Active";
        
                    Statement statement;
                    statement = connection.createStatement();
                    ResultSet resultSet;
                    resultSet = statement.executeQuery("SELECT count(*) from staff");
                    int count = 0;
                    while (resultSet.next()){
                        count = resultSet.getInt(1);
                    }
                    String [][] data = new String[count][10];
                    resultSet = statement.executeQuery("SELECT s.first_name, s.last_name, a.address, a.address2, a.district, c.city, a.postal_code, a.phone, s.store_id, s.active FROM staff s, address a, city c WHERE s.address_id = a.address_id AND a.city_id = c.city_id;");
                    int i = 0;
                    while (resultSet.next()){
                        data[i][0] = resultSet.getString("first_name");
                        data[i][1] = resultSet.getString("last_name");
                        data[i][2] = resultSet.getString("address");
                        if(resultSet.getString("address2") != null)
                            data[i][3] = resultSet.getString("address2");
                        else
                            data[i][3] = " ";                            
                        data[i][4] = resultSet.getString("district");
                        data[i][5] = resultSet.getString("city");
                        data[i][6] = resultSet.getString("postal_code");
                        data[i][7] = resultSet.getString("phone");
                        data[i][8] = "" + resultSet.getInt("store_id");
                        if (resultSet.getBoolean("active")){
                            data[i][9] = "Yes";
                        }
                        else{
                            data[i][9] = "No";
                        }
                        i++;
                    }

            //THE FOLLOWING CODE WAS OBTAINED FROM:
            //https://www.tutorialspoint.com/how-can-we-filter-a-jtable-in-java#:~:text=A%20JTable%20class%20provides%20the,()%20method%20of%20TableRowSorter%20class.
            TableModel model = new DefaultTableModel(data, colNames) {
                public Class getColumnClass(int column) {
                   Class returnValue;
                   if((column >= 0) && (column < getColumnCount())) {
                      returnValue = getValueAt(0, column).getClass();
                   } else {
                      returnValue = Object.class;
                   }
                   return returnValue;
                }
             };
             
            JTable table1 = new JTable(model);         
            JScrollPane sp = new JScrollPane(table1);
            sp.setPreferredSize(new Dimension(1000, 600));
            tab1Table.add(sp);
            sp.setVisible(true);
            final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(model);
            table1.setRowSorter(sorter);
            submitFilters.addActionListener(click ->{
                    String text = ta.getText();
                    if(text.length() == 0) {
                       sorter.setRowFilter(null);
                    } else {
                       try {
                          sorter.setRowFilter(RowFilter.regexFilter(text));
                       } catch(PatternSyntaxException pse) {
                             System.out.println("Bad regex pattern");
                       }
                     }
            });

            ResultSet resultSet2;
            resultSet2 = statement.executeQuery("SELECT COUNT(*) FROM film");
            int count2 = 0;
            while(resultSet2.next()){
                count2 = resultSet2.getInt(1);
            }
            String[] colNames2 = new String [9];
            colNames2[0] = "Title";
            colNames2[1] = "Description";
            colNames2[2] = "Release Year";
            colNames2[3] = "Rental Duration";
            colNames2[4] = "Rental Rate";
            colNames2[5] = "Length";
            colNames2[6] = "Replacement Cost";
            colNames2[7] = "Rating";
            colNames2[8] = "Special Features";
            String data2[][] = new String[count2][9];
            
            resultSet2 = statement.executeQuery("SELECT title, description, release_year, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film");
            int j = 0;
            while(resultSet2.next()){
                data2[j][0] = resultSet2.getString("title");
                data2[j][1] = resultSet2.getString("description");
                data2[j][2] = resultSet2.getString("release_year");
                data2[j][3] = resultSet2.getString("rental_duration");
                data2[j][4] = resultSet2.getString("rental_rate");
                data2[j][5] = resultSet2.getString("length");
                data2[j][6] = resultSet2.getString("replacement_cost");
                data2[j][7] = resultSet2.getString("rating");
                data2[j][8] = resultSet2.getString("special_features");
                j++;
            }

            TableModel model2 = new DefaultTableModel(data2, colNames2) {
                public Class getColumnClass(int column) {
                   Class returnValue;
                   if((column >= 0) && (column < getColumnCount())) {
                      returnValue = getValueAt(0, column).getClass();
                   } else {
                      returnValue = Object.class;
                   }
                   return returnValue;
                }
            };

            JTable table2 = new JTable(model2);
            JScrollPane sp2 = new JScrollPane(table2);
            sp2.setPreferredSize(new Dimension(1000, 600));
            filmsPanel.add(sp2);
            
            addButton.addActionListener(e -> {
                JDialog dialog = new JDialog(frame, "Add Film", true);
                dialog.setSize(500, 400);
                dialog.setLayout(new GridLayout(12, 2));
    
                dialog.add(new JLabel("Title:"));
                JTextField titleField = new JTextField();
                dialog.add(titleField);
                dialog.add(new JLabel("Description:"));
                JTextField descriptionField = new JTextField();
                dialog.add(descriptionField);
                dialog.add(new JLabel("Release year:"));
                JTextField yearField = new JTextField();
                dialog.add(yearField);
                dialog.add(new JLabel("Language ID:"));
                JTextField languageField = new JTextField();
                dialog.add(languageField);
                dialog.add(new JLabel("Original Lanuage ID:"));
                JTextField ogLanguageField = new JTextField();
                dialog.add(ogLanguageField);
                dialog.add(new JLabel("Rental Duration:"));
                JTextField rentalField = new JTextField();
                dialog.add(rentalField);
                dialog.add(new JLabel("Rental Rate:"));
                JTextField rentalRateField = new JTextField();
                dialog.add(rentalRateField);
                dialog.add(new JLabel("Length:"));
                JTextField lengthField = new JTextField();
                dialog.add(lengthField);
                dialog.add(new JLabel("Replacement Cost:"));
                JTextField replacementCostField = new JTextField();
                dialog.add(replacementCostField);
                dialog.add(new JLabel("Rating:"));
                JTextField ratingField = new JTextField();
                dialog.add(ratingField);
                dialog.add(new JLabel("Special Features:"));
                JTextField specialFeaturesField = new JTextField();
                dialog.add(specialFeaturesField);
    
                JButton saveButton = new JButton("Save");
                saveButton.addActionListener(e1 -> {
                    String title = titleField.getText();
                    String description = descriptionField.getText();
                    String year = yearField.getText();
                    String language = languageField.getText();
                    String ogLanguage = ogLanguageField.getText();
                    if(ogLanguage.length() == 0){
                        ogLanguage = "NULL";
                    }
                    String rental = rentalField.getText();
                    String rentalRate = rentalRateField.getText();                    
                    String length = lengthField.getText();
                    String replacement = replacementCostField.getText();
                    String rating = ratingField.getText();
                    String specialFeatures = specialFeaturesField.getText();
                    if(title.length() != 0 && description.length() != 0 && year.length() != 0 && language.length() != 0 && ogLanguage.length() != 0 && rental.length() != 0 && rentalRate.length() != 0 && length.length() != 0 && replacement.length() != 0 && rating.length() != 0 && specialFeatures.length() != 0){
                        try{
                            statement.execute("INSERT INTO film(title, description, release_year, language_id, original_language_id, rental_duration, rental_rate, length, replacement_cost, rating, special_features)"
                                               + " VALUES(\'"+title+"\',\'"+description+"\', "+year+", "+language+", "+ogLanguage+", "+rental+", "+rentalRate+", "+length+", "+replacement+", \'"+rating+"\', (\'"+specialFeatures+"\'));");
                                               
                            int countInsert = 0;
                            ResultSet insertSet;
                            insertSet = statement.executeQuery("SELECT COUNT(*) FROM film");
                            while(insertSet.next()){
                                countInsert = insertSet.getInt(1);
                            }
                            String[][] dataInsert = new String[countInsert][12];
                            insertSet = statement.executeQuery("SELECT title, description, release_year, rental_duration, rental_rate, length, replacement_cost, rating, special_features FROM film");
                            int x = 0;
                            while(insertSet.next()){
                                dataInsert[x][0] = insertSet.getString("title");
                                dataInsert[x][1] = insertSet.getString("description");
                                dataInsert[x][2] = insertSet.getString("release_year");
                                dataInsert[x][3] = insertSet.getString("rental_duration");
                                dataInsert[x][4] = insertSet.getString("rental_rate");
                                dataInsert[x][5] = insertSet.getString("length");
                                dataInsert[x][6] = insertSet.getString("replacement_cost");
                                dataInsert[x][7] = insertSet.getString("rating");
                                dataInsert[x][8] = insertSet.getString("special_features");
                                x++;
                            }
                            TableModel modelInsert = new DefaultTableModel(dataInsert, colNames2) {
                                public Class getColumnClass(int column) {
                                   Class returnValue;
                                   if((column >= 0) && (column < getColumnCount())) {
                                      returnValue = getValueAt(0, column).getClass();
                                   } else {
                                      returnValue = Object.class;
                                   }
                                   return returnValue;
                                }
                            };
                            table2.setModel(modelInsert);
                        }

                        catch(Exception ex){
                            System.out.println(ex);
                        }
    
                        dialog.dispose();
                    }
                });
                dialog.add(saveButton);
    
                JButton cancelButton = new JButton("Cancel");
                cancelButton.addActionListener(e1 -> dialog.dispose());
                dialog.add(cancelButton);
                dialog.setVisible(true);
            });


        ////////////////////////////////
        //    NOTIFICATION SECTION    //
        ////////////////////////////////

        JButton displayClient = new JButton("Display list of Customers");
        JButton insert = new JButton("Insert");
        JButton update = new JButton("Update");
        JButton delete = new JButton("Delete");
        JButton inactive = new JButton("Display list of Customers who dropped subscription");
        

        displayClient.addActionListener(e -> {
            try{
                JPanel databasePanel = new JPanel(); //need to display the changes
                JPanel filterPanel = new JPanel();
                JPanel overallPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gridC = new GridBagConstraints();
                gridC.gridy = 0;
                gridC.gridx = 0;
                int countCustomers = 0;
                ResultSet notifResultSet;
                notifResultSet = statement.executeQuery("SELECT COUNT(*) FROM customer");
                while(notifResultSet.next()){
                    countCustomers = notifResultSet.getInt(1);
                }

                String[] columnNames = {"First Name", "Last Name", "Email", "Address", "Address2", "District", "City", "Postal Code", "Phone", "Active"};
                String[][] rowData = new String[countCustomers][columnNames.length];
                notifResultSet = statement.executeQuery("SELECT c.first_name, c.last_name, c.email, a.address, a.address2, a.district, ci.city, a.postal_code, a.phone, c.active"
                                                        +" FROM customer c, address a, city ci WHERE c.address_id = a.address_id AND a.city_id = ci.city_id");
                int y = 0;
                    while (notifResultSet.next()){
                        rowData[y][0] = notifResultSet.getString("first_name");
                        rowData[y][1] = notifResultSet.getString("last_name");
                        rowData[y][2] = notifResultSet.getString("email");
                        rowData[y][3] = notifResultSet.getString("address");
                        if(notifResultSet.getString("address2") != null)
                            rowData[y][4] = notifResultSet.getString("address2");
                        else
                            rowData[y][4] = " ";                            
                        rowData[y][5] = notifResultSet.getString("district");
                        rowData[y][6] = notifResultSet.getString("city");
                        rowData[y][7] = notifResultSet.getString("postal_code");
                        rowData[y][8] = notifResultSet.getString("phone");
                        if (notifResultSet.getBoolean("active")){
                            rowData[y][9] = "Yes";
                        }
                        else{
                            rowData[y][9] = "No";
                       }
                    y++;
                }
                DefaultTableModel tempTable = new DefaultTableModel(rowData, columnNames) {
                    public Class getColumnClass(int column) {
                       Class returnValue;
                       if((column >= 0) && (column < getColumnCount())) {
                          returnValue = getValueAt(0, column).getClass();
                       } else {
                          returnValue = Object.class;
                       }
                       return returnValue;
                    }
                };                
                JTextField tf = new JTextField();
                tf.setPreferredSize(new Dimension(100, 20));
                JButton submitFilterNotif = new JButton("Filter");
                filterPanel.add(tf);
                filterPanel.add(submitFilterNotif);

                JTable table = new JTable(tempTable);
                JScrollPane tableScroll = new JScrollPane(table);
                tableScroll.setPreferredSize(new Dimension(1000,600));
                databasePanel.add(tableScroll);
                overallPanel.add(filterPanel, gridC);
                gridC.gridy = 1;
                overallPanel.add(databasePanel, gridC);
                final TableRowSorter<TableModel> sorterNotif = new TableRowSorter<TableModel>(tempTable);
                table.setRowSorter(sorterNotif);
                submitFilterNotif.addActionListener(click ->{
                    String text = tf.getText();
                    if(text.length() == 0) {
                       sorterNotif.setRowFilter(null);
                    } else {
                       try {
                          sorterNotif.setRowFilter(RowFilter.regexFilter(text));
                       } catch(PatternSyntaxException pse) {
                             System.out.println("Bad regex pattern");
                       }
                     }
                });
            
                JOptionPane.showMessageDialog(frame, overallPanel, "Clients", JOptionPane.PLAIN_MESSAGE);
            }
            catch(Exception except){
                System.out.println(except);
            }
        });

        insert.addActionListener(e1 -> {
            JDialog dialog = new JDialog(frame, "Insert New Record:", true);
            dialog.setSize(600, 400);
            dialog.setLayout(new GridLayout(12, 2));

            dialog.add(new JLabel("Store ID:"));
            JTextField storeIDText = new JTextField();
            dialog.add(storeIDText);
            dialog.add(new JLabel("Name:"));
            JTextField nameText = new JTextField();
            dialog.add(nameText);
            dialog.add(new JLabel("Surname:"));
            JTextField surnameText = new JTextField();
            dialog.add(surnameText);
            dialog.add(new JLabel("Email:"));
            JTextField emailText = new JTextField();
            dialog.add(emailText);
            dialog.add(new JLabel("Address:"));
            JTextField addressText = new JTextField();
            dialog.add(addressText);
            dialog.add(new JLabel("Address 2:"));
            JTextField address2Text = new JTextField();
            dialog.add(address2Text);
            dialog.add(new JLabel("District:"));
            JTextField districtText = new JTextField();
            dialog.add(districtText);
            dialog.add(new JLabel("City:"));
            JTextField cityText = new JTextField();
            dialog.add(cityText);
            dialog.add(new JLabel("Country:"));
            JTextField countryText = new JTextField();
            dialog.add(countryText);
            dialog.add(new JLabel("Postal Code:"));
            JTextField postalCodeText = new JTextField();
            dialog.add(postalCodeText);
            dialog.add(new JLabel("Phone:"));
            JTextField phoneText = new JTextField();
            dialog.add(phoneText);


            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> {
                String storeID = storeIDText.getText();
                String name = nameText.getText();
                String surname = surnameText.getText();
                String email = emailText.getText();
                String address = addressText.getText();
                String address2 = address2Text.getText();
                if(address2.length() == 0){
                    address2 = " ";
                }
                String district = districtText.getText();
                String city = cityText.getText();
                String country = countryText.getText();
                String postalCode = postalCodeText.getText();
                if(postalCode.length() == 0){
                    postalCode = " ";
                }
                String phone = phoneText.getText();
                if(phone.length() == 0){
                    phone = " ";
                }

                // Add code here to save the data to the database
                try{
                    ResultSet insertSet;
                    int countryID = 0;
                    insertSet = statement.executeQuery("SELECT * FROM country WHERE country = \'"+country+"\'");
                    if(insertSet.next()){
                        countryID = insertSet.getInt("country_id");
                    }
                    else{
                        statement.execute("INSERT INTO country(country, last_update) VALUES(\'"+country+"\', NOW())");
                        insertSet = statement.executeQuery("SELECT country_id FROM country WHERE country = \'"+country+"\'");
                        while(insertSet.next()){
                            countryID = insertSet.getInt(1);
                        }
                    }
                    int cityID = 0;
                    insertSet = statement.executeQuery("SELECT * FROM city WHERE city = \'"+city+"\'");
                    if(insertSet.next()){
                        cityID = insertSet.getInt("city_id");
                    }
                    else{
                        statement.execute("INSERT INTO city(city, country_id, last_update) VALUES(\'"+city+"\', "+countryID+", NOW())");
                        insertSet = statement.executeQuery("SELECT city_id FROM city WHERE city = \'"+city+"\'");
                        while(insertSet.next()){
                            cityID = insertSet.getInt(1);
                        }
                    }
                    
                    statement.execute("INSERT INTO address(address, address2, district, city_id, postal_code, phone, location, last_update) "
                                          +"VALUES(\'"+address+"\', \'"+address2+"\', \'"+district+"\', "+cityID+", \'"+postalCode+"\', \'"+phone+"\', ST_GeomFromText('POINT(0 0)'), NOW())");
                    insertSet = statement.executeQuery("SELECT LAST_INSERT_ID()");
                    int addressID = 0;
                    while(insertSet.next()){
                        addressID = insertSet.getInt(1);
                    }
                    statement.execute("INSERT INTO customer(store_id, first_name, last_name, email, address_id, active, create_date, last_update) "
                                          +"VALUES("+storeID+", \'"+name+"\', \'"+surname+"\', \'"+email+"\', "+addressID+", 1, NOW(), NOW())");
                }
                catch(Exception except){
                    System.out.println(except);
                }

                dialog.dispose();
            });
            dialog.add(saveButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e2 -> dialog.dispose());
            dialog.add(cancelButton);
            dialog.setVisible(true);
        });

        update.addActionListener(e1 -> {
            JDialog dialog = new JDialog(frame, "Update a Record:", true);
            dialog.setSize(600, 400);
            dialog.setLayout(new GridLayout(10, 2));

            dialog.add(new JLabel("Email of user to update:"));
            JTextField emailText = new JTextField();
            dialog.add(emailText);
            dialog.add(new JLabel("New First Name (Leave blank to maintain):"));
            JTextField firstNameNewText = new JTextField();
            dialog.add(firstNameNewText);
            dialog.add(new JLabel("New Last Name (Leave blank to maintain):"));
            JTextField lastNameNewText = new JTextField();
            dialog.add(lastNameNewText);            
            dialog.add(new JLabel("New Phone Number (Leave blank to maintain):"));
            JTextField phoneText = new JTextField();
            dialog.add(phoneText);
            dialog.add(new JLabel("New Email (Leave blank to maintain):"));
            JTextField emailNewText = new JTextField();
            dialog.add(emailNewText);
            dialog.add(new JLabel("New Address 1 (Leave blank to maintain):"));
            JTextField address1NewText = new JTextField();
            dialog.add(address1NewText);
            dialog.add(new JLabel("New Address 2 (Leave blank to maintain):"));
            JTextField address2NewText = new JTextField();
            dialog.add(address2NewText);
            dialog.add(new JLabel("New District (Leave blank to maintain):"));
            JTextField districtNewText = new JTextField();
            dialog.add(districtNewText);
            dialog.add(new JLabel("New Postal Code (Leave blank to maintain):"));
            JTextField postCodeNewText = new JTextField();
            dialog.add(postCodeNewText);

            JButton saveButton = new JButton("Save");
            saveButton.addActionListener(e -> {
                String email = emailText.getText();
                String firstNameNew = firstNameNewText.getText();
                String lastNameNew = lastNameNewText.getText();
                String phone = phoneText.getText();
                String emailNew = emailNewText.getText();
                String address1New = address1NewText.getText();
                String address2New = address2NewText.getText();
                String districtNew = districtNewText.getText();
                String postCodeNew = postCodeNewText.getText();

                // Add code here to save the data to the database
                try{
                    ResultSet updateSet;
                    updateSet = statement.executeQuery("SELECT * FROM customer WHERE email = \'"+email+"\'");
                    int customerId = 0;
                    int addressId = 0;
                    while(updateSet.next()){
                        customerId = updateSet.getInt("customer_id");
                        addressId = updateSet.getInt("address_id");
                    }
                    if(firstNameNew.length() != 0){
                        statement.execute("UPDATE customer SET first_name = \'"+firstNameNew+"\', last_update = NOW() WHERE customer_id = "+customerId);
                    }
                    if(lastNameNew.length() != 0){
                        statement.execute("UPDATE customer SET last_name = \'"+lastNameNew+"\', last_update = NOW() WHERE customer_id = "+customerId);
                    }
                    if(phone.length() != 0){
                        statement.execute("UPDATE address SET phone = \'"+phone+"\', last_update = NOW() WHERE address_id = "+addressId);
                    }
                    if(emailNew.length() != 0){
                        statement.execute("UPDATE customer SET email = \'"+emailNew+"\', last_update = NOW() WHERE customer_id = "+customerId);
                    }
                    if(address1New.length() != 0){
                        statement.execute("UPDATE address SET address = \'"+address1New+"\', last_update = NOW() WHERE address_id = "+addressId);
                    }
                    if(address2New.length() != 0){
                        statement.execute("UPDATE address SET address2 = \'"+address2New+"\', last_update = NOW() WHERE address_id = "+addressId);
                    }
                    if(districtNew.length() != 0){
                        statement.execute("UPDATE address SET district = \'"+districtNew+"\', last_update = NOW() WHERE address_id = "+addressId);
                    }
                    if(postCodeNew.length() != 0){
                        statement.execute("UPDATE address SET postal_code = \'"+postCodeNew+"\', last_update = NOW() WHERE address_id = "+addressId);
                    }
                }
                catch(Exception except){
                    System.out.println(except);
                }

                dialog.dispose();
            });
            dialog.add(saveButton);

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e2 -> dialog.dispose());
            dialog.add(cancelButton);
            dialog.setVisible(true);
        });

        delete.addActionListener(e1 -> {
            JDialog dialog = new JDialog(frame, "Delete a Record:", true);
            dialog.setSize(600, 100);
            dialog.setLayout(new GridLayout(2, 2));

            dialog.add(new JLabel("Email of account to be deleted:"));
            JTextField emailText = new JTextField();
            dialog.add(emailText);

            
            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e2 -> dialog.dispose());

            JButton saveButton = new JButton("Delete");
            saveButton.addActionListener(e -> {
                String email = emailText.getText();

                if(email.length() != 0){
                    try{
                        ResultSet deleteSet;
                        deleteSet = statement.executeQuery("SELECT address_id FROM customer WHERE email = \'"+email+"\'");
                        int addressID = 0;
                        if(deleteSet.next()){
                            addressID = deleteSet.getInt("address_id");
                            statement.execute("DELETE FROM customer WHERE email = \'"+email+"\'");                            
                            statement.execute("DELETE FROM address WHERE address_id = "+addressID);
                        }
                        else{
                            statement.execute("DELETE FROM customer WHERE email = \'"+email+"\'");
                        }

                        dialog.dispose();
                    }
                    catch(Exception except){
                        System.out.println(except);
                    }
                }
            });
            dialog.add(cancelButton);
            dialog.add(saveButton);

            dialog.setVisible(true);
        });

        inactive.addActionListener(inactiveListener ->{
            try{
                int countCustomers = 0;
                ResultSet notifResultSet;
                notifResultSet = statement.executeQuery("SELECT COUNT(*) FROM customer WHERE active = 0");
                while(notifResultSet.next()){
                    countCustomers = notifResultSet.getInt(1);
                }

                String[] columnNames = {"First Name", "Last Name", "Email", "Address", "Address2", "District", "City", "Postal Code", "Phone", "Active"};
                String[][] rowData = new String[countCustomers][columnNames.length];
                notifResultSet = statement.executeQuery("SELECT c.first_name, c.last_name, c.email, a.address, a.address2, a.district, ci.city, a.postal_code, a.phone, c.active"
                                                        +" FROM customer c, address a, city ci WHERE c.active = 0 AND c.address_id = a.address_id AND a.city_id = ci.city_id");
                int y = 0;
                    while (notifResultSet.next()){
                        rowData[y][0] = notifResultSet.getString("first_name");
                        rowData[y][1] = notifResultSet.getString("last_name");
                        rowData[y][2] = notifResultSet.getString("email");
                        rowData[y][3] = notifResultSet.getString("address");
                        if(notifResultSet.getString("address2") != null)
                            rowData[y][4] = notifResultSet.getString("address2");
                        else
                            rowData[y][4] = " ";                            
                        rowData[y][5] = notifResultSet.getString("district");
                        rowData[y][6] = notifResultSet.getString("city");
                        rowData[y][7] = notifResultSet.getString("postal_code");
                        rowData[y][8] = notifResultSet.getString("phone");
                        if (notifResultSet.getBoolean("active")){
                            rowData[y][9] = "Yes";
                        }
                        else{
                            rowData[y][9] = "No";
                       }
                    y++;
                }
                DefaultTableModel tempTable = new DefaultTableModel(rowData, columnNames) {
                    public Class getColumnClass(int column) {
                       Class returnValue;
                       if((column >= 0) && (column < getColumnCount())) {
                          returnValue = getValueAt(0, column).getClass();
                       } else {
                          returnValue = Object.class;
                       }
                       return returnValue;
                    }
                }; 
                JTable table = new JTable(tempTable);
                JScrollPane scrollpane = new JScrollPane(table);
                scrollpane.setPreferredSize(new Dimension(1000, 600));
                JOptionPane.showMessageDialog(frame, scrollpane, "Inactive Customers",JOptionPane.PLAIN_MESSAGE);
            }
            catch (Exception except){
                System.out.println(except);
            }
        });

        tab4.add(displayClient,BorderLayout.NORTH);
        tab4.add(inactive);
        tab4.add(insert);
        tab4.add(update);
        tab4.add(delete);
        

        //////////////////////////
        //    Report Section    //
        //////////////////////////
        JTable reportTable = new JTable();
        JScrollPane reportPane = new JScrollPane(reportTable);
        reportPane.setPreferredSize(new Dimension(1000, 600));
        tab3.add(reportPane);
        tabPan.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e){
                if(tabPan.getSelectedIndex() == 2){
                    try{
                        ResultSet reportSet;
                        reportSet = statement.executeQuery("SELECT COUNT(*) FROM category");
                        int reportCount = 0;
                        while(reportSet.next()){
                            reportCount = reportSet.getInt(1);
                        }
                        reportSet = statement.executeQuery("SELECT COUNT(*) FROM store");
                        while(reportSet.next()){
                            reportCount = reportCount*reportSet.getInt(1);
                        }
                        String[] columnNamesReport = {"Store Number", "Genre", "Number of Movies"};
                        reportSet = statement.executeQuery("SELECT c.name AS genre, s.store_id, COUNT(*) AS film_count"
                                                            +" FROM inventory i"
                                                            +" INNER JOIN film f ON i.film_id = f.film_id"
                                                            +" INNER JOIN film_category fc ON f.film_id = fc.film_id"
                                                            +" INNER JOIN category c ON fc.category_id = c.category_id"
                                                            +" INNER JOIN store s ON i.store_id = s.store_id"
                                                            +" GROUP BY c.name, s.store_id;");
                        String[][] reportData = new String[reportCount][3];
                        int k = 0;
                        while(reportSet.next()){
                            reportData[k][0] = reportSet.getString("store_id");
                            reportData[k][1] = reportSet.getString("genre");
                            reportData[k][2] = reportSet.getString("film_count");
                            k++;
                        }
                        TableModel tempTable = new DefaultTableModel(reportData, columnNamesReport) {
                            public Class getColumnClass(int column) {
                               Class returnValue;
                               if((column >= 0) && (column < getColumnCount())) {
                                  returnValue = getValueAt(0, column).getClass();
                               } else {
                                  returnValue = Object.class;
                               }
                               return returnValue;
                            }
                        };
                        reportTable.setModel(tempTable);
                    }
                    catch(Exception exception){
                        System.out.println(exception);
                    }
                }
            }
        });

        }
        catch (Exception exception) {
            System.out.println(exception);
        }
    }
   
}