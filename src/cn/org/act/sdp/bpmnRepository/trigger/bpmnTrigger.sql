DELIMITER $$

Drop TRIGGER IF EXISTS REPOSITORY.bpmnTrigger $$
CREATE TRIGGER bpmnTrigger AFTER INSERT ON bpmn
FOR EACH ROW
  insert into anotation set BPMNID=NEW.ID, derive=New.ID
$$

DELIMITER ;