package com.school.commentmanager.controller;

import com.school.commentmanager.model.Picture;
import com.school.commentmanager.repo.PictureRepository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/picture")
public class PictureController {

    private final PictureRepository pictureRepository;

    public PictureController(PictureRepository pictureRepository) {
        this.pictureRepository = pictureRepository;
    }

    @GetMapping
    private Iterable<Picture> getAllPicturesDataAsFiles() {
        return pictureRepository.findAll();
    }

    @PostMapping
    private void uploadPicture(@RequestBody MultipartFile picture) throws IOException {
        pictureRepository.save(new Picture(picture.getBytes()));
    }

    @DeleteMapping("/{id}")
    private void deletePicture(@PathVariable("id") String id) {
        pictureRepository.deleteById(UUID.fromString(id));
    }
}
