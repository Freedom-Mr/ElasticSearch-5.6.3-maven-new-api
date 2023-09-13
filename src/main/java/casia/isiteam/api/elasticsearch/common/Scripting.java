package casia.isiteam.api.elasticsearch.common;

public class Scripting {
	public static String facetime = "if(!doc['%s'].empty){Date date = new Date(doc['%s'].value);SimpleDateFormat format = new SimpleDateFormat('%s');format.setTimeZone(TimeZone.getTimeZone('GMT'));format.format(date)}" ;
	public static String inline = "ctx._source['%s']=%s";
}
