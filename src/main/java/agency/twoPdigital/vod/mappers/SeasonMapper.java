    package agency.twoPdigital.vod.mappers;

    import agency.twoPdigital.vod.entities.SeasonEntity;
    import agency.twoPdigital.vod.models.SeasonModel;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.mapstruct.factory.Mappers;

    @Mapper(componentModel = "spring")
    public interface SeasonMapper {
        SeasonMapper INSTANCE = Mappers.getMapper(SeasonMapper.class);

        SeasonModel toModel(SeasonEntity entity);

        @Mapping(target = "createdAt", ignore = true)
        @Mapping(target = "updatedAt", ignore = true)
        SeasonEntity toEntity(SeasonModel model);
    }
