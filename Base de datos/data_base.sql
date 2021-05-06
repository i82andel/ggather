-- MySQL Script generated by MySQL Workbench
-- Wed May  5 14:23:47 2021
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Jugadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Jugadores` (
  `idJugador` INT NOT NULL AUTO_INCREMENT,
  `usuario` VARCHAR(45) NOT NULL,
  `nombre` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `apellidos` VARCHAR(45) NOT NULL,
  `fechaNacimiento` DATETIME NOT NULL,
  `contraseña` VARCHAR(45) NULL,
  PRIMARY KEY (`idJugador`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Propietario`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Propietario` (
  `idClub` INT NOT NULL AUTO_INCREMENT,
  `nombreClub` VARCHAR(45) NOT NULL,
  `localización` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `contraseña` VARCHAR(45) NOT NULL,
  `telefono` INT NOT NULL,
  `alta` INT NOT NULL,
  PRIMARY KEY (`idClub`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Pistas`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Pistas` (
  `idPista` INT NOT NULL AUTO_INCREMENT,
  `idClub` INT NOT NULL,
  `Deporte` VARCHAR(45) NOT NULL,
  `horarioInicio` TIME NOT NULL,
  `horarioFin` TIME NOT NULL,
  `precioHora` DECIMAL(4,2) NOT NULL,
  `puntuacionMedia` INT NULL,
  `Propietario_idClub` INT NOT NULL,
  PRIMARY KEY (`idPista`, `Propietario_idClub`),
  INDEX `fk_Pistas_Propietario1_idx` (`Propietario_idClub` ASC) VISIBLE,
  CONSTRAINT `fk_Pistas_Propietario1`
    FOREIGN KEY (`Propietario_idClub`)
    REFERENCES `mydb`.`Propietario` (`idClub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Partidos`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Partidos` (
  `idPartido` INT NOT NULL AUTO_INCREMENT,
  `deporte` VARCHAR(45) NOT NULL,
  `fechayHora` DATETIME NOT NULL,
  `idPista` INT NOT NULL,
  `idcreador` INT NOT NULL,
  `Partidoscol` VARCHAR(45) NULL,
  `Jugadores_idJugador` INT NOT NULL,
  `Pistas_idPista` INT NOT NULL,
  PRIMARY KEY (`idPartido`, `Jugadores_idJugador`, `Pistas_idPista`),
  INDEX `fk_Partidos_Jugadores_idx` (`Jugadores_idJugador` ASC) VISIBLE,
  INDEX `fk_Partidos_Pistas1_idx` (`Pistas_idPista` ASC) VISIBLE,
  CONSTRAINT `fk_Partidos_Jugadores`
    FOREIGN KEY (`Jugadores_idJugador`)
    REFERENCES `mydb`.`Jugadores` (`idJugador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Partidos_Pistas1`
    FOREIGN KEY (`Pistas_idPista`)
    REFERENCES `mydb`.`Pistas` (`idPista`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Puntuaciones`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Puntuaciones` (
  `idPuntuaciones` INT NOT NULL AUTO_INCREMENT,
  `idPista` INT NOT NULL,
  `idUsuario` INT NOT NULL,
  `puntuacion` INT NOT NULL,
  `Comentario` TEXT(240) NULL,
  `Pistas_idPista` INT NOT NULL,
  `Pistas_Propietario_idClub` INT NOT NULL,
  PRIMARY KEY (`idPuntuaciones`, `Pistas_idPista`, `Pistas_Propietario_idClub`),
  INDEX `fk_Puntuaciones_Pistas1_idx` (`Pistas_idPista` ASC, `Pistas_Propietario_idClub` ASC) VISIBLE,
  CONSTRAINT `fk_Puntuaciones_Pistas1`
    FOREIGN KEY (`Pistas_idPista` , `Pistas_Propietario_idClub`)
    REFERENCES `mydb`.`Pistas` (`idPista` , `Propietario_idClub`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Valoraciones_jugadores`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`Valoraciones_jugadores` (
  `idValoraciones` INT NOT NULL AUTO_INCREMENT,
  `idUsuarioValorador` INT NOT NULL,
  `puntuación` INT NOT NULL,
  `Jugadores_idJugador` INT NOT NULL,
  PRIMARY KEY (`idValoraciones`, `Jugadores_idJugador`),
  INDEX `fk_Valoraciones_jugadores_Jugadores1_idx` (`Jugadores_idJugador` ASC) VISIBLE,
  CONSTRAINT `fk_Valoraciones_jugadores_Jugadores1`
    FOREIGN KEY (`Jugadores_idJugador`)
    REFERENCES `mydb`.`Jugadores` (`idJugador`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`table1`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `mydb`.`table1` (
)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
