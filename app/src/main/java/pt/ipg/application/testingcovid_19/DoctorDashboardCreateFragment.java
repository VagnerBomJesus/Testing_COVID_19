package pt.ipg.application.testingcovid_19;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import pt.ipg.application.testingcovid_19.database.ContentProvider;
import pt.ipg.application.testingcovid_19.database.Convert;
import pt.ipg.application.testingcovid_19.object.Choice;
import pt.ipg.application.testingcovid_19.object.Question;


public class DoctorDashboardCreateFragment extends Fragment implements
        AdapterView.OnItemSelectedListener {

    public final static int MIN_WEIGHT = 0;
    public final static int MAX_WEIGHT = 5;

    public static String[] optionType = {"ToggleButton", "CheckBox", "RadioButton", "TextView", "Number"};

    private Button btn_add_option, btn_save;

    private EditText editTextQuestion;
    ArrayList<EditText> editTextOption = new ArrayList<>();
    ArrayList<TextView> textViewWeight = new ArrayList<>();
    ArrayList<Button> btnMoreAndLess = new ArrayList<>();
    ArrayList<TextView> textViews = new ArrayList<>();
    ArrayList<LinearLayout> ListOptionGroupLayout = new ArrayList<>();
    private int num_option = 0;
    private LinearLayout linearLayoutRoot;
    private int position = -1;
    private Context context;

    // it's depend
    private boolean save_condition = true;

    // local variable
    private String local_question;
    private String[] local_option;
    private Integer[] local_weight;
    private String[] local_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard_doctor_create, container,false);

        context = getContext();
        linearLayoutRoot = (LinearLayout) view.findViewById(R.id.layout_optionPlace);
        editTextQuestion = (EditText) view.findViewById(R.id.question);
        setSpinnerContent(view);
        btn_add_option = view.findViewById(R.id.addOption);
        btn_add_option.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
            @Override
            public void onClick(View view) {
                addOption(view);
            }
        });
        btn_save = view.findViewById(R.id.save);
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // verify if is valid information
                verifyCondition(position);

                if(save_condition){
                    if( position == 0 ){
                        ToggleButtonToVariable();
                    }

                    saveQuestion(local_question, local_option, local_weight, local_type);
                    // clear all views from layout
                    if( num_option > 0 ){
                        editTextQuestion.setText("");
                        linearLayoutRoot.removeAllViews();
                        num_option = 0;
                    }

                    // go back to preview activity
                    Intent intent = new Intent(getContext(), DoctorDashboardActivity.class);
                    context.startActivity(intent);
                }else{
                    Toast.makeText(context, "Error in the fields please check again", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void ToggleButtonToVariable(){
        local_question = editTextQuestion.getText().toString();
        int len = editTextOption.size();
        local_option = new String[len];
        local_type = new String[len];
        for(int i=0; i<len; i++){
            local_option[i] = editTextOption.get(i).getText().toString();
            local_type[i] = "0"; //TODO define type of option..
        }
        len = textViewWeight.size();
        local_weight = new Integer[len];
        for(int i=0; i<len; i++){
            local_weight[i] = Integer.parseInt(textViewWeight.get(i).getText().toString());
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addOption(View view){
        if( position == 0 ){
            make_ToggleButton(context);
        }
    }

    private void verifyCondition(int pos){
        save_condition = true;
        String question = editTextQuestion.getText().toString().trim();
        if(question.isEmpty()|| question.length() < 3){
            save_condition = false;
        }
        if(pos == 0){
            int len = editTextOption.size();
            if(len < 2){
                save_condition = false;
            }
            for(int i=0; i<len; i++){
                String opt = editTextOption.get(i).getText().toString().trim();
                if(opt.isEmpty()||opt.length()<1){
                    save_condition = false;
                }
            }
        }
    }

    private void setSpinnerContent( View view ) {
        Spinner spin = (Spinner) view.findViewById(R.id.spinner_option);
        spin.setOnItemSelectedListener(this);
        //Creating the ArrayAdapter instance having the Options list
        ArrayAdapter aa = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, optionType);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void make_ToggleButton(Context context){
        // TODO save fragment status..
        // Create a LayoutParams for TextView
        LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT // Height of TextView
        );

        final LinearLayout horizontalGroup = new LinearLayout(context);

        // Save BUTTON on btnMoreAndLess
        Button button = new Button(context);
        btnMoreAndLess.add(button);

        // New textView for weight...
        int pos = textViews.size();
        TextView textViewWeight = new TextView(context);
        textViews.add(textViewWeight);

        button.setLayoutParams(new LinearLayout.LayoutParams(
                80, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT // Height of TextView
        ));
        button.setText("-");
        button.setTag(pos);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = (int) view.getTag();
                TextView textView = textViews.get(pos);

                int num = Integer.parseInt(textView.getText().toString());
                if( num > MIN_WEIGHT ){
                    textView.setText("" + (num-1));
                }

                if( num < 1 ){
                    // Delete linear layout by id
                    LinearLayout g =  ListOptionGroupLayout.get(pos);
                    g.removeAllViews();
                    editTextOption.remove(pos); // delete the previews array position
                }
            }
        });

        horizontalGroup.addView(button);

        textViewWeight.setText("0");
        horizontalGroup.addView(textViewWeight);
        this.textViewWeight.add(textViewWeight);

        button = new Button(context);
        button.setTag(pos);
        button.setLayoutParams(new LinearLayout.LayoutParams(
                80, // Width of TextView
                LinearLayout.LayoutParams.WRAP_CONTENT // Height of TextView
        ));
        button.setText("+");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                TextView textView = textViews.get(pos);
                int num = Integer.parseInt(textView.getText().toString());
                if( num < MAX_WEIGHT ){
                    textView.setText("" + (num+1));
                }
            }
        });

        // Save BUTTON on btnMoreAndLess and Horizontal Layout
        horizontalGroup.addView(button);
        btnMoreAndLess.add(button);

        EditText INPUT = new EditText(context);
        INPUT.setHint("Option " + (num_option+1));

        // Apply the layout parameters to TextView widget
        INPUT.setLayoutParams(ll);
        horizontalGroup.addView(INPUT);
        editTextOption.add(INPUT);
        ListOptionGroupLayout.add(horizontalGroup);

        // ADD View to Layout...
        linearLayoutRoot.addView(horizontalGroup);
        num_option++;
    }

    private void saveQuestion(String question, String[] option, Integer[] weight, String[] type){
        int id_doctor = 1;

        Question obj_question = new Question();
        obj_question.setQuestion(question);
        obj_question.setFk_doctor(id_doctor);

        try {
            ContentResolver resolver = getActivity().getContentResolver();
            Uri uri = resolver.insert(ContentProvider.QUESTION_ADDRESS, Convert.questionToContentValues(obj_question));
            int id_question = Integer.parseInt(uri.getLastPathSegment());
            for(int i=0; i<option.length; i++){
                Choice obj_choice = new Choice();
                obj_choice.setChoice(option[i]);
                obj_choice.setWeight(weight[i]);
                obj_choice.setType(type[i]);
                obj_choice.setFk_question(id_question);
                resolver.insert(ContentProvider.CHOICES_ADDRESS, Convert.choicesToContentValues(obj_choice));
            }
           Toast.makeText(getContext(), "Added successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) { System.out.println("Something went wrong..."); }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(this.position>-1){
            Toast.makeText(getContext(), optionType[position] , Toast.LENGTH_LONG).show();
        }
        this.position = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}