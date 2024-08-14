package com.SeriesAnalyzer.main.Repositories.Series;


import com.SeriesAnalyzer.main.Dtos.WorkDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class WorkRepositoryCustomImpl implements WorkRepositoryCustom{

    @PersistenceContext
    private EntityManager entityManager;


    @Override
    public List<WorkDto> findAllWorksWithImageRoute() {
        String jpql = "SELECT new com.SeriesAnalyzer.main.Dtos.WorkDto(" +
                "w.id, w.name, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN a.imageRoute " +
                "WHEN Manga THEN m.imageRoute " +
                "WHEN Novel THEN n.imageRoute " +
                "END, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN 'Anime' " +
                "WHEN Manga THEN 'Manga' " +
                "WHEN Novel THEN 'Novel' " +
                "END) " +
                "FROM Work w " +
                "LEFT JOIN Anime a ON w.id = a.id " +
                "LEFT JOIN Manga m ON w.id = m.id " +
                "LEFT JOIN Novel n ON w.id = n.id";

        TypedQuery<WorkDto> query = entityManager.createQuery(jpql, WorkDto.class);
        return query.getResultList();
    }

    @Override
    public Page<WorkDto> findAllWorksWithImageRoute(Pageable pageable) {
        // Define la consulta JPQL
        String jpql = "SELECT new com.SeriesAnalyzer.main.Dtos.WorkDto(" +
                "w.id, w.name, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN a.imageRoute " +
                "WHEN Manga THEN m.imageRoute " +
                "WHEN Novel THEN n.imageRoute " +
                "END, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN 'Anime' " +
                "WHEN Manga THEN 'Manga' " +
                "WHEN Novel THEN 'Novel' " +
                "END) " +
                "FROM Work w " +
                "LEFT JOIN Anime a ON w.id = a.id " +
                "LEFT JOIN Manga m ON w.id = m.id " +
                "LEFT JOIN Novel n ON w.id = n.id";

        // Crear la consulta con JPQL
        TypedQuery<WorkDto> query = entityManager.createQuery(jpql, WorkDto.class);

        // Configurar la paginación
        query.setFirstResult((int) pageable.getOffset());  // OFFSET
        query.setMaxResults(pageable.getPageSize());       // LIMIT

        // Ejecutar la consulta y obtener los resultados
        List<WorkDto> content = query.getResultList();

        // Consulta para obtener el conteo total
        String countJpql = "SELECT COUNT(w.id) FROM Work w";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        long total = countQuery.getSingleResult();

        // Devolver los resultados en una página
        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<WorkDto> findAllWorksWithImageRouteAndSearch(String search, Pageable pageable) {
        String jpql = "SELECT new com.SeriesAnalyzer.main.Dtos.WorkDto(" +
                "w.id, w.name, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN a.imageRoute " +
                "WHEN Manga THEN m.imageRoute " +
                "WHEN Novel THEN n.imageRoute " +
                "END, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN 'Anime' " +
                "WHEN Manga THEN 'Manga' " +
                "WHEN Novel THEN 'Novel' " +
                "END) " +
                "FROM Work w " +
                "LEFT JOIN Anime a ON w.id = a.id " +
                "LEFT JOIN Manga m ON w.id = m.id " +
                "LEFT JOIN Novel n ON w.id = n.id " +
                "WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%'))";

        TypedQuery<WorkDto> query = entityManager.createQuery(jpql, WorkDto.class);
        query.setParameter("search", search);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<WorkDto> content = query.getResultList();

        String countJpql = "SELECT COUNT(w.id) FROM Work w WHERE LOWER(w.name) LIKE LOWER(CONCAT('%', :search, '%'))";
        TypedQuery<Long> countQuery = entityManager.createQuery(countJpql, Long.class);
        countQuery.setParameter("search", search);
        long total = countQuery.getSingleResult();

        System.out.println("total:" + total);

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<WorkDto> getAllWorksByUserId(Long userId, Pageable pageable) {
        String jpql = "SELECT new com.SeriesAnalyzer.main.Dtos.WorkDto(" +
                "w.id, w.name, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN a.imageRoute " +
                "WHEN Manga THEN m.imageRoute " +
                "WHEN Novel THEN n.imageRoute " +
                "END, " +
                "CASE TYPE(w) " +
                "WHEN Anime THEN 'Anime' " +
                "WHEN Manga THEN 'Manga' " +
                "WHEN Novel THEN 'Novel' " +
                "END)" +
                "FROM UserWork uw " +
                "JOIN uw.work w " +
                "LEFT JOIN Anime a ON w.id = a.id " +
                "LEFT JOIN Manga m ON w.id = m.id " +
                "LEFT JOIN Novel n ON w.id = n.id " +
                "WHERE uw.user.id = :userId";

        TypedQuery<WorkDto> query = entityManager.createQuery(jpql, WorkDto.class);
        query.setParameter("userId", userId);
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<WorkDto> workDtos = query.getResultList();

        String countQueryStr = "SELECT COUNT(uw.id) " +
                "FROM UserWork uw " +
                "WHERE uw.user.id = :userId";

        TypedQuery<Long> countQuery = entityManager.createQuery(countQueryStr, Long.class);
        countQuery.setParameter("userId", userId);

        Long total = countQuery.getSingleResult();

        return new PageImpl<>(workDtos, pageable, total);
    }

}
