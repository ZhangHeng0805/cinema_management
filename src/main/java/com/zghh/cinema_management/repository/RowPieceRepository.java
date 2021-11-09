package com.zghh.cinema_management.repository;

import com.zghh.cinema_management.bean.Film;
import com.zghh.cinema_management.bean.RowPiece;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface RowPieceRepository extends JpaRepository<RowPiece,Integer> {
    //根据电影id查询排片
    @Query(value = "select * from t_row_piece where film_id = ?1 " ,nativeQuery = true)
    List<RowPiece> findRowPieceByFilmId(Integer film_id);
    //根据影厅id查询排片
    @Query(value = "select * from t_row_piece where screens_id = ?1 " ,nativeQuery = true)
    List<RowPiece> findRowPieceByScreensId(Integer screens_id);
}
