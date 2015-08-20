package com.dhcc.mobile;
/**
 * 通用接口
 */
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

public class WsApiUtil {
	static final String SERVICE_NS = "http://tempuri.org";

	// 定义Web Service的命名空间
	public static void main(String[] sar) {
		//loadData();
	}

	
	/**
	 * 
	 * 接收服务器返回的SoapObject数据
	 * @param serviceUrl
	 * @param methodName
	 * @param requestCode
	 * @param requestXml
	 * @return
	 * @throws Exception
	 */
	public static String loadSoapObject(String serviceUrl,String methodName, Map Parrm) throws Exception {
		String retData = null ;
		
		// 创建soapObject对象
		SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);
		// 设置参数
			
		Iterator iter = Parrm.entrySet().iterator() ;//先获取这个map的set序列，再或者这个序列的迭代器
	    while(iter.hasNext()){
	    Map.Entry entry = (Map.Entry)iter.next(); //得到这个序列的映射项，就是set中的类型，HashMap都是Map.Entry类型（详情见map接口声明）
	     //Integer key = (Integer)entry.getKey(); //获得key
	     soapObject.addProperty(entry.getKey().toString(), entry.getValue());
	   
	   } 

		// 创建SoapSerializationEnvelope对象，并设置WebService版本号
		SoapSerializationEnvelope serializationEnvelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		// 设置serializationEnvelope对象的badyOut属性
		serializationEnvelope.bodyOut = soapObject;
		// 创建HttpTransportSE对象,并且确定wsdl网络地址
		HttpTransportSE httpTransportSE = new HttpTransportSE(serviceUrl);//
		
		try {
			// httpTransportSE调用Call方法
			httpTransportSE.call(SERVICE_NS + "/" + methodName,
					serializationEnvelope);
			// 获取返回的结果对象
			if (serializationEnvelope.getResponse() != null) {
				SoapObject result = (SoapObject) serializationEnvelope.bodyIn;
				Object obj =  result.getProperty(methodName+ "Result");
				
				//obj: <Response><ResultCode>0</ResultCode><ResultDesc></ResultDesc><ResultList><Patinfo><name>张三</name><sex>男</sex><age>20</age></Patinfo><Patinfo><name>李四</name><sex>女</sex><age>22</age></Patinfo></ResultList></Response>
				
				retData = obj.toString();
				
			}
			
		} catch (IOException e) {
			throw e;
		} catch (XmlPullParserException e2) {
			throw e2;
		}catch (Exception e3) {
			throw e3;
		}

		return retData ;
	}
	

	
}
