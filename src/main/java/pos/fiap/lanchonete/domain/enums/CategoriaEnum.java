package pos.fiap.lanchonete.domain.enums;

import lombok.Getter;

import java.util.Optional;
import java.util.stream.Stream;

@Getter
public enum CategoriaEnum {

    LANCHE("lanche"),
    ACOMPANHAMENTO("acompanhamento"),
    BEBIDA("bebida"),
    SOBREMESA("sobremesa");

    private final String name;

    CategoriaEnum(String name) {
        this.name = name;
    }

    public static Optional<CategoriaEnum> getCategoriaByLabel(String label) {
        try {
            return Optional.of(CategoriaEnum.valueOf(label.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public static Optional<CategoriaEnum> from(String status) {
        return Stream.of(CategoriaEnum.values())
                .filter(s -> s.getName().equals(status))
                .findFirst();
    }
}
