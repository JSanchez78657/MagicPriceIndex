package com.mpi.controller;

import com.mpi.model.BuyList;
import com.mpi.model.MPI;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class HTMLController {

    @GetMapping("/mpi")
    public String mpiForm(Model model) {
        model.addAttribute("mpi", new MPI());
        return "mpi";
    }

    @PostMapping("/mpi")
    public ResponseEntity<String> submitList(@ModelAttribute MPI mpi, Model model) {
        RequestController controller = new RequestController();
        BuyList buyList = controller.getBuyList(mpi.getTextArea());
        HttpHeaders headers = new HttpHeaders();
        String csv = buyList.csvString(controller.getFormat(mpi.getCurrency()));
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=buylist.csv");
        model.addAttribute("mpi", new MPI());
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(csv.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(csv);
    }
}
