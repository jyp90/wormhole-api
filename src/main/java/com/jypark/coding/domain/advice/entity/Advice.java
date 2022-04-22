package com.jypark.coding.domain.advice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jypark.coding.common.advice.AdviceUtils;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "advices")
@EqualsAndHashCode(of = "adviceId")
public class Advice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long adviceId;

    @Column(name = "ip")
    private String ip;

    @Column(name = "uri")
    private String uri;

    @Column(name = "message", columnDefinition = "TEXT")
    private String message;

    @Column(name = "method")
    private String method;

    @Column(name = "parameters", columnDefinition = "TEXT")
    private String parameters;

    @CreationTimestamp
    @Column(name = "error_at")
    private LocalDateTime errorAt = LocalDateTime.now();

    public Advice(HttpServletRequest req, Exception e) {
        this.ip = AdviceUtils.getIpAddress(req);
        this.uri = AdviceUtils.getUri(req);
        this.message = e.getMessage();
        this.errorAt = LocalDateTime.now();
        this.method = AdviceUtils.getMethod(req);
        this.parameters = AdviceUtils.getParameters(req);
    }
}
