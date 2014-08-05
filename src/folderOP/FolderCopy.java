package folderOP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FolderCopy {
	public static void copy(File[] s, File d) {
		if (!d.exists())                             // 如果文件夹不存在
		d.mkdir();                            // 建立新的文件夹
		for (int i = 0; i < s.length; i++) {
		if (s[i].isFile()) {                   // 如果是文件类型就复制文件
		try {
		FileInputStream fis = new FileInputStream(s[i]);
		FileOutputStream out = new FileOutputStream(new File(d
		.getPath()
		+ File.separator + s[i].getName()));
		int count = fis.available();
		byte[] data = new byte[count];
		if ((fis.read(data)) != -1) {
		out.write(data);          // 复制文件内容
		}
		out.close();                   // 关闭输出流
		fis.close();                    // 关闭输入流
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		if (s[i].isDirectory()) {                        // 如果是文件夹类型
		File des = new File(d.getPath() + File.separator
		+ s[i].getName());
		des.mkdir();                                // 在目标文件夹中创建相同的文件夹
		copy(s[i].listFiles(), des);                     // 递归调用方法本身
		}
		}
		}

	public static void main(String[] args) {
		File sourFile = null, desFile = null;
//		String sourFolder = "./sourceFolder";              //可以修改源文件夹路径
//		String desFolder = "./desFolder";                        //可以修改目标文件夹路径
		String sourFolder="f:/1";
		String desFolder ="e:/2"; 
		
		sourFile = new File(sourFolder);
		if (!sourFile.isDirectory() || !sourFile.exists()) {
			System.out.println("源文件夹不存在");
		}
		desFile = new File(desFolder);
		desFile.mkdir();
		copy(sourFile.listFiles(), desFile);                    //调用copy()方法

	}

}
