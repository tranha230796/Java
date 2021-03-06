/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ims.gui;

import ims.bll.DantocBLL;
import ims.bll.DoiBLL;
import ims.bll.GiadinhBLL;
import ims.bll.NhanvienBLL;
import ims.bll.PhongbanBLL;
import ims.bll.QuoctichBLL;
import ims.bll.TongiaoBLL;
import ims.bll.TophongBLL;
import ims.bll.XahoiBLL;
import ims.dto.Dantoc;
import ims.dto.Doi;
import ims.dto.Giadinh;
import ims.dto.Nhanvien;
import ims.dto.Nhanvien_;
import ims.dto.Noisinhsong;
import ims.dto.Phongban;
import ims.dto.Quoctich;
import ims.dto.Tongiao;
import ims.dto.Tophong;
import ims.dto.Xahoi;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author NAT
 */
public class Employee extends javax.swing.JFrame implements WindowListener{

    private void activeForm(boolean b) {
         cbhonnhan.setEnabled(isDisplayable());
    }

    private void cleanForm() {
         //To change body of generated methods, choose Tools | Templates.
    }

    private void validForm() {
         //To change body of generated methods, choose Tools | Templates.
    }
    
    public static enum ACTION {INSERT, EDIT, VIEW};
    private ACTION action;
    
    private List<Tophong> listtophong;
    private List<Tongiao> listtongiao;
    private List<Xahoi> listxahoi;
    private List<Quoctich> listquoctich;
    private List<Phongban> listphongban;
    private List<Giadinh> listgiadinh;
    private List<Dantoc> listdantoc;
    private List<Doi> listdoi;
    private List<Nhanvien> listnhanvien;
    private List<Noisinhsong> listnoisinhsong;
    private Nhanvien nhanvien;

