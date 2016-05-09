package uy.gub.dgr.sur.entity;//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : CeldaLte.java
//  @ Date : 11/12/2013
//  @ Author : 
//
//

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Data
@ToString(exclude = "nodoLte")
@EqualsAndHashCode(callSuper = true, exclude = {"nodoLte", "pci"})
@DiscriminatorValue("LTE")
@Audited
public class CeldaLte extends Celda {

    private String pci;
    @ManyToOne

    private NodoLte nodoLte;

}