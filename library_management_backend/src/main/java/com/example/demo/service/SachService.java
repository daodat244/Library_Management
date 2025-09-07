package com.example.demo.service;

import com.example.demo.dto.SachDTO;
import com.example.demo.dto.SachInputDTO;
import com.example.demo.entity.Sach;
import com.example.demo.repository.ChiTietPhieuMuonRepository;
import com.example.demo.repository.SachRepository;
import com.example.demo.repository.TacGiaRepository;
import com.example.demo.repository.NhaXuatBanRepository;
import com.example.demo.repository.PhieuMuonRepository;
import com.example.demo.repository.TheLoaiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SachService {

    @Autowired
    private SachRepository repository;
    @Autowired
    private TacGiaRepository tacGiaRepository;
    @Autowired
    private NhaXuatBanRepository nhaXuatBanRepository;
    @Autowired
    private TheLoaiRepository theLoaiRepository;
    @Autowired
    private ChiTietPhieuMuonRepository chiTietPhieuMuonRepository;
    @Autowired
    private PhieuMuonRepository phieuMuonRepository;

    public List<SachDTO> getAllSach() {
        return repository.findAll().stream()
                .map(sach -> new SachDTO(
                        sach.getMasach(),
                        sach.getTensach(),
                        sach.getTacGia().getMatacgia(),
                        sach.getTacGia().getTentacgia(),
                        sach.getNhaXuatBan().getManxb(),
                        sach.getNhaXuatBan().getTennxb(),
                        sach.getTheLoai().getMatheloai(),
                        sach.getTheLoai().getTentheloai(),
                        sach.getNamxb(),
                        sach.getSotrang(),
                        sach.getSoluong()))
                .collect(Collectors.toList());
    }

    public SachDTO getSachById(String masach) {
        Sach sach = repository.findById(masach)
                .orElseThrow(() -> new IllegalArgumentException("Sách không tồn tại"));
        return new SachDTO(
                sach.getMasach(),
                sach.getTensach(),
                sach.getTacGia().getMatacgia(),
                sach.getTacGia().getTentacgia(),
                sach.getNhaXuatBan().getManxb(),
                sach.getNhaXuatBan().getTennxb(),
                sach.getTheLoai().getMatheloai(),
                sach.getTheLoai().getTentheloai(),
                sach.getNamxb(),
                sach.getSotrang(),
                sach.getSoluong());
    }

    public SachDTO addSach(Sach sach) {
        if (repository.existsById(sach.getMasach())) {
            throw new IllegalArgumentException("Mã sách đã tồn tại");
        }
        validateSach(sach, true);
        Sach savedSach = repository.save(sach);
        return new SachDTO(
                savedSach.getMasach(),
                savedSach.getTensach(),
                savedSach.getTacGia().getMatacgia(),
                savedSach.getTacGia().getTentacgia(),
                savedSach.getNhaXuatBan().getManxb(),
                savedSach.getNhaXuatBan().getTennxb(),
                savedSach.getTheLoai().getMatheloai(),
                savedSach.getTheLoai().getTentheloai(),
                savedSach.getNamxb(),
                savedSach.getSotrang(),
                savedSach.getSoluong());
    }

    public SachDTO updateSach(String masach, SachInputDTO sachInput) {
        Sach existingSach = repository.findById(masach)
                .orElseThrow(() -> new IllegalArgumentException("Sách không tồn tại"));
        Sach sach = convertToEntity(sachInput);
        sach.setMasach(masach); // Gán masach từ @PathVariable
        validateSach(sach, false);
        existingSach.setTensach(sach.getTensach());
        existingSach.setTacGia(tacGiaRepository.findById(sachInput.getMatacgia())
                .orElseThrow(() -> new IllegalArgumentException("Tác giả không tồn tại")));
        existingSach.setNhaXuatBan(nhaXuatBanRepository.findById(sachInput.getManxb())
                .orElseThrow(() -> new IllegalArgumentException("Nhà xuất bản không tồn tại")));
        existingSach.setTheLoai(theLoaiRepository.findById(sachInput.getMatheloai())
                .orElseThrow(() -> new IllegalArgumentException("Thể loại không tồn tại")));
        existingSach.setNamxb(sach.getNamxb());
        existingSach.setSotrang(sach.getSotrang());
        existingSach.setSoluong(sach.getSoluong());
        Sach updatedSach = repository.save(existingSach);
        return new SachDTO(
                updatedSach.getMasach(),
                updatedSach.getTensach(),
                updatedSach.getTacGia().getMatacgia(),
                updatedSach.getTacGia().getTentacgia(),
                updatedSach.getNhaXuatBan().getManxb(),
                updatedSach.getNhaXuatBan().getTennxb(),
                updatedSach.getTheLoai().getMatheloai(),
                updatedSach.getTheLoai().getTentheloai(),
                updatedSach.getNamxb(),
                updatedSach.getSotrang(),
                updatedSach.getSoluong());
    }

    public void deleteSach(String masach) {
        if (!repository.existsById(masach)) {
            throw new IllegalArgumentException("Sách không tồn tại");
        }
        repository.deleteById(masach);
    }

    public SachDTO giamSoLuongSach(String masach) {
        Sach sach = repository.findById(masach)
                .orElseThrow(() -> new IllegalArgumentException("Sách không tồn tại"));
        if (sach.getSoluong() <= 0) {
            throw new IllegalArgumentException("Số lượng sách đã bằng 0");
        }
        sach.setSoluong(sach.getSoluong() - 1);
        Sach updatedSach = repository.save(sach);
        return new SachDTO(
                updatedSach.getMasach(),
                updatedSach.getTensach(),
                updatedSach.getTacGia().getMatacgia(),
                updatedSach.getTacGia().getTentacgia(),
                updatedSach.getNhaXuatBan().getManxb(),
                updatedSach.getNhaXuatBan().getTennxb(),
                updatedSach.getTheLoai().getMatheloai(),
                updatedSach.getTheLoai().getTentheloai(),
                updatedSach.getNamxb(),
                updatedSach.getSotrang(),
                updatedSach.getSoluong());
    }

    public SachDTO tangSoLuongSach(String masach) {
        Sach sach = repository.findById(masach)
                .orElseThrow(() -> new IllegalArgumentException("Sách không tồn tại"));
        sach.setSoluong(sach.getSoluong() + 1);
        Sach updatedSach = repository.save(sach);
        return new SachDTO(
                updatedSach.getMasach(),
                updatedSach.getTensach(),
                updatedSach.getTacGia().getMatacgia(),
                updatedSach.getTacGia().getTentacgia(),
                updatedSach.getNhaXuatBan().getManxb(),
                updatedSach.getNhaXuatBan().getTennxb(),
                updatedSach.getTheLoai().getMatheloai(),
                updatedSach.getTheLoai().getTentheloai(),
                updatedSach.getNamxb(),
                updatedSach.getSotrang(),
                updatedSach.getSoluong());
    }

    public List<SachDTO> search(String searchText, String criteria) {
        System.out.println("Backend search: searchText=[" + searchText + "], criteria=[" + criteria + "]");
        List<Sach> sachList;
        if (searchText == null || searchText.trim().isEmpty()) {
            System.out.println("Search text is empty or null, returning all books");
            sachList = repository.findAll();
        } else {
            switch (criteria) {
                case "Mã sách":
                    System.out.println("Searching by Mã sách: " + searchText);
                    Optional<Sach> sach = repository.findById(searchText);
                    sachList = sach.map(List::of).orElse(List.of());
                    System.out.println("Found " + sachList.size() + " books by Mã sách");
                    break;
                case "Tên sách":
                    System.out.println("Searching by Tên sách: " + searchText);
                    sachList = repository.findByTensachContainingIgnoreCase(searchText);
                    System.out.println("Found " + sachList.size() + " books by Tên sách");
                    break;
                case "Tác giả":
                    System.out.println("Searching by Tác giả: " + searchText);
                    sachList = repository.findByTacGia_TentacgiaIgnoreCaseContaining(searchText);
                    System.out.println("Found " + sachList.size() + " books by Tác giả");
                    break;
                case "NXB":
                    System.out.println("Searching by NXB: " + searchText);
                    sachList = repository.findByNhaXuatBan_TennxbContainingIgnoreCase(searchText);
                    System.out.println("Found " + sachList.size() + " books by NXB");
                    break;
                case "Thể loại":
                    System.out.println("Searching by Thể loại: " + searchText);
                    sachList = repository.findByTheLoai_TentheloaiContainingIgnoreCase(searchText);
                    System.out.println("Found " + sachList.size() + " books by Thể loại");
                    break;
                default:
                    System.out.println("Invalid criteria: " + criteria + ", returning empty list");
                    sachList = List.of();
            }
        }
        List<SachDTO> result = sachList.stream()
                .map(sach -> new SachDTO(
                sach.getMasach(),
                sach.getTensach(),
                sach.getTacGia().getMatacgia(),
                sach.getTacGia().getTentacgia(),
                sach.getNhaXuatBan().getManxb(),
                sach.getNhaXuatBan().getTennxb(),
                sach.getTheLoai().getMatheloai(),
                sach.getTheLoai().getTentheloai(),
                sach.getNamxb(),
                sach.getSotrang(),
                sach.getSoluong()))
                .collect(Collectors.toList());
        System.out.println("Returning " + result.size() + " books after mapping to DTO");
        return result;
    }

    public List<SachDTO> getSachByIds(String[] masachArray) {
        List<Sach> sachList = repository.findAllById(Arrays.asList(masachArray));
        return sachList.stream()
                .map(sach -> new SachDTO(
                sach.getMasach(),
                sach.getTensach(),
                sach.getTacGia().getMatacgia(),
                sach.getTacGia().getTentacgia(),
                sach.getNhaXuatBan().getManxb(),
                sach.getNhaXuatBan().getTennxb(),
                        sach.getTheLoai().getMatheloai(),
                        sach.getTheLoai().getTentheloai(),
                        sach.getNamxb(),
                        sach.getSotrang(),
                        sach.getSoluong()))
                .collect(Collectors.toList());
    }

    private void validateSach(Sach sach, boolean isAdd) {
        // Kiểm tra các trường bắt buộc
        if (sach.getMasach() == null || sach.getMasach().trim().isEmpty()) {
            throw new IllegalArgumentException("Mã sách không được để trống");
        }
        if (sach.getTensach() == null || sach.getTensach().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không được để trống");
        }
        if (sach.getSoluong() == null) {
            throw new IllegalArgumentException("Số lượng không được để trống");
        }

        // Kiểm tra định dạng mã sách (bắt đầu bằng 'S' và 3 chữ số)
        if (!sach.getMasach().matches("^S\\d{3}$")) {
            throw new IllegalArgumentException("Mã sách phải bắt đầu bằng 'S' và theo sau là 3 chữ số (ví dụ: S001)");
        }
        
        // Kiểm tra số lượng
        if (sach.getSoluong() <= 0) {
            throw new IllegalArgumentException("Số lượng phải lớn hơn 0");
        }

        // Kiểm tra năm xuất bản (nếu có)
        if (sach.getNamxb() != null) {
            if (sach.getNamxb() <= 1975 || sach.getNamxb() > Year.now().getValue()) {
                throw new IllegalArgumentException("Năm xuất bản phải lớn hơn 1975 và nhỏ hơn hoặc bằng năm hiện tại (" + Year.now().getValue() + ")");
            }
        }

        // Kiểm tra số trang (nếu có)
        if (sach.getSotrang() != null && sach.getSotrang() <= 0) {
            throw new IllegalArgumentException("Số trang phải lớn hơn 0");
        }

        // Kiểm tra mã sách đã tồn tại (chỉ khi thêm mới)
        if (isAdd && repository.existsById(sach.getMasach())) {
            throw new IllegalArgumentException("Mã sách đã tồn tại");
        }
    }

    public Sach convertToEntity(SachInputDTO dto) {
        Sach sach = new Sach();
        sach.setMasach(dto.getMasach());
        sach.setTensach(dto.getTensach());
        sach.setTacGia(tacGiaRepository.findById(dto.getMatacgia())
                .orElseThrow(() -> new IllegalArgumentException("Tác giả không tồn tại")));
        sach.setNhaXuatBan(nhaXuatBanRepository.findById(dto.getManxb())
                .orElseThrow(() -> new IllegalArgumentException("Nhà xuất bản không tồn tại")));
        sach.setTheLoai(theLoaiRepository.findById(dto.getMatheloai())
                .orElseThrow(() -> new IllegalArgumentException("Thể loại không tồn tại")));
        sach.setNamxb(dto.getNamxb());
        sach.setSotrang(dto.getSotrang());
        sach.setSoluong(dto.getSoluong());
        return sach;
    }

    public Long getSoLuongDauSach() {
        return repository.countSoLuongDauSach();
    }

    public Long getTongSoLuongSach() {
        return repository.sumTongSoLuongSach();
    }

    public Long getTongSoLuongSachDangMuon() {
        Long result = chiTietPhieuMuonRepository.sumSoLuongSachDangMuon("Chưa trả");
        return result != null ? result : 0L;
    }

    public Long getTongSoLuongSachConTrongKho() {
        Long tongSoLuongSach = getTongSoLuongSach();
        Long tongSoLuongSachDangMuon = getTongSoLuongSachDangMuon();
        return tongSoLuongSach != null ? (tongSoLuongSach - tongSoLuongSachDangMuon) : 0L;
    }

    public List<SachDTO> getSachDuocMuonNhieuNhat() {
        return repository.findSachDuocMuonNhieuNhat().stream()
                .map(sach -> new SachDTO(
                        sach.getMasach(),
                        sach.getTensach(),
                        sach.getTacGia().getMatacgia(),
                        sach.getTacGia().getTentacgia(),
                        sach.getNhaXuatBan().getManxb(),
                        sach.getNhaXuatBan().getTennxb(),
                        sach.getTheLoai().getMatheloai(),
                        sach.getTheLoai().getTentheloai(),
                        sach.getNamxb(),
                        sach.getSotrang(),
                        sach.getSoluong()))
                .collect(Collectors.toList());
    }
}