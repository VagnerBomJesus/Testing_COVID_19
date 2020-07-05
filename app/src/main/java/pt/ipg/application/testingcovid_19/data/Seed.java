package pt.ipg.application.testingcovid_19.data;

import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import pt.ipg.application.testingcovid_19.database.Convert;
import pt.ipg.application.testingcovid_19.database.table.DBTableChoice;
import pt.ipg.application.testingcovid_19.database.table.DBTableDoctor;
import pt.ipg.application.testingcovid_19.database.table.DBTableFaq;
import pt.ipg.application.testingcovid_19.database.table.DBTableHistory;
import pt.ipg.application.testingcovid_19.database.table.DBTableQuestion;
import pt.ipg.application.testingcovid_19.database.table.DBTableUser;
import pt.ipg.application.testingcovid_19.database.table.DBTableUserChoice;
import pt.ipg.application.testingcovid_19.object.Choice;
import pt.ipg.application.testingcovid_19.object.Doctor;
import pt.ipg.application.testingcovid_19.object.Faq;
import pt.ipg.application.testingcovid_19.object.History;
import pt.ipg.application.testingcovid_19.object.Question;
import pt.ipg.application.testingcovid_19.object.User;
import pt.ipg.application.testingcovid_19.object.UserChoice;

public class Seed {

    private static SQLiteDatabase Database;

    // Declare all table here
    private DBTableDoctor tb_doctor;
    private DBTableQuestion tb_question;
    private DBTableChoice tb_Choice;
    private DBTableUserChoice tb_userChoice;
    private DBTableHistory tb_history;
    private DBTableUser tb_user;
    private DBTableFaq tb_faqs;

    // Declare all object here
    private Doctor obj_doctor;
    private Question obj_question;
    private Choice obj_choice;
    private UserChoice obj_userChoice;
    private History obj_history;
    private User obj_user;
    private Faq obj_faqs;

    // Declare all id's here
    private ArrayList<Integer> id_doctor;
    private ArrayList<Integer> id_question;
    private ArrayList<Integer> id_choice;
    private ArrayList<Integer> id_userChoice;
    private ArrayList<Integer> id_history;
    private ArrayList<Integer> id_user;
    private ArrayList<Integer> id_faqs;

    public Seed(SQLiteDatabase Database){
        this.Database = Database;

        // Initialize tables here
        tb_doctor = new DBTableDoctor(Database);
        tb_question = new DBTableQuestion(Database);
        tb_Choice = new DBTableChoice(Database);
        tb_userChoice = new DBTableUserChoice(Database);
        tb_history = new DBTableHistory(Database);
        tb_user = new DBTableUser(Database);
        tb_faqs = new DBTableFaq(Database);

        // Initialize objects here
        obj_doctor = new Doctor();
        obj_question = new Question();
        obj_choice = new Choice();
        obj_userChoice = new UserChoice();
        obj_history = new History();
        obj_user = new User();
        obj_faqs = new Faq();

        // Initialize id's here
        id_doctor = new ArrayList<>();
        id_question = new ArrayList<>();
        id_choice = new ArrayList<>();
        id_userChoice = new ArrayList<>();
        id_history = new ArrayList<>();
        id_user = new ArrayList<>();
        id_faqs = new ArrayList<>();
    }

    public void load(){

        table_doctor();
        table_question();
        table_Choice();
        table_user();
        table_faqs();

        System.out.println("All data has been successfully loaded into seeData");
    }

    public void table_doctor () {

        //Do not continue if there is something in the database..
        if( tb_doctor.count() > 0 ){
            return;
        }

        obj_doctor.setName("Marcos Paulo");
        obj_doctor.setTin("230934900");
        obj_doctor.setEmail("marcospaulo@gmail.com");
        obj_doctor.setPhone("+351-965-554-888");
        obj_doctor.setPassword("kpl0vvdm");
        obj_doctor.setConfirmed("1");
        id_doctor.add((int) tb_doctor.insert(Convert.doctorToContentValues(obj_doctor)));

        obj_doctor.setName("Patricia Dias");
        obj_doctor.setTin("231491662");
        obj_doctor.setEmail("patriciadias@gmail.com");
        obj_doctor.setPhone("+351-935-555-521");
        obj_doctor.setPassword("roda0viva");
        obj_doctor.setConfirmed("0");
        id_doctor.add((int) tb_doctor.insert(Convert.doctorToContentValues(obj_doctor)));

        obj_doctor.setName("Lucas Santos");
        obj_doctor.setTin("214699986");
        obj_doctor.setEmail("lucassantos@gmail.com");
        obj_doctor.setPhone("+351-921-555-982");
        obj_doctor.setPassword("banana123");
        obj_doctor.setConfirmed("1");
        id_doctor.add((int) tb_doctor.insert(Convert.doctorToContentValues(obj_doctor)));

    }

