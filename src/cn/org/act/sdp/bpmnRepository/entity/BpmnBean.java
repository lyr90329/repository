package cn.org.act.sdp.bpmnRepository.entity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class BpmnBean {
	private int id;

	private String bpmnLocation;

	private String diagramLocation;

	private String flexLocation;

	private int metaId;

	private MetaBean meta;

	private String BPMNName;

	private String DiagramName;

	private InputStream bpmnStream;

	private long bpmnSize;

	private long diagramSize;

	private long flexSize;

	private long flexImgSize;

	private InputStream diagramStream;

	private InputStream flexStream;

	private InputStream flexImgStream;

	private String bpmnContent;

	private String diagramContent;

	private String flexContent;

	private byte[] flexImg;

	public String getBpmnContent() {
		return bpmnContent;
	}

	public String getBpmnLocation() {
		return bpmnLocation;
	}

	public String getBPMNName() {
		return BPMNName;
	}

	public long getBpmnSize() {
		return bpmnSize;
	}

	public InputStream getBpmnStream() {
		return bpmnStream;
	}

	public String getDiagramContent() {
		return diagramContent;
	}

	public String getDiagramLocation() {
		return diagramLocation;
	}

	public String getDiagramName() {
		return DiagramName;
	}

	public long getDiagramSize() {
		return diagramSize;
	}

	public InputStream getDiagramStream() {
		return diagramStream;
	}

	public String getFlexContent() {
		return flexContent;
	}

	public byte[] getFlexImg() {
		return flexImg;
	}

	public long getFlexImgSize() {
		return flexImgSize;
	}

	public InputStream getFlexImgStream() {
		return flexImgStream;
	}

	public String getFlexLocation() {
		return flexLocation;
	}

	public long getFlexSize() {
		return flexSize;
	}

	public InputStream getFlexStream() {
		return flexStream;
	}

	public int getId() {
		return id;
	}

	public MetaBean getMeta() {
		return meta;
	}

	public int getMetaId() {
		return metaId;
	}

	public void setBpmnContent(String bpmnContent) {
		this.bpmnContent = bpmnContent;
		if (bpmnContent != null) {
			byte[] bytes = bpmnContent.getBytes();
			this.bpmnSize = bytes.length;
			this.bpmnStream = new ByteArrayInputStream(bytes);
		}
	}

	public void setBpmnLocation(String bpmnLocation) {
		this.bpmnLocation = bpmnLocation;
	}

	public void setBPMNName(String name) {
		BPMNName = name;
	}

	public void setBpmnSize(long bpmnSize) {
		this.bpmnSize = bpmnSize;
	}

	public void setBpmnStream(InputStream bpmnStream) {
		if (bpmnStream != null) {
			try {
				byte[] bytes = new byte[bpmnStream.available()];
				this.bpmnSize = bytes.length;
				bpmnStream.read(bytes, 0, bytes.length);
				this.bpmnContent = new String(bytes);
				this.bpmnStream = new ByteArrayInputStream(this.bpmnContent
						.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setDiagramContent(String diagramContent) {
		this.diagramContent = diagramContent;
		if (diagramContent != null) {
			byte[] bytes = diagramContent.getBytes();
			this.diagramSize = bytes.length;
			this.diagramStream = new ByteArrayInputStream(bytes);
		}
	}

	public void setDiagramLocation(String diagramLocation) {
		this.diagramLocation = diagramLocation;
	}

	public void setDiagramName(String diagramName) {
		DiagramName = diagramName;
	}

	public void setDiagramSize(long diagramSize) {
		this.diagramSize = diagramSize;
	}

	public void setDiagramStream(InputStream diagramStream) {
		this.diagramStream = diagramStream;
		if (diagramStream != null) {
			try {
				byte[] bytes = new byte[diagramStream.available()];
				this.diagramSize = bytes.length;
				diagramStream.read(bytes, 0, bytes.length);
				diagramStream.reset();
				this.diagramContent = new String(bytes);
				this.diagramStream = new ByteArrayInputStream(
						this.diagramContent.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setFlexContent(String flexContent) {
		this.flexContent = flexContent;
		if (flexContent != null) {
			byte[] bytes = flexContent.getBytes();
			this.flexSize = bytes.length;
			this.flexStream = new ByteArrayInputStream(bytes);
		}
	}

	public void setFlexImg(byte[] flexImg) {
		this.flexImg = flexImg;
		this.flexImgStream = new ByteArrayInputStream(this.flexImg);
	}

	public void setFlexImgSize(long flexImgSize) {
		this.flexImgSize = flexImgSize;
	}

	public void setFlexImgStream(InputStream flexImgStream) {
		if (flexImgStream != null) {
			try {
				this.flexImgSize = flexImgStream.available();
				this.flexImg = new byte[flexImgStream.available()];
				flexImgStream.read(this.flexImg, 0, this.flexImg.length);
				this.flexImgStream = new ByteArrayInputStream(this.flexImg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setFlexLocation(String flexLocation) {
		this.flexLocation = flexLocation;
	}

	public void setFlexSize(long flexSize) {
		this.flexSize = flexSize;
	}

	public void setFlexStream(InputStream flexStream) {
		if (flexStream != null) {
			try {
				byte[] bytes = new byte[flexStream.available()];
				this.flexSize = bytes.length;
				flexStream.read(bytes, 0, bytes.length);
				this.flexContent = new String(bytes);
				this.flexStream = new ByteArrayInputStream(this.flexContent
						.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMeta(MetaBean meta) {
		this.meta = meta;
	}

	public void setMetaId(int metaId) {
		this.metaId = metaId;
	}

}
