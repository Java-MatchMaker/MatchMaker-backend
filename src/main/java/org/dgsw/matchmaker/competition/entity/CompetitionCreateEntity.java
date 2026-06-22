package org.dgsw.matchmaker.competition.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "competition")
public class CompetitionCreateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 대회명
    private String title;
    // 종목
    private String sportType;
    // 설명
    private String description;
    // 최소 참가 인원
    private Integer minParticipants;
    // 최대 참가 인원
    private Integer maxParticipants;
    // 모집 시작일
    private LocalDate recruitStartDate;
    // 모집 종료일
    private LocalDate recruitEndDate;
    // 대회 시작일
    private LocalDate competitionStartDate;
    // 대회 종료일
    private LocalDate competitionEndDate;
    // 장소
    private String location;
    // 경기 방식
    private String competitionType;
    // 상태
    private String status;
}