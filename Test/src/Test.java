import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm");
		Date startTime = sdf.parse("2015/07/08 01:00");
		Date endTime = sdf.parse("2015/07/08 07:21");
		int elapsedTime = (int)DateUtils.getMinuteForLifeLog(startTime, endTime);
		System.out.println(elapsedTime);
		System.out.println(date00ByDate(endTime, 0));
		System.out.println(date00ByDate(endTime, 1));
		
	    String myIP = InetAddress.getLocalHost().getHostAddress();
	    System.out.println("local host address: " + myIP);
	    NetworkInterface myNetwork = NetworkInterface.getByInetAddress(InetAddress.getLocalHost());
	    if (myNetwork == null) {
        	System.out.println("NetworkInterface(NIC)の取得に失敗しました。");
        }
	      // 複数NICが割り当てられる可能性がある
	      for (InterfaceAddress address : myNetwork.getInterfaceAddresses()) {
	    	System.out.println("InterfaceAddress:" + address.toString());
	        if (address == null || address.getAddress() == null)
	          System.out.println("address is null");
	           //continue;
	        if (!myIP.equals(address.getAddress().getHostAddress())) {
	          System.out.println("ip address:" + address.getAddress().getHostAddress());
	          //continue;
	        } else {
	          String myBroadcast = address.getBroadcast().getHostAddress();
	          System.out.println("myBroadcast:" + myBroadcast.toString());
	          //break;
	        }
	      }
	      
	      String str = "2015-09-10 19:41:45	131172	06	119189	00	3	us10000000007713	2014-05-26 21:30:00.0	2014-05-26 21:44:00.0	2015-09-10 19:40:43.000	2015-09-10 19:41:00.113	2015-09-10 19:41:45.320	ENDED	spawn:139480,139481	null";
	      String[] array = str.split("\t");
	      for (String data : array) {
	    	  System.out.println(data);
	      }
	      
	      Map<String, String> mapA = new LinkedHashMap<String, String>();
	      mapA.put("key1", "1");
	      mapA.put("key3", "3");
	      mapA.put("key4", "4");
	      mapA.put("key5", "5");
	      
	      Map<String, String> mapB = new LinkedHashMap<String, String>();
	      mapA.put("key1", "11");
	      mapA.put("key2", "22");
	      mapA.put("key3", "33");
	      mapA.put("key6", "66");
	      
	      mapA.putAll(mapB);
	      List<Entry<String, String>> entries = new ArrayList<Entry<String, String>>(mapA.entrySet());
	      Collections.sort(entries, 
	    		  new Comparator<Entry<String, String>>() {
			      	  @Override
			  		  public int compare(Entry<String, String> entry1, Entry<String,String> entry2) {
			              return (entry1.getValue()).compareTo(entry2.getValue());
			  		  }
	    		  });
	      
	      for (Entry<String, String> entry : entries) {
	    	  System.out.println(entry.getKey() + ":" + entry.getValue());
	      }
	      /*
	      for (Iterator<Entry<String,String>> it = mapA.entrySet().iterator(); it.hasNext();) {
	    	  Entry<String, String> entry = it.next();
    	      Object key = entry.getKey();
    	      Object value = entry.getValue();
    	      System.out.println(key + ":" + value);
	      }
	      */
	}
	
    /**
     * ���Ԃ�Minute�����擾����B
     * @param start
     * @param end
     * @return ���Ԃ�Minute��
     */
    public static long getMinuteForLifeLog(Date start, Date end) {

        long diff = end.getTime() - start.getTime();
        return diff / (60 * 1000) + 1;
    }

    /**
     * 年月日 00:00:00
     * @param date
     * @param day
     * @return
     */
    private static Date date00ByDate(Date date, int day) {

        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
        String temp = dateformat.format(date);
        Date dateTemp = DateUtils.praseString2Date(temp);
        if (day > 0) {
            Calendar c = Calendar.getInstance();
            c.setTime(dateTemp);
            int days = c.get(Calendar.DATE);
            c.set(Calendar.DATE, days + day);
            dateTemp = c.getTime();
        }

        return dateTemp;

    }

}
