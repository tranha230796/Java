/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "tinhtrangsuckhoe")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tinhtrangsuckhoe.findAll", query = "SELECT t FROM Tinhtrangsuckhoe t")})
public class Tinhtrangsuckhoe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idsuckhoe", nullable = false)
    private Integer idsuckhoe;
    @Basic(optional = false)
    @Column(name = "nhommau", nullable = false, length = 4)
    private String nhommau;
    @Basic(optional = false)
    @Column(name = "chieucao", nullable = false)
    private int chieucao;
    @Basic(optional = false)
    @Column(name = "cannang", nullable = false)
    private int cannang;
    @Basic(optional = false)
    @Column(name = "loaisuckhoe", nullable = false)
    private int loaisuckhoe;
    @Basic(optional = false)
    @Column(name = "benhly", nullable = false, length = 50)
    private String benhly;

    public Tinhtrangsuckhoe() {
    }

    public Tinhtrangsuckhoe(Integer idsuckhoe) {
        this.idsuckhoe = idsuckhoe;
    }

    public Tinhtrangsuckhoe(Integer idsuckhoe, String nhommau, int chieucao, int cannang, int loaisuckhoe, String benhly) {
        this.idsuckhoe = idsuckhoe;
        this.nhommau = nhommau;
        this.chieucao = chieucao;
        this.cannang = cannang;
        this.loaisuckhoe = loaisuckhoe;
        this.benhly = benhly;
    }

    public Integer getIdsuckhoe() {
        return idsuckhoe;
    }

    public void setIdsuckhoe(Integer idsuckhoe) {
        this.idsuckhoe = idsuckhoe;
    }

    public String getNhommau() {
        return nhommau;
    }

    public void setNhommau(String nhommau) {
        this.nhommau = nhommau;
    }

    public int getChieucao() {
        return chieucao;
    }

    public void setChieucao(int chieucao) {
        this.chieucao = chieucao;
    }

    public int getCannang() {
        return cannang;
    }

    public void setCannang(int cannang) {
        this.cannang = cannang;
    }

    public int getLoaisuckhoe() {
        return loaisuckhoe;
    }

    public void setLoaisuckhoe(int loaisuckhoe) {
        this.loaisuckhoe = loaisuckhoe;
    }

    public String getBenhly() {
        return benhly;
    }

    public void setBenhly(String benhly) {
        this.benhly = benhly;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idsuckhoe != null ? idsuckhoe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tinhtrangsuckhoe)) {
            return false;
        }
        Tinhtrangsuckhoe other = (Tinhtrangsuckhoe) object;
        if ((this.idsuckhoe == null && other.idsuckhoe != null) || (this.idsuckhoe != null && !this.idsuckhoe.equals(other.idsuckhoe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Tinhtrangsuckhoe[ idsuckhoe=" + idsuckhoe + " ]";
    }
    
}
