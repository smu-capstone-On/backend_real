package graduation.petshop.domain.board.entity;

import lombok.Getter;

@Getter
public enum TagType {
    CAT("고양이"),
    DOG("강아지"),
    SMALL_ANIMAL("소동물"),
    REPTILES("파충류"),
    BIRD("조류"),
    QUESTION("질문"),
    ;

    private final String koName;

    TagType(String koName) {
        this.koName = koName;
    }
}
