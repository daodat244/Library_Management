package com.example.demo.service;

import com.example.demo.dto.TacGiaDTO;
import com.example.demo.dto.TacGiaThongKeDTO;
import com.example.demo.entity.TacGia;
import com.example.demo.repository.TacGiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TacGiaService {

    @Autowired
    private TacGiaRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^0\\d{9}$");

    public List<TacGiaDTO> getAll() {
        return repository.findAll().stream()
                .map(tacGia -> modelMapper.map(tacGia, TacGiaDTO.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public TacGiaDTO add(TacGiaDTO tacGiaDTO) {
        TacGia tacGia = modelMapper.map(tacGiaDTO, TacGia.class);
        validateTacGia(tacGia);
        if (tacGia.getEmail() != null && repository.existsByEmail(tacGia.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (tacGia.getSdt() != null && repository.existsBySdt(tacGia.getSdt())) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }
        TacGia savedTacGia = repository.save(tacGia);
        return modelMapper.map(savedTacGia, TacGiaDTO.class);
    }

    @Transactional
    public TacGiaDTO update(int matacgia, TacGiaDTO tacGiaDTO) {
        if (!repository.existsById(matacgia)) {
            throw new IllegalArgumentException("Tác giả không tồn tại");
        }
        TacGia tacGia = modelMapper.map(tacGiaDTO, TacGia.class);
        validateTacGia(tacGia);
        if (tacGia.getTentacgia() != null && repository.existsByTentacgiaAndMatacgiaNot(tacGia.getTentacgia().trim(), matacgia)) {
            throw new IllegalArgumentException("Tên tác giả đã tồn tại");
        }
        if (tacGia.getEmail() != null && repository.existsByEmailAndMatacgiaNot(tacGia.getEmail(), matacgia)) {
            throw new IllegalArgumentException("Email đã tồn tại");
        }
        if (tacGia.getSdt() != null && repository.existsBySdtAndMatacgiaNot(tacGia.getSdt(), matacgia)) {
            throw new IllegalArgumentException("Số điện thoại đã tồn tại");
        }
        tacGia.setMatacgia(matacgia);
        TacGia updatedTacGia = repository.save(tacGia);
        return modelMapper.map(updatedTacGia, TacGiaDTO.class);
    }

    @Transactional
    public void delete(int matacgia) {
        if (!repository.existsById(matacgia)) {
            throw new IllegalArgumentException("Tác giả không tồn tại");
        }
        repository.deleteById(matacgia);
    }

    public List<TacGiaDTO> search(String searchText, String criteria) {
        List<TacGia> tacGiaList;
        if (searchText.isEmpty()) {
            tacGiaList = repository.findAll();
        } else if (criteria.equals("Mã tác giả")) {
            try {
                int id = Integer.parseInt(searchText);
                Optional<TacGia> tacGia = repository.findById(id);
                tacGiaList = tacGia.map(List::of).orElse(List.of());
            } catch (NumberFormatException e) {
                tacGiaList = List.of();
            }
        } else {
            tacGiaList = repository.findByTentacgiaContainingIgnoreCase(searchText);
        }
        return tacGiaList.stream()
                .map(tacGia -> modelMapper.map(tacGia, TacGiaDTO.class))
                .collect(Collectors.toList());
    }

    public List<TacGiaThongKeDTO> getThongKeTacGia() {
        return repository.thongKeTacGia();
    }

    private void validateTacGia(TacGia tacGia) {
        if (tacGia.getTentacgia() == null || tacGia.getTentacgia().trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không được để trống");
        }
        if (!tacGia.getTentacgia().matches("^[a-zA-Z\\s\\p{L}]+$")) {
            throw new IllegalArgumentException("Tên tác giả chỉ được chứa chữ cái và khoảng trắng");
        }

        if (tacGia.getNamsinh() != null) {
            if (!String.valueOf(tacGia.getNamsinh()).matches("^[0-9]+$")) {
                throw new IllegalArgumentException("Năm sinh chỉ được chứa số");
            }
            if (tacGia.getNamsinh() <= 0 || tacGia.getNamsinh() > Year.now().getValue()) {
                throw new IllegalArgumentException("Năm sinh không hợp lệ, phải lớn hơn 0 và nhỏ hơn năm hiện tại");
            }
        }
    
        if (tacGia.getSdt() != null && !PHONE_PATTERN.matcher(tacGia.getSdt()).matches()) {
            throw new IllegalArgumentException("Số điện thoại phải có 10 chữ số và bắt đầu bằng 0");
        }
    
        if (tacGia.getEmail() != null && !EMAIL_PATTERN.matcher(tacGia.getEmail()).matches()) {
            throw new IllegalArgumentException("Email không hợp lệ");
        }
    
        if (tacGia.getQuequan() != null) {
            if (!tacGia.getQuequan().matches("^[a-zA-Z0-9\\s\\p{L}]+$")) {
                throw new IllegalArgumentException("Quê quán chỉ được chứa chữ cái, số và khoảng trắng");
            }
            if (tacGia.getQuequan().length() > 255) {
                throw new IllegalArgumentException("Quê quán không được vượt quá 255 ký tự");
            }
        }
    
        if (tacGia.getMota() != null && tacGia.getMota().length() > 1000) {
            throw new IllegalArgumentException("Mô tả không được vượt quá 1000 ký tự");
        }
    }
}
