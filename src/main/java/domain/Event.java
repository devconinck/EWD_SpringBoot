package domain;

import java.io.Serializable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.format.annotation.NumberFormat;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import utils.LocalDateTimeDeserializer;
import utils.LocalDateTimeSerializer;

@Entity
@Getter
@NoArgsConstructor()
@Setter
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
@AllArgsConstructor()
@JsonPropertyOrder({"date", "numberSeats"})
public class Event implements Serializable {
	
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)    
    private LocalDateTime date;
    
    private String discipline1;
    private String discipline2;
   

    private int olympicNumber1;
    private int olympicNumber2;
    @NotNull(message = "{validation.ticketPrice.NotNull.message}")
    @DecimalMin(value = "0.0", inclusive = false, message = "{validation.ticketPrice.DecimalMin.message}")
    @DecimalMax(value = "150.0", inclusive = false, message = "{validation.ticketPrice.DecimalMax.message}")
    private BigDecimal ticketPrice;
    @NotNull(message = "{validation.numberSeats.NotNull.message}")
    @Min(value = 1, message = "{validation.numberSeats.Min.message}")
    @Max(value = 49, message = "{validation.numberSeats.Max.message}")
    private int numberSeats;

    @ManyToOne
    private Sport sport;

    @ManyToOne
    private Stadium stadium;
    
    public Event(Sport sport, Stadium stadium, LocalDateTime date, String discipline1, String discipline2, int olympicNumber1, int olympicNumber2, BigDecimal ticketPrice,int numberSeats) {
    	this.sport = sport;
    	this.stadium = stadium;
    	this.date = date;
    	this.discipline1 = discipline1;
    	this.discipline2 = discipline2;
    	this.olympicNumber1 = olympicNumber1;
    	this.olympicNumber2 = olympicNumber2;
    	this.ticketPrice = ticketPrice;
    	this.numberSeats = numberSeats;
    	
    }
    
    

    
}