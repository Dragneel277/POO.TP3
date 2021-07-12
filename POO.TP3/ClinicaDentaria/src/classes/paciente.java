package classes;



import java.time.LocalDate;


import javafx.scene.control.TextField;

public class paciente  { 
    private String nome;
    private String contacto;
    public Integer idPaciente;
    private String segurado;
    private LocalDate dataNasc;
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getContacto() {
        return contacto;
    }
    public void setContacto(String contacto) {
        this.contacto = contacto;
    }
    public Integer getIdPaciente() {
        return idPaciente;
    }
    public void setIdPaciente(Integer idPaciente) {
        this.idPaciente = idPaciente;
    }
    public String getSegurado() {
        return segurado;
    }
    public void setSegurado(String segurado) {
        this.segurado = segurado;
    }
    public LocalDate getDataNasc() {
        return dataNasc;
    }
    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }
    public paciente(String nome, String contacto, Integer idPaciente, String segurado, LocalDate dataNasc) {
        this.nome = nome;
        this.contacto = contacto;
        this.idPaciente = idPaciente;
        this.segurado = segurado;
        this.dataNasc = dataNasc;
    }
    public paciente(String nome2, TextField contacto2, String idPaciente2) {
    }


    

}

