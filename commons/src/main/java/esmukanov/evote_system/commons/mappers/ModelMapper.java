package esmukanov.evote_system.commons.mappers;

import java.util.LinkedList;
import java.util.List;

public interface ModelMapper<M, E> {

    M toModel(E entity);

    E toEntity(M model);

    default List<M> toModels(List<E> entities) {
        return entities.stream().map(this::toModel).toList();
    }

    default List<E> toEntities(List<M> models) {
        return models.stream().map(this::toEntity).toList();
    }

    default List<M> toModels(Iterable<E> entities) {
        List<M> models = new LinkedList<>();
        entities.forEach(model -> models.add(toModel(model)));
        return models;
    }

    default List<E> toEntities(Iterable<M> models) {
        List<E> entities = new LinkedList<>();
        models.forEach(model -> entities.add(toEntity(model)));
        return entities;
    }
}
