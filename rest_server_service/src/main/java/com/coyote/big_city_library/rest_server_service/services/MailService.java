package com.coyote.big_city_library.rest_server_service.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;
import com.coyote.big_city_library.rest_server_model.dao.entities.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender emailSender;

    /**
     * Send a mail to a user which have expired book loan.
     *
     * @param loan the user's loan
     */
    public void sendUserLoanReminder(Loan loan) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(loan.getUser().getEmail());
        message.setSubject("Prêt d'un livre expiré !");

        // Custom message
        String pseudo = loan.getUser().getPseudo();
        String bookTitle = loan.getExemplary().getBook().getTitle();
        Boolean loanExtended = loan.getExtend();
        LocalDate lastReturnedDate = loan.getLoanDate().plusMonths(Boolean.TRUE.equals(loanExtended) ? 2 : 1);
        String libraryName = loan.getExemplary().getLibrary().getName();
        String bringBack = "\nVeuillez le ramener à la " + libraryName + " dès que possible !";

        message.setText(
                "Bonjour " + pseudo + ","
                        + "\n\nLe prêt du livre '" + bookTitle + "' est arrivé à expiration depuis le "
                        + lastReturnedDate + ". "
                        + bringBack
                        + "\n\nMerci d'avance,"
                        + "\n\nL'équipe des bibliothèques de la ville");

        emailSender.send(message);
    }

    /**
    * Send a mail to the user for reservation notification.
    *
    * @param to the user to mail
    */
    public void sendUserReservationNotification(Reservation reservation) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(reservation.getUser().getEmail());
        message.setSubject("Réservation d'un livre disponible !");

        // Custom message
        String pseudo = reservation.getUser().getPseudo();
        String bookTitle = reservation.getExemplary().getBook().getTitle();
        ZonedDateTime maxDate = reservation.getNotifiedAt()
                                           .plusHours(48)
                                           .withZoneSameInstant(ZoneId.of("Europe/Paris"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm:ss");
        String formattedMaxDate = maxDate.format(formatter);
        String libraryName = reservation.getExemplary().getLibrary().getName();

        message.setText(
                "Bonjour " + pseudo + ","
                        + "\n\nVotre réservation du livre '" + bookTitle + "' est disponible."
                        + "\nVous disposez de 48h depuis l'assignation de votre réservation pour récupérer votre exemplaire :"
                        + "\n- Récupérez votre exemplaire avant le : " + formattedMaxDate
                        + "\n- Votre exemplaire se trouve dans la bibliothèque : " + libraryName
                        + "\n\nMerci d'avance,"
                        + "\n\nL'équipe des bibliothèques de la ville");

        emailSender.send(message);
    }
}
