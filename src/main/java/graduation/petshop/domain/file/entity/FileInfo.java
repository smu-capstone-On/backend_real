package graduation.petshop.domain.file.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import graduation.petshop.common.entity.Base;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FileInfo extends Base {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_info_id")
    private Long id;

    private String fileName;

    private String fileOriName;

    private String fileUrl;

    private String fileExt;

    private Long fileSize;
}
