package repository.AppStore;

import java.util.List;
import java.sql.Date;
import java.util.Calendar;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppDB appdb = new AppDB();
		List<String> list;
		List<App> list2;
		// list=appdb.findTag(1);
		// App a=appdb.findById(1);

		// list2=appdb.getAppfromtag(1, "ss");
		// list=appdb.getCate();
		// list2=appdb.getAppfromCate(1, 1);
		// Calendar cal=Calendar.getInstance();

		java.sql.Date sd;
		java.util.Date ud;
		// initialize the ud such as ud = new java.util.Date();

		sd = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		// App app=new App(5,"user1","tools","yes",sd);
		// appdb.save(app,1);

	}

}
