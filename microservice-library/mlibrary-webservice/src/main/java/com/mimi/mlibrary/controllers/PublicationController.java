package com.mimi.mlibrary.controllers;

import com.mimi.mlibrary.model.dto.publication.*;
import com.mimi.mlibrary.service.contract.PublicationService;
import com.mimi.mlibrary.exceptions.ResourceNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Publications")
public class PublicationController {

    private PublicationService publicationService;
    final static Logger logger  = LogManager.getLogger(PublicationController.class);


    public PublicationController(PublicationService publicationService){
        this.publicationService = publicationService;
    }


    @GetMapping( value = "/criterias" )
    public List<PublicationDto> getPublicationByCriteria( @RequestParam(required = false) String author,
                                                          @RequestParam(required = false) String title ,
                                                          @RequestParam(required = false) String category,
                                                          @RequestParam(required = false) String editor,
                                                          @RequestParam(required = false) int libId ) {


        List<PublicationDto> publicationDtos = publicationService.findAllByCriteria( author,  title, category, editor, libId );
        if( publicationDtos.isEmpty() ) throw new ResourceNotFoundException( "Cet ouvrage n'existe pas dans notre base de données." );

        return publicationDtos ;
    }

    @GetMapping( "/{id}" )
    public PublicationDto getPublicationsById( @PathVariable int id ) {
        PublicationDto publicationDto = publicationService.findPublicationById( id );
        if( publicationDto == null ) throw new ResourceNotFoundException( "Il n'y a pas d'ouvrage qui correspond à cette date." );

        return publicationDto;
    }

    @GetMapping
    public List<PublicationDto> getAllPublications() {
        List<PublicationDto> publicationDtos = publicationService.findAllPublications();
        if( publicationDtos.isEmpty() ) throw new ResourceNotFoundException( "Il n'y a pas d'ouvrage dans la base de données." );

        return publicationDtos ;
    }
}

