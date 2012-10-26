package MapReducematrix.matrix;

import java.rmi.server.UID;

public class Config {
	//hdfs config
	public static final String USERINFO="hadoop,hadoop"; ////设置hadoop server用户名和密码
	public static final String UGI="hadoop.job.ugi";
	public static final String STORAGEPATH="hdfs://202.201.1.49:9000/user/hadoop/";
	//database config
	public final static String URL = "jdbc:mysql://202.201.14.27:3306/hdfsUser";
	public final static String USER = "hadoop";
	public final static String PASSWORD = "hadoop";
	public final static String SQL="select path from usrsTable where id=";
	public final static String PATH="path";
}
