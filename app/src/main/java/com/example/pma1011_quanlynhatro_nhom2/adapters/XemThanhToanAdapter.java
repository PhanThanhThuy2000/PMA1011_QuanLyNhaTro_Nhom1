package com.example.pma1011_quanlynhatro_nhom2.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pma1011_quanlynhatro_nhom2.R;
import com.example.pma1011_quanlynhatro_nhom2.daos.HopDongDAO;
import com.example.pma1011_quanlynhatro_nhom2.daos.PhiDichVuDAO;
import com.example.pma1011_quanlynhatro_nhom2.daos.PhongDAO;
import com.example.pma1011_quanlynhatro_nhom2.daos.ThanhToanDAO;
import com.example.pma1011_quanlynhatro_nhom2.daos.ThongBaoDAO;
import com.example.pma1011_quanlynhatro_nhom2.models.HopDong;
import com.example.pma1011_quanlynhatro_nhom2.models.PhiDichVu;
import com.example.pma1011_quanlynhatro_nhom2.models.Phong;
import com.example.pma1011_quanlynhatro_nhom2.models.ThanhToan;
import com.example.pma1011_quanlynhatro_nhom2.models.User;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class XemThanhToanAdapter extends RecyclerView.Adapter<XemThanhToanAdapter.ViewHolder>{

    private final Context context;
    private ArrayList<ThanhToan> listThanhToan;
    private ThanhToanDAO thanhToanDAO;
    private HopDongDAO hopDongDAO;
    private PhongDAO phongDAO;
    private PhiDichVuDAO phiDichVuDAO;
    private ThongBaoDAO thongBaoDAO;

    public XemThanhToanAdapter(Context context, ArrayList<ThanhToan> listThanhToan) {
        this.context = context;
        this.listThanhToan = listThanhToan;
        this.thanhToanDAO = new ThanhToanDAO(context);
        this.hopDongDAO = new HopDongDAO(context);
        this.phongDAO = new PhongDAO(context);
        this.phiDichVuDAO = new PhiDichVuDAO(context);
        this.thongBaoDAO = new ThongBaoDAO(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_thanhtoan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ThanhToan thanhToan = listThanhToan.get(position);

        HopDong hopDong = hopDongDAO.getHopDongByHopDongID(thanhToan.getHopDongId());

        User nguoiThue = thanhToanDAO.getNguoiThueByHopDongID(thanhToan.getHopDongId());
        holder.tvNguoiThue.setText("Khách thuê: "+nguoiThue.getHoTen());
        holder.tvSDT.setText("SĐT: "+nguoiThue.getSdt());

        Phong phong = phongDAO.getPhongByPhongID(hopDong.getPhongId());
        holder.tvPhong.setText("Phòng: "+phong.getSoPhong());
        holder.tvHopDongID.setText("Hợp đồng: "+thanhToan.getHopDongId());
        //Tiền dịch vụ
        PhiDichVu phiDichVu = phiDichVuDAO.getPhiDichVuByID(hopDong.getPhiDichVuId());
        holder.tvTienDien.setText("Tiền điện: "+phiDichVu.getDonGiaDien()*thanhToan.getSoDien() + " đồng");
        holder.tvTienNuoc.setText("Tiền nước: "+phiDichVu.getDonGiaNuoc()*thanhToan.getSoNuoc() + " đồng");
        holder.tvTienVeSinh.setText("Vệ sinh chung: "+phiDichVu.getVeSinh()+ " đồng");
        holder.tvTienGuiXe.setText("Gửi xe: "+phiDichVu.getGuiXe()+ " đồng");
        holder.tvTienInternet.setText("Internet: "+phiDichVu.getInternet()+ " đồng");
        holder.tvThangMay.setText("Thang máy: "+phiDichVu.getThangMay()+ " đồng");
        holder.tvTienPhong.setText("Tiền phòng: "+phong.getGiaThue()+ " đồng");
        holder.tvTrangThaiThanhToan.setText(thanhToan.getTrangThaiThanhToan() == 0? "Chưa thanh toán" : "Đã thanh toán");
        holder.tvTrangThaiThanhToan.setTextColor(thanhToan.getTrangThaiThanhToan() == 0 ? context.getResources().getColor(R.color.red) : context.getResources().getColor(R.color.green));
        holder.tvTongTien.setText("Tổng tiền: "+thanhToan.getTongTien()+" đồng");
        holder.tvNgayTao.setText("Ngày tạo: "+formatDate(thanhToan.getNgayTao()));
    }

    @Override
    public int getItemCount() {
        return listThanhToan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvNguoiThue, tvSDT, tvPhong, tvHopDongID, tvTienDien,
                tvTienNuoc, tvTienVeSinh, tvTienGuiXe, tvTienInternet, tvThangMay,
                tvTienPhong, tvTrangThaiThanhToan, tvTongTien, tvNgayTao;
        MaterialButton btnDoiTrangThai, btnXoa;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNguoiThue = itemView.findViewById(R.id.tvNguoiThue);
            tvSDT = itemView.findViewById(R.id.tvSDT);
            tvPhong = itemView.findViewById(R.id.tvPhong);
            tvHopDongID = itemView.findViewById(R.id.tvHopDongID);
            tvTienDien = itemView.findViewById(R.id.tvTienDien);
            tvTienNuoc = itemView.findViewById(R.id.tvTienNuoc);
            tvTienVeSinh = itemView.findViewById(R.id.tvTienVeSinh);
            tvTienGuiXe = itemView.findViewById(R.id.tvTienGuiXe);
            tvTienInternet = itemView.findViewById(R.id.tvTienInternet);
            tvThangMay = itemView.findViewById(R.id.tvThangMay);
            tvTienPhong = itemView.findViewById(R.id.tvTienPhong);
            tvTrangThaiThanhToan = itemView.findViewById(R.id.tvTrangThaiThanhToan);
            tvTongTien = itemView.findViewById(R.id.tvTongTien);
            tvNgayTao = itemView.findViewById(R.id.tvNgayTao);

            btnDoiTrangThai = itemView.findViewById(R.id.btnDoiTrangThai);
            btnXoa = itemView.findViewById(R.id.btnXoa);
            btnDoiTrangThai.setVisibility(View.GONE);
            btnXoa.setVisibility(View.GONE);
        }
    }

    // Hàm formatDate để chuyển đổi ngày sang định dạng ngày/tháng/năm
    private String formatDate(String timestampString) {
        try {
            long timestamp = Long.parseLong(timestampString);
            Date date = new Date(timestamp);
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", java.util.Locale.getDefault());
            return dateFormat.format(date);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "Ngày không hợp lệ";
        }
    }
}
