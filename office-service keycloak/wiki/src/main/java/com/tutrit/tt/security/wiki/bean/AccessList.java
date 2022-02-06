package com.tutrit.tt.security.wiki.bean;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccessList {
    @Id
    @GeneratedValue
    Long id;
    Long articleId;
    Long principalId;
}
