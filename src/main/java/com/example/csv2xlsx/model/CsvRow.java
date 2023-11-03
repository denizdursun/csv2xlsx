package com.example.csv2xlsx.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CsvRow {

    private String companyId;
    private String companyName;
    private String taxNumber;
    private String latitude;
    private String longitude;
    private String cityName;
    private String districtName;
    private String neighborhoodName;
    private String streetName;
    private String companySubTypeCode;

    public CsvRow(String companyId, String companyName, String taxNumber, String latitude, String longitude, 
    String cityName, String districtName, String neighborhoodName, String streetName, String companySubTypeCode) {
        this.companyId = companyId;
        this.companyName = companyName;
        this.taxNumber = taxNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityName = cityName;
        this.districtName = districtName;
        this.neighborhoodName = neighborhoodName;
        this.streetName = streetName;
        this.companySubTypeCode = companySubTypeCode;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String citName) {
        this.cityName = cityName;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    public String getNeighborhoodName() {
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName) {
        this.neighborhoodName = neighborhoodName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getCompanySubTypeCode() {
        return companySubTypeCode;
    }

    public void setCompanySubTypeCode(String companySubTypeCode) {
        this.companySubTypeCode = companySubTypeCode;
    }

    public int size() {
        return 10;
    }
    
    public String get(int index) {
        switch (index) {
            case 0:
                return companyId;
            case 1:
                return companyName;
            case 2:
                return taxNumber;
            case 3:
                return latitude;
            case 4:
                return longitude;
            case 5:
                return cityName;
            case 6:
                return districtName;
            case 7:
                return neighborhoodName;
            case 8:
                return streetName;
            case 9:
                return companySubTypeCode;
            default:
                throw new IndexOutOfBoundsException("Index out of range");
        }
    }
    
    public static List<CsvRow> parse(InputStream csvFileInputStream) throws IOException {
        List<CsvRow> rows = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(csvFileInputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                //String[] columns = line.split("#|#");
                //String[] columns = line.split("\\|\\#\\|");
                Pattern pattern = Pattern.compile("#\\|#");
                String[] columns = pattern.split(line);
                if (columns.length >= 10) {
                    CsvRow row = new CsvRow(columns[0].replace("\"", ""), columns[1], columns[2], columns[3], columns[4], columns[5], columns[6], columns[7], columns[8], columns[9]);
                    rows.add(row);
                }
            }
        }

        return rows;
    }

}