package com.example.demo.service;

import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.model.Filemodel;
import com.example.demo.repository.Filerepo;

@Service
public class Fileservice {
	@Autowired
	private Filerepo filerepo;
	
	public Filemodel saveFile(MultipartFile file) {
		String filename=file.getOriginalFilename();
		try {
			Filemodel filemodel=new Filemodel(filename,file.getContentType(),file.getBytes());
			return filerepo.save(filemodel);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public Optional<Filemodel> getFile(Integer fileId) {
		return filerepo.findById(fileId);
	}
	public List<Filemodel> getFiles(){
		return filerepo.findAll();
	}

}


