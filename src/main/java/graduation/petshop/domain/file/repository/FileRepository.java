package graduation.petshop.domain.file.repository;

import graduation.petshop.domain.file.entity.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileInfo, Long> {
}