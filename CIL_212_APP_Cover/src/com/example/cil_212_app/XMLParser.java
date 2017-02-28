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

	// 构造方法  
    public XMLParser() {   
    
    }   
    
    /**  
     * 从URL获取XML使HTTP请求 
     * @param url string  
     * */  
    public String getXmlFromUrl(String url) {   
        String xml = null;   
    
        try {   
            // defaultHttpClient   
            DefaultHttpClient httpClient = new DefaultHttpClient();   //新建HttpClient连接..
 
            HttpPost httpPost = new HttpPost(url);   //post方式
    
            HttpResponse httpResponse = httpClient.execute(httpPost);//接收服务器响应信息...   
           
            HttpEntity httpEntity = httpResponse.getEntity();   //获取服务器响应后的实体...
            xml = EntityUtils.toString(httpEntity);       //EntityUtils将响应数据转换成字符串...   
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
     * 获取XML DOM元素 
     * @param XML string  
     * */  
    public Document getDomElement(String xml){   
        Document doc = null;   
        
        //从xml文档中获取DOM的解析器...
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();   
        try {   
    
        	//从DOM工厂中获取DOM解析器...
            DocumentBuilder db = dbf.newDocumentBuilder();   
    
            InputSource is = new InputSource();   //定义一个xml文档的输入源..
                is.setCharacterStream(new StringReader(xml));   //设置输入源的字符流...
                doc = db.parse(is);     //指定输入源的内容解析成一个XML文档，并返回一个DOM对象...
    
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
    
    /** 获取节点值 
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
      * 获取节点值 
      * @param Element node  
      * @param key string  
      * */  
     public String getValue(Element item, String str) {   
            NodeList n = item.getElementsByTagName(str);   
            return this.getElementValue(n.item(0));   
     }   
}
