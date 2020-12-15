//package old.files;
//
//import com.google.gson.Gson;
//import old.files.GoogleResults;
//
//import java.io.InputStreamReader;
//import java.io.Reader;
//import java.net.URL;
//import java.net.URLEncoder;
//
//public class Main {
//    public static void main(String[] args) throws Exception {
//        String google = "https://www.google.com/search?newwindow=1&safe=off&sxsrf=ALeKk03_hhAH70jc15HW-x7TmOsgujRfmQ%3A1607934331533&source=hp&ei=eyHXX96lHZKSlwSrhoywAQ&q=stack&oq=stack&gs_lcp=CgZwc3ktYWIQAzIECCMQJzIECCMQJzIECAAQQzIFCAAQsQMyBwgAELEDEEMyBwgAEBQQhwIyBAgAEEMyBAgAEEMyAggAMgIIADoFCAAQywE6BggjECcQEzoLCAAQsQMQxwEQowI6CggAELEDEIMBEEM6BAguEENQ-wpY_SZggShoAHAAeACAAXeIAcAEkgEDNC4ymAEAoAECoAEBqgEHZ3dzLXdpeg&sclient=psy-ab&ved=0ahUKEwiez5mVhs3tAhUSyYUKHSsDAxYQ4dUDCAc&uact=5";
//        String search = "stackoverflow";
//        String charset = "UTF-8";
//
//        URL url = new URL(google + URLEncoder.encode(search, charset));
//        Reader reader = new InputStreamReader(url.openStream(), charset);
//        GoogleResults results = new Gson().fromJson(reader, GoogleResults.class);
//
//        // Show title and URL of 1st result.
//        System.out.println(results.getResponseData().getResults().get(0).getTitle());
//        System.out.println(results.getResponseData().getResults().get(0).getUrl());
//    }
//}
