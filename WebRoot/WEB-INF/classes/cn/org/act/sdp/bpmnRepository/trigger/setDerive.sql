DELIMITER $$

DROP PROCEDURE IF EXISTS `repository`.`setDrive` $$
CREATE PROCEDURE `repository`.`setDrive` (in parent INTEGER, in child INTEGER)
BEGIN
     declare num integer;
     select vernum into num from Anotation where BPMNID=parent;
     update Anotation set Derive=parent where BPMNID=child;
     update Anotation set version=num where BPMNID=child;
     update Anotation set vernum=num+1 where BPMNID=parent;
END $$

DELIMITER ;

#call repository.setDrive(30, 31);