    public void table_question() {
        //Do not continue if there is something in the database..
        if( tb_question.count() > 0 ){
            return;
        }

        obj_question.setFk_doctor(id_doctor.get(0));
        obj_question.setQuestion("Question number 1?");
        id_question.add((int) tb_question.insert(Convert.questionToContentValues(obj_question)));

        obj_question.setFk_doctor(id_doctor.get(0));
        obj_question.setQuestion("Question number 2?");
        id_question.add((int) tb_question.insert(Convert.questionToContentValues(obj_question)));

        obj_question.setFk_doctor(id_doctor.get(1));
        obj_question.setQuestion("Question number 3?");
        id_question.add((int) tb_question.insert(Convert.questionToContentValues(obj_question)));

        obj_question.setFk_doctor(id_doctor.get(0));
        obj_question.setQuestion("Question number 4?");
        id_question.add((int) tb_question.insert(Convert.questionToContentValues(obj_question)));

        obj_question.setFk_doctor(id_doctor.get(1));
        obj_question.setQuestion("Question number 5?");
        id_question.add((int) tb_question.insert(Convert.questionToContentValues(obj_question)));

        obj_question.setFk_doctor(id_doctor.get(2));
        obj_question.setQuestion("Question number 6?");
        id_question.add((int) tb_question.insert(Convert.questionToContentValues(obj_question)));
    }

