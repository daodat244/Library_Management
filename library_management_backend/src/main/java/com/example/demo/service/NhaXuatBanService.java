package com.example.demo.service;

import com.example.demo.dto.NhaXuatBanDTO;
import com.example.demo.dto.NhaXuatBanThongKeDTO;
import com.example.demo.entity.NhaXuatBan;
import com.example.demo.repository.NhaXuatBanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NhaXuatBanService {
    @Autowired
    private NhaXuatBanRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");

    public List<NhaXuatBanDTO> getAll() {
        return repository.findAll().stream()
                .map(nxb -> modelMapper.map(nxb, NhaXuatBanDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public NhaXuatBanDTO add(NhaXuatBanDTO nxbDTO) {
        NhaXuatBan nxb = modelMapper.map(nxbDTO, NhaXuatBan.class);
        validateNhaXuatBan(nxb);
        if (nxb.getTennxb() != null && repository.existsByTennxb(nxb.getTennxb().trim())) {
            throw new IllegalArgumentException("Tên NXB đã tồn tại");
        }
        if (nxb.getEmail() != null && repository.existsByEmail(nxb.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (nxb.getSdt() != null && repository.existsBySdt(nxb.getSdt())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }
        NhaXuatBan savedNxb = repository.save(nxb);
        return modelMapper.map(savedNxb, NhaXuatBanDTO.class);
    }

    @Transactional
    public NhaXuatBanDTO update(int manxb, NhaXuatBanDTO nxbDTO) {
        if (!repository.existsById(manxb)) {
            throw new IllegalArgumentException("Nhà xuất bản không tồn tại");
        }
        NhaXuatBan nxb = modelMapper.map(nxbDTO, NhaXuatBan.class);
        validateNhaXuatBan(nxb);
        if (nxb.getTennxb() != null && repository.existsByTennxbAndManxbNot(nxb.getTennxb().trim(), manxb)) {
            throw new IllegalArgumentException("Tên NXB đã tồn tại");
        }
        if (nxb.getEmail() != null && repository.existsByEmailAndManxbNot(nxb.getEmail(), manxb)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (nxb.getSdt() != null && repository.existsBySdtAndManxbNot(nxb.getSdt(), manxb)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }
        nxb.setManxb(manxb);
        NhaXuatBan updatedNxb = repository.save(nxb);
        return modelMapper.map(updatedNxb, NhaXuatBanDTO.class);
    }

    @Transactional
    public void delete(int manxb) {
        if (!repository.existsById(manxb)) {
            throw new IllegalArgumentException("Nhà xuất bản không tồn tại");
        }
        repository.deleteById(manxb);
    }

    public List<NhaXuatBanDTO> search(String searchText, String criteria) {
        List<NhaXuatBan> nxbList;
        if (searchText.isEmpty()) {
            nxbList = repository.findAll();
        } else if (criteria.equals("Mã NXB")) {
            try {
                int id = Integer.parseInt(searchText);
                Optional<NhaXuatBan> nxb = repository.findById(id);
                nxbList = nxb.map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                nxbList = List.of();
            }
        } else {
            nxbList = repository.findByTennxbContainingIgnoreCase(searchText);
        }
        return nxbList.stream()
                .map(nxb -> modelMapper.map(nxb, NhaXuatBanDTO.class))
                .collect(Collectors.toList());
    }

    public List<NhaXuatBanThongKeDTO> getThongKeNXB() {
        return repository.thongKeNXB();
    }

    private void validateNhaXuatBan(NhaXuatBan nxb) {
        if (nxb.getTennxb() == null || nxb.getTennxb().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên nhà xuất bản không được để trống");
        }
        if (nxb.getEmail() == null || !EMAIL_PATTERN.matcher(nxb.getEmail()).matches()) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
        if (nxb.getSdt() != null && !PHONE_PATTERN.matcher(nxb.getSdt()).matches()) {
            throw new IllegalArgumentException("Số điện thoại phải có 10 chữ số và bắt đầu bằng 0");
        }
        if (nxb.getDiachi() != null && nxb.getDiachi().length() > 255) {
            throw new IllegalArgumentException("Địa chỉ không được vượt quá 255 ký tự");
        }
    }
}