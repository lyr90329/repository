/**
 * Service4All: A Service-oriented Cloud Platform for All about Software
 * Development Copyright (C) Institute of Advanced Computing Technology, Beihang
 * University Contact: service4all@act.buaa.edu.cn
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 3.0 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package monitor.util;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import monitor.bean.AppInfo;
import monitor.bean.DiskInfo;
import monitor.bean.HostInfo;
import monitor.bean.MonitorInfo;
import monitor.bean.StatisticInfo;
import monitor.bean.TomcatInfo;
import monitor.bean.UserInfo;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axiom.om.impl.builder.StAXOMBuilder;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.rpc.client.RPCServiceClient;

import config.Config;

/**
 * Get message from MonitorCenterService,
 * parse message
 * 
 * @author Zhao Tao
 * 
 */
public class DocParser {
	
	private int failueTime=0;
	
	public int getFailueTime(){
		return failueTime;
	}
	/**
	 * Get message from MonitorCenterService
	 * @param method SOAP method name
	 * @param username null for administrator-oriented message
	 * @return
	 */
	public OMElement getDoc(String method,String username) {
		OMElement doc = null;
		OMFactory fac = OMAbstractFactory.getOMFactory();
		OMNamespace omNs = fac.createOMNamespace(
				"http://service.monitorcenter.service4all.act.buaa.edu.cn", "");
		OMElement saMsg = fac.createOMElement(method, omNs);
		OMElement userOM=fac.createOMElement("user",omNs);
		userOM.setText(username);
		saMsg.addChild(userOM);
		try {
			RPCServiceClient serviceClient = new RPCServiceClient();
			Options options = new Options();
			options.setTo(new EndpointReference(Config.getSendDocUrl()));
			serviceClient.setOptions(options);
			doc = serviceClient.sendReceive(saMsg);
		} catch (Exception e) {
			failueTime++;
			return null;
		}
		return doc;
	}
	/**
	 * parse administrator-oriented message
	 * @param doc administrator-oriented message
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public MonitorInfo parse(OMElement doc){
		if (doc == null)
			return null;
		MonitorInfo result = new MonitorInfo();
		List<HostInfo> list = new ArrayList<HostInfo>();
		OMElement monitorInfoOM = doc.getFirstElement().getFirstElement();
		if (!monitorInfoOM.getChildren().hasNext())
			return null;
		for (Iterator<OMElement> it = monitorInfoOM.getChildElements(); it
				.hasNext();) {
			HostInfo hostInfo = new HostInfo();
			List<TomcatInfo> tomcat = new ArrayList<TomcatInfo>();
			// <host>
			OMElement hostOM = it.next();
			// ip
			String ip = hostOM.getAttributeValue(new QName("ip"));
			hostInfo.setIp(ip);
			// same ip same host
			String[] ipStr = ip.split("\\.");
			hostInfo.setId(256 * Integer.parseInt(ipStr[2])
					+ Integer.parseInt(ipStr[3]));
			for(Iterator<OMElement> hostIt=hostOM.getChildElements();hostIt.hasNext();){
				OMElement htom=hostIt.next();
				if("hostInfo".equalsIgnoreCase(htom.getLocalName())){
				for (Iterator<OMElement> hInfoIt = htom.getChildElements(); hInfoIt
					.hasNext();) {
				OMElement child = hInfoIt.next();
				// memory
				if ("memory".equals(child.getLocalName())) {
					StatisticInfo<Long> memory = new StatisticInfo<Long>();
					for (Iterator<OMElement> memIt = child.getChildElements(); memIt
							.hasNext();) {
						OMElement child2 = memIt.next();
						if ("free".equals(child2.getLocalName())) {
							memory.setFree(Long
									.parseLong(child2.getText().substring(0,
											child2.getText().length() - 1)));
						} else if ("used".equals(child2.getLocalName())) {
							memory.setUsed(Long
									.parseLong(child2.getText().substring(0,
											child2.getText().length() - 1)));
						}
					}
					hostInfo.setMemory(memory);
				}
				// cpu
				else if ("cpu".equals(child.getLocalName())) {
					StatisticInfo<Double> cpu=new StatisticInfo<Double>();
					for(Iterator<OMElement> cpuIt=child.getChildElements();cpuIt.hasNext();){
						OMElement child2=cpuIt.next();
						if ("average".equals(child2.getLocalName())) {
							cpu.setAverage(Double.parseDouble(child2
									.getText()));
						} else if ("maximum".equals(child2.getLocalName())) {
							cpu.setMaximum(Double.parseDouble(child2
									.getText()));
						} else if ("minimum".equals(child2.getLocalName())) {
							cpu.setMinimum(Double.parseDouble(child2
									.getText()));
						} else if ("used".equals(child2.getLocalName())) {
							if(child2.getText().contains("%"))
								cpu.setUsed(Double.parseDouble(child2
										.getText().substring(0,child2.getText().length()-1)));
							else cpu.setUsed(Double.parseDouble(child2.getText()));
						} else if ("free".equals(child2.getLocalName())) {
							cpu.setFree(Double.parseDouble(child2
									.getText()));
						}
					}
					hostInfo.setCpu(cpu);
				}
				// net
				else if ("net".equals(child.getLocalName())) {
					for (Iterator<OMElement> netIt = child.getChildElements(); netIt
							.hasNext();) {
						OMElement child2 = netIt.next();
						if ("sendRate".equals(child2.getLocalName())) {
							hostInfo.setSendRate(Double.parseDouble(child2
									.getText()));
						} else if ("receiveRate".equals(child2.getLocalName())) {
							hostInfo.setReceiveRate(Double.parseDouble(child2
									.getText()));
						}
					}
				}
				// disks
				else if ("disk".equals(child.getLocalName())) {
					List<DiskInfo> disk = new ArrayList<DiskInfo>();
					for (Iterator<OMElement> disksIt = child.getChildElements(); disksIt
							.hasNext();) {
						OMElement child2 = disksIt.next();
						if (child2.getChildElements().hasNext()) {
							DiskInfo diskInfo = new DiskInfo();
							diskInfo.setName(child2.getAttributeValue(
									new QName("name")).substring(0, 1));
							for (Iterator<OMElement> deviceIt = child2
									.getChildElements(); deviceIt.hasNext();) {
								OMElement child3 = deviceIt.next();
								if ("free".equals(child3.getLocalName())) {
									diskInfo
											.setFree(Long
													.parseLong(child3
															.getText()
															.substring(
																	0,
																	child3
																			.getText()
																			.length() - 2)));
								}else if ("used".equals(child3.getLocalName())) {
									String ddusedText = child3.getText();
									if (ddusedText.contains("KB"))
										diskInfo
												.setUsed(Long
														.parseLong(ddusedText
																.substring(
																		0,
																		ddusedText
																				.length() - 2)));
								}
							}
							disk.add(diskInfo);
						}
					}
					Collections.sort(disk, new Comparator<DiskInfo>() {

						public int compare(DiskInfo o1, DiskInfo o2) {
							return o1.getName().compareTo(o2.getName());
						}

					});
					hostInfo.setDisk(disk);
				} else if ("startTime".equals(child.getLocalName())) {
					hostInfo.setStartTime(child.getText());
				}
			}
		}
				else if("tomcatInfo".equalsIgnoreCase(htom.getLocalName())){
					TomcatInfo tomcatInfo = new TomcatInfo();
					List<AppInfo> apps = new ArrayList<AppInfo>();
					// port
					String port = htom.getAttributeValue(new QName("port"));
					// same port same tomcat
					tomcatInfo.setPort(Integer.parseInt(port));
					for (Iterator<OMElement> tInfoIt = htom.getChildElements(); tInfoIt
							.hasNext();) {
						OMElement child = tInfoIt.next();
						// memory
						if ("memory".equals(child.getLocalName())) {
							StatisticInfo<Long> tmemory = new StatisticInfo<Long>();
							for (Iterator<OMElement> memIt = child
									.getChildElements(); memIt.hasNext();) {
								OMElement child2 = memIt.next();
								if ("average".equals(child2.getLocalName())) {
									tmemory.setAverage(Long.parseLong(child2
											.getText()));
								} else if ("free".equals(child2.getLocalName())) {
									tmemory.setFree(Long
											.parseLong(child2.getText()));
								} else if ("used".equals(child2.getLocalName())) {
									tmemory.setUsed(Long
											.parseLong(child2.getText()));
								} else if ("maximum".equals(child2.getLocalName())) {
									tmemory.setMaximum(Long.parseLong(child2
											.getText()));
								} else if ("minimum".equals(child2.getLocalName())) {
									tmemory.setMinimum(Long.parseLong(child2
											.getText()));
								}
							}
							tomcatInfo.setMemory(tmemory);
						}
						// cpu
						else if ("cpu".equals(child.getLocalName())) {
							StatisticInfo<Double> tcpu = new StatisticInfo<Double>();
							for (Iterator<OMElement> tcpuIt = child
									.getChildElements(); tcpuIt.hasNext();) {
								OMElement child2 = tcpuIt.next();
								if ("average".equals(child2.getLocalName())) {
									tcpu.setAverage(Double.parseDouble(child2
											.getText()));
								} else if ("maximum".equals(child2.getLocalName())) {
									tcpu.setMaximum(Double.parseDouble(child2
											.getText()));
								} else if ("minimum".equals(child2.getLocalName())) {
									tcpu.setMinimum(Double.parseDouble(child2
											.getText()));
								} else if ("used".equals(child2.getLocalName())) {
									tcpu.setUsed(Double.parseDouble(child2
											.getText()));
								} else if ("free".equals(child2.getLocalName())) {
									tcpu.setFree(Double.parseDouble(child2
											.getText()));
								}
							}
							tomcatInfo.setCpu(tcpu);
						}
						// runningTime
						else if ("runningTime".equals(child.getLocalName())) {
							tomcatInfo.setRunningTime(child.getText() + "s");
						} else if ("threadInfo".equals(child.getLocalName())) {
//							String num = child.getAttributeValue(new QName("num"));
							for (Iterator<OMElement> appIt = child
									.getChildElements(); appIt.hasNext();) {
								OMElement appOM = appIt.next();
								AppInfo appInfo = new AppInfo();
								String location = appOM
										.getAttributeValue(new QName("location"));
								String appName=null;
								if(location.endsWith(".aar"))
									appName="Web Service:"+location.substring(location
											.lastIndexOf('/') + 1,location.lastIndexOf('.'));
								else 
									appName ="Web App:"+ location.substring(location
											.lastIndexOf('/') + 1);
								// same name same app
								appInfo.setName(appName);
								String user = appOM.getAttributeValue(new QName(
										"user"));
								appInfo.setLocation(location);
								appInfo.setUser(user);
								for (Iterator<OMElement> aInfoIt = appOM
										.getChildElements(); aInfoIt.hasNext();) {
									OMElement child2 = aInfoIt.next();
									// cpu
									if ("cpu".equals(child2.getLocalName())) {
										StatisticInfo<Double> acpu = new StatisticInfo<Double>();
										for (Iterator<OMElement> acpuIt = child2
												.getChildElements(); acpuIt
												.hasNext();) {
											OMElement child3 = acpuIt.next();
											if ("average".equals(child3
													.getLocalName())) {
												acpu.setAverage(Double
														.parseDouble(child3
																.getText()));
											} else if ("maximum".equals(child3
													.getLocalName())) {
												acpu.setMaximum(Double
														.parseDouble(child3
																.getText()));
											} else if ("minimum".equals(child3
													.getLocalName())) {
												acpu.setMinimum(Double
														.parseDouble(child3
																.getText()));
											} else if ("used".equals(child3
													.getLocalName())) {
												acpu.setUsed(Double
														.parseDouble(child3
																.getText()));
											} else if ("free".equals(child3
													.getLocalName())) {
												acpu.setFree(Double
														.parseDouble(child3
																.getText()));
											}
										}
										appInfo.setCpu(acpu);
									}
									// throughput
									else if ("throughput".equals(child2
											.getLocalName())) {
										StatisticInfo<Long> athroughput = new StatisticInfo<Long>();
										for (Iterator<OMElement> athrIt = child2
												.getChildElements(); athrIt
												.hasNext();) {
											OMElement child3 = athrIt.next();
											if ("average".equals(child3
													.getLocalName())) {
												athroughput
														.setAverage(Long
																.parseLong(child3
																		.getText()));
											} else if ("free".equals(child3
													.getLocalName())) {
												athroughput
														.setFree(Long
																.parseLong(child3
																		.getText()));
											} else if ("used".equals(child3
													.getLocalName())) {
												athroughput
														.setUsed(Long
																.parseLong(child3
																		.getText()));
											} else if ("maximum".equals(child3
													.getLocalName())) {
												athroughput
														.setMaximum(Long
																.parseLong(child3
																		.getText()));
											} else if ("minimum".equals(child3
													.getLocalName())) {
												athroughput
														.setMinimum(Long
																.parseLong(child3
																		.getText()));
											}
										}
										appInfo.setThroughput(athroughput);
									}
									// responseTime
									else if ("responseTime".equals(child2
											.getLocalName())) {
										StatisticInfo<Long> responseTime = new StatisticInfo<Long>();
										for (Iterator<OMElement> respIt = child2
												.getChildElements(); respIt
												.hasNext();) {
											OMElement child3 = respIt.next();
											if ("average".equals(child3
													.getLocalName())) {
												responseTime
														.setAverage(Long
																.parseLong(child3
																		.getText()));
											} else if ("free".equals(child3
													.getLocalName())) {
												responseTime
														.setFree(Long
																.parseLong(child3
																		.getText()));
											} else if ("used".equals(child3
													.getLocalName())) {
												responseTime
														.setUsed(Long
																.parseLong(child3
																		.getText()));
											} else if ("maximum".equals(child3
													.getLocalName())) {
												responseTime
														.setMaximum(Long
																.parseLong(child3
																		.getText()));
											} else if ("minimum".equals(child3
													.getLocalName())) {
												responseTime
														.setMinimum(Long
																.parseLong(child3
																		.getText()));
											}
										}
										appInfo.setResponseTime(responseTime);
									}
									// invokeResult
									else if ("invokeResult".equals(child2
											.getLocalName())) {
										for (Iterator<OMElement> iresIt = child2
												.getChildElements(); iresIt
												.hasNext();) {
											OMElement child3 = iresIt.next();
											if ("successful".equals(child3
													.getLocalName())) {
												appInfo
														.setSuccessfull(Long
																.parseLong(child3
																		.getText()));
											} else if ("failed".equals(child3
													.getLocalName())) {
												appInfo
														.setFailed(Long
																.parseLong(child3
																		.getText()));
											}
										}
									}
								}
								apps.add(appInfo);
							}
							tomcatInfo.setApps(apps);
						}
					}
					tomcat.add(tomcatInfo);
				}
				hostInfo.setTomcat(tomcat);
			}
			list.add(hostInfo);
		}
		result.setHost(list);
		return result;
	}
	/**
	 * parse administrator-oriented message from tmpfile
	 * @return
	 */
	public MonitorInfo parse() {
		XMLStreamReader reader = null;
		try {
			reader = XMLInputFactory.newInstance().createXMLStreamReader(
					new FileInputStream(ReceiverThread.tmpFile));
		} catch (Exception e) {
			return null;
		}
		StAXOMBuilder builder = new StAXOMBuilder(reader);
		OMElement doc = builder.getDocumentElement();
		return parse(doc);
	}
	/**
	 * parse user-oriented message
	 * @param username
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public UserInfo parseUser(String username) {
		OMElement doc = getDoc(Constant.USER_METHOD,username);
		if (doc == null)
			return null;
		UserInfo result = new UserInfo();
		List<AppInfo> apps = new ArrayList<AppInfo>();
		OMElement monitorInfoOM = doc.getFirstElement().getFirstElement();
		OMElement userOM = monitorInfoOM.getFirstElement();
		if (userOM == null)
			return null;
		String userName = userOM.getAttributeValue(new QName("name"));
		result.setName(userName);
		for(Iterator<OMElement> userIt=userOM.getChildElements();userIt.hasNext();){
			OMElement child=userIt.next();
			//total cpu
			if("cpu".equals(child.getLocalName())){
				StatisticInfo<Double> usercpu = new StatisticInfo<Double>();
				for(Iterator<OMElement> usercpuIt=child.getChildElements();usercpuIt.hasNext();){
					OMElement child2=usercpuIt.next();
					if("average".equals(child2.getLocalName())){
						usercpu.setAverage(Double.parseDouble(child2.getText()));
					}
					else if("maximum".equals(child2.getLocalName())){
						usercpu.setMaximum(Double.parseDouble(child2.getText()));
					}
					else if("minimum".equals(child2.getLocalName())){
						usercpu.setMinimum(Double.parseDouble(child2.getText()));
					}
					else if("used".equals(child2.getLocalName())){
						usercpu.setUsed(Double.parseDouble(child2.getText()));
					}
					else if("free".equals(child2.getLocalName())){
						usercpu.setFree(Double.parseDouble(child2.getText()));
					}
				}
				result.setCpu(usercpu);
			}
			//<appList>s
			else if("appList".equals(child.getLocalName())){
				for (Iterator<OMElement> appIt = child.getChildElements(); appIt
				.hasNext();) {
					OMElement appOM = appIt.next();
					AppInfo appInfo = new AppInfo();
					String appName = appOM.getAttributeValue(new QName("name"));
					appInfo.setName(appName);
					for(Iterator<OMElement> appInfoIt=appOM.getChildElements();appInfoIt.hasNext();){
						OMElement child2=appInfoIt.next();
						// cpu
						if("cpu".equals(child2.getLocalName())){
							StatisticInfo<Double> acpu = new StatisticInfo<Double>();
							for(Iterator<OMElement> acpuIt=child2.getChildElements();acpuIt.hasNext();){
								OMElement child3=acpuIt.next();
								if("average".equals(child3.getLocalName())){
									acpu.setAverage(Double.parseDouble(child3.getText()));
								}
								else if("maximum".equals(child3.getLocalName())){
									acpu.setMaximum(Double.parseDouble(child3.getText()));
								}
								else if("minimum".equals(child3.getLocalName())){
									acpu.setMinimum(Double.parseDouble(child3.getText()));
								}
								else if("used".equals(child3.getLocalName())){
									acpu.setUsed(Double.parseDouble(child3.getText()));
								}
								else if("free".equals(child3.getLocalName())){
									acpu.setFree(Double.parseDouble(child3.getText()));
								}
							}
							appInfo.setCpu(acpu);
						}
						// throughput
						else if("throughput".equals(child2.getLocalName())){
							StatisticInfo<Long> athroughput = new StatisticInfo<Long>();
							for(Iterator<OMElement> athrIt=child2.getChildElements();athrIt.hasNext();){
								OMElement child3=athrIt.next();
								if("average".equals(child3.getLocalName())){
									athroughput.setAverage(Long.parseLong(child3.getText()));
								}
								else if("maximum".equals(child3.getLocalName())){
									athroughput.setMaximum(Long.parseLong(child3.getText()));
								}
								else if("minimum".equals(child3.getLocalName())){
									athroughput.setMinimum(Long.parseLong(child3.getText()));
								}
								else if("used".equals(child3.getLocalName())){
									athroughput.setUsed(Long.parseLong(child3.getText()));
								}
								else if("free".equals(child3.getLocalName())){
									athroughput.setFree(Long.parseLong(child3.getText()));
								}
							}
							appInfo.setThroughput(athroughput);
						}
						// responseTime
						else if("responseTime".equals(child2.getLocalName())){
							StatisticInfo<Long> responseTime = new StatisticInfo<Long>();
							for(Iterator<OMElement> respIt=child2.getChildElements();respIt.hasNext();){
								OMElement child3=respIt.next();
								if("average".equals(child3.getLocalName())){
									responseTime.setAverage(Long.parseLong(child3.getText()));
								}
								else if("maximum".equals(child3.getLocalName())){
									responseTime.setMaximum(Long.parseLong(child3.getText()));
								}
								else if("minimum".equals(child3.getLocalName())){
									responseTime.setMinimum(Long.parseLong(child3.getText()));
								}
								else if("used".equals(child3.getLocalName())){
									responseTime.setUsed(Long.parseLong(child3.getText()));
								}
								else if("free".equals(child3.getLocalName())){
									responseTime.setFree(Long.parseLong(child3.getText()));
								}
							}
							appInfo.setResponseTime(responseTime);
						}
						// invokeResult
						else if("invokeResult".equals(child2.getLocalName())){
							for(Iterator<OMElement> invIt=child2.getChildElements();invIt.hasNext();){
								OMElement child3=invIt.next();
								if("successful".equals(child3.getLocalName())){
									appInfo.setSuccessfull(Long.parseLong(child3.getText()));
								}
								else if("failed".equals(child3.getLocalName())){
									appInfo.setFailed(Long.parseLong(child3.getText()));
								}
							}
							
						}
					}
					apps.add(appInfo);
				}
				result.setApps(apps);
			}
		}
		return result;
	}
}
