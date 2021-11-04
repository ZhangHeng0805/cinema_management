package com.zghh.cinema_management.bean;


import javax.persistence.*;

/*排片管理实体类*/
@Entity
@Table(name = "[t_rowPiece]")
public class RowPiece {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//排片id
    @Column
    private Integer filmId;//电影id
    @Column
    private Integer screensId;//影厅id
    @Column
    private String playingTime;//播放时间
    @Column
    private Double fare;//票价
    @Column(columnDefinition = "TEXT")
    private String sitState;//座位状态


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFilmId() {
        return filmId;
    }

    public void setFilmId(Integer filmId) {
        this.filmId = filmId;
    }

    public Integer getScreensId() {
        return screensId;
    }

    public void setScreensId(Integer screensId) {
        this.screensId = screensId;
    }

    public String getPlayingTime() {
        return playingTime;
    }

    public void setPlayingTime(String playingTime) {
        this.playingTime = playingTime;
    }

    public Double getFare() {
        return fare;
    }

    public void setFare(Double fare) {
        this.fare = fare;
    }

    @Lob
    @Column(columnDefinition = "TEXT")
    public String getSitState() {
        return sitState;
    }

    public void setSitState(String sitState) {
        this.sitState = sitState;
    }
}
