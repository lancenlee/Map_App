package com.example.cil_212_app;


import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import android.util.Log;

public class XMLParser {

	// ���췽��  
    public XMLParser() {   
    
    }   
    
    /**  
     * ��URL��ȡXMLʹHTTP���� 
     * @param url string  
     * */  
    public String getXmlFromUrl(String url) {   
        String xml = null;   
    
        try {   
            // defaultHttpClient   
            DefaultHttpClient httpClient = new DefaultHttpClient();   //�½�HttpClient����..
 
            HttpPost httpPost = new HttpPost(url);   //post��ʽ
    
            HttpResponse httpResponse = httpClient.execute(httpPost);//���շ�������Ӧ��Ϣ...   
           
            HttpEntity httpEntity = httpResponse.getEntity();   //��ȡ��������Ӧ���ʵ��...
            xml = EntityUtils.toString(httpEntity);       //EntityUtils����Ӧ����ת�����ַ���...   
        } catch (UnsupportedEncodingException e) {   
            e.printStackTrace();   
        } catch (ClientProtocolException e) {   
            e.printStackTrace();   
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        return xml;   
    }   
    
    /**  
     * ��ȡXML DOMԪ�� 
     * @param XML string  
     * */  
    public Document getDomElement(String xml){   
        Document doc = null;   
        
        //��xml�ĵ��л�ȡDOM�Ľ�����...
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   
        try {   
    
        	//��DOM�����л�ȡDOM������...
            DocumentBuilder db = dbf.newDocumentBuilder();   
    
            InputSource is = new InputSource();   //����һ��xml�ĵ�������Դ..
                is.setCharacterStream(new StringReader(xml));   //��������Դ���ַ���...
                doc = db.parse(is);     //ָ������Դ�����ݽ�����һ��XML�ĵ���������һ��DOM����...
    
            } catch (ParserConfigurationException e) {   
                Log.e("Error: ", e.getMessage());   
                return null;   
            } catch (SAXException e) {   
                Log.e("Error: ", e.getMessage());   
                return null;   
            } catch (IOException e) {   
                Log.e("Error: ", e.getMessage());   
                return null;   
            }   
    
            return doc;   
    }   
    
    /** ��ȡ�ڵ�ֵ 
      * @param elem element  
      */  
     public final String getElementValue( Node elem ) {   
         Node child;   
         if( elem != null){   
             if (elem.hasChildNodes()){   
                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){   
                     if( child.getNodeType() == Node.TEXT_NODE  ){   
                         return child.getNodeValue();   
                     }   
                 }   
             }   
         }   
         return "";   
     }   
    
     /**  
      * ��ȡ�ڵ�ֵ 
      * @param Element node  
      * @param key string  
      * */  
     public String getValue(Element item, String str) {   
            NodeList n = item.getElementsByTagName(str);   
            return this.getElementValue(n.item(0));   
     }   
}