    public void table_Choice() {
        //Do not continue if there is something in the database..
        if( tb_Choice.count() > 0 ){
            return;
        }

        obj_choice.setFk_question(id_question.get(0));
        obj_choice.setChoice("Choice 1"); // 0..2
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(0));
        obj_choice.setChoice("Choice 2"); // 0..2
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(0));
        obj_choice.setChoice("Choice 3"); // 0..2
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));


        obj_choice.setFk_question(id_question.get(1));
        obj_choice.setChoice("Choice 1"); // 0..4
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(1));
        obj_choice.setChoice("Choice 2"); // 0..4
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(1));
        obj_choice.setChoice("Choice 3"); // 0..4
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(1));
        obj_choice.setChoice("Choice 4"); // 0..4
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(1));
        obj_choice.setChoice("Choice 5"); // 0..4
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));


        obj_choice.setFk_question(id_question.get(2));
        obj_choice.setChoice("Choice 1"); // 0..5
        obj_choice.setWeight(3); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(2));
        obj_choice.setChoice("Choice 2"); // 0..5
        obj_choice.setWeight(2); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(2));
        obj_choice.setChoice("Choice 3"); // 0..5
        obj_choice.setWeight(4); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(2));
        obj_choice.setChoice("Choice 4"); // 0..5
        obj_choice.setWeight(1); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(2));
        obj_choice.setChoice("Choice 5"); // 0..5
        obj_choice.setWeight(0); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(2));
        obj_choice.setChoice("Choice 6"); // 0..5
        obj_choice.setWeight(2); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));


        obj_choice.setFk_question(id_question.get(3));
        obj_choice.setChoice("Choice 1"); // 0..2
        obj_choice.setWeight(3); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(3));
        obj_choice.setChoice("Choice 2"); // 0..2
        obj_choice.setWeight(5); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(3));
        obj_choice.setChoice("Choice 3"); // 0..2
        obj_choice.setWeight(2); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));


        obj_choice.setFk_question(id_question.get(4));
        obj_choice.setChoice("Choice 1"); // 0..1
        obj_choice.setWeight(3); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(4));
        obj_choice.setChoice("Choice 2"); // 0..1
        obj_choice.setWeight(5); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));


        obj_choice.setFk_question(id_question.get(5));
        obj_choice.setChoice("Choice 1"); // 0..2
        obj_choice.setWeight(3); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(5));
        obj_choice.setChoice("Choice 2"); // 0..2
        obj_choice.setWeight(2); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));

        obj_choice.setFk_question(id_question.get(5));
        obj_choice.setChoice("Choice 3"); // 0..2
        obj_choice.setWeight(1); // 0..5
        id_choice.add((int) tb_Choice.insert(Convert.choicesToContentValues(obj_choice)));
    }

    public void table_user() {
        //Do not continue if there is something in the database..
        if( tb_user.count() > 0 ){
            return;
        }

        obj_user.setName("Agostinho Ramos");
        obj_user.setGender("M");
        obj_user.setTin("287273962");
        obj_user.setEmail("agostinhopina095@gmail.com");
        obj_user.setPhone("+351-934-927-329");
        obj_user.setBirthday("26/03/1995");
        obj_user.setDistrict("Guarda");
        obj_user.setCountry("Portugal");
        id_user.add((int) tb_user.insert(Convert.userToContentValues(obj_user)));
    }

    public void table_userChoice() {
        //Do not continue if there is something in the database..
        if( tb_userChoice.count() > 0 ){
            return;
        }

        //obj_userChoice.set("");
        //obj_userChoice.set("");
        //id_userChoice.add((int) tb_userChoice.insert(Convert.userChoiceToContentValues(obj_userChoice)));
    }

    public void table_history() {
        //Do not continue if there is something in the database..
        if( tb_history.count() > 0 ){
            return;
        }

        //obj_history.set("");
        //obj_history.set("");
        //obj_history.set("");
        //id_history.add((int) tb_history.insert(Convert.historyToContentValues(obj_history)));
    }

    public void table_faqs() {
        //Do not continue if there is something in the database..
        if( tb_faqs.count() > 0 ){
            return;
        }

        obj_faqs.setFk_user(id_user.get(0));
        obj_faqs.setFk_doctor(id_doctor.get(0));
        obj_faqs.setQuestion("Faq Question 1");
        obj_faqs.setAnswer("Faq Answer 1");
        obj_faqs.setCreate_at("12/03/2020");
        id_faqs.add((int) tb_faqs.insert(Convert.faqToContentValues(obj_faqs)));

        obj_faqs.setFk_user(id_user.get(0));
        obj_faqs.setFk_doctor(id_doctor.get(0));
        obj_faqs.setQuestion("Faq Question 2");
        obj_faqs.setAnswer("Faq Answer 2");
        obj_faqs.setCreate_at("12/02/2020");
        id_faqs.add((int) tb_faqs.insert(Convert.faqToContentValues(obj_faqs)));

        obj_faqs.setFk_user(id_user.get(0));
        obj_faqs.setFk_doctor(id_doctor.get(1));
        obj_faqs.setQuestion("Faq Question 3");
        obj_faqs.setAnswer("Faq Answer 3");
        obj_faqs.setCreate_at("03/06/2020");
        id_faqs.add((int) tb_faqs.insert(Convert.faqToContentValues(obj_faqs)));

        obj_faqs.setFk_user(id_user.get(0));
        obj_faqs.setFk_doctor(id_doctor.get(0));
        obj_faqs.setQuestion("Faq Question 4");
        obj_faqs.setAnswer("Faq Answer 4");
        obj_faqs.setCreate_at("02/03/2020");
        id_faqs.add((int) tb_faqs.insert(Convert.faqToContentValues(obj_faqs)));

        obj_faqs.setFk_user(id_user.get(0));
        obj_faqs.setFk_doctor(id_doctor.get(2));
        obj_faqs.setQuestion("Faq Question 5");
        obj_faqs.setAnswer("Faq Answer 5");
        obj_faqs.setCreate_at("24/03/2020");
        id_faqs.add((int) tb_faqs.insert(Convert.faqToContentValues(obj_faqs)));

        obj_faqs.setFk_user(id_user.get(0));
        obj_faqs.setFk_doctor(id_doctor.get(1));
        obj_faqs.setQuestion("Faq Question 6");
        obj_faqs.setAnswer("Faq Answer 6");
        obj_faqs.setCreate_at("13/05/2020");
        id_faqs.add((int) tb_faqs.insert(Convert.faqToContentValues(obj_faqs)));
    }
}