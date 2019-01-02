package com.ixeron.vex;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VexIQDataParser {

    public String getRESTApiResponse(String apiUrl) {
        String inline = "";
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            int responsecode = conn.getResponseCode();
            if(responsecode != 200)
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            else
            {
                Scanner sc = new Scanner(url.openStream());
                while(sc.hasNext())
                {
                    inline+=sc.nextLine();
                }
                //System.out.println(inline);
                sc.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inline;
    }
    public List<SkillRank> fetchSkillRanks(String skillsRankurl){
        /**
         * skillsRankurl https://www.robotevents.com/api/seasons/124/skills?post_season=0&grade_level=Middle%20School
         * or https://www.robotevents.com/api/seasons/124/skills?post_season=0&grade_level=Elementary
         */
        String json = getRESTApiResponse(skillsRankurl);
        JsonParser jsonParser = new JsonParser();
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
        //TypeDTO[] myTypes = gson.fromJson(new FileReader("input.json"), TypeDTO[].class);

        SkillRank[] skillRanks = gson.fromJson(json, SkillRank[].class);
        return Arrays.asList(skillRanks);
    }
    public SkillRank findSkillRank(String teamId, List<SkillRank> skillRanks){
        for (SkillRank skillRank : skillRanks){
            if (skillRank.getTeam().getTeam().equals(teamId))
                return skillRank;
        }
        return null;
    }
    public EventAwardsList fetchTeamAwards(String teamProfileUrl){
        EventAwardsList eventAwardsList = null;
        try {
            final WebClient webClient = new WebClient(BrowserVersion.CHROME);
            final HtmlPage teamProfilePage = webClient.getPage(teamProfileUrl);

            String htmlSource = teamProfilePage.getWebResponse().getContentAsString();
            Pattern pattern = Pattern.compile(":team=\"[0-9]+\"");
            Matcher matcher = pattern.matcher(htmlSource);
            String teamApiNumber = "";
            if (matcher.find()) {
                String rawMatch = matcher.group();
                Pattern teamNumPattern = Pattern.compile("[0-9]+");
                matcher = teamNumPattern.matcher(rawMatch);
                if(matcher.find())
                    teamApiNumber = matcher.group();
            }
            String teamAwardsApiUrlTpl = "https://www.robotevents.com/api/teams/%s/awards?page=1";
            String teamAwardsApiUrl = String.format(teamAwardsApiUrlTpl, teamApiNumber);

            String json = getRESTApiResponse(teamAwardsApiUrl);
            JsonParser jsonParser = new JsonParser();
            final GsonBuilder builder = new GsonBuilder();
            final Gson gson = builder.create();
            eventAwardsList = gson.fromJson(json, EventAwardsList.class);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return eventAwardsList;
    }
    public List<EventTeam> fetchTeamListFromEvent(final String eventName){
        List<EventTeam> eventTeams = new ArrayList<EventTeam>();
        try {
            final WebClient webClient = new WebClient();
            final HtmlPage eventPage = webClient.getPage(String.format("https://www.robotevents.com/%s.html", eventName));
            //final HtmlLink teamlistLink = eventPage.getFirstByXPath("//a[@href='#teamList']");
            final HtmlElement tableDiv = eventPage.getHtmlElementById("teamList");
            final HtmlTable table = (HtmlTable) tableDiv.getElementsByAttribute("table", "id", "data-table").get(0);
            for (final HtmlTableRow row : table.getRows()) {
                if (row.getAttribute("class").equals("row_headlines")) continue;
                int colCount = 0;
                String[] cols = new String[5];
                for (final HtmlTableCell cell : row.getCells()) {
                    String colValue = "";
                    DomNode colObj = cell.getFirstChild();
                    if (colCount == 0){
                        // team name to be processed differently than other columns
                        HtmlAnchor anchor = (HtmlAnchor)colObj.getNextSibling();
                        colValue = anchor.getHrefAttribute();
                        cols[4] = colValue;
                        colValue = anchor.getTextContent();
                    }else {
                        try{
                            colValue = colObj.getTextContent();
                        }catch (Exception ex){

                        }
                    }
                    cols[colCount] = colValue;
                    colCount ++;
                }
                eventTeams.add(new EventTeam(cols[0].trim(), cols[1].trim(), cols[2].trim(), cols[3], cols[4].trim()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return eventTeams;
    }

    public void printTeamlistFromEvent(String eventName){
        VexIQDataParser parser = new VexIQDataParser();
        List<EventTeam> teams = parser.fetchTeamListFromEvent(eventName);

        for (EventTeam team : teams)
        {
            System.out.println(team.toString());
        }
    }
    public static void main(String[] args) {
        VexIQDataParser parser = new VexIQDataParser();
        // sample code to get awards of a team
        //String teamProfileUrl = "https://www.robotevents.com/teams/VIQC/94568C";

        //parser.fetchTeamAwards(teamProfileUrl);

        // sample code to fetch team list from an event
        HashMap<String, String> eventMap = new HashMap<String, String>();
        eventMap.put("RE-VIQC-18-7370", "Hollister_Jan_12");
        eventMap.put("RE-VIQC-18-7716", "San_Jose_final_Mar_03");

        List<SkillRank> skillRanks = parser.fetchSkillRanks("https://www.robotevents.com/api/seasons/124/skills?post_season=0&grade_level=Middle%20School");
        List<SkillRank> elementarySkillRanks = parser.fetchSkillRanks("https://www.robotevents.com/api/seasons/124/skills?post_season=0&grade_level=Elementary");


        //parser.printTeamlistFromEvent(eventName);
        for (String eventName : eventMap.keySet()){
            String fileSuffix = eventMap.get(eventName);
            List<EventTeam> teams = parser.fetchTeamListFromEvent(eventName);
            String teamProfileUrlTpl = "https://www.robotevents.com/teams/VIQC/%s";
            String output = "";
            for (EventTeam team : teams){
                SkillRank skillRank = parser.findSkillRank(team.getId(), skillRanks);
                if (skillRank == null){
                    skillRank = parser.findSkillRank(team.getId(), elementarySkillRanks);
                }
                String scores = (skillRank == null)? "":skillRank.getScores().toString();
                String line = String.format("%s: %s", team.getName(), team.getId());
                if (skillRank != null){
                    line = String.format("%s: World rank: %s, %s", line, skillRank.getRank(), skillRank.getScores().toString());
                }
                line += "\n";

                System.out.println(line);
                output += line;
                EventAwardsList eventAwardsList = parser.fetchTeamAwards(String.format(teamProfileUrlTpl, team.getId()));
                if (eventAwardsList != null){
                    output += formatTeamAwards(eventAwardsList);
                }else{
                }
            }
            try {
                Files.write(output, new File(String.format("/Users/annon/Downloads/VEX_IQ_%s_teams.txt", fileSuffix)), Charset.defaultCharset());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static String formatTeamAwards(EventAwardsList eventAwardsList){
        String output = "";
        if (eventAwardsList != null){
            for(EventAwards eventAwards : eventAwardsList.events){
                if (eventAwards.getEndAt() < 1525942221000L) continue;
                String line = String.format("\t%s\n", eventAwards.getName());
                System.out.println(line);
                output += line;
                for (EventAward eventAward : eventAwards.awards){
                    line = String.format("\t\t%s\n", eventAward.getName());
                    System.out.println(line);
                    output += line;
                }
            }
        }
        return output;
    }
    private static void loginAndGetEventTeamList(){
        try {
            final WebClient webClient = new WebClient();
            final HtmlPage page1 = webClient.getPage("https://www.robotevents.com/auth/login");
            final HtmlForm form = page1.getFirstByXPath("//form[@class='form-horizontal']");
                    //page1.getFormByName("form-horizontal");

            final HtmlButton button = form.getFirstByXPath("//button[@id='login-button']");
            final HtmlEmailInput textField = form.getInputByName("email");
            textField.setValueAttribute("annonhong@gmail.com");
            final HtmlPasswordInput textField2 = form.getInputByName("password");
            textField2.setValueAttribute("248303Ab");
            final HtmlPage accountPage = button.click();
            final HtmlPage eventPage = webClient.getPage(String.format("https://www.robotevents.com/%s.html", "RE-VIQC-18-7370"));
            //final HtmlLink teamlistLink = eventPage.getFirstByXPath("//a[@href='#teamList']");
            final HtmlElement tableDiv = eventPage.getHtmlElementById("teamList");
            final HtmlTable table = (HtmlTable) tableDiv.getElementsByAttribute("table", "id", "data-table").get(0);
            List<EventTeam> eventTeams = new ArrayList<EventTeam>();
            for (final HtmlTableRow row : table.getRows()) {
                if (row.getAttribute("class").equals("row_headlines")) continue;
                int colCount = 0;
                String[] cols = new String[5];
                for (final HtmlTableCell cell : row.getCells()) {
                    String colValue = "";
                    DomNode colObj = cell.getFirstChild();
                    if (colCount == 0){
                        // team name to be processed differently than other columns
                        HtmlAnchor anchor = (HtmlAnchor)colObj.getNextSibling();
                        colValue = anchor.getHrefAttribute();
                        cols[4] = colValue;
                        colValue = anchor.getTextContent();
                    }else {
                        try{
                        colValue = colObj.getTextContent();
                        }catch (Exception ex){

                        }
                    }
                    cols[colCount] = colValue;
                    colCount ++;
                    System.out.println("   Found cell: " + ((colValue == null)? "":colValue) );
                }
                eventTeams.add(new EventTeam(cols[0].trim(), cols[1].trim(), cols[2].trim(), cols[3], cols[4].trim()));
            }

            for (EventTeam team : eventTeams)
            {
                System.out.println(team.toString());
            }
            //teamlistLink.click();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
