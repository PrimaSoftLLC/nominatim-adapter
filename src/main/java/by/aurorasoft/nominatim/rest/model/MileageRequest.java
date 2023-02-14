package by.aurorasoft.nominatim.rest.model;

import by.nhorushko.distancecalculator.LatLngAlt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.List;

@Value
@AllArgsConstructor
@Builder
public class MileageRequest {
    List<TrackPoint> trackPoints;
    int minDetectionSpeed;
    int maxMessageTimeout;

    @Value
    @AllArgsConstructor
    @Builder
    public static class TrackPoint implements LatLngAlt {

        @NotNull
        @PastOrPresent
        Instant datetime;

        @NotNull
        @DecimalMin("-90")
        @DecimalMax("90")
        Float latitude;

        @NotNull
        @DecimalMin("-180")
        @DecimalMax("180")
        Float longitude;

        @NotNull
        Integer altitude;

        @NotNull
        @Min(0)
        @Max(1000)
        Integer speed;

        @NotNull
        Boolean valid;

        @Override
        public float getLatitude() {
            return this.latitude;
        }

        @Override
        public float getLongitude() {
            return this.longitude;
        }

        @Override
        public int getAltitude() {
            return this.altitude;
        }

        @Override
        public int getSpeed() {
            return this.speed;
        }

        @Override
        public boolean isValid() {
            return this.valid;
        }
    }
}
