package covid19trackingmanager;

public enum States{
    ALABAMA("ALABAMA", "AL"),
    ALASKA("ALASKA", "AK"),
    ARIZONA("ARIZONA", "AZ"),
    ARKANSAS("ARKANSAS", "AR"),
    CALIFORNIA("CALIFORNIA", "CA"),
    COLORADO("COLORADO", "CO"),
    CONNECTICUT("CONNECTICUT", "CT"),
    DELAWARE("DELAWARE", "DE"),
    FLORIDA("FLORIDA", "FL"),
    GEORGIA("GEORGIA", "GA"),
    HAWAII("HAWAII", "HI"),
    IDAHO("IDAHO", "ID"),
    ILLINOIS("ILLINOIS", "IL"),
    INDIANA("INDIANA", "IN"),
    IOWA("IOWA", "IA"),
    KANSAS("KANSAS", "KS"),
    KENTUCKY("KENTUCKY", "KY"),
    LOUISIANA("LOUISIANA", "LA"),
    MAINE("MAINE", "ME"),
    MARYLAND("MARYLAND", "MD"),
    MASSACHUSETTS("MASSACHUSETTS", "MA"),
    MICHIGAN("MICHIGAN", "MI"),
    MINNESOTA("MINNESOTA", "MN"),
    MISSISSIPPI("MISSISSIPPI", "MS"),
    MISSOURI("MISSOURI", "MO"),
    MONTANA("MONTANA", "MT"),
    NEBRASKA("NEBRASKA", "NE"),
    NEVADA("NEVADA", "NV"),
    NEWHAMPSHIRE("NEW HAMPSHIRE", "NH"),
    NEWJERSEY("NEW JERSEY", "NJ"),
    NEWMEXICO("NEW MEXICO", "NM"),
    NEWYORK("NEW YORK", "NY"),
    NORTHCAROLINA("NORTH CAROLINA", "NC"),
    NORTHDAKOTA("NORTH DAKOTA", "ND"),
    OHIO("OHIO", "OH"),
    OKLAHOMA("OKLAHOMA", "OK"),
    OREGON("OREGON", "OR"),
    PENNSYLVANIA("PENNSYLVANIA", "PA"),
    RHODEISLAND("RHODE ISLAND", "RI"),
    SOUTHCAROLINA("SOUTH CAROLINA", "SC"),
    SOUTHDAKOTA("SOUTH DAKOTA", "SD"),
    TENNESSEE("TENNESSEE", "TN"),
    TEXAS("TEXAS", "TX"),
    UTAH("UTAH", "UT"),
    VERMONT("VERMONT", "VT"),
    VIRGINIA("VIRGINIA", "VA"),
    WASHINGTON("WASHINGTON", "WA"),
    WESTVIRGINIA("WEST VIRGINIA", "WV"),
    WISCONSIN("WISCONSIN", "WI"),
    WYOMIN("WYOMING", "WY");

    private final String fullName;
    private final String abbreviation;

    private States(String fullName, String abbreviation){
        this.fullName = fullName;
        this.abbreviation = abbreviation;
    }

    public String getFullName(){
        return this.fullName;
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }
}