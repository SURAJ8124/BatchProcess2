package org.example;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/FacebookApi", "root", "root@123");
            Statement stmt = con.createStatement();
            Statement statement = null;
            statement = con.createStatement();
            String sql = "SELECT * FROM Users";
            ResultSet resultSet = statement.executeQuery(sql);
            String sql1 = "UPDATE Users set name=? where faceBookId=?";

        // Create a URL object for the API endpoint
        URL url = new URL("https://run.mocky.io/v3/cf3f0c29-19a9-45e5-b3fd-21a5fb425482");
// Open a connection to the API endpoint
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
// Set the request method and headers (if needed)
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Content-Type", "application/json");
// Get the response code from the API
        int responseCode = connection.getResponseCode();
// If the response code indicates success (2xx range)
        if (responseCode >= 200 && responseCode < 300) {
            // Read the response stream as a String
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                responseBuilder.append(line);
            }
            String response = responseBuilder.toString();
            // Use Gson to parse the JSON array and convert it into an array of Person objects
            Gson gson = new Gson();
            ApiData[] people = gson.fromJson(response, ApiData[].class);

//            System.out.println("Data updated successFul !!!");
            for (ApiData person : people) {
                if (person != null) {
                    int id= Integer.parseInt(person.getFaceBookId());
                    String personName= person.getName();
                    while (resultSet.next()) {
                        int faceBookId = resultSet.getInt("faceBookId");
                        if(faceBookId==id){
                            PreparedStatement stmt1 = con.prepareStatement(sql1);
                            stmt1.setString(1, personName);
                            stmt1.setInt(2, id);
                            stmt1.executeUpdate();
                        }

                    }
                }
            }
           }
       else {
        // Handle the error response from the API
        System.out.println("API returned error code: " + responseCode);
    }
            stmt.close();
            resultSet.close();
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    }