package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reservation")
@IdClass(ReservationId.class)
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Reservation implements Serializable {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    @JsonIgnoreProperties("reservations")
    private Book book;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties("reservations")
    private User user;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "notified_at")
    private ZonedDateTime notifiedAt;

    @OneToOne
    @JoinColumn(name = "exemplary_id")
    @JsonIgnoreProperties("reservation")
    private Exemplary exemplary;

}
