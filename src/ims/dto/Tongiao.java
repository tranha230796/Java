/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "tongiao")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tongiao.findAll", query = "SELECT t FROM Tongiao t")})
public class Tongiao implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idtongiao", nullable = false)
    private Integer idtongiao;
    @Basic(optional = false)
    @Column(name = "ten", nullable = false, length = 255)
    private String ten;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idtongiao")
    private List<Nhanvien> nhanvienList;

    public Tongiao() {
    }

    public Tongiao(Integer idtongiao) {
        this.idtongiao = idtongiao;
    }

    public Tongiao(Integer idtongiao, String ten) {
        this.idtongiao = idtongiao;
        this.ten = ten;
    }

    public Integer getIdtongiao() {
        return idtongiao;
    }

    public void setIdtongiao(Integer idtongiao) {
        this.idtongiao = idtongiao;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    @XmlTransient
    public List<Nhanvien> getNhanvienList() {
        return nhanvienList;
    }

    public void setNhanvienList(List<Nhanvien> nhanvienList) {
        this.nhanvienList = nhanvienList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtongiao != null ? idtongiao.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tongiao)) {
            return false;
        }
        Tongiao other = (Tongiao) object;
        if ((this.idtongiao == null && other.idtongiao != null) || (this.idtongiao != null && !this.idtongiao.equals(other.idtongiao))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return ten;
    }
    
}
