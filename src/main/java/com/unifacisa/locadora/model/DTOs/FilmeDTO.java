package com.unifacisa.locadora.model.DTOs;

public class FilmeDTO {
    private Long id;
    private String capaUrl;

    public FilmeDTO() {
    }

    public FilmeDTO(Long id, String capaUrl) {
        this.id = id;
        this.capaUrl = capaUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCapaUrl() {
        return capaUrl;
    }

    public void setCapaUrl(String capaUrl) {
        this.capaUrl = capaUrl;
    }
}
