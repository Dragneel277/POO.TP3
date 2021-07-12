package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import classes.paciente;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Controller implements Initializable {

    String baseDadosPaciente = "ClinicaDentria/src/DB/BaseDadosPaciente.csv";
    String basedadosConsulta = "ClinicaDentria/src/DB/BaseDadosConsulta.csv";
    String[] dentistasList = {"Alicia Neves", "Jorge Tavares",
            "William Nogueira", "Walter Fortes", "Nelson Oliveira"};
    String[] tipoConsultaList = {"Tratamento", "Rotina", "Limpeza",
            "Diagnostico"};
    ArrayList<String> PacientesList=new ArrayList<>();
    private static String keepinfo="";
    private static String changesearch="";


    @FXML
    Button buttonMain, buttoncadastro, buttonmconsulta, buttonvpaciente, buttonpagamento, buttondashboard;

    // login
    @FXML
    private Label loginfail;

    @FXML
    private TextField Uname;

    @FXML
    private PasswordField Pass;

    @FXML
    public void toMain() throws IOException {
        checkLogin();
    }

    private void changescenes(String path,Button btn) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Parent root = loader.load();
            Stage window = (Stage) btn.getScene().getWindow();
            window.setScene(new Scene(root));

    }

    private void checkLogin() throws IOException {
        if (Uname.getText().toString().equals("Adm") && Pass.getText().toString().equals("12345")) {
            loginfail.setText("Success!");
            changescenes("../FMXL/main.fxml",buttonMain);
            
        }

        else if (Uname.getText().isEmpty() && Pass.getText().isEmpty()) {
            loginfail.setText("introduza as informacoes");
        }

        else {
            loginfail.setText("falhou username or password!");
        }
    }


    // menu
    @FXML

    public void toCadastro() throws IOException {
        changescenes("../FMXL/Cadastro.fxml", buttoncadastro);
    }

    @FXML
    public void toConsulta() throws IOException {
        changescenes("../FMXL/Consulta.fxml", buttonmconsulta);
    }

    @FXML
    public void toPagamento() throws IOException {
        changescenes("../FMXL/Pagamento.fxml", buttonvpaciente);
    }

    @FXML
    public void toPaciente() throws IOException {
        changescenes("../FMXL/Paciente.fxml", buttonpagamento);
    }

    @FXML
    public void toDashboard() throws IOException {
        changescenes("../FMXL/Dashboard.fxml", buttondashboard);
    }

    // menu

    @FXML
    private TextField idcadastro,fieldNome,contacto;

    @FXML
    private Button btncadastro;

    @FXML
    private DatePicker dataNascimento;

    // cadastrar
    @FXML
    private Label registerfail;

    @FXML
    public void cadastrarin() throws IOException {
        cadastrarpessoa();
        changescenes("../FMXL/cadastrosucess.fxml", btncadastro);

    }

    private void cadastrarpessoa() throws IOException {

        String nomecadastro = fieldNome.getText();
        LocalDate ddn = dataNascimento.getValue();
        String contactoCadastro = contacto.getText();
        String ckbox;
        Integer idcadastro = Integer.valueOf(getlast(baseDadosPaciente,0)) + 1;

        if (sbox.isSelected()) {
            ckbox = "Sim";
        } else {
            ckbox = "Não";
        }

        paciente paciente1 = new paciente(nomecadastro, contactoCadastro, idcadastro, ckbox, ddn);

        writeCsv(paciente1.getIdPaciente(), paciente1.getNome(), paciente1.getContacto(), paciente1.getSegurado(),
                paciente1.getDataNasc());

    }

    // checkbox
    @FXML
    private CheckBox sbox,nbox;

    @FXML
    void cnbox() {
        if (nbox.isSelected()) {
            sbox.setSelected(false);
        }

    }

    @FXML
    void csbox() {
        if (sbox.isSelected()) {
            nbox.setSelected(false);
        }

    }

    // after Cadastro
    @FXML
    private TextField sc1,sc2,sc3,sc4,sc5;

    @FXML
    private Button revercadastro;

    @FXML
    public void swowinfos() throws IOException {
        sc1.setText(getlast(baseDadosPaciente,0));
        sc2.setText(getlast(baseDadosPaciente,1));
        sc3.setText(getlast(baseDadosPaciente,2));
        sc4.setText(getlast(baseDadosPaciente,3));
        sc5.setText(getlast(baseDadosPaciente,4));
    }

    // LOGOUT
    @FXML
    private Button logout;

    @FXML
    void tologout() throws IOException {
        changescenes("../FMXL/login.fxml", logout);
    }

    // BACKTOMAIN
    @FXML
    private Button backmain;

    @FXML
    void toMainback() throws IOException {
        changescenes("../FMXL/main.fxml", backmain);
    }

    // CSV
    public void writeCsv(Integer id, String name, String contacto, String ckbox, LocalDate ddn) throws IOException {
        List<List<String>> rows = Arrays.asList(Arrays.asList(id.toString(), name, contacto, ddn.toString(), ckbox));
        FileWriter csvWriter = new FileWriter(baseDadosPaciente, true);

        for (List<String> rowData : rows) {
            csvWriter.append(String.join("/", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    
    public void writeCsvconsultas(Integer idconsulta,String idpaciente, String nomepaciente, String nomeDentista,String tConsulta ,LocalDate dataConsulta,Double hrConsulta) throws IOException {
        List<List<String>> rows = Arrays.asList(Arrays.asList(idconsulta.toString(),idpaciente, nomepaciente,nomeDentista, tConsulta,dataConsulta.toString(),hrConsulta.toString()));
        FileWriter csvWriter = new FileWriter(basedadosConsulta, true);

        for (List<String> rowData : rows) {
            csvWriter.append(String.join("/", rowData));
            csvWriter.append("\n");
        }

        csvWriter.flush();
        csvWriter.close();
    }

    // GettingDatafromCSVCliente


    // GetLast
    public String getlast(String baseDados,Integer position) throws IOException {
        String lastId = "";
        String file = baseDados;
        List<String[]> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split("/"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        lastId = content.get(content.size() - 1)[position];// para apanhar last person first colum this case is the ID
        return lastId;
    }


    



    // paginamarcarconsulta

    @FXML
    private ComboBox<String> dentistas;

    @FXML
    private ComboBox<String> tipoConsulta;
    @FXML
    private ComboBox<String> Pacientes;

    @FXML
    private ComboBox<String> Pacientesxconsul;

    @Override

    public void initialize(URL location, ResourceBundle resources) {

        String[] filename = location.toString().split("/");

        if (filename[filename.length - 1].toString().equals("Consulta.fxml")) {
            dentistas.setValue("Alicia Neves");
            dentistas.getItems().addAll(dentistasList);

            tipoConsulta.setValue("Tratamento");
            tipoConsulta.getItems().addAll((tipoConsultaList));

            SpinnerValueFactory<Double> hours=new SpinnerValueFactory.DoubleSpinnerValueFactory(8, 18, 12, 0.5);
            this.hourspinner.setValueFactory(hours);
            hourspinner.setEditable(true);
        }

        if(filename[filename.length - 1].toString().equals("ProcurarPacienteJanela.fxml")){
            try {
                getallName();
            } catch (IOException e) {
                
                e.printStackTrace();
            }
            Pacientes.setValue("Clica para procurar Paciente");
            Pacientes.getItems().addAll((PacientesList));
        }

        

    }

    // getallname
    public void getallName() throws IOException {
        String id = "";
        String nome = "";
        String contacto = "";
        String total = "";
        String file = baseDadosPaciente;
        List<String[]> content = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                content.add(line.split("/"));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < content.size(); i++) {
            id = content.get(i)[0];// 
            nome = content.get(i)[1];// 
            contacto = content.get(i)[2];// 
            total = id + "/" + nome + "/" + contacto;
            PacientesList.add(total);
        }


    }




    //Marcar Consulta

    @FXML
    private Button mcConsulta;
    @FXML
    private DatePicker dataConsulta;

    @FXML
    private Spinner<Double> hourspinner;

   

    
    @FXML
    void marcarConsulta() throws IOException {
        marcacaoConsulta();
    }
    
    private void marcacaoConsulta() throws IOException {


        String nome = pacientenomecomp.getText();
        LocalDate ddc = dataConsulta.getValue();
        Integer idConsulta = Integer.valueOf(getlast(basedadosConsulta,0)) + 1;
        String idPaciente = pacienteidcomp.getText();
        String dentista=dentistas.getValue();
        String qConsulta=tipoConsulta.getValue();
        Double hrconsulta=hourspinner.getValue();
        
        writeCsvconsultas(idConsulta,idPaciente, nome, dentista, qConsulta, ddc,hrconsulta);



        changescenes("../FMXL/Consultamarcada.fxml", mcConsulta);
    }


    //ShowConsultaMarcada
    @FXML
    private TextField rv1,rv2,rv3,rv4,rv5,rv6,rv7;


    @FXML
    private Button reverconsulta;


    @FXML
    void swowinfosconsulta()throws IOException {
        rv1.setText(getlast(basedadosConsulta,0));
        rv2.setText(getlast(basedadosConsulta,1));
        rv3.setText(getlast(basedadosConsulta,2));
        rv4.setText(getlast(basedadosConsulta,3));
        rv5.setText(getlast(basedadosConsulta,4));
        rv6.setText(getlast(basedadosConsulta,5));
        rv7.setText(getlast(basedadosConsulta,6));

    }


    @FXML
    private Button PrcPaciente;


    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private TextField pacienteidcomp;


    @FXML
    private TextField pacientenomecomp;

    @FXML
    void listarPacientes() throws IOException {
        changesearch="1";
        changescenes("../FMXL/ProcurarPacienteJanela.fxml", PrcPaciente);

    }

    @FXML
    private Button btncancelar;

    @FXML
    private Button btnconfirmar;

    @FXML
    void mainAfterListar() throws IOException {
        changescenes("../FMXL/main.fxml", btncancelar);
    }

    @FXML
    void backTMConsulta()throws IOException {
    keepinfo=Pacientes.getValue();


    if(changesearch.equals("1")){
    changescenes("../FMXL/Consulta.fxml",btnconfirmar);}
    if(changesearch.equals("2")){
        changescenes("../FMXL/Paciente.fxml",btnconfirmar);}
    if(changesearch.equals("3")){
        changescenes("../FMXL/Pagamento.fxml",btnconfirmar);}
    }

    @FXML
    private Button loadPaciente;

    public void showpacienteProcurado() throws IOException{
        String[] parts = keepinfo.split("/");
        String id = parts[0];
        String nome = parts[1];

        pacienteidcomp.setText(id);
        pacientenomecomp.setText(nome);
    }


//Verpaciente
@FXML
private Button PrcPaciente2;
@FXML
private Button loadPaciente2;

@FXML
private TextField pacienteidcomp2;

@FXML
private TextField pacientenomecomp2;

@FXML
private Label dc1;

@FXML
private Label dc2;


@FXML
void listarPacientes2() throws IOException {
    changesearch="2";
    changescenes("../FMXL/ProcurarPacienteJanela.fxml", PrcPaciente2);
}

public void showpacienteProcurado2() throws IOException{
    String[] parts = keepinfo.split("/");
    String nome = parts[1];
    pacientenomecomp2.setText(nome);
    pacienteidcomp2.setText(parts[0]);

    dc1.setText(getlast(basedadosConsulta, 5));
    dc2.setText(getlast(basedadosConsulta, 6));






}
@FXML
private Label dataconsultaShow;



@FXML
private Button mainaftererrorbtn;

@FXML
void mainaftererror() throws IOException {
    changescenes("../FMXL/main.fxml", mainaftererrorbtn);
}

@FXML
private Button pagar;
@FXML
private Button PrcPaciente3;

@FXML
private Button loadPaciente3;

@FXML
private TextField pacienteidcomp3;

@FXML
private TextField pacientenomecomp3;

@FXML
void gotoerrorpage() throws IOException {
    changescenes("../FMXL/error.fxml",pagar);

}

@FXML
void listarPacientes3() throws IOException {
    changesearch="3";
    changescenes("../FMXL/ProcurarPacienteJanela.fxml", PrcPaciente3);

}

@FXML
void showpacienteProcurado3() {
    String[] parts = keepinfo.split("/");
    String nome = parts[1];
    String  id= parts[0];
    pacientenomecomp3.setText(nome);
    pacienteidcomp3.setText(id);

}






 }


