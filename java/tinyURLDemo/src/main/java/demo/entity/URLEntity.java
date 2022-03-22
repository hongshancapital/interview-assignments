package demo.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class URLEntity implements Serializable {
    @Id
    private Integer id;

    private String oriURL;

    private String curURL;

    private Long createTimeStamp;
}
