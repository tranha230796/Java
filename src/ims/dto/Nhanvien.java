/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Hades
 */
@Entity
@Table(name = "nhanvien")
@NamedQueries({
    @NamedQuery(name = "Nhanvien.findAll", query = "SELECT n FROM Nhanvien n")})
public class Nhanvien implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idnhanvien", nullable = false)
    private Integer idnhanvien;
    @Basic(optional = false)
    @Lob
    @Column(name = "hoten", nullable = false, length = 65535)
    private String hoten;
    @Basic(optional = false)
    @Column(name = "gioitinh", nullable = false)
    private boolean gioitinh;
    @Basic(optional = false)
    @Column(name = "dienthoai", nullable = false)
    private int dienthoai;
    @Basic(optional = false)
    @Column(name = "dtdd", nullable = false)
    private int dtdd;
    @Basic(optional = false)
    @Column(name = "email", nullable = false, length = 255)
    private String email;
    @Basic(optional = false)
    @Lob
    @Column(name = "ghichu", nullable = false, length = 65535)
    private String ghichu;
    @Basic(optional = false)
    @Column(name = "socmnd", nullable = false)
    private int socmnd;
    @Basic(optional = false)
    @Lob
    @Column(name = "noicap", nullable = false, length = 65535)
    private String noicap;
    @Basic(optional = false)
    @Column(name = "ngaycap", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date ngaycap;
    @Basic(optional = false)
    @Lob
    @Column(name = "nhandang", nullable = false, length = 65535)
    private String nhandang;
    @Basic(optional = false)
    @Column(name = "madoi", nullable = false)
    private int madoi;
    @Basic(optional = false)
    @Column(name = "mato", nullable = false)
    private int mato;
    @JoinColumn(name = "idquoctich", referencedColumnName = "idquoctich", nullable = false)
    @ManyToOne(optional = false)
    private Quoctich idquoctich;
    @JoinColumn(name = "idgiadinh", referencedColumnName = "idgiadinh", nullable = false)
    @ManyToOne(optional = false)
    private Giadinh idgiadinh;
    @JoinColumn(name = "iddantoc", referencedColumnName = "iddantoc", nullable = false)
    @ManyToOne(optional = false)
    private Dantoc iddantoc;
    @JoinColumn(name = "idphongban", referencedColumnName = "idphongban", nullable = false)
    @ManyToOne(optional = false)
    private Phongban idphongban;
    @JoinColumn(name = "idtongiao", referencedColumnName = "idtongiao", nullable = false)
    @ManyToOne(optional = false)
    private Tongiao idtongiao;
    @JoinColumn(name = "idnoisinhsong", referencedColumnName = "idnoisinhsong", nullable = false)
    @ManyToOne(optional = false)
    private Noisinhsong idnoisinhsong;
    @JoinColumn(name = "idxahoi", referencedColumnName = "idxahoi", nullable = false)
    @ManyToOne(optional = false)
    private Xahoi idxahoi;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "nhanvien")
    private To to;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "nhanvien")
    private Doi doi;

    public Nhanvien() {
    }

    public Nhanvien(Integer idnhanvien) {
        this.idnhanvien = idnhanvien;
    }

    public Nhanvien(Integer idnhanvien, String hoten, boolean gioitinh, int dienthoai, int dtdd, String email, String ghichu, int socmnd, String noicap, Date ngaycap, String nhandang, int madoi, int mato) {
        this.idnhanvien = idnhanvien;
        this.hoten = hoten;
        this.gioitinh = gioitinh;
        this.dienthoai = dienthoai;
        this.dtdd = dtdd;
        this.email = email;
        this.ghichu = ghichu;
        this.socmnd = socmnd;
        this.noicap = noicap;
        this.ngaycap = ngaycap;
        this.nhandang = nhandang;
        this.madoi = madoi;
        this.mato = mato;
    }

    public Integer getIdnhanvien() {
        return idnhanvien;
    }

    public void setIdnhanvien(Integer idnhanvien) {
        this.idnhanvien = idnhanvien;
    }

    public String getHoten() {
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public boolean getGioitinh() {
        return gioitinh;
    }

    public void setGioitinh(boolean gioitinh) {
        this.gioitinh = gioitinh;
    }

    public int getDienthoai() {
        return dienthoai;
    }

    public void setDienthoai(int dienthoai) {
        this.dienthoai = dienthoai;
    }

    public int getDtdd() {
        return dtdd;
    }

    public void setDtdd(int dtdd) {
        this.dtdd = dtdd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public int getSocmnd() {
        return socmnd;
    }

    public void setSocmnd(int socmnd) {
        this.socmnd = socmnd;
    }

    public String getNoicap() {
        return noicap;
    }

    public void setNoicap(String noicap) {
        this.noicap = noicap;
    }

    public Date getNgaycap() {
        return ngaycap;
    }

    public void setNgaycap(Date ngaycap) {
        this.ngaycap = ngaycap;
    }

    public String getNhandang() {
        return nhandang;
    }

    public void setNhandang(String nhandang) {
        this.nhandang = nhandang;
    }

    public int getMadoi() {
        return madoi;
    }

    public void setMadoi(int madoi) {
        this.madoi = madoi;
    }

    public int getMato() {
        return mato;
    }

    public void setMato(int mato) {
        this.mato = mato;
    }

    public Quoctich getIdquoctich() {
        return idquoctich;
    }

    public void setIdquoctich(Quoctich idquoctich) {
        this.idquoctich = idquoctich;
    }

    public Giadinh getIdgiadinh() {
        return idgiadinh;
    }

    public void setIdgiadinh(Giadinh idgiadinh) {
        this.idgiadinh = idgiadinh;
    }

    public Dantoc getIddantoc() {
        return iddantoc;
    }

    public void setIddantoc(Dantoc iddantoc) {
        this.iddantoc = iddantoc;
    }

    public Phongban getIdphongban() {
        return idphongban;
    }

    public void setIdphongban(Phongban idphongban) {
        this.idphongban = idphongban;
    }

    public Tongiao getIdtongiao() {
        return idtongiao;
    }

    public void setIdtongiao(Tongiao idtongiao) {
        this.idtongiao = idtongiao;
    }

    public Noisinhsong getIdnoisinhsong() {
        return idnoisinhsong;
    }

    public void setIdnoisinhsong(Noisinhsong idnoisinhsong) {
        this.idnoisinhsong = idnoisinhsong;
    }

    public Xahoi getIdxahoi() {
        return idxahoi;
    }

    public void setIdxahoi(Xahoi idxahoi) {
        this.idxahoi = idxahoi;
    }

    public To getTo() {
        return to;
    }

    public void setTo(To to) {
        this.to = to;
    }

    public Doi getDoi() {
        return doi;
    }

    public void setDoi(Doi doi) {
        this.doi = doi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnhanvien != null ? idnhanvien.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nhanvien)) {
            return false;
        }
        Nhanvien other = (Nhanvien) object;
        if ((this.idnhanvien == null && other.idnhanvien != null) || (this.idnhanvien != null && !this.idnhanvien.equals(other.idnhanvien))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ims.dto.Nhanvien[ idnhanvien=" + idnhanvien + " ]";
    }
    
}
