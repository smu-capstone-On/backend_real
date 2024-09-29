package graduation.petshop.domain.waltkmate.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class WalkMate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long memberId;
    @Enumerated(value = EnumType.STRING)
    private SexType sexType;
    private int age;
    private Boolean hasPet;

    private BigDecimal latitude; // 위도
    private BigDecimal longitude; // 경도
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    private String memo;



}
