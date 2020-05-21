CREATE TABLE `tucuenta`.`factura` (
  `idfactura` INT NOT NULL AUTO_INCREMENT,
  `fecha` DATETIME NULL,
  `url` VARCHAR(500) NULL,
  `total` DECIMAL NULL,
  PRIMARY KEY (`idfactura`));

CREATE TABLE `tucuenta`.`comercio` (
  `idcomercio` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(45) NULL,
  `nit` VARCHAR(45) NULL,
  `direccion` VARCHAR(45) NULL,
  `telefono` VARCHAR(45) NULL,
  `barrio` VARCHAR(45) NULL,
  PRIMARY KEY (`idcomercio`));

CREATE TABLE `tucuenta`.`cliente` (
  `idcliente` INT NOT NULL AUTO_INCREMENT,
  `correo` VARCHAR(45) NULL,
  PRIMARY KEY (`idcliente`));

CREATE TABLE `tucuenta`.`producto` (
  `idproducto` INT NOT NULL AUTO_INCREMENT,
  `nombre` VARCHAR(100) NULL,
  `precio` DECIMAL NULL,
  `cantidad` INT NULL,
  PRIMARY KEY (`idproducto`));
  
ALTER TABLE `tucuenta`.`comercio` 
ADD COLUMN `idfactura` INT NULL AFTER `barrio`,
ADD INDEX `Fk_FacturaComercio_idx` (`idfactura` ASC);
ALTER TABLE `tucuenta`.`comercio` 
ADD CONSTRAINT `Fk_FacturaComercio`
  FOREIGN KEY (`idfactura`)
  REFERENCES `tucuenta`.`factura` (`idfactura`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `tucuenta`.`cliente` 
ADD COLUMN `idfactura` INT NULL AFTER `correo`,
ADD INDEX `Fk_FacturaCliente_idx` (`idfactura` ASC);
ALTER TABLE `tucuenta`.`cliente` 
ADD CONSTRAINT `Fk_FacturaCliente`
  FOREIGN KEY (`idfactura`)
  REFERENCES `tucuenta`.`factura` (`idfactura`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
ALTER TABLE `tucuenta`.`producto` 
ADD COLUMN `idfactura` INT NULL AFTER `cantidad`,
ADD INDEX `Fk_FacturaProducto_idx` (`idfactura` ASC);
ALTER TABLE `tucuenta`.`producto` 
ADD CONSTRAINT `Fk_FacturaProducto`
  FOREIGN KEY (`idfactura`)
  REFERENCES `tucuenta`.`factura` (`idfactura`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

  
