package com.work.finalproject.publicApi;

import com.work.finalproject.dto.XmlDTO;
import org.springframework.ui.Model;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PublicAPI {

    private static String getTagValue(String tag, Element eElement) {
        try {
            NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
            Node nValue = (Node) nlList.item(0);
            if (nValue == null) {
                return null;
            }
            return nValue.getNodeValue();
        } catch (Exception e) {
            return "";
        }

    }






    // 검색
    public List<XmlDTO> search(String keyword, String contentType, String page) throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/searchKeyword"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=AdNZDr5s3Wzlh%2BB%2FzHMNCVsu8Z7SH6qH1MLVmEDcQ%2Fi7ZNvtm8C1%2F%2FEjAoxzrBRSrC%2BXS8W0m2AOGcP0rzV5xQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("AdNZDr5s3Wzlh%2BB%2FzHMNCVsu8Z7SH6qH1MLVmEDcQ%2Fi7ZNvtm8C1%2F%2FEjAoxzrBRSrC%2BXS8W0m2AOGcP0rzV5xQ%3D%3D", "UTF-8")); /*공공데이터에서 발급받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS (아이폰), AND(안드로이드), ETC*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode(page, "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("listYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*목록 구분 (Y=목록, N=개수)*/
        urlBuilder.append("&" + URLEncoder.encode("arrange","UTF-8") + "=" + URLEncoder.encode("R", "UTF-8")); /*(A=제목순, B=조회순, C=수정일순, D=생성일순) 대표이미지가 반드시 있는 정렬(O=제목순, P=조회순, Q=수정일순, R=생성일순)*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode(contentType, "UTF-8")); /*관광타입(관광지, 숙박 등) ID*/
        urlBuilder.append("&" + URLEncoder.encode("areaCode","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*지역코드*/
        urlBuilder.append("&" + URLEncoder.encode("sigunguCode","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*시군구코드(areaCode 필수)*/
        urlBuilder.append("&" + URLEncoder.encode("cat1","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*대분류 코드*/
        urlBuilder.append("&" + URLEncoder.encode("cat2","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*중분류 코드(cat1필수)*/
        urlBuilder.append("&" + URLEncoder.encode("cat3","UTF-8") + "=" + URLEncoder.encode("", "UTF-8")); /*소분류 코드(cat1,cat2필수)*/
        urlBuilder.append("&" + URLEncoder.encode("keyword","UTF-8") + "=" + URLEncoder.encode(keyword, "UTF-8")); /*검색 요청할 키워드 (국문=인코딩 필요)*/
        URL url = new URL(urlBuilder.toString());
        System.out.println(url);
        String sUrl = url.toString();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        List<XmlDTO> xmlList = new ArrayList<XmlDTO>();
        int totalCount = 0;
        int countList = 10;
        int countPage = 10;
        int page2 = 5;
        try{

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(sUrl);
            doc.getDocumentElement().normalize();
            System.out.println("element : " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("item");
            NodeList nodeList1 = doc.getElementsByTagName("body");
            System.out.println("파싱할 리스트 수 : " + nodeList.getLength());



            for (int temp = 0; temp<nodeList.getLength(); temp++){
                Node nNode = nodeList.item(temp);
                Node node = nodeList1.item(0);
                if(nNode.getNodeType()==Node.ELEMENT_NODE){
                    XmlDTO dto = new XmlDTO();
                    Element element = (Element) nNode;
                    Element element1 = (Element) node;
                    System.out.println("값 : "+getTagValue("totalCount",element1));
                    totalCount = Integer.parseInt(getTagValue("totalCount", element1));

                    dto.setKeyword(getTagValue("title", element));
                    dto.setContent_id(getTagValue("contentid", element));
                    dto.setContentType(getTagValue("contenttypeid", element));
                    dto.setFirstimage2(getTagValue("firstimage2" ,element));
                    dto.setMapx(getTagValue("mapx",element));
                    dto.setMapy(getTagValue("mapy",element));
                    dto.setTel(getTagValue("tel", element));



                    xmlList.add(dto);
                }
            }

        }catch (Exception e){
            System.out.println("xml읽기 오류");
        }


        // 페이지 처리 계산식
        int totalPage = totalCount / countList;

        if(totalCount % countList > 0){
            totalPage++;
        }

        if(totalPage< page2){
            page2 = totalPage;
        }

        int startPage = ((page2 -1) / 10) * 10 + 1;
        int endPage = startPage + countPage - 1;

        if(endPage > totalPage){
            endPage = totalPage;
        }

        for(int iCount = startPage; iCount <= endPage; iCount++){
            System.out.println("아이 카운트 : "+iCount);
        }



        xmlList.get(0).setTotalPage(totalPage);

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        return xmlList;
    }

    public XmlDTO detail(String content_id, String contentType) throws IOException{
        StringBuilder urlBuilder = new StringBuilder("http://api.visitkorea.or.kr/openapi/service/rest/KorService/detailCommon"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=AdNZDr5s3Wzlh%2BB%2FzHMNCVsu8Z7SH6qH1MLVmEDcQ%2Fi7ZNvtm8C1%2F%2FEjAoxzrBRSrC%2BXS8W0m2AOGcP0rzV5xQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("AdNZDr5s3Wzlh%2BB%2FzHMNCVsu8Z7SH6qH1MLVmEDcQ%2Fi7ZNvtm8C1%2F%2FEjAoxzrBRSrC%2BXS8W0m2AOGcP0rzV5xQ%3D%3D", "UTF-8")); /*공공데이터포털에서*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*현재 페이지 번호*/
        urlBuilder.append("&" + URLEncoder.encode("MobileOS","UTF-8") + "=" + URLEncoder.encode("ETC", "UTF-8")); /*IOS(아이폰),AND(안드로이드),WIN(원도우폰),ETC*/
        urlBuilder.append("&" + URLEncoder.encode("MobileApp","UTF-8") + "=" + URLEncoder.encode("AppTest", "UTF-8")); /*서비스명=어플명*/
        urlBuilder.append("&" + URLEncoder.encode("contentId","UTF-8") + "=" + URLEncoder.encode(content_id, "UTF-8")); /*콘텐츠ID*/
        urlBuilder.append("&" + URLEncoder.encode("contentTypeId","UTF-8") + "=" + URLEncoder.encode(contentType, "UTF-8")); /*관광타입(관광지, 숙박 등) ID*/
        urlBuilder.append("&" + URLEncoder.encode("defaultYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*기본정보 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("firstImageYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*원본, 썸네일 대표이미지 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("areacodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*지역코드, 시군구코드 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("catcodeYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*대,중,소분류코드 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("addrinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*주소, 상세주소 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("mapinfoYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*좌표 X,Y 조회여부*/
        urlBuilder.append("&" + URLEncoder.encode("overviewYN","UTF-8") + "=" + URLEncoder.encode("Y", "UTF-8")); /*콘텐츠 개요 조회여부*/
        URL url = new URL(urlBuilder.toString());
        System.out.println(url);
        System.out.println("유알엘!!!!!!!!!!!!!!!!!!" + url);
        String sUrl = url.toString();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;

        XmlDTO xmlDTO = new XmlDTO();
        try{

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(sUrl);
            doc.getDocumentElement().normalize();
            System.out.println("element : " + doc.getDocumentElement().getNodeName());
            NodeList nodeList = doc.getElementsByTagName("item");

            for (int temp = 0; temp<nodeList.getLength(); temp++){
                Node nNode = nodeList.item(temp);
                if(nNode.getNodeType()==Node.ELEMENT_NODE){
                    XmlDTO dto = new XmlDTO();
                    Element element = (Element) nNode;
                    System.out.println("################");

                    xmlDTO.setTitle(getTagValue("title", element));
                    xmlDTO.setContent_id(getTagValue("contentid", element));
                    xmlDTO.setContentType(getTagValue("contenttypeid", element));
                    xmlDTO.setOverview(getTagValue("overview", element));
                    xmlDTO.setMapx(getTagValue("mapx",element));
                    xmlDTO.setMapy(getTagValue("mapy", element));
                    xmlDTO.setTel(getTagValue("tel", element));
                    xmlDTO.setHomepage(getTagValue("homepage", element));

                }
            }

        }catch (Exception e){
            System.out.println("xml읽기 오류");
        }

        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        System.out.println(sb.toString());

        return xmlDTO;
    }




}
