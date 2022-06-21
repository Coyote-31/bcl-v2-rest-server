package com.coyote.big_city_library.rest_server_service.services;

import java.time.LocalDate;

import com.coyote.big_city_library.rest_server_model.dao.entities.Loan;

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
     * @param to the user to mail
     */
    public void sendUserLoanReminder(Loan loan) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("noreply@bigcitylibrary.com");
        message.setTo(loan.getUser().getEmail());
        message.setSubject("Prêt d'un livre expiré !");

        // Date of today
        LocalDate now = LocalDate.now();

        // Custom message
        String pseudo = loan.getUser().getPseudo();
        String bookTitle = loan.getExemplary().getBook().getTitle();
        Boolean loanExtended = loan.getExtend();
        LocalDate lastReturnedDate = loan.getLoanDate().plusMonths(Boolean.FALSE.equals(loanExtended) ? 2 : 1);
        String libraryName = loan.getExemplary().getLibrary().getName();
        String bringBack = "\nVeuillez le ramener à la " + libraryName + " dès que possible !";

        // If not extend and less than 2 months customise message
        if (Boolean.FALSE.equals(loanExtended) && loan.getLoanDate().plusMonths(2).isAfter(now)) {
            bringBack = "\nVeuillez le ramener à la " + libraryName + " dès que possible !"
                    + "\nN'oubliez pas que vous avez la possibilité de prolonger votre prêt de 1 mois.";
        }

        message.setText(
                "Bonjour " + pseudo + ","
                        + "\n\nLe prêt du livre '" + bookTitle + "' est arrivé à expiration depuis le "
                        + lastReturnedDate + ". "
                        + bringBack
                        + "\n\nMerci d'avance,"
                        + "\n\nL'équipe des bibliothèques de la ville");

        emailSender.send(message);
    }
}
