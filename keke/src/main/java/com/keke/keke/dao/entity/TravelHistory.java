package com.keke.keke.dao.entity;

import java.math.BigDecimal;
import com.keke.keke.constant.PaymentType;
import com.keke.keke.constant.TripStatus;
import com.keke.keke.constant.VehicleType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "travel_history")
public class TravelHistory extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passanger passenger;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rider_id")
    private Rider rider;

    @Column(name = "pickup_location", nullable = false)
    private String pickUpLocation;

    @Column(name = "dropoff_location", nullable = false)
    private String dropOffLocation;

    @Column(name = "pickup_latitude", precision = 10, scale = 8)
    private BigDecimal pickupLat;

    @Column(name = "pickup_longitude", precision = 11, scale = 8)
    private BigDecimal pickupLon;

    @Column(name = "dropoff_latitude", precision = 10, scale = 8)
    private BigDecimal dropoffLat;

    @Column(name = "dropoff_longitude", precision = 11, scale = 8)
    private BigDecimal dropoffLon;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TripStatus status;

    @Column(name = "pickup_time")
    private String pickUpTime;

    @Column(name = "dropoff_time")
    private String dropOffTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type")
    private VehicleType vehicleType;

    @Column(name = "fare")
    private BigDecimal fare;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_type")
    private PaymentType paymentType;
}

