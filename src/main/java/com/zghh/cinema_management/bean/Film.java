package com.zghh.cinema_management.bean;

import lombok.Data;

import javax.persistence.*;

/*影片实体类*/

@Entity
@Table(name = "t_film")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//主键自增
    private Integer id;//影片id
    @Column(name = "[filmName]")
    private String filmName;//电影名称
    @Column(name = "[cover]")
    private String cover;//影片封面
    @Column(name = "[filmType]")
    private String filmType;//电影类型
    @Column
    private String country;//国家
    @Column(name = "[director]")
    private String director;//导演
    @Column(name = "[mainActor]")
    private String mainActor;//主要演员
    @Column(name = "[releaseTime]")
    private String releaseTime;//上映时间
    @Column(name = "[movieRatings]")
    private Double movieRatings;//电影评分
    @Column(name = "[filmIntroduced]")
    private String filmIntroduced;//电影简介
    @Column(name = "[state]")
    private Integer state;//影片状态（0-即将上映 1-上映中 2-下线）

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getFilmType() {
        return filmType;
    }

    public void setFilmType(String filmType) {
        this.filmType = filmType;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getMainActor() {
        return mainActor;
    }

    public void setMainActor(String mainActor) {
        this.mainActor = mainActor;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Double getMovieRatings() {
        return movieRatings;
    }

    public void setMovieRatings(Double movieRatings) {
        this.movieRatings = movieRatings;
    }

    public String getFilmIntroduced() {
        return filmIntroduced;
    }

    public void setFilmIntroduced(String filmIntroduced) {
        this.filmIntroduced = filmIntroduced;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
}
