package com.mpi.controller;

import com.mpi.model.BuyList;
import com.mpi.model.MPI;
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
    public void submitList(@ModelAttribute MPI mpi, Model model) {
        RequestController controller = new RequestController();
        BuyList buyList = controller.getBuyList(mpi.getTextArea());
        controller.saveFile(buyList, mpi.getCurrency());
        model.addAttribute("mpi", new MPI());
//        return "mpi";
    }
}
