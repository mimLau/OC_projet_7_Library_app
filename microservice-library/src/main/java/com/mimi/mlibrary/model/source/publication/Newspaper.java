package com.mimi.mlibrary.model.source.publication;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@DiscriminatorValue("NewsPaper")
public class Newspaper extends Publication implements Serializable {

}
