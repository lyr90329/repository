/**
 * 
 */
package servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;
import cn.org.act.sdp.bpmnRepository.entity.BpmnBean;
import cn.org.act.sdp.bpmnRepository.tool.BpmnTool;
import db.entity.Bpmn;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import config.Config;



import db.dao.BpmnDao;
import db.entity.Bpmn;
/**
 * @author dell
 * 
 */
public class BpmnToolTest extends TestCase {
	BpmnTool tool;

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		tool = new BpmnTool();
		super.setUp();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testGetByID(){
    	BpmnBean bean=tool.getBpmnByID(34);
    	FileOutputStream fos = null;
		InputStream is = null;
		File file = null;
    	try {
			file = new File("data/output.bpmn_diagram");
			fos = new FileOutputStream(file);
			is = bean.getDiagramStream();
			int size;

			if (!file.exists()) {
				file.createNewFile(); // 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷冢锟斤拷虼唇锟??
			}
            if(is==null);
//            	continue;
			byte[] Buffer = new byte[4096];
			while ((size = is.read(Buffer)) != -1) {
				// System.out.println(size);
				fos.write(Buffer, 0, size);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 锟截憋拷锟矫碉拷锟斤拷锟斤拷源
			try {
				if (is != null)
					is.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		try {
			file = new File("data/output.bpmn");
			fos = new FileOutputStream(file);
			is = bean.getBpmnStream();
			int size;

			if (!file.exists()) {
				file.createNewFile(); // 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷冢锟斤拷虼唇锟??
			}

			byte[] Buffer = new byte[4096];
			while ((size = is.read(Buffer)) != -1) {
				// System.out.println(size);
				fos.write(Buffer, 0, size);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 锟截憋拷锟矫碉拷锟斤拷锟斤拷源
			try {
				if (is != null)
					is.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	/**
	 * Test method for
	 * {@link cn.org.act.sdp.bpmnRepository.tool.BpmnTool#getBpmnByName(java.lang.String)}.
	 */
//	public void testGetBpmnByName() {
//		ArrayList<BpmnBean> bpmns = tool.getBpmnByName("service1");
//		FileOutputStream fos = null;
//		InputStream is = null;
//		File file = null;
//		int i = 0;
//		for (Iterator iter = bpmns.iterator(); iter.hasNext();) {
//			i++;
//			BpmnBean element = (BpmnBean) iter.next();
//			try {
//				file = new File("data/output" + i + ".bpmn_diagram");
//				fos = new FileOutputStream(file);
//				is = element.getDiagramStream();
//				int size;
//
//				if (!file.exists()) {
//					file.createNewFile(); // 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷冢锟斤拷虼唇锟??
//				}
//                if(is==null)
//                	continue;
//				byte[] Buffer = new byte[4096];
//				while ((size = is.read(Buffer)) != -1) {
//					// System.out.println(size);
//					fos.write(Buffer, 0, size);
//				}
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				// 锟截憋拷锟矫碉拷锟斤拷锟斤拷源
//				try {
//					if (is != null)
//						is.close();
//					if (fos != null)
//						fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//			try {
//				file = new File("data/service" + i + ".bpmn");
//				fos = new FileOutputStream(file);
//				is = element.getBpmnStream();
//				int size;
//
//				if (!file.exists()) {
//					file.createNewFile(); // 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷冢锟斤拷虼唇锟??
//				}
//
//				byte[] Buffer = new byte[4096];
//				while ((size = is.read(Buffer)) != -1) {
//					// System.out.println(size);
//					fos.write(Buffer, 0, size);
//				}
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				// 锟截憋拷锟矫碉拷锟斤拷锟斤拷源
//				try {
//					if (is != null)
//						is.close();
//					if (fos != null)
//						fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//
//		fail("Not yet implemented");
//	}
	
	public void GetBpmnByName(Bpmn trial) {
		String bpmnName=trial.getBpmnName();
//		bpmnName+=".bpmn";
//		bpmnName="bpmn/"+bpmnName;
		
		ArrayList<BpmnBean> bpmns= new ArrayList() ;
		bpmns.add(0,tool.getBpmnByID(trial.getBpmnId()));
		
		FileOutputStream fos = null;
		InputStream is = null;
		File file = null;
		int i = 0;
		for (Iterator iter = bpmns.iterator(); iter.hasNext();) {
			i++;
			BpmnBean element = (BpmnBean) iter.next();
//			try {
//				file = new File("data/output" + i + ".bpmn_diagram");
//				fos = new FileOutputStream(file);
//				is = element.getDiagramStream();
//				int size;
//
//				if (!file.exists()) {
//					file.createNewFile(); // 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷冢锟斤拷虼唇锟??
//				}
//                if(is==null)
//                	continue;
//				byte[] Buffer = new byte[4096];
//				while ((size = is.read(Buffer)) != -1) {
//					// System.out.println(size);
//					fos.write(Buffer, 0, size);
//				}
//			} catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} finally {
//				// 锟截憋拷锟矫碉拷锟斤拷锟斤拷源
//				try {
//					if (is != null)
//						is.close();
//					if (fos != null)
//						fos.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
			try {
				file = new File("bpmn/" + bpmnName + ".bpmn");
				System.out.println("bpmn/" + bpmnName + ".bpmn");
//				file = new File("data/service" + i + ".bpmn");
				fos = new FileOutputStream(file);
				is = element.getBpmnStream();
				int size;

				if (!file.exists()) {
					file.createNewFile(); // 锟斤拷锟斤拷募锟斤拷锟斤拷锟斤拷冢锟斤拷虼唇锟??
				}

				byte[] Buffer = new byte[4096];
				while ((size = is.read(Buffer)) != -1) {
					// System.out.println(size);
					fos.write(Buffer, 0, size);
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				// 锟截憋拷锟矫碉拷锟斤拷锟斤拷源
				try {
					if (is != null)
						is.close();
					if (fos != null)
						fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		fail("Not yet implemented");
	}
}
