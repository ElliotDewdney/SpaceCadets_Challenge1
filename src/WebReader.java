import com.sun.org.apache.xerces.internal.impl.xpath.regex.RegularExpression;
import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class WebReader {

    static class PageReader{
        private String emailID;
        private String pageData;
        PageReader(String emailID) throws IOException {
            this.emailID = emailID;
            URL url = new URL("https://www.ecs.soton.ac.uk/people/dem" + emailID);
            InputStream inputStream = url.openStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            // not the standard way to do this
            class pageDetails{
                String pageString = "";
                void add(String data){
                    pageString += "\n"+data;
                }
                String getData(){
                    return pageString;
                }
            }
            final pageDetails pageData = new pageDetails();
            bufferedReader.lines().forEach(pageData::add);
            this.pageData = pageData.getData();
        }

        public String GetNameData(){
            return pageData.split("<title>")[1].split("\\|")[0];
        }

        public String toString(){
            return pageData;
        }
    }


    public static void main(String args[]){
        System.out.print("Enter the EmailID : ");
        String emailID = new Scanner(System.in).nextLine();
        try {
            System.out.println("The name of the person is : " + new PageReader(emailID).GetNameData());
        }catch(IOException e){
            System.out.println("Unable to load Web-page");
        }
    }
}
