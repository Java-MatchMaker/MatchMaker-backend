package org.dgsw.matchmaker.competition.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.dgsw.matchmaker.competition.type.CompetitionSportType;
import org.dgsw.matchmaker.competition.type.CompetitionStatus;
import org.dgsw.matchmaker.competition.type.CompetitionType;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "competition")
public class CompetitionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // 대회명

    @Column
    private String title;
    // 종목
    @Column
    @Enumerated(EnumType.STRING)
    private CompetitionSportType sportType;
    // 설명
    @Column
    private String description;
    // 최소 참가 인원
    @Column
    private Integer minParticipants;
    // 최대 참가 인원
    @Column
    private Integer maxParticipants;
    // 모집 시작일
    @Column
    private LocalDate recruitStartDate;
    // 모집 종료일
    @Column
    private LocalDate recruitEndDate;
    // 대회 시작일
    @Column
    private LocalDate competitionStartDate;
    // 대회 종료일
    @Column
    private LocalDate competitionEndDate;
    // 장소
    @Column
    private String location;
    // 경기 방식
    @Column
    @Enumerated(EnumType.STRING)
    private CompetitionType competitionType;
    // 상태
    @Column
    @Enumerated(EnumType.STRING)
    private CompetitionStatus status;
}