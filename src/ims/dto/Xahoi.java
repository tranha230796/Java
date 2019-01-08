/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "xahoi")
@NamedQueries({
    @NamedQuery(name = "Xahoi.findAll", query = "SELECT x FROM Xahoi x")})
public class Xahoi implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idxahoi", nullable = false)
    private Integer idxahoi;
    @Basic(optional = false)
    @Column(name = "tenxahoi", nullable = false, length = 255)
    private String tenxahoi;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idxahoi")
    private Collection<Nhanvien> nhanvienCollection;

    public Xahoi() {
    }

    public Xahoi(Integer idxahoi) {
        this.idxahoi = idxahoi;
    }

    public Xahoi(Integer idxahoi, String tenxahoi) {
        this.idxahoi = idxahoi;
        this.tenxahoi = tenxahoi;
    }

    public Integer getIdxahoi() {
        return idxahoi;
    }

    public void setIdxahoi(Integer idxahoi) {
        this.idxahoi = idxahoi;
    }

    public String getTenxahoi() {
        return tenxahoi;
    }

    public void setTenxahoi(String tenxahoi) {
        this.tenxahoi = tenxahoi;
    }

    public Collection<Nhanvien> getNhanvienCollection() {
        return nhanvienCollection;
    }

    public void setNhanvienCollection(Collection<Nhanvien> nhanvienCollection) {
        this.nhanvienCollection = nhanvienCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idxahoi != null ? idxahoi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Xahoi)) {
            return false;
        }
        Xahoi other = (Xahoi) object;
        if ((this.idxahoi == null && other.idxahoi != null) || (this.idxahoi != null && !this.idxahoi.equals(other.idxahoi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Xahoi[ idxahoi=" + idxahoi + " ]";
    }
    
}
