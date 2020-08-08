package neu.s.dey.connecteddevices.project;

// This class is used to retrieve Sunset Sunrise API data using GSON
public class SunriseSunsetData {
    public Results results;
    public String status;
 
    public class Results {
        public String sunrise;
        public String sunset;
        public String solar_noon;
        public String day_length;
        public String civil_twilight_begin;
        public String civil_twilight_end;
        public String nautical_twilight_begin;
        public String nautical_twilight_end;
        public String astronomical_twilight_begin;
        public String astronomical_twilight_end;
    }

}
