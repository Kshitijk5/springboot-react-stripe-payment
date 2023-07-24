package com.ezbytes.SpringbootSecuritydemo.payload;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {

    private Long id;

    private String img;

    private String courseName;

    private String courseDescription;

    private String unitPrice;

}
