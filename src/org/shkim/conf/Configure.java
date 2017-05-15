package org.shkim.conf;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;

public class Configure
{
	private static final String		ROOT_DIR_PATH	= System.getProperty("user.dir");
	private static final String		CONF_DIR_PATH	= ROOT_DIR_PATH + "/conf";

	private static final File		CONF_DIR		= new File(CONF_DIR_PATH);
	private static final File		CONF_FILE		= new File(CONF_DIR, "conf.properties");

	private static final Properties	CONF			= conf();

	private static Properties conf()
	{
		Properties conf = new Properties();
		try
		{
			InputStreamReader isr = new InputStreamReader(new FileInputStream(CONF_FILE), "utf-8");
			conf.load(isr);
			isr.close();
		} catch (Exception e)
		{
			System.err.println(e);
		}
		return conf;
	}

	public static final String	SERVER_IP	= CONF.getProperty("server.address");
	public static final int		SERVER_PORT	= Integer.parseInt(CONF.getProperty("server.port"));
	public static final int		MAX_USER	= Integer.parseInt(CONF.getProperty("chat.max.cnt"));

}
