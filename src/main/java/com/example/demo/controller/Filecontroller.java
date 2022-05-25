package com.example.demo.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.demo.model.Filemodel;
import com.example.demo.repository.Filerepo;
import com.example.demo.service.Fileservice;


@Controller
public class Filecontroller {
	@Autowired 
	private Fileservice fileservice;
	
	@GetMapping("/")
	public String get(Model model) {
		List<Filemodel> fileupdate=fileservice.getFiles();
		model.addAttribute("fileupdate", fileupdate);
		return "filemodel";
	}
	@PostMapping("/uploadFiles")
	public String uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
		for(MultipartFile files1: files) {
			fileservice.saveFile(files1);
		}
		
		return "redirect:/";
	}

	@GetMapping("/downloadFile/{fileId}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable Integer fileId){
		Filemodel filemodel=fileservice.getFile(fileId).get();
		return ResponseEntity.ok()
				.contentType(MediaType.parseMediaType(filemodel.getFiletype()))
				.header(HttpHeaders.CONTENT_DISPOSITION,"attachment:filename=\""+filemodel.getFilename()+"\"")
				.body(new ByteArrayResource(filemodel.getData()));
	}

}


