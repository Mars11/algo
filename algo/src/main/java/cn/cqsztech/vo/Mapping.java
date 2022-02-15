package cn.cqsztech.vo;

import org.mapstruct.Mapper;

import java.util.List;


public interface Mapping<E,D> {
    /**
     * dto转domain
     * @param dto
     * @return
     */
    E toDomain(D dto);
    /**
     * domain转dto
     * @param entity
     * @return
     */
    D toDto(E entity);
    /**
     * dto集合转domain集合
     * @param dtoList
     * @return
     */
    List<E> toDomain(List<D> dtoList);
    /**
     * domain集合转dto集合
     * @param entityList
     * @return
     */
    List <D> toDto(List<E> entityList);
}
