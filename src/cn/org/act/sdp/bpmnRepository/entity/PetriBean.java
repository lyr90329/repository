package cn.org.act.sdp.bpmnRepository.entity;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PetriBean {
	private int id;
	private int bpmnId;
	private Object petri;
	private InputStream petriIs;
	private long petriSize;

	public InputStream getPetriIs() {
		return petriIs;
	}

	public void setPetriIs(InputStream petriIs) {
		this.petriIs = petriIs;
		if (petriIs != null) {
			try {
				ObjectInputStream ois = new ObjectInputStream(petriIs);
				this.petriSize = ois.available();
				this.petri = ois.readObject();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public long getPetriSize() {
		return petriSize;
	}

	public void setPetriSize(long petriSize) {
		this.petriSize = petriSize;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBpmnId() {
		return bpmnId;
	}

	public void setBpmnId(int bpmnId) {
		this.bpmnId = bpmnId;
	}

	public Object getPetri() {
		return petri;
	}

	public void setPetri(Object petri) {
		this.petri = petri;
		if (petri != null) {
			byte[] bytes = null;
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(4096);
			ObjectOutputStream os;
			try {
				os = new ObjectOutputStream(byteStream);
				os.writeObject(this.petri);
				os.flush();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			// retrieves byte array
			bytes = byteStream.toByteArray();
			this.petriSize = bytes.length;
			this.petriIs = new ByteArrayInputStream(bytes);
		}
	}
}
