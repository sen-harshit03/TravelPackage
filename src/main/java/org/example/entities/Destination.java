package org.example.entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter @Setter @Builder @ToString
public class Destination {
    private String name;
    private List<Activity> activityList;
}
