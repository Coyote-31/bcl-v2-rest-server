package com.coyote.big_city_library.rest_server_model.dao.entities;

import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "reservation")
@IdClass(ReservationId.class)
@Data
@NoArgsConstructor
@ToString(includeFieldNames = true)
public class Reservation {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "notified_at")
    private ZonedDateTime notifiedAt;

    // Bi-directional synchronization :

    public void setBook(Book book) {
        this.book = book;
        book.addReservation(this);
    }

    public void setUser(User user) {
        this.user = user;
        user.addReservation(this);
    }

    // @Override
    // public String toString() {
    //     return "Reservation(book=" + book.getTitle() + ", user=" + user.getPseudo() + ")";
    // }
}
