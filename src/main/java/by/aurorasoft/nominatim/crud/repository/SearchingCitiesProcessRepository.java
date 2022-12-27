package by.aurorasoft.nominatim.crud.repository;

import by.aurorasoft.nominatim.crud.model.entity.SearchingCitiesProcessEntity;
import by.aurorasoft.nominatim.crud.model.entity.SearchingCitiesProcessEntity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SearchingCitiesProcessRepository extends JpaRepository<SearchingCitiesProcessEntity, Long> {

    @Modifying
    @Query("UPDATE SearchingCitiesProcessEntity e SET e.status = :newStatus WHERE e.id = :id")
    void updateStatus(Long id, Status newStatus);

    @Modifying
    @Query("UPDATE SearchingCitiesProcessEntity e "
            + "SET e.handledPoints = :newHandledPoints, e.status = :newStatus "
            + "WHERE e.id = :id")
    void updateHandledPointsAndStatus(Long id, long newHandledPoints, Status newStatus);
}