    /**
     * Creates new form Employee
     */
    public Employee() throws Exception {
        initComponents();
        stateButton(true);
         setInfoDialog();
    }
    public  void setInfoDialog() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);        
        setResizable(false);
    }
    @Override
    public void dispose() {
        super.dispose();
    }
    public void stateButton(boolean value){
        
        btNew.setVisible(value);
        btEdit.setVisible(value);
        btDelete.setVisible(value);
        btPrint.setVisible(value);
        btClose.setVisible(value);
        btSave.setVisible(!value);
        btCancel.setVisible(!value);
        
    }
    public void setNhanvien(Nhanvien nhanvien){
        this.nhanvien = nhanvien;
        tfma.setText(String.valueOf(nhanvien.getIdnhanvien()));
        tfhoten.setText(nhanvien.getHoten());
        tfthuongtru.setText(String.valueOf(nhanvien.getIdnoisinhsong()));
        tfnoio.setText(String.valueOf(nhanvien.getIdnoisinhsong()));
        tfnoisinh.setText(String.valueOf(nhanvien.getIdnoisinhsong()));
        tfnguyenquan.setText(String.valueOf(nhanvien.getIdnoisinhsong()));
        tfcmnd.setText(String.valueOf(nhanvien.getSocmnd()));
        tfngaycap.setText(nhanvien.getNgaycap().toString());
        tfnoicap.setText(nhanvien.getNoicap());
        tfnhandang.setText(nhanvien.getNhandang());
        tfdienthoai.setText(String.valueOf(nhanvien.getDienthoai()));
        tfdtdd.setText(String.valueOf(nhanvien.getDtdd()));
        tfemail.setText(String.valueOf(nhanvien.getEmail()));
        cbdantoc.setSelectedItem(nhanvien.getIddantoc());
        cbphong.setSelectedItem(nhanvien.getIdphongban());
        cbdoi.setSelectedItem(nhanvien.getMadoi());
        cbquoctich.setSelectedItem(nhanvien.getIdquoctich());
        cbto.setSelectedItem(nhanvien.getMato());
        cbtongiao.setSelectedItem(nhanvien.getIdtongiao());
        cbgiadinh.setSelectedItem(nhanvien.getIdgiadinh());
        cbxahoi.setSelectedItem(nhanvien.getIdxahoi());
    }
    public void setListDoi(List<Doi> listdoi){
        this.listdoi = listdoi;
        for(Doi doi:listdoi){
            cbdoi.addItem(String.valueOf(doi));
        }
    }
    public void setListDantoc(List<Dantoc> listdantoc){
        this.listdantoc = listdantoc;
        for (Dantoc dantoc : listdantoc) {
			cbdantoc.addItem(dantoc.toString()); 
		}
        
    }
    public void setListTongiao(List<Tongiao> listtongiao){
        this.listtongiao = listtongiao;
        for (Tongiao tongiao : listtongiao) {
			cbtongiao.addItem(tongiao.toString()); 
		}
        
    }
    
    public void setListQuoctich(List<Quoctich> listquoctich){
        this.listquoctich = listquoctich;
        for (Quoctich quoctich : listquoctich) {
			cbquoctich.addItem(quoctich.toString()); 
		} 
    }
    public void setListPhongban(List<Phongban> listphongban){
        this.listphongban = listphongban;
        for(Phongban phongban : listphongban){
            cbphong.addItem(phongban.toString());
        }
    }
    public void setListNhanvien(List<Nhanvien> listnhanvien){
        this.listnhanvien = listnhanvien;
        for(Nhanvien nhanvien : listnhanvien){
            cbgioitinh.addItem(String.valueOf(nhanvien));
        }
    }
    public void setListTophong(List<Tophong> listtophong){
        this.listtophong = listtophong;
        for(Tophong tophong : listtophong){
            cbto.addItem(String.valueOf(tophong));
        }
    }
    public void setListGiadinh(List<Giadinh> listgiadinh){
        this.listgiadinh = listgiadinh;
        for(Giadinh giadinh : listgiadinh){
            cbgiadinh.addItem(giadinh.toString());
        }
    }
    public void setListXahoi(List<Xahoi> listxahoi){
        this.listxahoi = listxahoi;
        for(Xahoi xahoi : listxahoi){
            cbxahoi.addItem(xahoi.toString());
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        btNew = new javax.swing.JButton();
        btEdit = new javax.swing.JButton();
        btDelete = new javax.swing.JButton();
        btPrint = new javax.swing.JButton();
        btClose = new javax.swing.JButton();
        btSave = new javax.swing.JButton();
        btCancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        tfma = new javax.swing.JTextField();
        tfhoten = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cbphong = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cbdoi = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        cbto = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        cbquoctich = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        cbdantoc = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        cbtongiao = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        cbgioitinh = new javax.swing.JComboBox<>();
        cbhonnhan = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        tfnoio = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        tfthuongtru = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        tfnoisinh = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        tfnguyenquan = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        tfcmnd = new javax.swing.JTextField();
        tfngaycap = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tfnoicap = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        cbgiadinh = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        cbxahoi = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jComboBox11 = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        tfchieucao = new javax.swing.JTextField();
        tfnhandang = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        tfcannang = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jComboBox12 = new javax.swing.JComboBox<>();
        jLabel27 = new javax.swing.JLabel();
        tfbenhly = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        tfdienthoai = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        tfdtdd = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        tfemail = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        tfghichu = new javax.swing.JTextField();

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        btNew.setText("Thêm");
        btNew.setPreferredSize(new java.awt.Dimension(75, 23));
        btNew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNewActionPerformed(evt);
            }
        });

        btEdit.setText("Sửa");
        btEdit.setPreferredSize(new java.awt.Dimension(75, 23));
        btEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btEditActionPerformed(evt);
            }
        });

        btDelete.setText("Xóa");
        btDelete.setPreferredSize(new java.awt.Dimension(75, 23));

        btPrint.setText("In");
        btPrint.setPreferredSize(new java.awt.Dimension(75, 23));

        btClose.setText("Thoát");
        btClose.setPreferredSize(new java.awt.Dimension(75, 23));
        btClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCloseActionPerformed(evt);
            }
        });

        btSave.setText("Ghi");
        btSave.setPreferredSize(new java.awt.Dimension(75, 23));
        btSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSaveActionPerformed(evt);
            }
        });

        btCancel.setText("Hủy");
        btCancel.setPreferredSize(new java.awt.Dimension(75, 23));
        btCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelActionPerformed(evt);
            }
        });

        jLabel1.setText("Mã NV");

        jLabel2.setText("Họ tên");

        jLabel3.setText("Phòng ban");

        cbphong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbphongActionPerformed(evt);
            }
        });

        jLabel5.setText("Giới tính");

        jLabel6.setText("Đội");

        jLabel8.setText("Tổ");

        jLabel9.setText("Quốc tịch");

        jLabel10.setText("Dân tộc");

        jLabel11.setText("Tôn giáo");

        jLabel12.setText("Hôn nhân");

        jLabel13.setText("Nơi ở hiện tại");

        jLabel14.setText("Thường trú");

        jLabel15.setText("Nơi sinh");

        jLabel16.setText("Nguyên quán");

        jLabel17.setText("Số CMND");

        jLabel19.setText("Ngày cấp");

        jLabel18.setText("Nơi cấp");

        jLabel20.setText("TP.Gia đình");

        jLabel21.setText("TP.Xã hội");

        jLabel22.setText("Nhận dạng");

        jLabel23.setText("Nhóm máu");

        jComboBox11.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "O", "A", "AB" }));

        jLabel24.setText("Chiều cao");

        jLabel25.setText("Cân nặng");

        jLabel26.setText("Loại sức khỏe");

        jComboBox12.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " ", "Tốt", "Không Tốt", " " }));
        jComboBox12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox12ActionPerformed(evt);
            }
        });

        jLabel27.setText("Bệnh lý");

        jLabel28.setText("Điện thoạt");

        tfdienthoai.setText(" ");

        jLabel29.setText("DTDD");

        jLabel30.setText("Email");

        tfemail.setToolTipText("");

        jLabel31.setText("Ghi chú");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 356, Short.MAX_VALUE)
                                .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel28)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel2))
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(tfhoten)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cbquoctich, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(cbdantoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cbtongiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(tfcmnd, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel19)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(tfngaycap)
                                                    .addComponent(cbxahoi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(tfdienthoai, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                                        .addComponent(jLabel24))
                                                    .addComponent(cbgiadinh, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addComponent(jLabel21)
                                                        .addGap(0, 232, Short.MAX_VALUE))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                            .addComponent(jLabel29)
                                                            .addComponent(tfchieucao, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(layout.createSequentialGroup()
                                                                .addComponent(jLabel25)
                                                                .addGap(12, 12, 12)
                                                                .addComponent(tfcannang))
                                                            .addComponent(tfdtdd))))))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel26))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addGap(9, 9, 9)
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(jLabel18)
                                                            .addComponent(jLabel22)))
                                                    .addGroup(layout.createSequentialGroup()
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jLabel30)))
                                                .addGap(17, 17, 17))))
                                    .addComponent(tfnoio)
                                    .addComponent(tfthuongtru)
                                    .addComponent(tfnoisinh)
                                    .addComponent(tfnguyenquan)
                                    .addComponent(tfma)
                                    .addComponent(cbgioitinh, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel12))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cbhonnhan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbto, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbdoi, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(cbphong, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(tfnoicap, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(tfnhandang)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 44, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel27)
                                .addGap(18, 18, 18)
                                .addComponent(tfbenhly))
                            .addComponent(tfemail)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel31)
                        .addGap(53, 53, 53)
                        .addComponent(tfghichu)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(tfma, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(cbphong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbdoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(tfhoten, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cbto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(cbgioitinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cbquoctich, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(cbdantoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cbtongiao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(cbhonnhan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(tfnoio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tfthuongtru, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(tfnoisinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(tfnguyenquan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(tfcmnd, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tfngaycap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel18)
                    .addComponent(tfnoicap, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(cbgiadinh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21)
                    .addComponent(cbxahoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22)
                    .addComponent(tfnhandang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBox11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(tfchieucao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(tfcannang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(jComboBox12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27)
                    .addComponent(tfbenhly, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(tfdienthoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(tfdtdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(tfemail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel31)
                    .addComponent(tfghichu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btPrint, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btClose, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btEdit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btCancel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btSave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btNewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNewActionPerformed
        stateButton(false);
        cleanForm();
        activeForm(true);
        action = ACTION.INSERT;
    }//GEN-LAST:event_btNewActionPerformed

    private void btSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSaveActionPerformed
        stateButton(true);
          validForm();
            setNhanvien(nhanvien);
            if (action == ACTION.INSERT) {
            try {
                new NhanvienBLL().create(nhanvien);
            } catch (Exception ex) {
                Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
            }
                listnhanvien.add(nhanvien);
          
    }//GEN-LAST:event_btSaveActionPerformed
    }
    private void btEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btEditActionPerformed
        stateButton(false);
        activeForm(true);
        tfma.setEditable(false);
        action = ACTION.EDIT;
    }//GEN-LAST:event_btEditActionPerformed

    private void btCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelActionPerformed
         stateButton(true);
         activeForm(false);
         dispose();
    }//GEN-LAST:event_btCancelActionPerformed

    private void jComboBox12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox12ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox12ActionPerformed

    private void cbphongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbphongActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbphongActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        // TODO add your handling code here:
        try {
            
            setListXahoi(new XahoiBLL().listAll());
            setListGiadinh(new GiadinhBLL().listAll());
            setListTongiao(new TongiaoBLL().listAll());
            setListTophong(new TophongBLL().listAll());
            setListPhongban(new PhongbanBLL().listAll());
            setListQuoctich(new QuoctichBLL().listAll());
            setListDoi(new DoiBLL().listAll());
            setListDantoc(new DantocBLL().listAll());
        } catch (Exception ex) {
            Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowOpened

    private void btCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCloseActionPerformed
                dispose();
    }//GEN-LAST:event_btCloseActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Employee.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new Employee().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(Employee.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btCancel;
    private javax.swing.JButton btClose;
    private javax.swing.JButton btDelete;
    private javax.swing.JButton btEdit;
    private javax.swing.JButton btNew;
    private javax.swing.JButton btPrint;
    private javax.swing.JButton btSave;
    private javax.swing.JComboBox<String> cbdantoc;
    private javax.swing.JComboBox<String> cbdoi;
    private javax.swing.JComboBox<String> cbgiadinh;
    private javax.swing.JComboBox<String> cbgioitinh;
    private javax.swing.JComboBox<String> cbhonnhan;
    private javax.swing.JComboBox<String> cbphong;
    private javax.swing.JComboBox<String> cbquoctich;
    private javax.swing.JComboBox<String> cbto;
    private javax.swing.JComboBox<String> cbtongiao;
    private javax.swing.JComboBox<String> cbxahoi;
    private javax.swing.JComboBox<String> jComboBox11;
    private javax.swing.JComboBox<String> jComboBox12;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField tfbenhly;
    private javax.swing.JTextField tfcannang;
    private javax.swing.JTextField tfchieucao;
    private javax.swing.JTextField tfcmnd;
    private javax.swing.JTextField tfdienthoai;
    private javax.swing.JTextField tfdtdd;
    private javax.swing.JTextField tfemail;
    private javax.swing.JTextField tfghichu;
    private javax.swing.JTextField tfhoten;
    private javax.swing.JTextField tfma;
    private javax.swing.JTextField tfngaycap;
    private javax.swing.JTextField tfnguyenquan;
    private javax.swing.JTextField tfnhandang;
    private javax.swing.JTextField tfnoicap;
    private javax.swing.JTextField tfnoio;
    private javax.swing.JTextField tfnoisinh;
    private javax.swing.JTextField tfthuongtru;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        
}
    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }
}
