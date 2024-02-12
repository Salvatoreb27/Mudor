package Mudor.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SingoloDTO {


    private String titoloSingolo;

    private Date dataRilascio;

    private List<String> generi;

    private List<String> artisti = new ArrayList<>();
}
