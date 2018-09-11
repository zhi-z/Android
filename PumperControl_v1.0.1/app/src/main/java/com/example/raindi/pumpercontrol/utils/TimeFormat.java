package com.example.raindi.pumpercontrol.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

@SuppressLint("SimpleDateFormat")
public class TimeFormat {
	private static SimpleDateFormat Format = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
	private static SimpleDateFormat tFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static SimpleDateFormat TZFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmss'Z'");
	private static SimpleDateFormat dayFormat = new SimpleDateFormat("yyyy年MM月dd日");
	private static SimpleDateFormat secFormat = new SimpleDateFormat("HH时mm分ss秒");
	private static SimpleDateFormat hourFormat = new SimpleDateFormat("HH:mm");
	private static SimpleDateFormat FormatYMD = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat FormatMDHM = new SimpleDateFormat("MM-dd HH:mm");
	private static Calendar cal = Calendar.getInstance();
	private static Date date = new Date();

	public static boolean isTimeInInterval(Date prevTime,int timeout){

		if(prevTime == null){
			return false;
		}

		Date nowDate = new Date();

		long diff = nowDate.getTime() - prevTime.getTime();

		if(diff < timeout){
			return true;
		}

		return false;
	}

	public static final String getTime(long time){
		date.setTime(time);
		return Format.format(date);
	}

	public static final String getTime(){
		date.setTime(System.currentTimeMillis());
		return tFormat.format(date);
	}

	public static final String getDay(){
		date.setTime(System.currentTimeMillis());
		return dayFormat.format(date);
	}

	public static final String getSec(){
		date.setTime(System.currentTimeMillis());
		return secFormat.format(date);
	}

	public static final String getHour(){
		date.setTime(System.currentTimeMillis());
		return hourFormat.format(date);
	}

	public static final String getWeekofDay(){
		String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
		date.setTime(System.currentTimeMillis());
		cal.setTime(date);
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0){
			w = 0;
		}
		return weekDays[w];
	}

	public static final String DateToString(Date mDate){
		return dayFormat.format(mDate);
	}

	public static final String DateToString1(Date mDate){
		return tFormat.format(mDate);
	}

	public static final String MDHMToString1(long time){
		return FormatMDHM.format(time);
	}

	public static final Date StringToDate(String dstr){
		try {
			return Format.parse(dstr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static Date stringToDate1(String strTime)
			throws ParseException {
		Date date = null;
		date = FormatYMD.parse(strTime);
		return date;
	}

	public static Date UTCToDate(String date){
		try {
			TZFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			return TZFormat.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String timeToGMT(String time){
		try {
			DateFormat df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.CHINA);
			Date date = df2.parse(time);
			return df.format(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
