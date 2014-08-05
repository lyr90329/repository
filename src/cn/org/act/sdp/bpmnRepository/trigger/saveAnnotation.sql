CREATE PROCEDURE pr_fn_saveAnnotation (msg TEXT, userId INTEGER, time DATETIME, bpmnId INTEGER, elementId INTEGER)
BEGIN
  DECLARE annotationId INTEGER;
  INSERT INTO pr_annotation(msg,userId,time) value(msg,userId,time);
  SET annotationId=LAST_INSERT_ID();
  INSERT INTO pr_r_bpmn_element_annotation(annotationId,bpmnId,elementId) values(annotationId,bpmnId,elementId);
  SELECT annotationId;
END;