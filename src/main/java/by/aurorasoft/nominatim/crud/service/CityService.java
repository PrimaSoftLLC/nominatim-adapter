package by.aurorasoft.nominatim.crud.service;

import by.aurorasoft.nominatim.crud.mapper.CityMapper;
import by.aurorasoft.nominatim.crud.model.dto.City;
import by.aurorasoft.nominatim.crud.model.entity.CityEntity;
import by.aurorasoft.nominatim.crud.repository.CityRepository;
import by.nhorushko.crudgeneric.v2.service.AbsServiceCRUD;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.LineString;
import org.locationtech.jts.geom.prep.PreparedGeometry;
import org.locationtech.jts.geom.prep.PreparedGeometryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;
import static org.locationtech.jts.geom.prep.PreparedGeometryFactory.prepare;

@Service
@Transactional
public class CityService extends AbsServiceCRUD<Long, CityEntity, City, CityRepository> {

    public CityService(CityMapper mapper, CityRepository repository) {
        super(mapper, repository);
    }

    public List<City> findAll(int pageNumber, int pageSize) {
        final Pageable pageable = PageRequest.of(pageNumber, pageSize);
        final Page<CityEntity> page = super.repository.findAll(pageable);
        final List<CityEntity> foundEntities = page.getContent();
        return super.mapper.toDtos(foundEntities);
    }

    public boolean isExistByGeometry(Geometry geometry) {
        return super.repository.isExistByGeometry(geometry);
    }

    public List<City> findCitiesIntersectedByLineString(LineString lineString) {
        final List<CityEntity> foundEntities = super.repository.findCitiesIntersectedByLineString(lineString);
        return super.mapper.toDtos(foundEntities);
    }

    public Map<PreparedGeometry, PreparedGeometry> findPreparedGeometriesByPreparedBoundingBoxes() {
        final List<Geometry> allCitiesGeometries = this.repository.findAllCitiesGeometries();
        return allCitiesGeometries
                .stream()
                .collect(
                        toMap(
                                geometry -> prepare(geometry.getEnvelope()),
                                PreparedGeometryFactory::prepare
                        )
                );
    }
}
